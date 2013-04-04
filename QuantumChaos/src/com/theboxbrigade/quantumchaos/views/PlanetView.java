package com.theboxbrigade.quantumchaos.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.theboxbrigade.quantumchaos.general.Assets;

public class PlanetView extends View {
	public static final int SUN = 0;
	public static final int MERCURY = 1;
	public static final int VENUS = 2;
	public static final int EARTH = 3;
	public static final int MARS = 4;
	public static final int JUPITER = 5;
	public static final int SATURN = 6;
	public static final int URANUS = 7;
	public static final int NEPTUNE = 8;
	protected int texture;
	protected Sprite planet;
	
	public PlanetView(int texture) {
		this.texture = texture;
		switch (texture) {
			case SUN:		planet = Assets.SUN; break;
			case MERCURY:	planet = Assets.MERCURY; break;
			case VENUS:		planet = Assets.VENUS; break;
			case EARTH:		planet = Assets.EARTH; break;
			case MARS:		planet = Assets.MARS; break;
			case JUPITER:	planet = Assets.JUPITER; break;
			case SATURN:	planet = Assets.SATURN; break;
			case URANUS:	planet = Assets.URANUS; break;
			case NEPTUNE:	planet = Assets.NEPTUNE; break;
		}
	}
	
	@Override
	public void update(int state) {
		update(0,0,state);
	}
	
	public void update(float x, float y, int state) {
		draw(planet, x, y);
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void draw(Sprite region, float x, float y) {
		spriteBatch.draw(region, x, y);
	}
}
