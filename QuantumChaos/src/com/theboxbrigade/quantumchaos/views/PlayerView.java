package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.general.AnimatedAssets;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

/**
 * View object for Player (Robert) - Handles the states that Robert
 *   is currently in and draws the Sprite in the SpriteBatch.
 * @author Jason
 */
public class PlayerView extends CharacterView {
	private static final int CARRY = 5;
	private static final int TELEPORT = 6;
	private static final int CELEBRATE = 7;
	private int currentState;
	private Sprite player;
	private SpriteBatch spriteBatch = new SpriteBatch();
	
	@Override
	public void update(int state) {
		// TODO Auto-generated method stub
		this.currentState = state;
		AnimatedAssets.setState(state);
		player = AnimatedAssets.robertCurrentFrame;
		draw(player, Globals.GAME_WIDTH/2, Globals.GAME_HEIGHT/2);
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}
}
