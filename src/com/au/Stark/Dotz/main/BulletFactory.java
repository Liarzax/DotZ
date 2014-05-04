package com.au.Stark.Dotz.main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BulletFactory {
	// TODO:
	// When add wepons/guns. Have the guns as seperate class, and it passes in thhe values such as
	// bullet speed, max bullets, bullet fire rate, reload speed, etc to the factory to handle firing those specific bullets!

	// Pew Pew Details
	int bulletSpeed = 6;
	private Animation pewLR, pewUD, pew;

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

	// temp reaload shiz
	private int curReloadTime = 0;
	private int reloadSpeed = 80;


	public BulletFactory() {

	}

	public void initBulletFactory() throws SlickException {
		//Load Bullet.init/constructor, etc
		// Could load multiple then assign bullets depending on clip in wepon? or bullets in clip? idk.
		//Image [] movePewLR = {new Image("assets/pewLR.png"), new Image("assets/pewLR.png")};
		//Image [] movePewUD = {new Image("assets/pewUD.png"), new Image("assets/pewUD.png")};
		Image [] movePew = {new Image("assets/pew.png"), new Image("assets/pew.png")};

		//pewLR = new Animation(movePewLR, duration, false);
		//pewUD = new Animation(movePewUD, duration, false);
		pew = new Animation(movePew, duration, false);
	}

	public void createBullet(Animation sprite, int x, int y, int faceingX, int faceingY) {
		if (curBulletsOnField < maxBullets) {
			// Gun Fire Sound!
			System.out.println("Bang!");
			bullets[curBulletsOnField] = new Bullet();
			//bullets[curBulletsOnField].setPewLR(pewLR);
			//bullets[curBulletsOnField].setPewUD(pewUD);
			bullets[curBulletsOnField].setPew(pew);
			bullets[curBulletsOnField].setBulletPosX(x + (faceingX *2));
			bullets[curBulletsOnField].setBulletPosY(y + (faceingY *2));
			bullets[curBulletsOnField].setDirX(faceingX);
			bullets[curBulletsOnField].setDirY(faceingY);

			bullets[curBulletsOnField].exists = true;
			curBulletsOnField++;
		}
		else {
			// Empty Clip Sound!
			System.out.println("Click!");
		}
		curBulletFireTimer = 0;
	}

	public int getCurOnField() {
		return curBulletsOnField;
	}

	public void resetCurOnField() {
		curBulletsOnField = 0;
	}

	// TODO: Need to change this into a link list or something, cuz this is limited and retarted!
	public void updateBullets(MapFactory mapFactory, Entity enemies[]) {
		for (int i = 0; i < curBulletsOnField; i++) {
			
			if (bullets[i].exists) {
				float nextX = bullets[i].getBulletPosX() + bullets[i].getDirX();
				float nextY = bullets[i].getBulletPosY() + bullets[i].getDirY();
				
				if (!mapFactory.isBlocked(nextX, nextY) && !BulletCollision(bullets, enemies)) {
					bullets[i].update(bulletSpeed);
				}
				else if (mapFactory.isBlocked(nextX, nextY)){
					bullets[i].destroyBullet();
					System.out.println("Wall Hit");
				}
			}
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

	public int getBulletFireRate() {
		return bulletFireRate;
	}

	public void setBulletFireRate(int bulletFireRate) {
		this.bulletFireRate = bulletFireRate;
	}

	public int getMaxBullets() {
		return maxBullets;
	}

	public void setMaxBullets(int maxBullets) {
		this.maxBullets = maxBullets;
	}

	public int getCurReloadTime() {
		return curReloadTime;
	}

	public void increaseCurReloadTime() {
		curReloadTime++;
	}

	public int getReloadSpeed() {
		return reloadSpeed;
	}

	public void setReloadSpeed(int reloadSpeed) {
		this.reloadSpeed = reloadSpeed;
	}

	public void resetCurReloadTime() {
		curReloadTime = 0;		
	}



	// TEMP 
	// super bang collision dang!
	public boolean BulletCollision(Bullet bullets[], Entity enemies[]) {
		boolean collision = false;
		// NOTE; if intersected push that shit outa the way
		for (int b = 0; b < curBulletsOnField; b++) {
			if (bullets[b].exists) {
				for (int i = 0; i < enemies.length; i++) { 
					if(bullets[b].rec.intersects(enemies[i].rec) ) { // && !enemies[i].dead) {
						System.out.println("Bullet Collision? w/ Enemy " +i);
						collision = true;
						
						enemies[i].getHit();
						bullets[b].destroyBullet();
					}
				}
			}
		}
		return collision;
	}

}
