package com.theboxbrigade.quantumchaos;

public class MainMenuScreen extends Screen {
	private int time = 0;
	
	@Override
	public void render() {
		// Draw the logo
		spriteBatch.begin();
		draw(Assets.logo, 0, 0);
		spriteBatch.end();
	}
	
	@Override
	public void tick(Input input) {
		time++;
		if (input.buttons[Input.INTERACT] && !input.oldButtons[Input.INTERACT]) {
			System.out.println("ENTER GAME");
			setScreen(new PlayingScreen());
			input.releaseAllKeys();
		}
	}
}
