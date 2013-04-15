package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.math.Matrix4;

public class Position {
	protected final int MAX_X;
	protected final int MAX_Y;
	protected TileManager tileManager;
	protected Tile tile;
	protected Matrix4 position;
	
	public Position(TileManager tileManager) {
		this.tileManager = tileManager;
		MAX_X = tileManager.getLayer(0).getWidth()-2;
		MAX_Y = tileManager.getLayer(0).getHeight()-2;
		tile = tileManager.getTile(MAX_X, MAX_Y);
		position = new Matrix4();
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public boolean shiftHorizontallyBy(int hShift, boolean checkForWalkable) {
		int newX = getX() + hShift;
		if (newX >= 0 && newX < tileManager.getLayer(0).getWidth()) {
			Tile tmpTile = tileManager.getTile(newX,getY());
			if (tmpTile.isObstructed()) return false;
			if (checkForWalkable) {
				if (tmpTile.isWalkable())
					if (tile.isObstructed()) {
						tile.setObstructed(false);
						tile = tmpTile;
						tile.setObstructed(true);
					} else {
						tile = tmpTile;
					}
				else { return false; }
			}
		} else { return false; }
		return true;
	}
	
	public boolean shiftVerticallyBy(int vShift, boolean checkForWalkable) {
		int newY = getY() + vShift;
		if (newY >= 0 && newY < tileManager.getLayer(0).getHeight()) {
			Tile tmpTile = tileManager.getTile(getX(),newY);
			if (tmpTile.isObstructed()) return false;
			if (checkForWalkable) {
				if (tmpTile.isWalkable())
					if (tile.isObstructed()) {
						tile.setObstructed(false);
						tile = tmpTile;
						tile.setObstructed(true);
					} else {
						tile = tmpTile;
					}
				else { return false; }
			}
		} else { return false; }
		return true;
	}
	
	public int getX() {
		return tile.getX();
	}
	
	public int getY() {
		return tile.getY();
	}
	
	public String toString() {
		return tile.toString();
	}
}
