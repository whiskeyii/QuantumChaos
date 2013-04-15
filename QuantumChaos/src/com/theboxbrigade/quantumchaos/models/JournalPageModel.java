package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.JournalPageController;

public class JournalPageModel extends Model {
	protected int state;

	public JournalPageModel(JournalPageController controller) {
		this.controller = controller;
	}
	
	public void pickUp() {
		state = JournalPageController.PICKED_UP;
		((JournalPageController)controller).setObstructing(false);
		((JournalPageController)controller).setInteractable(false);
		sync();
	}
	
	public void drop() {
		state = JournalPageController.DROPPED;
		((JournalPageController)controller).setObstructing(true);
		((JournalPageController)controller).setInteractable(true);
		sync();
	}
	
	@Override
	protected void sync() {
		((JournalPageController)controller).setState(state);
	}

}
