package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import models.FileModel;
import models.ImportedData;
import views.MainWindow;
import views.data_panel.DataPanel;

public class DataPanelController extends BaseController {
    private DataPanel dataPanel;
    private ImportedData data;
    private LogController logger;

    public DataPanelController(MainWindow mw, ImportedData data, LogController logger) {
        this.dataPanel = mw.getDataPanel();
        this.data = data;
        this.logger = logger;
    }


    private void updateDataViews() {
        dataPanel.updateDataViews(data);
    }

    @Override
    public void importFiles(List<FileModel> files) {
        try {
            data.addFiles(files);

            logger.logInfo("Added files: " + files.stream().map(FileModel::getFileName).collect(Collectors.toList()).toString());

        } catch (IOException e) {
            //todo log here
        }
        updateDataViews();
    }

    @Override
    public void deleteFiles(List<String> fileNamesToDelete) {

        if (fileNamesToDelete.size() > 0) {
            fileNamesToDelete.forEach(name -> data.getFileMap().remove(name));
            logger.logInfo("Removed files: " + fileNamesToDelete.toString());
            updateDataViews();
        }
    }

    @Override
    public void updateGeneratedFilesView(File defaultDestinationFolder) {
        dataPanel.updateGenerateFilesView(defaultDestinationFolder);
    }
}
