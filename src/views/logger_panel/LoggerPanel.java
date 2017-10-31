package views.logger_panel;

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LoggerPanel extends JScrollPane {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private JTextArea textArea;

    public LoggerPanel() {
        setBorder(BorderFactory.createTitledBorder("Logs"));

        textArea = new JTextArea();
        textArea.setEditable(false);
        addLog("Welcome to Automated 3D model generator.");

        this.setPreferredSize(new Dimension(100, 150));
        this.setViewportView(textArea);
    }

    public void addLog(String string) {
        textArea.append(DATE_TIME_FORMATTER.format(LocalDateTime.now()) + " [INFO]: " + string + "\n");
    }

    public void addErrorLog(String string) {
        textArea.append(DATE_TIME_FORMATTER.format(LocalDateTime.now()) + " [ERROR]: " + string + "\n");
    }
}
