package views;

import controllers.models_panel.ModelsPanelController;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import views.models_panel.ModelsPanel;

public class MainWindow extends JFrame {

    private ModelsPanel modelsPanel;

    private ModelsPanelController modelsPanelController;

    public MainWindow() {
        setTitle("Automation");
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tab1 = new JPanel();
        tab1.add(new JButton("Tab1"));
        tabbedPane.add("Tab1", tab1);
        JPanel tab2 = new JPanel();
        tab2.add(new JButton("Tab2"));
        tabbedPane.add("Tab2", tab2);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        modelsPanel = new ModelsPanel();
        getContentPane().add(modelsPanel, BorderLayout.EAST);

        JPanel logPanel = new JPanel();
        logPanel.setBorder(BorderFactory.createTitledBorder("Log"));
        JButton button1 = new JButton("LogPanel");
        logPanel.add(button1);
        getContentPane().add(logPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private GridBagConstraints buildGbConstraints(int anchor, int fill, int gridy, int gridx, int weightx) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.anchor = anchor;
        gbConstraints.fill = fill;
        gbConstraints.gridy = gridy;
        gbConstraints.gridx = gridx;
        gbConstraints.weightx = weightx;
        return gbConstraints;
    }

    public ModelsPanel getModelsPanel() {
        return modelsPanel;
    }

    public void setModelsPanelController(ModelsPanelController modelsPanelController) {
        this.modelsPanelController = modelsPanelController;

        modelsPanel.setController(this.modelsPanelController);
    }
}
