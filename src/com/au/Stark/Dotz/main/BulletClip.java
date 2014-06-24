package com.au.Stark.Dotz.main;

public class BulletClip {
	// item to hold bullets, collectable, etc...
	
	int curBullets = 10;
	int maxBullets = 10;
	
	public BulletClip() {
		
	}
	
	
	
	public int getCurBullets() {
		return curBullets;
	}

	public void setCurBullets(int curBullets) {
		this.curBullets = curBullets;
	}

	public int getMaxBullets() {
		return maxBullets;
	}

	public void setMaxBullets(int maxBullets) {
		this.maxBullets = maxBullets;
	}
	
}
