package com.theboxbrigade.quantumchaos;

import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Input;

public class TitleScreen extends Screen {
	private int time = 0;
	
	@Override
	public void render(float delta) {
		// Draw the logo
		spriteBatch.begin();
		draw(Assets.logo, 0, 0);

		// TODO: drawString: "Press SPACE to Start"
		// TODO: drawString: "Copyright The Box Brigade 2013"

		spriteBatch.end();
	}
	
	@Override
	public void tick(Input input) {
		time++;
		if (input.buttons[Input.ATTACK] && !input.oldButtons[Input.ATTACK]) {
			setScreen(new MainMenuScreen());
			System.out.println("SET TO NEW GAME SCREEN");
			input.releaseAllKeys();
		}
	}
}
