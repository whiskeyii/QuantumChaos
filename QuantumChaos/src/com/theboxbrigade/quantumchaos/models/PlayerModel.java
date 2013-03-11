package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.Carryable;
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
	public boolean move(int direction) {
		moving = true;
		switch (direction) {
			// NORTH
			case 0: moving = ((PlayerController)controller).getPosition().shiftVerticallyBy(-1,true);
					break;
			// EAST
			case 1: moving = ((PlayerController)controller).getPosition().shiftHorizontallyBy(1,true);
					break;
			// SOUTH
			case 2: moving = ((PlayerController)controller).getPosition().shiftVerticallyBy(1,true);
					break;
			// WEST
			case 3: moving = ((PlayerController)controller).getPosition().shiftHorizontallyBy(-1,true);
					break;
		}
		sync();
		return moving;
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
