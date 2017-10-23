package views.data_panel.tabbedPane;

import controllers.DataPanelController;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.ImportedData;
import models.Subjects;

public class DataPanelCopy extends JScrollPane {

    private DataPanelController controller;

    private JPanel mainPanel;

    public DataPanelCopy() {
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
        subjects.getSubjectList().forEach(subject -> defaultListModel.addElement(subject.getSubjectId()));

        JList jList = new JList(defaultListModel);
        jList.setFixedCellWidth(100);
        jList.setLayoutOrientation(JList.VERTICAL_WRAP);

        JScrollPane paneForOneFile = new JScrollPane(jList);

        paneForOneFile.setMinimumSize(new Dimension(100, 50));
        paneForOneFile.setBorder(BorderFactory.createTitledBorder(subjects.getFileName()));

        mainPanel.add(paneForOneFile);
    }

    /**
     private void addOneFile(String key, Subjects subjects) {

     JPanel panelForOneFile = new JPanel(new GridLayout(0, 2));

     panelForOneFile.setBorder(BorderFactory.createTitledBorder(key));

     subjects.getSubjectList().stream().forEach(subject -> panelForOneFile.add(buildTableForOneSubject(subject)));

     scrollablePanel.add(panelForOneFile);
     }
     */

    /**
     private JScrollPane buildTableForOneSubject(Subject subject) {
     DefaultTableModel model = new DefaultTableModel() {
    @Override public boolean isCellEditable(int row, int column) {
    return false;
    }
    };

     model.addColumn(subject.getSubjectId());
     subject.getOrderOfDays().forEach(day -> model.addColumn(day.toString()));

     model.addRow(getArray("Sedentary", subject.getSedentary()));
     model.addRow(getArray("Light", subject.getLight()));
     model.addRow(getArray("Moderate", subject.getModerate()));
     model.addRow(getArray("Vigorous", subject.getVigorous()));

     JTable jTable = new JTable(model);
     jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

     JScrollPane scrollPane = new JScrollPane(jTable);
     scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
     return scrollPane;
     }
     */

    /**
     * Builds a row of the table.
     *
     * @param rowLabel
     * @param values
     * @return
     */
    private String[] getArray(String rowLabel, List<Double> values) {
        String[] array = new String[values.size() + 1];
        array[0] = rowLabel;

        for (int i = 1; i < array.length; i++) {
            array[i] = String.format("%.2f", values.get(i - 1));
        }
        return array;
    }
}