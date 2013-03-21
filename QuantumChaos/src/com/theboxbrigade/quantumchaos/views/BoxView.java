package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;

public class BoxView extends View {
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	protected static final int OPEN = 0;
	protected static final int CLOSED = 1;
	protected int color;
	protected int currentState;
	protected Sprite box;
	protected SpriteBatch spriteBatch = new SpriteBatch();
	
	public BoxView(int color) {
		this.color = color;
	}
	
	@Override
	public void update(int state) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(float x, float y, int state) {
		switch (color) {
			case RED:	if (state == OPEN) box = Assets.redBoxOpen;
						else box = Assets.redBoxClosed;
						break;
			case GREEN:	if (state == OPEN) box = Assets.greenBoxOpen;
						else box = Assets.greenBoxClosed;
						break;
			case BLUE: 	if (state == OPEN) box = Assets.blueBoxOpen;
						else box = Assets.blueBoxClosed;
						break;
		}
		draw(box, x, y);
	}
	
	@Override
	public void updateAnimation(int state) {
		// TODO Auto-generated method stub
		
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}
}
