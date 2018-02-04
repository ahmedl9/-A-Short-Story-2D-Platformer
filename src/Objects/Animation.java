package Objects;

import java.awt.image.BufferedImage;

public class Animation {
	private BufferedImage[] frames;
	private int cFrame;
	
	private long start;
	private long delay;
	int filler;
	
	public Animation() {
		filler = 0;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		cFrame = 0;
		start = System.nanoTime();
	}
	
	public void setDelay(long d) { 
		delay = d;
	}
	

	public BufferedImage getImage() {
		return frames[cFrame];
	}
	
	
}
