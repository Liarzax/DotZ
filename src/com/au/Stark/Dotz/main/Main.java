package com.au.Stark.Dotz.main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Main extends BasicGame {
	final static int majorVersion = 0, minorVersion = 1, bugfix = 0, buildRev = 19;
	final static String devStage = "Pre-Alpha";
	final static String version = "v"+majorVersion+"."+minorVersion+"."+bugfix+"-"+devStage+"   build."+buildRev;
// slick, lwjgl, nifty-1.3.3, nifty-lwjgl-renderer-1.3.3, lwjgl_util, xpp3-1.1.3.4.c;
	static int WIDTH = 800;
	static int HEIGHT = 640;

	static boolean fullscreen = false;
	static String title = "Defence of the Zombopolypse "+version;
	static int fpslimit = 60;
	
	static boolean debug = true;
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
	//Entity enemy = new Entity();
	private int maxEnemies = 10;
	Entity[] enemies = new Entity[maxEnemies];

	// collision system
	CollisionSystem collisionSys = new CollisionSystem();
	
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
		if (debug) {
			app.setShowFPS(showFPS);
		}
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
		}
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		//Temp Quick DEBUG STUFF	       
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			gc.exit();
		// need to slow this down cuz i think when i try and load a map it will explode and try a million times! LOL!
		if (Keyboard.isKeyDown(Keyboard.KEY_RBRACKET))
			mapFactory.loadNextStage();
		if (Keyboard.isKeyDown(Keyboard.KEY_LBRACKET))
			mapFactory.loadPrevStage();

		// TODO - bullshit Collision start		
		// Move/Check Player
		//float nextX = 0;
		//float nextY = 0;
		// Set cur active char
		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			activePlayer = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			activePlayer = 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_3))
			activePlayer = 2;
		//activate ai for current unit - later do shit like, follow on/off, auto fire on/off, etc...
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (players[activePlayer].ai == true) {
				players[activePlayer].ai = false;
				System.out.println("AI for Unit "+ (activePlayer+1) +" Activated!");
			}
			else {
				players[activePlayer].ai = true;
				System.out.println("AI for Unit "+ (activePlayer+1) +" De-Activated!");
			}
		}
		//activate ai follow mode
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			if (players[activePlayer].aiFollow == true) {
				players[activePlayer].aiFollow = false;
				System.out.println("AI Following for Unit "+ (activePlayer+1) +" Activated!");
			}
			else {
				players[activePlayer].aiFollow = true;
				System.out.println("AI Followingfor Unit "+ (activePlayer+1) +" De-Activated!");
			}
		}
			
		
		if (Keyboard.isKeyDown(Input.KEY_UP)) {
			players[activePlayer].attemptPlayerUp(delta);

			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextY = players[activePlayer].curY;
			}
		}

		if (Keyboard.isKeyDown(Input.KEY_DOWN)) {
			players[activePlayer].attemptPlayerDown(delta);
			
			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextY = players[activePlayer].curY;
			}
		}

		if (Keyboard.isKeyDown(Input.KEY_LEFT)) {
			players[activePlayer].attemptPlayerLeft(delta);
			
			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextX = players[activePlayer].curX;
			}
		}

		if (Keyboard.isKeyDown(Input.KEY_RIGHT)) {
			players[activePlayer].attemptPlayerRight(delta);
			
			if (!mapFactory.isBlocked(players[activePlayer].nextMapX, players[activePlayer].nextMapY) ){
				players[activePlayer].sprite.update(delta);
			}
			else {
				players[activePlayer].nextX = players[activePlayer].curX;
			}
		}

		// Fire!
		if (Keyboard.isKeyDown(Input.KEY_SPACE) && players[activePlayer].reloading == false) {
			if (bulletFactory.curBulletFireTimer >= bulletFactory.bulletFireRate) {
				bulletFactory.createBullet(players[activePlayer].sprite,(int)players[activePlayer].curX + players[activePlayer].centerOfSprite, (int)players[activePlayer].curY + players[activePlayer].centerOfSprite, (int) players[activePlayer].faceingX, (int) players[activePlayer].faceingY);
			}
		}

		// Reload!
		if (Keyboard.isKeyDown(Input.KEY_R) && players[activePlayer].reloading == false) {
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
				
				if (players[i].aiFollow) {
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
					
					/*// Set position to new Position
					players[i].curX = players[i].nextX;
					players[i].curY = players[i].nextY;
					
					// Move bounding box with player.
					players[i].rec.setX(players[i].curX);
					players[i].rec.setY(players[i].curY);
					// move sight with entity
					players[i].sightRadius.setCenterX(players[i].curX + players[i].centerOfSprite);
					players[i].sightRadius.setCenterY(players[i].curY + players[i].centerOfSprite);*/
				}
			}
		}

		bulletFactory.updateBullets(mapFactory, enemies);

		// temp enemy update
		//enemy.update(delta, mapFactory);
		for (int i = 0; i < maxEnemies; i++) {
			enemies[i].update(delta, mapFactory);
		}
		
		
		//TODO MY GOD THIS COLLISION IS NOT AS FUN OR EASY AS ONE THINKS!
		// FINALY CHECK COLLISIONS
		// Detect Collisions
		for (int p = 0; p < players.length; p++) {
			for(int i = 0; i < enemies.length; i++) {
				if (collisionSys.detectCollision(players[p], enemies[i])) {
					collisionSys.handleCollisions(players[p], enemies[i]);
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
		}
		
		
		// check if things are insight
		collisionSys.detectVision(players, enemies);
		
		// UPDATE HUD DETAILS
		hud.update();

	}

	//@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// Display Map
		mapFactory.getCurMap().render(0, 0);
		// Draw player
		// Draw bullets.
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
