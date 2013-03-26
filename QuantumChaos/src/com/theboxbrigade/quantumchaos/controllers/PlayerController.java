package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.models.Model;
import com.theboxbrigade.quantumchaos.models.PlayerModel;
import com.theboxbrigade.quantumchaos.views.PlayerView;
import com.theboxbrigade.quantumchaos.views.View;

/**
 * Create this object in a World to get the MVC structure for a Player object (Robert)
 * @Additional Also creates PlayerModel, PlayerView objects
 * @author Jason
 */
public class PlayerController extends ObjectController {
	protected int maxHP = 4;					// Maximum hit points
	protected int currentHP = 4;				// Current hit points
	protected Position position;				// Robert's current tile position
	protected int state = Globals.NORTH;		// Robert's current state
	protected int keyPressed;					// Input processing: which key?

	// What is Robert currently doing?
	protected boolean moving;
	protected boolean teleporting;
	protected boolean carrying;
	protected boolean sliding;
	protected boolean attacking;
	protected boolean hit;
	protected boolean dead;
	
	protected TileManager tileManager;
	
	public PlayerController(TileManager tileManager) {
		this.tileManager = tileManager;
		position = new Position(this.tileManager);
		
		model = new PlayerModel(this);
		view = new PlayerView();
	}
	
	public void setHP(int hp) {
		this.currentHP = hp;
	}
	
	public int getHP() {
		return currentHP;
	}
	
	public void setPosition(Position p) {
		position.setTile(p.getTile());
	}
	
	public Position getPosition() {
		return position;
	}
	
	/** Looks forward one Tile in the direction Robert is facing and returns that tile
	 * @return Tile: The tile in front of Robert (null if he's at an edge of the tile map)
	 */
	public Tile getTileInFrontOfPlayer() {
		switch (state) {
			case 0: return tileManager.getTile(position.getX(), position.getY()-1);
			case 1: return tileManager.getTile(position.getX()+1, position.getY());
			case 2: return tileManager.getTile(position.getX(), position.getY()+1);
			case 3: return tileManager.getTile(position.getX()-1, position.getY());
		}
		return null;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int state() {
		return state;
	}
	
	@Override
	public void processInput(int keyPressed) {
		switch (keyPressed) {
			// NORTH
			case 0:	if (state == Globals.NORTH) {
						((PlayerModel)model).move(Globals.NORTH);
					} else {
						((PlayerModel)model).face(Globals.NORTH);
					}
					break;
			// EAST
			case 1:	if (state == Globals.EAST) {
						((PlayerModel)model).move(Globals.EAST);
					} else {
						((PlayerModel)model).face(Globals.EAST);
					}
					break;
			// SOUTH
			case 2:	if (state == Globals.SOUTH) {
						((PlayerModel)model).move(Globals.SOUTH);
					} else {
						((PlayerModel)model).face(Globals.SOUTH);
					}
					break;
			// WEST
			case 3:	if (state == Globals.WEST) {
						((PlayerModel)model).move(Globals.WEST);
					} else {
						((PlayerModel)model).face(Globals.WEST);
					}
					break;
		}
	}
	
	public void processGameState(int state) {
		final int BOX_OPEN = 4;
		
		switch (state) {
			case BOX_OPEN: 	((PlayerModel)model).teleport();
							break;
		}
	}
	
	public boolean isMoving() { return moving; }
	public void setMoving(boolean moving) { this.moving = moving; }
	
	public boolean isTeleporting() { return teleporting; }
	public void setTeleporting(boolean teleporting) { this.teleporting = teleporting; }
	
	public boolean isCarrying() { return carrying; }
	public void setCarrying(boolean carrying) { this.carrying = carrying; }
	
	public boolean isSliding() { return sliding; }
	public void setSliding(boolean sliding) {this.sliding = sliding; }
	
	public boolean isAttacking() { return attacking; }
	public void setAttacking(boolean attacking) { this.attacking = attacking; }
	
	public boolean isHit() { return hit; }
	public void setHit(boolean hit) { this.hit = hit; }
	
	public boolean isDead() { return dead; }
	public void setDead(boolean dead) { this.dead = dead; }
	
	@Override
	public void update() {
		updateView();
	}
	
	@Override
	protected void updateView() {
		((PlayerView)view).update(state);
	}
	
	@Override
	public boolean equals(ObjectController other) {
		return false;
	}
}
