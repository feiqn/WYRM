package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.fullscreenmenus.UnitInfoMenu;
import com.feiqn.wyrm.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.EscapeOneVictCon;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.LogicalTileType;
import com.feiqn.wyrm.models.mapdata.mapobjectdata.prefabObjects.OLD_BallistaObject;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;

public class FieldActionsPopup extends PopupMenu {

    final OLD_SimpleUnit unit;

    int storedOriginRow,
        storedOriginColumn;

    final FieldActionsPopup self = this;

    public FieldActionsPopup(WYRMGame game, OLD_SimpleUnit unit, int originRowY, int originColumnX) {
        super(game);
        this.unit = unit;
        storedOriginColumn = originColumnX;
        storedOriginRow = originRowY;

        layout.setFillParent(true);
        layout.pad(Gdx.graphics.getHeight() * .01f);

        // CANCEL
        final Label cancelLabel = new Label("Cancel", WYRMGame.assets().menuLabelStyle);
        layout.add(cancelLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
        cancelLabel.setFontScale(2);
        cancelLabel.setColor(Color.SCARLET);

        cancelLabel.addListener(new InputListener() {

            ToolTipPopup toolTipPopup;
            boolean clicked = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                clicked = true;
                game.activeOLDGridScreen.getLogicalMap().placeUnitAtPositionXY(unit, originColumnX, originRowY);
                unit.idle();
                ags.activeUnit = null;
                ags.setInputMode(OLD_GridScreen.OLD_InputMode.STANDARD);
                game.activeOLDGridScreen.hud().reset();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                toolTipPopup = new ToolTipPopup(game,"Reset back to original position.");
                game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
//                toolTipPopup.setPosition(cancelLabel.getX() + layout.getWidth() * 1.5f, cancelLabel.getY());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(!clicked) game.activeOLDGridScreen.hud().removeToolTip();
            }

        });

        // WAIT
        final Label waitLabel = new Label("Wait", WYRMGame.assets().menuLabelStyle);
        layout.add(waitLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
        waitLabel.setFontScale(2);
        waitLabel.setColor(Color.GOLD);

        waitLabel.addListener(new InputListener() {

            ToolTipPopup toolTipPopup;
            boolean clicked = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                clicked = true;
                unit.setCannotMove();
                unit.idle();
                ags.setInputMode(OLD_GridScreen.OLD_InputMode.STANDARD);

                game.activeOLDGridScreen.conditions().conversations().checkAreaTriggers(unit.rosterID, unit.getTeamAlignment(), new Vector2(unit.getX(), unit.getY()));

//                boolean yes = game.activeGridScreen.conditions().conversations().checkAreaTriggers(unit.rosterID, unit.getTeamAlignment(), new Vector2(unit.getColumnX(), unit.getRowY()));
                // TODO: better implementation ^

                ags.activeUnit = null;
//                if (!yes)
                    ags.checkLineOrder();
                game.activeOLDGridScreen.hud().reset();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                toolTipPopup = new ToolTipPopup(game,"Wait here.");
                game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
//                toolTipPopup.setPosition(waitLabel.getX() + layout.getWidth() * 1.5f, waitLabel.getY());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(!clicked) game.activeOLDGridScreen.hud().removeToolTip();
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
        final Label infoLabel = new Label("Info", WYRMGame.assets().menuLabelStyle);
        layout.add(infoLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
        infoLabel.setFontScale(2);

        infoLabel.addListener(new InputListener() {

            ToolTipPopup toolTipPopup;
            boolean clicked = false;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                clicked = true;
                final UnitInfoMenu infoPopup = new UnitInfoMenu(game, unit);
                ags.hud().addFullscreen(infoPopup);

                ags.activeUnit = null;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                toolTipPopup = new ToolTipPopup(game,"View unit's statistical details.");
                game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(!clicked) game.activeOLDGridScreen.hud().removeToolTip();
            }

        });

        // DOOR

        // CHEST

        // BREAK

        // BALLISTA
        boolean onABallista = false;
        OLD_BallistaObject presentBallista = null;

        for(OLD_BallistaObject ballista : ags.ballistaObjects) {
            if(ballista.row == unit.getRowY() && ballista.column == unit.getColumnX()) {
                onABallista = true;
                presentBallista = ballista;
            }
        }

        if(onABallista) {
            final Label ballistaLabel = new Label("Ballista", WYRMGame.assets().menuLabelStyle);
            layout.add(ballistaLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();
            ballistaLabel.setFontScale(2);

            final OLD_BallistaObject finalPresentBallista = presentBallista;
            ballistaLabel.addListener(new InputListener() {

                ToolTipPopup toolTipPopup;
                boolean clicked = false;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    clicked = true;
//                    finalPresentBallista.enterUnit(unit);
                    final BallistaActionsPopup bap = new BallistaActionsPopup(game, unit, finalPresentBallista);
                    ags.hud().addPopup(bap);
                    ags.activeUnit = null;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    toolTipPopup = new ToolTipPopup(game,"Hey Shinji Get In The Ballista.");
                    game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(!clicked) game.activeOLDGridScreen.hud().removeToolTip();
                }

            });
        }

        // ATTACK
        final Array<OLD_SimpleUnit> enemiesInRange = new Array<>();

        for(OLD_SimpleUnit enemy : ags.conditions().teams().getEnemyTeam()) {
            final int distance = ags.getLogicalMap().distanceBetweenTiles(enemy.getOccupyingTile(), unit.getOccupyingTile());
            if(distance <= unit.getSimpleReach()) {
//                Gdx.app.log("reach", "" + unit.getReach());
                enemiesInRange.add(enemy);
            }
        }

        if(enemiesInRange.size > 0) {
            final Label attackLabel = new Label("Attack", WYRMGame.assets().menuLabelStyle);
            attackLabel.setColor(Color.ORANGE);
            attackLabel.setFontScale(2);
            layout.add(attackLabel).padBottom(Gdx.graphics.getHeight() * 0.01f).row();

            attackLabel.addListener(new InputListener() {

                ToolTipPopup toolTipPopup;
                boolean clicked = false;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    // open attack interface
                    clicked = true;
                    if(enemiesInRange.size == 1) {
                        ags.hud().addPopup(new BattlePreviewPopup(game, unit, enemiesInRange.get(0), storedOriginRow, storedOriginColumn));
                        ags.activeUnit = null;
                    } else {
                        // list/highlight enemies in range and select which one to attack
                        ags.hud().addPopup(new EnemySelectionPopup(game, unit, enemiesInRange, storedOriginRow, storedOriginColumn));
                        ags.activeUnit = null;
                    }
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    toolTipPopup = new ToolTipPopup(game,"Attack the enemy.");
                    game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
//                    toolTipPopup.setPosition(attackLabel.getX() + layout.getWidth() * 1.5f, attackLabel.getY());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(!clicked) game.activeOLDGridScreen.hud().removeToolTip();
                }

            });
        }

        // --- ABILITIES

        // DIVE BOMB
        if(unit.getAbilities().contains(Abilities.DIVE_BOMB, true) && enemiesInRange.size > 0) {
            final Label diveBombLabel = new Label("Dive Bomb", WYRMGame.assets().menuLabelStyle);
            layout.add(diveBombLabel).padBottom(Gdx.graphics.getHeight() * .01f).row();
            diveBombLabel.setColor(Color.ORANGE);
            diveBombLabel.setFontScale(2);

            diveBombLabel.addListener(new InputListener() {

                ToolTipPopup toolTipPopup;
                boolean clicked = false;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    clicked = true;
                    if(enemiesInRange.size == 1) {
                        unit.setCannotMove();
                        ags.activeUnit = null;
                        game.activeOLDGridScreen.hud().reset();

                        ags.conditions().combat().useAbility().DiveBomb(enemiesInRange.get(0));

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                ags.setInputMode(OLD_GridScreen.OLD_InputMode.STANDARD);
                                ags.checkLineOrder();
//                                game.activeGridScreen.hud().reset();
                            }
                        }, 1);
                    } else {
                        // list/highlight enemies in range and select which one to attack
                    }
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    toolTipPopup = new ToolTipPopup(game,"Stun the enemy for 1 turn.");
                    game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
//                    toolTipPopup.setPosition(diveBombLabel.getX() + layout.getWidth() * 1.5f, diveBombLabel.getY());
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(!clicked) game.activeOLDGridScreen.hud().removeToolTip();
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
//
//            ToolTipPopup toolTipPopup;
//            boolean clicked = false;
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                clicked = true;
//                game.activeGridScreen.startConversation(new Conversation(game, new ChoreographedDialogScript(game)));
//            }
//
//            @Override
//            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                toolTipPopup = new ToolTipPopup(game,"Have a debug conversation.");
//                game.activeGridScreen.hud().addToolTip(toolTipPopup);
//            }
//
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                if(!clicked) game.activeGridScreen.hud().removeToolTip();
//            }
//
//        });

        // ESCAPE
        if(unit.getOccupyingTile().tileType == LogicalTileType.OBJECTIVE_ESCAPE) {
            final Label escapeLabel = new Label("Escape", WYRMGame.assets().menuLabelStyle);
            escapeLabel.setColor(Color.GREEN);
            escapeLabel.setFontScale(2);
            layout.add(escapeLabel).fill().row();

            escapeLabel.addListener(new InputListener() {

                ToolTipPopup toolTipPopup;
                boolean clicked = false;

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    if(clicked) clicked = false;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    clicked = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    if(!clicked) return;

                    // TODO: this check should probably be run in ConditionsHandler
                    for(VictoryCondition vc : ags.conditions().getVictoryConditions()) {
                        if(vc instanceof EscapeOneVictCon) {
                            if(vc.getAssociatedUnit() == unit.rosterID) {
                                Gdx.app.log("conditions", "victcon satisfied");
                                vc.satisfy();
                                break;
                            } else if(vc.getAssociatedUnit() == UnitRoster.LEIF
                                    && unit.rosterID == UnitRoster.LEIF_MOUNTED) {
                                Gdx.app.log("conditions", "victcon satisfied");
                                vc.satisfy();
                                break;
                            }

                        }
                    }

                    ags.conditions().teams().escapeUnit(unit);
                    ags.activeUnit = null;
                    ags.checkForStageCleared();
                    self.remove();

                    clicked = false;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    toolTipPopup = new ToolTipPopup(game,"Flee the battlefield safely.");
                    game.activeOLDGridScreen.hud().addToolTip(toolTipPopup);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(!clicked) game.activeOLDGridScreen.hud().removeToolTip();
                }

            });
        }
    }
}
