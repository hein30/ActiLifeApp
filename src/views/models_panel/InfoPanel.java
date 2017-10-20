package views.models_panel;

import controllers.models_panel.ModelsPanelController;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import models.ThreeDimensionalModels;

public class InfoPanel extends JPanel {

    private JLabel total;
    private JLabel selected;

    public InfoPanel() {
        setBorder(BorderFactory.createTitledBorder("Info"));
        GridBagLayout gbLayout = new GridBagLayout();
        setLayout(gbLayout);

        JLabel totalText = new JLabel("Total:", SwingConstants.RIGHT);
        totalText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        gbLayout.setConstraints(totalText, buildGbConstraints(GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 0, 0, 0));
        add(totalText);

        total = new JLabel("None", SwingConstants.LEFT);
        total.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        gbLayout.setConstraints(total, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 1, 0));
        add(total);

        JLabel selectedText = new JLabel("Selected:", SwingConstants.RIGHT);
        selectedText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        gbLayout.setConstraints(selectedText, buildGbConstraints(GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 0, 2, 0));
        add(selectedText);

        selected = new JLabel("4", SwingConstants.LEFT);
        selected.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        gbLayout.setConstraints(selected, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 3, 0));
        add(selected);
    }

    /**
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

    private GridBagConstraints buildGbConstraints(int anchor, int fill, int gridy, int gridx, int weightx) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.anchor = anchor;
        gbConstraints.fill = fill;
        gbConstraints.gridy = gridy;
        gbConstraints.gridx = gridx;
        gbConstraints.weightx = weightx;
        return gbConstraints;
    }
}
