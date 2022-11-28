import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private final Game game;
    private final GameView gameView;

    // Misc listeners
    private final ActionListener sleepListener;
    private final ActionListener upgradeFarmerListener;
    private final ActionListener bookListener;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        ActionListener gameRestartListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.reset();
                gameView.reset();

                gameView.updateUpgradeListener(upgradeFarmerListener);
                updateAllFarmTiles();
                updateNorthPanel();
                gameView.updateBottomPanel();
            }
        };

        this.sleepListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getPlayer().getObjectCoins() < 5 - game.getPlayer().getFarmerType().getSeedCostReduction() &&
                        !game.getFarm().hasCrop())
                {
                    gameView.endGame("You have no more crops and no more money to buy more!", gameRestartListener);

                    return;
                }

                game.advanceDay();

                if (game.getFarm().isAllWithered()) {
                    gameView.endGame("All of your crops have withered!", gameRestartListener);

                    return;
                }

                gameView.resetActionPanel();
                gameView.updateBottomPanel();
                
                updateNorthPanel();
                updateAllFarmTiles();
            }
        };

        this.upgradeFarmerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getPlayer().upgradeFarmer(game.getFarmerTypeList());
                updateNorthPanel();
            }
        };
        
        this.bookListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.showBook();
            }
        };

        this.gameView.initializeMiscListener(this.sleepListener, this.upgradeFarmerListener, this.bookListener);
        this.updateAllFarmTiles();
        this.updateNorthPanel();
    }

    private void updateNorthPanel() {
        Player player = game.getPlayer();

        gameView.updatePlayerInfo(player.getLevel(), player.getExperience(), player.getObjectCoins(), 
            game.getFarmerTypeList(), player.getFarmerTypeLvl(), game.getDay());

        gameView.setUpgradeFarmerButtonEnabled(game.getPlayer().canUpgradeFarmer(game.getFarmerTypeList()));
    }

    private void updateAllFarmTiles() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                this.updateTile(i, j);
            }
        }
    }

    private void updateTile(int row, int col) {
        Tile tile = this.game.getFarm().getTile(row, col);
        ActionListener shovelButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.getPlayer().shovel(row, col);

                gameView.resetActionPanel();
                gameView.setIsSelected(row, col);
                updateAllFarmTiles();
                updateNorthPanel();
                gameView.updateBottomPanel();
            }
        };

        if(tile.hasRock()) { // Tile has rock; show pickaxe
            this.gameView.setTileIcon("rock", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    gameView.resetActionPanel();

                    if (game.getPlayer().getObjectCoins() >= 50) {
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().pickaxe(row, col);

                                gameView.resetActionPanel();
                                gameView.setIsSelected(row, col);
                                updateAllFarmTiles();
                                updateNorthPanel();
                                gameView.updateBottomPanel();
                            }
                        }, "pickaxe");
                    }

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    gameView.updateBottomPanel();
                    gameView.setIsSelected(row, col);
                    updateAllFarmTiles();
                    updateNorthPanel();
                }
            }, row, col);

        } else if (tile.getCrop() != null) { // Tile has crop; show water, fertilize, shovel, harvest*
            Crop crop = tile.getCrop();

            if (crop.isAlive()) {
                gameView.setTileIcon(crop.getSeed().getName(), row, col);
                this.gameView.changeFarmTileListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        gameView.resetActionPanel();
                        gameView.resetSouthPanel();

                        Tile farmTile = game.getFarm().getTile(row, col);
                        if (farmTile.getCrop().getAge() != farmTile.getCrop().getSeed().getHarvestTime()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().water(row, col);

                                    gameView.setIsSelected(row, col);
                                    updateAllFarmTiles();
                                    updateNorthPanel();
                                }
                            }, "water");
                        }
    
                        if (game.getPlayer().getObjectCoins() >= 10 &&
                                farmTile.getCrop().getAge() != farmTile.getCrop().getSeed().getHarvestTime()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().fertilize(row, col);

                                    gameView.setIsSelected(row, col);
                                    updateAllFarmTiles();
                                    updateNorthPanel();
                                }
                            }, "fertilizer");
                        }
    
                        if (crop.isHarvestReady()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    Crop crop = game.getFarm().getTile(row, col).getCrop();
                                    gameView.showHarvestResultsPanel(game.getPlayer().harvest(row, col), crop);

                                    gameView.resetActionPanel();
                                    gameView.setIsSelected(row, col);
                                    updateAllFarmTiles();
                                    updateNorthPanel();
                                    gameView.updateBottomPanel();
                                }
                            }, "harvest");
                        }

                        if (game.getPlayer().getObjectCoins() >= 7) {
                            gameView.addActionButton(shovelButtonAL, "shovel");
                        }

                        gameView.updateBottomPanel();
                        gameView.setIsSelected(row, col);
                        updateAllFarmTiles();
                        updateNorthPanel();
                    }
                }, row, col);
            } else { // Crop is withered; show shovel
                gameView.setTileIcon("withered", row, col);
            }

        } else if (tile.isPlowed()) { // Tile is plowed; show all possible seed that can be planted
            gameView.setTileIcon("plowed", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    Player player = game.getPlayer();
                    int seedCostReduction = player.getFarmerType().getSeedCostReduction();
                    gameView.resetActionPanel();
                    gameView.resetSouthPanel();
                    // Add Plant action to seeds that can be bought with the current objectcoins only
                    if (player.getObjectCoins() >= 5 - seedCostReduction) {
                        // Turnip
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(0));
                                gameView.setIsSelected(row, col);
                                updateAllFarmTiles();

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.resetSouthPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(0).getName());

                        // Rose
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(3));
                                gameView.setIsSelected(row, col);
                                updateAllFarmTiles();

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.resetSouthPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(3).getName());
                    }

                    if (player.getObjectCoins() >= 10 - seedCostReduction) {
                        // Carrot
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(1));
                                gameView.setIsSelected(row, col);
                                updateAllFarmTiles();

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.resetSouthPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(1).getName());

                        // Tulips
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(4));
                                gameView.setIsSelected(row, col);
                                updateAllFarmTiles();

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.resetSouthPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(4).getName());

                        if (game.getPlayer().getObjectCoins() >= 20 - seedCostReduction) {
                            // Potato
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().plant(row, col, game.getSeed(2));
                                    gameView.setIsSelected(row, col);
                                    updateAllFarmTiles();

                                    updateNorthPanel();
                                    gameView.resetActionPanel();
                                    gameView.resetSouthPanel();
                                    gameView.updateBottomPanel();
                                }
                            }, game.getSeed(2).getName());

                            // Sunflower
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().plant(row, col, game.getSeed(5));
                                    gameView.setIsSelected(row, col);
                                    updateAllFarmTiles();

                                    updateNorthPanel();
                                    gameView.resetActionPanel();
                                    gameView.resetSouthPanel();
                                    gameView.updateBottomPanel();
                                }
                            }, game.getSeed(5).getName());

                            if (game.getPlayer().getObjectCoins() >= 100 - seedCostReduction) {
                                // Mango tree
                                if (game.getFarm().canPlantTree(row, col)) gameView.addActionButton(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        game.getPlayer().plant(row, col, game.getSeed(6));
                                        gameView.setIsSelected(row, col);
                                        updateAllFarmTiles();

                                        updateNorthPanel();
                                        gameView.resetActionPanel();
                                        gameView.resetSouthPanel();
                                        gameView.updateBottomPanel();
                                    }
                                }, game.getSeed(6).getName());

                                if (game.getPlayer().getObjectCoins() >= 200 - seedCostReduction) {
                                    // Apple tree
                                    if (game.getFarm().canPlantTree(row, col)) gameView.addActionButton(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent event) {
                                            game.getPlayer().plant(row, col, game.getSeed(7));
                                            gameView.setIsSelected(row, col);
                                            updateAllFarmTiles();

                                            updateNorthPanel();
                                            gameView.resetActionPanel();
                                            gameView.resetSouthPanel();
                                            gameView.updateBottomPanel();
                                        }
                                    }, game.getSeed(7).getName());
                                }
                            }
                        }
                    }

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    gameView.setIsSelected(row, col);
                    updateAllFarmTiles();
                    updateNorthPanel();
                    gameView.updateBottomPanel();
                }
            }, row, col);

        } else { // Tile is unplowed; show plow, shovel
            gameView.setTileIcon("unplowed", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    gameView.resetActionPanel();
                    gameView.resetSouthPanel();

                    gameView.addActionButton(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            game.getPlayer().plow(row, col);
                            gameView.setIsSelected(row, col);
                            updateAllFarmTiles();

                            gameView.resetActionPanel();
                            gameView.resetSouthPanel();
                            updateNorthPanel();
                            gameView.updateBottomPanel();
                        }
                    }, "plow");

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    gameView.setIsSelected(row, col);
                    updateAllFarmTiles();
                    updateNorthPanel();
                    gameView.updateBottomPanel();
                }
            }, row, col);
        }


    }
}
