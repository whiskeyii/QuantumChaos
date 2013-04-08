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
	public static Sprite logo;
	public static Sprite robertFaceN, robertFaceE, robertFaceS, robertFaceW;
	public static Sprite robertWalkN, robertWalkE, robertWalkS, robertWalkW;
	public static Sprite schrodingerS, schrodingerE;
	public static Sprite greenBoxClosed, greenBoxOpen;
	public static Sprite blueBoxClosed, blueBoxOpen;
	public static Sprite redBoxClosed, redBoxOpen;
	public static Sprite redKey, greenKey, blueKey, silverKey;
	public static Sprite doorW, doorN;
	
	public static Sprite dialogBoxBG;
	
	// planets
	public static Sprite SUN, MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE;
	
	public static BitmapFont font;
	
	/*********************/
	public static Sound chestOpen;
	public static Sound step;
	public static Sound walkIntoWall;
	public static Music theHubMusic;
	public static Music galileoMusic;
	public static Sound planetPickUp, planetDrop;
	public static Sound correctPlanetPlacement;
	public static Sound incorrectPlanetPlacement;
	public static Sound planetPuzzleComplete;
	public static Sound unlock;
	
	public static void load() {
		// FONT(S)
		font = loadFont("data/fonts/8bit.fnt", "data/fonts/8bit.png", false);
		
		// IMAGES
		logo = load("data/images/logo.png", 1024, 768);
		
		robertFaceN = load("data/images/robert.png", 0, 0, 65, 128, false);
		robertFaceW = load("data/images/robert.png", 65, 0, 65, 128, false);
		robertFaceE = load("data/images/robert.png", 130, 0, 65, 128, false);
		robertFaceS = load("data/images/robert.png", 195, 0, 65, 128, false);
		robertWalkN = load("data/images/robert.png", 0, 128, 65, 128, false);
		robertWalkW = load("data/images/robert.png", 65, 128, 65, 128, false);
		robertWalkE = load("data/images/robert.png", 130, 128, 65, 128, false);
		robertWalkS = load("data/images/robert.png", 195, 128, 65, 128, false);
		
		schrodingerS = load("data/images/schrodinger.png", 0, 0, 80, 128, false);
		schrodingerE = load("data/images/schrodinger.png", 80, 0, 80, 128, false);

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
		
		
		dialogBoxBG = load("data/images/dialog_bg.png", 0, 0, 1024, 256, false);
		
		// SOUNDS
		chestOpen = loadSound("data/sounds/chest open.mp3");
		walkIntoWall = loadSound("data/sounds/robert run into.mp3");
		step = loadSound("data/sounds/robert step.mp3");
		unlock = loadSound("data/sounds/DOOR-CHEST UNLOCK.mp3");
		
		// MUSIC
		theHubMusic = loadMusic("data/sounds/hub world theme.mp3");
		galileoMusic = loadMusic("data/sounds/Galileo World Theme.mp3");
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
									planetPickUp = loadSound("data/sounds/pick up.mp3");
									planetDrop = loadSound("data/sounds/drop.mp3");
									correctPlanetPlacement = loadSound("data/sounds/Correct placement.mp3");
									incorrectPlanetPlacement = loadSound("data/sounds/Wrong placement sound.mp3");
									planetPuzzleComplete = loadSound("data/sounds/Puzzle Complete.mp3");
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
		out.setVolume(0.4f);
		return out;
	}
}
