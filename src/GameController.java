import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private final Game game;
    private final GameView gameView;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        // initial state
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                this.updateTile(i, j);
                this.gameView.changeFarmTileListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        JButton farmTileBtn = (JButton) event.getSource();
                        int[] location = (int[]) farmTileBtn.getClientProperty("location");
                        game.getPlayer().plow(location[0], location[1]);
                        updateTile(location[0], location[1]);      
                    }
                }, i, j);
            }
        }

        this.updateGameView();
        this.gameView.updateSouthPanel();
    }

    private void updateGameView() {
        // update player info
        this.gameView.setDay(this.game.getDay());
        this.gameView.setFarmerType(this.game.getPlayer().getFarmerType());
        this.gameView.setObjectCoins(this.game.getPlayer().getObjectCoins());
        this.gameView.setLevel(this.game.getPlayer().getLevel(), this.game.getPlayer().getExperience());
    }

    private void updateTile(int row, int col) {
        Tile tile = this.game.getFarm().getTile(row, col);

        // change to change img
        if(tile.hasRock()) {
            this.gameView.setTileText("rock", row, col);
            // TODO: Add actionListener (Display "Pickaxe" button)
        } else if (tile.getCrop() != null) {
            Crop crop = tile.getCrop();

            this.gameView.setTileText(crop.getSeed().getName(), row, col);
        } else if (tile.isPlowed()) {
            this.gameView.setTileText("plowed", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JButton farmTileBtn = (JButton) event.getSource();
                    int[] location = (int[]) farmTileBtn.getClientProperty("location");
                    game.getPlayer().plant(location[0], location[1], game.getSeed(1));
                    updateTile(location[0], location[1]);      
                }
            }, row, col);
        } else {
            this.gameView.setTileText("unplowed", row, col);
        }
    }
}
