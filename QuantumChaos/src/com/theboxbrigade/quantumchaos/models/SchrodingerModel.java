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
	
	public void dialog() {
		talking = true;
		dialog = Assets.Dialog.TALK_TO_SCHRODINGER_HUB_1;
	}

	@Override
	protected void sync() {
		// TODO Auto-generated method stub
		((SchrodingerController)controller).setFacingDirection(facingDir);
		((SchrodingerController)controller).setDialogText(dialog);
		((SchrodingerController)controller).setTalking(talking);
	}

}
