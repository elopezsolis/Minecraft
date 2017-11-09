
package cube;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;



public class CUBE {
    private FPCameraController fp = new FPCameraController(0f,0f,0f);
    private DisplayMode displayMode;
    
    public static void main(String[] args) {
        CUBE basic = new CUBE();
        basic.start();
    }
    
    public void start() {
        try {
            createWindow();
            initGL();
            fp.gameLoop();//render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480 && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }

        Display.setDisplayMode(displayMode);
        Display.setTitle("Check Point # 1!");
        Display.create();
    }
    
    
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)
        displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
}

/*public class CUBE {
    public static void main(String[]args)
    {
        CUBE c = new CUBE();
        c.start();
    }
    
    public void start() 
    {
        try {
            createWindow();
            initGL();
            render();
        } 
    
        
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private void createWindow() throws Exception
    {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Hey Mom! I am using OpenGL!!!");
        Display.create();
    }
    
    private void initGL() 
    {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void render() 
    {
        while (!Display.isCloseRequested()) 
        {
            try
            {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                //drawing();
                Display.update();
                Display.sync(60);
            }
            catch(Exception e)
            {
            }
        }
        Display.destroy();
    }
    
    public void drawing()
    {
        /*glColor3f(1.0f, 0.0f, 0.0f);
        glBegin(GL_QUADS);

        glEnd();*/


		/*glColor3f(1.0f, 0.0f, 0.0f);
		glBegin(GL_LINES);
		glVertex2i(10, 10);
		glVertex2i(50, 60);
		glEnd();
		//Display.update();
		Display.sync(60);*/
        
    /*}

}*/
