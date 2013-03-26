package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.DialogBox;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Dialog;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.models.SchrodingerModel;
import com.theboxbrigade.quantumchaos.views.SchrodingerView;

public class SchrodingerController extends ObjectController implements Interactable {
	private Position position;
	protected int facingDir = Globals.SOUTH;
	protected float x = Globals.GAME_WIDTH/2;
	protected float y = Globals.TILE_HEIGHT*14.0f;
	protected boolean moving;
	protected String dialogText;
	protected boolean talking;
	private TileManager tileManager;
	
	public SchrodingerController(TileManager tileManager) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		position.setTile(tileManager.getTile(7, 7));
		tileManager.getTile(position.getX(), position.getY()).setObstructed(true);
		
		model = new SchrodingerModel(this);
		view = new SchrodingerView();
	}
	
	public void setPosition(Position p) {
		position.setTile(p.getTile());
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
		} else if (input == Globals.INTERACT) {
			whenInteractedWith();
		}
	}
	
	@Override
	public void whenInteractedWith() {
		((SchrodingerModel)model).dialog(Dialog.TEST_DIALOG);
	}
	
	public boolean isMoving() { return moving; }
	public void setMoving(boolean moving) { this.moving = moving; }
	
	public String getDialogText() { return dialogText; }
	public void setDialogText(String dialogText) { this.dialogText = dialogText; }
	
	public boolean isTalking() { return talking; }
	public void setTalking(boolean talking) { this.talking = talking; }
	public DialogBox getDialogBox() {
		return new DialogBox(Assets.schrodingerE, null);
	}
	
	@Override
	public void update() {
		updateView();
	}

	@Override
	protected void updateView() {
		// TODO Auto-generated method stub
		//((SchrodingerView)view).update(0, facingDir);
		((SchrodingerView)view).update(x, y, facingDir);
	}

	@Override
	public boolean equals(ObjectController other) {
		// TODO Auto-generated method stub
		return false;
	}
}
