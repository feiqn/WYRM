package com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

import static com.badlogic.gdx.Gdx.input;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler.InputMode.*;

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

    private InputMode inputMode;
    private MovementControl movementControl;

    private GridUnit selectedUnit = null;
    private Actor focusedMenu     = null;

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridInputHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
        inputMode = STANDARD;
        movementControl = MovementControl.COMBAT;
    }

    public void focusMenu(Actor focusedMenu) {
        this.focusedMenu = focusedMenu;
        inputMode = MENU_FOCUSED;
    }

    public void setInputMode(InputMode mode) {
        inputMode = mode;
        if(mode != MENU_FOCUSED) focusedMenu = null;
        if(mode != UNIT_SELECTED) selectedUnit = null;

    }
    public void setMovementControl(MovementControl movementControl) { this.movementControl = movementControl; }

    public InputMode getInputMode() { return inputMode; }
    public MovementControl getMovementControlMode() { return movementControl; }

    public static final class GridListeners {

        public static ClickListener HUD_actionMenuLabelListener(GridMetaHandler handler, GridInteraction interaction) {
            return new ClickListener() {
                boolean dragged = false;
                boolean clicked = false;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    // glow label & highlight associated actors
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


                    // clear hud,
                    // pass interaction to actor handler,

                    clicked = true;

                }

            };
        }
        public static DragListener mapDragListener(GridMetaHandler handler, GridScreen gridScreen) {
            return new DragListener() {
                final Vector3 tp = new Vector3();
                boolean dragged = false;
                boolean clicked = false;

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dragged = false;

                    switch(handler.input().inputMode) {
                        case STANDARD:
                        case MENU_FOCUSED:
                        case UNIT_SELECTED:
                            clicked = true;
                            return true;
                        default:
                            clicked = false;
                            return false;
                    }
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    if(dragged) {
                        dragged = false;
                        clicked = false;
                        return;
                    }

                    switch(handler.input().inputMode) {
                        case UNIT_SELECTED:
                            gridScreen.getGameStage().getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                            // TODO:
                            //  The following used to clear out tile highlighters and reset selected unit
                            //  if the clicked area was outside of the highlighted range.
                            //  Need to consider what to do with this functionality, if anything.

//                            if(!reachableTiles.contains(logicalMap.getTileAtPositionXY((int) tp.x, (int) tp.y), true)) {
//                                removeTileHighlighters();
//                                activeUnit.idle();
//                                activeUnit = null;
//                                clearAttackableEnemies();
//                                setInputMode(OLD_GridScreen.OLD_InputMode.STANDARD);
//                                hud().reset();
//                            }
                            break;

                        case STANDARD:
                        case MENU_FOCUSED:
                        default:
                            break;
                    }


                }

                @Override
                public boolean mouseMoved (InputEvent event, float x, float y) {
                    try {
                        if(handler.input().inputMode == STANDARD ||
                            handler.input().inputMode == UNIT_SELECTED ||
                            handler.input().inputMode == InputMode.MENU_FOCUSED) {

                            gridScreen.getGameStage().getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                            // TODO:
                            //  this should call to Unified Info, currently its updating Context

//                            handler.hud().setTileContext(handler.map().tileAt((int) tp.x, (int) tp.y));

                        }
                    } catch (Exception ignored) {}
                    return false;
                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    if(handler.input().inputMode == STANDARD ||
                        handler.input().inputMode == UNIT_SELECTED ||
                        handler.input().inputMode == InputMode.MENU_FOCUSED) {

                        dragged = true;

                        final float x = input.getDeltaX() * .05f; // TODO: variable drag speed setting can be injected here
                        final float y = input.getDeltaY() * .05f; // TODO: when you zoom in, make the drag slower


                        handler.camera().translate(-x, y);
                    }
                }
            };
        }

        public static InputAdapter mapScrollListener(GridMetaHandler handler) {
            return new InputAdapter() {
                @Override
                public boolean scrolled(float amountX, float amountY) {
                    final InputMode mode = handler.input().getInputMode();

                    if(mode == STANDARD ||
                       mode == UNIT_SELECTED ||
                       mode == InputMode.MENU_FOCUSED) {

                        float zoomChange = 0.1f * amountY; // Adjust zoom
                        handler.camera().camera().zoom = Math.max(0.2f, Math.min(handler.camera().camera().zoom + zoomChange, 1.25f));
                        handler.camera().camera().update();
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
                boolean clicked = false;

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
                boolean clicked = false;

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
                boolean clicked = false;

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
