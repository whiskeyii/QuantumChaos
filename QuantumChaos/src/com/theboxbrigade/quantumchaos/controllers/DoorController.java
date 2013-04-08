package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.models.DoorModel;
import com.theboxbrigade.quantumchaos.views.DoorView;

public class DoorController extends ObjectController implements Interactable, Obstructing {
	public static final int OPEN = 0;
	public static final int CLOSED = 1;
	public static final int PLAYER_HAS_KEY = 10;
	public static final int PLAYER_DOES_NOT_HAVE_KEY = 11;
	protected Position position;
	protected float x, y;
	protected boolean locked = true;
	public int state = CLOSED;
	protected boolean visible = true;
	protected boolean unlockable = false;
	protected TileManager tileManager;
	
	public DoorController(TileManager tileManager, int doorType) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new DoorModel(this);
		view = new DoorView(doorType);
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
	
	public void clearPosition() {
		tileManager.getTile(position.getX(), position.getY()).setObstructed(false);
		setPosition(tileManager.getTile(0, 0));
	}
	
	public void setLocked(boolean locked) { this.locked = locked; }
	public boolean isLocked() { return locked; }
	
	public void setUnlockable(boolean unlockable) { this.unlockable = unlockable; }
	public boolean isUnlockable() { return unlockable; }
	
	public void setState(int state) { this.state = state; }
	
	public void setVisible(boolean visible) { this.visible = visible; }
	public boolean isVisible() { return visible; }

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
	public void whenInteractedWith() {
		// Unlockable: Means Player has KEY
		// Locked: Means player interacted with door, and it is locked
		// If OPEN: No need to be interacting
		// If CLOSED: Open the door
		System.out.println("Unlockable? " + unlockable);
		System.out.println("Locked? " + locked);
		if (unlockable) {
			if (locked) ((DoorModel)model).unlock();
			else {
				if (state == OPEN) System.out.println("Enter!");
				else if (state == CLOSED) ((DoorModel)model).open();
			}
		}
	}

	@Override
	public boolean isInteractable() {
		return true;
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
		if (visible) ((DoorView)view).update(x, y, state);
	}

	public SpriteBatch getViewSpriteBatch() {
		return ((DoorView)view).getSpriteBatch();
	}
	
	@Override
	public boolean equals(ObjectController other) {
		// TODO Auto-generated method stub
		return false;
	}

}
