package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.SchrodingerController;
import com.theboxbrigade.quantumchaos.general.Assets;

public class SchrodingerModel extends CharacterModel {
	protected int state;
	protected int facingDir;
	protected boolean moving;
	protected boolean talking;
	
	public SchrodingerModel(SchrodingerController controller) {
		this.controller = controller;
	}
	
	public void returnToIdle() {
		state = SchrodingerController.IDLE;
		sync();
	}
	
	@Override
	public void face(int direction) {
		facingDir = direction;
		sync();
	}

	@Override
	public boolean move(int direction) {
		state = SchrodingerController.WALKING;
		sync();
		return false;
	}
	
	public void talk() {
		System.out.println("Talking to Schrodinger");
		state = SchrodingerController.INIT_TALKING;
		sync();
	}

	@Override
	protected void sync() {
		((SchrodingerController)controller).setFacingDirection(facingDir);
		((SchrodingerController)controller).setState(state);
	}

}
