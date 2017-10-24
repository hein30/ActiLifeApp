import controllers.DataPanelController;
import controllers.ModelsPanelController;
import models.ImportedData;
import models.ThreeDimensionalModels;
import views.MainWindow;

public class App {

    public static void main(String[] args) {

        //Maintain all the imported 3D model template files.
        ThreeDimensionalModels models = new ThreeDimensionalModels();
        ImportedData importedData = new ImportedData();

        MainWindow mw = new MainWindow();

        DataPanelController dataPanelController = new DataPanelController(mw, importedData);
        mw.setDataPanelController(dataPanelController);

        ModelsPanelController modelsPanelController = new ModelsPanelController(mw, models);
        mw.setModelsPanelController(modelsPanelController);
    }
}
