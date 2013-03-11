package com.theboxbrigade.quantumchaos;

public class Position {
	protected Tile tile;
	protected int screenX, screenY;
	
	public Position(Tile tile) {
		this.tile = tile;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public int getX() {
		return tile.getX();
	}
	
	public int getY() {
		return tile.getY();
	}
}
