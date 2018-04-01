package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 70;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 50.0f, 15.0f);
	private float pitch = 30;
	private float yaw ;
	private float roll;
	
	private Player player;
	
	public Camera(Player player)
	{
		this.player = player;
	}

	public void move()
	{
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		
		// Obsolete: use WASD to control camera
		/*if(Keyboard.isKeyDown(Keyboard.KEY_W))
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
		}*/
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
	
	private void calculateCameraPosition(float horizDistance, float verticDistance)
	{
		float theta = player.getRotY() +angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance;
	}
	
	private float calculateHorizontalDistance()
	{
		return (float)(distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance()
	{
		return (float)(distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.05f;
		// the if statement is in order to set a range for camera zoom
		if(distanceFromPlayer > 15 && distanceFromPlayer < 110) {
			distanceFromPlayer -= zoomLevel;
		}
		else if(distanceFromPlayer < 15 && zoomLevel < 0) {
			distanceFromPlayer -= zoomLevel;
		}
		else if(distanceFromPlayer > 110 && zoomLevel > 0) {
			distanceFromPlayer -= zoomLevel;
		}
			
	}
	
	private void calculatePitch()
	{
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			if(pitch > 10 && pitch < 75) {
				pitch -= pitchChange;
			}
			else if(pitch > 75 && pitchChange > 0) {
				pitch -= pitchChange;
			}
			else if(pitch < 10 && pitchChange < 0) {
				pitch -= pitchChange;
			}
		}
	}
	
	private void calculateAngleAroundPlayer()
	{
		if(Mouse.isButtonDown(0))
		{
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;	
		}
	}

}
