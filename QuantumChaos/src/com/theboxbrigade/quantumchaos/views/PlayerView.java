package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.AnimatedAssets;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

/**
 * View object for Player (Robert) - Handles the states that Robert
 *   is currently in and draws the Sprite in the SpriteBatch.
 * @author Jason
 */
public class PlayerView extends CharacterView {
	private int state;
	private Sprite player;
	private SpriteBatch spriteBatch = new SpriteBatch();
	
	@Override
	public void update(int state) {
		this.state = state;
		AnimatedAssets.setState(state);
		player = AnimatedAssets.robertCurrentFrame;
		draw(player, Globals.GAME_WIDTH/2, Globals.GAME_HEIGHT/2);
	}
	
	public void update(int state, int facingDir, int stateFrame) {
		this.state = state;
		switch (state) {
			case PlayerController.IDLE:			player = AnimatedAssets.getFrame(facingDir, 0);
												break;
			case PlayerController.WALKING:		player = AnimatedAssets.getFrame(facingDir, stateFrame);
												break;
			case PlayerController.INTERACTING:	player = AnimatedAssets.getFrame(facingDir, 0);
												break;
			case PlayerController.SLIDING:		player = AnimatedAssets.getFrame(facingDir, 0);
												break;
			case PlayerController.INIT_CARRYING:player = AnimatedAssets.getFrame(state, facingDir, stateFrame);
												break;
			default:							player = AnimatedAssets.getFrame(state, stateFrame);
												break;
		}
		draw(player, Globals.GAME_WIDTH/2, Globals.GAME_HEIGHT/2);
	}
	
	public void update(int state, int facingDir, int stateFrame, boolean carrying) {
		this.state = state;
		switch (state) {
			case PlayerController.IDLE:			if (carrying)
													player = AnimatedAssets.getFrame(PlayerController.CARRYING, facingDir, 0);
												else
													player = AnimatedAssets.getFrame(facingDir, 0);
												break;
			case PlayerController.WALKING:		if (carrying)
													player = AnimatedAssets.getFrame(PlayerController.CARRYING, facingDir, stateFrame);
												else
													player = AnimatedAssets.getFrame(PlayerController.WALKING, facingDir, stateFrame);
												break;
			case PlayerController.INTERACTING:	player = AnimatedAssets.getFrame(facingDir, 0);
												break;
			case PlayerController.SLIDING:		player = AnimatedAssets.getFrame(facingDir, 0);
												break;
			case PlayerController.INIT_CARRYING:player = AnimatedAssets.getFrame(state, facingDir, stateFrame);
												break;
			default:							player = AnimatedAssets.getFrame(state, stateFrame);
												break;
		}
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
