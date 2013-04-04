package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;

public class Globals {
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 768;
	
	public static final int TILE_WIDTH = 128;
	public static final int TILE_HEIGHT = 64;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int INTERACT = 4;
	public static final int TELEPORT = 5;
	
	public static final float DELTA = 0.066f;
	
	public static float OBJ_TRANSLATION_X = Globals.TILE_WIDTH / 1.75f;
	public static float OBJ_TRANSLATION_Y = Globals.TILE_HEIGHT / 1.75f;
	
	public static final int THE_HUB = 100;
	public static final int GALILEO1 = 101;
	public static final int GALILEO2 = 102;
	public static final int GALILEO3 = 103;
	public static final int NEWTON1 = 104;
	public static final int NEWTON2 = 105;
	public static final int NEWTON3 = 106;
	public static final int NEWTON4 = 107;
}
