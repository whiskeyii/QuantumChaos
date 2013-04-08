package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class PlayingScreen extends Screen {
	SpriteBatch spriteBatch;
	World world;
	
	public PlayingScreen() {
		world = new Galileo1();
		spriteBatch = world.getSpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		world.render(delta);
	}

	@Override
	public void tick(Input input) {
		world.parseInput(input);
		
		if (world.readyToLeave) {
			changeWorld();
		}
	}
	
	private void changeWorld() {
		int tmp = world.nextWorld();
		world.dispose();
		switch (tmp) {
			case Globals.GALILEO1:		world = new Galileo1();
										spriteBatch = world.getSpriteBatch();
										break;
		}
	}
}
