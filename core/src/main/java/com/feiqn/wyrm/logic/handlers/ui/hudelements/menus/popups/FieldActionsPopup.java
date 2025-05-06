package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus.UnitInfoMenu;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.ObjectiveEscapeTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class FieldActionsPopup extends PopupMenu {

    final SimpleUnit unit;

    int storedOriginRow,
        storedOriginColumn;

    final FieldActionsPopup self = this;

    public FieldActionsPopup(WYRMGame game, SimpleUnit unit, int originRowY, int originColumnX) {
        super(game);
        this.unit = unit;
        storedOriginColumn = originColumnX;
        storedOriginRow = originRowY;

        layout.setFillParent(true);

        layout.pad(Gdx.graphics.getHeight() * .01f);

        // CANCEL
        final Label cancelLabel = new Label("Cancel", game.assetHandler.menuLabelStyle);
        layout.add(cancelLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
        cancelLabel.setFontScale(2);
        cancelLabel.setColor(Color.SCARLET);

        cancelLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeGridScreen.getLogicalMap().placeUnitAtPositionROWCOLUMN(unit, originRowY, originColumnX);
                ags.activeUnit = null;
                ags.setInputMode(GridScreen.InputMode.STANDARD);
                game.activeGridScreen.hud().reset();
            }

        });

        // WAIT
        final Label waitLabel = new Label("Wait", game.assetHandler.menuLabelStyle);
        layout.add(waitLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
        waitLabel.setFontScale(2);
        waitLabel.setColor(Color.GOLD);

        waitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                unit.setCannotMove();
                ags.setInputMode(GridScreen.InputMode.STANDARD);

                game.activeGridScreen.conditions().conversations().checkAreaTriggers(unit.rosterID, new Vector2(unit.getRowY(), unit.getColumnX()));
                // TODO: better implementation ^

                ags.activeUnit = null;
                ags.checkLineOrder();
                game.activeGridScreen.hud().reset();
            }

        });

        // INVENTORY
//        final Label inventoryLabel = new Label("Inventory", game.assetHandler.menuLabelStyle);
//        layout.add(inventoryLabel).row();
//
//        inventoryLabel.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                final InventoryPopup inventoryPopup = new InventoryPopup(game, unit, storedOriginRow, storedOriginColumn);
//                ags.hud().addPopup(inventoryPopup);
//
//                ags.activeUnit = null;
//            }
//        });

        // INFO
        final Label infoLabel = new Label("Info", game.assetHandler.menuLabelStyle);
        layout.add(infoLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
        infoLabel.setFontScale(2);

        infoLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                final UnitInfoMenu infoPopup = new UnitInfoMenu(game, unit);
                ags.hud().addFullscreen(infoPopup);

                ags.activeUnit = null;
//                self.remove(); // needs to be put back by inventory when closed unless action used
            }
        });

        // DOOR

        // CHEST

        // BREAK

        // BALLISTA
        boolean onABallista = false;
        Ballista presentBallista = null;

        for(Ballista ballista : ags.ballistaObjects) {
            if(ballista.row == unit.getRowY() && ballista.column == unit.getColumnX()) {
                onABallista = true;
                presentBallista = ballista;
            }
        }

        if(onABallista) {
            final Label ballistaLabel = new Label("Ballista", game.assetHandler.menuLabelStyle);
            layout.add(ballistaLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
            ballistaLabel.setFontScale(2);

            final Ballista finalPresentBallista = presentBallista;
            ballistaLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    finalPresentBallista.enterUnit(unit);
                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, unit, finalPresentBallista);
                    ags.hud().addPopup(bap);
                    ags.activeUnit = null;
//                    self.remove();
                }
            });
        }

        // ATTACK
        final Array<SimpleUnit> enemiesInRange = new Array<>();

        for(SimpleUnit enemy : ags.conditions().teams().getEnemyTeam()) {
            final int distance = ags.getLogicalMap().distanceBetweenTiles(enemy.occupyingTile, unit.occupyingTile);
            if(distance <= unit.getSimpleReach()) {
//                Gdx.app.log("reach", "" + unit.getReach());
                enemiesInRange.add(enemy);
            }
        }

        if(enemiesInRange.size > 0) {
            final Label attackLabel = new Label("Attack", game.assetHandler.menuLabelStyle);
            attackLabel.setColor(Color.ORANGE);
            attackLabel.setFontScale(2);
            layout.add(attackLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();

            attackLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    // open attack interface
                    // TODO: select enemy from list
                    if(enemiesInRange.size == 1) {
                        ags.hud().addPopup(new BattlePreviewPopup(game, unit, enemiesInRange.get(0), storedOriginRow, storedOriginColumn));
                        ags.activeUnit = null;
//                        self.remove();
                    } else {
                        // list/highlight enemies in range and select which one to attack
                    }
                }
            });
        }

        // --- ABILITIES

        // DIVE BOMB

        if(unit.getAbilities().contains(Abilities.DIVE_BOMB, true) && enemiesInRange.size > 0) {
            final Label diveBombLabel = new Label("Dive Bomb", game.assetHandler.menuLabelStyle);
            layout.add(diveBombLabel).padBottom(Gdx.graphics.getHeight() * .01f).row();
            diveBombLabel.setColor(Color.ORANGE);
            diveBombLabel.setFontScale(2);

            diveBombLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    if(enemiesInRange.size == 1) {
                        unit.setCannotMove();
                        ags.activeUnit = null;
                        game.activeGridScreen.hud().reset();

                        ags.conditions().combat().abilities().DiveBomb(game, enemiesInRange.get(0));

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                ags.setInputMode(GridScreen.InputMode.STANDARD);
                                ags.checkLineOrder();
//                                game.activeGridScreen.hud().reset();
                            }
                        }, 1);
                    } else {
                        // list/highlight enemies in range and select which one to attack
                    }
                }
            });

        }

        // SEIZE

        // TALK
//        final Label talkLabel = new Label("Talk", game.assetHandler.menuLabelStyle);
//        layout.add(talkLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
//        talkLabel.setColor(Color.GREEN);
//        talkLabel.setFontScale(2);
//
//        talkLabel.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int point, int button) {
////                self.remove();
//                game.activeGridScreen.startConversation(new Conversation(game, new ChoreographedDialogScript(game)));
//            }
//        });

        // ESCAPE
        if(unit.occupyingTile.tileType == LogicalTileType.OBJECTIVE_ESCAPE) {
            final Label escapeLabel = new Label("Escape", game.assetHandler.menuLabelStyle);
            escapeLabel.setColor(Color.GREEN);
            escapeLabel.setFontScale(2);
            layout.add(escapeLabel).fill().row();

            escapeLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    if(unit.occupyingTile instanceof ObjectiveEscapeTile) {
                        // First, reassure compiler of type safety.
                        if(((ObjectiveEscapeTile) unit.occupyingTile).getObjectiveUnit() == unit.rosterID) {
                            // Check if escaping unit is associated with tile's victory condition. If not, falls to else{}.
                            for(int i = 0; i < ags.conditions().getVictoryConditions().size; i++) {
                                // Iterate through victory conditions to find the relevant one.
                                final VictoryCondition victcon = ags.conditions().getVictoryConditions().get(i);
                                if(victcon instanceof EscapeOneVictCon) {
                                    // Grab the correct victcon from the array.
                                    if(victcon.getAssociatedUnit() == unit.rosterID) {
                                        // Double check we have the correct victory condition selected.
                                        ags.conditions().teams().escapeUnit(unit);
                                        Gdx.app.log("conditions", "victcon satisfied");
                                        victcon.satisfy();

                                        ags.checkForStageCleared();

                                        ags.activeUnit = null;
                                        self.remove();
                                    }
                                } else {
                                    // TODO: handle EscapeManyVictCon
                                }
                            }
                        } else {
                            // escape unit, no victcon flags
                            // TODO: flesh out
                            ags.conditions().teams().escapeUnit(unit);
                            ags.activeUnit = null;
                            self.remove();
                        }
                    }
                }
            });
        }
    }
}
