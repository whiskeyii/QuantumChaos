package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

public class PauseMenu {
	public static final int RESUME = 0;
	public static final int RETURN_TO_HUB = 1;
	public static final int CONTROLS = 2;
	public static final int MAIN_MENU = 3;
	public static final String[] opts = {"Resume Game","Return to The Hub","Controls","Main Menu"};
	public static final String[] controls = {
		"W: Walk/Face West",
		"A: Walk/Face South",
		"S: Walk/Face East",
		"D: Walk/Face North",
		"Q: Interact",
		"SPACE: Attack",
		"",
		"(ESCAPE to return)"
	};
	protected SpriteBatch spriteBatch = new SpriteBatch();
	protected BitmapFont font = Assets.font;
	protected boolean visible = false;
	public int mouseOver = 0;
	public int selected = -1;
	public boolean seeControls = false;
	protected Sprite bg = Assets.pauseBG;
	
	public void setVisible(boolean visible) { this.visible = visible; }
	public boolean isVisible() { return visible; }
	public void setSeeControls(boolean seeControls) { this.seeControls = seeControls; }
	public boolean isSeeControls() { return seeControls; }
	
	public void processInput(int input) {
		if (input == Input.DPAD_UP) {
			if (mouseOver > 0) mouseOver--;
			else mouseOver = 3;
		} else if (input == Input.DPAD_DOWN) {
			if (mouseOver < 3) mouseOver++;
			else mouseOver = 0;
		} else if (input == Input.AFFIRM) {
			selected = mouseOver;
			System.out.println(selected);
		} else if (input == Input.ESCAPE) {
			if (seeControls) {
				seeControls = false;
			}
		}
	}
	
	public void update() {
		if (visible) draw();
		else selected = -1;
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	protected void draw() {
		// Draw background
		// Draw options in white
		// Draw selected option in blue
		spriteBatch.draw(bg, 0, 0);
		font.setScale(2.0f, 2.0f);
		if (!seeControls) {
			for (int i=0; i<opts.length; i++) {
				font.setColor(0, 0, 0, 1.0f);
				if (mouseOver == i) font.setColor(0.6f, 0.6f, 1.0f, 1.0f);
				font.draw(spriteBatch, opts[i], Globals.GAME_WIDTH / 2.0f - 200, Globals.GAME_HEIGHT / 2.0f + 75 - i*50);
			}
			font.setColor(0, 0, 0, 1.0f);
		} else {
			font.setColor(0, 0, 0, 1.0f);
			for (int i=0; i<controls.length; i++) {
				font.draw(spriteBatch, controls[i], Globals.GAME_WIDTH / 2.0f - 225, Globals.GAME_HEIGHT / 2.0f + 200 - i*50);
			}
		}
		font.setScale(1.0f,1.0f);
	}
}
