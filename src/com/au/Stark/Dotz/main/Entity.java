package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Entity {
	
	// Init Sprite
	Animation sprite, up, down, left, right; // needs idle image for shits n giggles.
	// sprite location
	float x = 0f, y = 0f;
	int faceingX = 0;
	int faceingY = 0;
	float speed = 0.1f;
	
	
	public Entity() {
		
	}
	
	void initEntity(int locationX, int locationY) throws SlickException {
		// spawn at
		x = locationX;
		y = locationY;
		
		//Load Sprite
 	   Image [] movementUp = {new Image("assets/zomb1_UD1.png"), new Image("assets/zomb1_UD2.png"),
			   new Image("assets/zomb1_UD3.png"), new Image("assets/zomb1_UD4.png"), new Image("assets/zomb1_UD5.png"), 
			   new Image("assets/zomb1_UD6.png"), new Image("assets/zomb1_UD7.png"), new Image("assets/zomb1_UD8.png")};
	   Image [] movementDown = {new Image("assets/zomb1_UD1.png").getFlippedCopy(false, true), new Image("assets/zomb1_UD2.png").getFlippedCopy(false, true),
			   new Image("assets/zomb1_UD3.png").getFlippedCopy(false, true), new Image("assets/zomb1_UD4.png").getFlippedCopy(false, true), new Image("assets/zomb1_UD5.png").getFlippedCopy(false, true), 
			   new Image("assets/zomb1_UD6.png").getFlippedCopy(false, true), new Image("assets/zomb1_UD7.png").getFlippedCopy(false, true), new Image("assets/zomb1_UD8.png").getFlippedCopy(false, true)};
	   Image [] movementLeft = {new Image("assets/zomb1_LR1.png").getFlippedCopy(true, false), new Image("assets/zomb1_LR2.png").getFlippedCopy(true, false),
			   new Image("assets/zomb1_LR3.png").getFlippedCopy(true, false), new Image("assets/zomb1_LR4.png").getFlippedCopy(true, false), new Image("assets/zomb1_LR5.png").getFlippedCopy(true, false), 
			   new Image("assets/zomb1_LR6.png").getFlippedCopy(true, false), new Image("assets/zomb1_LR7.png").getFlippedCopy(true, false), new Image("assets/zomb1_LR8.png").getFlippedCopy(true, false)};
	   Image [] movementRight = {new Image("assets/zomb1_LR1.png"), new Image("assets/zomb1_LR2.png"),
			   new Image("assets/zomb1_LR3.png"), new Image("assets/zomb1_LR4.png"), new Image("assets/zomb1_LR5.png"), 
			   new Image("assets/zomb1_LR6.png"), new Image("assets/zomb1_LR7.png"), new Image("assets/zomb1_LR8.png")};
	   // Idle Image.
	   int [] duration = {200, 200, 200, 200, 200, 200, 200, 200};
	   
	   up = new Animation(movementUp, duration, false);
	   down = new Animation(movementDown, duration, false);
	   left = new Animation(movementLeft, duration, false);
	   right = new Animation(movementRight, duration, false); 
	   // idle Image
	   
	   // Original orientation of the sprite. It will look right.
	   sprite = right;
	}
	
	void update() {
		
	}
	
	void render () {
		sprite.draw((int)x, (int)y);
		
	}

}
