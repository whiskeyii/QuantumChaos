package com.theboxbrigade.quantumchaos.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.theboxbrigade.quantumchaos.controllers.ObjectController;

public class AnimationTimerListener implements ActionListener {
	private static final float CAMERA_STEP_X = 2f / 5.0f;
	private static final float CAMERA_STEP_Y = 1f / 5.0f;
	private OrthographicCamera camera;
	protected Array<ObjectController> objects = new Array<ObjectController>();
	private boolean walking;
	private int direction;
	private int count = 0;
	public boolean blockInput = false;
	
	public AnimationTimerListener(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (walking) {
			blockInput = true;
			switch (direction) {
				case 0: camera.translate(CAMERA_STEP_X, CAMERA_STEP_Y);
						break;
				case 1: camera.translate(CAMERA_STEP_X,-CAMERA_STEP_Y);
						break;
				case 2: camera.translate(-CAMERA_STEP_X,-CAMERA_STEP_Y);
						break;
				case 3: camera.translate(-CAMERA_STEP_X,CAMERA_STEP_Y);
						break;
			}
			camera.update();
			count++;
			if (count > 4) {
				count = 0;
				walking = false;
				blockInput = false;
			}
			for (int i = 0; i < objects.size; i++) {
				objects.get(i).processInput(direction);
			}
		}
	}

	public void setMoving(boolean walking, int direction) {
		this.walking = walking;
		this.direction = direction;
	}
	
	public void setWalking(boolean walking) {
		this.walking = walking;
	}
	
	public boolean isWalking() {
		return walking;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void addObject(ObjectController object) {
		objects.add(object);
	}
}
