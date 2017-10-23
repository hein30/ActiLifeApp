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
     */
    public void add3DModels(File[] files) {
        for (File file : files) {
            models.add(new FileModel(file));
        }
    }

    /**
     * Add one 3-D model template file.
     *
     * @param file
     */
    public void add3DModel(File file) {
        models.add(new FileModel(file));
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
