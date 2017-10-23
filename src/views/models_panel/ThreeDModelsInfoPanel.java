package views.models_panel;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import models.ThreeDimensionalModels;
import views.BaseInfoPanel;

public class ThreeDModelsInfoPanel extends BaseInfoPanel {

    private JLabel total;
    private JLabel selected;

    public ThreeDModelsInfoPanel() {
        super("Info");

        JLabel totalText = new JLabel("Total:", SwingConstants.RIGHT);
        totalText.setBorder(createLeftBorder());
        getLayout().setConstraints(totalText, buildGbConstraints(GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 0, 0, 0));
        add(totalText);

        total = new JLabel("None", SwingConstants.LEFT);
        total.setBorder(createRightBorder());
        getLayout().setConstraints(total, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 1, 0));
        add(total);

        JLabel selectedText = new JLabel("Selected:", SwingConstants.RIGHT);
        selectedText.setBorder(createLeftBorder());
        getLayout().setConstraints(selectedText, buildGbConstraints(GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 0, 2, 0));
        add(selectedText);

        selected = new JLabel("0", SwingConstants.LEFT);
        selected.setBorder(createRightBorder());
        getLayout().setConstraints(selected, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 3, 0));
        add(selected);
    }


    /*
     * Update the total number of models and total number of selected models.
     *
     * @param models
     */
    public void updateModelInfo(ThreeDimensionalModels models) {
        total.setText(String.valueOf(models.getModels().size()));
        selected.setText(String.valueOf(models.getNumOfSelectedModels()));

        repaint();
        revalidate();
    }


}
