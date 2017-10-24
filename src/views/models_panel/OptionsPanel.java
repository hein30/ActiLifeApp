package views.models_panel;

import controllers.ModelsPanelController;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.FileModel;
import models.ThreeDimensionalModels;
import org.apache.commons.io.FilenameUtils;

public class OptionsPanel extends JPanel {

    private ModelsPanelController controller;

    private List<JCheckBox> checkBoxList;
    private JScrollPane scrollPane;
    private JPanel checkboxPanel;
    private JPanel buttonsPanel;
    private JButton importButton;
    private JButton deleteButton;
    private JButton generateButton;

    public OptionsPanel() {
        setBorder(BorderFactory.createTitledBorder("Options"));

        checkBoxList = new ArrayList<>();
        setLayout(new BorderLayout());

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        GridBagLayout gridBagLayout = new GridBagLayout();
        buttonsPanel = new JPanel(gridBagLayout);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder());
        add(buttonsPanel, BorderLayout.SOUTH);

        importButton = createImportButton();
        gridBagLayout.setConstraints(importButton, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 0, 0));
        buttonsPanel.add(importButton);

        deleteButton = createDeleteButton();
        gridBagLayout.setConstraints(deleteButton, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 1, 0));
        buttonsPanel.add(deleteButton);

        generateButton = new JButton("Generate");
        GridBagConstraints constraints = buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1, 0, 100);
        constraints.gridwidth = 2;
        gridBagLayout.setConstraints(generateButton, constraints);
        buttonsPanel.add(generateButton);
    }

    private JButton createDeleteButton() {
        return new JButton("Delete");
    }

    private JButton createImportButton() {
        return new JButton("Import");
    }

    public void setController(ModelsPanelController controller) {
        this.controller = controller;
    }

    public void updateModelList(ThreeDimensionalModels models) {
        models.getModels().forEach(model -> addCheckBox(model));

        repaint();
        revalidate();
    }

    private void addCheckBox(FileModel model) {
        JCheckBox checkBox = new JCheckBox(FilenameUtils.removeExtension(model.getFileName()));
        checkBox.addItemListener((ItemEvent e) -> {
            int indexOfChangedItem = checkBoxList.indexOf(e.getItem());
            controller.updateSelection(indexOfChangedItem, e.getStateChange());
        });

        checkboxPanel.add(checkBox);
        checkBoxList.add(checkBox);
    }

    protected GridBagConstraints buildGbConstraints(int anchor, int fill, int gridy, int gridx, int weightx) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.anchor = anchor;
        gbConstraints.fill = fill;
        gbConstraints.gridy = gridy;
        gbConstraints.gridx = gridx;
        gbConstraints.weightx = weightx;
        return gbConstraints;
    }

}
