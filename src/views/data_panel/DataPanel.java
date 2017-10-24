package views.data_panel;

import controllers.DataPanelController;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.ImportedData;
import views.data_panel.tabbedPane.TabbedPane;

public class DataPanel extends JPanel {

    private DataInfoPanel dataInfoPanel;
    private TabbedPane tabbedPane;

    private DataPanelController controller;

    public DataPanel() {
        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new BorderLayout());

        dataInfoPanel = new DataInfoPanel();
        add(dataInfoPanel, BorderLayout.NORTH);

        tabbedPane = new TabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    public void updateDataViews(ImportedData data) {
        dataInfoPanel.updateView(data);

        tabbedPane.updateView(data);
        tabbedPane.setSelectedIndex(1);
    }

    public void setController(DataPanelController controller) {
        this.controller = controller;

        tabbedPane.setController(controller);
    }
}
