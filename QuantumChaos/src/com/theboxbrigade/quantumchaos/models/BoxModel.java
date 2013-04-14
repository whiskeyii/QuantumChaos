package com.theboxbrigade.quantumchaos.models;

import com.badlogic.gdx.audio.Sound;
import com.theboxbrigade.quantumchaos.controllers.BoxController;
import com.theboxbrigade.quantumchaos.general.Assets;

public class BoxModel extends Model {
	protected boolean open;
	protected int state;
	
	public BoxModel(BoxController controller) {
		this.controller = controller;
	}
	
	public void toggleOpen() {
		open = !open;
		if (open) state = BoxController.OPEN;
		else state = BoxController.CLOSED;
		if (open) Assets.chestOpen.play();
		else Assets.chestClose.play();
		sync();
	}
	
	@Override
	protected void sync() {
		((BoxController)controller).setState(state);
	}

}
