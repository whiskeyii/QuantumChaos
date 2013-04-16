package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.controllers.JournalPageController;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

public class JournalPageView extends View {
	protected int state;
	protected String text;
	protected BitmapFont font = Assets.journalFont;
	//protected BitmapFont font = Assets.font;
	protected Sprite page = Assets.journalPage;
	//protected Sprite page = Assets.redKey;
	protected Sprite pageLarge = Assets.journalPageLarge;
	//protected Sprite pageLarge = Assets.blueKey;
	
	public JournalPageView(String text) {
		this.text = text;
	}
	
	@Override
	public void update(int state) {
		update(0, 0, state);
	}
	
	public void update(float x, float y, int state) {
		this.state = state;
		if (state == JournalPageController.DROPPED)
			draw(page, x, y);
		else if (state == JournalPageController.PICKED_UP)
			draw(pageLarge, Globals.GAME_WIDTH / 2.0f - pageLarge.getWidth() / 2.0f, Globals.GAME_HEIGHT / 2.0f - pageLarge.getHeight() / 2.0f);
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
		if (state == JournalPageController.PICKED_UP) {
			font.setColor(Color.BLACK);
			font.setScale(0.8f, 0.8f);
			font.drawWrapped(spriteBatch, text, Globals.GAME_WIDTH / 2.0f - 170, Globals.GAME_HEIGHT / 2.0f + 200, 285);
			font.setScale(1.0f, 1.0f);
			font.setColor(Color.WHITE);
		}
		
	}
}
