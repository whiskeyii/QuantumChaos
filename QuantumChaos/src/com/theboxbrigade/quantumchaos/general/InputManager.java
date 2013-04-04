package com.theboxbrigade.quantumchaos.general;

import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.controllers.Interactable;

public class InputManager {
	private Input input;
	private int parsedInput;
	
	public void setInput(Input input) {
		this.input = input;
	}
	
	public int parse() {
		if (input.buttons[Input.WALK_NORTH] && !input.oldButtons[Input.WALK_NORTH]) {
			input.releaseAllKeys();
			return parsedInput = Input.WALK_NORTH;
		} else if (input.buttons[Input.WALK_EAST] && !input.oldButtons[Input.WALK_EAST]) {
			input.releaseAllKeys();
			return parsedInput = Input.WALK_EAST;
		} else if (input.buttons[Input.WALK_SOUTH] && !input.oldButtons[Input.WALK_SOUTH]) {
			input.releaseAllKeys();
			return parsedInput = Input.WALK_SOUTH;
		} else if (input.buttons[Input.WALK_WEST] && !input.oldButtons[Input.WALK_WEST]) {
			input.releaseAllKeys();
			return parsedInput = Input.WALK_WEST;
		} else if (input.buttons[Input.INTERACT] && !input.oldButtons[Input.INTERACT]) {
			input.releaseAllKeys();
			return parsedInput = Input.INTERACT;
		}
		return -1;
	}
	
	public int parse(Input input) {
		setInput(input);
		return parse();
	}
	
	public int getValue() {
		return parsedInput;
	}
}
