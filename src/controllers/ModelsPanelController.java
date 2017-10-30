package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.StringJoiner;
import javax.swing.JFileChooser;
import models.FileModel;
import models.ImportedData;
import models.Subject;
import models.Subjects;
import models.ThreeDimensionalModels;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
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

    private static final String openJscadCMD;

    static {
        // todo open jscad command should be made to work with Linux/Mac
        openJscadCMD = "C:" +
                File.separator +
                "Users" +
                File.separator +
                System.getProperty("user.name") +
                File.separator +
                "AppData" + File.separator + "Roaming" + File.separator + "npm" + File.separator + "openjscad.cmd";
    }

    private DataPanelController dataPanelController;

    private ModelsPanel modelsPanel;
    private ThreeDimensionalModels models;
    private ImportedData importedData;

    private File defaultDestinationFolder;

    public ModelsPanelController(MainWindow mainWindow, ThreeDimensionalModels models, ImportedData importedData) {
        StringJoiner sj = new StringJoiner(File.separator);
        sj.add(System.getProperty("user.home"));
        sj.add("Documents");
        sj.add("ActiLife Automation");

        defaultDestinationFolder = new File(sj.toString());
        defaultDestinationFolder.mkdirs();

        this.modelsPanel = mainWindow.getModelsPanel();
        this.models = models;
        this.importedData = importedData;

        try {
            loadModels();
        } catch (IOException e) {
            //todo handle failure here.
            e.printStackTrace();
        }

        initialiseModelsPanelInformation();
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

    }

    @Override
    public void deleteFile() {

    }

    @Override
    public void updateGeneratedFilesView(File defaultDestinationFolder) {
        dataPanelController.updateGeneratedFilesView(this.defaultDestinationFolder);
    }

    /**
     * Generate 3D model OpenJSCAD file and subsequently STL files.
     */
    public void generateModels() {

        JFileChooser destChooser = new JFileChooser();
        destChooser.setDialogTitle("Choose destination");
        destChooser.setCurrentDirectory(defaultDestinationFolder);
        destChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        destChooser.setAcceptAllFileFilterUsed(false);
        destChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        int returnVal = destChooser.showDialog(null, "Select");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            defaultDestinationFolder = destChooser.getSelectedFile();
            generateFiles();

            updateGeneratedFilesView(this.defaultDestinationFolder);
        }


    }

    private void generateFiles() {
        importedData.getFileMap().forEach((k, v) -> generateFilesForOneImportFile(k, v));
    }

    private void generateFilesForOneImportFile(String fileName, Subjects subjects) {
        String folderForThisImportFile = defaultDestinationFolder.getAbsolutePath() + File.separator
                + fileName;

        File folderForThisImport = new File(folderForThisImportFile);
        folderForThisImport.mkdirs();


        for (FileModel model : models.getSelectedModels()) {

            Template template = buildTemplate(model);

            subjects.getSubjectList().forEach(subject -> generateFile(subject, model, folderForThisImportFile, template));
        }

        System.out.println("");
    }

    private Template buildTemplate(FileModel model) {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty("file.resource.loader.path", model.getFile().getParent());
        engine.init();

        return engine.getTemplate(model.getFile().getName());
    }

    private void generateFile(Subject subject, FileModel model, String folderForThisImportFile, Template template) {
        VelocityContext context = buildContext(subject);
        String outputFile = folderForThisImportFile + File.separator + subject.getSubjectId() + "_" + FilenameUtils.removeExtension(model.getFileName());
        String outputJSCADFile = outputFile + ".jscad";
        String outputSTLFile = outputFile + ".stl";

        try {
            FileWriter writer = new FileWriter(outputJSCADFile);
            template.merge(context, writer);
            writer.close();

            generateSTLFile(outputJSCADFile, outputSTLFile);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error writing jscad file.");

            //todo add more information here in case it failed and throw exception.
        }
    }

    private void generateSTLFile(String outputJSCADFile, String outputSTLFile) {

        StringBuffer output = new StringBuffer();

        try {
            Process p = Runtime.getRuntime().exec(new String[]{openJscadCMD, outputJSCADFile});
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private VelocityContext buildContext(Subject subject) {
        VelocityContext context = new VelocityContext();
        context.put("subjectId", "'" + subject.getSubjectId() + "'");
        context.put("mpa", subject.getModerate().toString());
        context.put("vpa", subject.getVigorous().toString());
        context.put("lpa", subject.getLight().toString());
        context.put("spa", subject.getSedentary().toString());

        return context;
    }

    public void setDataPanelController(DataPanelController dataPanelController) {
        this.dataPanelController = dataPanelController;
    }
}
