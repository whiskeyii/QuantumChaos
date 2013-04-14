package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;

public class KeyView extends View {
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int SILVER = 3;
	protected Sprite key;
	
	public KeyView(int color) {
		switch (color) {
			case RED: 		key = Assets.redKey; break;
			case GREEN: 	key = Assets.greenKey; break;
			case BLUE: 		key = Assets.blueKey; break;
			case SILVER:	key = Assets.silverKey; break;
			default:		key = Assets.silverKey; break;
		}
	}
	
	@Override
	public void update(int state) {
		update(0, 0, state);
	}
	
	public void update(float x, float y, int state) {
		draw(key, x, y);
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}
}
