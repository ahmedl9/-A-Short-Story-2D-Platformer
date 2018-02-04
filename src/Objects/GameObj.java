package Objects;
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Graphics;

import main.Direction;
import Level.Map;
/** 
 * An object in the game. 
 *
 * Game objects exist in the game court. They have a position, velocity, size and bounds. Their
 * velocity controls how they move; their position should always be within their bounds.
 */
public abstract class GameObj {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *  
     * Coordinates are given by the upper-left hand corner of the object. This position should
     * always be within bounds.
     *  0 <= px <= maxX 
     *  0 <= py <= maxY 
     */
    protected int px; 
    protected int py;
    protected int temp1;
    protected int temp2;

    /* Size of object, in pixels. */
    protected int width;
    protected int height;
    protected int cWidth;
    protected int cHeight;
    
    private int cRow;
    private int cCol;
    private double xNext;
    private double yNext;
    private boolean tL;
    private boolean tR;
    private boolean bL;
    private boolean bR;
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean jumping;
    protected double mapx;
    protected double mapy;
    protected Animation animation;
    private Map map;
    private int blockSize;
    protected double moveSpeed;
    protected double maxSpeed;
    protected double fallSpeed;
    protected double jumpStart;
    protected boolean facingRight;
    public int cantalk;



    /* Velocity: number of pixels to move every time move() is called. */
    protected int vx;
    protected int vy;

    /* 
     * Upper bounds of the area in which the object can be positioned. Maximum permissible x, y
     * positions for the upper-left hand corner of the object.
     */
    private int maxX;
    private int maxY;
    
    
    /**
     * Constructor
     */
    public GameObj(int vx, int vy, int px, int py, int width, int height, int courtWidth,
        int courtHeight, Map map) {
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;
        this.map = map;
        blockSize = map.getBlockSize();
        cantalk = 0;

        // take the width and height into account when setting the bounds for the upper left corner
        // of the object.
        this.maxX = courtWidth - width;
        this.maxY = courtHeight - height;
    }

    /*** GETTERS **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVx() {
        return this.vx;
    }
    
    public int getVy() {
        return this.vy;
    }


    /*** SETTERS **********************************************************************************/
    public void setPx(int px) {
        this.px = px;
        clip();
    }

    public void setPy(int py) {
        this.py = py;
        clip();
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    
    public void setMp() {
    	mapx = map.getx();
    	mapy = map.gety();
    }
    
    public void setLeft(boolean b) {
    	left = b;
    }
    public void setRight(boolean b) {
    	right = b;
    }
    public void setUp(boolean b) {
    	up = b;
    }


    /*** UPDATES AND OTHER METHODS ****************************************************************/

    /**
     * Prevents the object from going outside of the bounds of the area designated for the object.
     * (i.e. Object cannot go outside of the active area the user defines for it).
     */ 
    private void clip() {
        this.px = Math.min(Math.max(this.px, 0), this.maxX);
        this.py = Math.min(Math.max(this.py, 0), this.maxY);
    }

    /**
     * Moves the object by its velocity.  Ensures that the object does not go outside its bounds by
     * clipping.
     */
    public void move() {
        this.px += this.vx;
        this.py += this.vy;

        clip();
    }

    /**
     * Determine whether this game object is currently intersecting another object.
     * 
     * Intersection is determined by comparing bounding boxes. If the bounding boxes overlap, then
     * an intersection is considered to occur.
     * 
     * @param that The other object
     * @return Whether this object intersects the other object.
     */
    public boolean intersects(GameObj that) {
        return (this.px + this.width >= that.px
            && this.py + this.height >= that.py
            && that.px + that.width >= this.px 
            && that.py + that.height >= this.py);
    }
    
    public void findRect(double x, double y) {
    	int leftBlock = (int) (x-cWidth / 2 - blockSize) / blockSize;
    	int rightBlock = (int) (x + cWidth / 2 - 1) / blockSize;
    	int topBlock = (int) (y - cHeight / 2) / blockSize;
    	int bottomBlock = (int) (y + cHeight / 2 - blockSize - 1) / blockSize;

    	tL = map.getMap()[topBlock][leftBlock] != 0;
    	tR = map.getMap()[topBlock][rightBlock] != 0;
    	bL = map.getMap()[bottomBlock][leftBlock] != 0;
    	bR = map.getMap()[bottomBlock][rightBlock] != 0;
    	
    	if (map.getMap()[topBlock][leftBlock] == 2 || map.getMap()[topBlock][rightBlock] == 2 || map.getMap()[bottomBlock][leftBlock] == 2 || map.getMap()[bottomBlock][rightBlock] == 2) {
    		cantalk = 2;
    	}
    	else if (map.getMap()[topBlock][leftBlock] == 3 || map.getMap()[topBlock][rightBlock] == 3 || map.getMap()[bottomBlock][leftBlock] == 3 || map.getMap()[bottomBlock][rightBlock] == 3) {
    		cantalk = 3;
    	}
    	else {
    		cantalk = 0;
    	}

    }
    
    public void checkBlockCollision() {
    	cCol = (int) px / blockSize;
    	cRow = (int) py / blockSize; 
    	
    	xNext = px + vx;
    	yNext = py + vy;
    	temp1 = px;
    	temp2 = py;
    	boolean dontgoon = false;
    	    	
    	findRect(px, yNext);
    	
    	if (vy == 0) {
    		yNext = py + 64;
    		findRect(px, yNext);
    		if (!(bR || bL)) {
    			//System.out.println("collision on botton");
    			vy = 3;
    			jumping = true;
    			temp2 = temp2 + vy;
    			dontgoon = true;
    		}
    	}
    	
    	if (vy < 0) {
    		if (tL || tR) {
    			//System.out.println("collision on top");
    			vy = 0;
    			temp2 = cRow * blockSize + cHeight / 2;
    		}
    		else {
    			temp2 = temp2 + vy;
    		}
    	}
    	
    	if (vy > 0 && !dontgoon) {
    		if (bR || bL) {
    			//System.out.println("Collision on bottom");
    			vy = 0;
    			jumping = false;
    			temp2 = (cRow + 1) * blockSize - cHeight / 2;
    		}
    		else {
    			temp2 = temp2 + vy;
    		}
    	}
    	
    	findRect(xNext, py);

    	if (vx < 0) {
    		if (tL || bL) {
    			vx = 0;
    			temp1 = cCol * blockSize + cWidth / 2;
    			//System.out.println("Collision on left");
    		}
    		else {
    			temp1 = temp1 + vx;
    		}
    	}
    	if (vx > 0) {
    		if (tR || bR) {
    			vx = 0;
    			temp1 = (cCol + 1) * blockSize - cWidth / 2;
    			//System.out.println("Collision on right");
    		}
    		else {
    			temp1 = temp1 + vx;
    		}
    	}
    }


    /**
     * Determine whether this game object will intersect another in the next time step, assuming
     * that both objects continue with their current velocity.
     * 
     * Intersection is determined by comparing bounding boxes. If the  bounding boxes (for the next
     * time step) overlap, then an intersection is considered to occur.
     * 
     * @param that The other object
     * @return Whether an intersection will occur.
     */
    public boolean willIntersect(GameObj that) {
        int thisNextX = this.px + this.vx;
        int thisNextY = this.py + this.vy;
        int thatNextX = that.px + that.vx;
        int thatNextY = that.py + that.vy;
    
        return (thisNextX + this.width >= thatNextX
            && thisNextY + this.height >= thatNextY
            && thatNextX + that.width >= thisNextX 
            && thatNextY + that.height >= thisNextY);
    }


    /**
     * Update the velocity of the object in response to hitting an obstacle in the given direction.
     * If the direction is null, this method has no effect on the object.
     *
     * @param d The direction in which this object hit an obstacle
     */
    public void bounce(Direction d) {
        if (d == null) return;
        
        switch (d) {
        case UP:
            this.vy = Math.abs(this.vy);
            break;  
        case DOWN:
            this.vy = -Math.abs(this.vy);
            break;
        case LEFT:
            this.vx = Math.abs(this.vx);
            break;
        case RIGHT:
            this.vx = -Math.abs(this.vx);
            break;
        }
    }

    /**
     * Determine whether the game object will hit a wall in the next time step. If so, return the
     * direction of the wall in relation to this game object.
     *  
     * @return Direction of impending wall, null if all clear.
     */
    public Direction hitWall() {
        if (this.px + this.vx < 0) {
            return Direction.LEFT;
        } else if (this.px + this.vx > this.maxX) {
           return Direction.RIGHT;
        }

        if (this.py + this.vy < 0) {
            return Direction.UP;
        } else if (this.py + this.vy > this.maxY) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }

    /**
     * Determine whether the game object will hit another object in the next time step. If so,
     * return the direction of the other object in relation to this game object.
     * 
     * @param that The other object
     * @return Direction of impending object, null if all clear.
     */
    public Direction hitObj(GameObj that) {
        if (this.willIntersect(that)) {
            double dx = that.px + that.width / 2 - (this.px + this.width / 2);
            double dy = that.py + that.height / 2 - (this.py + this.height / 2);

            double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy *dy)));
            double diagTheta = Math.atan2(this.height / 2, this.width / 2);

            if (theta <= diagTheta) {
                return Direction.RIGHT;
            } else if (theta > diagTheta && theta <= Math.PI - diagTheta) {
                // Coordinate system for GUIs is switched
                if (dy > 0) {
                    return Direction.DOWN;
                } else {
                    return Direction.UP;
                }
            } else {
                return Direction.LEFT;
            }
        } else {
            return null;
        }
    }

    /**
     * Default draw method that provides how the object should be drawn in the GUI. This method does
     * not draw anything. Subclass should override this method based on how their object should
     * appear.
     * 
     * @param g The <code>Graphics</code> context used for drawing the object. Remember graphics
     * contexts that we used in OCaml, it gives the context in which the object should be drawn (a
     * canvas, a frame, etc.)
     */
    public abstract void draw(Graphics g);
}