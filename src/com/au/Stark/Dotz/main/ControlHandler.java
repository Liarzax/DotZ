package com.au.Stark.Dotz.main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class ControlHandler {

	private boolean downEsc = false;
	
	/*private boolean downMinus = false;
	private boolean debug = false;*/

	public ControlHandler() {

	}

	public void init(GameContainer container) {

	}

	public void update(GameContainer container) {
		// Check what keys are pressed / currently down.
		checkKeys(container);
		
		// do stuff according to keys.
		if(downEsc){
			container.exit();
		}
		
		
		
	}

	public void checkKeys(GameContainer container) {
		Input input = container.getInput();
		
		// Pressed Down / Held Down / Let Go.
		if(input.isKeyPressed(Keyboard.KEY_ESCAPE)) {
			downEsc = true;
		} 
		else if(input.isKeyDown(Keyboard.KEY_ESCAPE)) {
			downEsc = true;
		} 
		else {
			downEsc = false;
		}
		
		
		
	}
	
}
