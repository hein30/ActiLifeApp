package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.ImportedData;
import models.Subjects;
import org.apache.commons.io.FilenameUtils;

public class DataPanel extends JScrollPane {

    private DataPanelController controller;

    private JPanel mainPanel;

    public DataPanel() {
        setBorder(BorderFactory.createTitledBorder("Subjects"));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        setViewportView(mainPanel);
    }

    public void updateView(ImportedData data) {
        mainPanel.removeAll();

        data.getFileMap().values().stream().forEach(subjects -> addList(subjects));

        validate();
        repaint();
    }

    private void addList(Subjects subjects) {
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        subjects.getSubjectList().forEach(subject -> defaultListModel.addElement(FilenameUtils.removeExtension(subject.getFileName())));

        JList jList = new JList(defaultListModel);
        jList.setFixedCellWidth(90);
        jList.setLayoutOrientation(JList.VERTICAL_WRAP);

        JScrollPane paneForOneFile = new JScrollPane(jList);

        paneForOneFile.setMinimumSize(new Dimension(100, 50));
        paneForOneFile.setBorder(BorderFactory.createTitledBorder(subjects.getFileName()));

        mainPanel.add(paneForOneFile);
    }
}