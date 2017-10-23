package models;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one user of the fitness tracker.
 */
public class Subject {

    private String subjectId;
    private List<Double> sedentary;
    private List<Double> light;
    private List<Double> moderate;
    private List<Double> vigorous;
    private List<DayOfWeek> orderOfDays;
    private List<FileModel> generatedModels;

    public Subject(String subjectId) {
        this.subjectId = subjectId;

        sedentary = new ArrayList<>();
        light = new ArrayList<>();
        moderate = new ArrayList<>();
        vigorous = new ArrayList<>();

        orderOfDays = new ArrayList<>();

        generatedModels = new ArrayList<>();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public List<Double> getSedentary() {
        return sedentary;
    }

    public void setSedentary(List<Double> sedentary) {
        this.sedentary = sedentary;
    }

    public List<Double> getLight() {
        return light;
    }

    public void setLight(List<Double> light) {
        this.light = light;
    }

    public List<Double> getModerate() {
        return moderate;
    }

    public void setModerate(List<Double> moderate) {
        this.moderate = moderate;
    }

    public List<Double> getVigorous() {
        return vigorous;
    }

    public void setVigorous(List<Double> vigorous) {
        this.vigorous = vigorous;
    }

    public List<DayOfWeek> getOrderOfDays() {
        return orderOfDays;
    }

    public void setOrderOfDays(List<DayOfWeek> orderOfDays) {
        this.orderOfDays = orderOfDays;
    }

    public List<FileModel> getGeneratedModels() {
        return generatedModels;
    }

    public void setGeneratedModels(List<FileModel> generatedModels) {
        this.generatedModels = generatedModels;
    }

    public void addSendentary(double value) {
        sedentary.add(value);
    }

    public void addLight(double value) {
        light.add(value);
    }

    public void addModerate(double value) {
        moderate.add(value);
    }

    public void addVigorous(double value) {
        vigorous.add(value);
    }

    public void addDay(String value) {
        orderOfDays.add(DayOfWeek.valueOf(value.toUpperCase()));
    }
}
