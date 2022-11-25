import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.xml.transform.Templates;

public class GameController {
    private final Game game;
    private final GameView gameView;

    // Misc listeners
    private final ActionListener sleepListener;
    private final ActionListener upgradeFarmerListener;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        ActionListener gameRestartListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.reset();
                gameView.reset();

                gameView.initializeMiscListener(sleepListener, upgradeFarmerListener);
                updateAllFarmTiles();
                updateNorthPanel();
                gameView.updateBottomPanel();
            }
        };

        this.sleepListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.getPlayer().getObjectCoins() < 5 && !game.getFarm().hasCrop()) {
                    gameView.endGame(
                            "You have no more crops and no more money to buy more!",
                            gameRestartListener
                    );

                    return;
                }

                if (game.getFarm().isAllWithered()) {
                    gameView.endGame(
                            "All of your crops have died!",
                            gameRestartListener
                    );

                    return;
                }

                game.advanceDay();
                gameView.resetActionPanel();
                gameView.updateBottomPanel();
                updateAllFarmTiles();
                updateNorthPanel();
            }
        };

        this.upgradeFarmerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getPlayer().upgradeFarmer(game.getFarmerTypeList());
                updateNorthPanel();
            }
        };

        this.gameView.initializeMiscListener(this.sleepListener, this.upgradeFarmerListener);
        this.updateAllFarmTiles();
        this.updateNorthPanel();
    }

    private void updateAllFarmTiles() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                this.updateTile(i, j);
            }
        }
    }

    private void updateNorthPanel() {
        gameView.updatePlayerInfo(game.getPlayer().getLevel(), game.getPlayer().getExperience(),
                game.getPlayer().getObjectCoins(), game.getPlayer().getFarmerType(),
                game.getDay());
        gameView.setUpgradeFarmerButtonEnabled(game.getPlayer().canUpgradeFarmer(game.getFarmerTypeList()));
    }

    private void updateTile(int row, int col) {
        Tile tile = this.game.getFarm().getTile(row, col);
        ActionListener shovelButtonAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                game.getPlayer().shovel(row, col);

                gameView.resetActionPanel();
                updateTile(row, col);
                updateNorthPanel();
                gameView.updateBottomPanel();
            }
        };

        // change to change img
        if(tile.hasRock()) { // Tile has rock; show pickaxe
            this.gameView.setTileText("rock", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    gameView.resetActionPanel();

                    if (game.getPlayer().getObjectCoins() >= 50) {
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().pickaxe(row, col);
                                updateTile(row, col);
                            }
                        }, "pickaxe");
                    }

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    gameView.updateBottomPanel();
                    updateTile(row, col);
                    updateNorthPanel();
                }
            }, row, col);

        } else if (tile.getCrop() != null) { // Tile has crop; show water, fertilize, shovel
            Crop crop = tile.getCrop();

            // template
            if (crop.getSeed().getName() == "Apple" || crop.getSeed().getName() == "Mango") {
                gameView.setTileText(crop.isAlive() ? crop.getSeed().getName() : "withered", row, col);
                
            } else {
                gameView.setTileIcon(crop.getSeed().getName(), row, col);
            }

            if (crop.isAlive()) {
                this.gameView.changeFarmTileListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        gameView.resetActionPanel();

                        Tile farmTile = game.getFarm().getTile(row, col);
                        if (farmTile.getCrop().getAge() != farmTile.getCrop().getSeed().getHarvestTime()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().water(row, col);

                                    updateTile(row, col);
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

                                    updateTile(row, col);
                                    updateNorthPanel();
                                }
                            }, "fertilizer");
                        }
    
                        if (crop.isHarvestReady()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().harvest(row, col);

                                    gameView.resetActionPanel();
                                    updateTile(row, col);
                                    updateNorthPanel();
                                    gameView.updateBottomPanel();
                                }
                            }, "harvest");
                        }

                        if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                        gameView.updateBottomPanel();
                        updateTile(row, col);
                        updateNorthPanel();
                    }
                }, row, col);
            }

        } else if (tile.isPlowed()) { // Tile is plowed; show all possible seed that can be planted
            gameView.setTileIcon("plowed", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    gameView.resetActionPanel();

                    // Add Plant action to seeds that can be bought with the current objectcoins only
                    if (game.getPlayer().getObjectCoins() >= 5) {
                        // Turnip
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(0));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(0).getName());

                        // Rose
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(3));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(3).getName());
                    }

                    if (game.getPlayer().getObjectCoins() >= 10) {
                        // Carrot
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(1));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(1).getName());

                        // Tulips
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(4));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(4).getName());
                    }

                    if (game.getPlayer().getObjectCoins() >= 20) {
                        // Potato
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(2));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(2).getName());

                        // Sunflower
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(5));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(5).getName());
                    }

                    if (game.getPlayer().getObjectCoins() >= 100) {
                        // Mango tree
                        if (game.getFarm().canPlantTree(row, col)) gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(6));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(6).getName());
                    }

                    if (game.getPlayer().getObjectCoins() >= 200) {
                        // Apple tree
                        if (game.getFarm().canPlantTree(row, col)) gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(7));
                                updateTile(row, col);

                                updateNorthPanel();
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(7).getName());
                    }

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    updateTile(row, col);
                    updateNorthPanel();
                    gameView.updateBottomPanel();
                }
            }, row, col);

        } else { // Tile is unplowed; no action buttons
            gameView.setTileIcon("unplowed", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    gameView.resetActionPanel();

                    gameView.addActionButton(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            game.getPlayer().plow(row, col);
                            updateTile(row, col);

                            gameView.resetActionPanel();
                            updateNorthPanel();
                            gameView.updateBottomPanel();
                        }
                    }, "plow");

                    if (game.getPlayer().getObjectCoins() >= 7) gameView.addActionButton(shovelButtonAL, "shovel");

                    updateTile(row, col);
                    updateNorthPanel();
                    gameView.updateBottomPanel();
                }
            }, row, col);
        }

        //gameView.setFarmTileSelected(row, col);
    }
}
