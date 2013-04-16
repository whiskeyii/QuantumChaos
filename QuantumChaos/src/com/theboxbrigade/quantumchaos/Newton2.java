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
import com.theboxbrigade.quantumchaos.controllers.DoorController;
import com.theboxbrigade.quantumchaos.controllers.Interactable;
import com.theboxbrigade.quantumchaos.controllers.KeyController;
import com.theboxbrigade.quantumchaos.controllers.N2ChunkController;
import com.theboxbrigade.quantumchaos.controllers.ObjectController;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.DialogManager;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;
import com.theboxbrigade.quantumchaos.views.KeyView;

public class Newton2 extends World {
	private static final float CAMERA_STEP_X = 2f;
	private static final float CAMERA_STEP_Y = 1f;
	protected final String mapPath = "data/maps/";
	protected final String mapName = "Newton2";
	protected final String dialogPath = "data/dialog/";
	protected final String dialogName = "Newton2.txt";
	
	protected TileManager tileManager;
	protected DialogManager dialogManager;
	
	protected Array<ObjectController> objects;
	protected PlayerController robert;
	protected N2ChunkController[] chunks;
	protected KeyController key;
	protected DoorController door;
	
	protected PauseMenu pauseMenu = new PauseMenu();
	protected boolean showDialog;
	protected DialogBox dialogBox;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - NEWTON-2!");
		Assets.load(Globals.NEWTON2);
		
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
		
		tileManager = new TileManager(tileMap);
		dialogManager = new DialogManager();
		dialogManager.loadFile(dialogPath + dialogName);
		
		objects = new Array<ObjectController>();
		populateWorld();
		
		nextWorld = Globals.THE_HUB;
		Assets.newtonMusic.setLooping(true);
		Assets.newtonMusic.play();
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
		
		// Draw Robert
		spriteBatch = robert.getViewSpriteBatch();
		spriteBatch.begin();
			if (robert.update(delta) && (robert.state == PlayerController.WALKING || robert.state == PlayerController.SLIDING)) {
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
				if (tmp != null && tmp.interactableType() != Interactable.N2_CHUNK) {
					robert.setInteractable(tmp);
					robert.processInput(Input.INTERACT);
					tmp = null; 
				}
				input.releaseAllKeys();
			} else if (input.buttons[Input.ATTACK] && !input.oldButtons[Input.ATTACK]) {
				Interactable tmp = (Interactable)robert.getTileInFrontOfPlayer().getObstructing();
				if (tmp != null) {
					if (tmp.interactableType() == Interactable.N2_CHUNK) {
						updateFacingForChunks();
						robert.setInteractable(tmp);
						robert.processInput(Input.INTERACT);
						tmp = null; 
					}
				}
				input.releaseAllKeys();
			}
			
			checkDoorUnlockable();
			checkReadyToLeave();
		}
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
		robert.setPosition(tileManager.getTile(14, 14));
		
		// Craete chunks
		chunks = new N2ChunkController[13];
		int S = N2ChunkController.SMALL;
		int M = N2ChunkController.MEDIUM;
		int L = N2ChunkController.LARGE;
		int[] chunkTypes = {S, S, L, S, L, M, L, S, S, L, M, S, S};
		for (int i=0; i<13; i++) {
			chunks[i] = new N2ChunkController(tileManager, chunkTypes[i]);
			objects.add(chunks[i]);
		}
		chunks[0].setPosition(tileManager.getTile(13,14));
		chunks[0].setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 0.875f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 0.25f);
		chunks[1].setPosition(tileManager.getTile(10,13));
		chunks[1].setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 2.125f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 2.5f);
		chunks[2].setPosition(tileManager.getTile(11,12));
		chunks[2].setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 0.875f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 2.5f);
		chunks[3].setPosition(tileManager.getTile(9,12));
		chunks[3].setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 2.125f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 3.75f);
		chunks[4].setPosition(tileManager.getTile(9,11));
		chunks[4].setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 1.5f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 4.25f);
		chunks[5].setPosition(tileManager.getTile(12,10));
		chunks[5].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 0.875f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 3.0f);
		chunks[6].setPosition(tileManager.getTile(6,10));
		chunks[6].setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 2.675f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 6.75f);
		chunks[7].setPosition(tileManager.getTile(13,9));
		chunks[7].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 2.125f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 3.0f);
		chunks[8].setPosition(tileManager.getTile(11,9));
		chunks[8].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 0.875f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 4.25f);
		chunks[9].setPosition(tileManager.getTile(14,8));
		chunks[9].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 3.375f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 3.05f);
		chunks[10].setPosition(tileManager.getTile(11,6));
		chunks[10].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 2.75f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 6.0f);
		chunks[11].setPosition(tileManager.getTile(12,5));
		chunks[11].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 3.9f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 6.0f);
		chunks[12].setPosition(tileManager.getTile(7,5));
		chunks[12].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 0.875f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 9.0f);
		
		door = new DoorController(tileManager, Globals.WEST);
		door.setPosition(tileManager.getTile(4,6));
		door.setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 0.875f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 10.375f);
		objects.add(door);
		
		key = new KeyController(tileManager, KeyView.SILVER);
		key.setPosition(tileManager.getTile(12,14));
		key.setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH * 1.125f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 1.125f);
		key.setVisible(true);
		key.setInteractable(true);
		key.setObstructing(true);
		objects.add(key);
	}
	
	protected void updateFacingForChunks() {
		for (int i=0; i<chunks.length; i++) {
			chunks[i].setPlayerFacing(robert.getFacing());
		}
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
	
	protected void checkDoorUnlockable() {
		if (key.state == KeyController.PICKED_UP) {
			if (!door.isUnlockable()) {
				System.out.println("Make door unlockable");
				door.setUnlockable(true);
			}
		}
	}
	
	protected void checkReadyToLeave() {
		if (robert.getPosition().getTile().equals(tileManager.getTile(4, 6))) {
			readyToLeave = true;
			Globals.Newton = true;
		}
	}

	@Override
	public void dispose() {
		assetManager.clear();
		if (nextWorld != Globals.NEWTON3) Assets.newtonMusic.stop();
		Assets.unload(Globals.NEWTON2);
	}
}
