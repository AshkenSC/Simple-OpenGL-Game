package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private static float cameraSpeed = 1.0f;
	private Vector3f position = new Vector3f(0, 3.5f, 0);
	private float pitch = 10;
	private float yaw;
	private float roll;
	
	public Camera()
	{
		
	}

	public void move()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z -= cameraSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z += cameraSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x += cameraSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.x -= cameraSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q) && position.y<=10.0f)
		{
			position.y += cameraSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E) && position.y>=3.0f)
		{
			position.y -= cameraSpeed;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	

}
