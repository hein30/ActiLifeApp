package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import controllers.ImportDragAndDrop;
import java.awt.dnd.DropTarget;
import javax.swing.JTabbedPane;
import models.ImportedData;

public class TabbedPane extends JTabbedPane {

    private ImportPanel importPanel;
    private DataPanelCopy dataPanel;

    private ImportDragAndDrop dragAndDropListener;

    public TabbedPane() {
        importPanel = new ImportPanel();
        add("Import", importPanel);

        dataPanel = new DataPanelCopy();
        add("Data", dataPanel);

        enableDragAndDrop();
    }

    public void updateView(ImportedData data) {
        importPanel.updateImportFileList(data);
        dataPanel.updateView(data);
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
