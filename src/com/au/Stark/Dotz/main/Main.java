package com.au.Stark.Dotz.main;

import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Main extends BasicGame {
	    
	   static int width = 640;
	   static int height = 480;
	   
	   static boolean fullscreen = false;
	   static boolean showFPS = true;
	   static String title = "Defence of the Zombopolypse";
	   static int fpslimit = 60;
	   
	   // Init Map (20x14 Tiles).
	   private TiledMap grassMap;
	   
	   // Init Sprite
	   private Animation sprite, up, down, left, right;
	   // sprite location
	   private float x = 34f, y = 66f;
	   
	   // Map Collision Data
	   private boolean[][] blocked;
	   private static final int SIZE = 32;  // sprite/tile size | collision range (eg, Down = x2 - Cuz x64 in size).
	   
	   // Pew Pew Details
	   Bullet bullet = new Bullet();
	   int faceingX = 0;
	   int faceingY = 0;
	   
	   
	   
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
	 	    grassMap = new TiledMap("assets/grassmap.tmx");
	 	    
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
		   int [] duration = {200, 200, 200, 200, 200, 200, 200, 200};
		   
		   up = new Animation(movementUp, duration, false);
		   down = new Animation(movementDown, duration, false);
		   left = new Animation(movementLeft, duration, false);
		   right = new Animation(movementRight, duration, false); 
		   
		   // Original orientation of the sprite. It will look right.
		   sprite = right;
		   
		   // Load Bullet.init/constructor, etc		   
		   bullet.initBullet();
	 	   
		   // Build Map Collision Array  based on tile properties in the TileD map 
		   blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];
		   for (int xAxis=0;xAxis<grassMap.getWidth(); xAxis++) {
                
			   for (int yAxis=0;yAxis<grassMap.getHeight(); yAxis++) {
                    int tileID = grassMap.getTileId(xAxis, yAxis, 0);
                    String value = grassMap.getTileProperty(tileID, "blocked", "false");
                    
                    if ("true".equals(value)) {
                        blocked[xAxis][yAxis] = true;
                    }
                }
		    }
	    }
	 
	    @Override
	    public void update(GameContainer gc, int delta) throws SlickException {
	    	//Temp Quick ESC	       
	    	if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
	    	   gc.exit();
	    	
	    	// Check Player
	    	Input input = gc.getInput();
	    	if (input.isKeyDown(Input.KEY_UP)) {
	             sprite = up;
	             
	             if (!isBlocked(x, y - delta * 0.1f)) {
	                 sprite.update(delta);
	                 // The lower the delta the slower the sprite will animate.
	                 y -= delta * 0.1f;
	             }
	             faceingX = 0;
	             faceingY = -1;
	         }
	    	
	         if (input.isKeyDown(Input.KEY_DOWN)) {
	             sprite = down;
	             
	             if (!isBlocked(x, y + SIZE + delta * 0.1f)) {
	                 sprite.update(delta);
	                 y += delta * 0.1f;
	             }
	             faceingX = 0;
	             faceingY = 1;
	         }
	         
	         if (input.isKeyDown(Input.KEY_LEFT)) {
	             sprite = left;
	             
	             if (!isBlocked(x - delta * 0.1f, y)) {
	                 sprite.update(delta);
	                 x -= delta * 0.1f;
	             }
	             faceingX = -1;
	             faceingY = 0;
	         }
	         
	         if (input.isKeyDown(Input.KEY_RIGHT)) {
	             sprite = right;
	             
	             if (!isBlocked(x + SIZE + delta * 0.1f, y)) {
	                 sprite.update(delta);
	                 x += delta * 0.1f;
	             }
	             faceingX = 1;
	             faceingY = 0;
	         }
	         
	         // to make easier, make function, iff key pressed, send in sprite facing.
	         // spawn pew peww base don sprite faceing, +++++++ in faceing direction, tooo eazy
	         /*if (sprite == right && input.isKeyDown(Input.KEY_SPACE)) {
	        	 System.out.println("Should Pew Pew Now");
	        	 // spawn pew pew bullets
	        	 // add to group
	        	 pewX = (int) x +2;
	        	 pewY = (int) y;
	         }
	         // loop through Bullet group
	         //while (int i =0; i < bullet.getlengh(); i++) {
		         if (pewX != 0 && pewY != 0) // just remove this and update pos.
		        	 pewX++;*/
	         if (input.isKeyDown(Input.KEY_SPACE))
	        	 bullet.createBullet(sprite,(int)x, (int)y, input, (int) faceingX, (int) faceingY);
	         	
	         if (bullet.exists)
	        	 bullet.update();
	         
	         
	    }
	 
	    @Override
	    public void render(GameContainer gc, Graphics g) throws SlickException {
	    	// Display Map
	    	grassMap.render(0, 0);
	    	// Draw Zombie
	    	sprite.draw((int)x, (int)y);
	    	
	    	/*// go through Bullet group and update the pews
	    	//while (int i =0; i < bullet.getlengh(); i++) {
	    		pewLR.draw(pewX, pewY);*/
	    	bullet.render();
	    	
	    }
	    
	    private boolean isBlocked(float x, float y) {
	    	int xBlock = (int)x / SIZE;
	        int yBlock = (int)y / SIZE;
	        return blocked[xBlock][yBlock];
	    }
	    
	    
	   
	    
	   
	}