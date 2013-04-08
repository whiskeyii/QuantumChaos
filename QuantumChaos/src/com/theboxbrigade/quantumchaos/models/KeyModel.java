package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.KeyController;

public class KeyModel extends Model {
	protected int state;

	public KeyModel(KeyController controller) {
		this.controller = controller;
	}
	
	public void pickUp() {
		state = KeyController.PICKED_UP;
		((KeyController)controller).setObstructing(false);
		sync();
	}
	
	public void drop() {
		state = KeyController.DROPPED;
		((KeyController)controller).setObstructing(true);
		sync();
	}
	
	@Override
	protected void sync() {
		((KeyController)controller).setState(state);
	}

}
