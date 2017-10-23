package views;

import controllers.DataPanelController;
import controllers.ModelsPanelController;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import views.data_panel.DataPanel;
import views.models_panel.ModelsPanel;

public class MainWindow extends JFrame {

    private ModelsPanel modelsPanel;
    private DataPanel dataPanel;

    private ModelsPanelController modelsPanelController;
    private DataPanelController dataPanelController;

    public MainWindow() {
        setTitle("Automation");
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dataPanel = new DataPanel();
        getContentPane().add(dataPanel, BorderLayout.CENTER);

        modelsPanel = new ModelsPanel();
        getContentPane().add(modelsPanel, BorderLayout.EAST);

        JPanel logPanel = new JPanel();
        logPanel.setBorder(BorderFactory.createTitledBorder("Log"));
        JButton button1 = new JButton("LogPanel");
        logPanel.add(button1);
        getContentPane().add(logPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public ModelsPanel getModelsPanel() {
        return modelsPanel;
    }

    public DataPanel getDataPanel() {
        return dataPanel;
    }

    public void setModelsPanelController(ModelsPanelController modelsPanelController) {
        this.modelsPanelController = modelsPanelController;

        modelsPanel.setController(this.modelsPanelController);
    }

    public void setDataPanelController(DataPanelController dataPanelController) {
        this.dataPanelController = dataPanelController;

        dataPanel.setController(dataPanelController);
    }
}
