package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import models.FileModel;
import models.ImportedData;
import models.ThreeDimensionalModels;
import org.apache.commons.io.FilenameUtils;
import utils.FileGenerator;
import views.MainWindow;
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
    private ThreeDimensionalModels models;
    private ImportedData importedData;

    private File defaultDestinationFolder;

    private FileGenerationProgressBar progressBar;

    public ModelsPanelController(MainWindow mainWindow, ThreeDimensionalModels models, ImportedData importedData, LogController logger) {

        this.modelsPanel = mainWindow.getModelsPanel();
        this.models = models;
        this.importedData = importedData;
        this.logger = logger;

        buildDefaultDestinationFolder();

        try {
            loadModels();
        } catch (IOException e) {
            //todo handle failure here.
            e.printStackTrace();
        }

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

    /**
     * Load all the 3D model template files in the resource folder.
     *
     * @throws IOException
     */
    private void loadModels() throws IOException {

        URL url = ClassLoader.getSystemResource("3dmodels");

        String path = URLDecoder.decode(url.getPath(), System.getProperty("file.encoding"));
        File[] files = new File(path).listFiles();
        models.add3DModels(files);
    }

    private void initialiseModelsPanelInformation() {
        modelsPanel.updateViews(models);
    }

    public void updateSelection(int indexOfChangedItem, int stateChange) {

        models.updateSelection(indexOfChangedItem, stateChange);
        modelsPanel.updateViews(models);
    }

    @Override
    public void importFile(List<FileModel> files) {
//todo handle importing templates
    }

    @Override
    public void deleteFile(List fileNamesToDelete) {
//todo handle removing templates
    }

    @Override
    public void updateGeneratedFilesView(File defaultDestinationFolder) {
        logger.logInfo("3D model generation completed.");
        dataPanelController.updateGeneratedFilesView(this.defaultDestinationFolder);
        progressBar.dispose();
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

            progressBar = new FileGenerationProgressBar(0, models.getNumOfSelectedModels() * importedData.getNumPeople());

            FileGenerator fileGenerator = new FileGenerator(this, progressBar, importedData, models, defaultDestinationFolder);
            Thread fileGenerationThread = new Thread(fileGenerator);
            fileGenerationThread.start();
        } else {
            logger.logInfo("3D model generation cancelled.");
        }
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
