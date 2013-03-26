package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.models.BoxModel;
import com.theboxbrigade.quantumchaos.views.BoxView;

public class BoxController extends ObjectController implements Interactable {
	public static final int OPEN = 0;
	public static final int CLOSED = 1;
	protected Position position;
	protected float x;
	protected float y;
	protected int openState = CLOSED;
	private TileManager tileManager;
	
	public BoxController(TileManager tileManager, int color) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		tileManager.getTile(position.getX(), position.getY()).setObstructed(true);
		
		model = new BoxModel(this);
		view = new BoxView(color);
	}
	
	public void setPosition(Position p) {
		position.setTile(p.getTile());
	}
	
	public void setPosition(Tile t) {
		tileManager.getTile(position.getX(), position.getY()).setObstructed(false);
		position.setTile(t);
		tileManager.getTile(position.getX(), position.getY()).setObstructed(true);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setScreenPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setOpen(int openState) {
		this.openState = openState;
	}
	
	public boolean isOpen() {
		if (openState == 0) return true;
		return false;
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	@Override
	public void update() {
		updateView();
	}

	@Override
	protected void updateView() {
		// TODO Auto-generated method stub
		((BoxView)view).update(x, y, openState);
	}

	@Override
	public void processInput(int input) {
		// TODO Auto-generated method stub
		if (input == Globals.NORTH) {
			translate(-(float)Globals.OBJ_TRANSLATION_X, -(float)Globals.OBJ_TRANSLATION_Y);
		} else if (input == Globals.EAST) {
			translate(-(float)Globals.OBJ_TRANSLATION_X, (float)Globals.OBJ_TRANSLATION_Y);
		} else if (input == Globals.SOUTH) {
			translate((float)Globals.OBJ_TRANSLATION_X, (float)Globals.OBJ_TRANSLATION_Y);
		} else if (input == Globals.WEST) {
			translate((float)Globals.OBJ_TRANSLATION_X, -(float)Globals.OBJ_TRANSLATION_Y);
		} else if (input == Globals.INTERACT) {
			whenInteractedWith();
		}
	}

	@Override
	public void whenInteractedWith() {
		// TODO Auto-generated method stub
		System.out.println("Toggling open box");
		((BoxModel)model).toggleOpen();
	}

	@Override
	public boolean equals(ObjectController other) {
		// TODO Auto-generated method stub
		return false;
	}
}
