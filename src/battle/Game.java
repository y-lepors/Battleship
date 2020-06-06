package battle;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class allows you to create and initialize a Game
 * @author LePors
 */
public class Game implements IGame {

    private ShotResult result;
    private ArrayList<Ship> fleet;
    private Player auto;
    private Player captain;
    private Player current;

    /**
     * The constructor which define the Game and initialize the players
     *
     * @param fleet       The fleet consisting of ships
     * @param playerName1 The first player's name
     * @param playerName2 The second player's name
     * @param width       The width
     * @param height      The height
     * @param mode        The mode
     */
    public Game(ArrayList<Ship> fleet, String playerName1, String playerName2, int width, int height, Mode mode) {
        if (mode == Mode.HH) {
            this.captain = new HumanPlayer(fleet, playerName1, width, height);
            this.auto = new HumanPlayer(fleet, playerName2, width, height);
        } else if (mode == Mode.HA) {
            this.captain = new HumanPlayer(fleet, playerName1, width, height);
            this.auto = new AutoPlayer(fleet, playerName2, width, height);
        } else if (mode == Mode.AA) {
            this.captain = new AutoPlayer(fleet, playerName1, width, height);
            this.auto = new AutoPlayer(fleet, playerName2, width, height);
        }
        if (captain != null) {
            this.current = this.captain;
        }
    }


    /**
     * Give a game description
     * @return A string with the game description
     */
    @Override
    public String description() {
        String s = "La bataille navale, appelée aussi touché-coulé, est un jeu de société dans lequel deux joueurs doivent placer des « navires » sur une grille tenue secrète et tenter de « toucher » les navires adverses. Le gagnant est celui qui parvient à couler tous les navires de l'adversaire avant que tous les siens ne le soient. On dit qu'un navire est coulé si chacune de ses cases a été touchées par un coup de l'adversaire.";
        return s;
    }

    /**
     * This method is used to start the game
     */
    @Override
    public void start() {
        JOptionPane.showMessageDialog(null, this.description());
        JOptionPane.showMessageDialog(null, "Placez vos grilles de jeu (5 secondes)");
        this.current.displayMygrid();
        this.current.displayOpponentGrid();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!this.allSunk(this.captain) && !this.allSunk(this.auto)) {
            final ShotResult analyzeShot = this.analyzeShot(this.readShot(this.current));
            if (analyzeShot == ShotResult.MISS) {
                JOptionPane.showMessageDialog(null, "Le tir de " + this.current.name + " n'a rien touché");
            }
            else if (analyzeShot == ShotResult.HIT) {
                JOptionPane.showMessageDialog(null, "Le tir de " + this.current.name + " a touché quelque chose ");
            }
            else {
                JOptionPane.showMessageDialog(null, "Le tir de " + this.current.name + " a coulé un navire");
            }
            this.changeCurrent();
            if (!this.current.getClass().getName().equals("battle.AutoPlayer")) {
                this.current.displayMygrid();
                this.current.displayOpponentGrid();
            }
        }
        this.endOfGame();
    }

    /**
     * Ends the game and announces the winner
     */
    @Override
    public void endOfGame() {
        if (this.captain.allSunk()) {
            JOptionPane.showMessageDialog(null, "Fin de la partie, le gagant est : " + this.auto.getName());
        }
        else {
            JOptionPane.showMessageDialog(null, "Fin de la partie, le gagant est : " + this.captain.getName());
        }
    }

    /**
     * Change de current player
     */
    private void changeCurrent() {
        if (this.current == this.captain) {
            this.current = this.auto;
        } else if (this.current == this.auto) {
            this.current = this.captain;
        }
    }

    /**
     * Read the player shot
     * @param player The player which shot
     * @return The coordinate of the shot
     */
    public int[] readShot(Player player) {
        int[] ret = null;
        if (player != null) {
            ret = player.newShot();
        } else {
            System.err.println("readShot error : null player");
        }
        return ret;
    }

    /**
     * Analyze if the shot is good
     * @param array The tab of coordinate
     * @return The shot result
     */
    public ShotResult analyzeShot(final int[] array) {
        ShotResult shotResult = ShotResult.MISS;
        if (array != null && array.length == 2) {
            Player player;
            if (this.current == this.captain) {
                player = this.auto;
            } else {
                player = this.captain;
            }
            if (player.myGrid[array[1]][array[0]].isFree()) {
                shotResult = ShotResult.MISS;
            } else if (!player.myGrid[array[1]][array[0]].isHit()) {
                shotResult = ShotResult.HIT;
                this.current.opponentGrid[array[1]][array[0]].setHit();
                this.changeCurrent();
                this.current.myGrid[array[1]][array[0]].setHit();
                int n = 0;
                Ship ship = null;
                final Iterator iterator = this.current.fleet.iterator();
                while (n == 0 && iterator.hasNext()) {
                    ship = (Ship) iterator.next();
                    if (ship.getDirection() == Direction.HORIZONTAL) {
                        if (array[0] != ship.getyOrigin() || array[1] < ship.getxOrigin() || array[1] >= ship.getxOrigin() + ship.getSize()) {
                            continue;
                        }
                        n = 1;
                    } else {
                        if (array[1] != ship.getxOrigin() || array[0] < ship.getyOrigin() || array[0] >= ship.getyOrigin() + ship.getSize()) {
                            continue;
                        }
                        n = 1;
                    }
                }
                if (n != 0 && ship != null) {
                    ship.addHit();
                }
                if (this.current.isSunk(array[1], array[0])) {
                    shotResult = ShotResult.SUNK;
                }
                this.changeCurrent();
            }
        } else {
            System.out.println("analyzeShot error : wrong shot");
        }
        return shotResult;
    }

    /**
     * Verify if every ship are sunk
     * @param player The player
     * @return True if all sunk
     */
    public boolean allSunk(final Player player) {
        boolean ret = false;
        if (player != null) {
            ret = player.allSunk();
        }
        else {
            System.out.println("allSunk error : null arg");
        }
        return ret;
    }
}
