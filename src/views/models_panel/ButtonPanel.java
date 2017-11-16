package views.models_panel;

import controllers.FileChooser;
import controllers.ModelsPanelController;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import models.ImportedData;
import models.ThreeDModels;

public class ButtonPanel extends JPanel {

    /**
     * automatically generated by eclipse
     */
    private static final long serialVersionUID = -2839945845091543531L;

    private ModelsPanelController controller;

    private FileChooser fileChooser;

    private JButton importButton;
    private JButton deleteButton;
    private JButton generateButton;

    public ButtonPanel() {
        setBorder(BorderFactory.createTitledBorder("3D Model Operations"));

        fileChooser = new FileChooser(this);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        importButton = new JButton("Import");
        importButton.setToolTipText("Add 3D model templates.");
        importButton.addActionListener(fileChooser);
        GridBagConstraints importButtonConstraints = new GridBagConstraints();
        importButtonConstraints.gridy = 0;
        importButtonConstraints.gridx = 0;
        importButtonConstraints.anchor = GridBagConstraints.WEST;
        importButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagLayout.setConstraints(importButton, importButtonConstraints);
        add(importButton);

        deleteButton = new JButton("Delete");
        disableDeleteButton();
        deleteButton.addActionListener((ActionEvent e) -> {
            controller.deleteFiles(null);
        });
        GridBagConstraints deleteButtonConstraints = new GridBagConstraints();
        deleteButtonConstraints.gridy = 0;
        deleteButtonConstraints.gridx = 1;
        gridBagLayout.setConstraints(deleteButton, deleteButtonConstraints);
        add(deleteButton);

        generateButton = new JButton("Generate");
        disableGenerateButton();
        generateButton.addActionListener((ActionEvent e) -> controller.generateModels());
        GridBagConstraints generateButtonConstraints = new GridBagConstraints();
        generateButtonConstraints.gridy = 1;
        generateButtonConstraints.gridx = 0;
        generateButtonConstraints.gridwidth = 2;
        generateButtonConstraints.anchor = GridBagConstraints.WEST;
        generateButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagLayout.setConstraints(generateButton, generateButtonConstraints);
        add(generateButton);
    }

    public void setController(ModelsPanelController controller) {
        this.controller = controller;
        this.fileChooser.addController(this.controller);
    }

    public void updateButtonStates(ThreeDModels models, ImportedData importedData) {
        if (models.getSelectedModels().isEmpty()) {
            disableGenerateButton();
            disableDeleteButton();
        } else {
            enableGenerateButton(importedData);
            enableDeleteButton(models);
        }
    }

    private void disableDeleteButton() {
        deleteButton.setEnabled(false);
        deleteButton.setToolTipText("Delete 3D model templates. Select one or more imported models above to delete.");
    }

    /**
     * Enable the delete button if necessary.
     *
     * @param models - if at least one deletable model is chosen, enable it.
     */
    private void enableDeleteButton(ThreeDModels models) {

        if (models.getSelectedModels().stream().filter(model -> model.isDeletable()).count() > 0) {
            deleteButton.setEnabled(true);
            deleteButton.setToolTipText("Delete selected 3D model templates.");
        }
    }

    private void disableGenerateButton() {
        generateButton.setEnabled(false);
        generateButton.setToolTipText("Select one or more models to generate.");
    }

    /**
     * Enable the generate button if necessary.
     *
     * @param importedData - imported data must not be empty.
     */
    private void enableGenerateButton(ImportedData importedData) {
        if (!importedData.getFileMap().isEmpty()) {
            generateButton.setEnabled(true);
            generateButton.setToolTipText("Generate selected 3D models for the input.");
        }
    }

    public void toggleButtons(boolean isEnabled) {
        importButton.setEnabled(isEnabled);
        deleteButton.setEnabled(isEnabled);
        generateButton.setEnabled(isEnabled);
    }
}
