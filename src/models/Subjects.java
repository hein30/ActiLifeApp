package models;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import utils.ImportDataReader;

/**
 * Represents subjects from a single excel/csv import file.
 */
public class Subjects {

    private FileModel file;
    private List<Subject> subjectList;

    public Subjects(FileModel file) throws IOException {
        this.file = file;
        subjectList = ImportDataReader.readImportedFile(file.getFile());
    }

    public String getFileName() {
        return file.getFileName();
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public int numSubjects() {
        return subjectList.size();
    }

    public List<Subject> getSelectedSubjects() {
        return subjectList.stream().filter(subject -> subject.isSelected()).collect(Collectors.toList());
    }

    public int getNumberOfSelectedSubjects() {
        return getSelectedSubjects().size();
    }
}
