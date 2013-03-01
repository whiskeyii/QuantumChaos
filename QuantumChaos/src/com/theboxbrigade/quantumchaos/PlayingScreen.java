package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayingScreen extends Screen {
	SpriteBatch spriteBatch;
	World world;
	
	public PlayingScreen() {
		world = new TheHub();
		spriteBatch = world.getSpriteBatch();
	}
	
	@Override
	public void render() {
		world.render();
	}

	@Override
	public void tick(Input input) {
		if ((input.buttons[Input.WALK_NORTH] && !input.oldButtons[Input.WALK_NORTH])
			|| (input.buttons[Input.WALK_EAST] && !input.oldButtons[Input.WALK_EAST])) {
			System.out.println("PRESSED NORTH OR EAST");
			input.releaseAllKeys();
		}
	}
}
