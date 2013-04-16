package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;
import com.theboxbrigade.quantumchaos.general.K;

public class MainMenuScreen extends Screen {
	private BitmapFont font = Assets.font;
	private float textAlpha = 1.0f;
	private boolean increasing = false;
	private K k = new K();
	
	public MainMenuScreen() {
		System.out.println("MainMenuScreen");
		Assets.menuMusic.setLooping(true);
		Assets.menuMusic.play();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		draw(Assets.logo, 0, 0);
		displayText();
		spriteBatch.end();
	}
	
	@Override
	public void tick(Input input) {
		k.pr(input);
		if (k.s()) { Assets.planetPuzzleComplete.play(); Globals.K = !Globals.K; }
		
		if (input.buttons[Input.AFFIRM] && !input.oldButtons[Input.AFFIRM])
		if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
			System.out.println("ENTER GAME");
			Assets.menuMusic.stop();
			setScreen(new PlayingScreen());
			input.releaseAllKeys();
		}
	}
	
	private void displayText() {
		String text = "Press ENTER to play";
		if (increasing) {
			if (textAlpha >= 1.0f) {
				increasing = false;
			} else {
				textAlpha += 0.05f;
			}
		} else {
			if (textAlpha <= 0.0f) {
				increasing = true;
			} else {
				textAlpha -= 0.05f;
			}
		}
		font.setScale(1.5f, -1.5f);
		font.setColor(1.0f - (k.i * 0.1f), 1.0f - (k.i * 0.1f), 1.0f, textAlpha);
		font.draw(spriteBatch, text, Globals.GAME_WIDTH / 2.0f - 200, Globals.GAME_HEIGHT / 2.0f + 150);
		font.setScale(1.0f, 1.0f);
	}
}
