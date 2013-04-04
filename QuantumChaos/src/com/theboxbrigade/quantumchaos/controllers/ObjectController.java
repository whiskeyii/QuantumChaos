package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.models.Model;
import com.theboxbrigade.quantumchaos.views.View;

public abstract class ObjectController implements Controller {
	protected Model model;
	protected View view;
	protected Position position;
	
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
	
	public SpriteBatch getViewSpriteBatch() {
		return view.getSpriteBatch();
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	protected abstract void updateView();
	
	public abstract void translate(float x, float y);
	
	public abstract void processInput(int input);
	
	public abstract boolean equals(ObjectController other);
}
