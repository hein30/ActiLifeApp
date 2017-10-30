import controllers.DataPanelController;
import controllers.ModelsPanelController;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import models.ImportedData;
import models.ThreeDimensionalModels;
import views.MainWindow;

public class App {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());

        //Maintain all the imported 3D model template files.
        ThreeDimensionalModels models = new ThreeDimensionalModels();
        ImportedData importedData = new ImportedData();

        MainWindow mw = new MainWindow();

        DataPanelController dataPanelController = new DataPanelController(mw, importedData);
        mw.setDataPanelController(dataPanelController);

        ModelsPanelController modelsPanelController = new ModelsPanelController(mw, models, importedData);
        modelsPanelController.setDataPanelController(dataPanelController);
        mw.setModelsPanelController(modelsPanelController);
    }
}
