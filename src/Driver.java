/**
 * The Driver class.
 * This class initializes and starts the game.
 */
public class Driver {
    public static void main (String[] args) {
        Game game = new Game();
        GameView gameView = new GameView();
        
        GameController gameController = new GameController(game, gameView);
    }
}