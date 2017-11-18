/*
 * Controls the camera and draws the cube.
 * Last modified: 11/11/17
 */
package cube;
import java.io.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class FPCameraController {
    //3d vector to store the camera's position in
    private Vector3f position = null;
    private Vector3f lPosition = null;
    //the rotation around the Y axis of the camera
    private float yaw = 0.0f;
    //the rotation around the X axis of the camera
    private float pitch = 0.0f;
    private Vector3Float me;
    
    private int VBOTextureHandle;
    private Texture texture;
    private Chunk chunk = new Chunk(0,0,0);
 
    
    public FPCameraController(float x, float y, float z)
    {
        //instantiate position Vector3f to the x y z params.
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x,y,z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
    }
    
    public void yaw(float amount)
    {
        //increment the yaw by the amount param
        yaw += amount;
    }
    //increment the camera's current yaw rotation
    public void pitch(float amount)
    {
        //increment the pitch by the amount param
        pitch -= amount;
    }
    
    //moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }
    
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    public void strafeRight(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    public void moveUp(float distance)
    {
        position.y -= distance;
    }
    //moves the camera down
    public void moveDown(float distance)
    {
        position.y += distance;
    }
    
    //translates and rotate the matrix so that it looks through the camera
    //this does basically what gluLookAt() does
    public void lookThrough()
    {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }
    
    
    public void gameLoop(){
        FPCameraController camera = new FPCameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        //hide the mouse
        Mouse.setGrabbed(true);
       
        
        // keep looping till the display window is closed or the ESC key is down
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            time = Sys.getTime();
            lastTime = time;
            //distance in mouse movement
            //from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement
            //from the last getDY() call.
            dy = Mouse.getDY();
            //when passing in the distance to move
            //we times the movementSpeed with dt this is a time scale
            //so if its a slow frame u move more then a fast frame
            //so on a slow computer you move just as fast as on a fast computer
            
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
            //you would draw your scene here.
            chunk.render();
            //render();
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy(); 
    }
    
    public void loadTexture() {
        Texture currentT = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("grass.png")));
            // Replace PNG with your file extension
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
        texture = currentT;
    }
        
    
    
    private void render() {
        try{
                     
            
//            glBegin(GL_QUADS);
//            //Top
//            glColor3f(0.0f,0.0f,2.0f);
//            glVertex3f( 2.0f, 2.0f,-2.0f);
//            glVertex3f(-2.0f, 2.0f,-2.0f);
//            glVertex3f(-2.0f, 2.0f, 2.0f);
//            glVertex3f( 2.0f, 2.0f, 2.0f);
            
//            //Bottom
//            glColor3f(0.0f,2.0f,0.0f);
//            glVertex3f( 2.0f,-2.0f, 2.0f);
//            glVertex3f(-2.0f,-2.0f, 2.0f);
//            glVertex3f(-2.0f,-2.0f,-2.0f);
//            glVertex3f( 2.0f,-2.0f,-2.0f);
//            
//            //Front
//            glColor3f(2.0f,0.0f,0.0f);
//            glVertex3f( 2.0f, 2.0f, 2.0f);
//            glVertex3f(-2.0f, 2.0f, 2.0f);
//            glVertex3f(-2.0f,-2.0f, 2.0f);
//            glVertex3f( 2.0f,-2.0f, 2.0f);
//            
//            //Back
//            glColor3f(0.0f,2.0f,2.0f);
//            glVertex3f( 2.0f,-2.0f,-2.0f);
//            glVertex3f(-2.0f,-2.0f,-2.0f);
//            glVertex3f(-2.0f, 2.0f,-2.0f);
//            glVertex3f( 2.0f, 2.0f,-2.0f);
//            //Left
//            glColor3f(2.0f,0.0f,2.0f);
//            glVertex3f(-2.0f, 2.0f,2.0f);
//            glVertex3f(-2.0f, 2.0f,-2.0f);
//            glVertex3f(-2.0f,-2.0f,-2.0f);
//            glVertex3f(-2.0f,-2.0f, 2.0f);
//            //Right
//            glColor3f(2.0f,2.0f,0.0f);
//            glVertex3f( 2.0f, 2.0f,-2.0f);
//            glVertex3f( 2.0f, 2.0f, 2.0f);
//            glVertex3f( 2.0f,-2.0f, 2.0f);
//            glVertex3f( 2.0f,-2.0f,-2.0f);
//            glEnd();
//            
//            glBegin(GL_LINE_LOOP);
//            //Top
//            glColor3f(0.0f,0.0f,0.0f);
//            glVertex3f( 2.0f, 2.0f,-2.0f);
//            glVertex3f(-2.0f, 2.0f,-2.0f);
//            glVertex3f(-2.0f, 2.0f, 2.0f);
//            glVertex3f( 2.0f, 2.0f, 2.0f);
//            glEnd();
//            glBegin(GL_LINE_LOOP);
//            //Bottom
//            glVertex3f( 2.0f,-2.0f, 2.0f);
//            glVertex3f(-2.0f,-2.0f, 2.0f);
//            glVertex3f(-2.0f,-2.0f,-2.0f);
//            glVertex3f( 2.0f,-2.0f,-2.0f);
//            glEnd();
//            glBegin(GL_LINE_LOOP);
//            //Front
//            glVertex3f( 2.0f, 2.0f, 2.0f);
//            glVertex3f(-2.0f, 2.0f, 2.0f);
//            glVertex3f(-2.0f,-2.0f, 2.0f);
//            glVertex3f( 2.0f,-2.0f, 2.0f);
//            glEnd();
        }catch(Exception e){
        }
      }
    
}
