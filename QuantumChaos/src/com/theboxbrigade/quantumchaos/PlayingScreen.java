package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Input;

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
		world.parseInput(input);
	}
}
