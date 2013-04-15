package com.theboxbrigade.quantumchaos.controllers;

import com.theboxbrigade.quantumchaos.Obstructing;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.models.N2ChunkModel;
import com.theboxbrigade.quantumchaos.views.N2ChunkView;

public class N2ChunkController extends ObjectController implements Obstructing, Interactable {
	public static final int STATIC = 0;
	public static final int SLIDING = 1;
	public static final int LARGE = 1;
	public static final int MEDIUM = 2;
	public static final int SMALL = 3;
	
	public static final float TX = Globals.OBJ_TRANSLATION_X / 4.0f;
	public static final float TY = Globals.OBJ_TRANSLATION_Y / 4.0f;
	protected int type = 1;
	protected int state = STATIC;
	protected int playerFacing;
	protected int deltaState = 0;
	protected int moveDuration = 0;
	protected Position position;
	protected float x, y;
	protected TileManager tileManager;
	
	public N2ChunkController(TileManager tileManager, int type) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new N2ChunkModel(this);
		view = new N2ChunkView(type);
		
		this.type = type;
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
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setPlayerFacing(int playerFacing) {
		this.playerFacing = playerFacing;
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
		if (moveDuration > 0) {
			if (++deltaState > 3 && state == SLIDING) {
				moveDuration--;
				switch (playerFacing) {
					case Globals.NORTH:	translate(TX,TY);
										break;
					case Globals.EAST:	translate(TX,-TY);
										break;
					case Globals.SOUTH:	translate(-TX,-TY);
										break;
					case Globals.WEST:	translate(-TX,TY);
										break;
				}
				deltaState = 0;
			}
			if (moveDuration == 0) Assets.slide.stop();
		} else {
			state = STATIC;
		}
		
		updateView();
		return false;
	}

	@Override
	protected void updateView() {
		((N2ChunkView)view).update(state, x, y);
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
		System.out.println("Facing: " + playerFacing);
		boolean moved = false;
		moveDuration = 0;
		System.out.println("Old Position: " + position);
		if (type <= MEDIUM) {
			moved = ((N2ChunkModel)model).move(playerFacing);
			if (moved) moveDuration += 4;
			moved = ((N2ChunkModel)model).move(playerFacing);
			if (moved) moveDuration += 4;
		}
		if (type == LARGE) {
			moved = ((N2ChunkModel)model).move(playerFacing);
			if (moved) moveDuration += 4;
		}
		if (type != SMALL) type++;
		((N2ChunkView)view).setType(type);
		System.out.println("New Position: " + position);
	}

	@Override
	public boolean isInteractable() {
		return true;
	}

	@Override
	public int interactableType() {
		return Interactable.N2_CHUNK;
	}
}
