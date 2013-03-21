package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class View {
	public SpriteBatch spriteBatch = new SpriteBatch();
	
	public abstract void update(int state);
	
	public abstract void updateAnimation(int state);
	
	public abstract void draw(Sprite region, float x, float y);
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}
