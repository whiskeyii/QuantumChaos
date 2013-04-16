package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.theboxbrigade.quantumchaos.DialogBox;
import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.YesNoDialogBox;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.models.BoxModel;
import com.theboxbrigade.quantumchaos.views.BoxView;

public class BoxController extends ObjectController implements Interactable, Obstructing {
	public static final int OPEN = 0;
	public static final int CLOSED = 1;
	protected Position position;
	protected int color;
	protected float x;
	protected float y;
	public int state = CLOSED;
	protected boolean locked = false;
	private DialogBox dialogBox;
	private String dialogBoxText;
	private TileManager tileManager;
	
	private int worldToTravelTo;
	
	public BoxController(TileManager tileManager, int color) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new BoxModel(this);
		view = new BoxView(color);
		
		this.color = color;
		if (color == BoxView.RED) {
			dialogBox = new YesNoDialogBox(Assets.redBoxOpen, "Travel to World?");
			dialogBoxText = ("Travel to Newton's World?");
		} else if (color == BoxView.GREEN) {
			dialogBox = new YesNoDialogBox(Assets.greenBoxOpen, "Travel to World?");
			dialogBoxText = ("Travel to Galileo's World?");
		}
		dialogBox.setUseGeneratedPortrait(false);
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
	public String getDialogBoxText() {
		return dialogBoxText;
	}
	
	public String getLockedText() {
		return "It's locked.";
	}
	
	public Sprite getDialogBoxPortrait() {
		if (color == BoxView.RED) return Assets.redBoxOpen;
		else if (color == BoxView.GREEN) return Assets.greenBoxOpen;
		else if (color == BoxView.BLUE) return Assets.blueBoxOpen;
		return null;
	}
	
	public void setState(int state) { this.state = state; }
	
	public boolean isOpen() {
		if (state == OPEN) return true;
		return false;
	}
	
	public void setLocked(boolean locked) { this.locked = locked; }
	
	public boolean isLocked() { return locked; }
	
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
		if (state == OPEN) {
			dialogBox.setVisible(true);
		} else dialogBox.setVisible(false);
		
		updateView();
		return false;
	}

	@Override
	protected void updateView() {
		((BoxView)view).update(x, y, state);
	}

	@Override
	public void processInput(int input) {
	}

	@Override
	public void whenInteractedWith() {
		if (!locked) {
			System.out.println("Toggling open box");
			((BoxModel)model).toggleOpen();
		} else System.out.println("Box locked");
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

	@Override
	public int interactableType() {
		return Interactable.BOX;
	}
}
