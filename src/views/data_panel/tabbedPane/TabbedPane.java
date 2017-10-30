package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import controllers.ImportDragAndDrop;
import java.awt.dnd.DropTarget;
import java.io.File;
import javax.swing.JTabbedPane;
import models.ImportedData;

public class TabbedPane extends JTabbedPane {

    private ImportPanel importPanel;
    private DataPanelCopy dataPanel;
    private GeneratedFilesPanel generatedFilesPanel;

    private ImportDragAndDrop dragAndDropListener;

    public TabbedPane() {
        importPanel = new ImportPanel();
        add("Import", importPanel);

        dataPanel = new DataPanelCopy();
        add("Data", dataPanel);

        generatedFilesPanel = new GeneratedFilesPanel();
        add("Generated Files", generatedFilesPanel);

        enableDragAndDrop();
    }

    public void updateView(ImportedData data) {
        importPanel.updateImportFileList(data);
        dataPanel.updateView(data);
    }

    public void updateGenerateFilesView(File rootDirectory) {
        generatedFilesPanel.updateGeneratedFilesView(rootDirectory);
    }

    /**
     * Implement Drag and Drop operation.
     */
    private void enableDragAndDrop() {
        dragAndDropListener = new ImportDragAndDrop();
        new DropTarget(this, dragAndDropListener);
    }


    public void setController(DataPanelController controller) {

        importPanel.setController(controller);
        dragAndDropListener.setController(controller);
    }
}
