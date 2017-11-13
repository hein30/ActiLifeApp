package models;

import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThreeDimensionalModels {

    private List<FileModel> models;

    public ThreeDimensionalModels() {
        models = new ArrayList<>();
    }

    /**
     * Add 3-D model template files.
     *
     * @param files
     * @param deletable
     */
    public void add3DModels(File[] files, boolean deletable) {
        for (File file : files) {
            models.add(new FileModel(file, deletable));
        }
    }

    /**
     * Add one 3-D model template file.
     *
     * @param file
     * @param deletable
     */
    public void add3DModel(File file, boolean deletable) {
        models.add(new FileModel(file, deletable));
    }

    public List<FileModel> getModels() {
        return models;
    }

    /**
     * Return the selected 3D model templates.
     *
     * @return - List of {@link models.FileModel} that has been selected.
     */
    public List<FileModel> getSelectedModels() {
        return models.stream().filter(model -> model.isSelected()).collect(Collectors.toList());
    }

    public boolean isNameUsed(String fileName) {
        return models.stream().filter(model -> model.getFileName().contentEquals(fileName)).count() != 0;
    }

    /**
     * Remove files and return the deleted files for logging.
     *
     * @return
     */
    public List<FileModel> removeSelectedModels() {

        List<FileModel> filesToDelete = getSelectedModels().stream().filter(fileModel -> fileModel.isDeletable()).collect(Collectors.toList());

        filesToDelete.forEach(fileToDelete -> {
            models.remove(fileToDelete);
            fileToDelete.getFile().delete();
        });

        return filesToDelete;
    }

    /**
     * The number of selected 3D model templates.
     *
     * @return
     */
    public int getNumOfSelectedModels() {
        return getSelectedModels().size();
    }

    public void updateSelection(int indexOfChangedItem, int stateChange) {
        models.get(indexOfChangedItem).setSelected(stateChange == ItemEvent.SELECTED);
    }
}
