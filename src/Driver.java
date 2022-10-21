public class Driver {
    public static void main (String[] args) {
        Game game = new Game();

        game.initialize(1, 1); // Farm is 1x1 Tiles in MCO 1
        game.start();
    }
}