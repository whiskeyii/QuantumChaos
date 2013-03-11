package com.theboxbrigade.quantumchaos.controllers;

import com.theboxbrigade.quantumchaos.models.Model;
import com.theboxbrigade.quantumchaos.views.View;

public abstract class ObjectController implements Controller {
	protected Model model;
	protected View view;
	
	public Model getModel() {
		return model;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public View getView() {
		return view;
	}
	
	public void setView(View view) {
		this.view = view;
	}

	protected abstract void updateView();
}
