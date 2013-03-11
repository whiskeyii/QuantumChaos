package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

public class PlayerView extends CharacterView {
	private static final int CARRY = 5;
	private static final int TELEPORT = 6;
	private static final int CELEBRATE = 7;
	private int currentState;
	private TextureRegion player;
	private SpriteBatch spriteBatch = new SpriteBatch();
	
	@Override
	public void update(int state, int facingDir) {
		// TODO Auto-generated method stub
		this.currentState = state;
		switch (facingDir) {
			case 0: player = Assets.robertFaceN;
					break;
			case 1: player = Assets.robertFaceE;
					break;
			case 2: player = Assets.robertFaceS;
					break;
			case 3: player = Assets.robertFaceW;
					break;
		}
		draw(player, Globals.GAME_WIDTH/2, Globals.GAME_HEIGHT/2);
	}
	@Override
	public void updateAnimation(int state, int facingDir) {
		// TODO Auto-generated method stub
		this.currentState = state;
	}
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	private void draw(TextureRegion region, int x, int y) {
		spriteBatch.draw(region, x, y);
	}
}
