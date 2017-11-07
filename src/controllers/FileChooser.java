package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import models.FileModel;

public class FileChooser implements ActionListener {

    private Component parent;
    private BaseController controller;

    public FileChooser(Component parent) {
        this.parent = parent;
    }

    public void addController(BaseController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(getFileNameExtFilter());
        int returnVal = chooser.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = chooser.getSelectedFiles();
            List<FileModel> fileModels = Arrays.stream(files).map(FileModel::new).collect(Collectors.toList());

            controller.importFiles(fileModels);
        }
    }


    /**
     * Get the file name extension filter, depending on the controller passed in.
     * <br>
     * If @link {@link DataPanelController} is passed in, we should read .csv/excel files. Otherwise, read the 3D model template files.
     */
    private FileNameExtensionFilter getFileNameExtFilter() {
        return controller instanceof DataPanelController ? getCSVFilter() : get3DModelTemplateFilter();
    }

    public FileNameExtensionFilter getCSVFilter() {
        return new FileNameExtensionFilter("Excel Files", "xlsx");
    }

    public FileNameExtensionFilter get3DModelTemplateFilter() {
        return new FileNameExtensionFilter("3D Model Templates", "haha");
    }
}
