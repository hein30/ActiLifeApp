package controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import models.FileModel;
import models.ImportedData;
import models.Subject;
import models.Subjects;
import views.MainWindow;
import views.data_panel.DataPanel;

public class DataPanelController extends BaseController {
    private DataPanel dataPanel;
    private ImportedData data;
    private LogController logger;

    private ModelsPanelController modelsPanelController;

    public DataPanelController(MainWindow mw, ImportedData data, LogController logger) {
        this.dataPanel = mw.getDataPanel();
        this.data = data;
        this.logger = logger;
    }

    /**
     * Update the data views as well as the model options panel.
     */
    private void updateDataViews() {
        dataPanel.updateDataViews(data);
        modelsPanelController.toggleAllButtonsOnModelOptionsPanel(data.getNumSelectedPeople() == 0 ? false : true);
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

    public void setModelsPanelController(ModelsPanelController modelsPanelController) {
        this.modelsPanelController = modelsPanelController;
    }

    @Override
    public void updateGeneratedFilesView(ImportedData importedData) {
        dataPanel.updateGenerateFilesView(importedData);
    }

    @Override
    public void toggleAllButtons(boolean isEnabled) {
        if (!data.getFileMap().isEmpty()) {
            dataPanel.toggleAllButtons(isEnabled);
        }
    }

    public void changeSelectionForSubject(Subject subject) {
        subject.setSelected(!subject.isSelected());
        updateDataViews();
    }

    public void deselectAllSubjects() {
        changeSelectedValues(false);
        updateDataViews();
    }

    private void changeSelectedValues(boolean selected) {
        data.getFileMap().values().stream().map(Subjects::getSubjectList).flatMap(subjects -> subjects.stream()).forEach(subject ->
                subject.setSelected(selected));
    }

    public void selectAllSubjects() {
        changeSelectedValues(true);
        updateDataViews();
    }

    public void updateName(Subject subject, String name) {
        subject.setSubjectId(name);
    }
}
