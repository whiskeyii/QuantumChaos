package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.SchrodingerController;
import com.theboxbrigade.quantumchaos.general.Assets;

public class SchrodingerModel extends CharacterModel {
	protected int facingDir;
	protected boolean moving;
	protected String dialog;
	protected boolean talking;
	
	public SchrodingerModel(SchrodingerController controller) {
		this.controller = controller;
	}
	
	@Override
	public void face(int direction) {
		// TODO Auto-generated method stub
		facingDir = direction;
		sync();
	}

	@Override
	public boolean move(int direction) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void dialog(String line) {
		System.out.println("Talking to Schrodinger");
		talking = true;
		dialog = line;
		sync();
	}

	@Override
	protected void sync() {
		// TODO Auto-generated method stub
		((SchrodingerController)controller).setFacingDirection(facingDir);
		((SchrodingerController)controller).setDialogText(dialog);
		((SchrodingerController)controller).setTalking(talking);
	}

}
