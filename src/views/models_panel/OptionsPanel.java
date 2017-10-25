package views.models_panel;

import controllers.ModelsPanelController;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.FileModel;
import models.ThreeDimensionalModels;
import org.apache.commons.io.FilenameUtils;

public class OptionsPanel extends JScrollPane {

    private ModelsPanelController controller;

    private List<JCheckBox> checkBoxList;
    private JPanel checkboxPanel;

    public OptionsPanel() {
        setBorder(BorderFactory.createTitledBorder("3D Models"));

        checkBoxList = new ArrayList<>();

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        setViewportView(checkboxPanel);
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
}
