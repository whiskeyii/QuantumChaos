package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.theboxbrigade.quantumchaos.controllers.BoxController;
import com.theboxbrigade.quantumchaos.controllers.Interactable;
import com.theboxbrigade.quantumchaos.controllers.ObjectController;
import com.theboxbrigade.quantumchaos.controllers.PlanetController;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.controllers.SchrodingerController;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.DialogManager;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;
import com.theboxbrigade.quantumchaos.views.BoxView;
import com.theboxbrigade.quantumchaos.views.PlanetView;

public class Galileo1 extends World {
	private static final float CAMERA_STEP_X = 2f;
	private static final float CAMERA_STEP_Y = 1f;
	protected final String mapPath = "data/maps/";
	protected final String mapName = "Galileo1";
	protected final String dialogPath = "data/dialog/";
	protected final String dialogName = "Galileo1.txt";
	
	protected WorldNotifier notifier;
	protected TileManager tileManager;
	protected DialogManager dialogManager;
	
	protected Array<ObjectController> objects;
	protected PlayerController robert;
	protected PlanetController[] planets;
	
	protected boolean showDialog;
	protected DialogBox dialogBox;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - GALILEO-1!");
		Assets.load(Globals.GALILEO1);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ((Globals.GAME_WIDTH * 1.0f) / (Globals.GAME_HEIGHT * 1.0f)) * 10, 10);
		camera.zoom = 2;
		camera.translate(24.5f,-17.5f);
		camera.update();

		spriteBatch = new SpriteBatch();
		
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load(mapPath + mapName + ".tmx", TiledMap.class);
		assetManager.finishLoading();
		tileMap = assetManager.get(mapPath + mapName + ".tmx");
		tileMapRenderer = new IsometricTiledMapRenderer(tileMap, 1f / 32f);
		
		notifier = new WorldNotifier();
		tileManager = new TileManager(tileMap);
		dialogManager = new DialogManager();
		dialogManager.loadFile(dialogPath + dialogName);
		
		objects = new Array<ObjectController>();
		populateWorld();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// Draw the Tiled Map
		tileMapRenderer.setView(camera);
		tileMapRenderer.render();
		
		// Draw objects under Robert
		for (ObjectController object : objects) {
			spriteBatch = object.getViewSpriteBatch();
			spriteBatch.begin();
				object.update(delta);
			spriteBatch.end();
		}
		
		// Draw the player
		spriteBatch = robert.getViewSpriteBatch();
		spriteBatch.begin();
			if (robert.update(delta) && robert.state == PlayerController.WALKING) {
				moveCamera();
			}
		spriteBatch.end();
		
		// Dialog Box
		if (showDialog && dialogBox != null) {
			dialogBox.setVisible(true);
			spriteBatch = dialogBox.getSpriteBatch();
			spriteBatch.begin();
				dialogBox.update();
			spriteBatch.end();
		}
	}
	
	@Override
	public void parseInput(Input input) {
		if (input.buttons[Input.WALK_NORTH]) {
			robert.processInput(Input.WALK_NORTH);
			//input.releaseAllKeys();
		} else if (input.buttons[Input.WALK_EAST]) {
			robert.processInput(Input.WALK_EAST);
			//input.releaseAllKeys();
		} else if (input.buttons[Input.WALK_SOUTH]) {
			robert.processInput(Input.WALK_SOUTH);
			//input.releaseAllKeys();
		} else if (input.buttons[Input.WALK_WEST]) {
			robert.processInput(Input.WALK_WEST);
			//input.releaseAllKeys();
		} else if (input.buttons[Input.INTERACT] && !input.oldButtons[Input.INTERACT]) {
			Interactable tmp = (Interactable)robert.getTileInFrontOfPlayer().getObstructing();
			if (tmp != null) {
				robert.setInteractable(tmp);
				robert.processInput(Input.INTERACT);
				tmp = null; 
			}
			input.releaseAllKeys();
		} else if (input.buttons[Input.AFFIRM] && !input.oldButtons[Input.AFFIRM]) {
			if (showDialog) {
				int choice = ((YesNoDialogBox)dialogBox).processInput(Input.AFFIRM);
				if (choice == YesNoDialogBox.YES_SELECTED) readyToLeave = true;
			}
		}
	}
	
	protected int getWorldToTravelTo(BoxController box) {
		return box.getWorldToTravelTo();
	}
	
	protected void moveCamera() {
		float scale = 4.0f;
		float tX = 0, tY = 0;
		float oX = 0, oY = 0;
		switch (robert.getFacing()) {
			case Globals.NORTH:	tX = CAMERA_STEP_X / scale;
								tY = CAMERA_STEP_Y / scale;
								oX = -Globals.OBJ_TRANSLATION_X / scale;
								oY = -Globals.OBJ_TRANSLATION_Y / scale;
								break;
			case Globals.EAST:	tX = CAMERA_STEP_X / scale;
								tY = -CAMERA_STEP_Y / scale;
								oX = -Globals.OBJ_TRANSLATION_X / scale;
								oY = Globals.OBJ_TRANSLATION_Y / scale;
								break;
			case Globals.SOUTH:	tX = -CAMERA_STEP_X / scale;
								tY = -CAMERA_STEP_Y / scale;
								oX = Globals.OBJ_TRANSLATION_X / scale;
								oY = Globals.OBJ_TRANSLATION_Y / scale;
								break;
			case Globals.WEST:	tX = -CAMERA_STEP_X / scale;
								tY = CAMERA_STEP_Y / scale;
								oX = Globals.OBJ_TRANSLATION_X / scale;
								oY = -Globals.OBJ_TRANSLATION_Y / scale;
		}
		camera.translate(tX, tY);
		for (ObjectController object : objects)
			object.translate(oX, oY);
		camera.update();
	}
	
	protected void populateWorld() {
		// Create the Player object
		robert = new PlayerController(tileManager);
		
		planets = new PlanetController[9];
		planets[0] = new PlanetController(tileManager, PlanetView.SUN);
		planets[1] = new PlanetController(tileManager, PlanetView.MERCURY);
		planets[2] = new PlanetController(tileManager, PlanetView.VENUS);
		planets[3] = new PlanetController(tileManager, PlanetView.EARTH);
		planets[4] = new PlanetController(tileManager, PlanetView.MARS);
		planets[5] = new PlanetController(tileManager, PlanetView.JUPITER);
		planets[6] = new PlanetController(tileManager, PlanetView.SATURN);
		planets[7] = new PlanetController(tileManager, PlanetView.URANUS);
		planets[8] = new PlanetController(tileManager, PlanetView.NEPTUNE);
		objects.add(planets[0]);
		objects.add(planets[1]);
		objects.add(planets[2]);
		objects.add(planets[3]);
		objects.add(planets[4]);
		objects.add(planets[5]);
		objects.add(planets[6]);
		objects.add(planets[7]);
		objects.add(planets[8]);
		
		planets[0].setScreenPosition(Globals.TILE_WIDTH*6.75f, Globals.TILE_HEIGHT*13.5f);
		
	}
	
	protected void addWorldListener(WorldListener listener) {
		notifier.addListener(listener);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
