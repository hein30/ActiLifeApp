package utils;

import controllers.ModelsPanelController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import models.FileModel;
import models.ImportedData;
import models.Subject;
import models.Subjects;
import models.ThreeDModels;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import views.models_panel.FileGenerationProgressBar;

public class FileGenerator implements Runnable {

    private static final String openJSCAD;

    static {
        String fileName = System.getProperty("os.name").contains("Windows") ? "openjscad.cmd" : "openjscad";

        URL url = ClassLoader.getSystemResource("openjscad");
        String path = null;
        try {
            path = URLDecoder.decode(url.getPath(), System.getProperty("file.encoding"));

        } catch (UnsupportedEncodingException e) {
            //todo throw exception here to notify that openjscad library is missing.
            System.out.println("Missing openjscad library");
            e.printStackTrace();
        }

        openJSCAD = path + File.separator + fileName;

        if (openJSCAD.endsWith("openjscad")) {

            try {
                Process p = Runtime.getRuntime().exec("chmod a+x " + openJSCAD);
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                System.out.println("Failed to set permissions");
                e.printStackTrace();
            }
        }
    }

    private FileGenerationProgressBar progressBar;
    private ImportedData importedData;
    private ThreeDModels models;
    private File defaultDestinationFolder;
    private ModelsPanelController controller;

    public FileGenerator(ModelsPanelController controller, FileGenerationProgressBar progressBar, ImportedData importedData, ThreeDModels models, File defaultDesinationFolder) {
        this.progressBar = progressBar;
        this.importedData = importedData;
        this.models = models;
        this.defaultDestinationFolder = defaultDesinationFolder;
        this.controller = controller;
    }

    @Override
    public void run() {
        importedData.getFileMap().forEach((k, v) -> generateFilesForOneImportFile(k, v));
        controller.updateGeneratedFilesView(importedData);
    }

    private void generateFilesForOneImportFile(String fileName, Subjects subjects) {
        String folderForThisImportFile = defaultDestinationFolder.getAbsolutePath() + File.separator
                + fileName;

        File folderForThisImport = new File(folderForThisImportFile);
        folderForThisImport.mkdirs();


        for (FileModel model : models.getSelectedModels()) {
            Template template = buildTemplate(model);

            subjects.getSelectedSubjects().forEach(subject -> generateFile(subject, model, folderForThisImportFile, template));
        }
    }

    private Template buildTemplate(FileModel model) {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty("file.resource.loader.path", model.getFile().getParent());
        engine.init();

        return engine.getTemplate(model.getFile().getName());
    }

    private void generateFile(Subject subject, FileModel model, String folderForThisImportFile, Template template) {
        progressBar.incrementProgress(1);

        VelocityContext context = buildContext(subject);
        String outputFile = folderForThisImportFile + File.separator + FilenameUtils.removeExtension(
                subject.getFileName()) + "_" + FilenameUtils.removeExtension(model.getFileName());
        String outputJSCADFile = outputFile + ".jscad";
        String outputSTLFile = outputFile + ".stl";

        try {
            FileWriter writer = new FileWriter(outputJSCADFile);
            template.merge(context, writer);
            writer.close();

            generateSTLFile(subject, outputJSCADFile, outputSTLFile);

        } catch (IOException e) {
            e.printStackTrace();
            controller.logError("Error while writing to jscad");
            controller.logError(e.toString());
            System.out.println("error writing jscad file.");
            //todo add more information here in case it failed and throw exception.
        }
    }

    private void generateSTLFile(Subject subject, String outputJSCADFile, String outputSTLFile) {
        StringBuffer output = new StringBuffer();
        try {

            Process p = Runtime.getRuntime().exec(new String[]{openJSCAD, outputJSCADFile});
            p.waitFor();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            controller.logError("Error generating .stl files.");
            controller.logError(e.toString());
        } finally {
            File stl = new File(outputSTLFile);
            if (!stl.exists()) {
                controller.logError("Failed to generate " + outputSTLFile);
            } else {
                subject.addOneGeneratedFile(stl);
                new File(outputJSCADFile).delete();
            }
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
