package views;

import controllers.DataPanelController;
import controllers.ModelsPanelController;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import views.data_panel.DataPanel;
import views.logger_panel.LoggerPanel;
import views.models_panel.ModelsPanel;

public class MainWindow extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -2952363495030327863L;
    private ModelsPanel modelsPanel;
    private DataPanel dataPanel;
    private LoggerPanel loggerPanel;

    public MainWindow() {
        setTitle("Automation");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dataPanel = new DataPanel();
        getContentPane().add(dataPanel, BorderLayout.CENTER);

        modelsPanel = new ModelsPanel();
        getContentPane().add(modelsPanel, BorderLayout.EAST);

        loggerPanel = new LoggerPanel();
        getContentPane().add(loggerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public ModelsPanel getModelsPanel() {
        return modelsPanel;
    }

    public DataPanel getDataPanel() {
        return dataPanel;
    }

    public LoggerPanel getLoggerPanel() {
        return loggerPanel;
    }

    public void setModelsPanelController(ModelsPanelController modelsPanelController) {
        modelsPanel.setModelsPanelController(modelsPanelController);
        dataPanel.setModelsPanelController(modelsPanelController);
    }

    public void setDataPanelController(DataPanelController dataPanelController) {
        dataPanel.setDataPanelController(dataPanelController);
    }
}
