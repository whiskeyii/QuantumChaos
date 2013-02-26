package com.theboxbrigade.quantumchaos;

public class TitleScreen extends Screen {
	private int time = 0;
	
	@Override
	public void render() {
		int yOffs = 0;
		spriteBatch.begin();
		draw(Assets.logo, 0, yOffs);
		if (time > 240) {
			// TODO: drawString: "Press SPACE to Start"
		}
		if (time >= 0) {
			// TODO: drawString: "Copyright The Box Brigade 2013"
		}
		spriteBatch.end();
	}
	
	@Override
	public void tick(Input input) {
		time++;
		if (time > 240) {
			if (input.buttons[Input.ATTACK] && !input.oldButtons[Input.ATTACK]) {
				//setScreen(new GameScreen());
				System.out.println("SET TO NEW GAME SCREEN");
				input.releaseAllKeys();
			}
		}
	}
}
