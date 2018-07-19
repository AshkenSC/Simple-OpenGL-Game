package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
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
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		
		FontType font = new FontType(loader.loadFontTextureAtlas("segoeUI"), new File("res/segoeUI.fnt"));
		GUIText text = new GUIText("Font rendering test text", 3, font, new Vector2f(0, 0), 1f, true);
		text.setColour(1, 0, 0);
		
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
		// put the two terrains into a list
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);
		terrains.add(terrain2);
		
		// load trees
		RawModel treeModel1 = OBJLoader.loadObjModel("lowPolyTree", loader);
		TexturedModel staticTreeModel1 = new TexturedModel(treeModel1,new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		RawModel treeModel2 = OBJLoader.loadObjModel("tree", loader);
        TexturedModel staticTreeModel2 = new TexturedModel(treeModel2,new ModelTexture(loader.loadTexture("tree")));
        
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
        fern.getTexture().setUseFakeLighting(true);
        
        // load flowers
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grass", loader),
        					new ModelTexture(loader.loadTexture("flower")));
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        
        // load lists of entities and NORMAL MAP entities
        List<Entity> entities = new ArrayList<Entity>();
        List<Entity> normalMapEntities = new ArrayList<Entity>();
	    // set random entity numbers and locations
        Random random = new Random();
        for(int i=0; i<500 ;i++){
        	if (i % 3 == 0)		// in order to reduce the number of trees to 1/3 of grass
        	{
        		float x = random.nextFloat() * 800 - 400;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
        		// load a random tree
        		if(random.nextFloat() > 0.5f) {
        			entities.add(new Entity(staticTreeModel1, new Vector3f(x, y, z),0,0,0,0.85f));
        		}
        		else {
        			entities.add(new Entity(staticTreeModel2, new Vector3f(x, y, z),0,0,0,7));
        		}
        		
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
		lights.add(new Light(new Vector3f(0, 5000, -1500), new Vector3f(0.9f, 0.9f, 0.8f)));
		lights.add(new Light(new Vector3f(0, 15, -60), new Vector3f(2, 0, 0), new Vector3f(1, 0.001f, 0.001f)));
		lights.add(new Light(new Vector3f(0, 15, -130), new Vector3f(1, 0, 8), new Vector3f(1, 0.001f, 0.002f)));
		lights.add(new Light(new Vector3f(0, 15, -195), new Vector3f(2, 2, 0), new Vector3f(1, 0.001f, 0.002f)));
		
		// load lamps
		// CAUTION: lamps x and z should be the same as the lights' to pretend light is from lamps.
		RawModel lampModel = OBJLoader.loadObjModel("lamp", loader);
		TexturedModel lamp = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);
		//entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
		//entities.add(new Entity(lamp, new Vector3f(370, 4.2f, 300), 0, 0, 0, 1));
		//entities.add(new Entity(lamp, new Vector3f(293, -6.7f, -305), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(0, 0, -60), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(0, 0, -130), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(0, 0, -195), 0, 0, 0, 1));
		
		// load NORMAL-MAPPED barrel model
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader), 
													  new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		normalMapEntities.add(new Entity(barrelModel, new Vector3f(50, 15, -205), 0, 0, 0, 1.0f));
		
		// load player
		RawModel playerModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel texturedPlayer = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("playerTexture")));
		Player player = new Player(texturedPlayer, new Vector3f(50, -3, -100), 0, 180, 0, 1);
		
		// load GUI
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(-0.8f, 0.86f), new Vector2f(0.15f, 0.15f));
		GuiTexture gui2 = new GuiTexture(loader.loadTexture("image"), new Vector2f(-0.8f, -0.75f), new Vector2f(0.15f, 0.15f));
		guis.add(gui2);
		guis.add(gui);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		// Load camera and master renderer
		Camera camera = new Camera(player);
		MasterRenderer renderer = new MasterRenderer(loader);

		// Load mouse picker
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		Entity lampEntity = (new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));
		entities.add(lampEntity);
		
		// Load water
		WaterFrameBuffers fbos = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos);
		List<WaterTile> waters = new ArrayList<WaterTile> ();
		WaterTile water = new WaterTile(75, -175, 0);
		waters.add(water);
		
		
		while(!Display.isCloseRequested()) {
	
			camera.move();
			
			// Mouse picker
			picker.update();
			Vector3f terrainPoint = picker.getCurrentTerrainPoint();
			if(terrainPoint != null) {
				lampEntity.setPosition(terrainPoint);
			}
			System.out.println(picker.getCurrentRay());	// To test if the mouse picker is working
			
			// player movement
			if (player.getPosition().x > 0)
			{
				player.move(terrain);
			}
			else
			{
				player.move(terrain2);
			}
			
			// enable clip plane
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);	
			// render reflection texture
			fbos.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1.0f));
			renderer.processEntity(player);
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			// render re-fraction texture
			fbos.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()+1.0f));
			renderer.processEntity(player);
			
			// render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			fbos.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
			renderer.processEntity(player);
			waterRenderer.render(waters, camera, lights.get(0));
			guiRenderer.render(guis);
			TextMaster.render();
			
			DisplayManager.updateDisplay();
		}
		
		// Clean Up
		TextMaster.cleanUp();
		fbos.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
