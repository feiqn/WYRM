package com.feiqn.wyrm.logic.handlers.ui.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.PopupMenu;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.ObjectiveEscapeTile;
import com.feiqn.wyrm.models.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.unitdata.Unit;

public class FieldActionsPopup extends PopupMenu {

    final Unit unit;

    int storedOriginRow,
        storedOriginColumn;

    final FieldActionsPopup self = this;

    public FieldActionsPopup(WYRMGame game, Unit unit, float x, float y, int originRow, int originColumn) {
        super(game);
        this.unit = unit;
//        Gdx.app.log("x", "" + x);
//        Gdx.app.log("y", "" + y);
        storedOriginColumn = originColumn;
        storedOriginRow = originRow;
        addSmallTargeted(this.unit);
    }

    protected void addSmallTargeted(final Unit unit) {

        addActor(background);

        background.setPosition((unit.occupyingTile.getCoordinates().x + 1), (unit.occupyingTile.getCoordinates().y + 1)); // TODO: place menu on mouse cursor -- translate hudStage to gameStage co-ords?

        final Array<Label> labels = new Array<>();

        // CANCEL

        // WAIT
        final Label waitLabel = new Label("Wait", game.assetHandler.menuLabelStyle);
        waitLabel.setFontScale(1);
        labels.add(waitLabel);
        waitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                unit.setCannotMove();

                game.activeBattleScreen.activeUnit = null;
                game.activeBattleScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange();
                self.remove();
            }

        });

        // INVENTORY
        final Label inventoryLabel = new Label("Inventory", game.assetHandler.menuLabelStyle);
        inventoryLabel.setFontScale(1);
        labels.add(inventoryLabel);
        inventoryLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                final InventoryPopup inventoryPopup = new InventoryPopup(game, unit, storedOriginRow, storedOriginColumn);
                game.activeBattleScreen.uiGroup.addActor(inventoryPopup);
                inventoryPopup.setPosition(game.activeBattleScreen.hudStage.getWidth() * .6f,game.activeBattleScreen.hudStage.getHeight() * .2f);

                game.activeBattleScreen.activeUnit = null;
                self.remove(); // needs to be put back by inventory when closed unless action used
            }

        });

        // INFO
        final Label infoLabel = new Label("Info", game.assetHandler.menuLabelStyle);
        infoLabel.setFontScale(1);
        labels.add(infoLabel);
        infoLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                final UnitInfoPopup infoPopup = new UnitInfoPopup(game, unit);
                game.activeBattleScreen.uiGroup.addActor(infoPopup);
                infoPopup.setPosition(game.activeBattleScreen.hudStage.getWidth() * .6f,game.activeBattleScreen.hudStage.getHeight() * .2f);

                game.activeBattleScreen.activeUnit = null;
                self.remove(); // needs to be put back by inventory when closed unless action used
            }
        });

        // DOOR

        // CHEST

        // BREAK

        // BALLISTA
        boolean onABallista = false;
        Ballista presentBallista = null;

        for(Ballista ballista : game.activeBattleScreen.ballistaObjects) {
            if(ballista.row == unit.getRow() && ballista.column == unit.getColumn()) {
                onABallista = true;
                presentBallista = ballista;
            }
        }

        if(onABallista) {
            final Label ballistaLabel = new Label("Ballista", game.assetHandler.menuLabelStyle);
            ballistaLabel.setFontScale(1);
            labels.add(ballistaLabel);

            final Ballista finalPresentBallista = presentBallista;
            ballistaLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    finalPresentBallista.enterUnit(unit);
                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, unit, finalPresentBallista);
                    game.activeBattleScreen.uiGroup.addActor(bap);
                    game.activeBattleScreen.activeUnit = null;
                    self.remove();
                }
            });
        }

        // ATTACK
        final Array<Unit> enemiesInRange = new Array<>();

        for(Unit enemy : game.activeBattleScreen.getEnemyTeam()) {
            final int distance = game.activeBattleScreen.distanceBetweenTiles(enemy.occupyingTile, unit.occupyingTile);
            if(distance <= unit.getReach()) {
//                Gdx.app.log("reach", "" + unit.getReach());
                enemiesInRange.add(enemy);
            }
        }

        if(enemiesInRange.size > 0) {
            final Label attackLabel = new Label("Attack", game.assetHandler.menuLabelStyle);
            attackLabel.setFontScale(1);

            labels.add(attackLabel);

            attackLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    // open attack interface
                    // TODO: select enemy from list
                    if(enemiesInRange.size == 1) {
                        game.activeBattleScreen.uiGroup.addActor(new BattlePreviewPopup(game, game.activeBattleScreen.activeUnit, enemiesInRange.get(0), storedOriginRow, storedOriginColumn));
                        game.activeBattleScreen.activeUnit = null;
                        self.remove();
                    } else {
                        // list/highlight enemies in range and select which one to attack
                    }
                }

            });
        }

        // SEIZE

        // ESCAPE
        if(unit.occupyingTile.tileType == LogicalTileType.OBJECTIVE_ESCAPE) {
            final Label escapeLabel = new Label("Escape", game.assetHandler.menuLabelStyle);
            escapeLabel.setFontScale(1);
            escapeLabel.setColor(Color.GREEN);

            labels.add(escapeLabel);

            escapeLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    if(unit.occupyingTile instanceof ObjectiveEscapeTile) {
                        // First, reassure compiler of type safety.
                        if(((ObjectiveEscapeTile) unit.occupyingTile).requiredUnit == unit.rosterID) {
                            // Check if escaping unit is associated with tile's victory condition. If not, falls to else{}.
                            for(int i = 0; i < game.activeBattleScreen.conditionsHandler.getVictoryConditions().size; i++) {
                                // Iterate through victory conditions to find the relevant one.
                                final VictoryCondition victcon = game.activeBattleScreen.conditionsHandler.getVictoryConditions().get(i);
                                if(victcon instanceof EscapeOneVictCon) {
                                    // Once again, reassure compiler of type safety.
                                    if(victcon.associatedUnit() == unit.rosterID) {
                                        // Double check we have the correct victory condition selected.
                                        game.activeBattleScreen.escapeUnit(unit);
                                        Gdx.app.log("conditions", "victcon satisfied");
                                        victcon.satisfy();

                                        game.activeBattleScreen.checkForStageCleared();

                                        game.activeBattleScreen.activeUnit = null;
                                        self.remove();
                                    }
                                } else {
                                    // TODO: handle EscapeManyVictCon
                                }
                            }
                        } else {
                            // escape unit, no victcon flags
                            game.activeBattleScreen.escapeUnit(unit);
                            game.activeBattleScreen.activeUnit = null;
                            self.remove();
                        }
                    }
                }

            });

        }

        // LAYOUT
        Vector2 lastPosition = new Vector2(background.getX() + background.getWidth() * .1f, background.getY() - (waitLabel.getHeight() * .7f));
        float width = 0;
        for(Label label : labels) {
            addActor(label);
            if(label.getWidth() > width) {
                width = label.getWidth();
            }
            label.setPosition(lastPosition.x, lastPosition.y + label.getHeight() * 1.5f);
            lastPosition = new Vector2(label.getX(), label.getY());
        }

        background.setWidth(width + (width * .2f));
        background.setHeight(waitLabel.getHeight() * (labels.size + 2));

    }

}
