import java.util.Arrays;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

import java.util.ArrayList;

/**
 * The Driver class.
 * This class initializes and starts the game.
 */
public class Driver {
    public static void main (String[] args) throws FileNotFoundException {
        // Start reading rocks from input file
        Scanner rocksFile = new Scanner(new FileReader("rocks.txt"));
        ArrayList<Integer[]> rocks = new ArrayList<>();
        while (rocksFile.hasNext()) {
            String[] rock = rocksFile.next().split(",");
            rocks.add(new Integer[] { Integer.parseInt(rock[0]), Integer.parseInt(rock[1]) });
        }

        // Initialize the game
        Game game = new Game(rocks);
        GameView gameView = new GameView();
        GameController gameController = new GameController(game, gameView);
    }
}