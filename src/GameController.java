import java.awt.event.ActionListener;

/**
 * The GameController class.
 */
public class GameController {
    /**
     * The game model.
     */
    private final Game game;
    /**
     * The game view.
     */
    private final GameView gameView;

    /**
     * The coordinates of the selected tile in the farm.
     */
    private final int[] currTileSelected = {-1, -1};

    private final ActionListener gameRestartListener;

    /**
     * Creates a new GameController.
     * @param game      The game.
     * @param gameView  The game view.
     */
    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        this.gameRestartListener = event -> {
            game.resetGame();
            gameView.resetView();

            updatePlayerInfo();
            updateAllFarmTiles();
            gameView.updateBottomPanel();
        };

        // Misc listeners
        ActionListener sleepListener = event -> {
            gameView.resetBottomPanel();

            checkCoinsCondition();

            game.advanceDay();

            if (game.getFarm().isAllWithered()) {
                gameView.showEndGame("All of your crops have withered!", gameRestartListener);

                return;
            }

            gameView.updateBottomPanel();

            updatePlayerInfo();
            updateAllFarmTiles();
        };

        ActionListener upgradeFarmerListener = event -> {
            game.getPlayer().upgradeFarmer(game.getFarmerTypeList());
            updatePlayerInfo();
        };

        ActionListener bookListener = event -> gameView.showBook();

        this.gameView.initializeMiscListener(sleepListener, upgradeFarmerListener, bookListener);
        this.updateUpgradeButton();
        this.updatePlayerInfo();
        this.updateAllFarmTiles();
    }

    /**
     * Updates the player info in the game view.
     */
    private void updatePlayerInfo() {
        Player player = game.getPlayer();

        gameView.updateTopPanel(player.getLevel(), player.getExperience(), player.getObjectCoins(), 
            game.getFarmerTypeList(), player.getFarmerTypeLvl(), game.getDay());

        gameView.setUpgradeFarmerButtonEnabled(game.getPlayer().canUpgradeFarmer(game.getFarmerTypeList()));
    }

    /**
     * Updates the state of the upgrade farmer button.
     */
    private void updateUpgradeButton() {
        gameView.setUpgradeFarmerButtonEnabled(game.getPlayer().canUpgradeFarmer(game.getFarmerTypeList()));
    }

    /**
     * Updates the player information panel and the bottom panel.
     */
    private void updateTopBottomPanels() {
        this.updatePlayerInfo();
        gameView.updateBottomPanel();
    }

    /**
     * Updates all farm tiles in the game view.
     */
    private void updateAllFarmTiles() {
        Farm farm = this.game.getFarm();

        for (int i = 0; i < farm.getRows(); i++) {
            for (int j = 0; j < farm.getColumns(); j++) {
                this.updateTile(i, j);
            }
        }
    }

    private void checkCoinsCondition() {
        if (game.getPlayer().getObjectCoins() < 5 - game.getPlayer().getFarmerType().seedCostReduction() &&
                !game.getFarm().hasCrop()) {
            gameView.showEndGame("You have no more crops and no more money to buy more!", this.gameRestartListener);
        }
    }

    /**
     * Updates a farm tile in the game view.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    private void updateTile(int row, int column) {
        Tile tile = this.game.getFarm().getTile(row, column);
        ActionListener shovelButtonAL = event -> {
            gameView.resetBottomPanel();

            game.getPlayer().shovel(row, column);

            updateUpgradeButton();
            updateTileSelection(row, column);
            updateTopBottomPanels();

            checkCoinsCondition();
        };

        if(tile.hasRock()) { /* Tile has rock; show pickaxe (if player has enough objectCoins) and shovel */
            this.gameView.setTileIcon("rock", row, column);
            this.gameView.changeFarmTileListener(event -> {
                gameView.resetBottomPanel();

                if (game.getPlayer().getObjectCoins() >= 50) {
                    gameView.addActionButton(event1 -> {
                        gameView.resetBottomPanel();

                        game.getPlayer().pickaxe(row, column);

                        updateUpgradeButton();
                        updateTileSelection(row, column);
                        updateTopBottomPanels();

                        checkCoinsCondition();
                    }, "pickaxe");
                }

                if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                updateTileSelection(row, column);
                updateTopBottomPanels();
            }, row, column);

        } else if (tile.getCrop() != null) { /* Tile has crop; show water, fertilize, shovel (if player has enough objectCoins), harvest (if ready) */
            Crop crop = tile.getCrop();

            if (crop.isAlive()) {
                gameView.setTileIcon(crop.getSeed().name(), row, column);
                this.gameView.changeFarmTileListener(event -> {
                    gameView.resetBottomPanel();

                    Tile farmTile = game.getFarm().getTile(row, column);
                    if (farmTile.getCrop().getAge() != farmTile.getCrop().getSeed().harvestTime()) {
                        gameView.addActionButton(event12 -> {
                            game.getPlayer().water(row, column);

                            updateUpgradeButton();
                            updateTileSelection(row, column);
                            updatePlayerInfo();
                        }, "water");
                    }

                    if (game.getPlayer().getObjectCoins() >= 10 &&
                            farmTile.getCrop().getAge() != farmTile.getCrop().getSeed().harvestTime()) {
                        gameView.addActionButton(event13 -> {
                            game.getPlayer().fertilize(row, column);

                            updateUpgradeButton();
                            updateTileSelection(row, column);
                            updatePlayerInfo();

                            checkCoinsCondition();
                        }, "fertilizer");
                    }

                    if (crop.isHarvestReady()) {
                        gameView.addActionButton(event14 -> {
                            Crop crop1 = game.getFarm().getTile(row, column).getCrop();
                            gameView.resetBottomPanel();

                            gameView.updateFeedbackPanel(game.getPlayer().harvest(row, column), crop1);

                            updateUpgradeButton();
                            updateTileSelection(row, column);
                            updateTopBottomPanels();
                        }, "harvest");
                    }

                    if (game.getPlayer().getObjectCoins() >= 7) {
                        gameView.addActionButton(shovelButtonAL, "shovel");
                    }

                    updateTileSelection(row, column);
                    updateTopBottomPanels();
                }, row, column);
            } else { /* Crop is withered; show shovel (if player has enough objectCoins) */
                gameView.setTileIcon("withered", row, column);
            }

        } else if (tile.isPlowed()) { /* Tile is plowed; show all possible seed that can be planted */
            gameView.setTileIcon("plowed", row, column);
            this.gameView.changeFarmTileListener(event -> {
                Player player = game.getPlayer();
                int seedCostReduction = player.getFarmerType().seedCostReduction();
                gameView.resetBottomPanel();

                /* Add plant action to seeds that can be bought with the current objectcoins only */
                if (player.getObjectCoins() >= 5 - seedCostReduction) {
                    // Turnip
                    gameView.addActionButton(event15 -> {
                        gameView.resetBottomPanel();

                        game.getPlayer().plant(row, column, game.getSeed(0));

                        updateUpgradeButton();
                        updateTileSelection(row, column);
                        updateTopBottomPanels();
                    }, game.getSeed(0).name());

                    // Rose
                    gameView.addActionButton(event16 -> {
                        gameView.resetBottomPanel();

                        game.getPlayer().plant(row, column, game.getSeed(3));

                        updateUpgradeButton();
                        updateTileSelection(row, column);
                        updateTopBottomPanels();
                    }, game.getSeed(3).name());
                }

                if (player.getObjectCoins() >= 10 - seedCostReduction) {
                    // Carrot
                    gameView.addActionButton(event17 -> {
                        gameView.resetBottomPanel();

                        game.getPlayer().plant(row, column, game.getSeed(1));

                        updateUpgradeButton();
                        updateTileSelection(row, column);
                        updateTopBottomPanels();
                    }, game.getSeed(1).name());

                    // Tulips
                    gameView.addActionButton(event18 -> {
                        gameView.resetBottomPanel();

                        game.getPlayer().plant(row, column, game.getSeed(4));

                        updateUpgradeButton();
                        updateTileSelection(row, column);
                        updateTopBottomPanels();
                    }, game.getSeed(4).name());

                    if (game.getPlayer().getObjectCoins() >= 20 - seedCostReduction) {
                        // Potato
                        gameView.addActionButton(event19 -> {
                            gameView.resetBottomPanel();

                            game.getPlayer().plant(row, column, game.getSeed(2));

                            updateUpgradeButton();
                            updateTileSelection(row, column);
                            updateTopBottomPanels();
                        }, game.getSeed(2).name());

                        // Sunflower
                        gameView.addActionButton(event110 -> {
                            gameView.resetBottomPanel();

                            game.getPlayer().plant(row, column, game.getSeed(5));

                            updateUpgradeButton();
                            updateTileSelection(row, column);
                            updateTopBottomPanels();
                        }, game.getSeed(5).name());

                        if (game.getPlayer().getObjectCoins() >= 100 - seedCostReduction &&
                                game.getFarm().canPlantTree(row, column))
                        {
                            // Mango tree
                            gameView.addActionButton(event111 -> {
                                gameView.resetBottomPanel();

                                game.getPlayer().plant(row, column, game.getSeed(6));

                                updateUpgradeButton();
                                updateTileSelection(row, column);
                                updateTopBottomPanels();
                            }, game.getSeed(6).name());

                            if (game.getPlayer().getObjectCoins() >= 200 - seedCostReduction &&
                                    game.getFarm().canPlantTree(row, column))
                            {
                                // Apple tree
                                gameView.addActionButton(event112 -> {
                                    gameView.resetBottomPanel();

                                    game.getPlayer().plant(row, column, game.getSeed(7));

                                    updateUpgradeButton();
                                    updateTileSelection(row, column);
                                    updateTopBottomPanels();
                                }, game.getSeed(7).name());
                            }
                        }
                    }
                }

                if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                updateTileSelection(row, column);
                updateTopBottomPanels();
            }, row, column);

        } else { /* Tile is unplowed; show plow, shovel (if player has enough objectCoins) */
            gameView.setTileIcon("unplowed", row, column);
            this.gameView.changeFarmTileListener(event -> {
                gameView.resetBottomPanel();

                gameView.addActionButton(event113 -> {
                    gameView.resetBottomPanel();
                    game.getPlayer().plow(row, column);

                    updateUpgradeButton();
                    updateTileSelection(row, column);
                    updateTopBottomPanels();
                }, "plow");

                if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                updateTileSelection(row, column);
                updateTopBottomPanels();
            }, row, column);
        }
    }

    /**
     * Updates the selection status of the tile at the given row and column.
     * @param row       The row of the tile.
     * @param column    The column of the tile.
     */
    private void updateTileSelection(int row, int column) {
        /* updates GUI */
        gameView.setIsSelected(row, column);

        if (currTileSelected[0] != -1 && currTileSelected[1] != -1) {
            updateTile(currTileSelected[0], currTileSelected[1]);
        }

        updateTile(row, column);
        
        /* stores current selected tile */
        currTileSelected[0] = row;
        currTileSelected[1] = column;
    }
}
