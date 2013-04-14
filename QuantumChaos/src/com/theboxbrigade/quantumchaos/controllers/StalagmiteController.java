package com.theboxbrigade.quantumchaos.controllers;

import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.models.StalagmiteModel;
import com.theboxbrigade.quantumchaos.views.StalagmiteView;

public class StalagmiteController extends ObjectController implements Interactable, Obstructing {
	public static final int STATIC = 0;
	protected Position position;
	protected float x;
	protected float y;
	protected TileManager tileManager;
	
	public StalagmiteController(TileManager tileManager) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new StalagmiteModel(this);
		view = new StalagmiteView();
	}
	
	public void setPosition(Tile t) {
		tileManager.getTile(position.getX(), position.getY()).setObstructed(false);
		tileManager.getTile(position.getX(), position.getY()).setObstructing(null);
		position.setTile(tileManager.getTile(t.getX(), t.getY()));
		tileManager.getTile(position.getX(), position.getY()).setObstructed(true);
		tileManager.getTile(position.getX(), position.getY()).setObstructing(this);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setScreenPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	@Override
	public boolean update(float delta) {
		updateView();
		return false;
	}

	@Override
	protected void updateView() {
		((StalagmiteView)view).update(STATIC, x, y);
	}
	
	@Override
	public void processInput(int input) {
		
	}

	@Override
	public boolean equals(ObjectController other) {
		return false;
	}

	@Override
	public void whenInteractedWith() {
	}

	@Override
	public boolean isInteractable() {
		return true;
	}

	@Override
	public int interactableType() {
		return Interactable.STALAGMITE;
	}

}
