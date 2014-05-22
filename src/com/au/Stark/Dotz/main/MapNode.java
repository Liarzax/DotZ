package com.au.Stark.Dotz.main;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

public class MapNode {
	
	private Vector2f position = new Vector2f();;
	private int nodeID;
	private Circle node;
	private float nodeSize = 1f;
	
	private boolean blocked = false;
	
	public MapNode() {
		
	}
	
	public void initMapNode(Vector2f nodePosition, int nodeID) {
		this.position = nodePosition;
		this.nodeID = nodeID;
		setNode(nodePosition.getX(), nodePosition.getY(), nodeSize);
	}
	
	public void initMapNode(float x, float y, int nodeID) {
		this.position.x = x;
		this.position.y = y;
		this.nodeID = nodeID;
		
		setNode(x, y, nodeSize);
	}
	
	public void renderNode(Graphics g, boolean debug) {
		if(debug) {
			if (blocked) {
				g.setColor(Color.red);
				g.draw(node);
			}
			else {
				g.setColor(Color.green);
				g.draw(node);
			}
		}
	}
	
	// create circle to see during dbugging.
	private void setNode(float x, float y, float size) {
		this.node = new Circle(x, y, size);
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public int getNodeID() {
		return nodeID;
	}

}
