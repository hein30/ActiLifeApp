package controllers.models_panel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import models.ThreeDimensionalModels;
import views.MainWindow;
import views.models_panel.ModelsPanel;

/**
 * Controller for {@link views.models_panel.ModelsPanel}.
 */
public class ModelsPanelController {

    private ModelsPanel modelsPanel;
    private ThreeDimensionalModels models;

    public ModelsPanelController(MainWindow mainWindow, ThreeDimensionalModels models) {
        this.modelsPanel = mainWindow.getModelsPanel();
        this.models = models;

        try {
            loadModels();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialiseModelsPanelInformation();
    }

    /**
     * Load all the 3D model template files in the resource folder.
     *
     * @throws IOException
     */
    private void loadModels() throws IOException {

        URL url = ClassLoader.getSystemResource("3dmodels");

        String path = URLDecoder.decode(url.getPath(), System.getProperty("file.encoding"));
        File[] files = new File(path).listFiles();
        models.add3DModels(files);
    }

    private void initialiseModelsPanelInformation() {
        modelsPanel.updateModelInfo(models);
        modelsPanel.updateModelList(models);
    }

    public void updateSelection(int indexOfChangedItem, int stateChange) {

        models.updateSelection(indexOfChangedItem, stateChange);
        modelsPanel.updateModelInfo(models);
    }
}
