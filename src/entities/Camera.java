package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera()
	{
		
	}

	public void move()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.y += 1.0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.y -= 1.0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x += 1.0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.x -= 1.0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
		{
			position.z -= 1.0f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			position.z += 1.0f;
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
