package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.general.Input;

public class YesNoDialogBox extends DialogBox {
	public static final int YES_SELECTED = 0;
	public static final int NO_SELECTED = 1;
	protected boolean optionChosen = false;
	public int state = YES_SELECTED;
	protected boolean showYesNo = false;
	
	public YesNoDialogBox() {
		super();
	}
	
	public YesNoDialogBox(Sprite portrait, String text) {
		super(portrait,text);
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setShowYesNo(boolean showYesNo) {
		this.showYesNo = showYesNo;
	}
	
	public boolean yesNoShowing() {
		return showYesNo;
	}
	
	public int processInput(int input) {
		if (input == Input.DPAD_UP || input == Input.DPAD_DOWN) {
			if (state == YES_SELECTED) state = NO_SELECTED;
			else state = YES_SELECTED;
		} else if (input == Input.AFFIRM) {
			if (state == YES_SELECTED) yes();
			else no();
		}
		return state;
	}
	
	public void yes() {
		System.out.println("YES");
		optionChosen = true;
		setVisible(false);
	}
	
	public void no() {
		System.out.println("NO");
		optionChosen = true;
		setVisible(false);
	}
	
	protected void draw() {
		super.draw();
		if (showYesNo) {
			if (state == YES_SELECTED) font.setColor(0.6f, 0.6f, 1.0f, 1.0f);
			font.draw(spriteBatch, "Yes", START_TEXT_X + 625, START_TEXT_Y - 40);
			font.setColor(Color.BLACK);
			if (state == NO_SELECTED) font.setColor(0.6f, 0.6f, 1.0f, 1.0f);
			font.draw(spriteBatch, "No", START_TEXT_X + 625, START_TEXT_Y - 100);
			font.setColor(Color.BLACK);
		}
	}
}
