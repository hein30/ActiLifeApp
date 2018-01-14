package views.stlViewer;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;
import static com.jogamp.opengl.GL2GL3.GL_FILL;
import static com.jogamp.opengl.GL2GL3.GL_PRIMITIVE_RESTART;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import hall.collin.christopher.stl4j.STLParser;
import hall.collin.christopher.stl4j.Triangle;
import hall.collin.christopher.stl4j.Vec3d;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

class Renderer implements GLEventListener, MouseMotionListener, MouseListener, MouseWheelListener {

    private Transform T = new Transform();

    // VAOs and VBOs parameters
    private int idPoint = 0, numVAOs = 1;
    private int idBuffer = 0, numVBOs = 1;
    private int idElement = 0, numEBOs = 1;
    private int[] VAOs = new int[numVAOs];
    private int[] VBOs = new int[numVBOs];
    private int[] EBOs = new int[numEBOs];

    // Model parameters
    private int numElements;
    private int vPosition;
    private int vNormal;

    // Transformation parameters
    private int ModelView;
    private int Projection;
    private int NormalTransform;
    /* task 1.a: transformation parameters */
    private float scale = .02f; // scaling parameter
    private float rx = -30, ry = 0; // rotation parameters
    private float tx = 0, ty = 0; // translation parameters

    private Point mousePt;

    private File file;

    public Renderer(File file) {
        this.file = file;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3(); // Get the GL pipeline object

        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        gl.glPointSize(5);
        gl.glLineWidth(5);

        T.initialize();

        // Key control interaction
        T.scale(scale, scale, scale);
        /* task 1.c: transformation code */
        T.translate(tx, ty, 0); // for translation
        T.rotateX(rx); // rotation along X-axis
        T.rotateY(ry); // rotation along Y-axis

        // Locate camera
        T.lookAt(0, 0, 0, 0, 0, -1, 0, 1, 0); // Default

        // Send model_view and normal transformation matrices to shader.
        // Here parameter 'true' for transpose means to convert the
        // row-major
        // matrix to column major one, which is required when vertices'
        // location vectors are pre-multiplied by the model_view matrix.
        // Note that the normal transformation matrix is the
        // inverse-transpose
        // matrix of the vertex transformation matrix
        gl.glUniformMatrix4fv(ModelView, 1, true, T.getTransformv(), 0);
        gl.glUniformMatrix4fv(NormalTransform, 1, true, T.getInvTransformTv(), 0);

        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); // default
        gl.glDrawElements(GL_TRIANGLES, numElements, GL_UNSIGNED_INT, 0); // for
        // solid
        // teapot
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3(); // Get the GL pipeline object

        gl.glEnable(GL_PRIMITIVE_RESTART);
        gl.glPrimitiveRestartIndex(0xFFFF);

        gl.glEnable(GL_CULL_FACE);

        ArrayList<Float> verticsList = new ArrayList<Float>();
        ArrayList<Float> normalList = new ArrayList<Float>();
        ArrayList<Integer> indexList = new ArrayList<Integer>();

        List<Triangle> mesh = new ArrayList<>();
        try {
            mesh = new STLParser().parseSTLFile(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = 0;
        for (Triangle triangle : mesh) {

            final Vec3d[] vertices = triangle.getVertices();
            final Vec3d normal = triangle.getNormal();

            normalList.add((float) normal.x);
            normalList.add((float) normal.y);
            normalList.add((float) normal.z);
            normalList.add((float) normal.x);
            normalList.add((float) normal.y);
            normalList.add((float) normal.z);
            normalList.add((float) normal.x);
            normalList.add((float) normal.y);
            normalList.add((float) normal.z);

            verticsList.add((float) vertices[0].x);
            indexList.add(index++);
            verticsList.add((float) vertices[0].y);
            indexList.add(index++);
            verticsList.add((float) vertices[0].z);
            indexList.add(index++);

            verticsList.add((float) vertices[1].x);
            indexList.add(index++);
            verticsList.add((float) vertices[1].y);
            indexList.add(index++);
            verticsList.add((float) vertices[1].z);
            indexList.add(index++);

            verticsList.add((float) vertices[2].x);
            indexList.add(index++);
            verticsList.add((float) vertices[2].y);
            indexList.add(index++);
            verticsList.add((float) vertices[2].z);
            indexList.add(index++);
        }

        float[] vertexArray = ArrayUtils.toPrimitive(verticsList.toArray(new Float[0]), 0.0f);
        float[] normalArray = ArrayUtils.toPrimitive(normalList.toArray(new Float[0]), 0.0f);
        int[] vertexIndexs = ArrayUtils.toPrimitive(indexList.toArray(new Integer[0]), 0);
        numElements = vertexIndexs.length;

        gl.glGenVertexArrays(numVAOs, VAOs, 0);
        gl.glBindVertexArray(VAOs[idPoint]);

        FloatBuffer vertices = FloatBuffer.wrap(vertexArray);
        FloatBuffer normals = FloatBuffer.wrap(normalArray);

        gl.glGenBuffers(numVBOs, VBOs, 0);
        gl.glBindBuffer(GL_ARRAY_BUFFER, VBOs[idBuffer]);

        // Create an empty buffer with the size we need
        // and a null pointer for the data values
        long vertexSize = vertexArray.length * (Float.SIZE / 8);
        long normalSize = normalArray.length * (Float.SIZE / 8);
        gl.glBufferData(GL_ARRAY_BUFFER, vertexSize + normalSize, null, GL_STATIC_DRAW); // pay
        // attention
        // to
        // *Float.SIZE/8

        // Load the real data separately. We put the colors right after the
        // vertex coordinates,
        // so, the offset for colors is the size of vertices in bytes
        gl.glBufferSubData(GL_ARRAY_BUFFER, 0, vertexSize, vertices);
        gl.glBufferSubData(GL_ARRAY_BUFFER, vertexSize, normalSize, normals);

        IntBuffer elements = IntBuffer.wrap(vertexIndexs);

        gl.glGenBuffers(numEBOs, EBOs, 0);
        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBOs[idElement]);

        long indexSize = vertexIndexs.length * (Integer.SIZE / 8);
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexSize, elements, GL_STATIC_DRAW); // pay
        // attention
        // to
        // *Float.SIZE/8
        String path = null;

        try {
            path = URLDecoder.decode(ClassLoader.getSystemResource("rendering").getPath(), System.getProperty("file.encoding"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ShaderProg shaderproc = new ShaderProg(gl, path + File.separator + "Gouraud.vert", path + File.separator + "Gouraud.frag");
        int program = shaderproc.getProgram();
        gl.glUseProgram(program);

        // Initialize the vertex position attribute in the vertex shader
        vPosition = gl.glGetAttribLocation(program, "vPosition");
        gl.glEnableVertexAttribArray(vPosition);
        gl.glVertexAttribPointer(vPosition, 3, GL_FLOAT, false, 0, 0L);

        // Initialize the vertex color attribute in the vertex shader.
        // The offset is the same as in the glBufferSubData, i.e.,
        // vertexSize
        // It is the starting point of the color data
        vNormal = gl.glGetAttribLocation(program, "vNormal");
        gl.glEnableVertexAttribArray(vNormal);
        gl.glVertexAttribPointer(vNormal, 3, GL_FLOAT, false, 0, vertexSize);

        // Get connected with the ModelView matrix in the vertex shader
        ModelView = gl.glGetUniformLocation(program, "ModelView");
        NormalTransform = gl.glGetUniformLocation(program, "NormalTransform");
        Projection = gl.glGetUniformLocation(program, "Projection");

        // Initialize shader lighting parameters
        float[] lightPosition = {10.0f, 10.0f, -10.0f, 0.0f};
        Vec4 lightAmbient = new Vec4(0.7f, 0.7f, 0.7f, 1.0f);
        Vec4 lightDiffuse = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
        Vec4 lightSpecular = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);

        // Brass material
        Vec4 materialAmbient = new Vec4(0.329412f, 0.223529f, 0.027451f, 1.0f);
        Vec4 materialDiffuse = new Vec4(0.780392f, 0.568627f, 0.113725f, 1.0f);
        Vec4 materialSpecular = new Vec4(0.992157f, 0.941176f, 0.807843f, 1.0f);
        float materialShininess = 27.8974f;

        Vec4 ambientProduct = lightAmbient.times(materialAmbient);
        float[] ambient = ambientProduct.getVector();
        Vec4 diffuseProduct = lightDiffuse.times(materialDiffuse);
        float[] diffuse = diffuseProduct.getVector();
        Vec4 specularProduct = lightSpecular.times(materialSpecular);
        float[] specular = specularProduct.getVector();

        gl.glUniform4fv(gl.glGetUniformLocation(program, "AmbientProduct"), 1, ambient, 0);
        gl.glUniform4fv(gl.glGetUniformLocation(program, "DiffuseProduct"), 1, diffuse, 0);
        gl.glUniform4fv(gl.glGetUniformLocation(program, "SpecularProduct"), 1, specular, 0);

        gl.glUniform4fv(gl.glGetUniformLocation(program, "LightPosition"), 1, lightPosition, 0);

        gl.glUniform1f(gl.glGetUniformLocation(program, "Shininess"), materialShininess);

        // This is necessary. Otherwise, the The color on back face may
        // display
        // gl.glDepthFunc(GL_LESS);
        gl.glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {

        GL3 gl = drawable.getGL().getGL3(); // Get the GL pipeline object
        // this

        gl.glViewport(x, y, w, h);

        T.initialize();

        // projection
        if (h < 1) {
            h = 1;
        }
        if (w < 1) {
            w = 1;
        }
        float a = (float) w / h; // aspect
        if (w < h) {
            T.ortho(-1, 1, -1 / a, 1 / a, -1, 1);
        } else {
            T.ortho(-1 * a, 1 * a, -1, 1, -1, 1);
        }

        // Convert right-hand to left-hand coordinate system
        T.reverseZ();
        gl.glUniformMatrix4fv(Projection, 1, true, T.getTransformv(), 0);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - mousePt.x;
        int dy = e.getY() - mousePt.y;
        mousePt = e.getPoint();

        rx += dy;
        ry += dx;

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePt = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();

        if (notches > 0) {
            scale *= 1.1;
        } else {
            scale *= 0.9;
        }
    }
}
