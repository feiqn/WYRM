package com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworld.RPGridScreen;

import static com.badlogic.gdx.Gdx.input;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.RPGridInputHandler.InputMode.*;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.RPGridInputHandler.MovementControl.*;

public final class RPGridInputHandler extends WyrInputHandler {

    public enum InputMode {
        STANDARD,
        UNIT_SELECTED,
        MENU_FOCUSED,
        LOCKED,
        CUTSCENE,
    }

    public enum MovementControl {
        FREE_MOVE,
        COMBAT,
    }

    public enum RPGMode {
        DIVINE,
        IRON,
        LEGENDARY,
    }

    private InputMode       inputMode       = STANDARD;
    private MovementControl movementControl = COMBAT;
    private RPGMode         rpgMode         = RPGMode.DIVINE;

    private RPGridUnit selectedUnit = null;
    private Actor      focusedMenu  = null;

    private final RPGridMetaHandler h; // It's fun to just type "h".

    public RPGridInputHandler(RPGridMetaHandler metaHandler) {
        this.h = metaHandler;
    }

    public void focusMenu(Actor focusedMenu) {
        this.focusedMenu = focusedMenu;
        inputMode = MENU_FOCUSED;
    }

    public void focusUnit(RPGridUnit unit) {
        this.selectedUnit = unit;
        inputMode = UNIT_SELECTED;
    }

    public void clearFocus(boolean standardizeInput) {
        focusedMenu = null;
        selectedUnit = null;
        if(standardizeInput) inputMode = STANDARD;
    }

    public void setInputMode(InputMode mode) {
        inputMode = mode;
        if(mode != MENU_FOCUSED) focusedMenu = null;
        if(mode != UNIT_SELECTED) selectedUnit = null;
    }

    public void setMovementControl(MovementControl movementControl) { this.movementControl = movementControl; }

    public void setToCombat() { this.setToCombat(true); }
    public void setToCombat(boolean standardize) {
        if(this.movementControl == COMBAT) return;
        this.movementControl = COMBAT;
        if(standardize) this.inputMode = STANDARD;
    }

    public void setFreeMove() { this.setFreeMove(true); }
    public void setFreeMove(boolean standardize) {
        if(this.movementControl == FREE_MOVE) return;
        this.movementControl = FREE_MOVE;
        if(standardize) this.inputMode = STANDARD;
    }

    public InputMode getInputMode() { return inputMode; }
    public MovementControl getMovementControlMode() { return movementControl; }
    @Override
    public boolean isBusy() { return isBusy || inputMode == MENU_FOCUSED || inputMode == UNIT_SELECTED; }

    public static final class Listeners {

//        public static InputMultiplexer tileHighlighterMultiplexer(GridMetaHandler handler, GridTile tile) {
//            final InputMultiplexer returnValue = new InputMultiplexer();
//            returnValue.addProcessor(tileHighlighterListener(handler,tile));
//        }

        public static ClickListener TILE_highlighterRightClick(RPGridMetaHandler handler, GridTile tile) {
            return new ClickListener(Input.Buttons.RIGHT) {
                boolean dragged = false;
                boolean clicked = false;

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    super.touchDragged(event,screenX,screenY, pointer);
                    dragged = true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event,x,y,pointer,button);
                    dragged = false;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    super.touchUp(event,x,y,point,button);
//                    if (dragged) {
//                        dragged = false;
//                        clicked = false;
//                        return;
//                    }

                    if(button != this.getButton()) return;

                    clicked = true;

                    if(tile.getAllInteractions().size == 0) return;

                    handler.hud().displayActionMenuForTile(tile);

                    handler.hud().clearContextDisplay();
                    handler.map().clearAllHighlights();
                    tile.highlight();
                    tile.pulse(true);
                }
            };
        }

        public static ClickListener TILE_highlighterLeftClick(RPGridMetaHandler handler, GridTile tile) {
            return new ClickListener(Input.Buttons.LEFT) {
                boolean dragged = false;
                boolean clicked = false;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event,x,y,pointer,fromActor);
                    handler.hud().setContextDisplayTile(tile);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event,x,y,pointer,toActor);
                    if(clicked) return;
                    handler.hud().clearContextDisplay();
                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    super.touchDragged(event,screenX,screenY,pointer);
                    dragged = true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    dragged = false;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button) {
                    super.touchUp(event, x, y, point, button);

//                    if (dragged) {
//                        dragged = false;
//                        clicked = false;
//                        return;
//                    }

                    if(button != this.getButton()) return;

                    clicked = true;

                    if(tile.getAllInteractions().size == 0) return;

                    if (tile.getAllInteractions().size == 1) {
                        handler.interactions().parseInteractable(tile.getAllInteractions().get(0));
                    } else {
                        for(RPGridInteraction interaction : tile.getAllInteractions()) {
                            if(interaction.getInteractType() == RPGridInteraction.GridInteractID.MOVE_WAIT) {
                                handler.interactions().parseInteractable(interaction);
                                return;
                            }
                        }
                        handler.hud().displayActionMenuForTile(tile);
                        tile.highlight();
                    }
                }
            };
        }

        public static ClickListener HUD_actionMenuLabel(RPGridMetaHandler handler, RPGridInteraction interaction) {
            return new ClickListener() {
                boolean dragged = false;
                boolean clicked = false;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event,x,y,pointer,fromActor);

                    // glow label & highlight associated actors

//                    switch(interaction.getInteractType()) {
//                        case MOVE:
//                            handler.map().clearAllHighlights();
//                            final GridMoveInteraction gmi = (GridMoveInteraction) interaction;
//                            for(GridTile t : gmi.getPath().getPath()) {
//                                t.highlight(false);
//                            }
//                            break;
//                        case WAIT:
//                            break;
//                    }

                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event,x,y,pointer,toActor);
                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    super.touchDragged(event,screenX,screenY,pointer);
                    dragged = true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event,x,y,pointer,button);
                    dragged = false;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    super.touchUp(event,x,y,point,button);
//                    if(dragged) {
//                        dragged = false;
//                        clicked = false;
//                        return;
//                    }
                    // TODO: better drag detection

                    clicked = true;

                    // clear hud,
                    // pass interaction to actor handler,
                    handler.hud().standardize();
                    handler.interactions().parseInteractable(interaction);

                }
            };
        }

        public static DragListener MAP_drag(RPGridMetaHandler handler, RPGridScreen RPGridScreen) {
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
                            RPGridScreen.getGameStage().getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

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

                            RPGridScreen.getGameStage().getCamera().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

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

                        final float x = input.getDeltaX() * .12f; // TODO: variable drag speed setting can be injected here
                        final float y = input.getDeltaY() * .12f; // TODO: when you zoom in, make the drag slower


                        handler.camera().translate(-x, y);
                    }
                }
            };
        }

        public static InputAdapter MAP_scroll(RPGridMetaHandler handler) {
            return new InputAdapter() {
                @Override
                public boolean scrolled(float amountX, float amountY) {
                    final InputMode mode = handler.input().getInputMode();

                    if(mode == STANDARD ||
                       mode == UNIT_SELECTED ||
                       mode == InputMode.MENU_FOCUSED) {

                        float zoomChange = 0.05f * amountY; // Adjust zoom
                        handler.camera().actual().zoom = Math.max(0.2f, Math.min(handler.camera().actual().zoom + zoomChange, 1.05f));
                        handler.camera().actual().update();
                        return true;
                    } else {
                        return false;
                    }
                }
            };
        }

        public static ClickListener UNIT_enemyLeftClick(RPGridMetaHandler handler, RPGridUnit enemyUnit) {
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

        public static ClickListener UNIT_playerLeftClick(RPGridMetaHandler handler, RPGridUnit playerUnit) {
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

                    // TODO: discrete behavior here

//                    if(dragged
//                        || !playerUnit.canMove()
//                        || !handler.priority().unitsHoldingPriority().contains(playerUnit, true)
//                        || (handler.input().inputMode != STANDARD && (handler.input().inputMode == UNIT_SELECTED && handler.input().selectedUnit != playerUnit))) {
//                            dragged = false;
//                            clicked = false;
//                            return;
//                    }

                    clicked = true;

                    switch(handler.input().getInputMode()) {

                        case STANDARD:
                            switch(handler.input().getMovementControlMode()) {
                                case COMBAT:
                                    switch(handler.priority().unitsHoldingPriority().size) {
                                        case 0:
                                            return;
                                        case 1:
                                            if(handler.priority().unitsHoldingPriority().get(0) == playerUnit) {
//                                                handler.hud().setActionMenuContext(playerUnit.getOccupiedTile(), playerUnit);
//                                                handler.hud().displayModalActionMenu();
                                            } else {
                                                return;
                                            }
                                            break;
                                        default:
//                                            handler.priority().prioritizeUnit(playerUnit);
                                            break;
                                    }

                                case FREE_MOVE:
                                    break;
                            }

                        case LOCKED:
                        default:
                            break;
                    }

                }
            };
        }

        public static ClickListener PROP_leftClick(RPGridProp prop) {
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
