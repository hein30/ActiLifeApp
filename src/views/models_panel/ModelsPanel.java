package views.models_panel;

import controllers.ModelsPanelController;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.ThreeDimensionalModels;

public class ModelsPanel extends JPanel {

    private ThreeDModelsInfoPanel threeDModelsInfoPanel;
    private OptionsPanel optionsPanel;
    private ButtonPanel buttonPanel;

    public ModelsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Models"));

        threeDModelsInfoPanel = new ThreeDModelsInfoPanel();
        add(threeDModelsInfoPanel, BorderLayout.NORTH);

        optionsPanel = new OptionsPanel();
        add(optionsPanel, BorderLayout.CENTER);

        buttonPanel = new ButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setController(ModelsPanelController controller) {
        optionsPanel.setController(controller);
        buttonPanel.setController(controller);
    }

    public void updateViews(ThreeDimensionalModels models) {
        threeDModelsInfoPanel.updateModelInfo(models);
        optionsPanel.updateModelList(models);
        buttonPanel.updateGenerateButton(models);
    }
}
