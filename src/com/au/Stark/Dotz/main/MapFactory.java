package com.au.Stark.Dotz.main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MapFactory {

	//Init Map (20x14 Tiles).
	private TiledMap currentMap;
	// Map Collision Data
	private boolean[][] blocked;
	private static final int TILESIZE = 16;  // tile size (orig Map 32).
	
	// temp for debbugging.
	public MapNode[][] nodeMap;
	
	// tile ID

	// the level we are on/map we are using - could end up putting this into a stage class or something?
	// 0 = debug stage
	private int curStage = 0;
	private int lastStage = curStage--;
	private int nextStage = curStage++;

	private String map = "assets/maps/map"+curStage+".tmx";

	public MapFactory() {

	}


	public void init() {
		// TODO Auto-generated method stub
		try {
			//setCurMap(new TiledMap("assets/grassmap.tmx"));
			setCurMap(new TiledMap(map));
		} 
		catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// could not find the map brooo!!! BBOOOOMMMMM!!!!!
		}

		
		
		// Build MapCollision Array  based on tile properties in the TileD map 
		blocked = new boolean[getCurMap().getWidth()][getCurMap().getHeight()];
		// Create an Array of MapNodes for the nodeMap
		nodeMap = new MapNode[getCurMap().getWidth()][getCurMap().getHeight()];
		// Assign tempX/Y Values for the starting positions (These should be half the tile size)
		int tempX = (TILESIZE/2), tempY = (TILESIZE/2);
		// Loop through the map and begin filling our arrays.
		for (int xAxis=0;xAxis<getCurMap().getWidth(); xAxis++) {
			// Set the initial position for the yAxis
			tempY = (TILESIZE/2);
			
			// Loop through the yAxis
			for (int yAxis=0;yAxis<getCurMap().getHeight(); yAxis++) {
				// Assign a tile ID based on the x/yAxis CoOrds of the Map
				int tileID = getCurMap().getTileId(xAxis, yAxis, 0);
				// set the value of blocked as false if tileID matches
				String value = getCurMap().getTileProperty(tileID, "blocked", "false");
				
				// Create a new mapNode & initialize it at this location using tempX/Y
				nodeMap[xAxis][yAxis] = new MapNode();
				nodeMap[xAxis][yAxis].initMapNode(tempX, tempY, tileID);
				
				// if the value of blocked matches true, change from false to true
				if ("true".equals(value)) {
					blocked[xAxis][yAxis] = true;
					nodeMap[xAxis][yAxis].setBlocked(true);
				}				
				
				// Increment the y Axis
				tempY += (TILESIZE);
			}
			
			// Increment the x Axis
			tempX += (TILESIZE);
		}
		
		
	}

	// TODO Load Stages stuff!! LAH LAH LAH - for Debug [ = prev / ] = next
	public void loadNextStage () {
		// i think these two should be easy?
		// just change curStage and re call init, so simple?
		// eg; curStage = nextStage; <-- everything else works around this anyway, brb!
		System.out.println("Load Next Stage!");
		// will i need to unload stuff? how will this even be handled?
		lastStage = 1;
		curStage = 2;
		nextStage = 3;
		map = "assets/maps/map"+curStage+".tmx";

		init();
	}

	public void loadPrevStage () {
		System.out.println("Load Previous Stage!");
		// Could just do = --, but due to keyboard capture rate, temp value static value assigned.
		lastStage = 0;
		curStage = 1;
		nextStage = 2;
		map = "assets/maps/map"+curStage+".tmx";

		init();
	}


	// Done, BABEH!
	public boolean isBlocked(float x, float y) {
		int xBlock = (int)x / getTileSize();
		int yBlock = (int)y / getTileSize();
		return blocked[xBlock][yBlock];
	}

	public TiledMap getCurMap() {
		return currentMap;
	}

	public void setCurMap(TiledMap mapToSet) {
		this.currentMap = mapToSet;
	}

	public static int getTileSize() {
		return TILESIZE;
	}

	public int getCurStage() {
		return curStage;
	}

	public int getNextStage() {
		return nextStage;
	}

	public int getPrevStage() {
		return lastStage;
	}




}
