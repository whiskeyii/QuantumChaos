package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedAssets {
	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 1;
	
	private static final int FRAME_WIDTH = 64;
	private static final int FRAME_HEIGHT = 128;
	public static int numWalkingFrames = 5;
	public static int numTeleportingFrames = 4;
	private static final String robertPath = "data/images/robert_walk.png";
	private static Sprite[] robertWalkNorthFrames;
	private static Sprite[] robertWalkWestFrames;
	private static Sprite[] robertWalkEastFrames;
	private static Sprite[] robertWalkSouthFrames;
	private static Sprite[] robertTeleportFrames;
	public static int robertCurrentFrameNum = -1;
	public static Sprite robertCurrentFrame;

	public static void load() {
		robertWalkNorthFrames = new Sprite[numWalkingFrames];
		robertWalkWestFrames = new Sprite[numWalkingFrames];
		robertWalkEastFrames = new Sprite[numWalkingFrames];
		robertWalkSouthFrames = new Sprite[numWalkingFrames];
		robertTeleportFrames = new Sprite[numTeleportingFrames];
		for (int i = 0; i < numWalkingFrames; i++) {
			robertWalkNorthFrames[i] = load(robertPath, i*FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkWestFrames[i] = load(robertPath, i*FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkEastFrames[i] = load(robertPath, i*FRAME_WIDTH, 2*FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkSouthFrames[i] = load(robertPath, i*FRAME_WIDTH, 3*FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
		}
		robertTeleportFrames[0] = robertWalkNorthFrames[0];
		robertTeleportFrames[1] = robertWalkEastFrames[0];
		robertTeleportFrames[2] = robertWalkSouthFrames[0];
		robertTeleportFrames[3] = robertWalkWestFrames[0];
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
	
	private static void setCurrentFrame(int state) {
		switch (state) {
			// NORTH
			case 0: robertCurrentFrame = robertWalkNorthFrames[robertCurrentFrameNum];
					break;
			// EAST
			case 1: robertCurrentFrame = robertWalkEastFrames[robertCurrentFrameNum];
					break;
			// SOUTH
			case 2: robertCurrentFrame = robertWalkSouthFrames[robertCurrentFrameNum];
					break;
			// WEST
			case 3: robertCurrentFrame = robertWalkWestFrames[robertCurrentFrameNum];
					break;
			// TELEPORT
			case 4: robertCurrentFrame = robertTeleportFrames[robertCurrentFrameNum];
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
