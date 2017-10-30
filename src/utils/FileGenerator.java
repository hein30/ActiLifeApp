package utils;

import controllers.ModelsPanelController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JProgressBar;
import models.FileModel;
import models.ImportedData;
import models.Subject;
import models.Subjects;
import models.ThreeDimensionalModels;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class FileGenerator implements Runnable {

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

    private JProgressBar jProgressBar;
    private ImportedData importedData;
    private ThreeDimensionalModels models;
    private File defaultDestinationFolder;
    private ModelsPanelController controller;

    public FileGenerator(ModelsPanelController controller, JProgressBar jProgressBar, ImportedData importedData, ThreeDimensionalModels models, File defaultDesinationFolder) {
        this.jProgressBar = jProgressBar;
        this.importedData = importedData;
        this.models = models;
        this.defaultDestinationFolder = defaultDesinationFolder;
        this.controller = controller;
    }

    @Override
    public void run() {
        importedData.getFileMap().forEach((k, v) -> generateFilesForOneImportFile(k, v));
        controller.updateGeneratedFilesView(defaultDestinationFolder);
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
    }

    private Template buildTemplate(FileModel model) {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty("file.resource.loader.path", model.getFile().getParent());
        engine.init();

        return engine.getTemplate(model.getFile().getName());
    }

    private void generateFile(Subject subject, FileModel model, String folderForThisImportFile, Template template) {
        jProgressBar.setValue(jProgressBar.getValue() + 1);
        jProgressBar.setString("Generating files (" + jProgressBar.getValue() + " of " + jProgressBar.getMaximum() + ")...");

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
}
