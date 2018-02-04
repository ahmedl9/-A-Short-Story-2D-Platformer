package Level;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;

import main.GameCourt;


public class Map {
	
	private double px;
	private double py;
	
	
	private int minx;
	private int maxx;
	private int miny;
	private int maxy;
	
	
	public int[][] map;
	private int blockSize;
	private int rowCount;
	private int colCount;
	private int width;
	private int height;
	
	private BufferedImage block;
	
	private int rowStart;
	private int colStart;
	private int rowDrawCount;
	private int colDrawCount;
	
	private BufferedImage npc;
	private BufferedImage npc2;

	
	public Map(int blockSize, String s1, String s2, String s3) {
		this.blockSize = blockSize;
		this.rowDrawCount = GameCourt.COURT_HEIGHT / blockSize + 2;
		this.colDrawCount = GameCourt.COURT_WIDTH / blockSize + 2;
		
		try {
            if (npc == null) {
                npc = ImageIO.read(new File(s1));
            }			
        } 
		catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
		try {
            if (npc2 == null) {
                npc2 = ImageIO.read(new File(s2));
            }			
        } 
		catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
		try {
            if (block == null) {
                block = ImageIO.read(new File(s3));
            }			
        } 
		catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
	}
	
	
	public void loadMap(String s) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(s));
			colCount = Integer.parseInt(br.readLine());
			rowCount = Integer.parseInt(br.readLine());
			width = colCount * blockSize;
			height = rowCount * blockSize;
			
			minx = GameCourt.COURT_WIDTH - width;
			maxx = 0;
			miny = GameCourt.COURT_HEIGHT - height;
			maxy = 0;
				
			map = new int[rowCount][colCount];
			String ds = "\\s+";
			
					
			for (int x = 0; x <rowCount; x++) {
				String line = br.readLine();
				String[] tk = line.split(ds);
				for (int y = 0; y < colCount; y++) {
					map[x][y] = Integer.parseInt(tk[y]);
				}
				
			}
			br.close();

			
        } 
		catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }	 
		
	}
	
	public int getBlockSize() {
		return blockSize;
	}
	
	public int[][] getMap() {
		return map;
	}
	
	public int getx() {
		return (int) px;
	}
	
	public int gety() {
		return (int) py;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void setP(double x, double y) {
		this.px = x;
		this.py = y;
		if (px < minx) {
			px = minx;
		}
		if (py < miny) {
			py = miny;
		}
		if (px > maxx) {
			px = maxx;
		}
		if (py > maxy) {
			py = maxy;
		}
		
		colStart = (int)-this.px / blockSize;
		rowStart = (int)-this.py / blockSize;
	}
	
	public void draw(Graphics g) {
		for (int r = rowStart; r < rowDrawCount + rowStart; r++) {
			
			if (r >= rowCount) {
				break;
			}
			
			for (int c = colStart; c < colDrawCount + colStart; c++) {
				
				if (c > colCount) {
					break;
				}
				
				if (map[r][c] == 1) {
					g.drawImage(block, (int)px + c * blockSize, (int)py + r * blockSize, null);
				}
				if (map[r][c] == 2) {
					g.drawImage(npc, (int)px + c * blockSize, (int)py + r * blockSize, null); 
				}
				if (map[r][c] == 3) {
					g.drawImage(npc2, (int)px + c * blockSize, (int)py + r * blockSize, null); 
				}				
				
			}
		}
	}

	

}
