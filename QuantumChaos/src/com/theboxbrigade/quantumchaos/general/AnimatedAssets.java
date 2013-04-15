package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;

public class AnimatedAssets {
	private static final int FRAME_WIDTH = 64;
	private static final int FRAME_HEIGHT = 128;
	private static final int PICKUP_Y_OFF = 512;
	private static final int CARRY_X_OFF = 320;
	public static int numWalkingFrames = 5;
	public static int numPickUpFrames = 3;
	private static final String robertPath = "data/images/robert_walk.png";
	private static Sprite[] robertWalkNorthFrames, robertWalkEastFrames, robertWalkSouthFrames, robertWalkWestFrames;
	private static Sprite[] pickUpNorthFrames, pickUpEastFrames, pickUpSouthFrames, pickUpWestFrames;
	private static Sprite[] carryNorthFrames, carryEastFrames, carrySouthFrames, carryWestFrames;
	public static int robertCurrentFrameNum = -1;
	public static Sprite robertCurrentFrame;

	public static void load() {
		robertWalkNorthFrames = new Sprite[numWalkingFrames];
		robertWalkWestFrames = new Sprite[numWalkingFrames];
		robertWalkEastFrames = new Sprite[numWalkingFrames];
		robertWalkSouthFrames = new Sprite[numWalkingFrames];
		pickUpNorthFrames = new Sprite[numPickUpFrames];
		pickUpEastFrames = new Sprite[numPickUpFrames];
		pickUpSouthFrames = new Sprite[numPickUpFrames];
		pickUpWestFrames = new Sprite[numPickUpFrames];
		carryNorthFrames = new Sprite[numWalkingFrames];
		carryEastFrames = new Sprite[numWalkingFrames];
		carrySouthFrames = new Sprite[numWalkingFrames];
		carryWestFrames = new Sprite[numWalkingFrames];
		for (int i = 0; i < numWalkingFrames; i++) {
			robertWalkNorthFrames[i] = load(robertPath, i*FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkWestFrames[i] = load(robertPath, i*FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkEastFrames[i] = load(robertPath, i*FRAME_WIDTH, 2*FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
			robertWalkSouthFrames[i] = load(robertPath, i*FRAME_WIDTH, 3*FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT, false);
			carryNorthFrames[i] = load(robertPath, CARRY_X_OFF + i*71, 0, 71, FRAME_HEIGHT, false);
			carryWestFrames[i] = load(robertPath, CARRY_X_OFF + i*71, FRAME_HEIGHT, 71, FRAME_HEIGHT, false);
			carryEastFrames[i] = load(robertPath, CARRY_X_OFF + i*71, 2*FRAME_HEIGHT, 71, FRAME_HEIGHT, false);
			carrySouthFrames[i] = load(robertPath, CARRY_X_OFF + i*71, 3*FRAME_HEIGHT, 71, FRAME_HEIGHT, false);
		}
		for (int i=0; i < numPickUpFrames; i++) {
			pickUpNorthFrames[i] = load(robertPath, i*71, PICKUP_Y_OFF, 71, FRAME_HEIGHT, false);
			pickUpEastFrames[i] = load(robertPath, i*71, PICKUP_Y_OFF + FRAME_HEIGHT, 71, FRAME_HEIGHT, false);
			pickUpSouthFrames[i] = load(robertPath, i*71, PICKUP_Y_OFF + 2*FRAME_HEIGHT, 71, FRAME_HEIGHT, false);
			pickUpWestFrames[i] = load(robertPath, i*71, PICKUP_Y_OFF + 3*FRAME_HEIGHT, 71, FRAME_HEIGHT, false);
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
	
	public static Sprite getFrame(int state, int facing, int frame) {
		if (state == PlayerController.WALKING) {
			switch (facing) {
				case Globals.NORTH: 		frame %= numWalkingFrames;	
											return robertWalkNorthFrames[frame];
				case Globals.EAST:			frame %= numWalkingFrames;
											return robertWalkEastFrames[frame];
				case Globals.SOUTH:			frame %= numWalkingFrames;
											return robertWalkSouthFrames[frame];
				case Globals.WEST:			frame %= numWalkingFrames;
											return robertWalkWestFrames[frame];
			}
		} else if (state == PlayerController.INIT_CARRYING) {
			switch (facing) {
				case Globals.NORTH: 		frame %= numPickUpFrames;	
											return pickUpNorthFrames[frame];
				case Globals.EAST:			frame %= numPickUpFrames;
											return pickUpEastFrames[frame];
				case Globals.SOUTH:			frame %= numPickUpFrames;
											return pickUpSouthFrames[frame];
				case Globals.WEST:			frame %= numPickUpFrames;
											return pickUpWestFrames[frame];
			}
		} else if (state == PlayerController.CARRYING) {
			switch (facing) {
				case Globals.NORTH: 		frame %= numWalkingFrames;	
											return carryNorthFrames[frame];
				case Globals.EAST:			frame %= numWalkingFrames;
											return carryEastFrames[frame];
				case Globals.SOUTH:			frame %= numWalkingFrames;
											return carrySouthFrames[frame];
				case Globals.WEST:			frame %= numWalkingFrames;
											return carryWestFrames[frame];
			}
		}
		return robertCurrentFrame;
	}
	
	private static void setCurrentFrame(int state) {
		switch (state) {
			case Globals.NORTH: 	robertCurrentFrame = robertWalkNorthFrames[robertCurrentFrameNum];
									break;
			case Globals.EAST: 		robertCurrentFrame = robertWalkEastFrames[robertCurrentFrameNum];
									break;
			case Globals.SOUTH: 	robertCurrentFrame = robertWalkSouthFrames[robertCurrentFrameNum];
									break;
			case Globals.WEST: 		robertCurrentFrame = robertWalkWestFrames[robertCurrentFrameNum];
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
