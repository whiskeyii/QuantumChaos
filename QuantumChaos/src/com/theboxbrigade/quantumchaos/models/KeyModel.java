package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.KeyController;

public class KeyModel extends Model {
	protected int state;
	protected boolean visible = false;

	public KeyModel(KeyController controller) {
		this.controller = controller;
	}
	
	public void makeVisible() {
		visible = true;
		sync();
	}
	
	public void pickUp() {
		state = KeyController.PICKED_UP;
		sync();
	}
	
	public void drop() {
		state = KeyController.DROPPED;
		sync();
	}
	
	@Override
	protected void sync() {
		((KeyController)controller).setVisible(visible);
		((KeyController)controller).setState(state);
	}

}
