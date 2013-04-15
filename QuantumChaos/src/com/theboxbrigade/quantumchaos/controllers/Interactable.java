package com.theboxbrigade.quantumchaos.controllers;

public interface Interactable {
	public static final int KEY = 0;
	public static final int DOOR = 1;
	public static final int JOURNAL_PAGE = 2;
	public static final int BOX = 3;
	public static final int SCHRODINGER = 4;
	public static final int PLANET = 5;
	public static final int PLANET_SLOT = 6;
	public static final int STALAGMITE = 7;
	public static final int N2_CHUNK = 8;
	public static final int FORCE_CHUNK = 9;
	
	public void whenInteractedWith();
	
	public boolean isInteractable();
	
	public int interactableType();
}
