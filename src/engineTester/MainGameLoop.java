package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;

import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import terrains.TerrainTexture;
import terrains.TerrainTexturePack;
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
        
        // load flowers
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grass", loader),
        					new ModelTexture(loader.loadTexture("flower")));
        flower.getTexture().setHasTransparency(true);
        
	    // set random entity numbers and locations
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
        	if (i % 3 == 0)		// in order to reduce the number of trees to 1/3 of grass
        	{
        		entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,6));
        	}
            entities.add(new Entity(grass, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
            entities.add(new Entity(flower, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
        }
        
        // load light
		Light light = new Light(new Vector3f(20000, 20000, 20000), new Vector3f(1, 1, 1));
		
		// load terrains
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
		//Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap");
		
		//load player
		RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);
		TexturedModel bunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("yellow")));
		Player player = new Player(bunny, new Vector3f(0, -3, -50), 0, 0, 0, 1);
		
		
		// load camera and master renderer
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer();

		while(!Display.isCloseRequested()) {
	
			camera.move();
			
			// stuff you want to render
			player.move(terrain);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);
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
