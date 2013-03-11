package com.theboxbrigade.quantumchaos.models;

import com.theboxbrigade.quantumchaos.controllers.Controller;

public abstract class Model {
	protected Controller controller;
	
	protected abstract void sync();
}
