package controllers;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import models.FileModel;

/**
 * Implements drag and drop operation for importing files.
 *
 * @author Hein Min Htike
 */
public class ImportDragAndDrop implements DropTargetListener {

    private BaseController controller;

    public void setController(BaseController controller) {
        this.controller = controller;
    }

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
            //get all the dropped files.
            List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

            List<FileModel> fileModels = files.stream().filter(fileExtensionFilter()).map(FileModel::new).collect(Collectors.toList());
            controller.importFile(fileModels);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Filter the dropped files using file type.
     *
     * @return
     */
    private Predicate<File> fileExtensionFilter() {
        return file -> file.getName().endsWith(".xlsx");
    }
}
