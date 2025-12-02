package com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BallistaActionsPopup;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups.BattlePreviewPopup;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;

public class GridInputHandler extends WyrInputHandler {

    public enum InputMode {
        STANDARD,
        UNIT_SELECTED,
        MENU_FOCUSED,
        LOCKED,
        CUTSCENE,
    }

    public enum MovementControl {
        FREE_MOVE,
        COMBAT
    }

    protected InputMode inputMode;

    protected MovementControl movementControl;

    public GridInputHandler(WYRMGame root) {
        super(root);
        inputMode = InputMode.STANDARD;
        movementControl = MovementControl.COMBAT;
    }

    public void setInputMode(InputMode mode) { inputMode = mode; }
    public void setMovementControl(MovementControl movementControl) { this.movementControl = movementControl; }

    public InputMode getInputMode() { return inputMode; }


    public static class GridListeners {

        // TODO: method bodies (lol)

        public ClickListener enemyUnitListener(GridUnit enemyUnit) {
            return new ClickListener() {
                boolean dragged = false;
                boolean clicked = true;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    dragged = true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dragged = false;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    if(dragged || !enemyUnit.canMove()) {
                        dragged = false;
                        clicked = false;
                        return;
                    }

                    clicked = true;

//                    for(LogicalTile tile : highlighted) {
//                        tile.clearHighlight();
//                    }
//                    highlighted.clear();

//                    final GridScreen ags = game.activeGridScreen;

//                Gdx.app.log("unit", "touch up, " + ags.getInputMode());

//                    switch(ags.getInputMode()) {
//
//                        case STANDARD:
//                            switch(ags.getMovementControl()) {
//
//                                case COMBAT:
//                                    if(ags.conditions().tickCount() == self.modifiedSimpleSpeed()) {
//                                        // Only move if it's your turn
//                                        if(self.teamAlignment == TeamAlignment.PLAYER) {
//                                            // Unit is player's own unit
//                                            if(!isOccupyingMapObject) {
//                                                ags.setInputMode(GridScreen.InputMode.UNIT_SELECTED);
//                                                ags.activeUnit = self;
//                                                ags.highlightAllTilesUnitCanAccess(self);
//                                                for(SimpleUnit enemy : ags.attackableUnits) {
//                                                    if(enemy.teamAlignment == TeamAlignment.ENEMY || enemy.teamAlignment == TeamAlignment.OTHER) {
//                                                        enemy.addListener(new InputListener() {
//
//                                                            @Override
//                                                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
//
//                                                            @Override
//                                                            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                                                                ags.setInputMode(GridScreen.InputMode.MENU_FOCUSED);
//                                                                ags.activeUnit = null;
//                                                                ags.removeTileHighlighters();
//                                                                final int originCX = getColumnX();
//                                                                final int originRY = getRowY();
//
//                                                                final RunnableAction finish = new RunnableAction();
//                                                                finish.setRunnable(new Runnable() {
//                                                                    @Override
//                                                                    public void run() {
//                                                                        ags.hud().addPopup(new BattlePreviewPopup(game, self, enemy, originRY, originCX));
//
//                                                                    }
//                                                                });
//
//                                                                ags.getLogicalMap().moveAlongPath(self, ags.getLogicalMap().pathToNearestNeighborInRange(self, enemy.getOccupyingTile()), finish, false);
//
//                                                            }
//
//                                                        });
//
//                                                    }
//                                                }
//                                                flourish();
//                                            } else {
//                                                switch(occupyingMapObject.objectType) {
//
//                                                    case BALLISTA:
//                                                        final BallistaActionsPopup bap = new BallistaActionsPopup(game, self, occupyingMapObject);
//                                                        ags.hudStage.addActor(bap);
//                                                        break;
//
//                                                    case DOOR:
//                                                    default:
//                                                        break;
//
//                                                }
//                                            }
//                                        }
//                                    }
//                                    break;
//
//                                case FREE_MOVE:
//                                    if(self.teamAlignment == TeamAlignment.PLAYER) {
//
//                                    }
//                                    break;
//                            }
//
//                            break;
//                        case CUTSCENE:
//                        case MENU_FOCUSED:
//                        case UNIT_SELECTED:
//                        case LOCKED:
//                            break;
//                    }

                }

            };
        }

        public ClickListener playerUnitListener(GridUnit playerUnit) {
            return new ClickListener() {
                boolean dragged = false;
                boolean clicked = true;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    dragged = true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dragged = false;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    if(dragged || !playerUnit.canMove()) {
                        dragged = false;
                        clicked = false;
                        return;
                    }

                    clicked = true;

//                    for(LogicalTile tile : highlighted) {
//                        tile.clearHighlight();
//                    }
//                    highlighted.clear();

//                    final GridScreen ags = game.activeGridScreen;

//                Gdx.app.log("unit", "touch up, " + ags.getInputMode());

//                    switch(ags.getInputMode()) {
//
//                        case STANDARD:
//                            switch(ags.getMovementControl()) {
//
//                                case COMBAT:
//                                    if(ags.conditions().tickCount() == self.modifiedSimpleSpeed()) {
//                                        // Only move if it's your turn
//                                        if(self.teamAlignment == TeamAlignment.PLAYER) {
//                                            // Unit is player's own unit
//                                            if(!isOccupyingMapObject) {
//                                                ags.setInputMode(GridScreen.InputMode.UNIT_SELECTED);
//                                                ags.activeUnit = self;
//                                                ags.highlightAllTilesUnitCanAccess(self);
//                                                for(SimpleUnit enemy : ags.attackableUnits) {
//                                                    if(enemy.teamAlignment == TeamAlignment.ENEMY || enemy.teamAlignment == TeamAlignment.OTHER) {
//                                                        enemy.addListener(new InputListener() {
//
//                                                            @Override
//                                                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
//
//                                                            @Override
//                                                            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                                                                ags.setInputMode(GridScreen.InputMode.MENU_FOCUSED);
//                                                                ags.activeUnit = null;
//                                                                ags.removeTileHighlighters();
//                                                                final int originCX = getColumnX();
//                                                                final int originRY = getRowY();
//
//                                                                final RunnableAction finish = new RunnableAction();
//                                                                finish.setRunnable(new Runnable() {
//                                                                    @Override
//                                                                    public void run() {
//                                                                        ags.hud().addPopup(new BattlePreviewPopup(game, self, enemy, originRY, originCX));
//
//                                                                    }
//                                                                });
//
//                                                                ags.getLogicalMap().moveAlongPath(self, ags.getLogicalMap().pathToNearestNeighborInRange(self, enemy.getOccupyingTile()), finish, false);
//
//                                                            }
//
//                                                        });
//
//                                                    }
//                                                }
//                                                flourish();
//                                            } else {
//                                                switch(occupyingMapObject.objectType) {
//
//                                                    case BALLISTA:
//                                                        final BallistaActionsPopup bap = new BallistaActionsPopup(game, self, occupyingMapObject);
//                                                        ags.hudStage.addActor(bap);
//                                                        break;
//
//                                                    case DOOR:
//                                                    default:
//                                                        break;
//
//                                                }
//                                            }
//                                        }
//                                    }
//                                    break;
//
//                                case FREE_MOVE:
//                                    if(self.teamAlignment == TeamAlignment.PLAYER) {
//
//                                    }
//                                    break;
//                            }
//
//                            break;
//                        case CUTSCENE:
//                        case MENU_FOCUSED:
//                        case UNIT_SELECTED:
//                        case LOCKED:
//                            break;
//                    }

                }

            };
        }

        public ClickListener propListener(GridProp prop) {
            return new ClickListener() {
                boolean dragged = false;
                boolean clicked = true;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    dragged = true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dragged = false;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    if(dragged) {
                        dragged = false;
                        clicked = false;
                        return;
                    }

                    clicked = true;

//                    for(LogicalTile tile : highlighted) {
//                        tile.clearHighlight();
//                    }
//                    highlighted.clear();

//                    final GridScreen ags = game.activeGridScreen;

//                Gdx.app.log("unit", "touch up, " + ags.getInputMode());

//                    switch(ags.getInputMode()) {
//
//                        case STANDARD:
//                            switch(ags.getMovementControl()) {
//
//                                case COMBAT:
//                                    if(ags.conditions().tickCount() == self.modifiedSimpleSpeed()) {
//                                        // Only move if it's your turn
//                                        if(self.teamAlignment == TeamAlignment.PLAYER) {
//                                            // Unit is player's own unit
//                                            if(!isOccupyingMapObject) {
//                                                ags.setInputMode(GridScreen.InputMode.UNIT_SELECTED);
//                                                ags.activeUnit = self;
//                                                ags.highlightAllTilesUnitCanAccess(self);
//                                                for(SimpleUnit enemy : ags.attackableUnits) {
//                                                    if(enemy.teamAlignment == TeamAlignment.ENEMY || enemy.teamAlignment == TeamAlignment.OTHER) {
//                                                        enemy.addListener(new InputListener() {
//
//                                                            @Override
//                                                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}
//
//                                                            @Override
//                                                            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                                                                ags.setInputMode(GridScreen.InputMode.MENU_FOCUSED);
//                                                                ags.activeUnit = null;
//                                                                ags.removeTileHighlighters();
//                                                                final int originCX = getColumnX();
//                                                                final int originRY = getRowY();
//
//                                                                final RunnableAction finish = new RunnableAction();
//                                                                finish.setRunnable(new Runnable() {
//                                                                    @Override
//                                                                    public void run() {
//                                                                        ags.hud().addPopup(new BattlePreviewPopup(game, self, enemy, originRY, originCX));
//
//                                                                    }
//                                                                });
//
//                                                                ags.getLogicalMap().moveAlongPath(self, ags.getLogicalMap().pathToNearestNeighborInRange(self, enemy.getOccupyingTile()), finish, false);
//
//                                                            }
//
//                                                        });
//
//                                                    }
//                                                }
//                                                flourish();
//                                            } else {
//                                                switch(occupyingMapObject.objectType) {
//
//                                                    case BALLISTA:
//                                                        final BallistaActionsPopup bap = new BallistaActionsPopup(game, self, occupyingMapObject);
//                                                        ags.hudStage.addActor(bap);
//                                                        break;
//
//                                                    case DOOR:
//                                                    default:
//                                                        break;
//
//                                                }
//                                            }
//                                        }
//                                    }
//                                    break;
//
//                                case FREE_MOVE:
//                                    if(self.teamAlignment == TeamAlignment.PLAYER) {
//
//                                    }
//                                    break;
//                            }
//
//                            break;
//                        case CUTSCENE:
//                        case MENU_FOCUSED:
//                        case UNIT_SELECTED:
//                        case LOCKED:
//                            break;
//                    }

                }

            };
        }
    }
}
