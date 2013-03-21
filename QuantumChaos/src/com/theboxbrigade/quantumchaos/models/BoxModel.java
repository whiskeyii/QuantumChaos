package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.BoxController;

public class BoxModel extends Model {
	protected boolean open;
	
	public BoxModel(BoxController controller) {
		this.controller = controller;
	}
	
	public void toggleOpen() {
		open = !open;
		sync();
	}
	
	@Override
	protected void sync() {
		// TODO Auto-generated method stub
		if (open) ((BoxController)controller).setOpen(BoxController.OPEN);
		else ((BoxController)controller).setOpen(BoxController.CLOSED);
	}

}
