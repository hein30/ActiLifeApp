package views.data_panel;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import models.ImportedData;
import views.BaseInfoPanel;

public class DataInfoPanel extends BaseInfoPanel {

    private JLabel importedFiles;
    private JLabel selectedFiles;
    private JLabel numberOfPeople;

    public DataInfoPanel() {
        super("Data Information");

        /*
        Imported files label.
         */
        JLabel filesImportedText = new JLabel("Imported Files:", SwingConstants.RIGHT);
        filesImportedText.setBorder(createLeftBorder());
        getLayout().setConstraints(filesImportedText, buildGbConstraints(GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 0, 0, 0));
        add(filesImportedText);

        importedFiles = new JLabel("0", SwingConstants.LEFT);
        importedFiles.setBorder(createRightBorder());
        getLayout().setConstraints(importedFiles, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 1, 0));
        add(importedFiles);

        /*
         * Selected files label.
         */
        JLabel filesSelectedText = new JLabel("Selected Files:", SwingConstants.RIGHT);
        filesSelectedText.setBorder(createLeftBorder());
        getLayout().setConstraints(filesSelectedText, buildGbConstraints(GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 0, 2, 0));
        add(filesSelectedText);

        selectedFiles = new JLabel("0", SwingConstants.LEFT);
        selectedFiles.setBorder(createRightBorder());
        getLayout().setConstraints(selectedFiles, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 3, 0));
        add(selectedFiles);

        /*
          Number of people in file label.
         */
        JLabel numberOfPeopleText = new JLabel("No. of People:", SwingConstants.RIGHT);
        numberOfPeopleText.setBorder(createLeftBorder());
        getLayout().setConstraints(numberOfPeopleText, buildGbConstraints(GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, 0, 4, 0));
        add(numberOfPeopleText);

        numberOfPeople = new JLabel("0", SwingConstants.LEFT);
        numberOfPeople.setBorder(createRightBorder());
        getLayout().setConstraints(numberOfPeople, buildGbConstraints(GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 0, 5, 0));
        add(numberOfPeople);
    }

    public void updateView(ImportedData data) {
        importedFiles.setText(String.valueOf(data.getFileMap().size()));

        //todo update the selected file value.
        selectedFiles.setText("0");

        numberOfPeople.setText(String.valueOf(data.getNumPeople()));

        revalidate();
    }
}