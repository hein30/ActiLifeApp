package views.stlViewer;

import java.io.File;
import javax.swing.JLabel;

public class MacPreviewer extends STLPreviewer {

    public MacPreviewer() {
        super();

        add(new JLabel("Preview not supported on Mac yet"));
    }

    @Override
    public void loadFile(File file) {
        //do nothing.
    }
}
