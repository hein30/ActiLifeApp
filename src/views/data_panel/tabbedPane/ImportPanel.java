package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import controllers.FileChooser;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.ImportedData;


public class ImportPanel extends JPanel {

    private DataPanelController controller;
    private FileChooser fileChooser;

    private DefaultListModel<String> defaultListModel;
    private JList listOfFiles;
    private JScrollPane scrollPane;

    public ImportPanel() {
        setBorder(BorderFactory.createTitledBorder("Imported Files"));
        setLayout(new BorderLayout());

        fileChooser = new FileChooser(this);

        defaultListModel = new DefaultListModel();
        listOfFiles = new JList(defaultListModel);
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

        JButton importButton = new JButton("Add Files");
        importButton.addActionListener(fileChooser);
        buttonPanel.add(importButton);

        JButton deleteButton = new JButton("Remove");
        deleteButton.addActionListener((ActionEvent e)->{
            listOfFiles.getSelectedValuesList().forEach(x -> System.out.println("Selected " + x));
            controller.deleteFile(listOfFiles.getSelectedValuesList());

        });
        buttonPanel.add(deleteButton);
        //todo delete function

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
