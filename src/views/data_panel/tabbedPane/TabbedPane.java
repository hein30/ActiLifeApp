package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import controllers.ImportDragAndDrop;
import java.awt.dnd.DropTarget;
import javax.swing.JTabbedPane;
import models.ImportedData;

public class TabbedPane extends JTabbedPane {

    /**
     * Automatically generated by eclipse.
     */
    private static final long serialVersionUID = -2679305138083018523L;
    private DataImportPanel dataImportPanel;
    private DataSubjectListPanel dataSubjectListPanel;
    private GeneratedFilesPanel generatedFilesPanel;

    private ImportDragAndDrop dragAndDropListener;

    public TabbedPane() {
        dataImportPanel = new DataImportPanel();
        add("Import", dataImportPanel);

        dataSubjectListPanel = new DataSubjectListPanel();
        add("Data", dataSubjectListPanel);

        generatedFilesPanel = new GeneratedFilesPanel();
        add("Generated Files", generatedFilesPanel);

        enableDragAndDrop();
    }

    public void updateView(ImportedData data) {
        dataImportPanel.updateImportFileList(data);
        dataSubjectListPanel.updateView(data);
    }

    public void updateGenerateFilesView(ImportedData importedData) {
        generatedFilesPanel.updateGeneratedFilesView(importedData);
    }

    /**
     * Implement Drag and Drop operation.
     */
    private void enableDragAndDrop() {
        dragAndDropListener = new ImportDragAndDrop();
        new DropTarget(this, dragAndDropListener);
    }

    public void setController(DataPanelController controller) {

        dataImportPanel.setController(controller);
        dragAndDropListener.setController(controller);
    }

    public void toggleButtons(boolean isEnabled) {
        dataImportPanel.toggleButtons(isEnabled);
        dragAndDropListener.setEnabled(isEnabled);
    }
}
