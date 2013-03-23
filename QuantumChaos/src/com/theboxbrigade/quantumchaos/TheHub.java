package com.theboxbrigade.quantumchaos;

import javax.swing.Timer;

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
import com.theboxbrigade.quantumchaos.controllers.ObjectController;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.controllers.SchrodingerController;
import com.theboxbrigade.quantumchaos.general.AnimationTimerListener;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class TheHub extends World {
	private static final float CAMERA_STEP_X = 2f;
	private static final float CAMERA_STEP_Y = 1f;
	private static final int TIMERMSECS = 30;
	protected final String path = "data/maps/";
	protected final String mapName = "TheHub";
	protected TileManager tileManager;

	protected Timer timer;
	protected AnimationTimerListener timerListener;
	
	protected Array<ObjectController> objects;
	protected PlayerController player;
	protected SchrodingerController schrodinger;
	protected BoxController redBox, greenBox, blueBox;
	
	// TEST
	protected boolean showDialog;
	protected DialogBox dialogBox;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - THE HUB!");
		float w = Globals.GAME_WIDTH;
		float h = Globals.GAME_HEIGHT;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 2;
		camera.translate(24.5f,-17.5f);
		camera.update();

		spriteBatch = new SpriteBatch();
		
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load(path + mapName + ".tmx", TiledMap.class);
		assetManager.finishLoading();
		tileMap = assetManager.get(path + mapName + ".tmx");
		tileMapRenderer = new IsometricTiledMapRenderer(tileMap, 1f / 32f);
		
		// Read the tileMap
		tileManager = new TileManager(tileMap);
		System.out.println(tileManager.getNumberOfLayers());
		
		// Timer
		timerListener = new AnimationTimerListener(camera);
		timer = new Timer(TIMERMSECS, timerListener);
		timer.start();
		
		objects = new Array<ObjectController>();
		
		// Create the Player object;
		player = new PlayerController(tileManager);
		
		// Create the Schrodinger object;
		schrodinger = new SchrodingerController(tileManager);
		objects.add(schrodinger);
		timerListener.addObject(schrodinger);
		
		// Create boxes
		redBox = new BoxController(tileManager,0);
		greenBox = new BoxController(tileManager,1);
		blueBox = new BoxController(tileManager,2);
		objects.add(redBox);
		objects.add(greenBox);
		objects.add(blueBox);
		timerListener.addObject(redBox);
		timerListener.addObject(greenBox);
		timerListener.addObject(blueBox);
		redBox.setPosition(tileManager.getTile(10, 14));
		redBox.setScreenPosition(Globals.TILE_WIDTH*1.5f, Globals.TILE_HEIGHT*8.5f);
		greenBox.setPosition(tileManager.getTile(10, 5));
		greenBox.setScreenPosition(Globals.TILE_WIDTH*6.75f, Globals.TILE_HEIGHT*13.5f);
		blueBox.setPosition(tileManager.getTile(14, 10));
		blueBox.setScreenPosition(Globals.TILE_WIDTH*6.25f, Globals.TILE_HEIGHT*8.5f);
	}

	@Override
	public void render() {
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
				objects.get(i).update();
			spriteBatch.end();
		}
		
		// Draw the player
		spriteBatch = player.getViewSpriteBatch();
		spriteBatch.begin();
			player.update();
		spriteBatch.end();
		
		// TEST - DIALOG BOX
		if (showDialog) {
			spriteBatch = dialogBox.getSpriteBatch();
			spriteBatch.begin();
				dialogBox.update();
			spriteBatch.end();
		}
	}
	
	@Override
	public void parseInput(Input input) {
		if (!timerListener.blockInput) {
			if (input.buttons[Input.WALK_NORTH] && !input.oldButtons[Input.WALK_NORTH]) {
				player.processInput(Input.WALK_NORTH);
				if (player.isMoving()) {
					timerListener.setMoving(true, Globals.NORTH);
				}
				input.releaseAllKeys();
			} else if (input.buttons[Input.WALK_EAST] && !input.oldButtons[Input.WALK_EAST]) {
				player.processInput(Input.WALK_EAST);
				if (player.isMoving()) {
					timerListener.setMoving(true, Globals.EAST);
				}
				input.releaseAllKeys();
			} else if (input.buttons[Input.WALK_SOUTH] && !input.oldButtons[Input.WALK_SOUTH]) {
				player.processInput(Input.WALK_SOUTH);
				if (player.isMoving()) {
					timerListener.setMoving(true, Globals.SOUTH);
				}
				input.releaseAllKeys();
			} else if (input.buttons[Input.WALK_WEST] && !input.oldButtons[Input.WALK_WEST]) {
				player.processInput(Input.WALK_WEST);
				if (player.isMoving()) {
					timerListener.setMoving(true, Globals.WEST);
				}
				input.releaseAllKeys();
			} else if (input.buttons[Input.INTERACT] && !input.oldButtons[Input.INTERACT]) {
				player.processInput(Input.INTERACT);
				Tile tmpTile = player.getTileInFrontOfPlayer();
				for (int i = 0; i < objects.size; i++) {
					if (objects.get(i).getPosition().getTile().equals(tmpTile)) {
						objects.get(i).processInput(Globals.INTERACT);
						break;
					}
				}
				if (schrodinger.isTalking()) {
					dialogBox = schrodinger.getDialogBox();
					dialogBox.setVisible(true);
					showDialog = true;
				}
				input.releaseAllKeys();
			}
		}
	}

	protected void updateObjects(int state) {
		for (int i = 0; i < objects.size; i++) {
			objects.get(i).processInput(state);
		}
	}
	
	protected ObjectController getObject(ObjectController object) {
		for (int i = 0; i < objects.size; i++) {
			if (objects.get(i) == object) return objects.get(i);
		}
		return null;
	}
}
