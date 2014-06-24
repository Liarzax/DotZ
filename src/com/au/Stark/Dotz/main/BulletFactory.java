package com.au.Stark.Dotz.main;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BulletFactory {
	// TODO: Fix this class so all it should do eventuly is just handle bullets created/moving/removing
	
	// When add wepons/guns. Have the guns as seperate class, and it passes in thhe values such as
	// bullet speed, max bullets, bullet fire rate, reload speed, etc to the factory to handle firing those specific bullets!

	// Pew Pew Details
	int bulletSpeed = 6;
	private Animation pew;

	// firing speed controller. Possible to use game timer? or timer class?
	int curBulletFireTimer = 0;
	int bulletFireRate = 20;
	
	List<Bullet> bullets = new ArrayList<Bullet>();

	// Misc
	//Animation Duration
	private int duration = 300;

	// temp reaload shiz
	private int curReloadTime = 0;
	private int reloadSpeed = 80;


	public BulletFactory() {

	}

	public void initBulletFactory() throws SlickException {
		// Could load multiple then assign bullets depending on clip in wepon? or bullets in clip? idk.
		Image [] movePew = {new Image("assets/pew.png"), new Image("assets/pew.png")};

		pew = new Animation(movePew, duration, false);
	}
	
	public void createBullet(Entity entity) {
		// this should eventuly be a check to see if you have bullets, fire if not, click.
		if (entity.currentClip.curBullets > 0) {
			// Gun Fire Sound!
			System.out.println("Bang!");
			Bullet bullet = new Bullet();
			bullet.setPew(pew);
			// Unit Position
			bullet.setBulletPosX((int)entity.curX + (entity.faceingX *2));
			bullet.setBulletPosY((int)entity.curY + (entity.faceingY *2));
			// Could have faceing, normalised then bam... easy?
			bullet.setDirX(entity.faceingX);
			bullet.setDirY(entity.faceingY);
			
			bullet.exists = true;
			bullets.add(bullet);
			
			entity.currentClip.curBullets--;
		}
		else {
			// Empty Clip Sound!
			System.out.println("Click!");
		}
		curBulletFireTimer = 0;
	}

	// TODO: Need to change this into a link list or something, cuz this is limited and retarted!
	public void updateBullets(MapFactory mapFactory, Entity enemies[]) {
		for (int i = 0; i < bullets.size(); i++) {
		
			float nextX = bullets.get(i).getBulletPosX() + bullets.get(i).getDirX();
			float nextY = bullets.get(i).getBulletPosY() + bullets.get(i).getDirY();
			
			if (!mapFactory.isBlocked(nextX, nextY) && !BulletCollision(bullets, enemies)) {
				bullets.get(i).update(bulletSpeed);
			}
			else if (mapFactory.isBlocked(nextX, nextY)){
				bullets.get(i).destroyBullet();
				System.out.println("Wall Hit");
				bullets.remove(i);
			}
			
		}

		if (curBulletFireTimer < bulletFireRate) {
			curBulletFireTimer++;
		}
		else {
			// Do Nothing / wait for timer before able to fire again.
		}
	}
	
	public void renderBullets(Graphics g, boolean debug) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render();
			
			if (debug) {
				renderDebug(bullets.get(i), g);
			}
		}
	}
	
	void renderDebug(Bullet b, Graphics g) {
		// Draw Collision Boxes.
		g.setColor(new Color(0f,0f,1f,1f));
		g.drawRect(b.rec.getX(), b.rec.getY(), b.rec.getWidth(), b.rec.getHeight());
		
	}

	public int getBulletFireRate() {
		return bulletFireRate;
	}

	public void setBulletFireRate(int bulletFireRate) {
		this.bulletFireRate = bulletFireRate;
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
	public boolean BulletCollision(List<Bullet> bullets, Entity enemies[]) {
		boolean collision = false;
		// NOTE; if intersected push that shit outa the way
		for (int b = 0; b < bullets.size(); b++) {
			if (bullets.get(b).exists) {
				for (int i = 0; i < enemies.length; i++) { 
					if(bullets.get(b).rec.intersects(enemies[i].rec) && !enemies[i].dead) {
						System.out.println("Bullet Collision? w/ Enemy " +i);
						collision = true;
						
						enemies[i].getHit();
						bullets.get(b).destroyBullet();
					}
				}
			}
		}
		return collision;
	}

}
