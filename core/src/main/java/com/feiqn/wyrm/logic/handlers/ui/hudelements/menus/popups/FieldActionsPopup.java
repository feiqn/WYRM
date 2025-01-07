package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus.UnitInfoMenu;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.tiledata.prefabtiles.ObjectiveEscapeTile;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.Ballista;
import com.feiqn.wyrm.models.unitdata.Unit;

public class FieldActionsPopup extends PopupMenu {

    final Unit unit;

    int storedOriginRow,
        storedOriginColumn;

    final FieldActionsPopup self = this;

    public FieldActionsPopup(WYRMGame game, Unit unit, int originRow, int originColumn) {
        super(game);
        this.unit = unit;
        storedOriginColumn = originColumn;
        storedOriginRow = originRow;

//        setPosition(Gdx.input.getX(), Gdx.input.getY());

//        final Array<Label> labels = new Array<>();

        // CANCEL

        // WAIT
        final Label waitLabel = new Label("Wait", game.assetHandler.menuLabelStyle);
//        waitLabel.setFontScale(1);
//        labels.add(waitLabel);
        layout.add(waitLabel).row();

        waitLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                unit.setCannotMove();

                ags.activeUnit = null;
                ags.checkIfAllUnitsHaveMovedAndPhaseShouldChange();
                self.remove();
            }

        });

        // INVENTORY
        final Label inventoryLabel = new Label("Inventory", game.assetHandler.menuLabelStyle);
//        inventoryLabel.setFontScale(1);
//        labels.add(inventoryLabel);
        layout.add(inventoryLabel).row();

        inventoryLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                final InventoryPopup inventoryPopup = new InventoryPopup(game, unit, storedOriginRow, storedOriginColumn);
                ags.hudStage.addActor(inventoryPopup);
//                inventoryPopup.setPosition(abs.hudStage.getWidth() * .6f,abs.hudStage.getHeight() * .2f);

                ags.activeUnit = null;
                self.remove(); // needs to be put back by inventory when closed unless action used // TODO: fadeout instead?
            }

        });

        // INFO
        final Label infoLabel = new Label("Info", game.assetHandler.menuLabelStyle);
//        infoLabel.setFontScale(1);
//        labels.add(infoLabel);
        layout.add(infoLabel).row();

        infoLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                final UnitInfoMenu infoPopup = new UnitInfoMenu(game, unit);
                ags.hudStage.addActor(infoPopup);
//                infoPopup.setPosition(abs.hudStage.getWidth() * .6f,abs.hudStage.getHeight() * .2f);

                ags.activeUnit = null;
                self.remove(); // needs to be put back by inventory when closed unless action used
            }
        });

        // DOOR

        // CHEST

        // BREAK

        // BALLISTA
        boolean onABallista = false;
        Ballista presentBallista = null;

        for(Ballista ballista : ags.ballistaObjects) {
            if(ballista.row == unit.getRow() && ballista.column == unit.getColumn()) {
                onABallista = true;
                presentBallista = ballista;
            }
        }

        if(onABallista) {
            final Label ballistaLabel = new Label("Ballista", game.assetHandler.menuLabelStyle);
//            ballistaLabel.setFontScale(1);
//            labels.add(ballistaLabel);
            layout.add(ballistaLabel).row();

            final Ballista finalPresentBallista = presentBallista;
            ballistaLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    finalPresentBallista.enterUnit(unit);
                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, unit, finalPresentBallista);
                    ags.hudStage.addActor(bap);
                    ags.activeUnit = null;
                    self.remove();
                }
            });
        }

        // ATTACK
        final Array<Unit> enemiesInRange = new Array<>();

        for(Unit enemy : ags.teamHandler.getEnemyTeam()) {
            final int distance = ags.getLogicalMap().distanceBetweenTiles(enemy.occupyingTile, unit.occupyingTile);
            if(distance <= unit.iron_getReach()) {
//                Gdx.app.log("reach", "" + unit.getReach());
                enemiesInRange.add(enemy);
            }
        }

        if(enemiesInRange.size > 0) {
            final Label attackLabel = new Label("Attack", game.assetHandler.menuLabelStyle);
//            attackLabel.setFontScale(1);
//            labels.add(attackLabel);
            layout.add(attackLabel).row();

            attackLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    // open attack interface
                    // TODO: select enemy from list
                    if(enemiesInRange.size == 1) {
                        ags.hudStage.addActor(new BattlePreviewPopup(game, ags.activeUnit, enemiesInRange.get(0), storedOriginRow, storedOriginColumn));
                        ags.activeUnit = null;
                        self.remove();
                    } else {
                        // list/highlight enemies in range and select which one to attack
                    }
                }

            });
        }

        // SEIZE

        // TALK
        final Label talkLabel = new Label("Talk", game.assetHandler.menuLabelStyle);
//        talkLabel.setFontScale(1);
//        labels.add(talkLabel);
        layout.add(talkLabel).row();

        talkLabel.setColor(Color.GREEN);

        talkLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                self.addAction(Actions.fadeOut(.5f));
                game.activeGridScreen.startConversation(new Conversation(game));
            }
        });

        // ESCAPE
        if(unit.occupyingTile.tileType == LogicalTileType.OBJECTIVE_ESCAPE) {
            final Label escapeLabel = new Label("Escape", game.assetHandler.menuLabelStyle);
//            escapeLabel.setFontScale(1);
//            labels.add(escapeLabel);
            escapeLabel.setColor(Color.GREEN);

            layout.add(escapeLabel).fill().row();


            escapeLabel.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    if(unit.occupyingTile instanceof ObjectiveEscapeTile) {
                        // First, reassure compiler of type safety.
                        if(((ObjectiveEscapeTile) unit.occupyingTile).requiredUnit == unit.rosterID) {
                            // Check if escaping unit is associated with tile's victory condition. If not, falls to else{}.
                            for(int i = 0; i < ags.conditionsHandler.getVictoryConditions().size; i++) {
                                // Iterate through victory conditions to find the relevant one.
                                final VictoryCondition victcon = ags.conditionsHandler.getVictoryConditions().get(i);
                                if(victcon instanceof EscapeOneVictCon) {
                                    // Once again, reassure compiler of type safety.
                                    if(victcon.associatedUnit() == unit.rosterID) {
                                        // Double check we have the correct victory condition selected.
                                        ags.teamHandler.escapeUnit(unit);
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
                            // TODO: flesh out / remove from team / etc
                            ags.teamHandler.escapeUnit(unit);
                            ags.activeUnit = null;
                            self.remove();
                        }
                    }
                }

            });

        }

        // LAYOUT
//        Vector2 lastPosition = new Vector2(background.getX() + background.getWidth() * .05f, background.getY() - (waitLabel.getHeight() * .7f));
//        float width = 0;
//        for(Label label : labels) {
//            addActor(label);
//            if(label.getWidth() > width) {
//                width = label.getWidth() + label.getWidth() * .25f;
//            }
//            label.setPosition(lastPosition.x, lastPosition.y + label.getHeight() * 1.5f);
//            lastPosition = new Vector2(label.getX(), label.getY());
//        }
//
//        background.setWidth(width + (width * .25f));
//        background.setHeight(waitLabel.getHeight() * (labels.size + 2.5f));
    }
}
