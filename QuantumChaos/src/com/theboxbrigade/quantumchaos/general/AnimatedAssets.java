package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;

public class AnimatedAssets {
	private static final int FRAME_WIDTH = 64;
	private static final int FRAME_HEIGHT = 128;
	public static int numWalkingFrames = 5;
	public static int numTeleportingFrames = 4;
	private static final String robertPath = "data/images/robert_walk.png";
	private static Sprite[] robertWalkNorthFrames;
	private static Sprite[] robertWalkWestFrames;
	private static Sprite[] robertWalkEastFrames;
	private static Sprite[] robertWalkSouthFrames;
	public static int robertCurrentFrameNum = -1;
	public static Sprite robertCurrentFrame;

	public static void load() {
		robertWalkNorthFrames = new Sprite[numWalkingFrames];
		robertWalkWestFrames = new Sprite[numWalkingFrames];
		robertWalkEastFrames = new Sprite[numWalkingFrames];
		robertWalkSouthFrames = new Sprite[numWalkingFrames];
		for (int i = 0; i < numWalkingFrames; i++) {
			robertWalkNorthFrames[i] = load(robertPath, i*FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkWestFrames[i] = load(robertPath, i*FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkEastFrames[i] = load(robertPath, i*FRAME_WIDTH, 2*FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkSouthFrames[i] = load(robertPath, i*FRAME_WIDTH, 3*FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
		}
		robertCurrentFrameNum = 0;
		robertCurrentFrame = robertWalkNorthFrames[robertCurrentFrameNum];
	}
	
	public static void setState(int state) {
		setCurrentFrame(state);
	}
	
	public static void advanceCurrentFrame(int state) {
		if (++robertCurrentFrameNum >= numWalkingFrames) {
			robertCurrentFrameNum = 0;
		}
		setCurrentFrame(state);
	}
	
	public static Sprite getFrame(int state, int frame) {
		switch (state) {
			case Globals.NORTH: 		frame %= numWalkingFrames;	
										return robertWalkNorthFrames[frame];
			case Globals.EAST:			frame %= numWalkingFrames;
										return robertWalkEastFrames[frame];
			case Globals.SOUTH:			frame %= numWalkingFrames;
										return robertWalkSouthFrames[frame];
			case Globals.WEST:			frame %= numWalkingFrames;
										return robertWalkWestFrames[frame];
		}
		return robertCurrentFrame;
	}
	
	private static void setCurrentFrame(int state) {
		switch (state) {
			case Globals.NORTH: robertCurrentFrame = robertWalkNorthFrames[robertCurrentFrameNum];
								break;
			case Globals.EAST: 	robertCurrentFrame = robertWalkEastFrames[robertCurrentFrameNum];
								break;
			case Globals.SOUTH: robertCurrentFrame = robertWalkSouthFrames[robertCurrentFrameNum];
								break;
			case Globals.WEST: 	robertCurrentFrame = robertWalkWestFrames[robertCurrentFrameNum];
								break;
		}
	}
	
	public static Sprite load(String name, int x, int y, int width, int height, boolean flipY) {
		Texture texture = new Texture(Gdx.files.internal(name));
		Sprite region = new Sprite(texture, x, y, width, height);
		if (flipY) region.flip(false, true);
		return region;
	}
}
