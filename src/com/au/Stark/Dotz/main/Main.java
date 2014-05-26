package com.au.Stark.Dotz.main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

public class Main extends BasicGame {
	// latest stuff added!
	//added SSSSSTTTTTTUUUUUUFFFFFF!!!!!!!!!!!!!!! 
	final static int majorVersion = 0, minorVersion = 1, bugfix = 0, buildRev = 23;
	final static String devStage = "Pre-Alpha";
	final static String version = "v"+majorVersion+"."+minorVersion+"."+bugfix+"-"+devStage+"   build."+buildRev;
// slick, lwjgl, nifty-1.3.3, nifty-lwjgl-renderer-1.3.3, lwjgl_util, xpp3-1.1.3.4.c;
	//static int WIDTH = 800;
	//static int HEIGHT = 640;
	static int WIDTH = 800;
	static int HEIGHT = 640;

	static boolean fullscreen = false;
	static String title = "Defence of the Zombopolypse "+version;
	static int fpslimit = 60;
	
	// temp using godMode for see all, probably use it as a cheat for things during debugging.
	// use = to toggle
	static boolean godMode = false;
	// use - to toggle
	static boolean debug = false;
	static boolean showFPS = true;

	// Create mapFactory
	MapFactory mapFactory = new MapFactory();
	
	// Create HUD
	HUD hud = new HUD();

	// Init Sprite
	// party start location
	private float x = 65f, y = 85f;
	// ai follow distance
	private float aiFollowDist = 40f;
	// movement speed
	//float walkSpeed = 0.05f, runSpeed = 0.08f;

	// Pew Pew Details
	BulletFactory bulletFactory = new BulletFactory();

	// Player
	private int playerPartySize = 3;
	Entity[] players = new Entity[playerPartySize];
	int activePlayer = 0;
	
	// temp enemy
	// 10
	private int maxEnemies = 10;
	Entity[] enemies = new Entity[maxEnemies];

	// collision system
	CollisionSystem collisionSys = new CollisionSystem();
	
	
	
	// controll handler.
	//ControlHandler input = new ControlHandler();
	//private boolean downDebug, downGodMode, downAI, downAIFollow = false;
	
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\
	

	public Main(String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main(title));
		app.setDisplayMode(WIDTH, HEIGHT, fullscreen);
		app.setSmoothDeltas(true);
		app.setTargetFrameRate(fpslimit);
		app.setShowFPS(showFPS);
		/*if (debug)
			app.setShowFPS(true);
		else
			app.setShowFPS(false);*/
		app.start();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//Load Map
		mapFactory.init();
		// LOAD HUD
		hud.init();

		// Load Bullet.init/constructor, etc	
		bulletFactory.initBulletFactory();
		// load collision system
		collisionSys.initCollisionSystem();

		// temp enemy init.
		//enemy.initEntity(337, 233);
		// Need to put a check to make sure not spawning in wall, also clean it up, temp ok, whatevs bru
		for (int i = 0; i < maxEnemies; i++) {
			int eStartX = randNumBetween(100, 600); 
			int eStartY = randNumBetween(100, 350); 
			
			enemies[i] = new Entity();
			enemies[i].initEntity(eStartX, eStartY);
			enemies[i].entID = i;
			System.out.println("ent " + (i+1) + " Created!");
			
			enemies[i].tempSetPlayerFollowDist(0f);
		}
		
		//temp party init
		for (int i = 0; i < playerPartySize; i++) {
			int eStartX = randNumBetween(((int)x-25), ((int)x+25)); 
			int eStartY = randNumBetween(((int)y-25), ((int)y+25)); 
			
			players[i] = new Entity();
			players[i].initEntity(eStartX, eStartY);
			players[i].entID = i;
			System.out.println("player " + (i+1) + " Created!");
			
			players[i].tempSetPlayerSight(150f);
			players[i].tempSetPlayerFollowDist(40f);
		}
		
		// initialise input.
		//input.init(gc);
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		//Temp Quick DEBUG STUFF	       
		if (input.isKeyPressed(Keyboard.KEY_ESCAPE))
			gc.exit();
		
		//input.update(gc);
		
		// need to slow this down cuz i think when i try and load a map it will explode and try a million times! LOL!
		if (input.isKeyPressed(Keyboard.KEY_RBRACKET))
			mapFactory.loadNextStage();
		if (input.isKeyPressed(Keyboard.KEY_LBRACKET))
			mapFactory.loadPrevStage();

		// debug mode Use - to view collision boxes and shizzle
		if (input.isKeyPressed(Keyboard.KEY_MINUS)) {
			if (debug == false) {
				debug = true;
				System.out.println("DEBUG Mode Activated!");
			}
			else {
				debug = false;
				System.out.println("DEBUG Mode De-Activated!");
			}
		}
		
		// GODMODE TOGGLE, See Everything! BE Invulnerable! MOVE THROUGH WALLS! Use =
		if (input.isKeyPressed(Keyboard.KEY_EQUALS)) {
			if (godMode == false) {
				godMode = true;
				System.out.println("God Mode Activated!");
			}
			else {
				godMode = false;
				System.out.println("God Mode De-Activated!");
			}
		}
		// Move/Check Player
		//float nextX = 0;
		//float nextY = 0;
		// Set cur active char
		if (input.isKeyPressed(Keyboard.KEY_1))
			activePlayer = 0;
		if (input.isKeyPressed(Keyboard.KEY_2))
			activePlayer = 1;
		if (input.isKeyPressed(Keyboard.KEY_3))
			activePlayer = 2;
		//activate ai for current unit - later do shit like, follow on/off, auto fire on/off, etc...
		if (input.isKeyPressed(Keyboard.KEY_A)) {
			if (players[activePlayer].ai == false) {
				players[activePlayer].ai = true;
				System.out.println("AI for Unit "+ (activePlayer+1) +" Activated!");
			}
			else {
				players[activePlayer].ai = false;
				System.out.println("AI for Unit "+ (activePlayer+1) +" De-Activated!");
			}
		}
		//activate ai follow mode
		if (input.isKeyPressed(Keyboard.KEY_F)) {
			if (players[activePlayer].aiFollow == false) {
				players[activePlayer].aiFollow = true;
				System.out.println("AI Following for Unit "+ (activePlayer+1) +" Activated!");
			}
			else {
				players[activePlayer].aiFollow = false;
				System.out.println("AI Following for Unit "+ (activePlayer+1) +" De-Activated!");
			}
		}
		//activate ai follow mode
		if (input.isKeyPressed(Keyboard.KEY_S)) {
			if (players[activePlayer].aiShoot == false) {
				players[activePlayer].aiShoot = true;
				System.out.println("AI AutoFire for Unit "+ (activePlayer+1) +" Activated!");
			}
			else {
				players[activePlayer].aiShoot = false;
				System.out.println("AI AutoFire for Unit "+ (activePlayer+1) +" De-Activated!");
			}
		}
			
		
		if (input.isKeyDown(Input.KEY_UP)) {
			players[activePlayer].attemptPlayerUp(delta);

			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else if (godMode){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextY = players[activePlayer].curY;
			}
		}

		if (input.isKeyDown(Input.KEY_DOWN)) {
			players[activePlayer].attemptPlayerDown(delta);
			
			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else if (godMode){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextY = players[activePlayer].curY;
			}
		}

		if (input.isKeyDown(Input.KEY_LEFT)) {
			players[activePlayer].attemptPlayerLeft(delta);
			
			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else if (godMode){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextX = players[activePlayer].curX;
			}
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			players[activePlayer].attemptPlayerRight(delta);
			
			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else if (godMode){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextX = players[activePlayer].curX;
			}
		}

		// Fire! - kept as isKeyDown since player could have subMachine gun and hold button, maby have a way for this to alt? based on wepons?
		// TODO maby a bool that is like, if automatic and is key down, bambambam, els if not just bam..... and have to press again tpo bam again. or something
		if (input.isKeyDown(Input.KEY_SPACE) && players[activePlayer].reloading == false) {
			if (bulletFactory.curBulletFireTimer >= bulletFactory.bulletFireRate) {
				bulletFactory.createBullet(players[activePlayer].sprite,(int)players[activePlayer].curX + players[activePlayer].centerOfSprite, (int)players[activePlayer].curY + players[activePlayer].centerOfSprite, (int) players[activePlayer].faceingX, (int) players[activePlayer].faceingY);
			}
		}

		// Reload!
		if (input.isKeyPressed(Input.KEY_R) && players[activePlayer].reloading == false) {
			// Reload Sounds
			System.out.println("Unit "+activePlayer+" Reloading!");
			players[activePlayer].reloading = true;
		}
		if (players[activePlayer].reloading == true) {
			bulletFactory.increaseCurReloadTime();
			System.out.println("Reloading Time = " + bulletFactory.getCurReloadTime());

			if (bulletFactory.getCurReloadTime() >= bulletFactory.getReloadSpeed()) {
				bulletFactory.resetCurOnField();
				bulletFactory.resetCurReloadTime();
				players[activePlayer].reloading = false;
			}
		}
		
		// else display idle animation and rotate animation to facing
		if (players[activePlayer].canIdle) {
			players[activePlayer].sprite.restart();
			if (players[activePlayer].sprite != players[activePlayer].idleL || players[activePlayer].sprite != players[activePlayer].idleR || players[activePlayer].sprite != players[activePlayer].idleU || players[activePlayer].sprite != players[activePlayer].idleD) {
				if (players[activePlayer].faceingX == 1) {
					//System.out.println("setting idle RIght");
					players[activePlayer].sprite = players[activePlayer].idleR;
				}
				if (players[activePlayer].faceingX == -1) {
					//System.out.println("setting idle Left");
					players[activePlayer].sprite = players[activePlayer].idleL;
				}
				if (players[activePlayer].faceingY == -1) {
					//System.out.println("setting idle Up");
					players[activePlayer].sprite = players[activePlayer].idleU;
				}
				if (players[activePlayer].faceingY == 1) {
					//System.out.println("setting idle Down");
					players[activePlayer].sprite = players[activePlayer].idleD;
				}
			}
			//sprite.restart(); not restarting animation? - check later
			players[activePlayer].sprite.update(delta);
		}
		
		// player finished doing things
		players[activePlayer].canIdle = true;
		
		// TODO loop through playerParty, if not active && ai active, unit do ai stuff
		for (int i = 0; i < players.length; i++) {
			if (i != activePlayer && players[i].ai) {
				System.out.println("AI Controlling Unit = "+ (i+1));
				
				// TODO maby use the needGoUp/needGoLeft/needGoDown/needGoRight System? worked for zombies right?
				if (players[i].aiFollow) {
					if(!players[i].followRadius.intersects(players[activePlayer].rec)) {
						if (players[i].curY < (players[activePlayer].curY + aiFollowDist) || players[i].curY < (players[activePlayer].curY - aiFollowDist)) {
							players[i].attemptPlayerDown(delta);
							
							if (!mapFactory.isBlocked(players[i].nextMapX, players[i].nextMapY) ){
								players[i].sprite.update(delta);
							}
							else {
								players[i].nextX = players[i].curX;
							}
						}
						
						if (players[i].curY > (players[activePlayer].curY + aiFollowDist) || players[i].curY > (players[activePlayer].curY - aiFollowDist)) {
							players[i].attemptPlayerUp(delta);
							
							if (!mapFactory.isBlocked(players[i].nextMapX, players[i].nextMapY) ){
								players[i].sprite.update(delta);
							}
							else {
								players[i].nextX = players[i].curX;
							}
						}
						
						if (players[i].curX < (players[activePlayer].curX + aiFollowDist) || players[i].curX < (players[activePlayer].curX - aiFollowDist)) {
							players[i].attemptPlayerRight(delta);
							
							if (!mapFactory.isBlocked(players[i].nextMapX, players[i].nextMapY) ){
								players[i].sprite.update(delta);
							}
							else {
								players[i].nextX = players[i].curX;
							}
						}
						
						if (players[i].curX > (players[activePlayer].curX + aiFollowDist) || players[i].curX > (players[activePlayer].curX - aiFollowDist)) {
							players[i].attemptPlayerLeft(delta);
							
							if (!mapFactory.isBlocked(players[i].nextMapX, players[i].nextMapY) ){
								players[i].sprite.update(delta);
							}
							else {
								players[i].nextX = players[i].curX;
							}
						}
						// /inFollowRadius
					}
					// /aiFollowModeCheck
				}
				
				/*float tempXDir1 = 0, tempXDir2 = 0;
				float tempYDir1 = 0, tempYDir2 = 0;
				float tempShootCone = 5;*/
				
				// ai fires at enemies in range/front of ai
				if (players[i].aiShoot){
					//bulletFactory.createBullet(players[i].sprite, (int)players[i].curX, (int)players[i].curY, players[i].faceingX, players[i].faceingY);
					//true shoould be if enemy can be hit, likecast a ray or something.
					//boolean rayHit = false;
					players[i].tempXDir1 = 0;
					players[i].tempXDir2 = 0;
					players[i].tempYDir1 = 0;
					players[i].tempYDir2 = 0;
					//players[i].tempShootCone = 5;
					
					// x0 y-1 for up | x0 y1 for down | x-1 y0 for left | x1 y0 for right
					if (players[i].faceingX == 1 && players[i].faceingY == 0) {
						players[i].tempXDir1 = players[i].rec.getCenterX() + players[i].sightRange;
						players[i].tempYDir1 = players[i].rec.getCenterY() -players[i].tempShootCone;
						players[i].tempXDir2 = players[i].rec.getCenterX() + players[i].sightRange;
						players[i].tempYDir2 = players[i].rec.getCenterY() +players[i].tempShootCone;
					}
					if (players[i].faceingX == -1 && players[i].faceingY == 0) {
						players[i].tempXDir1 = players[i].rec.getCenterX() - players[i].sightRange;
						players[i].tempYDir1 = players[i].rec.getCenterY() -players[i].tempShootCone;
						players[i].tempXDir2 = players[i].rec.getCenterX() - players[i].sightRange;
						players[i].tempYDir2 = players[i].rec.getCenterY() +players[i].tempShootCone;
					}
					if (players[i].faceingY == 1 && players[i].faceingX == 0) {
						players[i].tempYDir1 = players[i].rec.getCenterY() + players[i].sightRange;
						players[i].tempXDir1 = players[i].rec.getCenterX() -players[i].tempShootCone;
						players[i].tempYDir2 = players[i].rec.getCenterY() + players[i].sightRange;
						players[i].tempXDir2 = players[i].rec.getCenterX() +players[i].tempShootCone;
					}
					if (players[i].faceingY == -1 && players[i].faceingX == 0) {
						players[i].tempYDir1 = players[i].rec.getCenterY() - players[i].sightRange;
						players[i].tempXDir1 = players[i].rec.getCenterX() -players[i].tempShootCone;
						players[i].tempYDir2 = players[i].rec.getCenterY() - players[i].sightRange;
						players[i].tempXDir2 = players[i].rec.getCenterX() +players[i].tempShootCone;
					}
					
					players[i].bulletRay1 = new Line(players[i].rec.getCenterX(), players[i].rec.getCenterY(), players[i].tempXDir1, players[i].tempYDir1);
					players[i].bulletRay2 = new Line(players[i].rec.getCenterX(), players[i].rec.getCenterY(), players[i].tempXDir2, players[i].tempYDir2);
					
					for (int e = 0; e < enemies.length; e++) {
						if (players[i].bulletRay1.intersects(enemies[e].rec) || players[i].bulletRay2.intersects(enemies[e].rec)) {
							players[i].bulletRayHit = true;
						}
					}
					
					if (players[i].bulletRayHit && players[i].reloading == false) {
						if (bulletFactory.curBulletFireTimer >= bulletFactory.bulletFireRate) {
							bulletFactory.createBullet(players[i].sprite,(int)players[i].curX + players[i].centerOfSprite, (int)players[i].curY + players[i].centerOfSprite, (int) players[i].faceingX, (int) players[i].faceingY);
						}
						players[i].bulletRayHit = false;
					}
					
					// if reloading == false, and clip empty -> if has clip -> reload.
					
				}
				
				
				// NEW ACTIONS HERE.
				
			}
			// /endCheckAIActive
			else {
				// clear the ai range bullets red cone thingy temp bugfix. clear cuz pc takes over
				players[activePlayer].bulletRay1.set(0,0,0,0);
				players[activePlayer].bulletRay2.set(0,0,0,0);
			}
		}
		// /endAIForLoops
		

		bulletFactory.updateBullets(mapFactory, enemies);

		// temp enemy update
		//enemy.update(delta, mapFactory);
		for (int i = 0; i < maxEnemies; i++) {
			enemies[i].update(delta, mapFactory, players);
		}
		
		
		//TODO MY GOD THIS COLLISION IS NOT AS FUN OR EASY AS ONE THINKS!
		// FINALY CHECK COLLISIONS
		// Detect Collisions
		for (int p = 0; p < players.length; p++) {
			for(int i = 0; i < enemies.length; i++) {
				if (collisionSys.detectCollision(players[p], enemies[i])) {
					//collisionSys.handleCollisions(players[p], enemies[i]);
				}
			}
		}
		
		
		// Set position to new Position for All Player Units
		for(int i = 0; i < players.length; i++) {
			players[i].curX = players[i].nextX;
			players[i].curY = players[i].nextY;
			
			// Move bounding box with player units.
			players[i].rec.setX(players[i].curX);
			players[i].rec.setY(players[i].curY);
			// move sight radius with player units
			players[i].sightRadius.setCenterX(players[i].curX + players[i].centerOfSprite);
			players[i].sightRadius.setCenterY(players[i].curY + players[i].centerOfSprite);
			// move follow radius with player units
			players[i].followRadius.setCenterX(players[i].curX + players[i].centerOfSprite);
			players[i].followRadius.setCenterY(players[i].curY + players[i].centerOfSprite);
			// move the shootingCone / not required.
			//players[i].bulletRay1.set(players[i].rec.getCenterX(), players[i].rec.getCenterY(), players[i].tempXDir1, players[i].tempYDir1);
			//players[i].bulletRay2.set(players[i].rec.getCenterX(), players[i].rec.getCenterY(), players[i].tempXDir2, players[i].tempYDir2);
			
			
			
			
		}
		
		
		// check if things are insight
		collisionSys.detectVision(players, enemies);
		// TODO make enemies chase player if they can see him
		
		// UPDATE HUD DETAILS
		hud.update();

	}

	//@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// Display Map
		mapFactory.getCurMap().render(0, 0);
		// Draw player
		// Draw bullets.
		// loop if bullets visible draw
		bulletFactory.renderBullets(g, debug);
		
		// temp player party render
		for (int i = 0; i < playerPartySize; i++) {
			players[i].render(g, debug);
		}
		
		// temp enemy.draw
		//enemy.render();
		for (int i = 0; i < maxEnemies; i++) {
			if(enemies[i].visible) {
				enemies[i].render(g, debug);
			}
			else if (godMode) {
				enemies[i].render(g, debug);
			}
		}
		
		// DRAW the Node Map
		// TODO THE FUCK CANT THE COLOUR BE SET!!!!!
		for (int i = 0; i < mapFactory.nodeMap.length; i++) {
			for (int j = 0; j < mapFactory.nodeMap.length; j++) {
				mapFactory.nodeMap[i][j].renderNode(g, debug);
			}
		}
		
		
		
		// RENDER HUD DETAILS
		hud.render();

	}
	
	// stupid java rand generator only from 0 to num, not from num to num. FIXED!
	public int randNumBetween(int min, int max) {
		int num = (int) ((Math.random() * (max - min)) + min); 
		return num;
	}
	
}
