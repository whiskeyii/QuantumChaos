package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.theboxbrigade.quantumchaos.general.Globals;

public class Main {
	private static int WIDTH = Globals.GAME_WIDTH;
	private static int HEIGHT = Globals.GAME_HEIGHT;
	private static String version = "v0.2";
	private static String title = "Quantum Chaos " + version;
	private static boolean useGL2 = false;
	
	public static void main(String[] args) {
		new LwjglApplication(new QuantumChaos(), title, WIDTH, HEIGHT, useGL2);
	}
}