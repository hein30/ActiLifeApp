package controllers;

import java.io.IOException;
import java.util.List;
import models.FileModel;
import models.ImportedData;
import views.MainWindow;
import views.data_panel.DataPanel;

public class DataPanelController extends BaseController {
    private DataPanel dataPanel;
    private ImportedData data;

    public DataPanelController(MainWindow mw, ImportedData data) {
        this.dataPanel = mw.getDataPanel();
        this.data = data;
    }

    private void updateDataViews() {
        dataPanel.updateDataViews(data);
    }

    @Override
    public void importFile(List<FileModel> files) {
        try {
            data.addFiles(files);
        } catch (IOException e) {
            //todo log here
        }
        updateDataViews();
    }

    @Override
    public void deleteFile() {

    }
}
