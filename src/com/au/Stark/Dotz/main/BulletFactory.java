package com.au.Stark.Dotz.main;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BulletFactory {
	// TODO:
	// When add wepons/guns. Have the guns as seperate class, and it passes in thhe values such as
	// bullet speed, max bullets, bullet fire rate, etc to the factory to handle firing those specific bullets!
	
	// Pew Pew Details
	int bulletSpeed = 6;
    private Animation pew, pewLR, pewUD;
    
    // firing speed controller. Possible to use game timer? or timer class?
    int curBulletFireTimer = 0;
    int bulletFireRate = 20;
    
    //LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private int curBulletsOnField = 0;
    private int maxBullets = 10;
	Bullet[] bullets = new Bullet[maxBullets];
    
    // Misc
    //Animation Duration
    private int duration = 300;
    
	
	public BulletFactory() {
		
	}
	
	public void initBulletFactory() throws SlickException {
		//Load Bullet.init/constructor, etc
	    Image [] movePewLR = {new Image("assets/pewLR.png"), new Image("assets/pewLR.png")};
	    Image [] movePewUD = {new Image("assets/pewUD.png"), new Image("assets/pewUD.png")};
	    
	    pewLR = new Animation(movePewLR, duration, false);
	    pewUD = new Animation(movePewUD, duration, false);
	    
	    //bullets = null;
		
	}

	public void createBullet(Animation sprite, int x, int y, int faceingX, int faceingY) {
		// TODO Auto-generated method stub
		System.out.println("Start Fire!");
		
		if (curBulletsOnField < maxBullets) {
			bullets[curBulletsOnField] = new Bullet();
			bullets[curBulletsOnField].bulletText(curBulletsOnField);
			bullets[curBulletsOnField].setPewLR(pewLR);
			bullets[curBulletsOnField].setPewUD(pewUD);
			bullets[curBulletsOnField].setBulletPosX(x + (faceingX *2));
			bullets[curBulletsOnField].setBulletPosY(y + (faceingY *2));
			bullets[curBulletsOnField].setDirX(faceingX);
			bullets[curBulletsOnField].setDirY(faceingY);
			
			bullets[curBulletsOnField].completeBullet(curBulletsOnField);
			curBulletsOnField++;
		}
		curBulletFireTimer = 0;
	}

	public int getCurOnField() {
		return curBulletsOnField;
	}

	public void setCurOnField(int curOnField) {
		this.curBulletsOnField = curOnField;
	}

	public void updateBullets() {
		for (int i = 0; i < curBulletsOnField; i++) {
			bullets[i].update(bulletSpeed);
		}
		
		if (curBulletFireTimer < bulletFireRate) {
			curBulletFireTimer++;
		}
		else {
			// Do Nothing / wait for timer before able to fire again.
		}
	}
	
	public void renderBullets() {
		for (int i = 0; i < curBulletsOnField; i++) {
			bullets[i].render();
		}
	}
	
	
	

}
