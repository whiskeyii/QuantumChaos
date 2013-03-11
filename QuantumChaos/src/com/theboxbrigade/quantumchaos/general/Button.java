package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class Button {
	private HAlignment alignment;
	private String text;
	private boolean wasPressed = false;
	private float x, y;
	private float w, h;
	private boolean activated = false;
	private boolean down = false;
	private BitmapFont font;
	private float textHeight;
	
	public Button(String text, BitmapFont font) {
		this.text = text;
		this.font = font;
		//TextBounds bounds = Assets.textFont.getBounds
	}
	
	public void setWidth(float w) {
		this.w = w;
	}
	
	public void setHeight(float h) {
		this.h = h;
	}
	
	public void setAlignment(HAlignment alignment) {
		this.alignment = alignment;
	}
	
	private boolean isInBounds(float x, float y) {
		return (x >= this.x && x < this.x + this.w && y >= this.y && y < this.y + this.h);
	}
	
	public void draw(SpriteBatch spriteBatch) {
		// TODO: Draw
	}
}
