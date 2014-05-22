package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Entity {

	// Init Sprite
	Animation sprite, up, down, left, right, idle, lDeath, uDeath, rDeath, dDeath; // needs idle image for shits n giggles.
	Animation idleU, idleD, idleL, idleR;
	// Damage Animations
	Animation spriteDamage, bloodSplat1;
	// sprite location
	float curX = 0f, curY = 0f, nextMapX = 0, nextMapY = 0, spawnX = 0f, spawnY = 0f, destX = 0, destY = 0, nextX = 0, nextY = 0;
	int faceingX = 0, faceingY = 0, health = 3;
	// sprite size - Later find away to just get this from the image & set center also eventuly
	int spriteSize = 32;
	int centerOfSprite = 16;
	// Temp Bounding Box Info for Testing
	Rectangle rec = new Rectangle(curX, curY, spriteSize, spriteSize);
	boolean dead = false, visible = true, ai = false, aiFollow = false, canRespawn = false;
	
	/*float runSpeed = 0.8f;
	// Base walk speed 0.05f?
	float curWalkSpeed = 0.04f;
	float walkSpeed = 0.05f;*/
	
	float Velocity = 0, acceleration = 0.04f, maxSpeed = 0.04f;
	//float curSpeed = 0;
	
	int idleTimerCur = 0, idleTimerMin = 280, idleTimerMax = 350;
	
	// Sight Range?
	//Shape circle = new Circle(curX+15,curY+15,50);
	// 50 is nice and close, but not enough for vision.
	float sightRange = 50f;
	Circle sightRadius = new Circle(curX, curY, sightRange);;
	
	
	// map height & width - find a way tp fix this later
	//int mapWidth = 640, mapHeight = 480;
	int mapWidth = 800, mapHeight = 640;
	

	// movement passes, to make it smother.
	int pass = 0;
	// direction need to go
	boolean needGoUp = false, needGoLeft = false, needGoDown = false, needGoRight = false, canIdle = false;

	// temp for collision
	// this should be renamed to something like mapCollisionPadding
	float collisionPaddingDistance = 0.35f;
	int entID = 0;
	public boolean reloading = false;

	public Entity() {

	}

	void initEntity(int locationX, int locationY) throws SlickException {
		// spawn at
		curX = locationX;
		curY = locationY;
		nextX = locationX;
		nextY = locationY;
		// Initialize collision ranges
		rec.setCenterX(curX + centerOfSprite);
		rec.setCenterY(curY + centerOfSprite);
		sightRadius.setCenterX(curX + centerOfSprite);
		sightRadius.setCenterY(curY + centerOfSprite);
		
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
		
		// maby one for the rec for collisions later
		
		// set the sight radius
		//sightRadius = new Circle(curX, curY, sightRange);
		
		
		// TODO STOP THIS FROM PLAYING IMEDIETLY
		spriteDamage = bloodSplat1;
		//spriteDamage.stopAt(8);
		spriteDamage.setCurrentFrame(7);
		//spriteDamage.stopAt(7);
		
		// set alive bool to true
		dead = false;
	}

	void update(int delta, MapFactory mapFactory, Entity players[]) {
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
				nextX = curX+centerOfSprite;
				nextY = (curY+centerOfSprite) - delta * collisionPaddingDistance;
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
				nextX = curX+centerOfSprite;
				nextY = (curY+centerOfSprite) + delta * collisionPaddingDistance;
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
				nextX = (curX+centerOfSprite) - delta * collisionPaddingDistance;
				nextY = curY+centerOfSprite;
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
				nextX = (curX+centerOfSprite) + delta * collisionPaddingDistance;
				nextY = curY+centerOfSprite;
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
						
			// Move bounding box with entity.
			rec.setCenterX(curX + centerOfSprite);
			rec.setCenterY(curY + centerOfSprite);
			// move sight with entity
			sightRadius.setCenterX(curX + centerOfSprite);
			sightRadius.setCenterY(curY + centerOfSprite);
						
		}

		// update animation after changes
		sprite.update(delta);
		spriteDamage.update(delta);
		spriteDamage.setLooping(false);
		if(dead) {
			sprite.setLooping(false);
			if (sprite.isStopped()) {
				canRespawn = true;
			}
		}
		if (canRespawn) {
			// attempt to create new entity in this dead ones place
			try {
				// add some random numbers in here for respawning.
				int tempX = 0;
				int tempY = 0;
				
				// TODO make this better, its passable but a bit spastic.
				// generate random x, y
				// loop untill outsid eplayer vision
				// check if x, y is blocked by map
				// if not spawn unit there
				boolean inSite = true;
				//int pass = 0;
				while(inSite) {
					//tempX = (int) ((Math.random() * ((mapWidth) - (10))) + (10)); 
					//tempY = (int) ((Math.random() * ((mapHeight) - (10))) + (10)); 
					tempX = (int) ((Math.random() * ((mapWidth-220) - (10))) + (10)); 
					tempY = (int) ((Math.random() * ((mapHeight-160) - (10))) + (10)); 
					
					//this.idleWonder();
					// check for collision with map first
					if (!mapFactory.isBlocked(tempX, tempY)) {
						this.rec.setCenterX((float)tempX);
						this.rec.setCenterX((float)tempY);
						
						for (int i = 0; i < players.length; i++) {
							if(this.rec.intersects(players[i].sightRadius)) { 
								inSite = true;
							}
							else {
								inSite = false;
							}
						}
					}
				}
				
				//this.initEntity(tempX, tempY);
				this.initEntity((int)tempX, (int)tempY);
				health = 3;
				canRespawn = false;
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				// i exploded for some reason!
				e.printStackTrace();
			}
		}

		// TODO: Seems ok, but needs work!
		// add idle, maby idle timer, idk, something looks like its missing.
		// int R = (Math.random() * (100 - 10)) + 10; 
		// This gives you a random number in between 10 (inclusive) and 100 (exclusive)
		
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
			
		}
		pass++;
		//System.out.println("Pass "+pass+" Done!");

	}

	void render (Graphics g, Boolean debug) {
		if (!dead) {
			//sprite.draw((int)x, (int)y);
			// TODO Need to add change to damage render location
			bloodSplat1.draw(curX, curY);
			
			if (debug) {
				renderDebug(g);
			}
		}
		sprite.draw((int)curX, (int)curY);
	}
	
	void renderDebug(Graphics g) {
		// Draw Collision Boxes.
		g.setColor(Color.blue);
		g.draw(rec);
		
		// Render Sight Range.
		g.setColor(Color.white);
		g.draw(sightRadius);
		
		// Render Sight Cone? -^ could change that to a cone instead of full 360* vision, lol.
		
		// render next dest
		//g.setColor(Color.red);
		//g.drawLine(curX+centerOfSprite, curY+centerOfSprite, destX+centerOfSprite, destY+centerOfSprite);
		
		
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
			if (destX < (mapWidth -1) && destX > 1) {
				xSet = true;
			}
			
			if (destX < curX) {
				needGoLeft = true;
			}
			else if (destX > curX) {
				needGoRight = true;
			}
			else {
				// drool.
			}
		}
		
		// set dest y
		while (!ySet) {
			destY = (int) ((Math.random() * ((spawnY*1.2) - (spawnY*0.2))) + (spawnY*0.2)); 
			if (destY < (mapHeight -1) && destY > 1) {
				ySet = true;
			}
			
			if (destY < curY) {
				needGoUp = true;
			}
			else if (destY > curY) {
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
		//curY -= delta * curWalkSpeed;
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}

		curY -= delta * Velocity;
		
		faceingX = 0;
		faceingY = -1;
	}

	private void moveDown(int delta) {
		sprite = down;
		//curY += delta * curWalkSpeed;
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}

		curY += delta * Velocity;
		
		faceingX = 0;
		faceingY = 1;
	}

	private void moveLeft(int delta) {
		sprite = left;
		//curX -= delta * curWalkSpeed;
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}

		curX -= delta * Velocity;
		
		faceingX = -1;
		faceingY = 0;
	}

	private void moveRight(int delta) {
		sprite = right;
		//curX += delta * curWalkSpeed;
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}

		curX += delta * Velocity;
		
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
		
	}
	
	public void attemptPlayerUp(int delta) {
		sprite = up;
		canIdle = false;
		
		handlePlayerUp(delta);
	}
	public void attemptPlayerDown(int delta) {
		sprite = down;
		canIdle = false;
		
		handlePlayerDown(delta);
	}
	public void attemptPlayerLeft(int delta) {
		sprite = left;
		canIdle = false;
		
		handlePlayerLeft(delta);
	}
	public void attemptPlayerRight(int delta) {
		sprite = right;
		canIdle = false;
		
		handlePlayerRight(delta);
	}
	
	private void handlePlayerUp(int delta) {
		nextMapX = curX + centerOfSprite;
		nextMapY = (curY+centerOfSprite) - delta * collisionPaddingDistance;
		
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}
		// speed = speed + velocity.
		// nextY = nextY + speed.
		nextY -= delta * Velocity;
		rec.setCenterY(nextY);

		faceingX = 0;
		faceingY = -1;
	}
	private void handlePlayerDown(int delta) {
		nextMapX = curX + centerOfSprite;
		nextMapY = (curY + centerOfSprite) + delta * collisionPaddingDistance;
		
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}
		nextY += delta * Velocity;
		rec.setCenterY(nextY);

		faceingX = 0;
		faceingY = 1;
	}
	private void handlePlayerLeft(int delta) {
		nextMapX = (curX + centerOfSprite) - delta * collisionPaddingDistance;
		nextMapY = curY + centerOfSprite;
		
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}
		nextX -= delta * Velocity;
		rec.setCenterX(nextX);
		
		faceingX = -1;
		faceingY = 0;
	}
	private void handlePlayerRight(int delta) {
		nextMapX = (curX + centerOfSprite) + delta * collisionPaddingDistance;
		nextMapY = curY + centerOfSprite;
		
		Velocity = Velocity + acceleration;
		if(Velocity  > maxSpeed){
			Velocity  = maxSpeed;
		}
		nextX += delta * Velocity;
		rec.setCenterX(nextX);
		
		faceingX = 1;
		faceingY = 0;
	}
	
	public void tempSetPlayerSight(float sight) {
		this.sightRange = sight;
		sightRadius = new Circle(curX, curY, sight);
	}

}
