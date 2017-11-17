package views.stlViewer;

import com.sun.j3d.utils.universe.SimpleUniverse;
import hall.collin.christopher.stl4j.STLParser;
import hall.collin.christopher.stl4j.Triangle;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.stlviewer.PCanvas3D;
import org.stlviewer.PModel;

public class Java3DPreviewer extends STLPreviewer {

    private PCanvas3D canvas;
    private PModel model;
    private SimpleUniverse universe;

    public Java3DPreviewer() {
        super();

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new PCanvas3D(config);
        add(canvas, BorderLayout.CENTER);

        universe = new SimpleUniverse(canvas);
        canvas.initcanvas(universe);
    }

    @Override
    public void loadFile(File file) {

        // read file to array of triangles
        try {

            List<Triangle> mesh = new STLParser().parseSTLFile(file.toPath());

            if (mesh == null || mesh.isEmpty()) {
                return;
            } else if (model != null)
                model.cleanup();
            model = new PModel();
            //   model.setBnormstrip(mnstrp.isSelected());
            model.addtriangles(mesh);

            canvas.rendermodel(model, universe);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Logger.getLogger(Java3DPreviewer.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }
}
