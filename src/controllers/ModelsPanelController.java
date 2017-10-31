package controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import models.FileModel;
import models.ImportedData;
import models.ThreeDimensionalModels;
import org.apache.commons.io.FilenameUtils;
import utils.FileGenerator;
import views.MainWindow;
import views.models_panel.ModelsPanel;

/**
 * Controller for {@link views.models_panel.ModelsPanel}.
 * <p>
 * todo move the file generation code to a new class on a different thread.
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

    private JProgressBar jProgressBar;
    private JDialog dialog;

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
        modelsPanel.updateModelInfo(models);
        modelsPanel.updateModelList(models);
    }

    public void updateSelection(int indexOfChangedItem, int stateChange) {

        models.updateSelection(indexOfChangedItem, stateChange);
        modelsPanel.updateModelInfo(models);
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
        dialog.dispose();
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

            jProgressBar = new JProgressBar(0, models.getNumOfSelectedModels() * importedData.getNumPeople());
            jProgressBar.setPreferredSize(new Dimension(300, 20));
            jProgressBar.setString("Generating files (0 of " + jProgressBar.getMaximum() + "...");
            jProgressBar.setValue(0);
            jProgressBar.setStringPainted(true);

            JLabel jLabel = new JLabel("Progress: ");

            JPanel panel = new JPanel();
            panel.add(jLabel);
            panel.add(jProgressBar);

            dialog = new JDialog((JFrame) null, "Generating files");
            dialog.getContentPane().add(panel, BorderLayout.CENTER);
            dialog.pack();
            dialog.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - dialog.getWidth() / 2, (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - dialog.getHeight() / 2);

            dialog.setVisible(true);

            FileGenerator fileGenerator = new FileGenerator(this, jProgressBar, importedData, models, defaultDestinationFolder);
            Thread fileGenerationThread = new Thread(fileGenerator);
            fileGenerationThread.start();
        } else {
            logger.logInfo("3D model generation cancelled.");
        }
    }

    public void setDataPanelController(DataPanelController dataPanelController) {
        this.dataPanelController = dataPanelController;
    }
}
