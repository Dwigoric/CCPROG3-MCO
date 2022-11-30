import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private final Game game;
    private final GameView gameView;

    private final int[] currTileSelected = {-1, -1};

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
                updatePlayerInfo();
                updateAllFarmTiles();
                gameView.updateBottomPanel();
            }
        };

        this.sleepListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.resetBottomPanel();

                if (game.getPlayer().getObjectCoins() < 5 - game.getPlayer().getFarmerType().getSeedCostReduction() &&
                        !game.getFarm().hasCrop())
                {
                    gameView.showEndGame("You have no more crops and no more money to buy more!", gameRestartListener);

                    return;
                }

                game.advanceDay();

                if (game.getFarm().isAllWithered()) {
                    gameView.showEndGame("All of your crops have withered!", gameRestartListener);

                    return;
                }

                gameView.updateBottomPanel();
                
                updatePlayerInfo();
                updateAllFarmTiles();
            }
        };

        this.upgradeFarmerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getPlayer().upgradeFarmer(game.getFarmerTypeList());
                updatePlayerInfo();
            }
        };
        
        this.bookListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.showBook();
            }
        };

        this.gameView.initializeMiscListener(this.sleepListener, this.upgradeFarmerListener, this.bookListener);
        this.updatePlayerInfo();
        this.updateAllFarmTiles();
    }

    private void updatePlayerInfo() {
        Player player = game.getPlayer();

        gameView.updateTopPanel(player.getLevel(), player.getExperience(), player.getObjectCoins(), 
            game.getFarmerTypeList(), player.getFarmerTypeLvl(), game.getDay());

        gameView.setUpgradeFarmerButtonEnabled(game.getPlayer().canUpgradeFarmer(game.getFarmerTypeList()));
    }

    private void updateAllFarmTiles() {
        Farm farm = this.game.getFarm();

        for (int i = 0; i < farm.getRows(); i++) {
            for (int j = 0; j < farm.getColumns(); j++) {
                this.updateTile(i, j);
            }
        }
    }

    private void updateTile(int row, int column) {
        Tile tile = this.game.getFarm().getTile(row, column);
        ActionListener shovelButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                gameView.resetBottomPanel();
                
                game.getPlayer().shovel(row, column);

                updateTileSelection(row, column);
                updateTopBottomPanels();
            }
        };

        if(tile.hasRock()) { /* Tile has rock; show pickaxe (if player has enough objectCoins) and shovel */
            this.gameView.setTileIcon("rock", row, column);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    gameView.resetBottomPanel();

                    if (game.getPlayer().getObjectCoins() >= 50) {
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                gameView.resetBottomPanel();

                                game.getPlayer().pickaxe(row, column);

                                updateTileSelection(row, column);
                                updateTopBottomPanels();
                            }
                        }, "pickaxe");
                    }

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    updateTileSelection(row, column);
                    updateTopBottomPanels();
                }
            }, row, column);

        } else if (tile.getCrop() != null) { /* Tile has crop; show water, fertilize, shovel (if player has enough objectCoins), harvest (if ready) */
            Crop crop = tile.getCrop();

            if (crop.isAlive()) {
                gameView.setTileIcon(crop.getSeed().getName(), row, column);
                this.gameView.changeFarmTileListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        gameView.resetBottomPanel();

                        Tile farmTile = game.getFarm().getTile(row, column);
                        if (farmTile.getCrop().getAge() != farmTile.getCrop().getSeed().getHarvestTime()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().water(row, column);

                                    updateTileSelection(row, column);
                                    updatePlayerInfo();
                                }
                            }, "water");
                        }
    
                        if (game.getPlayer().getObjectCoins() >= 10 &&
                                farmTile.getCrop().getAge() != farmTile.getCrop().getSeed().getHarvestTime()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().fertilize(row, column);

                                    updateTileSelection(row, column);
                                    updatePlayerInfo();
                                }
                            }, "fertilizer");
                        }
    
                        if (crop.isHarvestReady()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    Crop crop = game.getFarm().getTile(row, column).getCrop();
                                    gameView.resetBottomPanel();

                                    gameView.updateFeedbackPanel(game.getPlayer().harvest(row, column), crop);

                                    updateTileSelection(row, column);
                                    updateTopBottomPanels();
                                }
                            }, "harvest");
                        }

                        if (game.getPlayer().getObjectCoins() >= 7) {
                            gameView.addActionButton(shovelButtonAL, "shovel");
                        }

                        updateTileSelection(row, column);
                        updateTopBottomPanels();
                    }
                }, row, column);
            } else { /* Crop is withered; show shovel (if player has enough objectCoins) */
                gameView.setTileIcon("withered", row, column);
            }

        } else if (tile.isPlowed()) { /* Tile is plowed; show all possible seed that can be planted */
            gameView.setTileIcon("plowed", row, column);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    Player player = game.getPlayer();
                    int seedCostReduction = player.getFarmerType().getSeedCostReduction();
                    gameView.resetBottomPanel();

                    /* Add plant action to seeds that can be bought with the current objectcoins only */
                    if (player.getObjectCoins() >= 5 - seedCostReduction) {
                        // Turnip
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                gameView.resetBottomPanel();

                                game.getPlayer().plant(row, column, game.getSeed(0));
                                updateTileSelection(row, column);

                                updateTopBottomPanels();
                            }
                        }, game.getSeed(0).getName());

                        // Rose
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                gameView.resetBottomPanel();

                                game.getPlayer().plant(row, column, game.getSeed(3));
                                updateTileSelection(row, column);

                                updateTopBottomPanels();
                            }
                        }, game.getSeed(3).getName());
                    }

                    if (player.getObjectCoins() >= 10 - seedCostReduction) {
                        // Carrot
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                gameView.resetBottomPanel();

                                game.getPlayer().plant(row, column, game.getSeed(1));

                                updateTileSelection(row, column);
                                updateTopBottomPanels();
                            }
                        }, game.getSeed(1).getName());

                        // Tulips
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                gameView.resetBottomPanel();

                                game.getPlayer().plant(row, column, game.getSeed(4));

                                updateTileSelection(row, column);
                                updateTopBottomPanels();
                            }
                        }, game.getSeed(4).getName());

                        if (game.getPlayer().getObjectCoins() >= 20 - seedCostReduction) {
                            // Potato
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    gameView.resetBottomPanel();

                                    game.getPlayer().plant(row, column, game.getSeed(2));

                                    updateTileSelection(row, column);
                                    updateTopBottomPanels();
                                }
                            }, game.getSeed(2).getName());

                            // Sunflower
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    gameView.resetBottomPanel();

                                    game.getPlayer().plant(row, column, game.getSeed(5));

                                    updateTileSelection(row, column);
                                    updateTopBottomPanels();
                                }
                            }, game.getSeed(5).getName());

                            if (game.getPlayer().getObjectCoins() >= 100 - seedCostReduction) {
                                // Mango tree
                                if (game.getFarm().canPlantTree(row, column)) gameView.addActionButton(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        gameView.resetBottomPanel();

                                        game.getPlayer().plant(row, column, game.getSeed(6));

                                        updateTileSelection(row, column);
                                        updateTopBottomPanels();
                                    }
                                }, game.getSeed(6).getName());

                                if (game.getPlayer().getObjectCoins() >= 200 - seedCostReduction) {
                                    // Apple tree
                                    if (game.getFarm().canPlantTree(row, column)) gameView.addActionButton(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent event) {
                                            gameView.resetBottomPanel();

                                            game.getPlayer().plant(row, column, game.getSeed(7));

                                            updateTileSelection(row, column);
                                            updateTopBottomPanels();
                                        }
                                    }, game.getSeed(7).getName());
                                }
                            }
                        }
                    }

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    updateTileSelection(row, column);
                    updateTopBottomPanels();
                }
            }, row, column);

        } else { /* Tile is unplowed; show plow, shovel (if player has enough objectCoins) */
            gameView.setTileIcon("unplowed", row, column);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    gameView.resetBottomPanel();

                    gameView.addActionButton(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            gameView.resetBottomPanel();
                            game.getPlayer().plow(row, column);
                            updateTileSelection(row, column);

                            updateTopBottomPanels();
                        }
                    }, "plow");

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    updateTileSelection(row, column);
                    updateTopBottomPanels();
                }
            }, row, column);
        }
    }

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

    private void updateTopBottomPanels() {
        this.updatePlayerInfo();
        gameView.updateBottomPanel();
    }
}
