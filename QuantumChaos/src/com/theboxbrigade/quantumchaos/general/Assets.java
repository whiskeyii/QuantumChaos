package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Assets class is where all paths for sounds and textures belong.
 * When you add an asset data field, be sure to add the corresponding path in load()
 * 
 * @author Jason
 */
public class Assets {
	public static TextureRegion logo;
	public static TextureRegion robertFaceN, robertFaceE, robertFaceS, robertFaceW;
	public static TextureRegion robertWalkN, robertWalkE, robertWalkS, robertWalkW;
	
	public static void load() {
		logo = load("data/images/logo.png", 1024, 768);
		
		robertFaceN = load("data/images/robert.png", 0, 0, 65, 128, false);
		robertFaceW = load("data/images/robert.png", 65, 0, 65, 128, false);
		robertFaceE = load("data/images/robert.png", 130, 0, 65, 128, false);
		robertFaceS = load("data/images/robert.png", 195, 0, 65, 128, false);
		robertWalkN = load("data/images/robert.png", 0, 128, 65, 128, false);
		robertWalkW = load("data/images/robert.png", 65, 128, 65, 128, false);
		robertWalkE = load("data/images/robert.png", 130, 128, 65, 128, false);
		robertWalkS = load("data/images/robert.png", 195, 128, 65, 128, false);
	}
	
	public static TextureRegion load(String name, int width, int height) {
		return load(name, 0, 0, width, height, true);
	}
	
	public static TextureRegion load(String name, int x, int y, int width, int height, boolean flipY) {
		Texture texture = new Texture(Gdx.files.internal(name));
		TextureRegion region = new TextureRegion(texture, x, y, width, height);
		if (flipY) region.flip(false, true);
		return region;
	}
}
