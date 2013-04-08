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
	protected final String dialogName = "TheHub.txt";
	
	protected TileManager tileManager;
	protected DialogManager dialogManager;
	
	protected Array<ObjectController> objects;
	protected PlayerController robert;
	protected SchrodingerController schrodinger;
	protected BoxController redBox, greenBox, blueBox;
	
	protected boolean showDialog;
	protected DialogBox dialogBox;
	protected int dialogBoxType = -1;
	protected static final int BOX_DIALOG = 0;
	protected static final int SCHRODINGER_DIALOG = 1;

	protected Music bgMusic;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - THE HUB!");
		camera = new OrthographicCamera();
		// Camera viewport:
		// 13.3333 x 10.0000
		// [-6.666 : 6.666] x [-5.000 : 5.000]
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
	}
	
	@Override
	public void parseInput(Input input) {
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
					else if (blueBox.isOpen()) nextWorld = blueBox.getWorldToTravelTo();
				}
			}
		}
		
		checkSchrodingerTalking();
		checkOpenBox();
		closeInactiveDialog();
	}
	
	/**
	 * If Robert is interacting with Dr. Schrodinger, create a dialog box for it
	 */
	protected void checkSchrodingerTalking() {
		if (schrodinger.state == SchrodingerController.INIT_TALKING) {
			dialogBoxType = SCHRODINGER_DIALOG;
			schrodinger.setState(SchrodingerController.TALKING);
			String text = dialogManager.getString();
			dialogBox = new DialogBox(Assets.schrodingerE, text);
			dialogBox.setVisible(true);
			showDialog = true;
		} else if (robert.state != PlayerController.INTERACTING && 
				schrodinger.state != SchrodingerController.TALKING && 
				dialogBoxType == SCHRODINGER_DIALOG) {
			dialogBoxType = -1;
			dialogBox = null;
			showDialog = false;
		}
	}
	
	/**
	 * If Robert is interacting with a box, create a dialog box for it
	 */
	protected void checkOpenBox() {
		if (redBox.isOpen()) {
			dialogBoxType = BOX_DIALOG;
			if (dialogBox == null) {
				dialogBox = redBox.getDialogBox();
				showDialog = true;
			}
		} else if (greenBox.isOpen()) {
			dialogBoxType = BOX_DIALOG;
			if (dialogBox == null) {
				dialogBox = greenBox.getDialogBox();
				showDialog = true;
			}
		} else if (blueBox.isOpen()) {
			dialogBoxType = BOX_DIALOG;
			if (dialogBox == null) {
				dialogBox = blueBox.getDialogBox();
				showDialog = true;
			}
		}  else if (dialogBoxType == BOX_DIALOG) {
			dialogBox = null;
			showDialog = false;
			dialogBoxType = -1;
		}
	}
	
	/**
	 * If Robert is no longer interacting with an object, close any dialog boxes
	 */
	protected void closeInactiveDialog() {
		if (robert.state != PlayerController.INTERACTING) {
			if (showDialog && dialogBox != null) {
				dialogBoxType = -1;
				dialogBox = null;
				showDialog = false;
			}
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
		blueBox = new BoxController(tileManager, BoxView.BLUE);
		greenBox.setWorldToTravelTo(Globals.GALILEO1);
		blueBox.setWorldToTravelTo(Globals.NEWTON1);
		objects.add(redBox);
		objects.add(greenBox);
		objects.add(blueBox);
		redBox.setPosition(tileManager.getTile(10, 14));
		redBox.setScreenPosition(Globals.TILE_WIDTH*1.5f, Globals.TILE_HEIGHT*8.5f);
		greenBox.setPosition(tileManager.getTile(10, 5));
		greenBox.setScreenPosition(Globals.TILE_WIDTH*6.75f, Globals.TILE_HEIGHT*13.5f);
		blueBox.setPosition(tileManager.getTile(14, 10));
		blueBox.setScreenPosition(Globals.TILE_WIDTH*6.25f, Globals.TILE_HEIGHT*8.5f);
	}

	@Override
	public void dispose() {
		assetManager.clear();
		bgMusic.stop();
	}
}
