package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The Assets class is where all paths for sounds and textures belong.
 * When you add an asset data field, be sure to add the corresponding path in load()
 * 
 * @author Jason
 */
public class Assets {
	/*********** SPRITES **********/
	// Main Menu
	public static Sprite teamLogo, logo, playBtn;
	
	// The Hub / Common
	public static Sprite robertPortrait;
	public static Sprite schrodingerS, schrodingerE;
	public static Sprite catS, catE;
	public static Sprite greenBoxClosed, greenBoxOpen;
	public static Sprite blueBoxClosed, blueBoxOpen;
	public static Sprite redBoxClosed, redBoxOpen;
	public static Sprite redKey, greenKey, blueKey, silverKey;
	public static Sprite doorW, doorN;
	public static Sprite journalPage, journalPageLarge;
	public static Sprite dialogBoxBG, pauseBG;
	
	// Galileo's World
	public static Sprite SUN, MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE;
	
	// Newton's World
	public static Sprite stalagmite, chunkSmall, chunkMedium, chunkLarge, forceBlock, movingBlock;
	
	/*********** FONT(S) **********/
	public static BitmapFont font;
	public static BitmapFont journalFont;
	
	/******* SOUNDS & MUSIC *******/
	// The Hub / Common
	public static Music menuMusic;
	public static Music theHubMusic;
	public static Sound btnPress;
	public static Sound chestOpen, chestClose;
	public static Sound step;
	public static Sound walkIntoWall;
	
	// Galileo's World
	public static Music galileoMusic;
	public static Sound planetPickUp, planetDrop;
	public static Sound correctPlanetPlacement;
	public static Sound incorrectPlanetPlacement;
	public static Sound planetPuzzleComplete;
	public static Sound unlock;
	
	// Newton's World
	public static Music newtonMusic;
	public static Sound slide;
	
	public static void load() {
		// FONT(S)
		font = loadFont("data/fonts/8bit.fnt", "data/fonts/8bit2.png", false);
		//journalFont = loadFont("data/fonts/fely.fnt", "data/fonts/fely.png", false);
		
		// IMAGES
		teamLogo = load("data/images/BoxBrigadeLogo.png", 512, 512);
		logo = load("data/images/logo.png", 1024, 768);
		playBtn = load("data/images/PlayButton.png", 256, 64);
		robertPortrait = load("data/images/robert.png", 124, 0, 68, 128, false);
		schrodingerS = load("data/images/schrodinger.png", 0, 0, 80, 128, false);
		schrodingerE = load("data/images/schrodinger.png", 80, 0, 80, 128, false);
		catS = load("data/images/cat.png", 128, 0, 128, 150, false);
		catE = load("data/images/cat.png", 0, 0, 128, 150, false);
		greenBoxClosed = load("data/images/boxes2.png", 214, 0, 107, 96, false);
		greenBoxOpen = load("data/images/boxes_open.png", 128, 128, 128, 128, false);
		redBoxClosed = load("data/images/boxes2.png", 107, 192, 107, 96, false);
		redBoxOpen = load("data/images/boxes_open.png", 256, 128, 128, 128, false);
		blueBoxClosed = load("data/images/boxes2.png", 321, 288, 107, 96, false);
		blueBoxOpen = load("data/images/boxes_open.png", 384, 0, 128, 128, false);
		silverKey = load("data/images/keys.png", 0, 0, 26, 57, false);
		redKey = load("data/images/keys.png", 26, 0, 26, 57, false);
		greenKey = load("data/images/keys.png", 52, 0, 26, 57, false);
		blueKey = load("data/images/keys.png", 78, 0, 26, 57, false);
		doorW = load("data/images/doors.png", 0, 0, 70, 128, false);
		doorN = load("data/images/doors.png", 70, 0, 70, 128, false);
		journalPage = load("data/images/journal_page.png", 0, 0, 128, 128, false);
		journalPageLarge = load("data/images/journal_page_large.png", 0, 0, 512, 512, false);
		dialogBoxBG = load("data/images/dialog_bg.png", 0, 0, 1024, 256, false);
		pauseBG = load("data/images/pause_bg.png", 0, 0, 1024, 768, false);
		
		// SOUND FX
		btnPress = loadSound("data/sounds/Button noise.mp3");
		chestOpen = loadSound("data/sounds/chest open.mp3");
		chestClose = loadSound("data/sounds/chest close.wav");
		walkIntoWall = loadSound("data/sounds/Robert run into.mp3");
		step = loadSound("data/sounds/Robert Step.mp3");
		unlock = loadSound("data/sounds/DOOR-CHEST UNLOCK.mp3");
		planetPuzzleComplete = loadSound("data/sounds/Puzzle Complete.mp3");
		
		// MUSIC
		menuMusic = loadMusic("data/sounds/Main theme music.mp3");
		theHubMusic = loadMusic("data/sounds/Hub world theme.mp3");
	}
	
	public static void load(int world) {
		switch (world) {
			case Globals.GALILEO1: 	SUN = load("data/images/planets.png", 612, 0, 96, 96, false);
									MERCURY = load("data/images/planets.png", 288, 0, 96, 96, false);
									VENUS = load("data/images/planets.png", 869, 0, 96, 96, false);
									EARTH = load("data/images/planets.png", 0, 0, 96, 96, false);
									MARS = load("data/images/planets.png", 192, 0, 96, 96, false);
									JUPITER = load("data/images/planets.png", 96, 0, 96, 96, false);
									SATURN = load("data/images/planets.png", 480, 0, 132, 96, false);
									URANUS = load("data/images/planets.png", 708, 0, 161, 96, false);
									NEPTUNE = load("data/images/planets.png", 384, 0, 96, 96, false);
									galileoMusic = loadMusic("data/sounds/Galileo World Theme.mp3");
									planetPickUp = loadSound("data/sounds/pick up.mp3");
									planetDrop = loadSound("data/sounds/drop.mp3");
									correctPlanetPlacement = loadSound("data/sounds/Correct placement.mp3");
									incorrectPlanetPlacement = loadSound("data/sounds/Wrong placement sound.mp3");
									planetPuzzleComplete = loadSound("data/sounds/Puzzle Complete.mp3");
									break;
			case Globals.NEWTON1:	stalagmite = load("data/images/stalagmite.png", 0, 0, 128, 128, false);
									newtonMusic = loadMusic("data/sounds/newton world theme.mp3");
									slide = loadSound("data/sounds/Robert Slide.mp3");
									break;
			case Globals.NEWTON2:	chunkSmall = load("data/images/newtonChunks.png", 256, 0, 128, 128, false);
									chunkMedium = load("data/images/newtonChunks.png", 128, 0, 128, 128, false);
									chunkLarge = load("data/images/newtonChunks.png", 0, 0, 128, 128, false);
									if (newtonMusic == null) newtonMusic = loadMusic("data/sounds/newton world theme.mp3");
									if (slide == null) slide = loadSound("data/sounds/Robert Slide.mp3");
									break;
		}
	}
	
	public static void unload(int world) {
		switch (world) {
		case Globals.GALILEO1: 	SUN = null; MERCURY = null; VENUS = null; EARTH = null;
								MARS = null; JUPITER = null; SATURN = null; URANUS = null; NEPTUNE = null;
								planetPickUp = null; planetDrop = null;
								correctPlanetPlacement = null; incorrectPlanetPlacement = null;
								planetPuzzleComplete = null;
								break;
		case Globals.NEWTON1:	stalagmite = null;
								break;
		case Globals.NEWTON2:	chunkSmall = null;
								chunkMedium = null;
								chunkLarge = null;
								break;
	}
	}
	
	public static Sprite load(String name, int width, int height) {
		return load(name, 0, 0, width, height, true);
	}
	
	public static Sprite load(String name, int x, int y, int width, int height, boolean flipY) {
		Texture texture = new Texture(Gdx.files.internal(name));
		Sprite region = new Sprite(texture, x, y, width, height);
		if (flipY) region.flip(false, true);
		return region;
	}
	
	public static BitmapFont loadFont(String fontFile, String imageFile, boolean flipY) {
		FileHandle fontHandle = Gdx.files.internal(fontFile);
		FileHandle imageHandle = Gdx.files.internal(imageFile);
		return new BitmapFont(fontHandle, imageHandle, flipY);
	}
	
	public static Sound loadSound(String soundFile) {
		FileHandle soundHandle = Gdx.files.internal(soundFile);
		return Gdx.audio.newSound(soundHandle);
	}
	
	public static Music loadMusic(String musicFile) {
		FileHandle musicHandle = Gdx.files.internal(musicFile);
		Music out = Gdx.audio.newMusic(musicHandle);
		out.setVolume(0.2f);
		return out;
	}
}
