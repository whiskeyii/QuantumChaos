package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.N2ChunkController;
import com.theboxbrigade.quantumchaos.general.Assets;
import com.theboxbrigade.quantumchaos.general.Globals;

public class N2ChunkModel extends Model {
	protected int state;
	
	public N2ChunkModel(N2ChunkController controller) {
		this.controller = controller;
	}
	
	public boolean move(int direction) {
		state = N2ChunkController.SLIDING;
		boolean moved = false;
		switch (direction) {
			case Globals.NORTH: moved = ((N2ChunkController)controller).getPosition().shiftVerticallyBy(-1,true);
								break;
			case Globals.EAST:	moved = ((N2ChunkController)controller).getPosition().shiftHorizontallyBy(1,true);
								break;
			case Globals.SOUTH: moved = ((N2ChunkController)controller).getPosition().shiftVerticallyBy(1,true);
								break;
			case Globals.WEST: 	moved = ((N2ChunkController)controller).getPosition().shiftHorizontallyBy(-1,true);
								break;
		}
		sync();
		if (!moved) {
			Assets.slide.stop();
			Assets.walkIntoWall.play();
		}
		else Assets.slide.play();
		return moved;
	}

	@Override
	protected void sync() {
		((N2ChunkController)controller).setState(state);
	}
}
