package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Entity {

	// Init Sprite
	Animation sprite, up, down, left, right, idle, lDeath, uDeath, rDeath, dDeath; // needs idle image for shits n giggles.
	Animation idleU, idleD, idleL, idleR;
	// Damage Animations
	Animation spriteDamage, bloodSplat1;
	// sprite location
	float x = 0f, y = 0f;
	int faceingX = 0;
	int faceingY = 0;
	// sprite size
	int spritePicSize = 16;
	// Temp Bounding Box Info for Testing
	Rectangle rec = new Rectangle(x, y, 32f, 32f);
	boolean dead = false;
	// health
	private int health = 3;
	
	// spawn location
	float spawnX = 0f, spawnY = 0f;

	float destX = 0, destY = 0;

	float runSpeed = 0.8f;
	// Base walk speed 0.05f?
	float curWalkSpeed = 0.04f;
	float walkSpeed = 0.05f;
	
	int idleTimerCur = 0;
	int idleTimerMin = 280;
	int idleTimerMax = 350;

	// random number generator.
	//Random randomGenerator = new Random();
	// 0 - 199
	//int rand = 0;
	// randomGenerator.nextInt(200);

	// map height & width - find a way tp fix this later
	int mapWidth = 640;
	int mapHeight = 480;

	// movement passes, to make it smother.
	int pass = 0;
	// direction need to go
	boolean needGoUp = false, needGoLeft = false, needGoDown = false, needGoRight = false, canIdle = false;
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
		// big - little should equal this number --^ of zombies to select from.
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
		// Idle Animations
		Image [] movementIdleU = {new Image("assets/zomb"+R+"_iD1.png")};
		Image [] movementIdleD = {new Image("assets/zomb"+R+"_iD1.png")};
		Image [] movementIdleL = {new Image("assets/zomb"+R+"_iD1.png")};
		Image [] movementIdleR = {new Image("assets/zomb"+R+"_iD1.png")};
		// TODO: / COULD REPLACE THIS SHIT WITH A SIMPLE == eg deathLeft = deathUp.
		// lDeath, uDeath, rDeath, dDeath - 
		Image [] deathUp = {new Image("assets/zomb"+R+"_Death1.png"), new Image("assets/zomb"+R+"_Death2.png"),
				new Image("assets/zomb"+R+"_Death3.png"), new Image("assets/zomb"+R+"_Death4.png"), new Image("assets/zomb"+R+"_Death5.png"), 
				new Image("assets/zomb"+R+"_Death6.png"), new Image("assets/zomb"+R+"_Death7.png"), new Image("assets/zomb"+R+"_Death8.png")};
		Image [] deathDown = {new Image("assets/zomb"+R+"_Death1.png"), new Image("assets/zomb"+R+"_Death2.png"),
				new Image("assets/zomb"+R+"_Death3.png"), new Image("assets/zomb"+R+"_Death4.png"), new Image("assets/zomb"+R+"_Death5.png"), 
				new Image("assets/zomb"+R+"_Death6.png"), new Image("assets/zomb"+R+"_Death7.png"), new Image("assets/zomb"+R+"_Death8.png")};
		Image [] deathLeft = {new Image("assets/zomb"+R+"_Death1.png"), new Image("assets/zomb"+R+"_Death2.png"),
				new Image("assets/zomb"+R+"_Death3.png"), new Image("assets/zomb"+R+"_Death4.png"), new Image("assets/zomb"+R+"_Death5.png"), 
				new Image("assets/zomb"+R+"_Death6.png"), new Image("assets/zomb"+R+"_Death7.png"), new Image("assets/zomb"+R+"_Death8.png")};
		Image [] deathRight = {new Image("assets/zomb"+R+"_Death1.png"), new Image("assets/zomb"+R+"_Death2.png"),
				new Image("assets/zomb"+R+"_Death3.png"), new Image("assets/zomb"+R+"_Death4.png"), new Image("assets/zomb"+R+"_Death5.png"), 
				new Image("assets/zomb"+R+"_Death6.png"), new Image("assets/zomb"+R+"_Death7.png"), new Image("assets/zomb"+R+"_Death8.png")};
		
		// load damage Animations
		Image [] hitBloodSplat1 = {new Image("assets/bloodSplat4.png"), new Image("assets/bloodSplat1.png"),
				new Image("assets/bloodSplat1.png"), new Image("assets/bloodSplat2.png"), new Image("assets/bloodSplat2.png"), 
				new Image("assets/bloodSplat3.png"), new Image("assets/bloodSplat4.png"), new Image("assets/bloodSplat5.png")};
		
		// Rotate each movement image in the Animation
		for (int i = 0; i< movementUp.length; i++) {
			movementDown[i].rotate(180f);
			movementLeft[i].rotate(270f);
			movementRight[i].rotate(90f);
		}
		// rotate each idle image
		for (int i = 0; i< movementIdleU.length; i++) {
			movementIdleD[i].rotate(180f);
			movementIdleL[i].rotate(270f);
			movementIdleR[i].rotate(90f);
		}
		// rotate each death image in the animation
		for (int i = 0; i< deathUp.length; i++) {
			deathDown[i].rotate(180f);
			deathLeft[i].rotate(270f);
			deathRight[i].rotate(90f);
		}
		
		int [] idleDur 			= {200};
		int [] duration	 		= {200, 200, 200, 200, 200, 200, 200, 200};
		int [] deathDuration	 		= {200, 200, 200, 200, 200, 200, 200, 200};
		int [] damageDuration 	= {200, 50, 50, 50, 50, 50, 50, 200};

		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false); 
		// idle Image
		idleU = new Animation(movementIdleU, idleDur, false);
		idleD = new Animation(movementIdleD, idleDur, false);
		idleL = new Animation(movementIdleL, idleDur, false);
		idleR = new Animation(movementIdleR, idleDur, false);
		// Death animations
		// TODO: Replace with DEATH ANIMATIONS
		uDeath = new Animation(deathUp, deathDuration, false);
		dDeath = new Animation(deathDown, deathDuration, false);
		lDeath = new Animation(deathLeft, deathDuration, false);
		rDeath = new Animation(deathRight, deathDuration, false);
		// Damage Animations
		bloodSplat1 = new Animation(hitBloodSplat1, damageDuration, false);

		// Starting orientation of the sprite.
		R = (int) ((Math.random() * (5 - 1)) + 1); 
		if (R == 1) {
			sprite = idleU;
		}
		if (R == 2) {
			sprite = idleD;
		}
		if (R == 3) {
			sprite = idleL;
		}
		if (R == 4) {
			sprite = idleR;
		}
		
		// TODO STOP THIS FROM PLAYING IMEDIETLY
		spriteDamage = bloodSplat1;
		//spriteDamage.stopAt(8);
		spriteDamage.setCurrentFrame(7);
		//spriteDamage.stopAt(7);
	}

	void update(int delta, MapFactory mapFactory) {
		// TODO: add a thingy where it checks if at dest, create new dest, else move tawords dest.
		// TODO add another 4 vars that check what he is doing and bool, busy, so if busy, keep doing
		// what his doinmg, else change to another pass on what his doing? makes sence in my head
		// maby create a percentage? if closer then 90% change dest? hmm... 

		// if just moved up/down/left/right etc, move in same direction? else change direction? else drool?
		float nextX = 0;
		float nextY = 0;
		
		if (!dead) {
			if (needGoUp) {
				sprite = up;
				nextX = x+spritePicSize;
				nextY = (y+spritePicSize) - delta * collisionPaddingDistance;
				if (!mapFactory.isBlocked(nextX, nextY)) {
					//sprite.update(delta);
					moveUp(delta);
					needGoUp = true;
				}
				else {
					needGoUp = false;
				}
			}
	
			if (needGoDown) {
				sprite = down;
				nextX = x+spritePicSize;
				nextY = (y+spritePicSize) + delta * collisionPaddingDistance;
				if (!mapFactory.isBlocked(nextX, nextY)) {
					//sprite.update(delta);
					moveDown(delta);
					needGoDown = true;
				}
				else {
					needGoDown = false;
				}
			}
	
			if (needGoLeft) {
				sprite = left;
				nextX = (x+spritePicSize) - delta * collisionPaddingDistance;
				nextY = y+spritePicSize;
				if (!mapFactory.isBlocked(nextX, nextY)) {
					//sprite.update(delta);
					moveLeft(delta);
					needGoLeft = true;
				}
				else {
					needGoLeft = false;
				}
			}
	
			if (needGoRight) {
				sprite = right;
				nextX = (x+spritePicSize) + delta * collisionPaddingDistance;
				nextY = y+spritePicSize;
				if (!mapFactory.isBlocked(nextX, nextY)) {
					//sprite.update(delta);
					moveRight(delta);
					needGoRight = true;
				}
				else {
					needGoRight = false;
				}
			}
			
			if (!needGoLeft && !needGoRight && !needGoUp && !needGoDown) {
				if (faceingX == 1) {
					sprite = idleR;
				}
				if (faceingX == -1) {
					sprite = idleL;	
				}
				if (faceingY == 1) {
					sprite = idleD;
				}
				if (faceingY == -1) {
					sprite = idleU;
				}
			}
						
			// Move bounding box with player.
			rec.setX(x);
			rec.setY(y);
			
			// update animation after changes
			/*sprite.update(delta);
			spriteDamage.update(delta);
			spriteDamage.setLooping(false);*/
			//sprite.update(delta);
			
		}
		// maby need to put the update here? so the dead animation plays.
		// update animation after changes
		//sprite.update(delta);
		sprite.update(delta);
		spriteDamage.update(delta);
		spriteDamage.setLooping(false);
		if(dead) {
			sprite.setLooping(false);
		}

		// TODO: Seems ok, but needs work!
		// add idle, maby idle timer, idk, something looks like its missing.
		// int R = (Math.random() * (100 - 10)) + 10; 
		// This gives you a random number in between 10 (inclusive) and 100 (exclusive)
		
		//int R = (int) ((Math.random() * (idleTimerMax - idleTimerMin)) + idleTimerMin); 
		//if(pass > R) {
		if(pass > idleTimerCur) {
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
		if (!dead) {
			//sprite.draw((int)x, (int)y);
			// TODO Need to add change to damage render location
			bloodSplat1.draw(x, y);
		}
		sprite.draw((int)x, (int)y);
	}
	
	/*void renderDamage () {
		bloodSplat1.draw(x, y);
	}*/
	
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
		idleTimerCur = (int) ((Math.random() * (idleTimerMax - idleTimerMin)) + idleTimerMin);

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
	
	public void getHit() {
		// play blood splat?
		// register bloodsplat, then play it in the render?
		//bloodSplat1.draw(x, y);
		spriteDamage.restart();
		
		health--;
		System.out.println("BLOOD SPLAT");
		
		if (health <= 0) {
			dead = true;
			destroyEntity();
		}
	}
	
	public void destroyEntity() {
		//dead = true;
		// TODO: replace with death animations
		if (faceingX == 1) {
			sprite = rDeath;
		}
		if (faceingX == -1) {
			sprite = lDeath;
		}
		if (faceingY == 1) {
			sprite = dDeath;
		}
		if (faceingY == -1) {
			sprite = uDeath;
		}
		
		sprite.restart();
		sprite.stopAt(7);
		System.out.println("ddeeaaddd blegh");
		/*rec.setWidth(0);
		rec.setHeight(0);*/
		// play death animation? lalalala
		
	}

}
