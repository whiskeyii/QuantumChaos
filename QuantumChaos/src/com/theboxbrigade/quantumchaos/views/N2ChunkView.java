package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.controllers.N2ChunkController;
import com.theboxbrigade.quantumchaos.general.Assets;

public class N2ChunkView extends View {
	protected Sprite chunk = Assets.chunkLarge;
	
	public N2ChunkView(int type) {
		setType(type);
	}

	@Override
	public void update(int state) {
		update(state, 0, 0);
	}
	
	public void update(int state, float x, float y) {
		draw(chunk, x, y);
	}

	public void setType(int type) {
		switch (type) {
			case N2ChunkController.LARGE: 	chunk = Assets.chunkLarge;
											break;
			case N2ChunkController.MEDIUM:	chunk = Assets.chunkMedium;
											break;
			case N2ChunkController.SMALL:	chunk = Assets.chunkSmall;
											break;
		}
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}
}