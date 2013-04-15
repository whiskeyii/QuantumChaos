package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.theboxbrigade.quantumchaos.controllers.Interactable;

public class Tile implements TiledMapTile {
	protected static int numTiles = 0;
	protected int id;
	protected int xIndex;
	protected int yIndex;
	protected boolean walkable;
	protected boolean obstructed;
	protected boolean slippery;
	protected Obstructing obstructing;
	protected MapProperties properties;
	protected TextureRegion textureRegion;
	
	public Tile(int xIndex, int yIndex) {
		this.id = numTiles++;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	public Tile(TiledMapTile tile) {
		this(0,0);
		this.properties = tile.getProperties();
		this.textureRegion = tile.getTextureRegion();
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
	
	public boolean isSlippery() {
		return slippery;
	}
	
	public void setSlippery(boolean slippery) {
		this.slippery = slippery;
	}
	
	public void setObstructing(Obstructing obs) {
		this.obstructing = obs;
	}
	
	public Obstructing getObstructing() {
		return obstructing;
	}
	
	public boolean isObstructingInteractable() {
		return ((Interactable)obstructing).isInteractable();
	}

	public int getId() {
		return id;
	}
	
	@Override
	public BlendMode getBlendMode() {
		return null;
	}

	@Override
	public void setBlendMode(BlendMode blendMode) {
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
		if (tile != null) {
			if (this.id == tile.id) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String out = "";
		out += "(" + xIndex + ", " + yIndex + ")";
		return out;
	}
}
