package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.DialogBox;
import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.YesNoDialogBox;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.models.BoxModel;
import com.theboxbrigade.quantumchaos.views.BoxView;

public class BoxController extends ObjectController implements Interactable, Obstructing {
	public static final int OPEN = 0;
	public static final int CLOSED = 1;
	public static final int OPEN_INTERACTING = 2;
	protected Position position;
	protected int color;
	protected float x;
	protected float y;
	public int state = CLOSED;
	private DialogBox dialogBox;
	private TileManager tileManager;
	
	private int worldToTravelTo;
	
	public BoxController(TileManager tileManager, int color) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new BoxModel(this);
		view = new BoxView(color);
		
		this.color = color;
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
	
	public DialogBox getDialogBox() {
		return dialogBox;
	}
	
	public void setState(int state) { this.state = state; }
	
	public boolean isOpen() {
		if (state == OPEN) return true;
		return false;
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
		if (state == OPEN || state == OPEN_INTERACTING) {
			Sprite tmp = Assets.redBoxOpen;
			if (color == BoxView.GREEN) tmp = Assets.greenBoxOpen;
			else if (color == BoxView.BLUE) tmp = Assets.blueBoxOpen;
			dialogBox = new YesNoDialogBox(tmp, "Travel to World?");
			dialogBox.setVisible(true);
		} else dialogBox = null;
		
		updateView();
		return false;
	}

	@Override
	protected void updateView() {
		((BoxView)view).update(x, y, state);
	}

	@Override
	public void processInput(int input) {
		// TODO: Auto-generated method stub
	}

	@Override
	public void whenInteractedWith() {
		System.out.println("Toggling open box");
		((BoxModel)model).toggleOpen();
	}

	public void setWorldToTravelTo(int worldToTravelTo) {
		this.worldToTravelTo = worldToTravelTo;
	}
	
	public int getWorldToTravelTo() {
		return worldToTravelTo;
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
