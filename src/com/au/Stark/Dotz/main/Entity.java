package com.au.Stark.Dotz.main;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Entity {

	// spawn location
	float spawnX = 0f, spawnY = 0f;
	// Init Sprite
	Animation sprite, up, down, left, right, idle; // needs idle image for shits n giggles.
	// sprite location
	float x = 0f, y = 0f;
	int faceingX = 0;
	int faceingY = 0;

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

	// temp for collision
	private float collisionPaddingDistance = 0.1f;

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

		// Starting orientation of the sprite. It will look right.
		sprite = left;
	}

	void update(int delta, MapFactory mapFactory) {
		// TODO: add a thingy where it checks if at dest, create new dest, else move tawords dest.
		// TODO add another 4 vars that check what he is doing and bool, busy, so if busy, keep doing
		// what his doinmg, else change to another pass on what his doing? makes sence in my head
		// maby create a percentage? if closer then 90% change dest? hmm... 
		
		// dest up
		if (destY < y) {
			/*if (!mapFactory.isBlocked(x, y - runSpeed)) {
				moveUp(delta);
            }*/
			if (!mapFactory.isBlocked(x, y - delta * collisionPaddingDistance)) {
				moveUp(delta);
			}
			//moveUp(delta);
			//pass = 0;
		}

		// dest down
		if (destY > y) {
			/*if (!mapFactory.isBlocked(x, y + runSpeed)) {
				moveDown(delta);
			}*/
			if (!mapFactory.isBlocked(x, y + mapFactory.getTileSize() + delta * collisionPaddingDistance)) {
				moveDown(delta);
			}
			//moveDown(delta);
			//pass = 0;
		}

		// dest left;
		if (destX < x) {
			/*if (!mapFactory.isBlocked(x - runSpeed, y)) {
				moveLeft(delta);
			}*/
			if (!mapFactory.isBlocked(x - delta * collisionPaddingDistance, y)) {
				moveLeft(delta);
			}
			//moveLeft(delta);
			//pass = 0;
		}

		// dest right
		if (destX > x) {
			/*if (!mapFactory.isBlocked(x + runSpeed, y)) {
				moveRight(delta);
			}*/
			if (!mapFactory.isBlocked(x + mapFactory.getTileSize() + delta * collisionPaddingDistance, y)) {
				moveRight(delta);
			}
			//moveRight(delta);
			//pass = 0;
		}

		// TODO: Seems ok, but needs work!
		// add idle, maby idle timer, idk, something looks like its missing.
		// int R = (Math.random() * (100 - 10)) + 10; 
		// This gives you a random number in between 10 (inclusive) and 100 (exclusive)
		
		int R = (int) ((Math.random() * (150 - 60)) + 60); 
		if(pass > R) {
			//System.out.println("DestX = "+destX+" DestY = "+destY+" Reached!");
			idleWonder();
			pass = 0;
		}
		pass++;
		//System.out.println("Pass "+pass+" Done!");

	}

	void render () {
		sprite.draw((int)x, (int)y);
	}
	
	private void idleWonder() {
		System.out.println("Setting New Destination");
		boolean xSet = false;
		boolean ySet = false;
		
		// set dest x 
		while (!xSet) {
			destX = (int) ((Math.random() * ((spawnX*0.2) - (spawnX/0.2))) + (spawnX/0.2)); 
			if (destX < (mapWidth -1) && destX > (mapWidth - (mapWidth +1))) {
				xSet = true;
			}
		}
		
		// set dest y
		while (!ySet) {
			destY = (int) ((Math.random() * ((spawnY*0.2) - (spawnY/0.2))) + (spawnY/0.2)); 
			if (destY < (mapHeight -1) && destY > (mapHeight - (mapHeight +1))) {
				ySet = true;
			}
		}
		
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
