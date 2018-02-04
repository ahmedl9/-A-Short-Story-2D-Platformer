package GameMode;

import Level.Background;

import java.awt.*;
import java.io.IOException;

import java.awt.event.KeyEvent;


public class Instructions extends GameMode{
	private Background background;
	
	
	int choice = 0;
	private String[] lines = {"Hey, welcome to a 'A Short Story'", 
								"The game is quite simple. Use the arrow keys to move/jump",
								"If you wish to interact with an object, move towards it while",
								"clicking the space bar. The game itself is a mini-tutorial where", 
								"you must complete a simple quest, jumping through platforms to get",
								"to your goal! Your orders of interactions will yield different",
								"dialogue. Have fun playing! - Press Enter to return to menu."};
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public Instructions(GameModeController c) {
		this.c = c;
		try {
			 this.background = new Background("files/SkyBackground.gif", 1);
			 background.setVelocity(-0.1, 0.0);
			 this.titleColor = new Color(0, 0, 0);
			 this.titleFont = new Font("Times New Roman", Font.PLAIN, 32);
		 } catch (Exception e) {
	            System.out.println("Internal Error:" + e.getMessage());
	     }
	}
	
	public void init() {
	}
	
	public void tick() {
		background.tick();
	}
	
	public void draw(Graphics g) {
		background.draw(g);
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Instructions", 400, 100);
		
		for (int x = 0; x < lines.length; x++) {
			g.drawString(lines[x], 45, 140 + x * 50);
		}
	}
	
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			c.setMode(0);
		}
	}
	
	public void keyReleased(int k) {
	}
}
