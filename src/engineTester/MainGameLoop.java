package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();		
		Renderer renderer = new Renderer(shader);
		// OpenGL vertices
		float[] vertices = {
				// vertex list is DIFFERENT!
				
				// left bottom triangle
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				//0.5f, -0.5f, 0f,
				// right top triangle
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0
				//-0.5f, 0.5f, 0f
		};
		
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};
		
		float[] textureCoords = {
				0, 0,
				0, 1,
				1, 1,
				1, 0
		};
		
		//RawModel model = loader.loadToVAO(vertices);
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		//ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		//TexturedModel texturedModel = new TexturedModel(model, texture);
		//和之前的代码奇怪地不一致了……
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);
		
		while(!Display.isCloseRequested()) {
			//rotation animation
			entity.increasePosition(0, 0, -0.05f);
			entity.increaseRotation(0, 1, 0);
			
			renderer.prepare();
			//game logic
			//shader starts
			shader.start();
			//render
			renderer.render(entity, shader);
			//shader ends
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
