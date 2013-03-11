package com.theboxbrigade.quantumchaos.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.Position;
import com.theboxbrigade.quantumchaos.Tile;
import com.theboxbrigade.quantumchaos.TileManager;
import com.theboxbrigade.quantumchaos.models.Model;
import com.theboxbrigade.quantumchaos.models.PlayerModel;
import com.theboxbrigade.quantumchaos.views.PlayerView;
import com.theboxbrigade.quantumchaos.views.View;

public class PlayerController extends ObjectController {
	private static final int DIRECTION_NORTH = 0;
	private static final int DIRECTION_EAST = 1;
	private static final int DIRECTION_SOUTH = 2;
	private static final int DIRECTION_WEST = 3;
	protected int maxHP = 4;
	protected int currentHP = 4;
	protected Position position;
	protected int facingDir = DIRECTION_NORTH;
	protected int keyPressed;
	protected boolean moving;
	protected boolean carrying;
	protected boolean sliding;
	protected boolean attacking;
	protected boolean hit;
	protected boolean dead;
	protected TileManager tileManager;
	
	public PlayerController() {
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
	
	public void setFacingDirection(int direction) {
		this.facingDir = direction;
	}
	
	public int facingDirection() {
		return facingDir;
	}
	
	public void processInput(int keyPressed) {
		processInput(keyPressed,null);
	}
	
	public void processInput(int keyPressed, Tile tile) {
		switch (keyPressed) {
		case DIRECTION_NORTH:	if (facingDir == DIRECTION_NORTH) {
									((PlayerModel)model).move(DIRECTION_NORTH);
								} else {
									((PlayerModel)model).face(DIRECTION_NORTH);
								}
								break;
		case DIRECTION_EAST:	if (facingDir == DIRECTION_EAST) {
									((PlayerModel)model).move(DIRECTION_EAST);
								} else {
									((PlayerModel)model).face(DIRECTION_EAST);
								}
								break;
		case DIRECTION_SOUTH:	if (facingDir == DIRECTION_SOUTH) {
									((PlayerModel)model).move(DIRECTION_SOUTH);
								} else {
									((PlayerModel)model).face(DIRECTION_SOUTH);
								}
								break;
		case DIRECTION_WEST:	if (facingDir == DIRECTION_WEST) {
									((PlayerModel)model).move(DIRECTION_WEST);
								} else {
									((PlayerModel)model).face(DIRECTION_WEST);
								}
								break;
	}
	}
	
	public boolean isMoving() { return moving; }
	public void setMoving(boolean moving) { this.moving = moving; }
	
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
		// TODO Auto-generated method stub
		((PlayerView)view).update(0, facingDir);
	}
	
	public SpriteBatch getViewSpriteBatch() {
		return ((PlayerView)view).getSpriteBatch();
	}
	
	@Override
	public void setState(int state) {
		// TODO Auto-generated method stub
		
	}
	
}
