package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class QuantumChaos extends Game {
	public static final int WIDTH = Globals.GAME_WIDTH;
	public static final int HEIGHT = Globals.GAME_HEIGHT;
	
	private TitleScreen titleScreen;
	private MainMenuScreen mainMenuScreen;
	private PlayingScreen playingScreen;
	
	private boolean running = false;
	private Screen screen;
	private final Input input = new Input();
	private final boolean started = false;
	private float accum = 0;
	
	
	@Override
	public void create() {
		titleScreen = new TitleScreen();
		Assets.load();
		Gdx.input.setInputProcessor(input);
		running = true;
		setScreen(titleScreen);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while (accum > 1.0f / 60.0f) {
			screen.tick(input);
			input.tick();
			accum -= 1.0f / 60.0f;
		}
		screen.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		running = false;
	}

	@Override
	public void resume() {
		running = true;
	}
	
	public void setScreen(Screen newScreen) {
		if (screen != null) screen.removed();
		screen = newScreen;
		if (screen != null) screen.init(this);
	}
}
