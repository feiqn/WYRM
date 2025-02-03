package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class FieldActionsPopup extends PopupMenu {

    final SimpleUnit unit;

    int storedOriginRow,
        storedOriginColumn;

    final FieldActionsPopup self = this;

    public FieldActionsPopup(WYRMGame game, SimpleUnit unit, int originRow, int originColumn) {
        super(game);
        this.unit = unit;
        storedOriginColumn = originColumn;
        storedOriginRow = originRow;

        layout.setFillParent(true);

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
                ags.checkLineOrder();
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
                ags.hud().addPopup(inventoryPopup);

                ags.activeUnit = null;
//                self.remove(); // needs to be put back by inventory when closed unless action used // TODO: fadeout instead?
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
                ags.hud().addFullscreen(infoPopup);

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
                    ags.hud().addPopup(bap);
                    ags.activeUnit = null;
                    self.remove();
                }
            });
        }

        // ATTACK
        final Array<SimpleUnit> enemiesInRange = new Array<>();

        for(SimpleUnit enemy : ags.conditionsHandler.teams().getEnemyTeam()) {
            final int distance = ags.getLogicalMap().distanceBetweenTiles(enemy.occupyingTile, unit.occupyingTile);
            if(distance <= unit.getSimpleReach()) {
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
                        ags.hud().addPopup(new BattlePreviewPopup(game, ags.activeUnit, enemiesInRange.get(0), storedOriginRow, storedOriginColumn));
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
                self.remove();
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
                                        ags.conditionsHandler.teams().escapeUnit(unit);
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
                            ags.conditionsHandler.teams().escapeUnit(unit);
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
