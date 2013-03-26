package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;

public class DialogBox {
	private SpriteBatch spriteBatch = new SpriteBatch();
	private BitmapFont font = Assets.font;
	private boolean visible;
	
	private Sprite bg = Assets.dialogBoxBG;
	private Sprite talkingPortrait;
	private String text;
	private String[] lines;
	private int nLines;

	// number of characters to break the string for the next line
	private static final int STR_BREAK = 50;
	private static final int MAX_LINES = 4;
	private static final int LINE_HEIGHT = 35;
	
	// offsets for portrait / text
	private static final int START_PORTRAIT_X = 100;
	private static final int START_PORTRAIT_Y = 75;
	private static final int START_TEXT_X = 275;
	private static final int START_TEXT_Y = 200;
	
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
	
	private void draw() {
		spriteBatch.draw(bg, 0, 0);
		spriteBatch.draw(talkingPortrait, START_PORTRAIT_X, START_PORTRAIT_Y);
		for (int i = 0; i < nLines; i++) {
			font.draw(spriteBatch, lines[i], START_TEXT_X, START_TEXT_Y-i*LINE_HEIGHT);
		}
	}
}
