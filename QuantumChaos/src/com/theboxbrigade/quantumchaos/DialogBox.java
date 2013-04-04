package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;

public class DialogBox {
	protected SpriteBatch spriteBatch = new SpriteBatch();
	protected BitmapFont font = Assets.font;
	protected boolean visible;
	
	protected Sprite bg = Assets.dialogBoxBG;
	protected Sprite talkingPortrait;
	protected String text;
	protected String[] lines;
	protected int nLines;

	// number of characters to break the string for the next line
	protected static final int STR_BREAK = 50;
	protected static final int MAX_LINES = 4;
	protected static final int LINE_HEIGHT = 35;
	
	// offsets for portrait / text
	protected static final int START_PORTRAIT_X = 100;
	protected static final int START_PORTRAIT_Y = 75;
	protected static final int START_TEXT_X = 275;
	protected static final int START_TEXT_Y = 200;
	
	public DialogBox(Sprite talkingPortrait, String text) {
		this.talkingPortrait = talkingPortrait;
		this.text = text;
		visible = false;
		
		if (text != null) {
			if (text.length() > MAX_LINES * STR_BREAK) {
				System.out.println("WARNING: Dialog Box string length too long! Clipping to 200 characters");
				text = text.substring(0, 200);
			}
			
			nLines = (int)Math.ceil(text.length() / 50.0f);
			lines = new String[nLines];
			for (int i = 0; i < nLines; i++) {
				int start = i*STR_BREAK;
				int end = (i+1)*STR_BREAK;
				if (end > text.length()) end = text.length();
				lines[i] = text.substring(start, end);
			}
		}
	}
	
	public void setTalkingPortrait(Sprite talkingPortrait) {
		this.talkingPortrait = talkingPortrait;
	}
	
	public void setText(String text) {
		if (text != null) {
			this.text = text;
			
			if (text.length() > MAX_LINES * STR_BREAK) {
				System.out.println("WARNING: Dialog Box string length too long! Clipping to 200 characters");
				text = text.substring(0, 200);
			}
			
			nLines = (int)Math.ceil(text.length() / 50.0f);
			lines = new String[nLines];
			for (int i = 0; i < nLines; i++) {
				int start = i*STR_BREAK;
				int end = (i+1)*STR_BREAK;
				if (end > text.length()) end = text.length();
				lines[i] = text.substring(start, end);
			}
		}
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	public void update() {
		if (visible) draw();
	}
	
	protected void draw() {
		spriteBatch.draw(bg, 0, 0);
		spriteBatch.draw(talkingPortrait, START_PORTRAIT_X, START_PORTRAIT_Y);
		for (int i = 0; i < nLines; i++) {
			font.draw(spriteBatch, lines[i], START_TEXT_X, START_TEXT_Y-i*LINE_HEIGHT);
		}
	}
}
