package cube;

/**
 * *****************************************************
 * file: Driver.java 
 * authors: Tomik Ajhajanian, Arineh Abrahamian, Erick Lopez, Jenna Barrett 
 * class: CS 445 Computer Graphics
 * 
 * assignment: Final project Check point 2 
 * date last modified: 11/18/2017
 *
 * purpose: river class handles creating the program
 * *****************************************************
 */
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Display;

public class Driver {

    private CameraController fp;
    private DisplayMode displayMode;

    public static void main(String[] args) {
        Driver basic = new Driver();
        basic.start();
    }
    
    //method: start()
    //purpose: create window and start game loop
    public void start() {
        try {
            createWindow();
            initGL();
            fp = new CameraController(0f, 0f, 0f);
            fp.gameLoop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method: initGL()
    //purpose: initialize GL properties
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float) displayMode.getWidth() / (float) displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);

    }

    //method: createWindow()
    //purpose: creates window
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();

        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }

        Display.setDisplayMode(displayMode);
        Display.setTitle("Check Point #2");
        Display.create();
    }
}
