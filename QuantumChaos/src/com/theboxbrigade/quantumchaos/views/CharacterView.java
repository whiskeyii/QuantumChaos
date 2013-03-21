package com.theboxbrigade.quantumchaos.views;

public abstract class CharacterView extends View {
	private static final int IDLE = 0;
	private static final int WALK = 1;
	private static final int ATTACK = 2;
	private static final int HIT = 3;
	private static final int DIE = 4;
	
	@Override
	public void update(int state) {
		update(state, 0);
	}

	public abstract void update(int state, int facingDir);
	
	@Override
	public void updateAnimation(int state) {
		updateAnimation(state, 0);
	}

	public abstract void updateAnimation(int state, int facingDir);
}
