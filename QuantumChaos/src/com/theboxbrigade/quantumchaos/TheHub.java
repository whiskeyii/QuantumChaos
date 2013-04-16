package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
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
import com.theboxbrigade.quantumchaos.controllers.SchrodingerController;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.DialogManager;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;
import com.theboxbrigade.quantumchaos.views.BoxView;

public class TheHub extends World {
	private static final float CAMERA_STEP_X = 2f;
	private static final float CAMERA_STEP_Y = 1f;
	protected final String mapPath = "data/maps/";
	protected final String mapName = "TheHub";
	protected final String dialogPath = "data/dialog/";
	protected final String dialogName = "theHub.txt";
	protected final String dialogName2 = "theHub2.txt";
	protected final String dialogName3 = "theHub3.txt";
	
	protected TileManager tileManager;
	protected DialogManager dialogManager;
	
	protected Array<ObjectController> objects;
	protected PlayerController robert;
	protected SchrodingerController schrodinger;
	protected BoxController redBox, greenBox;
	
	protected PauseMenu pauseMenu = new PauseMenu();
	protected boolean showDialog;
	protected DialogBox dialogBox;
	protected boolean dialogEnded = false;

	protected Music bgMusic;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - THE HUB!");
		camera = new OrthographicCamera();
		// Camera viewport
		camera.setToOrtho(false, ((Globals.GAME_WIDTH * 1.0f) / (Globals.GAME_HEIGHT * 1.0f)) * 10, 10);
		camera.zoom = 2;
		camera.translate(24.5f,-17.5f);
		camera.update();

		spriteBatch = new SpriteBatch();
		
		// Load Tiled map
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load(mapPath + mapName + ".tmx", TiledMap.class);
		assetManager.finishLoading();
		tileMap = assetManager.get(mapPath + mapName + ".tmx");
		tileMapRenderer = new IsometricTiledMapRenderer(tileMap, 1f / 32f);
		
		tileManager = new TileManager(tileMap);
		dialogManager = new DialogManager();
		if (Globals.Newton)
			dialogManager.loadFile(dialogPath + dialogName3);
		else if (Globals.Galileo)
			dialogManager.loadFile(dialogPath + dialogName2);
		else
			dialogManager.loadFile(dialogPath + dialogName);
		
		// Place Objects in the World
		dialogBox = new YesNoDialogBox();
		objects = new Array<ObjectController>();
		populateWorld();
		
		// Background Music
		bgMusic = Assets.theHubMusic;
		bgMusic.setLooping(true);
		bgMusic.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// Draw the Tiled Map
		tileMapRenderer.setView(camera);
		tileMapRenderer.render();
		
		// Draw objects under Robert
		for (int i = 0; i < objects.size; i++) {
			spriteBatch = objects.get(i).getViewSpriteBatch();
			spriteBatch.begin();
				objects.get(i).update(delta);
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
			closeInactiveDialog();
			
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
			} else if ((input.buttons[Input.DPAD_DOWN] && !input.oldButtons[Input.DPAD_DOWN])
					|| (input.buttons[Input.DPAD_UP] && !input.oldButtons[Input.DPAD_UP])) {
				if (showDialog) {
					((YesNoDialogBox)dialogBox).processInput(Input.DPAD_DOWN);
				}
				input.releaseAllKeys();
			} else if (input.buttons[Input.AFFIRM] && !input.oldButtons[Input.AFFIRM]) {
				if (showDialog) {
					int choice = ((YesNoDialogBox)dialogBox).processInput(Input.AFFIRM);
					if (choice == YesNoDialogBox.YES_SELECTED) {
						readyToLeave = true;
						if (redBox.isOpen()) nextWorld = redBox.getWorldToTravelTo();
						else if (greenBox.isOpen()) nextWorld = greenBox.getWorldToTravelTo();
					}
				}
			}
		
			checkOpenBox();
			checkSchrodingerTalking();
			closeInactiveBoxes();
			
			// When done talking to Dr. Schrodinger, unlock Galileo's World
			if (dialogBox.isDialogEnded() && greenBox.isLocked()) {
				greenBox.setLocked(false);
			}
			// When Galileo's World is beaten, unlock Newton's World
			if (Globals.Galileo) {
				redBox.setLocked(false);
			}
		}
	}

	protected void checkSchrodingerTalking() {
		if (schrodinger.state == SchrodingerController.INIT_TALKING) {
			schrodinger.setState(SchrodingerController.TALKING);
			showDialog = true;
			dialogBox.setVisible(true);
			String text = dialogManager.getString();
			dialogBox.setText(text);
			dialogBox.setUseGeneratedPortrait(true);
			dialogBox.setPortraitOffsets(0, 0);
			((YesNoDialogBox)dialogBox).setShowYesNo(false);
		}
		if (robert.state != PlayerController.INTERACTING && robert.state != PlayerController.IDLE)
			schrodinger.setState(SchrodingerController.IDLE);
	}

	protected void checkOpenBox() {
		if (redBox.isOpen()) {
			showDialog = true;
			dialogBox.setVisible(true);
			dialogBox.setUseGeneratedPortrait(false);
			dialogBox.setText(redBox.getDialogBoxText());
			dialogBox.setTalkingPortrait(redBox.getDialogBoxPortrait());
			dialogBox.setPortraitOffsets(-40, 0);
			((YesNoDialogBox)dialogBox).setShowYesNo(true);
		}
		if (greenBox.isOpen()) {
			showDialog = true;
			dialogBox.setVisible(true);
			dialogBox.setUseGeneratedPortrait(false);
			dialogBox.setText(greenBox.getDialogBoxText());
			dialogBox.setTalkingPortrait(greenBox.getDialogBoxPortrait());
			dialogBox.setPortraitOffsets(-40, 0);
			((YesNoDialogBox)dialogBox).setShowYesNo(true);
		}
	}
	
	/**
	 * If Robert is no longer interacting with an object, close any dialog boxes
	 */
	protected void closeInactiveDialog() {
		//if (robert.state != PlayerController.INTERACTING) {
			if (schrodinger.state != SchrodingerController.TALKING && !redBox.isOpen() && !greenBox.isOpen()) {
				showDialog = false;
				dialogBox.setVisible(false);
			}
		//}
	}
	
	protected void closeInactiveBoxes() {
		if (robert.state != PlayerController.INTERACTING && robert.state != PlayerController.IDLE) {
			if (redBox.isOpen()) redBox.whenInteractedWith();
			if (greenBox.isOpen()) greenBox.whenInteractedWith();
		}
	}
	
	/**
	 * If Robert is walking, follow him with the camera
	 */
	protected void moveCamera() {
		float camScale = 4.0f;
		float scale = 4.0f;
		float tX = 0, tY = 0;
		float oX = 0, oY = 0;
		switch (robert.getFacing()) {
			case Globals.NORTH:	tX = CAMERA_STEP_X / camScale;
								tY = CAMERA_STEP_Y / camScale;
								oX = -Globals.OBJ_TRANSLATION_X / scale;
								oY = -Globals.OBJ_TRANSLATION_Y / scale;
								break;
			case Globals.EAST:	tX = CAMERA_STEP_X / camScale;
								tY = -CAMERA_STEP_Y / camScale;
								oX = -Globals.OBJ_TRANSLATION_X / scale;
								oY = Globals.OBJ_TRANSLATION_Y / scale;
								break;
			case Globals.SOUTH:	tX = -CAMERA_STEP_X / camScale;
								tY = -CAMERA_STEP_Y / camScale;
								oX = Globals.OBJ_TRANSLATION_X / scale;
								oY = Globals.OBJ_TRANSLATION_Y / scale;
								break;
			case Globals.WEST:	tX = -CAMERA_STEP_X / camScale;
								tY = CAMERA_STEP_Y / camScale;
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
		
		// Create the Schrodinger object
		schrodinger = new SchrodingerController(tileManager);
		schrodinger.setPosition(tileManager.getTile(9, 8));
		schrodinger.setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH / 2.0f, Globals.TILE_HEIGHT * 12.5f);
		objects.add(schrodinger);
		
		// Create boxes
		redBox = new BoxController(tileManager, BoxView.RED);
		greenBox = new BoxController(tileManager, BoxView.GREEN);
		greenBox.setWorldToTravelTo(Globals.GALILEO1);
		redBox.setWorldToTravelTo(Globals.NEWTON1);
		objects.add(redBox);
		objects.add(greenBox);
		redBox.setPosition(tileManager.getTile(10, 14));
		redBox.setScreenPosition(Globals.TILE_WIDTH*1.5f, Globals.TILE_HEIGHT*8.5f);
		redBox.setLocked(true);
		greenBox.setPosition(tileManager.getTile(10, 5));
		greenBox.setScreenPosition(Globals.TILE_WIDTH*6.75f, Globals.TILE_HEIGHT*13.5f);
		greenBox.setLocked(true);
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
		Assets.chestOpen.stop();
		bgMusic.stop();
	}
}
