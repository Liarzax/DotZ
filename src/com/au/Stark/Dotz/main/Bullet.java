package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;

public class Bullet {
	
	// Pew Pew Details
    private Animation pewLR, pewUD;
    private int bulletPosX = 0, bulletPosY = 0;
    private int dirX = 0, dirY = 0;
    
	// type of bullet
	// damage
	// penetration
	// special effects / poison/explosive/etc
	
	public Bullet() {
		
	}
	
	public void update(int bulletSpeed) {
		bulletPosX += (dirX * bulletSpeed);
		bulletPosY += (dirY * bulletSpeed);
	}
	
	public void render () {
		if (this.dirX != 0 && dirY == 0 )
			pewLR.draw(bulletPosX, bulletPosY);
	
		if(this.dirX == 0 && dirY != 0 )
			pewUD.draw(bulletPosX, bulletPosY);
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
