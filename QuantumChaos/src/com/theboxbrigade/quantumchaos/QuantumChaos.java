package com.theboxbrigade.quantumchaos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.Timer;
import com.theboxbrigade.quantumchaos.general.AnimatedAssets;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class QuantumChaos extends Game {	
	private static final float TIMERSECS = Globals.DELTA;
	private TitleScreen titleScreen;
	
	private boolean running = false;
	private Screen screen;
	private final Input input = new Input();
	private final boolean started = false;
	
	private Timer timer;
	private TimerTask timerTask;
	
	@Override
	public void create() {
		Assets.load();
		AnimatedAssets.load();
		
		titleScreen = new TitleScreen();
		
		timer = new Timer();
		timerTask = new TimerTask(titleScreen, input);
		timer.scheduleTask(timerTask, 0, TIMERSECS);
		
		Gdx.input.setInputProcessor(input);
		
		running = true;
		setScreen(titleScreen);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		screen.render(TIMERSECS);
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
		timerTask.setScreen(screen);
	}
	
	public class TimerTask extends Timer.Task {
		private Screen screen;
		private Input input;
		
		public TimerTask(Screen screen, Input input) {
			this.screen = screen;
			this.input = input;
		}
		
		public void setScreen(Screen screen) {
			this.screen = screen;
		}

		@Override
		public void run() {
			screen.tick(input);
			input.tick();
		}
		
	}
}
