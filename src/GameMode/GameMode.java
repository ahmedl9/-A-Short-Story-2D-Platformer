package GameMode;

import java.util.*;

public abstract class GameMode {
	
	protected GameModeController c;
	
	public abstract void init();
	public abstract void tick();
	public abstract void draw(java.awt.Graphics g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	

}
