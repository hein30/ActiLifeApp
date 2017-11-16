package views.data_panel.tabbedPane;

import controllers.FileChooser;
import controllers.ModelsPanelController;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.FileModel;
import models.ThreeDModels;

public class ModelImportPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private ModelsPanelController modelsPanelController;
    private FileChooser fileChooser;

    private DefaultListModel<FileModel> defaultListModel;
    private JList<FileModel> listOfModels;
    private JScrollPane scrollPane;

    private JButton importButton;
    private JButton deleteButton;

    public ModelImportPanel() {
        setBorder(BorderFactory.createTitledBorder("Import Models"));
        setLayout(new BorderLayout());

        fileChooser = new FileChooser(this);

        addInformationPanel();

        addScrollPane();

        addButtonPanel();
    }

    public void updateModelList(ThreeDModels models) {
        defaultListModel.clear();
        models.getModels().forEach(model -> defaultListModel.addElement(model));
    }

    private void addInformationPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        labelPanel.add(new JLabel("Add or delete 3D model templates here. Built-in models cannot be removed. (Ctrl+Left click) for multiple selection."));
        add(labelPanel, BorderLayout.NORTH);
    }

    /**
     * Initialise default list model and add scrollpane to the center.
     */
    private void addScrollPane() {
        defaultListModel = new DefaultListModel<>();
        listOfModels = new JList<>(defaultListModel);
        scrollPane = new JScrollPane(listOfModels);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        importButton = new JButton("Add 3D Models");
        importButton.addActionListener(fileChooser);
        buttonPanel.add(importButton);

        deleteButton = new JButton("Remove 3D Models");
        deleteButton.addActionListener((ActionEvent e) ->
                modelsPanelController.deleteFiles(
                        listOfModels.getSelectedValuesList()
                                .stream()
                                .map(FileModel::getFileName)
                                .collect(Collectors.toList())));

        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setController(ModelsPanelController controller) {
        this.modelsPanelController = controller;
        this.fileChooser.addController(controller);
    }


    public void toggleButtons(boolean isEnabled) {
        importButton.setEnabled(isEnabled);
        deleteButton.setEnabled(isEnabled);
    }
}

