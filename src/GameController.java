import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private final Game game;
    private final GameView gameView;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;

        this.gameView.initializeMiscListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.advanceDay();
                    gameView.resetActionPanel();
                    gameView.updateBottomPanel();
                    updateAllFarmTiles();

                    gameView.updatePlayerInfo(game.getPlayer().getLevel(), game.getPlayer().getExperience(),
                                              game.getPlayer().getObjectCoins(), game.getPlayer().getFarmerType(),
                                              game.getDay());
                }
            }, 
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.getPlayer().upgradeFarmer(game.getFarmerTypeList());
                    gameView.updatePlayerInfo(game.getPlayer().getLevel(), game.getPlayer().getExperience(),
                                              game.getPlayer().getObjectCoins(), game.getPlayer().getFarmerType(),
                                              game.getDay());
                }
            }
        );

        this.updateAllFarmTiles();
        this.gameView.updatePlayerInfo(this.game.getPlayer().getLevel(), this.game.getPlayer().getExperience(),
                                       this.game.getPlayer().getObjectCoins(), this.game.getPlayer().getFarmerType(),
                                       this.game.getDay());
    }

    private void updateAllFarmTiles() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                this.updateTile(i, j);
            }
        }
    }

    private void updateTile(int row, int col) {
        Tile tile = this.game.getFarm().getTile(row, col);

        // change to change img
        if(tile.hasRock()) { // Tile has rock; show pickaxe
            this.gameView.setTileText("rock", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JButton farmTileBtn = (JButton) event.getSource();
                    int[] location = (int[]) farmTileBtn.getClientProperty("location");
                    
                    gameView.resetActionPanel();

                    if (game.getPlayer().getObjectCoins() >= 50) {
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().pickaxe(location[0], location[1]);
                                updateTile(row, col);
                            }
                        }, "Pickaxe");
                    } 

                    gameView.updateBottomPanel();
                    updateTile(location[0], location[1]);
                    gameView.updatePlayerInfo(game.getPlayer().getLevel(), game.getPlayer().getExperience(),
                                                game.getPlayer().getObjectCoins(), game.getPlayer().getFarmerType(),
                                                game.getDay());
                }
            }, row, col);

        } else if (tile.getCrop() != null) { // Tile has crop; show water, fertilize, shovel?
            Crop crop = tile.getCrop();
            this.gameView.setTileText(crop.isAlive() ? crop.getSeed().getName() : "withered", row, col);

            if (crop.isAlive()) {
                this.gameView.changeFarmTileListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        JButton farmTileBtn = (JButton) event.getSource();
                        int[] location = (int[]) farmTileBtn.getClientProperty("location");
    
                        gameView.resetActionPanel();
    
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().water(location[0], location[1]);
                                updateTile(row, col);
                            }
                        }, "Water");
    
                        if(game.getPlayer().getObjectCoins() >= 10) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().fertilize(location[0], location[1]);
                                    updateTile(row, col);
                                }
                            }, "Fertilizer");
                        }
    
                        if(crop.isHarvestReady()) {
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().harvest(location[0], location[1]);
                                    updateTile(row, col);
                                }
                            }, "Harvest");
                        }
                        
                        gameView.updateBottomPanel();
                        updateTile(location[0], location[1]);
                        gameView.updatePlayerInfo(game.getPlayer().getLevel(), game.getPlayer().getExperience(),
                                                  game.getPlayer().getObjectCoins(), game.getPlayer().getFarmerType(),
                                                  game.getDay());
                    }
                }, row, col);
            } else {
                
            }

        } else if (tile.isPlowed()) { // Tile is plowed; show all possible seed that can be planted
            gameView.setTileText("plowed", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JButton farmTileBtn = (JButton) event.getSource();
                    int[] location = (int[]) farmTileBtn.getClientProperty("location");

                    gameView.resetActionPanel();

                    // Add Plant action to seeds that can be bought with the current objectcoins only
                    if (game.getPlayer().getObjectCoins() >= 5) {
                        // Turnip
                        gameView.addActionButton(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                game.getPlayer().plant(row, col, game.getSeed(0));
                                updateTile(row, col);
    
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
    
                                gameView.resetActionPanel();
                                gameView.updateBottomPanel();
                            }
                        }, game.getSeed(3).getName());

                        if (game.getPlayer().getObjectCoins() >= 10) {
                            // Carrot
                            gameView.addActionButton(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    game.getPlayer().plant(row, col, game.getSeed(1));
                                    updateTile(row, col);
        
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
        
                                    gameView.resetActionPanel();
                                    gameView.updateBottomPanel();
                                }
                            }, game.getSeed(4).getName());

                            if (game.getPlayer().getObjectCoins() >= 20) {
                                // Potato
                                gameView.addActionButton(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        game.getPlayer().plant(row, col, game.getSeed(2));
                                        updateTile(row, col);
            
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
            
                                        gameView.resetActionPanel();
                                        gameView.updateBottomPanel();
                                    }
                                }, game.getSeed(5).getName());

                                
                                if (game.getPlayer().getObjectCoins() >= 100) {
                                    // Mango tree
                                    gameView.addActionButton(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent event) {
                                            game.getPlayer().plant(row, col, game.getSeed(6));
                                            updateTile(row, col);
                
                                            gameView.resetActionPanel();
                                            gameView.updateBottomPanel();
                                        }
                                    }, game.getSeed(6).getName());


                                    if (game.getPlayer().getObjectCoins() >= 200) {
                                        // Apple tree
                                        gameView.addActionButton(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent event) {
                                                game.getPlayer().plant(row, col, game.getSeed(7));
                                                updateTile(row, col);
                    
                                                gameView.resetActionPanel();
                                                gameView.updateBottomPanel();
                                            }
                                        }, game.getSeed(7).getName());
                                    }
                                }
                            }
                        }
                    }

                    updateTile(location[0], location[1]);
                    gameView.updateBottomPanel();
                }
            }, row, col);

        } else { // Tile is unplowed; no action buttons
            gameView.setTileText("unplowed", row, col);
            this.gameView.changeFarmTileListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JButton farmTileBtn = (JButton) event.getSource();
                    int[] location = (int[]) farmTileBtn.getClientProperty("location");

                    game.getPlayer().plow(location[0], location[1]);

                    updateTile(location[0], location[1]);
                    gameView.updatePlayerInfo(game.getPlayer().getLevel(), game.getPlayer().getExperience(),
                                              game.getPlayer().getObjectCoins(), game.getPlayer().getFarmerType(),
                                              game.getDay());
                }
            }, row, col);
        }
    }
}
