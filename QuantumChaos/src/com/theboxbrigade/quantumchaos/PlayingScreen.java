package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class PlayingScreen extends Screen {
	protected SpriteBatch spriteBatch;
	protected World world;
	
	public PlayingScreen() {
		world = new TheHub();
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
			case Globals.THE_HUB:		world = new TheHub();
										break;
			case Globals.GALILEO1:		world = new Galileo1();
										break;
			case Globals.GALILEO2:		world = new Galileo2();
										break;
			case Globals.NEWTON1:		world = new Newton1();
										break;
			case Globals.NEWTON2:		world = new Newton2();
										break;
			case Globals.MAIN_MENU:		setScreen(new MainMenuScreen());
										break;
		}
		spriteBatch = world.getSpriteBatch();
	}
}
