package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import controllers.FileChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.ImportedData;


public class DataImportPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private DataPanelController controller;
    private FileChooser fileChooser;

    private DefaultListModel<String> defaultListModel;
    private JList<String> listOfFiles;
    private JScrollPane scrollPane;

    private JButton importButton;
    private JButton deleteButton;

    public DataImportPanel() {
        setBorder(BorderFactory.createTitledBorder("Imported Files"));
        setLayout(new BorderLayout());

        fileChooser = new FileChooser(this);

        defaultListModel = new DefaultListModel<>();
        listOfFiles = new JList<>(defaultListModel);
        scrollPane = new JScrollPane(listOfFiles);

        add(scrollPane, BorderLayout.CENTER);
        addButtonPanel();
    }

    public void updateImportFileList(ImportedData data) {
        defaultListModel.clear();
        data.getFileMap().forEach((k, v) -> defaultListModel.addElement(k));
    }


    public void setController(DataPanelController controller) {
        this.controller = controller;

        this.fileChooser.addController(controller);
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        importButton = new JButton("Add Data Files");
        importButton.addActionListener(fileChooser);
        buttonPanel.add(importButton);

        deleteButton = new JButton("Remove Data Files");
        deleteButton.addActionListener((ActionEvent e) -> controller.deleteFiles(listOfFiles.getSelectedValuesList()));
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void toggleButtons(boolean isEnabled) {
        importButton.setEnabled(isEnabled);
        deleteButton.setEnabled(isEnabled);
    }
}
