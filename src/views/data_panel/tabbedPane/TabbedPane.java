package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JTabbedPane;
import models.ImportedData;

public class TabbedPane extends JTabbedPane {

    private ImportPanel importPanel;
    private DataPanelCopy dataPanel;

    private DataPanelController dataPanelController;

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
        DropTarget target = new DropTarget(this, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {

                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                try {
                    List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    System.out.println("drop");
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setController(DataPanelController controller) {
        this.dataPanelController = controller;

        importPanel.setController(controller);
    }


}
