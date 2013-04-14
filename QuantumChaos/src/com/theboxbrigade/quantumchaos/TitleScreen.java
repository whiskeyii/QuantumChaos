package com.theboxbrigade.quantumchaos;

import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class TitleScreen extends Screen {
	private int time = 0;
	private float alpha = 1.0f;
	
	@Override
	public void render(float delta) {
		// Draw the logo
		spriteBatch.begin();
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
		draw(Assets.teamLogo, (int)(Globals.GAME_WIDTH / 2 - Assets.teamLogo.getWidth() / 2), (int)(Globals.GAME_HEIGHT / 2 - Assets.teamLogo.getHeight() / 2));
		// TODO: drawString: "Copyright The Box Brigade 2013"

		spriteBatch.end();
	}
	
	@Override
	public void tick(Input input) {
		time++;
		if (time <= 20) alpha += 0.05f;
		if (time >= 40) setScreen(new MainMenuScreen());	// TODO: CHANGE BACK TO 60
	}
}
