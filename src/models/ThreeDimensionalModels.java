package models;

import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThreeDimensionalModels {

    private List<ThreeDimensionalModel> models;

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
            models.add(new ThreeDimensionalModel(file));
        }
    }

    /**
     * Add one 3-D model template file.
     *
     * @param file
     */
    public void add3DModel(File file) {
        models.add(new ThreeDimensionalModel(file));
    }

    public List<ThreeDimensionalModel> getModels() {
        return models;
    }

    /**
     * Return the selected 3D model templates.
     *
     * @return - List of {@link models.ThreeDimensionalModels.ThreeDimensionalModel} that has been selected.
     */
    public List<ThreeDimensionalModel> getSelectedModels() {
        return models.stream().filter(model -> model.selected).collect(Collectors.toList());
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
        models.get(indexOfChangedItem).selected = stateChange == ItemEvent.SELECTED;
    }

    /**
     * Represents one 3D Model template file.
     */
    public class ThreeDimensionalModel {

        private File file;
        private boolean selected;

        public ThreeDimensionalModel(File file) {
            this.setFile(file);
        }

        public String getName() {
            return file.getName();
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
}
