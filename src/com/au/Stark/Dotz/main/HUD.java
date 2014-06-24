package com.au.Stark.Dotz.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

//import de.lessvoid.nifty.Nifty;

public class HUD {

	//Nifty nifty;
	// temp - player party size = 3
	String statusEmpty = "[ ]", statusChecked = "[x]";
	String ammo0 = "[          ]";
	String ammo1 = "[|         ]", ammo2 = "[||        ]", ammo3 = "[|||       ]", ammo4 = "[||||      ]", ammo5  = "[|||||     ]";
	String ammo6 = "[||||||    ]", ammo7 = "[|||||||   ]", ammo8 = "[||||||||  ]", ammo9 = "[||||||||| ]", ammo10 = "[||||||||||]";
	
	//AIStatus = AI On/Off, AIFStatus = Follow On/Off, AISStatus = AutoShoot On/Off
	String p1AIStatus = statusEmpty, p2AIStatus = statusEmpty, p3AIStatus = statusEmpty;
	String p1AIFStatus = statusEmpty, p2AIFStatus = statusEmpty, p3AIFStatus = statusEmpty;
	String p1AISStatus = statusEmpty, p2AISStatus = statusEmpty, p3AISStatus = statusEmpty;
	// ammo/ clips
	String p1Ammo = ammo10, p2Ammo = ammo10, p3Ammo = ammo10;
	int p1Clips = 00, p2Clips = 00, p3Clips = 00;

	String p1Status = "Player 1";
	String p2Status = "Player 2";
	String p3Status = "Player 3";

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
			
			// TODO - Break this into a more Modular Fashion
			// Check for other changes.
			switch(i) {
			case 0: 
				p1Clips = players[i].availableClips.size();
				if (players[i].currentClip == null) {
					p1Ammo = ammo0;
				}
				else if (players[i].currentClip.curBullets == 0) {
					p1Ammo = ammo0;
				}
				else if (players[i].currentClip.curBullets == 1) {
					p1Ammo = ammo1;
				}
				else if (players[i].currentClip.curBullets == 2) {
					p1Ammo = ammo2;
				}
				else if (players[i].currentClip.curBullets == 3) {
					p1Ammo = ammo3;
				}
				else if (players[i].currentClip.curBullets == 4) {
					p1Ammo = ammo4;
				}
				else if (players[i].currentClip.curBullets == 5) {
					p1Ammo = ammo5;
				}
				else if (players[i].currentClip.curBullets == 6) {
					p1Ammo = ammo6;
				}
				else if (players[i].currentClip.curBullets == 7) {
					p1Ammo = ammo7;
				}
				else if (players[i].currentClip.curBullets == 8) {
					p1Ammo = ammo8;
				}
				else if (players[i].currentClip.curBullets == 9) {
					p1Ammo = ammo9;
				}
				else if (players[i].currentClip.curBullets == 10) {
					p1Ammo = ammo10;
				}
			break;
			
			case 1: 
				p2Clips = players[i].availableClips.size();
				if (players[i].currentClip == null) {
					p2Ammo = ammo0;
				}
				else if (players[i].currentClip.curBullets == 0) {
					p2Ammo = ammo0;
				}
				else if (players[i].currentClip.curBullets == 1) {
					p2Ammo = ammo1;
				}
				else if (players[i].currentClip.curBullets == 2) {
					p2Ammo = ammo2;
				}
				else if (players[i].currentClip.curBullets == 3) {
					p2Ammo = ammo3;
				}
				else if (players[i].currentClip.curBullets == 4) {
					p2Ammo = ammo4;
				}
				else if (players[i].currentClip.curBullets == 5) {
					p2Ammo = ammo5;
				}
				else if (players[i].currentClip.curBullets == 6) {
					p2Ammo = ammo6;
				}
				else if (players[i].currentClip.curBullets == 7) {
					p2Ammo = ammo7;
				}
				else if (players[i].currentClip.curBullets == 8) {
					p2Ammo = ammo8;
				}
				else if (players[i].currentClip.curBullets == 9) {
					p2Ammo = ammo9;
				}
				else if (players[i].currentClip.curBullets == 10) {
					p2Ammo = ammo10;
				}
			break;
			
			case 2: 
				p3Clips = players[i].availableClips.size();
				if (players[i].currentClip == null) {
					p3Ammo = ammo0;
				}
				else if (players[i].currentClip.curBullets == 0) {
					p3Ammo = ammo0;
				}
				else if (players[i].currentClip.curBullets == 1) {
					p3Ammo = ammo1;
				}
				else if (players[i].currentClip.curBullets == 2) {
					p3Ammo = ammo2;
				}
				else if (players[i].currentClip.curBullets == 3) {
					p3Ammo = ammo3;
				}
				else if (players[i].currentClip.curBullets == 4) {
					p3Ammo = ammo4;
				}
				else if (players[i].currentClip.curBullets == 5) {
					p3Ammo = ammo5;
				}
				else if (players[i].currentClip.curBullets == 6) {
					p3Ammo = ammo6;
				}
				else if (players[i].currentClip.curBullets == 7) {
					p3Ammo = ammo7;
				}
				else if (players[i].currentClip.curBullets == 8) {
					p3Ammo = ammo8;
				}
				else if (players[i].currentClip.curBullets == 9) {
					p3Ammo = ammo9;
				}
				else if (players[i].currentClip.curBullets == 10) {
					p3Ammo = ammo10;
				}
			break;
			
			default: 
				break;
			}
			
		}
		
		// update new string.
		p1Status = "Unit 1  |  HP = [|||||||]  |  Ammo = "+p1Ammo+" - Clips "+p1Clips+"  |  AI"+p1AIStatus+" - F"+p1AIFStatus+" S"+p1AISStatus;
		p2Status = "Unit 2  |  HP = [|||||||]  |  Ammo = "+p2Ammo+" - Clips "+p2Clips+"  |  AI"+p2AIStatus+" - F"+p2AIFStatus+" S"+p2AISStatus;
		p3Status = "Unit 3  |  HP = [|||||||]  |  Ammo = "+p3Ammo+" - Clips "+p3Clips+"  |  AI"+p3AIStatus+" - F"+p3AIFStatus+" S"+p3AISStatus;
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
