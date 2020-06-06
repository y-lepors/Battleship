package battle;

import java.util.ArrayList;
import view.GridTableFrame;

/**
 * This class allows you to create a Player with his grid and his fleet.
 * @author LePors
 */
public abstract class Player {
    protected String name;
    protected int width;
    protected int height;
    protected ArrayList<Ship> fleet;
    protected Square[][] myGrid;
    protected Square[][] opponentGrid;
    private GridTableFrame myFrame;
    private GridTableFrame enemyFrame;

    /**
     * Initialize every variables and executed createCopy
     * @param fleet The fleet to copy
     * @param name The player's name
     * @param width The player's width
     * @param height The player's height
     */
    public Player(ArrayList<Ship> fleet, String name, int width, int height){
        if(name != null && fleet != null && width > 0 && height > 0){
            this.fleet = new ArrayList<Ship>();
            this.name = name;
            this.width = width;
            this.height = height;
            createCopy(fleet);
            initializeMyGrid();
            initializeOpponentGrid();
            this.myFrame = new GridTableFrame(this.myGrid);
            this.enemyFrame = new GridTableFrame(this.opponentGrid);
        }
    }

    /**
     * Create a copy of the fleet
     * @param fleet The fleet
     */
    protected void createCopy(final ArrayList<Ship> fleet) {
        if (fleet != null) {
            for (final Ship ship : fleet) {
                this.fleet.add(new Ship(ship.getName(), ship.getSize(), ship.getxOrigin(), ship.getyOrigin(), ship.getHitNumber(), ship.getDirection()));
            }
        }
        else {
            System.out.println("createCopy error null arg");
        }
    }

    /**
     * Initialize the first player's grid
     */
    protected void initializeMyGrid(){
        this.myGrid = new Square[this.width][this.height];
        for(int i = 0; i < this.width ; i++){
            for(int j = 0; j < this.height ; j++){
                this.myGrid[i][j] = new Square(i,j);
            }
        }
    }

    /**
     * Initialize the second player's grid
     */
    protected void initializeOpponentGrid(){
        this.opponentGrid = new Square[this.width][this.height];
        for(int i = 0; i < this.width ; i++){
            for(int j = 0; j < this.height ; j++){
                this.opponentGrid[i][j] = new Square(i,j);
            }
        }
    }

    /**
     * This method display my grid
     */
    public void displayMygrid() {
        this.myFrame.setVisible(false);
        this.myFrame.dispose();
        (this.myFrame = new GridTableFrame(this.myGrid)).showIt(" ma grille");
    }

    /**
     * This method display the opponent's grid
     */
    public void displayOpponentGrid() {
        this.enemyFrame.setVisible(false);
        this.enemyFrame.dispose();
        (this.enemyFrame = new GridTableFrame(this.opponentGrid)).showIt( " grille de l'ennemi");
    }


    public abstract int[] newShot();
    public abstract void shipPlacement();

    /**
     * Test if every ship are sunk
     * @return true if all of them are sunk
     */
    public boolean allSunk(){
        boolean ret = true;

        for(Ship p : this.fleet){
            if(!p.isSunk()){
                ret = false;
            }
        }
        return ret;
    }

    /**
     * Ask the fleet if a boat contains the coordinates passed in parameters
     * and if it's true ask if the ship is sunk.
     * @param x The x coordinate
     * @param y The y coordinate
     * @return True if the ship is sunk
     */
    public boolean isSunk(int x, int y){
        boolean ret = false;
        Ship e = null;

        for(Ship p : this.fleet){
            if(p.contains(x,y)){
                ret = true;
                e = p;
            }
        }

        if(ret){
            if(!e.isSunk()){
                ret = false;
            }
        }

        return ret;
    }

    /**
     * Get the name
     * @return The player name
     */
    public String getName() {
        return name;
    }
}
