package models;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportedData {

    private Map<String, Subjects> fileMap;

    public ImportedData() {
        fileMap = new HashMap<>();
    }

    /**
     * Import one file.
     *
     * @param file
     */
    public void addFile(FileModel file) throws IOException {
        fileMap.put(file.getFileName(), new Subjects(file));
    }

    /**
     * Import multiple files.
     *
     * @param files
     */
    public void addFiles(List<FileModel> files) throws IOException {
        for (FileModel file : files) {
            addFile(file);
        }
    }

    public Map<String, Subjects> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, Subjects> fileMap) {
        this.fileMap = fileMap;
    }
}
