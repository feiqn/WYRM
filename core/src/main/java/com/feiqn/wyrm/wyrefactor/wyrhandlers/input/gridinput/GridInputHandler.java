package com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridInputHandler extends WyrInputHandler {

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

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridInputHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
        inputMode = InputMode.STANDARD;
        movementControl = MovementControl.COMBAT;
    }

    public void setInputMode(InputMode mode) { inputMode = mode; }
    public void setMovementControl(MovementControl movementControl) { this.movementControl = movementControl; }

    public InputMode getInputMode() { return inputMode; }


    public static final class GridListeners {

        public static InputAdapter mapScrollListener(GridMetaHandler h) {
            return new InputAdapter() {
                @Override
                public boolean scrolled(float amountX, float amountY) {
                    final InputMode mode = h.inputs().getInputMode();

                    if(mode == InputMode.STANDARD ||
                       mode == InputMode.UNIT_SELECTED ||
                       mode == InputMode.MENU_FOCUSED) {

                        float zoomChange = 0.1f * amountY; // Adjust zoom
                        h.camera().camera().zoom = Math.max(0.2f, Math.min(h.camera().camera().zoom + zoomChange, 1.25f));
                        h.camera().camera().update();
                        return true;
                    } else {
                        return false;
                    }
                }
            };
        }

        public static ClickListener enemyUnitListener(GridUnit enemyUnit) {
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

        public static ClickListener playerUnitListener(GridUnit playerUnit) {
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

        public static ClickListener propListener(GridProp prop) {
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
