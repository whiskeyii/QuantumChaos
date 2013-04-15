package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.Carryable;
import com.theboxbrigade.quantumchaos.controllers.Interactable;
import com.theboxbrigade.quantumchaos.controllers.PlayerController;
import com.theboxbrigade.quantumchaos.general.Assets;
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
	protected int oldState;
	protected int state;
	protected int facingDir;
	
	public PlayerModel(PlayerController controller) {
		this.controller = controller;
	}

	@Override
	public void face(int direction) {
		oldState = state;
		facingDir = direction;
		state = PlayerController.IDLE;
		sync();
	}

	@Override
	public boolean move(int direction) {
		face(direction);
		oldState = state;
		state = PlayerController.WALKING;
		boolean moved = false;
		switch (direction) {
			case Globals.NORTH: moved = ((PlayerController)controller).getPosition().shiftVerticallyBy(-1,true);
								break;
			case Globals.EAST:	moved = ((PlayerController)controller).getPosition().shiftHorizontallyBy(1,true);
								break;
			case Globals.SOUTH: moved = ((PlayerController)controller).getPosition().shiftVerticallyBy(1,true);
								break;
			case Globals.WEST: 	moved = ((PlayerController)controller).getPosition().shiftHorizontallyBy(-1,true);
								break;
		}
		sync();
		if (!moved) {
			Assets.step.stop();
			Assets.walkIntoWall.play();
		}
		else Assets.step.play();
		return moved;
	}
	
	public boolean slide(int direction) {
		face(direction);
		//oldState = state;
		state = PlayerController.SLIDING;
		boolean moved = false;
		switch (direction) {
			case Globals.NORTH: moved = ((PlayerController)controller).getPosition().shiftVerticallyBy(-1,true);
								break;
			case Globals.EAST:	moved = ((PlayerController)controller).getPosition().shiftHorizontallyBy(1,true);
								break;
			case Globals.SOUTH: moved = ((PlayerController)controller).getPosition().shiftVerticallyBy(1,true);
								break;
			case Globals.WEST: 	moved = ((PlayerController)controller).getPosition().shiftHorizontallyBy(-1,true);
								break;
		}
		sync();
		if (!moved) {
			Assets.slide.stop();
			Assets.walkIntoWall.play();
		}
		else Assets.slide.play();
		return moved;
	}
	
	public void interactWith(Interactable interactable) {
		oldState = state;
		state = PlayerController.INTERACTING;
		if (interactable.interactableType() == Interactable.PLANET
				|| interactable.interactableType() == Interactable.KEY)
			state = PlayerController.INIT_CARRYING;
		interactable.whenInteractedWith();
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
		oldState = state;
		state = PlayerController.ATTACKING;
	}
	
	public void hit() {
		oldState = state;
		state = PlayerController.HIT;
	}
	
	public void die() {
		oldState = state;
		state = PlayerController.DEAD;
	}

	@Override
	protected void sync() {
		((PlayerController)controller).setHP(currentHP);
		((PlayerController)controller).setState(state);
		if (oldState != state) ((PlayerController)controller).resetState();
		((PlayerController)controller).setFacing(facingDir);
	}
}
