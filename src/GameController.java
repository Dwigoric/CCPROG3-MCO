public class GameController {
    private Game game;
    private GameView gameView;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        this.gameView.addPanel(this.game.getPlayerController().getPanel());
        this.gameView.update();
    }
}
