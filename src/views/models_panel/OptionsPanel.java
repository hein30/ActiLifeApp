package views.models_panel;

import controllers.ModelsPanelController;
import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class OptionsPanel extends JPanel {

    private ModelsPanelController controller;

    private List<JCheckBox> checkBoxList;
    private JScrollPane scrollPane;
    private JPanel checkboxPanel;
    private JPanel buttonsPanel;
    private JButton importButton;
    private JButton deleteButton;

    public OptionsPanel() {
        setBorder(BorderFactory.createTitledBorder("Options"));

        checkBoxList = new ArrayList<>();
        setLayout(new BorderLayout());

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder());

        buttonsPanel.setLayout(new GridLayout(1, 2));
        add(buttonsPanel, BorderLayout.SOUTH);

        importButton = createImportButton();
        deleteButton = createDeleteButton();

        buttonsPanel.add(importButton);
        buttonsPanel.add(deleteButton);
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
        JCheckBox checkBox = new JCheckBox(model.getFileName());
        checkBox.addItemListener((ItemEvent e) -> {
            int indexOfChangedItem = checkBoxList.indexOf(e.getItem());
            controller.updateSelection(indexOfChangedItem, e.getStateChange());
        });

        checkboxPanel.add(checkBox);
        checkBoxList.add(checkBox);
    }


}
