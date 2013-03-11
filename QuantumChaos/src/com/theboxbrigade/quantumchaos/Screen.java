package com.theboxbrigade.quantumchaos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.theboxbrigade.quantumchaos.general.Globals;
import com.theboxbrigade.quantumchaos.general.Input;

/**
 * All screens should extend this Screen class.
 * 
 * @author Jason
 */
public abstract class Screen {
	private QuantumChaos game;
	public SpriteBatch spriteBatch;
	
	public void removed() {
		spriteBatch.dispose();
	}
	
	public final void init(QuantumChaos game) {
		this.game = game;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, Globals.GAME_WIDTH, Globals.GAME_HEIGHT, 0, -1, 1);
		
		spriteBatch = new SpriteBatch(100);
		spriteBatch.setProjectionMatrix(projection);
	}
	
	protected void setScreen(Screen screen) {
		game.setScreen(screen);
	}
	
	public void draw(TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		if (width < 0) width = -width;
		spriteBatch.draw(region, x, y, width, region.getRegionHeight());
	}
	
	public abstract void render();
	
	public void tick(Input input) {}
}
