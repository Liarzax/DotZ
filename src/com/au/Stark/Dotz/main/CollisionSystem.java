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
				if(players[p].sightRadius.intersects(enemies[i].rec) && !enemies[i].dead) {
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
		System.out.println("Handling Collision w/ Enemy "+ (othEntity.entID+1) +" and Player "+ (curEntity.entID+1));
		
		//float xOverlap = Math.abs((curEntity.nextX - curEntity.centerOfSprite) - (othEntity.nextX - othEntity.centerOfSprite));
		//float yOverlap = Math.abs((curEntity.nextY - curEntity.centerOfSprite) - (othEntity.nextY - othEntity.centerOfSprite));
		float xOverlap = Math.abs((curEntity.nextX) - (othEntity.nextX));
		float yOverlap = Math.abs((curEntity.nextY) - (othEntity.nextY));
		
		System.out.println("othEntity.nextX "+ othEntity.nextX);
		System.out.println("curEntity.nextX "+ curEntity.nextX);
		System.out.println("xOverlap "+ xOverlap);
		System.out.println("yOverlap "+ yOverlap);
		
		
		
		/*curEntity.nextX = curEntity.nextX - ((curEntity.Velocity/(curEntity.Velocity + othEntity.Velocity)) * xOverlap);
		curEntity.nextY = curEntity.nextY - ((curEntity.Velocity/(curEntity.Velocity + othEntity.Velocity)) * yOverlap);
			
			System.out.println("nextX "+ curEntity.nextX);
			System.out.println("nextY "+ curEntity.nextY);
			
		othEntity.nextX = othEntity.nextX - ((othEntity.Velocity/(othEntity.Velocity + curEntity.Velocity)) * xOverlap);
		othEntity.nextY = othEntity.nextY - ((othEntity.Velocity/(othEntity.Velocity + curEntity.Velocity)) * yOverlap);*/
	}
	
}
