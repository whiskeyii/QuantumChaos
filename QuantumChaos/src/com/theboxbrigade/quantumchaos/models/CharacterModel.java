package com.theboxbrigade.quantumchaos.models;

public abstract class CharacterModel extends Model {

	public abstract void face(int direction);
	
	public abstract boolean move(int direction);
}
