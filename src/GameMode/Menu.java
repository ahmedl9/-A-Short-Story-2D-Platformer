package GameMode;

import Level.Background;
import java.io.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.awt.event.KeyEvent;

public class Menu extends GameMode{
	
	private Background background;
	
	int choice = 0;
	private String[] choices = {"Continue Game", "New Game", "Instructions"};
	

	
	public Menu(GameModeController c) {
		this.c = c;
		 try {
			 this.background = new Background("files/SkyBackground.gif", 1);
			 background.setVelocity(-0.1, 0.0);
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
		g.setColor(Color.BLACK);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		g.drawString("A Short Story", 400, 100);
		
		for (int x = 0; x < choices.length; x++) {
			if (x == choice) {
				g.setColor(Color.BLACK);;
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(choices[x], 145, 140 + x * 50);
		}
	}
	
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			if (choice == 0) {
				c.setMode(1);
			}
			if (choice == 1) {
				//load from save file
				try {
					BufferedReader br = new BufferedReader(new FileReader("files/defaultMap.txt"));
					BufferedWriter fw = new BufferedWriter(new FileWriter("files/map.txt"));
					String s = br.readLine();
					while(!s.equals("end")) {
						fw.write(s);
						fw.newLine();
						s = br.readLine();
					}
					br.close();
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (choice == 2) {
				c.setMode(2);
			}
		}
		if (k == KeyEvent.VK_UP) {
			choice = choice - 1;
			if (choice < 0) {
				choice = choices.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			choice = choice + 1;
			if (choice == choices.length) {
				choice = 0;
			}

			
		}

	}
	
	public void keyReleased(int k) {
		
	}
}
