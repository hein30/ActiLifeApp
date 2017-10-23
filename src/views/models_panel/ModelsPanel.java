package views.models_panel;

import controllers.ModelsPanelController;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.ThreeDimensionalModels;

public class ModelsPanel extends JPanel {

    private ThreeDModelsInfoPanel threeDModelsInfoPanel;
    private OptionsPanel optionsPanel;

    private ModelsPanelController controller;

    public ModelsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Models"));

        threeDModelsInfoPanel = new ThreeDModelsInfoPanel();
        add(threeDModelsInfoPanel, BorderLayout.NORTH);

        optionsPanel = new OptionsPanel();
        add(optionsPanel, BorderLayout.CENTER);
    }

    public void setController(ModelsPanelController controller) {
        this.controller = controller;
        optionsPanel.setController(controller);
    }

    public void updateModelInfo(ThreeDimensionalModels models) {
        threeDModelsInfoPanel.updateModelInfo(models);
    }

    public void updateModelList(ThreeDimensionalModels models) {
        optionsPanel.updateModelList(models);
    }
}
