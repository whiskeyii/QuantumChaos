package com.theboxbrigade.quantumchaos.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedAssets {
	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 1;
	
	private Animation robertWalkNorthAnim;
	private Texture robertWalkNorthSheet;
	private TextureRegion[] robertWalkNorthFrames;
	private TextureRegion robertWalkNorthCurrentFrame;
	private SpriteBatch spriteBatch;
	
	private float stateTime;
	
	public void create() {
		robertWalkNorthSheet = new Texture(Gdx.files.internal("data/images/robertWalkNorth.png"));
		TextureRegion[][] tmp = TextureRegion.split(robertWalkNorthSheet,
				robertWalkNorthSheet.getWidth() / FRAME_COLS,
				robertWalkNorthSheet.getHeight() / FRAME_ROWS);
		robertWalkNorthFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i=0; i<FRAME_ROWS; i++) {
			for (int j=0; j<FRAME_COLS; j++) {
				robertWalkNorthFrames[index++] = tmp[i][j];
			}
		}
		robertWalkNorthAnim = new Animation(0.25f, robertWalkNorthFrames);
		stateTime = 0f;
	}
	
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        robertWalkNorthCurrentFrame = robertWalkNorthAnim.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(robertWalkNorthCurrentFrame, 50, 50);
        spriteBatch.end();
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}
