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

public class Main extends BasicGame {
	final static int majorVersion = 0, minorVersion = 0, bugfix = 0, buildRev = 13;
	final static String devStage = "Pre-Alpha";
	final static String version = "v"+majorVersion+"."+minorVersion+"."+bugfix+"-"+devStage+"   build."+buildRev;

	static int width = 640;
	static int height = 480;

	static boolean fullscreen = false;
	static boolean showFPS = true;
	static String title = "Defence of the Zombopolypse "+version;
	static int fpslimit = 60;

	// Create mapFactory
	MapFactory mapFactory = new MapFactory();

	// Init Sprite
	private Animation sprite, up, down, left, right, idleU, idleD, idleL, idleR;
	boolean playerIdle = true;
	// sprite location
	private float x = 34f, y = 66f;
	// sprite facing
	int faceingX = 0;
	int faceingY = 0;
	// movement speed
	float walkSpeed = 0.05f;
	float runSpeed = 0.1f;
	// misc
	float collisionPaddingDistance = 0.1f;
	// reload shit also need animation
	boolean reloading = false;

	// Pew Pew Details
	BulletFactory bulletFactory = new BulletFactory();

	// temp enemy
	//Entity enemy = new Entity();
	private int maxEnemies = 10;
	Entity[] enemies = new Entity[maxEnemies];



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

		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false); 
		idleU = new Animation(movementIdleU, idleDur, false);
		idleD = new Animation(movementIdleD, idleDur, false);
		idleL = new Animation(movementIdleL, idleDur, false);
		idleR = new Animation(movementIdleR, idleDur, false);
		// idle animation?

		// spawn orientation of the sprite. It will look right.
		sprite = idleR;

		// Load Bullet.init/constructor, etc	
		bulletFactory.initBulletFactory();

		// temp enemy init.
		//enemy.initEntity(337, 233);
		// Need to put a check to make sure not spawning in wall, also clean it up, temp ok, whatevs bru
		for (int i = 0; i < maxEnemies; i++) {
			int eStartX = createRandNum(100, 600); 
			int eStartY = createRandNum(100, 350); 
			
			enemies[i] = new Entity();
			enemies[i].initEntity(eStartX, eStartY);
		}
	}

	// what the hell is this delta for and where is it from!? fps?? Seconds? MilSeconds? TIMe!?
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

		// Move/Check Player
		if (Keyboard.isKeyDown(Input.KEY_UP)) {
			sprite = up;
			playerIdle = false;

			if (!mapFactory.isBlocked(x, y - delta * collisionPaddingDistance)) {
				sprite.update(delta);
				// The lower the delta the slower the sprite will move.
				// LSHIFT to run, sucka!
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					y -= delta * runSpeed;
				else
					y -= delta * walkSpeed;
			}
			faceingX = 0;
			faceingY = -1;
		}

		if (Keyboard.isKeyDown(Input.KEY_DOWN)) {
			sprite = down;
			playerIdle = false;

			if (!mapFactory.isBlocked(x, y + mapFactory.getTileSize() + delta * collisionPaddingDistance)) {
				sprite.update(delta);
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					y += delta * runSpeed;
				else 
					y += delta * walkSpeed;
			}
			faceingX = 0;
			faceingY = 1;
		}

		if (Keyboard.isKeyDown(Input.KEY_LEFT)) {
			sprite = left;
			playerIdle = false;

			if (!mapFactory.isBlocked(x - delta * collisionPaddingDistance, y)) {
				sprite.update(delta);
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					x -= delta * runSpeed;
				else
					x -= delta * walkSpeed;
			}
			faceingX = -1;
			faceingY = 0;
		}

		if (Keyboard.isKeyDown(Input.KEY_RIGHT)) {
			sprite = right;
			playerIdle = false;

			if (!mapFactory.isBlocked(x + mapFactory.getTileSize() + delta * collisionPaddingDistance, y)) {
				sprite.update(delta);
				if (Keyboard.isKeyDown(Input.KEY_LSHIFT))
					x += delta * runSpeed;
				else
					x += delta * walkSpeed;
			}
			faceingX = 1;
			faceingY = 0;
		}
		

		// Fire!
		if (Keyboard.isKeyDown(Input.KEY_SPACE) && reloading == false) {
			if (bulletFactory.curBulletFireTimer >= bulletFactory.bulletFireRate) {
				bulletFactory.createBullet(sprite,(int)x, (int)y, (int) faceingX, (int) faceingY);
			}
		}

		// Reload!
		if (Keyboard.isKeyDown(Input.KEY_R) && reloading == false) {
			// Reload Sounds
			System.out.println("Reloading!");
			reloading = true;
		}
		if (reloading == true) {
			bulletFactory.increaseCurReloadTime();
			System.out.println("Reloading Time = " + bulletFactory.getCurReloadTime());

			if (bulletFactory.getCurReloadTime() >= bulletFactory.getReloadSpeed()) {
				bulletFactory.resetCurOnField();
				bulletFactory.resetCurReloadTime();
				reloading = false;
			}
		}
		
		// else display idle animation and rotate animation to facing
		if (playerIdle) {
			if (sprite != idleL || sprite != idleR || sprite != idleU || sprite != idleD) {
				if (faceingX == 1) {
					//System.out.println("setting idle RIght");
					sprite = idleR;
				}
				if (faceingX == -1) {
					//System.out.println("setting idle Left");
					sprite = idleL;
				}
				if (faceingY == -1) {
					//System.out.println("setting idle Up");
					sprite = idleU;
				}
				if (faceingY == 1) {
					//System.out.println("setting idle Down");
					sprite = idleD;
				}
			}
			sprite.update(delta);
		}
		
		// player finished doing things
		playerIdle = true;




		bulletFactory.updateBullets(mapFactory);

		// temp enemy update
		//enemy.update(delta, mapFactory);
		for (int i = 0; i < maxEnemies; i++) {
			enemies[i].update(delta, mapFactory);
		}

	}

	//@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// Display Map
		mapFactory.getCurMap().render(0, 0);
		// Draw player
		sprite.draw((int)x, (int)y);
		// Draw bullets.
		bulletFactory.renderBullets();

		// temp enemy.draw
		//enemy.render();
		for (int i = 0; i < maxEnemies; i++) {
			enemies[i].render();
		}

	}
	
	// stupid java rand generator only from 0 to num, not from num to num. FIXED!
	public int createRandNum(int min, int max) {
		int num = (int) ((Math.random() * (max - min)) + min); 
		return num;
	}


}
