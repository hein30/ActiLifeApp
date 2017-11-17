package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import models.FileModel;
import models.ImportedData;
import models.ThreeDModels;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import utils.FileGenerator;
import utils.ModelLoader;
import views.MainWindow;
import views.data_panel.tabbedPane.ModelImportPanel;
import views.models_panel.FileGenerationProgressBar;
import views.models_panel.ModelsPanel;

/**
 * Controller for {@link views.models_panel.ModelsPanel}.
 * <p>
 *
 * @author Hein Min Htike
 */
public class ModelsPanelController extends BaseController {
    private DataPanelController dataPanelController;
    private LogController logger;

    private ModelsPanel modelsPanel;
    private ModelImportPanel modelImportPanel;

    private ThreeDModels models;
    private ImportedData importedData;

    private File defaultDestinationFolder;

    private FileGenerationProgressBar progressBar;

    public ModelsPanelController(MainWindow mainWindow, ThreeDModels models, ImportedData importedData, LogController logger) {

        this.modelsPanel = mainWindow.getModelsPanel();
        this.modelImportPanel = mainWindow.getDataPanel().getTabbedPane().getModelImportPanel();
        this.models = models;
        this.importedData = importedData;
        this.logger = logger;

        buildDefaultDestinationFolder();

        ModelLoader.loadAllModels(models);

        initialiseModelsPanelInformation();
    }

    private void buildDefaultDestinationFolder() {
        StringJoiner sj = new StringJoiner(File.separator);
        sj.add(System.getProperty("user.home"));
        sj.add("Documents");
        sj.add("ActiLife Automation");

        defaultDestinationFolder = new File(sj.toString());
        defaultDestinationFolder.mkdirs();
    }

    private void initialiseModelsPanelInformation() {
        modelsPanel.updateViews(models, importedData);
        modelImportPanel.updateModelList(models);
    }

    public void updateSelection(int indexOfChangedItem, int stateChange) {

        models.updateSelection(indexOfChangedItem, stateChange);
        modelsPanel.updateViews(models, importedData);
    }

    @Override
    public void importFiles(List<FileModel> files) {
        File folder = ModelLoader.getCustomModelsFolder();

        printExistingFileNames(files);

        List<FileModel> newFilesToAdd = files.stream().filter(newFileModel -> !models.isNameUsed(newFileModel.getFileName())).collect(Collectors.toList());
        log("Models added: " + newFilesToAdd.stream().map(FileModel::getFileName).collect(Collectors.toList()).toString());

        newFilesToAdd.forEach(file -> {
            try {
                copyToFolder(folder, file);
            } catch (IOException e) {
                e.printStackTrace();
                //todo handle exception here.
            }
        });

        initialiseModelsPanelInformation();
    }

    private void printExistingFileNames(List<FileModel> files) {
        List<FileModel> existingNames = files.stream().filter(newFileModel -> models.isNameUsed(newFileModel.getFileName())).collect(Collectors.toList());
        logError("Models with same names already exist. Please rename them.");
        logError("Cannot add files: " + existingNames.stream().map(FileModel::getFileName).collect(Collectors.toList()).toString());
    }

    private void copyToFolder(File folder, FileModel original) throws IOException {
        File desFile = new File(folder.getAbsolutePath() + File.separator + original.getFileName());
        FileUtils.copyFile(original.getFile(), desFile);
        models.add3DModel(desFile, true);
    }

    @Override
    public void deleteFiles(List<String> fileNamesToDelete) {

        List<FileModel> deletedFiles = models.removeSelectedModels(fileNamesToDelete);

        if (!deletedFiles.isEmpty()) {
            log("Deleted 3D models: " + deletedFiles.stream().map(FileModel::getFileName).collect(Collectors.toList()).toString());
        }

        initialiseModelsPanelInformation();
    }

    @Override
    public void updateGeneratedFilesView(ImportedData defaultDestinationFolder) {
        logger.logInfo("3D model generation completed.");
        dataPanelController.updateGeneratedFilesView(importedData);
        progressBar.dispose();
        toggleAllButtons(true);
    }

    /**
     * Generate 3D model OpenJSCAD file and subsequently STL files.
     */
    public void generateModels() {
        logger.logInfo("3D model generation starting.");
        logger.logInfo("Selected models: " + models.getSelectedModels().stream().map(FileModel::getFileName).map(fileName -> FilenameUtils.removeExtension(fileName)).collect(Collectors.toList()).toString());

        JFileChooser destChooser = new JFileChooser();
        destChooser.setDialogTitle("Choose destination");
        destChooser.setCurrentDirectory(defaultDestinationFolder);
        destChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        destChooser.setAcceptAllFileFilterUsed(false);
        destChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        int returnVal = destChooser.showDialog(null, "Select");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            defaultDestinationFolder = destChooser.getSelectedFile();
            logger.logInfo("Saving models to " + defaultDestinationFolder.getAbsolutePath());

            progressBar = new FileGenerationProgressBar(0, models.getNumOfSelectedModels() * importedData.getNumSelectedPeople());

            FileGenerator fileGenerator = new FileGenerator(this, progressBar, importedData, models, defaultDestinationFolder);
            Thread fileGenerationThread = new Thread(fileGenerator);
            toggleAllButtons(false);
            fileGenerationThread.start();
        } else {
            logger.logInfo("3D model generation cancelled.");
        }
    }

    public void toggleAllButtons(boolean isEnabled) {
        dataPanelController.toggleAllButtons(isEnabled);
        modelImportPanel.toggleButtons(isEnabled);
        toggleAllButtonsOnModelOptionsPanel(isEnabled);
    }

    public void toggleAllButtonsOnModelOptionsPanel(boolean isEnabled) {
        modelsPanel.toggleButtons(isEnabled);
    }

    public void setDataPanelController(DataPanelController dataPanelController) {
        this.dataPanelController = dataPanelController;
    }

    public void logError(String s) {
        logger.logError(s);
    }

    public void log(String s) {
        logger.logInfo(s);
    }
}
