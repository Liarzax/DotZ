package com.au.Stark.Dotz.main;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Main extends BasicGame {
	final static int majorVersion = 0, minorVersion = 1, bugfix = 0, buildRev = 18;
	final static String devStage = "Pre-Alpha";
	final static String version = "v"+majorVersion+"."+minorVersion+"."+bugfix+"-"+devStage+"   build."+buildRev;
// slick, lwjgl, nifty-1.3.3, nifty-lwjgl-renderer-1.3.3, lwjgl_util, xpp3-1.1.3.4.c;
	static int width = 800;
	static int height = 640;

	static boolean fullscreen = false;
	static boolean showFPS = true;
	static String title = "Defence of the Zombopolypse "+version;
	static int fpslimit = 60;

	// Create mapFactory
	MapFactory mapFactory = new MapFactory();
	
	// Create HUD
	HUD hud = new HUD();

	// Init Sprite
	// party start location
	private float x = 65f, y = 85f;
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

	/////// ************************ STOP GOING BACK!! \\\\\\\\\\\\\\\\\\\\\\


	public Main(String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main(title));
		app.setDisplayMode(width, height, fullscreen);
		app.setSmoothDeltas(true);
		app.setTargetFrameRate(fpslimit);
		app.setShowFPS(showFPS);
		app.start();
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//Load Map
		mapFactory.init();
		// LOAD HUD
		hud.init();
		
		//Load Sprite via Rotation! NOTE: Done but im sure theres a cleaner way to do this!?
		Image [] movementUp = {new Image("assets/player_UD1.png"), new Image("assets/player_UD2.png"),
				new Image("assets/player_UD3.png"), new Image("assets/player_UD4.png"), new Image("assets/player_UD5.png"), 
				new Image("assets/player_UD6.png"), new Image("assets/player_UD7.png"), new Image("assets/player_UD8.png")};
		Image [] movementDown = {new Image("assets/player_UD1.png"), new Image("assets/player_UD2.png"),
				new Image("assets/player_UD3.png"), new Image("assets/player_UD4.png"), new Image("assets/player_UD5.png"), 
				new Image("assets/player_UD6.png"), new Image("assets/player_UD7.png"), new Image("assets/player_UD8.png")};
		Image [] movementLeft = {new Image("assets/player_UD1.png"), new Image("assets/player_UD2.png"),
				new Image("assets/player_UD3.png"), new Image("assets/player_UD4.png"), new Image("assets/player_UD5.png"), 
				new Image("assets/player_UD6.png"), new Image("assets/player_UD7.png"), new Image("assets/player_UD8.png")};
		Image [] movementRight = {new Image("assets/player_UD1.png"), new Image("assets/player_UD2.png"),
				new Image("assets/player_UD3.png"), new Image("assets/player_UD4.png"), new Image("assets/player_UD5.png"), 
				new Image("assets/player_UD6.png"), new Image("assets/player_UD7.png"), new Image("assets/player_UD8.png")};
		
		Image [] movementIdleU = {new Image("assets/player_iD1.png")};
		Image [] movementIdleD = {new Image("assets/player_iD1.png")};
		Image [] movementIdleL = {new Image("assets/player_iD1.png")};
		Image [] movementIdleR = {new Image("assets/player_iD1.png")};
		
		for (int i = 0; i < movementUp.length; i++) {
			movementDown[i].rotate(180f);
			movementLeft[i].rotate(270f);
			movementRight[i].rotate(90f);
		}
		for (int i = 0; i< movementIdleU.length; i++) {
			movementIdleD[i].rotate(180f);
			movementIdleL[i].rotate(270f);
			movementIdleR[i].rotate(90f);
		}
		
		int [] duration = {200, 200, 200, 200, 200, 200, 200, 200};
		int [] idleDur = {200};

		
		// Sprite size 32x32, tile size 16x16
		// stuff.
		
		

		// Load Bullet.init/constructor, etc	
		bulletFactory.initBulletFactory();

		// temp enemy init.
		//enemy.initEntity(337, 233);
		// Need to put a check to make sure not spawning in wall, also clean it up, temp ok, whatevs bru
		for (int i = 0; i < maxEnemies; i++) {
			int eStartX = randNumBetween(100, 600); 
			int eStartY = randNumBetween(100, 350); 
			
			enemies[i] = new Entity();
			enemies[i].initEntity(eStartX, eStartY);
			enemies[i].entID = i;
			System.out.println("ent " + i + " Created!");
		}
		
		//temp party init
		for (int i = 0; i < playerPartySize; i++) {
			int eStartX = randNumBetween(((int)x-25), ((int)x+25)); 
			int eStartY = randNumBetween(((int)y-25), ((int)y+25)); 
			
			players[i] = new Entity();
			players[i].initEntity(eStartX, eStartY);
			players[i].entID = i;
			System.out.println("player " + i + " Created!");
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
		float nextX = 0;
		float nextY = 0;
		// Set cur active char
		if (Keyboard.isKeyDown(Keyboard.KEY_1))
			activePlayer = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_2))
			activePlayer = 1;
		if (Keyboard.isKeyDown(Keyboard.KEY_3))
			activePlayer = 2;
		
		if (Keyboard.isKeyDown(Input.KEY_UP)) {
			players[activePlayer].sprite = players[activePlayer].up;
			players[activePlayer].canIdle = false;
			
			nextX = players[activePlayer].curX + players[activePlayer].centerOfSprite;
			nextY = (players[activePlayer].curY+players[activePlayer].centerOfSprite) - delta * players[activePlayer].collisionPaddingDistance;
			if (!mapFactory.isBlocked(nextX, nextY) && !entityCollision(nextX, nextY, enemies)){// && entityCollision(x, y, enemies) == false) {
				players[activePlayer].sprite.update(delta);
				// The lower the delta the slower the sprite will move.
				// LSHIFT to run, sucka!
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					players[activePlayer].curY -= delta * players[activePlayer].runSpeed;
				else
					players[activePlayer].curY -= delta * players[activePlayer].walkSpeed;
			}
			
			players[activePlayer].faceingX = 0;
			players[activePlayer].faceingY = -1;
		}

		if (Keyboard.isKeyDown(Input.KEY_DOWN)) {
			players[activePlayer].sprite = players[activePlayer].down;
			players[activePlayer].canIdle = false;

			nextX = players[activePlayer].curX + players[activePlayer].centerOfSprite;
			nextY = (players[activePlayer].curY + players[activePlayer].centerOfSprite) + delta * players[activePlayer].collisionPaddingDistance;
			if (!mapFactory.isBlocked(nextX, nextY) && !entityCollision(nextX, nextY, enemies)){// && entityCollision(x, y, enemies) == false) {
				players[activePlayer].sprite.update(delta);
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					players[activePlayer].curY += delta * players[activePlayer].runSpeed;
				else 
					players[activePlayer].curY += delta * players[activePlayer].walkSpeed;
			}
			
			players[activePlayer].faceingX = 0;
			players[activePlayer].faceingY = 1;
		}

		if (Keyboard.isKeyDown(Input.KEY_LEFT)) {
			players[activePlayer].sprite = players[activePlayer].left;
			players[activePlayer].canIdle = false;

			nextX = (players[activePlayer].curX + players[activePlayer].centerOfSprite) - delta * players[activePlayer].collisionPaddingDistance;
			nextY = players[activePlayer].curY + players[activePlayer].centerOfSprite;
			if (!mapFactory.isBlocked(nextX, nextY) && !entityCollision(nextX, nextY, enemies)){// && entityCollision(x, y, enemies) == false) {
				players[activePlayer].sprite.update(delta);
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					players[activePlayer].curX -= delta * players[activePlayer].runSpeed;
				else
					players[activePlayer].curX -= delta * players[activePlayer].walkSpeed;
			}
			
			players[activePlayer].faceingX = -1;
			players[activePlayer].faceingY = 0;
		}

		if (Keyboard.isKeyDown(Input.KEY_RIGHT)) {
			players[activePlayer].sprite = players[activePlayer].right;
			players[activePlayer].canIdle = false;

			nextX = (players[activePlayer].curX + players[activePlayer].centerOfSprite) + delta * players[activePlayer].collisionPaddingDistance;
			nextY = players[activePlayer].curY + players[activePlayer].centerOfSprite;
			if (!mapFactory.isBlocked(nextX, nextY) && !entityCollision(nextX, nextY, enemies)){// && entityCollision(x, y, enemies) == false) {
				players[activePlayer].sprite.update(delta);
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					players[activePlayer].curX += delta * players[activePlayer].runSpeed;
				else
					players[activePlayer].curX += delta * players[activePlayer].walkSpeed;
			}
			
			players[activePlayer].faceingX = 1;
			players[activePlayer].faceingY = 0;
		}
		
		
		// Move bounding box with player.
		players[activePlayer].rec.setX(players[activePlayer].curX);
		players[activePlayer].rec.setY(players[activePlayer].curY);
		// TESTING SHIT
		entityCollision(players[activePlayer].curX, players[activePlayer].curY, enemies);

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
		
		// loop through playerParty, if not active player do ai stuff to follow




		bulletFactory.updateBullets(mapFactory, enemies);

		// temp enemy update
		//enemy.update(delta, mapFactory);
		for (int i = 0; i < maxEnemies; i++) {
			enemies[i].update(delta, mapFactory);
		}
		
		// UPDATE HUD DETAILS
		hud.update();

	}

	//@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// Display Map
		mapFactory.getCurMap().render(0, 0);
		// Draw player
		// Draw bullets.
		bulletFactory.renderBullets();

		// temp player party render
		for (int i = 0; i < playerPartySize; i++) {
			players[i].render();
		}
		
		// temp enemy.draw
		//enemy.render();
		for (int i = 0; i < maxEnemies; i++) {
			enemies[i].render();
		}
		
		// RENDER HUD DETAILS
		hud.render();

	}
	
	// stupid java rand generator only from 0 to num, not from num to num. FIXED!
	public int randNumBetween(int min, int max) {
		int num = (int) ((Math.random() * (max - min)) + min); 
		return num;
	}
	
	// super bang collision dang!
	public boolean entityCollision(float x, float y, Entity enemies[]) {
	//public boolean entityCollision(Entity curEnt, Entity enemies[]) {
		boolean collision = false;
		// NOTE; if intersected push that shit outa the way
		for (int i = 0; i < enemies.length; i++) { 
			if(players[activePlayer].rec.intersects(enemies[i].rec) && !enemies[i].dead) {
				
				System.out.println("Possible Collision? w/ Enemy " +i);
				collision = true;
			}
				
		}
		return collision;
	}


}
