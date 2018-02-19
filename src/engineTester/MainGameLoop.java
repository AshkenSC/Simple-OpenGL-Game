package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
				
		//RawModel model = loader.loadToVAO(vertices);
		//RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		//ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
		//TexturedModel texturedModel = new TexturedModel(model, texture);
		//strangely different from previous code...
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("yellow")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0, -3, -25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			//rotation animation
			entity.increasePosition(0, 0, 0.0f);
			entity.increaseRotation(0, 0.5f, 0);
			camera.move();
			// stuff you want to render
			// https://youtu.be/X6KjDwA7mZg?t=129
			for(Entity cube : allCubes)
			{
				renderer.processEntity(cube);
			}
			DisplayManager.updateDisplay();
			
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
