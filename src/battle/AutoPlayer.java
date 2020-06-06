package battle;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * This class allows you to create a auto player
 * @author LePors
 */
public class AutoPlayer extends Player {

    private final static String DELIMITER = "\\s*:\\s";

    /**
     * Initialize every variables
     *
     * @param fleet  The fleet to copy
     * @param name   The player's name
     * @param width  The player's width
     * @param height The player's height
     */
    public AutoPlayer(ArrayList<Ship> fleet, String name, int width, int height) {
        super(fleet, name, width, height);
        this.shipPlacement();
    }

    /**
     * Ask the player to give shooting coordinates
     *
     * @return A table with the two coordinates
     */
    @Override
    public int[] newShot() {
        final int[] ret = new int[2];
        JOptionPane.showMessageDialog(null, "Le bot " + this.name + " joue");
        final Random random = new Random();
        ret[1] = random.nextInt(this.width);
        ret[0] = random.nextInt(this.height);
        return ret;
    }

    /**
     * Do the ship placement
     */
    @Override
    public void shipPlacement() {
        readConfiguration();
        System.out.println(this.fleet.get(0).getSize());

        for (Ship p : this.fleet) {
            if (shipCaseVerification(p)) {

                for (int i = 0; i < p.getSize(); i++) {
                    if (p.getDirection() == Direction.HORIZONTAL) {
                        super.myGrid[p.getxOrigin() + i][p.getyOrigin()].setBusy();

                    } else if (p.getDirection() == Direction.VERTICAL) {
                        super.myGrid[p.getxOrigin()][p.getyOrigin() + i].setBusy();
                    }
                }
            } else {
                throw new IllegalArgumentException("Veuillez refaire le fichier de placement !");
            }
        }
    }

    /**
     * Read a file to place every ship
     */
    private void readConfiguration() {
        ArrayList<String> paramArray = new ArrayList<String>();

        // Read every parameter in the file and put them in an ArrayList.

        Scanner in = null;

        try {
            in = new Scanner(new FileReader("data/placementIA.txt"));
            in.useDelimiter(DELIMITER);

            while (in.hasNext()) {
                paramArray.add(in.next());
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        int i = 0;
        for (Ship p : this.fleet) {

            p.setXOrigin(parseInt(paramArray.get(i + 1).trim()));
            p.setYOrigin(parseInt(paramArray.get(i + 2).trim()));
            if (paramArray.get(i + 3).trim().equals("HORIZONTAL")) {
                p.setDirection(Direction.HORIZONTAL);
            } else if (paramArray.get(i + 3).trim().equals("VERTICAL")) {
                p.setDirection(Direction.VERTICAL);
            }
            i += 4;
        }
    }

    /**
     * Verif if every case are free around the ship
     *
     * @param s The ship
     * @return True if the ship can be placed
     */
    public boolean shipCaseVerification(Ship s) {
        boolean ret = true;

        // Teste si la case est disponible
        if (!super.myGrid[s.getxOrigin()][s.getyOrigin()].isFree()) {
            ret = false;
        }

        // HORIZONTAL
        if (s.getDirection() == Direction.HORIZONTAL) {
            if (s.getxOrigin() != 0) {
                if (!super.myGrid[s.getxOrigin() - 1][s.getyOrigin()].isFree()) {
                    ret = false;
                }
            }

            // Boucle pour les autres case
            for (int i = 0; i < s.getSize(); i++) {
                if (s.getxOrigin() + i != width && s.getyOrigin() != height && s.getxOrigin() + i != 0 && s.getyOrigin() != 0) {
                    if (!myGrid[s.getxOrigin() + i - 1][s.getyOrigin() + 1].isFree()
                            || !myGrid[s.getxOrigin() + i][s.getyOrigin() + 1].isFree() || !myGrid[s.getxOrigin() + i - 1][s.getyOrigin() - 1].isFree()
                            || !myGrid[s.getxOrigin() + i][s.getyOrigin() - 1].isFree() || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin() + 1].isFree()
                            || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin() - 1].isFree() || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin()].isFree()) {
                        ret = false;
                    }
                } else if (s.getxOrigin() + i == width) {
                    if (!myGrid[s.getxOrigin() + i - 1][s.getyOrigin() + 1].isFree() // Haut gauche
                            || !myGrid[s.getxOrigin() + i][s.getyOrigin() + 1].isFree() // Haut
                            || !myGrid[s.getxOrigin() + i - 1][s.getyOrigin() - 1].isFree() // Bas gauche
                            || !myGrid[s.getxOrigin() + i][s.getyOrigin() - 1].isFree()  // Bas
                    ) {
                        ret = false;
                    }
                } else if (s.getyOrigin() + i == height) {
                    if (!myGrid[s.getxOrigin() + i - 1][s.getyOrigin() + 1].isFree() // Haut gauche
                            || !myGrid[s.getxOrigin() + i][s.getyOrigin() + 1].isFree() // Haut
                            || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin() + 1].isFree() // Haut droite
                            || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin()].isFree()) { // Droite
                        ret = false;
                    }
                } else if (s.getxOrigin() + i == 0) {
                    if (
                            !myGrid[s.getxOrigin() + i][s.getyOrigin() + 1].isFree() // Haut
                                    || !myGrid[s.getxOrigin() + i][s.getyOrigin() - 1].isFree()  // Bas
                                    || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin() + 1].isFree() // Haut droite
                                    || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin() - 1].isFree() // Bas droite
                                    || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin()].isFree()) { // Droite
                        ret = false;
                    }
                } else if (s.getyOrigin() + i == 0) {
                    if (
                            !myGrid[s.getxOrigin() + i - 1][s.getyOrigin() - 1].isFree() // Bas gauche
                                    || !myGrid[s.getxOrigin() + i][s.getyOrigin() - 1].isFree()  // Bas
                                    || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin() - 1].isFree() // Bas droite
                                    || !myGrid[s.getxOrigin() + i + 1][s.getyOrigin()].isFree()) { // Droite
                        ret = false;
                    }
                }
            }

            // VERTICALE
            if (s.getDirection() == Direction.VERTICAL) {
                if (s.getxOrigin() != 0) {
                    if (!super.myGrid[s.getxOrigin()][s.getyOrigin() - 1].isFree()) {
                        ret = false;
                    }
                }

                // Boucle pour les autres case
                for (int i = 0; i < s.getSize(); i++) {
                    if (s.getxOrigin() != width && s.getyOrigin() + i != height && s.getxOrigin() != 0 && s.getyOrigin() + i != 0) {
                        if (!myGrid[s.getxOrigin() - 1][s.getyOrigin() + i + 1].isFree()
                                || !myGrid[s.getxOrigin()][s.getyOrigin() + i + 1].isFree() || !myGrid[s.getxOrigin() - 1][s.getyOrigin() + i - 1].isFree()
                                || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i + 1].isFree()
                                || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i - 1].isFree() || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i].isFree()
                                || !myGrid[s.getxOrigin() - 1][s.getyOrigin() + i].isFree()) {
                            ret = false;
                        }
                    } else if (s.getxOrigin() + i == width) {
                        if (!myGrid[s.getxOrigin() - 1][s.getyOrigin() + i + 1].isFree() // Haut gauche
                                || !myGrid[s.getxOrigin()][s.getyOrigin() + i + 1].isFree() // Haut
                                || !myGrid[s.getxOrigin() - 1][s.getyOrigin() + i - 1].isFree() // Bas gauche
                                || !myGrid[s.getxOrigin() - 1][s.getyOrigin() + i].isFree()
                        ) {
                            ret = false;
                        }
                    } else if (s.getyOrigin() + i == height) {
                        if (!myGrid[s.getxOrigin() - 1][s.getyOrigin() + i + 1].isFree() // Haut gauche
                                || !myGrid[s.getxOrigin()][s.getyOrigin() + i + 1].isFree() // Haut
                                || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i + 1].isFree() // Haut droite
                                || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i].isFree() // Droite
                                || !myGrid[s.getxOrigin() - 1][s.getyOrigin() + i].isFree()) {

                            ret = false;
                        }
                    } else if (s.getxOrigin() + i == 0) {
                        if (
                                !myGrid[s.getxOrigin() + i][s.getyOrigin() + i + 1].isFree() // Haut
                                        || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i + 1].isFree() // Haut droite
                                        || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i - 1].isFree() // Bas droite
                                        || !myGrid[s.getxOrigin() - 1][s.getyOrigin() + i].isFree()
                                        || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i].isFree()) { // Droite
                            ret = false;
                        }
                    } else if (s.getyOrigin() + i == 0) {
                        if (!myGrid[s.getxOrigin() - 1][s.getyOrigin() - 1 + i].isFree() // Bas gauche
                                || !myGrid[s.getxOrigin() + 1][s.getyOrigin() - 1 + i].isFree() // Bas droite
                                || !myGrid[s.getxOrigin() + 1][s.getyOrigin() + i].isFree() // Droite
                                || !myGrid[s.getxOrigin() - 1][s.getyOrigin() + i].isFree()) {
                            ret = false;
                        }
                    }
                }
            }
        }
        return ret;
    }
}
