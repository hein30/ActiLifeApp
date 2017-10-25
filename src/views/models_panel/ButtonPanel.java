package views.models_panel;

import controllers.FileChooser;
import controllers.ModelsPanelController;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

    private ModelsPanelController controller;

    private FileChooser fileChooser;

    private JButton importButton;
    private JButton deleteButton;
    private JButton generateButton;

    public ButtonPanel() {
        setBorder(BorderFactory.createTitledBorder("Operations"));

        fileChooser = new FileChooser(this);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        importButton = new JButton("Import");
        importButton.addActionListener(fileChooser);
        GridBagConstraints importButtonConstraints = new GridBagConstraints();
        importButtonConstraints.gridy = 0;
        importButtonConstraints.gridx = 0;
        importButtonConstraints.anchor = GridBagConstraints.WEST;
        importButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagLayout.setConstraints(importButton, importButtonConstraints);
        add(importButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener((ActionEvent e) -> {
            //todo add delete function here.
        });
        GridBagConstraints deleteButtonConstraints = new GridBagConstraints();
        deleteButtonConstraints.gridy = 0;
        deleteButtonConstraints.gridx = 1;
        gridBagLayout.setConstraints(deleteButton, deleteButtonConstraints);
        add(deleteButton);

        generateButton = new JButton("Generate");
        generateButton.addActionListener((ActionEvent e) -> {
            //todo add generate function here.
        });
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
    }
}
