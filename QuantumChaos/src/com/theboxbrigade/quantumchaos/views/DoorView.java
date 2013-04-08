package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

public class DoorView extends View {
	protected Sprite door;
	
	public DoorView(int type) {
		switch (type) {
			case Globals.NORTH: door = Assets.doorN; break;
			case Globals.WEST: 	door = Assets.doorW; break;
		}
	}
	
	@Override
	public void update(int state) {
		update(0, 0, state);
	}
	
	public void update(float x, float y, int state) {
		draw(door, x, y);
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}

}
