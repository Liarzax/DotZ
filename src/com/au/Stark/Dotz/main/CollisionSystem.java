package com.au.Stark.Dotz.main;

public class CollisionSystem {
	
	// have the colidable objects in here? like
	// enemies
	
	// players?
	
	
	public CollisionSystem() {
		
	}
	
	public void initCollisionSystem() {
		System.out.println("Collision System Active");
		// stuff
	}
	
	public boolean detectCollision(Entity curEntity, Entity othEntity) {
		boolean collision = false;

		if(curEntity.rec.intersects(othEntity.rec) && !othEntity.dead) {
			//System.out.println("Collision Between Enemy "+ (othEntity.entID+1) +" and Player "+ (curEntity.entID+1));
			collision = true;
		}
		
		return collision;
	}
	
	public void detectVision(Entity players[], Entity enemies[]) {
		for (int i = 0; i < enemies.length; i++) {
			enemies[i].visible = false;
		}
		
		for (int p = 0; p < players.length; p++) {
			for (int i = 0; i < enemies.length; i++) {
				if(players[p].sightRadius.intersects(enemies[i].rec)) {
					//System.out.println("Player "+ (players[p].entID+1) +" can See Enemy "+ (enemies[i].entID+1) +"!!!");
					enemies[i].visible = true;
				}
			}
		}
	}

	public void processCollisions(Entity entity1, Entity entity2) {
		
	}

	public void handleCollisions(Entity curEntity, Entity othEntity) {
		// TODO Auto-generated method stub
		//System.out.println("Handling Collision w/ Enemy "+ (othEntity.entID+1) +" and Player "+ (curEntity.entID+1));
		
		float xOverlap = Math.abs((curEntity.nextX) - (othEntity.nextX));
		float yOverlap = Math.abs((curEntity.nextY) - (othEntity.nextY));
		xOverlap = xOverlap/16;
		yOverlap = yOverlap/16;
		
		System.out.println("othEntity.nextX "+ othEntity.nextX);
		System.out.println("curEntity.nextX "+ curEntity.nextX);
		System.out.println("xOverlap "+ xOverlap);
		System.out.println("yOverlap "+ yOverlap);
		
		if (curEntity.curX < othEntity.curX) {
			curEntity.nextX -= xOverlap;
			othEntity.nextX += xOverlap;
			curEntity.curX = curEntity.nextX;
			othEntity.curX = othEntity.nextX;
		}
		else {
			curEntity.nextX += xOverlap;
			othEntity.nextX -= xOverlap;
			curEntity.curX = curEntity.nextX;
			othEntity.curX = othEntity.nextX;
		}
		
		if (curEntity.curY < othEntity.curY) {
			curEntity.nextY -= yOverlap;
			othEntity.nextY += yOverlap;
			curEntity.curY = curEntity.nextY;
			othEntity.curY = othEntity.nextY;
		}
		else {
			curEntity.nextY += yOverlap;
			othEntity.nextY -= yOverlap;
			curEntity.curY = curEntity.nextY;
			othEntity.curY = othEntity.nextY;
		}
		
		
	}
	
}
