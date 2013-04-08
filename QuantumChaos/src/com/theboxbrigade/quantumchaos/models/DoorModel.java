package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.DoorController;
import com.theboxbrigade.quantumchaos.general.Assets;

public class DoorModel extends Model {
	protected boolean locked;
	protected int state;
	
	public DoorModel(DoorController controller) {
		this.controller = controller;
	}
	
	public void lock() { locked = true; sync(); }
	public void unlock() {
		Assets.unlock.play();
		locked = false;
		open();
		sync();
	}
	
	public void open() {
		Assets.chestOpen.play();
		state = DoorController.OPEN;
		((DoorController)controller).clearPosition();
		((DoorController)controller).setVisible(false);
		sync();
	}
	public void close() { state = DoorController.CLOSED; sync(); }
	
	@Override
	protected void sync() {
		((DoorController)controller).setLocked(locked);
		((DoorController)controller).setState(state);
	}
}
