package com.au.Stark.Dotz.main;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BulletFactory {
	
	// Pew Pew Details
	int bulletSpeed = 6;
    private Animation pew, pewLR, pewUD;
    
    
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
		
		//Bullet bullet = new Bullet();
		/*bullet.bulletText();
		bullet.setPewLR(pewLR);
		bullet.setPewUD(pewUD);
		bullet.setBulletPosX(x + (faceingX *2));
		bullet.setBulletPosY(y + (faceingY *2));
		bullet.setDirX(faceingX);
		bullet.setDirY(faceingY);*/
		
		/*Bullet bullet = new Bullet();
		bullet.bulletText(curBulletsOnField);
		bullet.setPewLR(pewLR);
		bullet.setPewUD(pewUD);
		bullet.setBulletPosX(x + (faceingX *2));
		bullet.setBulletPosY(y + (faceingY *2));
		bullet.setDirX(faceingX);
		bullet.setDirY(faceingY);*/
		
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
		
		//bullets.add(bullet);
		/*for (int i = 0; i < curOnField+1 ; i++) {
			bullets[i] = new Bullet();
			
			bullets[i].bulletText(i);
			bullets[i].setPewLR(pewLR);
			bullets[i].setPewUD(pewUD);
			bullets[i].setBulletPosX(x + (faceingX *2));
			bullets[i].setBulletPosY(y + (faceingY *2));
			bullets[i].setDirX(faceingX);
			bullets[i].setDirY(faceingY);
			
			bullets[i].completeBullet(i);
		}*/
		
		/*if (curBulletsOnField < maxBullets) {
			bullets[++curBulletsOnField] = new Bullet();
			
			bullets[++curBulletsOnField].bulletText(++curBulletsOnField);
			bullets[++curBulletsOnField].setPewLR(pewLR);
			bullets[++curBulletsOnField].setPewUD(pewUD);
			bullets[++curBulletsOnField].setBulletPosX(x + (faceingX *2));
			bullets[++curBulletsOnField].setBulletPosY(y + (faceingY *2));
			bullets[++curBulletsOnField].setDirX(faceingX);
			bullets[++curBulletsOnField].setDirY(faceingY);
			
			bullets[++curBulletsOnField].completeBullet(++curBulletsOnField);
			curBulletsOnField++;
			//setCurOnField(getCurOnField() + 1);
		}*/
		
		//bullets.addFirst(bullet); 
		//return bullet;
	}

	public int getCurOnField() {
		return curBulletsOnField;
	}

	public void setCurOnField(int curOnField) {
		this.curBulletsOnField = curOnField;
	}

	public void updateBullets() {
		// TODO Auto-generated method stub
		/*while (bullets != null) {
			((Bullet) bullets.getFirst()).update(bulletSpeed);
		}*/
		for (int i = 0; i < curBulletsOnField; i++) {
			//bullets[i].update(bulletSpeed);
			bullets[i].bulletPosX += (bullets[i].dirX * bulletSpeed);
			bullets[i].bulletPosY += (bullets[i].dirY * bulletSpeed);
		}
	}
	
	public void renderBullets() {
		// TODO Auto-generated method stub
		/*while (bullets != null) {
			((Bullet) bullets.getFirst()).update(bulletSpeed);
		}*/
		for (int i = 0; i < curBulletsOnField; i++) {
			bullets[i].render();
		}
	}
	
	
	

}
