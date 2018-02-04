package Objects;
import java.awt.*;

import Level.Map;
import main.GameCourt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;


public class Player extends GameObj {
	
	private BufferedImage[] sprites;

	public java.util.Map<String, Integer> questController;
	
	
	
	public Player(Map map) {
		
		super(0, 0, 0, 0, 32, 64, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, map);
		
		width = 32;
		height = 64;
		cWidth = 32;
		cHeight = 64;
		moveSpeed = 1;
		maxSpeed = 1.2;
		fallSpeed = 3;
		jumpStart = -30;
		
		facingRight = true;
		animation = new Animation();
		
		sprites = new BufferedImage[3];
		vy = 1;
		jumping = true;
		questController = new HashMap<String, Integer>();

		if (map.map[11][6] == 3){
			questController.put("Tutorial",1);
		}

		
		try {
            sprites[0] = ImageIO.read(new File("files/idle.gif"));
            sprites[1] = ImageIO.read(new File("files/run.gif"));
            sprites[2] = ImageIO.read(new File("files/jump.png"));

        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
	}
	
	public void movePlayer() {
		
		
		if (left) {
			vx = (int) Math.min((int) vx - moveSpeed, maxSpeed);
		}
		if (right) {
			vx = (int) Math.max((int) vx + moveSpeed, maxSpeed);
		
		}
		if (!left && !right) {
			vx = 0;
		}
				
		if (up && jumping == false) {
			vy = (int) jumpStart;
			jumping = true;
		}
		
		if (jumping) {
			vy = (int)(vy + fallSpeed);
		}
		
	}
	
	public void tick() {
		
		movePlayer(); //might not be correct
		checkBlockCollision();
		setPx(temp1);
		setPy(temp2);
		
		if (vy != 0) {
			BufferedImage[] jumpAni = {sprites[2]};
			animation.setFrames(jumpAni);
			animation.setDelay(100);
		}
		else if (vx != 0) {
			BufferedImage[] walkAni = {sprites[0], sprites[1]};
			animation.setFrames(walkAni);
			animation.setDelay(100);
		}
		else {
			BufferedImage[] idleAni = {sprites[0]};
			animation.setFrames(idleAni);
			animation.setDelay(400);
		}
	}
	
	public void draw(Graphics g) {
		setMp();
		
		if (vx >= 0) {
			if (vx == 0 && (facingRight == false)) {
				g.drawImage(animation.getImage(), (int) (px+mapx-width / 2), (int) (py + mapy - height / 2), -width, height, null);
			}
			else{
				facingRight = true;
				g.drawImage(animation.getImage(), (int) (px+mapx-width / 2), (int) (py + mapy - height / 2), width, height, null);
			}
		}
		if (vx < 0) {
			g.drawImage(animation.getImage(), (int) (px+mapx-width / 2), (int) (py + mapy - height / 2), -width, height, null);
			facingRight = false;
		}
		
	}

}
