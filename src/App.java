import controllers.DataPanelController;
import controllers.LogController;
import controllers.ModelsPanelController;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import models.ImportedData;
import models.ThreeDModels;
import views.MainWindow;

public class App {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());

        //Maintain all the imported 3D model template files.
        ThreeDModels models = new ThreeDModels();
        ImportedData importedData = new ImportedData();

        MainWindow mw = new MainWindow();

        LogController logger = new LogController(mw);

        DataPanelController dataPanelController = new DataPanelController(mw, importedData, logger);
        mw.setDataPanelController(dataPanelController);

        ModelsPanelController modelsPanelController = new ModelsPanelController(mw, models, importedData, logger);
        modelsPanelController.setDataPanelController(dataPanelController);
        mw.setModelsPanelController(modelsPanelController);

        dataPanelController.setModelsPanelController(modelsPanelController);
    }
}
