package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door {
	protected SpriteBatch spriteBatch = new SpriteBatch();
	protected Sprite door;
	protected float x, y;
	protected boolean locked = true;
	protected boolean open = false;
	
	public void setLocked(boolean locked) { this.locked = locked; }
	public boolean isLocked() { return locked; }
	
	public void toggleOpen() { open = !open; }
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void setScreenPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		// TODO: Change sprite to open if open, closed if closed
		draw(door, x, y);
	}
	
	protected void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}
}
