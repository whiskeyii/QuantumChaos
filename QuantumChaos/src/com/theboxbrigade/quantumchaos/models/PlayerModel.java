package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.Carryable;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.Globals;

public class PlayerModel extends CharacterModel {
	protected Carryable itemCarried;
	protected int currentHP;
	protected int facingDir;
	protected boolean moving;
	
	public PlayerModel(PlayerController controller) {
		this.controller = controller;
	}

	@Override
	public void face(int direction) {
		// TODO Auto-generated method stub
		facingDir = direction;
		moving = false;
		sync();
	}

	@Override
	public void move(int direction) {
		moving = true;
		sync();
	}
	
	public void move(int direction, TileManager tileManager) {
		moving = true;
		int dx, dy;
		switch (direction) {
			case 0: // NORTH
					dx = 1; dy = 0;
					break;
			case 1: // EAST
					dx = 0; dy = 1;
					break;
			case 2: // SOUTH
					dx = -1; dy = 0;
					break;
			case 3: // WEST
					dx = 0; dy = -1;
					break;
			default: dx = 0; dy = 0;
		}
		int newX = ((PlayerController)controller).getPosition().getTile().getX() + dx;
		int newY = ((PlayerController)controller).getPosition().getTile().getY() + dy;
		((PlayerController)controller).setPosition(new Position(tileManager.getTile(newX, newY)));
		System.out.println("(" + newX + ", " + newY + ")");
		sync();
	}
	
	public void carry(Carryable itemCarried) {
		this.itemCarried = itemCarried;
		((PlayerController)controller).setCarrying(true);
	}
	
	public void drop() {
		this.itemCarried = null;
		((PlayerController)controller).setCarrying(false);
	}
	
	public void attack() {
		// TODO: Attack logic
		System.out.println("Attack!");
		((PlayerController)controller).setAttacking(true);
	}
	
	public void hit() {
		// TODO: Hit logic
		System.out.println("Hit!");
		((PlayerController)controller).setHit(true);
	}
	
	public void die() {
		// TODO: Die logic
		System.out.println("Die!");
		((PlayerController)controller).setDead(true);
	}

	@Override
	protected void sync() {
		((PlayerController)controller).setHP(currentHP);
		((PlayerController)controller).setFacingDirection(facingDir);
		((PlayerController)controller).setMoving(moving);
	}
}
