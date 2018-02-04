package GameMode;

import java.awt.*;
import java.awt.event.KeyEvent;

import Level.Map;
import Level.Background;
import main.GameCourt;
import Objects.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

public class World extends GameMode{
	
	private Map map;
	private Background background;
	private Player player;
	private String textDraw;
	
	public World(GameModeController c) {
		this.c = c;
		init();
	}
	
	public void init() {
		textDraw = "";
		map = new Map(32, "files/redDino.png", "files/blueDino.png","files/dirtBlockSmall.png");
		map.loadMap("files/map.txt");
		map.setP(0, 0);
		
		background = new Background("files/SkyBackground.gif", 0.1);


		player = new Player(map);
		player.setPx(100);
		player.setPy(100);
		
	}
	public void tick () {
		player.tick();
		map.setP(GameCourt.COURT_WIDTH / 2 - player.getPx(), GameCourt.COURT_HEIGHT / 2 - player.getPy());
	}
	
	public String questStatus () {
		if (!player.questController.containsKey("Tutorial")) {
			return "Quest: Not Begun";			}
		else if (player.questController.get("Tutorial")==0) {
			return "Quest: Begun";
		}
		else {
			return "Quest: Completed!";
		}
	}
	
	public void draw(Graphics g) {
		background.draw(g);
		
		
		map.draw(g);
		
		player.draw(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 450, GameCourt.COURT_WIDTH, 50);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		g.drawString(questStatus(), 50,475);
		
		if (textDraw != "") {
			g.setColor(Color.BLACK);
			g.fillRect(0, 300, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT / 3 - 100);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
			g.drawString(textDraw, 50,350);
			
			
			if (textDraw.equals("My brother sent you? Thanks for saving me!")){
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.setColor(Color.BLACK);
				g.fillRect(0,  0, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map.map[11][25] = 1;
				map.map[11][6] = 3;

				try {
					BufferedReader br = new BufferedReader(new FileReader("files/alteredMap.txt"));
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
		}

	}
	public void keyPressed(int k) {
		if(k==KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if(k==KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if(k==KeyEvent.VK_UP) {
			player.setUp(true);
		}
		
		if(k==KeyEvent.VK_SPACE && player.cantalk == 2){
			if (!player.questController.containsKey("Tutorial")) {
				textDraw = "My brother is trapped way up there! Can you talk to him so he comes back down?";
				player.questController.put("Tutorial",0);
			}
			else if (player.questController.get("Tutorial")==0) {
				textDraw = "Quick, we don't have much time! My brother is up there!";
			}
			else {
				textDraw = "Thank you for saving him!";
			}
		}
		
		if(k==KeyEvent.VK_SPACE && player.cantalk == 3){
			if (!player.questController.containsKey("Tutorial")) {
				textDraw = "Who are you? My brother says not to talk to strangers!";			}
			else if (player.questController.get("Tutorial")==0) {
				textDraw = "My brother sent you? Thanks for saving me!";
				player.questController.put("Tutorial", 1);
			}
			else {
				textDraw = "Thank you for saving me!";
			}
		}
		
		
	}
	public void keyReleased(int k) {
		if(k==KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if(k==KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if(k==KeyEvent.VK_UP) {
			player.setUp(false);
		}
		if(k==KeyEvent.VK_SPACE) {
			textDraw = "";
		}

	}
}
