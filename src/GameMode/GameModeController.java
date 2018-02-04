package GameMode;

import java.util.*;
import GameMode.Menu;


public class GameModeController {
	private ArrayList<GameMode> allModes;
	private int currentMode;

	public GameModeController() {
		this.allModes = new ArrayList<GameMode>();
		this.currentMode = 0;
		allModes.add(new Menu(this));
		allModes.add(new World(this));
		allModes.add(new Instructions(this));
	}
	
	public void setMode(int mode) {
		currentMode = mode;
		allModes.get(currentMode).init();
	}
	
	public int getMode() {
		return currentMode;
	}
	
	
	public void tick() {
		allModes.get(currentMode).tick();

	}
	
	public void draw(java.awt.Graphics g) {
		allModes.get(currentMode).draw(g);
	}
	
	public void keyPressed(int k) {
		allModes.get(currentMode).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		allModes.get(currentMode).keyReleased(k);
	}

}
