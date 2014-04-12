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
	private Animation pewLR, pewUD;

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
		Image [] movePewLR = {new Image("assets/pewLR.png"), new Image("assets/pewLR.png")};
		Image [] movePewUD = {new Image("assets/pewUD.png"), new Image("assets/pewUD.png")};

		pewLR = new Animation(movePewLR, duration, false);
		pewUD = new Animation(movePewUD, duration, false);
	}

	public void createBullet(Animation sprite, int x, int y, int faceingX, int faceingY) {
		if (curBulletsOnField < maxBullets) {
			// Gun Fire Sound!
			System.out.println("Bang!");
			bullets[curBulletsOnField] = new Bullet();
			bullets[curBulletsOnField].setPewLR(pewLR);
			bullets[curBulletsOnField].setPewUD(pewUD);
			bullets[curBulletsOnField].setBulletPosX(x + (faceingX *2));
			bullets[curBulletsOnField].setBulletPosY(y + (faceingY *2));
			bullets[curBulletsOnField].setDirX(faceingX);
			bullets[curBulletsOnField].setDirY(faceingY);

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
	public void updateBullets(MapFactory mapFactory) {
		for (int i = 0; i < curBulletsOnField; i++) {
			if (!mapFactory.isBlocked(bullets[i].getBulletPosX() + bullets[i].getDirX(), bullets[i].getBulletPosY() + bullets[i].getDirY())) {
				bullets[i].update(bulletSpeed);
			}
			//bullets[i].update(bulletSpeed);
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




}
