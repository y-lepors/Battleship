package battle;

/**
 * The Ship class allows you to create
 * a boat with all the necessary information.
 * @author LePors
 */
public class Ship {
    private String name;
    private int size;
    private int xOrigin;
    private int yOrigin;
    private int hitNumber;
    private Direction direction;

    /**
     * The builder who initializes the boat's variables.
     * @param name The name of the boat
     * @param size The size of the boat
     */
    public Ship (String name, int size){
        if(name != null && size > 0){
            this.name = name;
            this.size = size;
            this.xOrigin = 0;
            this.yOrigin = 0;
            this.hitNumber = 0;
            this.direction = Direction.HORIZONTAL;
        }
    }

    /**
     * Initialize the ship
     * @param name The ship name
     * @param size The ship size
     * @param xOrigin The x origin
     * @param yOrigin The y origin
     * @param hitNumber The number of hit on the ship
     * @param direction The ship direction
     */
    public Ship(final String name, final int size, final int xOrigin, final int yOrigin, final int hitNumber, final Direction direction) {
        if (name != null && size > 0 && xOrigin >= 0 && yOrigin >= 0 && hitNumber >= 0 && hitNumber <= this.size && (direction == Direction.HORIZONTAL || direction == Direction.VERTICAL)) {
            this.name = name;
            this.size = size;
            this.xOrigin = xOrigin;
            this.yOrigin = yOrigin;
            this.hitNumber = hitNumber;
            this.direction = direction;
        }
        else {
            System.out.println("Ship error : wrong arg");
        }
    }

    /**
     * Add hit to the ship
     */
    public void addHit(){
        this.hitNumber++;
    }

    /**
     * Test if the boat is skunk
     * @return true if the ship is sunk
     */
    public boolean isSunk(){
        boolean ret = false;

        if(this.hitNumber >= this.size){
            ret = true;
        }
        return ret;
    }

    /**
     * Tests whether the (x,y) coordinate box makes
     * part of the ship on the grid
     * @param x The x coordinate
     * @param y The y coordinate
     * @return True if the coordinate box make part of the ship
     */
    public boolean contains(final int x, final int y) {
        boolean ret = false;
        if (this.direction == Direction.HORIZONTAL) {
            if (this.yOrigin == y) {
                for (int i = 0; i < this.size; ++i) {
                    if (this.xOrigin + i == x) {
                        ret = true;
                    }
                }
            }
        }
        else if (this.xOrigin == x) {
            for (int j = 0; j < this.size; ++j) {
                if (this.yOrigin + j == y) {
                    ret = true;
                }
            }
        }
        return ret;
    }


    /**
     * Returns a string listing all the Ship variables
     * and their contents.
     * @return The String with all information
     */
    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", xOrigin=" + xOrigin +
                ", yOrigin=" + yOrigin +
                ", hitNumber=" + hitNumber +
                ", direction=" + direction +
                '}';
    }

    /**
     * Set the direction
     * @param direction The direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Set the x origin
     * @param xOrigin x origin
     */
    public void setXOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    /**
     * Set the y origin
     * @param yOrigin y position
     */
    public void setYOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    /**
     * Get the ship size
     * @return The ship size
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the x origin
     * @return The x origin
     */
    public int getxOrigin() {
        return xOrigin;
    }

    /**
     * Get the y origin
     * @return The y origin
     */
    public int getyOrigin() {
        return yOrigin;
    }

    /**
     * Get the direction
     * @return The ship direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the hit number
     * @return The hit number
     */
    public int getHitNumber() {
        return hitNumber;
    }

    /**
     * Get the name
     * @return the name
     */
    public String getName() {
        return name;
    }
}
