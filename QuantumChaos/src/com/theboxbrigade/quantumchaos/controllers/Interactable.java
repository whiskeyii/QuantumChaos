package com.theboxbrigade.quantumchaos.controllers;

public interface Interactable {
	public static final int KEY = 0;
	public static final int DOOR = 1;
	public static final int BOX = 2;
	public static final int SCHRODINGER = 3;
	public static final int PLANET = 4;
	public static final int PLANET_SLOT = 5;
	public static final int STALAGMITE = 6;
	public static final int N2_CHUNK = 7;
	public static final int FORCE_CHUNK = 8;
	
	public void whenInteractedWith();
	
	public boolean isInteractable();
	
	public int interactableType();
}
