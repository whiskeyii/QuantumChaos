package com.theboxbrigade.quantumchaos;

import com.theboxbrigade.quantumchaos.controllers.Interactable;
import com.theboxbrigade.quantumchaos.controllers.PlanetController;

public class PlanetSlot implements Interactable {
	private Position position;
	private TileManager tileManager;
	private int correctPlanet;
	private boolean empty = true;
	private boolean satisfied = false;
	
	public PlanetSlot(TileManager tileManager, int correctPlanet) {
		this.tileManager = tileManager;
		this.position = new Position(this.tileManager);
		this.correctPlanet = correctPlanet;
	}
	
	public void setPosition(Tile t) {
		position.setTile(tileManager.getTile(t.getX(), t.getY()));
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setCorrectPlanet(int correctPlanet) {
		this.correctPlanet = correctPlanet;
	}
	
	public boolean isCorrectPlanet(PlanetController planet) {
		if (planet.planet == correctPlanet) return true;
		return false;
	}
	
	public boolean isCorrectPlanet(int correctPlanet) {
		if (this.correctPlanet == correctPlanet) return true;
		return false;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	public boolean isSatisfied() {
		return satisfied;
	}
	
	public void makeSatisfied() {
		satisfied = true;
	}

	@Override
	public void whenInteractedWith() {
		System.out.println("Drop a planet on me");
	}

	@Override
	public boolean isInteractable() {
		return true;
	}

	@Override
	public int interactableType() {
		return Interactable.PLANET_SLOT;
	}
}
