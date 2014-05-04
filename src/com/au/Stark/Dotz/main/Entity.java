package com.au.Stark.Dotz.main;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Entity {

	// Init Sprite
	Animation sprite, up, down, left, right, idle; // needs idle image for shits n giggles.
	// sprite location
	float x = 0f, y = 0f;
	int faceingX = 0;
	int faceingY = 0;
	// sprite size
	int spritePicSize = 16;
	// Temp Bounding Box Info for Testing
	Rectangle rec = new Rectangle(x, y, 32f, 32f);
	boolean dead = false;
	
	// spawn location
	float spawnX = 0f, spawnY = 0f;

	float destX = 0, destY = 0;

	float runSpeed = 0.8f;
	// Base walk speed 0.05f?
	float curWalkSpeed = 0.04f;
	float walkSpeed = 0.05f;

	// random number generator.
	Random randomGenerator = new Random();
	// 0 - 199
	int rand = 0;
	// randomGenerator.nextInt(200);

	// map height & width - find a way tp fix this later
	int mapWidth = 640;
	int mapHeight = 480;

	// movement passes, to make it smother.
	int pass = 0;
	// direction need to go
	boolean needGoUp = false, needGoLeft = false, needGoDown = false, needGoRight = false;
	// direction just moved
	//boolean justMovedUp = false, justMovedLeft = false, justMovedDown = false, justMovedRight = false;

	// temp for collision
	private float collisionPaddingDistance = 0.95f;

	public Entity() {

	}

	void initEntity(int locationX, int locationY) throws SlickException {
		// spawn at
		x = locationX;
		y = locationY;
		// set original spawn location
		spawnX = locationX;
		spawnY = locationY;
		
		// create new destination
		// could have this shrunk to region? or give direct co-ordinates, or have if statements based on states?
		idleWonder();

		// sprite image / zombie style | currently 2 dif zombie types
		int R = (int) ((Math.random() * (3 - 1)) + 1); 
		//Load Sprite
		Image [] movementUp = {new Image("assets/zomb"+R+"_UD1.png"), new Image("assets/zomb"+R+"_UD2.png"),
				new Image("assets/zomb"+R+"_UD3.png"), new Image("assets/zomb"+R+"_UD4.png"), new Image("assets/zomb"+R+"_UD5.png"), 
				new Image("assets/zomb"+R+"_UD6.png"), new Image("assets/zomb"+R+"_UD7.png"), new Image("assets/zomb"+R+"_UD8.png")};
		Image [] movementDown = {new Image("assets/zomb"+R+"_UD1.png"), new Image("assets/zomb"+R+"_UD2.png"),
				new Image("assets/zomb"+R+"_UD3.png"), new Image("assets/zomb"+R+"_UD4.png"), new Image("assets/zomb"+R+"_UD5.png"), 
				new Image("assets/zomb"+R+"_UD6.png"), new Image("assets/zomb"+R+"_UD7.png"), new Image("assets/zomb"+R+"_UD8.png")};
		Image [] movementLeft = {new Image("assets/zomb"+R+"_UD1.png"), new Image("assets/zomb"+R+"_UD2.png"),
				new Image("assets/zomb"+R+"_UD3.png"), new Image("assets/zomb"+R+"_UD4.png"), new Image("assets/zomb"+R+"_UD5.png"), 
				new Image("assets/zomb"+R+"_UD6.png"), new Image("assets/zomb"+R+"_UD7.png"), new Image("assets/zomb"+R+"_UD8.png")};
		Image [] movementRight = {new Image("assets/zomb"+R+"_UD1.png"), new Image("assets/zomb"+R+"_UD2.png"),
				new Image("assets/zomb"+R+"_UD3.png"), new Image("assets/zomb"+R+"_UD4.png"), new Image("assets/zomb"+R+"_UD5.png"), 
				new Image("assets/zomb"+R+"_UD6.png"), new Image("assets/zomb"+R+"_UD7.png"), new Image("assets/zomb"+R+"_UD8.png")};
		
		// Idle ImageUD & LR? man, there has to be a way to rotate this stuff.
		/*Image [] idle = {new Image("assets/zomb"+R+"_UD1.png")};
		idle[1].rotate(2f);*/
		for (int i = 0; i< movementUp.length; i++) {
			movementDown[i].rotate(180f);
			movementLeft[i].rotate(270f);
			movementRight[i].rotate(90f);
		}
		
		int [] duration = {200, 200, 200, 200, 200, 200, 200, 200};

		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false); 
		// idle Image

		// Starting orientation of the sprite.
		sprite = left;
	}

	void update(int delta, MapFactory mapFactory) {
		// TODO: add a thingy where it checks if at dest, create new dest, else move tawords dest.
		// TODO add another 4 vars that check what he is doing and bool, busy, so if busy, keep doing
		// what his doinmg, else change to another pass on what his doing? makes sence in my head
		// maby create a percentage? if closer then 90% change dest? hmm... 

		// if just moved up/down/left/right etc, move in same direction? else change direction? else drool?
		float nextX = 0;
		float nextY = 0;
		
		if (needGoUp) {
			sprite = up;
			nextX = x+spritePicSize;
			nextY = (y+spritePicSize) - delta * collisionPaddingDistance;
			if (!mapFactory.isBlocked(nextX, nextY)) {
				//sprite.update(delta);
				moveUp(delta);
			}
		}

		if (needGoDown) {
			sprite = down;
			nextX = x+spritePicSize;
			nextY = (y+spritePicSize) + delta * collisionPaddingDistance;
			if (!mapFactory.isBlocked(nextX, nextY)) {
				//sprite.update(delta);
				moveDown(delta);
			}
		}

		if (needGoLeft) {
			sprite = left;
			nextX = (x+spritePicSize) - delta * collisionPaddingDistance;
			nextY = y+spritePicSize;
			if (!mapFactory.isBlocked(nextX, nextY)) {
				//sprite.update(delta);
				moveLeft(delta);
			}
		}

		if (needGoRight) {
			sprite = right;
			nextX = (x+spritePicSize) + delta * collisionPaddingDistance;
			nextY = y+spritePicSize;
			if (!mapFactory.isBlocked(nextX, nextY)) {
				//sprite.update(delta);
				moveRight(delta);
			}
		}
		// update animation after changes
		sprite.update(delta);
		// Move bounding box with player.
		rec.setX(x);
		rec.setY(y);

		// TODO: Seems ok, but needs work!
		// add idle, maby idle timer, idk, something looks like its missing.
		// int R = (Math.random() * (100 - 10)) + 10; 
		// This gives you a random number in between 10 (inclusive) and 100 (exclusive)
		
		int R = (int) ((Math.random() * (150 - 60)) + 60); 
		if(pass > R) {
			needGoUp = false;
			needGoLeft = false;
			needGoDown = false;
			needGoRight = false;
			
			//System.out.println("DestX = "+destX+" DestY = "+destY+" Reached!");
			if (!dead) {
				idleWonder();
			}
			
			pass = 0;
			
			/*justMovedUp = false;
			justMovedLeft = false;
			justMovedDown = false;
			justMovedRight = false;*/
		}
		pass++;
		//System.out.println("Pass "+pass+" Done!");

	}

	void render () {
		sprite.draw((int)x, (int)y);
	}
	
	private void idleWonder() {
		//System.out.println("Setting New Destination");
		boolean xSet = false;
		boolean ySet = false;
		
		// set dest x 
		while (!xSet) {
			destX = (int) ((Math.random() * ((spawnX*1.2) - (spawnX*0.2))) + (spawnX*0.2)); 
			if (destX < (mapWidth -1) && destX > (mapWidth - (mapWidth +1))) {
				xSet = true;
			}
			
			if (destX < x) {
				needGoLeft = true;
			}
			else if (destX > x) {
				needGoRight = true;
			}
			else {
				// drool.
			}
		}
		
		// set dest y
		while (!ySet) {
			destY = (int) ((Math.random() * ((spawnY*1.2) - (spawnY*0.2))) + (spawnY*0.2)); 
			if (destY < (mapHeight -1) && destY > (mapHeight - (mapHeight +1))) {
				ySet = true;
			}
			
			if (destY < y) {
				needGoUp = true;
			}
			else if (destY > y) {
				needGoDown = true;
			}
			else {
				// drool.
			}
		}
		
		//System.out.println("DestX = "+destX+" DestY = "+destY);

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
	
	public void destroyEntity() {
		dead = true;
		rec.setWidth(0);
		rec.setHeight(0);
	}

}
