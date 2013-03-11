package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.theboxbrigade.quantumchaos.general.Assets;

public class AnimatedPlayerView {
	private static final int WALKING = 0;
	
	private SpriteBatch spriteBatch = new SpriteBatch();
	
	private int currentState;
	private TextureRegion currentFrame;
	
	public void updateAnimation(int state, int facingDir) {
		this.currentState = state;
		if (currentState == WALKING) {
			switch (facingDir) {
				case 0: currentFrame = Assets.robertFaceN;
						break;
				case 1: currentFrame = Assets.robertFaceE;
						break;
				case 2: currentFrame = Assets.robertFaceS;
						break;
				case 3: currentFrame = Assets.robertFaceW;
						break;
			}
		}
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}
	
	private void draw(TextureRegion region, int x, int y) {
		spriteBatch.draw(region, x, y);
	}
}
