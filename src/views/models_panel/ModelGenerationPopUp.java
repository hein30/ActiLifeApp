package views.models_panel;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ModelGenerationPopUp extends JOptionPane {

    JTextField firstName;
    JTextField lastName;
    JPasswordField passwordField;

    List<JComponent> componentList;
    String title;

    Component parent;

    public ModelGenerationPopUp(Component parent) {
        title = "Generating files";
        this.parent = parent;
        componentList = new ArrayList<>();
        firstName = new JTextField();
        lastName = new JTextField();
        passwordField = new JPasswordField();

        componentList.add(new JLabel("First"));
        componentList.add(firstName);
        componentList.add(new JLabel("Last"));
        componentList.add(lastName);
        componentList.add(new JLabel("Password"));
        componentList.add(passwordField);
        componentList.add(new JButton("LOL"));

        componentList.clear();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("LOL"));
        panel.add(new JButton("LOL"));
        componentList.add(panel);
    }

    public void showDialog() {
        showConfirmDialog(parent, componentList.toArray(), title, JOptionPane.OK_CANCEL_OPTION);
    }

}
