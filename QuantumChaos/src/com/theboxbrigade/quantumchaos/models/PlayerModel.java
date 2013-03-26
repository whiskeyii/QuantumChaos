package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.Carryable;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.Globals;

/**
 * Model object for the Player (Robert) - Performs actions based on logic passed to it 
 *    via PlayerController. It then passes the result of those actions back to the controller
 *    for the view to update accordingly.
 * @author Jason
 */
public class PlayerModel extends CharacterModel {
	protected Carryable itemCarried;
	protected int currentHP;
	protected int facingDir;
	protected boolean moving, teleporting;
	
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
		System.out.println(((PlayerController)controller).getPosition());
		sync();
		return moving;
	}
	
	public void teleport() {
		this.teleporting = true;
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
		((PlayerController)controller).setState(facingDir);
		((PlayerController)controller).setMoving(moving);
		((PlayerController)controller).setTeleporting(teleporting);
	}
}
