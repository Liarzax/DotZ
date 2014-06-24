package com.au.Stark.Dotz.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

//import de.lessvoid.nifty.Nifty;

public class HUD {

	//Nifty nifty;
	// temp - player party size = 3
	String statusEmpty = "[ ]", statusChecked = "[x]";
	//AIStatus = AI On/Off, AIFStatus = Follow On/Off, AISStatus = AutoShoot On/Off
	String p1AIStatus = statusEmpty, p2AIStatus = statusEmpty, p3AIStatus = statusEmpty;
	String p1AIFStatus = statusEmpty, p2AIFStatus = statusEmpty, p3AIFStatus = statusEmpty;
	String p1AISStatus = statusEmpty, p2AISStatus = statusEmpty, p3AISStatus = statusEmpty;

	String p1Status = "Player 1 AI"+p1AIStatus+" - F"+p1AIFStatus+" S"+p1AISStatus;
	String p2Status = "Player 2 AI"+p2AIStatus+" - F"+p2AIFStatus+" S"+p2AISStatus;
	String p3Status = "Player 3 AI"+p3AIStatus+" - F"+p3AIFStatus+" S"+p3AISStatus;

	public HUD() {

	}

	public void init() {
		//nifty = new Nifty(null, null, null, null);
	}

	public void update(Entity players[]) {
		tempUpdate(players);

	}

	public void render(Graphics g, boolean debug) {
		tempRender(g, debug);
	}

	// Temp stuff
	private void tempUpdate(Entity players[]) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].aiStatusChange) {
				if (players[i].ai) {
					switch(i) {
					case 0: p1AIStatus = statusChecked;
					break;
					case 1: p2AIStatus = statusChecked;
					break;
					case 2: p3AIStatus = statusChecked;
					break;
					default: 
						break;
					}
				}
				else {
					switch(i) {
					case 0: p1AIStatus = statusEmpty;
					break;
					case 1: p2AIStatus = statusEmpty;
					break;
					case 2: p3AIStatus = statusEmpty;
					break;
					default: 
						break;
					}
				}
	
				if (players[i].aiFollow) {
					switch(i) {
					case 0: p1AIFStatus = statusChecked;
					break;
					case 1: p2AIFStatus = statusChecked;
					break;
					case 2: p3AIFStatus = statusChecked;
					break;
					default: 
						break;
					}
				}
				else {
					switch(i) {
					case 0: p1AIFStatus = statusEmpty;
					break;
					case 1: p2AIFStatus = statusEmpty;
					break;
					case 2: p3AIFStatus = statusEmpty;
					break;
					default: 
						break;
					}
				}
	
				if (players[i].aiShoot) {
					switch(i) {
					case 0: p1AISStatus = statusChecked;
					break;
					case 1: p2AISStatus = statusChecked;
					break;
					case 2: p3AISStatus = statusChecked;
					break;
					default: 
						break;
					}
				}
				else {
					switch(i) {
					case 0: p1AISStatus = statusEmpty;
					break;
					case 1: p2AISStatus = statusEmpty;
					break;
					case 2: p3AISStatus = statusEmpty;
					break;
					default: 
						break;
					}
				}
				players[i].aiStatusChange = false;
			}
		}
		
		// update new string.
		p1Status = "Unit 1  |  HP = [|||||||]  |  Ammo = [||||||||||]  |  AI"+p1AIStatus+" - F"+p1AIFStatus+" S"+p1AISStatus;
		p2Status = "Unit 2  |  HP = [|||||||]  |  Ammo = [||||||||||]  |  AI"+p2AIStatus+" - F"+p2AIFStatus+" S"+p2AISStatus;
		p3Status = "Unit 3  |  HP = [|||||||]  |  Ammo = [||||||||||]  |  AI"+p3AIStatus+" - F"+p3AIFStatus+" S"+p3AISStatus;
	}

	private void tempRender(Graphics g, boolean debug) {		
		// Start position 20, 580 then +20 to 580. (Can fit 1 more line at 560).
		// Vertical spacing = 20
		g.setColor(Color.white);
		
		g.drawString(p1Status, 20, 580);
		g.drawString(p2Status, 20, 600);
		g.drawString(p3Status, 20, 620);
		
		// TODO make this more prettier during debuging.
		/*if (debug) {
						
		}*/
	}
}
