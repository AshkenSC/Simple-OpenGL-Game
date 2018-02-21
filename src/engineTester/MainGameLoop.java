package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
				
		/* display dragon
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("yellow")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(0.2f);
		*/
		
		// load trees
		RawModel model = OBJLoader.loadObjModel("tree", loader);
        
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
        
        // load grass
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), 
        					new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        
        // load fern
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), 
        					new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        
	    // set random entity numbers and locations
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
            entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        
        }
        
        // load light
		Light light = new Light(new Vector3f(20000, 20000, 20000), new Vector3f(1, 1, 1));
		
		// load terrains
		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		
		// load camera and master renderer
		Camera camera = new Camera();
		MasterRenderer renderer = new MasterRenderer();

		while(!Display.isCloseRequested()) {
			
			camera.move();
			
			// stuff you want to render
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity:entities)
			{
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
