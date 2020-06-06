package battle;

/**
 * A class that allows you to create a Square
 * @author LePors
 */
public class Square {
    private int x;
    private int y;
    private boolean free;
    private boolean hit;

    /**
     * The constructor that initialize the Square
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Square(int x, int y){
        if(x >= 0 && y >= 0){
            this.x = x;
            this.y = y;
            this.free = true;
            this.hit = false;
        }
    }

    /**
     * Set the variable free to false
     */
    public void setBusy(){
        this.free= false;
    }

    /**
     * Set the hit to true
     */
    public void setHit(){
        this.hit = true;
    }

    /**
     * Get the X coordinate
     * @return The x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y coordinate
     * @return The y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Ask if the square is free
     * @return true if the square is free
     */
    public boolean isFree() {
        return this.free;
    }

    /**
     * Ask if the square is touched
     * @return true if is hit
     */
    public boolean isHit() {
        return this.hit;
    }

    /**
     * Give a description of the square
     * @return A String
     */
    @Override
    public String toString() {
        return "Square{" +
                "x=" + x +
                ", y=" + y +
                ", free=" + free +
                ", hit=" + hit +
                '}';
    }
}
