package views.models_panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class FileGenerationProgressBar extends JDialog {

    private JProgressBar jProgressBar;

    public FileGenerationProgressBar(int min, int max) {
        JLabel label = new JLabel("Progress: ");

        jProgressBar = new JProgressBar(min, max);
        jProgressBar.setPreferredSize(new Dimension(300, 20));
        jProgressBar.setString("Generating files (0 of " + jProgressBar.getMaximum() + "...");
        jProgressBar.setValue(0);
        jProgressBar.setStringPainted(true);

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(jProgressBar);

        setTitle("Generating files");
        getContentPane().add(panel, BorderLayout.CENTER);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2, (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2);
        pack();
        setVisible(true);
    }

    public void incrementProgress(int i) {
        jProgressBar.setValue(jProgressBar.getValue() + i);
        jProgressBar.setString("Generating files (" + jProgressBar.getValue() + " of " + jProgressBar.getMaximum() + ")...");
    }
}
