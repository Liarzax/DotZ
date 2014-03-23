package com.au.Stark.Dotz.main;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Entity {
	
	// Init Sprite
	Animation sprite, up, down, left, right, idle; // needs idle image for shits n giggles.
	// sprite location
	float x = 0f, y = 0f;
	int faceingX = 0;
	int faceingY = 0;
	
	float destX = 0, destY = 0;
	
	float runSpeed = 0.8f;
   	float curWalkSpeed = 0.1f;
   	float walkSpeed = 0.1f;
   	
   	// random number generator.
   	Random randomGenerator = new Random();
   	// 0 - 199
 	int rand = 0;
 	// randomGenerator.nextInt(200);
   	
 	// map height & width
 	int mapHeight = 300;
 	int mapWidth = 300;
 	
 	// movement passes, to make it smother.
 	int pass = 0;
	
	public Entity() {
		
	}
	
	void initEntity(int locationX, int locationY) throws SlickException {
		// spawn at
		x = locationX;
		y = locationY;
		// create new destination
		setNewDestination(mapHeight, mapWidth);
		
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
	   sprite = left;
	}
	
	void update(int delta) {
		// TODO: add a thingy where it checks if at dest, create new dest, else move tawords dest.
		
		
		// dest up
		if (destY < y) {
			moveUp(delta);
			//pass = 0;
		}
        
        // dest down
		if (destY > y) {
			moveDown(delta);
			//pass = 0;
		}
		
		// dest left;
		if (destX < x) {
			moveLeft(delta);
			//pass = 0;
		}
        
        // dest right
		if (destX > x) {
			moveRight(delta);
			//pass = 0;
		}
		
		// TODO: Seems ok, but needs work!
		// add idle, maby idle timer, idk, something looks like its missing.
		
		/*// dest reached
		if ((int)x == (int)destX && (int)y == (int)destY) {
			System.out.println("DestX = "+destX+" DestY = "+destY+" Reached!");
			setNewDestination(mapHeight, mapWidth);
		}
        
		//System.out.println("curX = "+x+" curY = "+y);
		//System.out.println("DestX = "+destX+" DestY = "+destY);
		
		if(pass > 0) {
			System.out.println("Reduce Speed!");
			//curWalkSpeed -= 0.005f;
		}
		if(pass == 0) {
			//System.out.println("Return Speed!");
			curWalkSpeed = walkSpeed;
		}*/
		if(pass > 150) {
			//System.out.println("DestX = "+destX+" DestY = "+destY+" Reached!");
			setNewDestination(mapHeight, mapWidth);
			pass = 0;
		}
		pass++;
		//System.out.println("Pass "+pass+" Done!");
		
	}
	
	void render () {
		sprite.draw((int)x, (int)y);
	}
	
	private void setNewDestination(int heightLimmiter, int widthLimmiter) {
		System.out.println("Setting New Destination");
		destY = randomGenerator.nextInt(heightLimmiter);
		destX = randomGenerator.nextInt(widthLimmiter);
		System.out.println("DestX = "+destX+" DestY = "+destY);
		
	}
	
	private void moveUp(int delta) {
		sprite = up;
        y -= delta * curWalkSpeed;
        faceingX = 0;
        faceingY = -1;
	}
	
	private void moveDown(int delta) {
		sprite = down;
        y += delta * curWalkSpeed;
        faceingX = 0;
        faceingY = 1;
	}
	
	private void moveLeft(int delta) {
		sprite = left;
        x -= delta * curWalkSpeed;
		faceingX = -1;
        faceingY = 0;
	}
	
	private void moveRight(int delta) {
		sprite = right;
        x += delta * curWalkSpeed;
        faceingX = 1;
        faceingY = 0;
	}

}
