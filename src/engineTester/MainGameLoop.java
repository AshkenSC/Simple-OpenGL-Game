package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
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
		
		// load terrains
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap");
		
		// load trees
		RawModel model = OBJLoader.loadObjModel("tree", loader);
        
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
        
        // load grass
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), 
        					new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        
        // load fern
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
        fernTextureAtlas.setNumberOfRows(2);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), 
        					fernTextureAtlas);
        fern.getTexture().setHasTransparency(true);
        
        // load flowers
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grass", loader),
        					new ModelTexture(loader.loadTexture("flower")));
        flower.getTexture().setHasTransparency(true);
        
	    // set random entity numbers and locations
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0; i<500 ;i++){
        	if (i % 3 == 0)		// in order to reduce the number of trees to 1/3 of grass
        	{
        		float x = random.nextFloat() * 800 - 400;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(staticModel, new Vector3f(x, y, z),0,0,0,6));
        	}
        	if (i % 2 == 0) {
        		float x = random.nextFloat() * 800 - 400;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
	            entities.add(new Entity(grass, new Vector3f(x, y, z),0,0,0,1));
	            entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z),0,random.nextFloat() * 360,0,0.9f));
	            entities.add(new Entity(flower, new Vector3f(x, y, z),0,0,0,1));
        	}
        }
        
        // load lights
		List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
		lights.add(new Light(new Vector3f(0, 10, -60), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.02f)));
		lights.add(new Light(new Vector3f(0, 17, -90), new Vector3f(0, 0, 10), new Vector3f(1, 0.01f, 0.02f)));
		lights.add(new Light(new Vector3f(0, 7, -135), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.02f)));
		
		// load lamps
		// CAUTION: lamps x and z should be the same as the lights' to pretend light is from lamps.
		RawModel lampModel = OBJLoader.loadObjModel("lamp", loader);
		TexturedModel lamp = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		//entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
		//entities.add(new Entity(lamp, new Vector3f(370, 4.2f, 300), 0, 0, 0, 1));
		//entities.add(new Entity(lamp, new Vector3f(293, -6.7f, -305), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(0, 0, -60), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(0, 0, -90), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(0, 0, -135), 0, 0, 0, 1));
		
		// load player
		RawModel playerModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel texturedPlayer = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
		Player player = new Player(texturedPlayer, new Vector3f(0, -3, -50), 0, 0, 0, 1);
		
		// load GUI
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		GuiTexture gui2 = new GuiTexture(loader.loadTexture("image"), new Vector2f(0.5f, 0.3f), new Vector2f(0.25f, 0.25f));
		guis.add(gui2);
		guis.add(gui);
		
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		// load camera and master renderer
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer();

		while(!Display.isCloseRequested()) {
	
			camera.move();
			
			// stuff you want to render
			if (player.getPosition().x > 0)
			{
				player.move(terrain);
			}
			else
			{
				player.move(terrain2);
			}
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity:entities)
			{
				renderer.processEntity(entity);
			}
			renderer.render(lights, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
		}
		
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
