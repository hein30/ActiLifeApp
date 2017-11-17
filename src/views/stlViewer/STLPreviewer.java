package views.stlViewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class STLPreviewer extends JPanel {

    public STLPreviewer() {
        setBorder(BorderFactory.createTitledBorder("Preview"));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
    }

    public abstract void loadFile(File file);
}
