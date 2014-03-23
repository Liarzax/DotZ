package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Bullet {
	
	// Pew Pew Details
    private Animation pewLR, pewUD;
    int bulletPosX = 0, bulletPosY = 0;
    int dirX = 0, dirY = 0;
    
	boolean exists = false;
	int bulletNum = 0;
	
	// type of bullet
	// damage
	// penetration
	// special effects / poison/explosive/etc
	
	public Bullet() {
		
	}
	
	/*void update(int bulletSpeed) {
		
		bulletPosX += (dirX * bulletSpeed);
		bulletPosY += (dirY * bulletSpeed);
		
	}*/
	
	void render () {
		// go through Bullet group and update the pews
    	//while (int i =0; i < bullet.getlengh(); i++) {
		
		if (this.dirX != 0 && dirY == 0 )
			pewLR.draw(bulletPosX, bulletPosY);
	
		if(this.dirX == 0 && dirY != 0 )
			pewUD.draw(bulletPosX, bulletPosY);
		
	}
	
	void bulletText(int i) {
        System.out.println("Should Pew Pew bullet " + i + " Now");
		//this.exists = true;
	}
	
	void completeBullet(int i) {
		System.out.println("Should of Pew Pewed bullet " + i);
	}

	public Animation getPewLR() {
		return pewLR;
	}

	public void setPewLR(Animation pewLR) {
		this.pewLR = pewLR;
	}

	public Animation getPewUD() {
		return pewUD;
	}

	public void setPewUD(Animation pewUD) {
		this.pewUD = pewUD;
	}

	public int getBulletPosX() {
		return bulletPosX;
	}

	public void setBulletPosX(int bulletPosX) {
		this.bulletPosX = bulletPosX;
	}

	public int getBulletPosY() {
		return bulletPosY;
	}

	public void setBulletPosY(int bulletPosY) {
		this.bulletPosY = bulletPosY;
	}

	public int getDirX() {
		return dirX;
	}

	public void setDirX(int dirX) {
		this.dirX = dirX;
	}

	public int getDirY() {
		return dirY;
	}

	public void setDirY(int dirY) {
		this.dirY = dirY;
	}

	
	
	

}
