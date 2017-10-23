package models;

import java.io.File;

/**
 * Represents files.
 */
public class FileModel {

    private String fileName;
    private File file;
    private boolean selected;

    public FileModel(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

