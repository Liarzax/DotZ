package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public class Bullet {
	
	// Pew Pew Details
    private Animation pewLR, pewUD, pew;
    private int bulletPosX = 0, bulletPosY = 0;
    private int dirX = 0, dirY = 0;
    Rectangle rec = new Rectangle(0, 0, 3, 3);
    
    boolean exists = false;
    
	// type of bullet
	// damage
	// penetration
	// special effects / poison/explosive/etc
	
	public Bullet() {
		
	}
	
	public void update(int bulletSpeed) {
		if (exists) {
			bulletPosX += (dirX * bulletSpeed);
			bulletPosY += (dirY * bulletSpeed);
			rec.setX(bulletPosX);
			rec.setY(bulletPosY);
		}
		
	}
	
	public void render () {
		if (exists) {
			pew.draw(bulletPosX, bulletPosY);
		}
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

	public void setPew(Animation pew) {
		this.pew = pew;		
	}

	public void destroyBullet() {
		exists = false;
		//rec.setWidth(0);
		//rec.setHeight(0);
	}
	
}
