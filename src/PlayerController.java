import javax.swing.JPanel;

public class PlayerController {
    private Player player;
    private PlayerView playerView;

    public PlayerController(Player player, PlayerView playerView) {
        this.player = player;
        this.playerView = playerView;

        this.playerView.setTypeLbl(this.player.getFarmerType().getTypeName());
    }

    public JPanel getPanel() {
        return this.playerView.getPanel();
    }
}
