package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class Tile implements TiledMapTile {
	private static final int TYPE_NULL = 0;
	private static final int TYPE_ICE = 1;
	private static final int TYPE_SAND = 2;
	private static final int TYPE_METAL = 3;
	private static final int TYPE_SPACE = 4;
	private static final int TYPE_WALL = 5;
	protected static int numTiles = 0;
	protected int id;
	protected int type;
	protected int xIndex;
	protected int yIndex;
	protected boolean walkable;
	protected boolean obstructed;
	protected MapProperties properties;
	protected TextureRegion textureRegion;
	
	public Tile(int xIndex, int yIndex) {
		this.id = numTiles++;
		this.type = TYPE_NULL;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	public Tile(TiledMapTile tile) {
		this(0,0);
		this.properties = tile.getProperties();
		this.textureRegion = tile.getTextureRegion();
	}
	
	public int getType() {
		return type;
	}
	
	public int getX() {
		return xIndex;
	}
	
	public void setX(int x) {
		this.xIndex = x;
	}
	
	public int getY() {
		return yIndex;
	}
	
	public void setY(int y) {
		this.yIndex = y;
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	
	public boolean isObstructed() {
		return obstructed;
	}
	
	public void setObstructed(boolean obstructed) {
		this.obstructed = obstructed;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public BlendMode getBlendMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBlendMode(BlendMode blendMode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	@Override
	public MapProperties getProperties() {
		return properties;
	}
	
	public boolean equals(Tile tile) {
		if (this.id == tile.id) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String out = "";
		out += "(" + xIndex + ", " + yIndex + ")";
		return out;
	}
}
