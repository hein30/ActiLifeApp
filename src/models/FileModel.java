package models;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

/**
 * Represents files.
 */
public class FileModel {

    private String fileName;
    private File file;
    private boolean selected;
    private boolean deletable;

    public FileModel() {

    }

    public FileModel(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public FileModel(File file, boolean deletable) {
        this.file = file;
        this.fileName = file.getName();
        this.deletable = deletable;
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

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    @Override
    public String toString() {
        return FilenameUtils.removeExtension(fileName) + (deletable ? "" : " (built-in)");
    }
}

