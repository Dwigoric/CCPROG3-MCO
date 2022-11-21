import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private final Game game;
    private final GameView gameView;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        this.gameView.setFarmTileListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JButton farmTileBtn = (JButton) event.getSource();
                int[] location = (int[]) farmTileBtn.getClientProperty("location");
                System.out.print("Attempting to plow tile at (" + location[0] + ", " + location[1] + ")... ");
                game.getPlayer().plow(location[0], location[1]);
                System.out.println(game.getFarm().getTile(location[0], location[1]).isPlowed() ? "Success!" : "Failed!");

                gameView.addButton(btnEvent -> {
                    // TODO: Add action listener for the button
                });
            }
        });

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                this.gameView.updateFarmTileListener(i, j);
            }
        }

        this.updateGameView();
        this.gameView.updateSouthPanel();
    }

    private void updateGameView() {
        this.gameView.setDay(this.game.getDay());
        this.gameView.setFarmerType(this.game.getPlayer().getFarmerType());
        this.gameView.setObjectCoins(this.game.getPlayer().getObjectCoins());
        this.gameView.setLevel(this.game.getPlayer().getLevel(), this.game.getPlayer().getExperience());
    }
}
