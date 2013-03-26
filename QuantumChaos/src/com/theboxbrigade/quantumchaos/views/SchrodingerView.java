package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

public class SchrodingerView extends CharacterView {
	private int currentState;
	private Sprite schrodinger;
	private SpriteBatch spriteBatch = new SpriteBatch();
	
	@Override
	public void update(int state) {
		this.currentState = state;
		if (state == 1) { // EAST
			schrodinger = Assets.schrodingerE;
		} else if (state == 2) { // SOUTH
			schrodinger = Assets.schrodingerS;
		}
		draw(schrodinger, Globals.GAME_WIDTH/4, Globals.GAME_HEIGHT/2);
	}
	
	public void update(float x, float y, int facingDir) {
		if (facingDir == 1) { // EAST
			schrodinger = Assets.schrodingerE;
		} else if (facingDir == 2) { // SOUTH
			schrodinger = Assets.schrodingerS;
		}
		draw(schrodinger, x, y);
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}
}
