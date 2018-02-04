package Level;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import main.GameCourt;


public class Background {
	private BufferedImage img;
    private double px;
    private double py;
    private double vx;
    private double vy ;
	private double parralaxMove;
	
	public Background(String s, double ms) {
		try {
            if (img == null) {
                img = ImageIO.read(new File(s));
        		this.parralaxMove = ms;

            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
	}
	
	public void setPosition(double x, double y) {
		this.px = (x * parralaxMove) % GameCourt.COURT_WIDTH;
		this.py = (y * parralaxMove) % GameCourt.COURT_WIDTH;
	}
	
	public void setVelocity(double x, double y) {
		this.vx = x;
		this.vy = y;
	}
	
	public void tick() {
		px = px + vx;
		py = py + vy;
	}
	
	public void draw(Graphics g) {

		g.drawImage(img, (int) px, (int) py, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, null);
		if (px < 0) {
			g.drawImage(img, (int)px+GameCourt.COURT_WIDTH, (int) py, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, null);
		}
		if (px > 0) {
			g.drawImage(img, (int)px-GameCourt.COURT_WIDTH, (int) py, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT, null);
		}
	}
}
