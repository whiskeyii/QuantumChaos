package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
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
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.DialogManager;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class Galileo2 extends World {
	private static final float CAMERA_STEP_X = 2f;
	private static final float CAMERA_STEP_Y = 1f;
	protected final String mapPath = "data/maps/";
	protected final String mapName = "Galileo2";
	protected final String dialogPath = "data/dialog/";
	protected final String dialogName = "Galileo2.txt";
	
	protected WorldNotifier notifier;
	protected TileManager tileManager;
	protected DialogManager dialogManager;
	
	protected Array<ObjectController> objects;
	protected PlayerController robert;
	
	protected boolean puzzleComplete = false;
	
	protected PauseMenu pauseMenu = new PauseMenu();
	protected boolean showDialog;
	protected DialogBox dialogBox;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - GALILEO-1!");
		Assets.load(Globals.GALILEO1);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, ((Globals.GAME_WIDTH * 1.0f) / (Globals.GAME_HEIGHT * 1.0f)) * 10, 10);
		camera.zoom = 2;
		camera.translate(14.5f,-12.5f);
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
		
		// Draw objects over Robert
		for (ObjectController object : objects) {
			if (object.getPosition().getX() > robert.getPosition().getX() ||
					object.getPosition().getY() > robert.getPosition().getY()) {
				spriteBatch = object.getViewSpriteBatch();
				spriteBatch.begin();
					object.update(delta);
				spriteBatch.end();
			}
		}
		
		// Dialog Box
		if (showDialog && dialogBox != null) {
			dialogBox.setVisible(true);
			spriteBatch = dialogBox.getSpriteBatch();
			spriteBatch.begin();
				dialogBox.update();
			spriteBatch.end();
		}
		
		// Pause Menu
		spriteBatch = pauseMenu.getSpriteBatch();
		spriteBatch.begin();
			pauseMenu.update();
		spriteBatch.end();
	}
	
	@Override
	public void parseInput(Input input) {
		/* Pause Menu Input */
		if (input.buttons[Input.ESCAPE] && !input.oldButtons[Input.ESCAPE]) {
			if (pauseMenu.isVisible()) {
				if (pauseMenu.isSeeControls()) pauseMenu.processInput(Input.ESCAPE);
				else pauseMenu.setVisible(false);
			}
			else pauseMenu.setVisible(true);
		} else if (input.buttons[Input.DPAD_UP] && !input.oldButtons[Input.DPAD_UP]) {
			if (pauseMenu.isVisible()) pauseMenu.processInput(Input.DPAD_UP);
		} else if (input.buttons[Input.DPAD_DOWN] && !input.oldButtons[Input.DPAD_DOWN]) {
			if (pauseMenu.isVisible()) pauseMenu.processInput(Input.DPAD_DOWN);
		} else if (input.buttons[Input.AFFIRM] && !input.oldButtons[Input.AFFIRM]) {
			if (pauseMenu.isVisible()) pauseMenu.processInput(Input.AFFIRM);
		}
		processPauseInput();
		
		/* Gameplay Input */
			if (!pauseMenu.isVisible()) {
			if (input.buttons[Input.WALK_NORTH]) {
				robert.processInput(Input.WALK_NORTH);
			} else if (input.buttons[Input.WALK_EAST]) {
				robert.processInput(Input.WALK_EAST);
			} else if (input.buttons[Input.WALK_SOUTH]) {
				robert.processInput(Input.WALK_SOUTH);
			} else if (input.buttons[Input.WALK_WEST]) {
				robert.processInput(Input.WALK_WEST);
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
	}
	
	protected int getWorldToTravelTo(BoxController box) {
		return box.getWorldToTravelTo();
	}
	
	/**
	 * Move the camera to the proper position<br/>
	 * Also, translate the scene objects to their proper positions
	 */
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
		robert.setPosition(tileManager.getTile(9, 14));
	}
	
	protected void processPauseInput() {
		switch (pauseMenu.selected) {
			case PauseMenu.RESUME:			pauseMenu.setVisible(false);
											break;
			case PauseMenu.RETURN_TO_HUB:	readyToLeave = true;
											nextWorld = Globals.THE_HUB;
											break;
			case PauseMenu.CONTROLS:		if (!pauseMenu.isSeeControls()) pauseMenu.setSeeControls(true);
											else pauseMenu.setSeeControls(false);
											pauseMenu.selected = -1;
											break;
			case PauseMenu.MAIN_MENU:		readyToLeave = true;
											nextWorld = Globals.MAIN_MENU;
											break;
		}
	}

	@Override
	public void dispose() {
		assetManager.clear();
		if (nextWorld != Globals.GALILEO3) Assets.galileoMusic.stop();
		Assets.unload(Globals.GALILEO2);
	}
}
