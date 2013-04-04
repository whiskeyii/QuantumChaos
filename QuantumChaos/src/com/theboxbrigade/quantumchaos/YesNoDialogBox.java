package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.general.Input;

public class YesNoDialogBox extends DialogBox {
	public static final int YES_SELECTED = 0;
	public static final int NO_SELECTED = 1;
	protected boolean optionChosen = false;
	public int state = YES_SELECTED;
	
	public YesNoDialogBox(Sprite portrait, String text) {
		super(portrait,text);
	}
	
	public void setState(int state) {
		this.state = state;
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
}
