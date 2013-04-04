package com.theboxbrigade.quantumchaos.controllers;

import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.models.PlanetModel;
import com.theboxbrigade.quantumchaos.views.PlanetView;

public class PlanetController extends ObjectController implements Interactable, Obstructing {
	public static final int ON_FLOOR = 0;
	public static final int ON_PEDESTAL = 1;
	public static final int IN_HANDS = 2;
	protected Position position;
	protected float x;
	protected float y;
	public int planet;
	public int state = ON_FLOOR;
	private TileManager tileManager;
	
	public PlanetController(TileManager tileManager, int planet) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new PlanetModel(this);
		view = new PlanetView(planet);
		this.planet = planet;
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
	
	public void setState(int state) { this.state = state; }
	
	@Override
	public boolean update(float delta) {

		
		updateView();
		return false;
	}

	@Override
	protected void updateView() {
		((PlanetView)view).update(x, y, state);
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
	public void processInput(int input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(ObjectController other) {
		if (((PlanetController)other).planet == planet &&
				other.getPosition().equals(position)) {
			return true;
		}
		return false;
	}

	@Override
	public void whenInteractedWith() {
		// TODO: Toggle pickup
		if (state == ON_FLOOR || state == ON_PEDESTAL) {
			((PlanetModel)model).pickUp();
		} else if (state == IN_HANDS) {
			((PlanetModel)model).drop();
		}
		
	}

	@Override
	public boolean isInteractable() {
		return true;
	}

}
