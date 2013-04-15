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
import com.theboxbrigade.quantumchaos.controllers.DoorController;
import com.theboxbrigade.quantumchaos.controllers.Interactable;
import com.theboxbrigade.quantumchaos.controllers.JournalPageController;
import com.theboxbrigade.quantumchaos.controllers.KeyController;
import com.theboxbrigade.quantumchaos.controllers.ObjectController;
import com.theboxbrigade.quantumchaos.controllers.PlanetController;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.DialogManager;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;
import com.theboxbrigade.quantumchaos.views.PlanetView;

public class Galileo1 extends World {
	private static final float CAMERA_STEP_X = 2f;
	private static final float CAMERA_STEP_Y = 1f;
	protected final String mapPath = "data/maps/";
	protected final String mapName = "Galileo1";
	protected final String dialogPath = "data/dialog/";
	protected final String dialogName = "Galileo1.txt";
	
	protected TileManager tileManager;
	protected DialogManager dialogManager;
	
	protected Array<ObjectController> objects;
	protected PlayerController robert;
	protected PlanetController[] planets;
	protected PlanetSlot[] slots;
	protected boolean puzzleComplete = false;
	protected KeyController key;
	protected DoorController door;
	protected JournalPageController journalPage;
	
	protected PauseMenu pauseMenu = new PauseMenu();
	protected boolean showDialog;
	protected DialogBox dialogBox;
	
	protected Music bgMusic;
	
	@Override
	public void create() {
		System.out.println("I AM HERE - GALILEO-1!");
		Assets.load(Globals.GALILEO1);
		
		// Camera viewport
		camera = new OrthographicCamera();
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
		dialogManager.loadFile(dialogPath + dialogName);
		
		// Place Objects in the World
		objects = new Array<ObjectController>();
		populateWorld();
		
		nextWorld = Globals.GALILEO2;
		bgMusic = Assets.galileoMusic;
		bgMusic.setVolume(0.2f);
		bgMusic.setLooping(true);
		bgMusic.play();
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
				handleDropPlanet();
				input.releaseAllKeys();
			} else if (input.buttons[Input.AFFIRM] && !input.oldButtons[Input.AFFIRM]) {
				if (showDialog) {
					int choice = ((YesNoDialogBox)dialogBox).processInput(Input.AFFIRM);
					if (choice == YesNoDialogBox.YES_SELECTED) readyToLeave = true;
				}
			}
		
			checkCarryingPlanet();
			drawPlanetOverPlayer();
			if (!puzzleComplete) checkPuzzleComplete();
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
		planets[0].setCorrectSlotPosition(tileManager.getTile(4, 14));
		planets[1].setCorrectSlotPosition(tileManager.getTile(5, 14));
		planets[2].setCorrectSlotPosition(tileManager.getTile(6, 13));
		planets[3].setCorrectSlotPosition(tileManager.getTile(4, 11));
		planets[4].setCorrectSlotPosition(tileManager.getTile(8, 14));
		planets[5].setCorrectSlotPosition(tileManager.getTile(6, 10));
		planets[6].setCorrectSlotPosition(tileManager.getTile(9, 12));
		planets[7].setCorrectSlotPosition(tileManager.getTile(7, 8));
		planets[8].setCorrectSlotPosition(tileManager.getTile(11, 11));
		objects.add(planets[0]);
		objects.add(planets[1]);
		objects.add(planets[2]);
		objects.add(planets[3]);
		objects.add(planets[4]);
		objects.add(planets[5]);
		objects.add(planets[6]);
		objects.add(planets[7]);
		objects.add(planets[8]);
		planets[0].setPosition(tileManager.getTile(4,14));
		planets[0].setScreenPosition(Globals.TILE_WIDTH * -2.125f, Globals.GAME_HEIGHT);
		planets[1].setPosition(tileManager.getTile(9,7));
		planets[1].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 7.0f);
		planets[2].setPosition(tileManager.getTile(7,4));
		planets[2].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 1.625f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 10.0f);
		planets[3].setPosition(tileManager.getTile(9,4));
		planets[3].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 2.875f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 8.75f);
		planets[4].setPosition(tileManager.getTile(12,9));
		planets[4].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 1.625f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 4.125f);
		planets[5].setPosition(tileManager.getTile(10,6));
		planets[5].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 2.25f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 7.25f);
		planets[6].setPosition(tileManager.getTile(8,5));
		planets[6].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 1.625f - 18, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 9.0f);
		planets[7].setPosition(tileManager.getTile(13,11));
		planets[7].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH - 22, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 2.5f);
		planets[8].setPosition(tileManager.getTile(11,7));
		planets[8].setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 2.25f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 6.0f);
		planets[6].offsetX = -18;
		planets[7].offsetX = -22;
		
		
		slots = new PlanetSlot[9];
		slots[0] = new PlanetSlot(tileManager, PlanetView.SUN);
		slots[0].setPosition(tileManager.getTile(4,14));
		slots[1] = new PlanetSlot(tileManager, PlanetView.MERCURY);
		slots[1].setPosition(tileManager.getTile(5,14));
		slots[2] = new PlanetSlot(tileManager, PlanetView.VENUS);
		slots[2].setPosition(tileManager.getTile(6,13));
		slots[3] = new PlanetSlot(tileManager, PlanetView.EARTH);
		slots[3].setPosition(tileManager.getTile(4,11));
		slots[4] = new PlanetSlot(tileManager, PlanetView.MARS);
		slots[4].setPosition(tileManager.getTile(8,14));
		slots[5] = new PlanetSlot(tileManager, PlanetView.JUPITER);
		slots[5].setPosition(tileManager.getTile(6,10));
		slots[6] = new PlanetSlot(tileManager, PlanetView.SATURN);
		slots[6].setPosition(tileManager.getTile(9,12));
		slots[7] = new PlanetSlot(tileManager, PlanetView.URANUS);
		slots[7].setPosition(tileManager.getTile(7,8));
		slots[8] = new PlanetSlot(tileManager, PlanetView.NEPTUNE);
		slots[8].setPosition(tileManager.getTile(11,11));
		
		// SUN slot is initially satisfied because it's already in the proper place
		slots[0].makeSatisfied();
		planets[0].setInteractable(false);
		
		// KEY and DOOR
		door = new DoorController(tileManager, Globals.NORTH);
		door.setPosition(tileManager.getTile(11,3));
		door.setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 4.5f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 8.0f);
		objects.add(door);
		
		key = new KeyController(tileManager, -1);
		key.setPosition(tileManager.getTile(13,6));
		key.setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 4.25f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 5.5f);
		key.setObstructing(false);
		objects.add(key);
		
		// Journal Page
		journalPage = new JournalPageController(tileManager, "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet.");
		journalPage.setPosition(tileManager.getTile(11,6));
		journalPage.setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH * 2.75f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 6.5f);
		journalPage.setObstructing(false);
		objects.add(journalPage);
	}
	
	/**
	 *  Check if all planets have been placed in their proper positions.<br/>
	 *  If so, reveal the key and Galileo's notebook page
	 */
	protected void checkPuzzleComplete() {
		puzzleComplete = true;
		for (PlanetSlot slot : slots) {
			if (!slot.isSatisfied()) {
				puzzleComplete = false;
				break;
			}
		}
		//if (true) {
		if (puzzleComplete) {
			key.setObstructing(true);
			key.setVisible(true);
			key.setInteractable(true);
			journalPage.setObstructing(true);
			journalPage.setVisible(true);
			journalPage.setInteractable(true);
			Assets.planetPuzzleComplete.play();
		}
	}
	
	/**
	 * Check if Robert has just picked up a planet
	 */
	protected void checkCarryingPlanet() {
		if (!robert.isCarrying()) {
			for (PlanetController planet : planets) {
				if (planet.state == PlanetController.IN_HANDS) {
					Assets.planetPickUp.play();
					robert.setCarrying(true);
					robert.setCarryable(planet);
					break;
				}
			}
		}
	}
	
	/**
	 * 1. Determine the planet that Robert is holding.<br/>
	 * 2. Set its position to the tile in front of Robert.<br/>
	 * 3. Do logic to drop the planet (planet-side; player-side; world-side).
	 */
	protected void handleDropPlanet() {
		Tile tmpTile = robert.getTileInFrontOfPlayer();
		if (!tmpTile.isObstructed()) {
			if (robert.isCarrying()) {
				int planetType = -1;
				PlanetController tmpPlanet = null;
				for (PlanetController planet : planets) {
					if (planet.state == PlanetController.IN_HANDS) {
						tmpPlanet = planet;
						planet.setPosition(tileManager.getTile(tmpTile.getX(), tmpTile.getY()));
						switch (robert.getFacing()) {
							case Globals.NORTH:	planet.setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH / 2.0f + tmpPlanet.offsetX, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT / 2.0f);
												break;
							case Globals.EAST:	planet.setScreenPosition(Globals.GAME_WIDTH / 2.0f + Globals.TILE_WIDTH / 2.0f + tmpPlanet.offsetX, Globals.GAME_HEIGHT / 2.0f - Globals.TILE_HEIGHT / 2.0f);
												break;
							case Globals.SOUTH:	planet.setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH / 1.5f + tmpPlanet.offsetX, Globals.GAME_HEIGHT / 2.0f - Globals.TILE_HEIGHT / 2.0f);
												break;
							case Globals.WEST:	planet.setScreenPosition(Globals.GAME_WIDTH / 2.0f - Globals.TILE_WIDTH / 1.5f + tmpPlanet.offsetX, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT / 2.0f);
												break;
						}
						planetType = planet.planet;
						break;
					}
				}
				// Dropped planet - on a slot
				if (isPlanetSlotInFrontOfPlayer()) {
					PlanetSlot tmpSlot = getPlanetSlotInFrontOfPlayer();
					if (tmpSlot.isCorrectPlanet(planetType)) {
						tmpSlot.makeSatisfied();
						if (tmpPlanet != null) tmpPlanet.setInteractable(false);
						Assets.correctPlanetPlacement.play();
					} else {
						Assets.incorrectPlanetPlacement.play();
					}
				// Dropped planet - NOT on a slot
				} else {
					Assets.planetDrop.play();
				}
				robert.getCarryable().dropAction();
				robert.setCarrying(false);
				robert.setCarryable(null);
			}
		} else System.out.println("Already a planet here");
	}
	
	/**
	 * If a planet's state is PlanetController.IN_HANDS (Robert is carrying),
	 * then draw it over Robert's head as if he's carrying it
	 */
	protected void drawPlanetOverPlayer() {
		for (PlanetController planet : planets) {
			if (planet.state == PlanetController.IN_HANDS) {
				planet.setScreenPosition(Globals.GAME_WIDTH / 2.0f - 24 + planet.offsetX, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 1.5f);
				break;
			}
		}
		if (key.state == KeyController.PICKED_UP) {
			key.setScreenPosition(Globals.GAME_WIDTH / 2.0f, Globals.GAME_HEIGHT / 2.0f + Globals.TILE_HEIGHT * 1.75f);
		}
	}
	
	protected boolean isPlanetSlotInFrontOfPlayer() {
		Tile tmp = robert.getTileInFrontOfPlayer();
		for (PlanetSlot slot : slots) {
			if (slot.getPosition().getX() == tmp.getX() && slot.getPosition().getY() == tmp.getY())
				return true;
		}
		return false;
	}
	
	protected PlanetSlot getPlanetSlotInFrontOfPlayer() {
		for (PlanetSlot slot : slots) {
			if (slot.getPosition().getTile().equals(robert.getTileInFrontOfPlayer()))
				return slot;
		}
		return null;
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
		if (robert.getPosition().getTile().equals(tileManager.getTile(11, 3))) {
			readyToLeave = true;
		}
	}
	
	@Override
	public void dispose() {
		assetManager.clear();
		if (nextWorld != Globals.GALILEO2) bgMusic.stop();
		Assets.unload(Globals.GALILEO1);
	}
}
