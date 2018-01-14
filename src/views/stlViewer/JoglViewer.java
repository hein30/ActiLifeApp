package views.stlViewer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.io.File;

public class JoglViewer extends STLPreviewer {

    public JoglViewer() {
        super();
    }

    @Override
    public void loadFile(File file) {

        removeAll();

        GLProfile glp = GLProfile.get(GLProfile.GL3);
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        FPSAnimator animator = new FPSAnimator(60, true);
        Renderer renderer = new Renderer(file);

        add(canvas, java.awt.BorderLayout.CENTER); // Put the canvas in the
        // frame
        canvas.addGLEventListener(renderer); // Set the canvas to listen
        // GLEvents
        canvas.addMouseListener(renderer);
        canvas.addMouseMotionListener(renderer);
        canvas.addMouseWheelListener(renderer);

        animator.add(canvas);

        animator.start();

        revalidate();
        repaint();
    }
}
