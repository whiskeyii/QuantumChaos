package com.theboxbrigade.quantumchaos.controllers;

import com.theboxbrigade.quantumchaos.Door;
import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.models.KeyModel;
import com.theboxbrigade.quantumchaos.views.KeyView;

public class KeyController extends ObjectController implements Obstructing, Interactable {
	public static final int DROPPED = 0;
	public static final int PICKED_UP = 1;
	protected Position position;
	protected Door door;
	protected float x;
	protected float y;
	public int state = DROPPED;
	protected boolean visible = false;
	protected boolean interactable = false;
	private TileManager tileManager;
	
	public KeyController(TileManager tileManager, Door door, int keyColor) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		this.door = door;
		model = new KeyModel(this);
		view = new KeyView(keyColor);
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
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void setScreenPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void processInput(int input) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean update(float delta) {
		updateView();
		
		return false;
	}

	@Override
	protected void updateView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void whenInteractedWith() {
		// TODO Auto-generated method stub
		
	}

	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}
	
	@Override
	public boolean isInteractable() {
		return interactable;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean equals(ObjectController other) {
		// TODO Auto-generated method stub
		return false;
	}

}
