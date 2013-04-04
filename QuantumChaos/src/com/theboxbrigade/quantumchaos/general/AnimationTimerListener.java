package com.theboxbrigade.quantumchaos.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.theboxbrigade.quantumchaos.controllers.ObjectController;

/**
 * Currently handles camera following Robert and advancing frames of AnimatedAssets
 * @author Jason
 */
public class AnimationTimerListener implements ActionListener {
	private static final float CAMERA_STEP_X = 2f / 5.0f;
	private static final float CAMERA_STEP_Y = 1f / 5.0f;
	private OrthographicCamera camera;
	protected Array<ObjectController> objects = new Array<ObjectController>();
	private boolean walking, teleporting;
	private int state;
	private int count = 0;
	public boolean blockInput = false;
	
	public AnimationTimerListener(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (walking) {
			teleporting = false;
			blockInput = true;
			switch (state) {
				case 0: camera.translate(CAMERA_STEP_X, CAMERA_STEP_Y);
						break;
				case 1: camera.translate(CAMERA_STEP_X,-CAMERA_STEP_Y);
						break;
				case 2: camera.translate(-CAMERA_STEP_X,-CAMERA_STEP_Y);
						break;
				case 3: camera.translate(-CAMERA_STEP_X,CAMERA_STEP_Y);
						break;
			}
			AnimatedAssets.advanceCurrentFrame(state);
			camera.update();
			if (++count >= AnimatedAssets.numWalkingFrames) {
				count = 0;
				walking = false;
				blockInput = false;
			}
			for (int i = 0; i < objects.size; i++) {
				objects.get(i).processInput(state);
			}
		} else if (teleporting) {
			walking = false;
			blockInput = true;
			//camera.translate(0,CAMERA_STEP_Y);
			// AnimatedAssets.advanceCurrentFrame(Globals.TELEPORT);
			camera.update();
			if (++count >= AnimatedAssets.numTeleportingFrames) {
				count = 0;
				teleporting = false;
				blockInput = false;
			}
		}
	}

	public void setMoving(boolean walking, int direction) {
		this.walking = walking;
		this.state = direction;
	}
	
	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
	}
	
	public boolean isWalking() {
		return walking;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public void addObject(ObjectController object) {
		objects.add(object);
	}
}
