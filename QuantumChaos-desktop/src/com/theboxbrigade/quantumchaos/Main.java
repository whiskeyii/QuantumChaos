package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	private static int WIDTH = 1024;
	private static int HEIGHT = 768;
	private static String version = "v0.1.0";
	private static String title = "Quantum Chaos " + version;
	private static boolean useGL2 = false;
	
	public static void main(String[] args) {
		new LwjglApplication(new QuantumChaos(), title, WIDTH, HEIGHT, useGL2);
	}
}