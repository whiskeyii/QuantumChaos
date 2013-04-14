package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.StalagmiteController;

public class StalagmiteModel extends Model {
	
	public StalagmiteModel(StalagmiteController controller) {
		this.controller = controller;
	}

	@Override
	protected void sync() {
	}
}
