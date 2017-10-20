import controllers.models_panel.ModelsPanelController;
import models.ThreeDimensionalModels;
import views.MainWindow;

public class App {

    public static void main(String[] args) {

        //Maintain all the imported 3D model template files.
        ThreeDimensionalModels models = new ThreeDimensionalModels();
        MainWindow mw = new MainWindow();

        ModelsPanelController modelsPanelController = new ModelsPanelController(mw, models);


        mw.setModelsPanelController(modelsPanelController);
    }
}
