package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Bullet {
	
	// Pew Pew Details
    private Animation pew, pewLR, pewUD;
    // Bullet bullet[] = new Bullet();
    // Bullet should have x,y poss, and animation shit upve.
    int pewPosX = 0, pewPosY = 0;
    int dirX = 0, dirY = 0;
    
    int duration = 300;
	boolean exists;
	int curOnField = 0;
	
	int bullSpeed = 6;
    
	
	public Bullet() {
		
	}
	
	void initBullet() throws SlickException {
		//Load Bullet.init/constructor, etc
	    Image [] movePewLR = {new Image("assets/pewLR.png"), new Image("assets/pewLR.png")};
	    Image [] movePewUD = {new Image("assets/pewUD.png"), new Image("assets/pewUD.png")};
	    
	    pewLR = new Animation(movePewLR, duration, false);
	    pewUD = new Animation(movePewUD, duration, false);
	    
	    
	}
	
	void update() {
		if (this.exists) {
			pewPosX += (dirX * bullSpeed);
			pewPosY += (dirY * bullSpeed);
		}
	}
	
	void render () {
		// go through Bullet group and update the pews
    	//while (int i =0; i < bullet.getlengh(); i++) {
		if (this.exists) {
			if (this.dirX != 0 && dirY == 0 )
				pewLR.draw(pewPosX, pewPosY);
		
			if(this.dirX == 0 && dirY != 0 )
				pewUD.draw(pewPosX, pewPosY);
		}
	}
	
	void createBullet(Animation sprite, int x, int y, int dirX, int dirY) {
		// to make easier, make function, iff key pressed, send in sprite facing.
        // spawn pew peww base don sprite faceing, +++++++ in faceing direction, tooo eazy
        System.out.println("Should Pew Pew Now");
       	// spawn pew pew bullets
       	// add to group
       	pewPosX = (int) x + (dirX *2);
       	pewPosY = (int) y + (dirY *2);
       	this.dirX = dirX;
       	this.dirY = dirY;
		this.exists = true;
	}

}
