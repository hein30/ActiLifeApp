package views.models_panel;

import controllers.models_panel.ModelsPanelController;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.ThreeDimensionalModels;

public class OptionsPanel extends JPanel {

    private ModelsPanelController controller;

    private List<JCheckBox> checkBoxList;
    private JScrollPane scrollPane;
    private JPanel checkboxPanel;
    private JPanel buttonsPanel;

    public OptionsPanel() {
        checkBoxList = new ArrayList<>();
        setLayout(new BorderLayout());

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Options"));
        add(scrollPane, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder());
        add(buttonsPanel, BorderLayout.SOUTH);
        buttonsPanel.add(new JButton("haha"));
    }

    public void setController(ModelsPanelController controller) {
        this.controller = controller;
    }

    public void updateModelList(ThreeDimensionalModels models) {
        models.getModels().forEach(model -> addCheckBox(model));

        repaint();
        revalidate();
    }

    private void addCheckBox(ThreeDimensionalModels.ThreeDimensionalModel model) {
        JCheckBox checkBox = new JCheckBox(model.getName());
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int indexOfChangedItem = checkBoxList.indexOf(e.getItem());

                controller.updateSelection(indexOfChangedItem, e.getStateChange());
            }
        });

        checkboxPanel.add(checkBox);
        checkBoxList.add(checkBox);
    }


}
