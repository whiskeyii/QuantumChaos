package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.utils.Array;

public class WorldNotifier implements WorldListener {
	private Array<WorldListener> listeners;
	
	public WorldNotifier() {
		listeners = new Array<WorldListener>();
	}
	
	public void addListener(WorldListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void onTalkToSchrodinger() {
		for (WorldListener listener : listeners)
			listener.onTalkToSchrodinger();
	}

	@Override
	public void onOpenBox() {
		for (WorldListener listener : listeners)
			listener.onOpenBox();
	}
	
}
