package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;

public class StalagmiteView extends View {
	protected Sprite stalagmite = Assets.stalagmite;
	
	@Override
	public void update(int state) {
		update(state, 0, 0);	
	}
	
	public void update(int state, float x, float y) {
		draw(stalagmite, x, y);
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}

}
