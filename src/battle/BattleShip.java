package battle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * The battleShip class reads the configuration file and
 * initializes the game parameters.
 * Then it creates an instance of the game and launches the game.
 * @author LePors
 */
public class BattleShip {
    private int width;
    private int height;
    private final static String DELIMITER = "\\s*:\\s";
    private Mode mode;
    private ArrayList<Ship> fleet;
    private Game gamePlay;

    /**
     * The constructor that initialize the BattleShip
     * @param fileName The configuration fileName
     * @param playerName1 The first player name
     * @param playerName2 The second player name
     */
    public BattleShip(String fileName, String playerName1, String playerName2){
        fleet = new ArrayList<>();
        this.configure(fileName);
        this.printConfiguration();
        Game newGame = new Game(this.fleet, playerName1, playerName2, this.width, this.height, this.mode);
        newGame.start();
    }

    /**
     * This method is used to initialize the BattleShip
     * She read the information in the configuration file
     * @param fileName The configuration file name
     */
    private void configure(String fileName) {

        ArrayList<String> paramArray = new ArrayList<String>();

        // Read every parameter in the file and put them in an ArrayList.

        Scanner in = null;

        try {
            in = new Scanner(new FileReader("data/" + fileName + ".txt"));
            in.useDelimiter(DELIMITER);

            while (in.hasNext()) {
                paramArray.add(in.next());
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize every variable with the configuration settings

        this.width = parseInt(paramArray.get(0));
        this.height = parseInt(paramArray.get(1));

        //Initialize the mode
        if(paramArray.get(3).trim().equals("HH")){
            this.mode = Mode.HH;
        } else if(paramArray.get(3).trim().equals("HA") || paramArray.get(3).equals("AH")){
            this.mode = Mode.HA;
        } else if(paramArray.get(3).trim().equals("AA")){
            this.mode = Mode.AA;
        }


        // Initialize the fleet
        for(int i = 4 ; i < paramArray.size() ; i+= 2){
            Ship boat = new Ship(paramArray.get(i).trim(), parseInt(paramArray.get(i+1)));
            fleet.add(boat);
        }
    }

    /**
     * Get the game play
     * @return The game
     */
    public Game getGamePlay() {
        return gamePlay;
    }

    /**
     * This method print the game configuration
     */
    public void printConfiguration(){
        System.out.println("Width = "+this.width);
        System.out.println("Height = "+this.height);
        System.out.println("Mode = "+this.mode);
        for(Ship s : fleet){
            System.out.println(s.toString());
        }
    }
}
