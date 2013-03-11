package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class TheHub extends World {
	protected final String path = "data/maps/";
	protected final String mapName = "TheHub";
	protected TileManager tileManager;
	protected PlayerController player;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - THE HUB!");
		float w = Globals.GAME_WIDTH;
		float h = Globals.GAME_HEIGHT;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 2;
		camera.translate(20,-10);
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
		
		player = new PlayerController();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		tileMapRenderer.setView(camera);
		tileMapRenderer.render();
		spriteBatch = player.getViewSpriteBatch();
		spriteBatch.begin();
			player.update();
		spriteBatch.end();
	}
	
	@Override
	public void parseInput(Input input) {
		if (input.buttons[Input.WALK_NORTH] && !input.oldButtons[Input.WALK_NORTH]) {
			System.out.println("PRESSED NORTH");
			player.processInput(Input.WALK_NORTH);
			if (player.isMoving()) {
				camera.translate(2,1);
				camera.update();
			}
			input.releaseAllKeys();
		} else if (input.buttons[Input.WALK_EAST] && !input.oldButtons[Input.WALK_EAST]) {
			System.out.println("PRESSED EAST");
			player.processInput(Input.WALK_EAST);
			if (player.isMoving()) {
				camera.translate(2,-1);
				camera.update();
			}
			input.releaseAllKeys();
		} else if (input.buttons[Input.WALK_SOUTH] && !input.oldButtons[Input.WALK_SOUTH]) {
			System.out.println("PRESSED SOUTH");
			player.processInput(Input.WALK_SOUTH);
			if (player.isMoving()) {
				camera.translate(-2,-1);
				camera.update();
			}
			input.releaseAllKeys();
		} else if (input.buttons[Input.WALK_WEST] && !input.oldButtons[Input.WALK_WEST]) {
			System.out.println("PRESSED WEST");
			player.processInput(Input.WALK_WEST);
			if (player.isMoving()) {
				camera.translate(-2,1);
				camera.update();
			}
			input.releaseAllKeys();
		}
	}

}
