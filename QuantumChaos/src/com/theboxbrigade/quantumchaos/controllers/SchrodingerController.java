package com.theboxbrigade.quantumchaos.controllers;

import com.theboxbrigade.quantumchaos.DialogBox;
import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.models.SchrodingerModel;
import com.theboxbrigade.quantumchaos.views.SchrodingerView;

public class SchrodingerController extends ObjectController implements Interactable, Obstructing {
	public static final int IDLE = 0;
	public static final int INIT_TALKING = 1;
	public static final int TALKING = 2;
	public static final int WALKING = 3;
	
	private Position position;
	protected int facingDir = Globals.SOUTH;
	protected float x;
	protected float y;
	public int state;
	protected boolean moving;
	protected String dialogText;
	protected boolean talking;
	private TileManager tileManager;
	
	public SchrodingerController(TileManager tileManager) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new SchrodingerModel(this);
		view = new SchrodingerView();
	}
	
	public void setPosition(Tile tile) {
		tileManager.getTile(position.getX(), position.getY()).setObstructed(false);
		tileManager.getTile(position.getX(), position.getY()).setObstructing(null);
		position.setTile(tile);
		tileManager.getTile(position.getX(), position.getY()).setObstructed(true);
		tileManager.getTile(position.getX(), position.getY()).setObstructing(this);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setFacingDirection(int direction) {
		this.facingDir = direction;
	}
	
	public int facingDirection() {
		return facingDir;
	}
	
	public void setState(int state) {
		this.state = state;
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
		// Translate the opposite direction of the camera translation
		if (input == Globals.NORTH) {
			translate(-(float)Globals.OBJ_TRANSLATION_X, -(float)Globals.OBJ_TRANSLATION_Y);
		} else if (input == Globals.EAST) {
			translate(-(float)Globals.OBJ_TRANSLATION_X, (float)Globals.OBJ_TRANSLATION_Y);
		} else if (input == Globals.SOUTH) {
			translate((float)Globals.OBJ_TRANSLATION_X, (float)Globals.OBJ_TRANSLATION_Y);
		} else if (input == Globals.WEST) {
			translate((float)Globals.OBJ_TRANSLATION_X, -(float)Globals.OBJ_TRANSLATION_Y);
		}
	}
	
	@Override
	public void whenInteractedWith() {
		((SchrodingerModel)model).talk();
	}
	
	public boolean isMoving() { return moving; }
	public void setMoving(boolean moving) { this.moving = moving; }
	
	public boolean isTalking() { return talking; }
	public void setTalking(boolean talking) { this.talking = talking; }
	public DialogBox getDialogBox() {
		return new DialogBox(Assets.schrodingerE, null);
	}
	
	@Override
	public boolean update(float delta) {
		updateView();
		return false;
	}

	@Override
	protected void updateView() {
		((SchrodingerView)view).update(x, y, facingDir);
	}

	@Override
	public boolean equals(ObjectController other) {
		return false;
	}

	@Override
	public boolean isInteractable() {
		return true;
	}
}
