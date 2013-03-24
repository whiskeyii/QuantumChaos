package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;
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
	public static Sprite test; //test commit
	
	public static Sprite dialogBoxBG;
	
	public static BitmapFont font;
	
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
		
		dialogBoxBG = load("data/images/dialog_bg.png", 0, 0, 1024, 256, false);
		
		// SOUNDS
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
	
	public class Dialog {
		public static final String TALK_TO_SCHRODINGER_HUB_1 = "Hello Robert! How are you today?";
	}
}
