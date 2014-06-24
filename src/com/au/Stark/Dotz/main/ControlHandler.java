package com.au.Stark.Dotz.main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class ControlHandler {

	private boolean escPressed = false;
	private boolean wPressed = false, wHeld = false;
	
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
		if (escPressed){
			container.exit();
		}
		
		if (wHeld) {
			
		}
		else if (wPressed) {
			
		}
		
	}

	public void checkKeys(GameContainer container) {
		Input input = container.getInput();
		
		// Pressed Down / Held Down / Let Go.
		if (input.isKeyPressed(Keyboard.KEY_ESCAPE)) {
			escPressed = true;
		} 
		else if (input.isKeyDown(Keyboard.KEY_ESCAPE)) {
			//escPressed = true;
		} 
		else {
			escPressed = false;
		}
		
		// W key - (Move Up)
		if (input.isKeyPressed(Keyboard.KEY_W)) {
			wPressed = true;
		} 
		else if (input.isKeyDown(Keyboard.KEY_W)) {
			wHeld = true;
		} 
		else {
			wPressed = false;
			wHeld = false;
		}
		
	}
	
}
