package models;

import java.io.File;
import java.util.StringJoiner;

public class GeneratedFileModel extends FileModel {

    private Subject subject;

    public GeneratedFileModel(Subject subject) {
        this.subject = subject;
    }

    public GeneratedFileModel(Subject subject, File file) {
        super(file);
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDetails() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("Sedentary: " + subject.getSedentary());
        sj.add("Light: " + subject.getLight());
        sj.add("Moderate: " + subject.getModerate());
        sj.add("Vigorous: " + subject.getVigorous());
        sj.add("File: " + getFile().getAbsolutePath());

        return sj.toString();
    }

    @Override
    public String toString() {
        if (getFile() == null) {
            return subject.getSubjectId();
        } else {
            return getFileName();
        }
    }
}
