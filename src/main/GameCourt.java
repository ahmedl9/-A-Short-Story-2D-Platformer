package main;
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import GameMode.GameModeController;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."

    public static final int COURT_WIDTH = 900;
    public static final int COURT_HEIGHT = 500;

    public static final int INTERVAL = 35;     // Update interval for timer, in milliseconds - roughly 29 fps

    private GameModeController c; //controller for which gamestate we are in
    

    public GameCourt(JLabel status) {
        c = new GameModeController();
    	
    	setBorder(BorderFactory.createLineBorder(Color.BLACK)); // creates border around the court area, JComponent method
        
        
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        setFocusable(true);// When this component has the keyboard focus, key events are handled by its key listener.

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                c.keyPressed(e.getKeyCode());
            }

            public void keyReleased(KeyEvent e) {
                c.keyReleased(e.getKeyCode());
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */

    //Replace with a save function
    public void begin() {
        //square = new Square(COURT_WIDTH, COURT_HEIGHT, Color.BLACK);

        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            c.tick();
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        c.draw(g);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}