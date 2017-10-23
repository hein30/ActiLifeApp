package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class BaseInfoPanel extends JPanel {

    private GridBagLayout gbLayout;

    public BaseInfoPanel(String title) {
        setBorder(BorderFactory.createTitledBorder(title));

        gbLayout = new GridBagLayout();
        setLayout(gbLayout);
    }

    public GridBagLayout getLayout() {
        return gbLayout;
    }

    protected GridBagConstraints buildGbConstraints(int anchor, int fill, int gridy, int gridx, int weightx) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.anchor = anchor;
        gbConstraints.fill = fill;
        gbConstraints.gridy = gridy;
        gbConstraints.gridx = gridx;
        gbConstraints.weightx = weightx;
        return gbConstraints;
    }

    protected Border createRightBorder() {
        return BorderFactory.createEmptyBorder(0, 5, 0, 10);
    }

    protected Border createLeftBorder() {
        return BorderFactory.createEmptyBorder(0, 10, 0, 0);
    }
}
