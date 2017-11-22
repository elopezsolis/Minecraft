package cube;
/**
 * *****************************************************
 * file: CameraController.java 
 * authors: Tomik Ajhajanian, Arineh Abrahamian, Erick Lopez, Jenna Barrett 
 * class: CS 445 Computer Graphics
 *
 * assignment: Final project Check point 2
 * date last modified: 11/18/2017
 *
 * purpose: describes the functions necessary for the user to control
*  the camera and render what is loaded into view.  
*  Controls are:
*  W,S,A,D: Forward, Backward, Left, Right
*  Space Bar: Move Up
*  Left Shift: Move Down
*  Mouse: Looks around
 * *****************************************************
 */

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.Sys;

public class CameraController {
    private Vector3f position = null;
    private Vector3f lPosition = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    private Chunk chunk = new Chunk(0,0,0);
 
    public CameraController(float x, float y, float z)
    {
        //instantiate position Vector3f to the x y z params.
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x,y,z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
    }    
    
    public void gameLoop(){
        CameraController camera = new CameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        
        //hide the mouse
        Mouse.setGrabbed(true);
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            time = Sys.getTime();
            lastTime = time;
            dx = Mouse.getDX();
            dy = Mouse.getDY();
            
            camera.yaw(dx * mouseSensitivity);
            camera.pitch(dy * mouseSensitivity);
            
            
            if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
            {
                camera.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
            {
                camera.walkBackwards(movementSpeed);
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left 
            {
                camera.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right 
            {
                camera.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))//move up 
            {
                camera.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
                camera.moveDown(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
                camera.moveDown(movementSpeed);
            }
           
            //set the modelview matrix back to the identity
            glLoadIdentity();
            //look through the camera before you draw anything
            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //scene drawn here
            chunk.render();
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy(); 
    }
    
    //method: walkForward
    //purpose: moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    //method: walkBakcwards(float)
    //purpose: Moves the camera backward
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }
    
    //method: strafeLeft(float)
    //purpose: strafes the camera left relative to its current rotation 
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    //method: strafeLeft(float)
    //purpose: strafes the camera right relative to its current rotation 
    public void strafeRight(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    //method: moveDown(float)
    //purpose: moves the camera up
    public void moveUp(float distance)
    {
        position.y -= distance;
    }
    
    //method: moveDown(float distance)
    //purpose: moves the camera down
    public void moveDown(float distance)
    {
        position.y += distance;
    }
    
        
    //method: yaw(float)
    //purpose: Increment the camera yaw rotation
    public void yaw(float amount)
    {
        yaw += amount;
    }
    
    //method:pitch(float)
    //purpose: increment the camera pitch rotation
    public void pitch(float amount)
    {
        pitch -= amount;
    }
    
    //method: lookThrough()
    //purpose: Translates and rotate the matrix so that it looks through the camera
    public void lookThrough()
    {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }
}
