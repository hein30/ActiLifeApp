package views.models_panel;

import controllers.models_panel.ModelsPanelController;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.ThreeDimensionalModels;

public class ModelsPanel extends JPanel {

    private InfoPanel infoPanel;
    private OptionsPanel optionsPanel;

    private ModelsPanelController controller;

    public ModelsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Models"));

        infoPanel = new InfoPanel();
        add(infoPanel, BorderLayout.NORTH);

        optionsPanel = new OptionsPanel();
        add(optionsPanel, BorderLayout.CENTER);
    }

    public void updateModelInfo(ThreeDimensionalModels models) {
        infoPanel.updateModelInfo(models);
    }

    public void updateModelList(ThreeDimensionalModels models) {
        optionsPanel.updateModelList(models);
    }

    public void setController(ModelsPanelController controller) {
        this.controller = controller;

        optionsPanel.setController(controller);
    }
}
