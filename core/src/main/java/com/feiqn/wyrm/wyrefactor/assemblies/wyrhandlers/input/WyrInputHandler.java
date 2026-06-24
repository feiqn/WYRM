package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

import static com.badlogic.gdx.Gdx.input;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.SubGenre.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MoveControlMode.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.InputMode.*;

public final class WyrInputHandler extends WyrHandler {

    private InputMode inputMode = STANDARD;
    private WyrActor focusedActor = null;
    private Actor focusedMenu  = null;

    private MoveControlMode moveControlMode = TURN_BASED;
    private SubGenre rpgSubGenre = DIVINE;

    public WyrInputHandler() {}

    public void setMoveControl(MoveControlMode moveControlMode) { this.moveControlMode = moveControlMode; }

    public void setToCombat() { this.setToCombat(true); }
    public void setToCombat(boolean standardize) {
//        if(this.moveControlMode == COMBAT) return;
        this.moveControlMode = TURN_BASED;
        if(standardize) setInputMode(STANDARD);
    }
    public void setFreeMove() { this.setFreeMove(true); }
    public void setFreeMove(boolean standardize) {
//        if(this.moveControlMode == FREE_MOVE) return;
        this.moveControlMode = FREE_MOVE;
        if(standardize) setInputMode(STANDARD);
    }
    public MoveControlMode getMovementControlMode() { return moveControlMode; }

    @Override
    public boolean isBusy() { return super.isBusy() || getInputMode() == MENU_FOCUSED || getInputMode() == ACTOR_FOCUSED; }

    public void setInputMode(InputMode mode) {
        inputMode = mode;
        if(mode != MENU_FOCUSED)  focusedMenu = null;
        if(mode != ACTOR_FOCUSED) focusedActor = null;
    }
    public void focusMenu(Actor focusedMenu) {
        this.focusedMenu = focusedMenu;
        setInputMode(MENU_FOCUSED);
    }
    public void focusActor(WyrActor unit) {
        this.focusedActor = unit;
        inputMode = ACTOR_FOCUSED;
    }
    @Override
    public boolean standardize() {
        clearFocus(true);
        return false;
    }
    public void clearFocus(boolean standardizeInput) {
        focusedMenu = null;
        focusedActor = null;
        if(standardizeInput) inputMode = STANDARD;
    }
    public void lock() {
        inputMode = LOCKED;
    }

    public InputMode getInputMode() { return inputMode; }

    public SubGenre getRPGSubGenre() {
        return rpgSubGenre;
    }

    public void setRPGSubGenre(SubGenre rpgSubGenre) {
        this.rpgSubGenre = rpgSubGenre;
    }

    public static final class Listeners {

//        public static InputMultiplexer tileHighlighterMultiplexer(GridMetaHandler handler, GridTile tile) {
//            final InputMultiplexer returnValue = new InputMultiplexer();
//            returnValue.addProcessor(tileHighlighterListener(handler,tile));
//        }

        public static ClickListener TILE_highlighterRightClick(RPGridTile tile) {
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

                    handlers.hud().displayActionMenuForTile(tile);

                    handlers.hud().clearContextDisplay();
                    handlers.map().standardize();
                    tile.highlight();
                    tile.pulse(true);
                }
            };
        }

        public static ClickListener TILE_highlighterLeftClick(RPGridTile tile) {
            return new ClickListener(Input.Buttons.LEFT) {
                boolean dragged = false;
                boolean clicked = false;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event,x,y,pointer,fromActor);
                    handlers.hud().setContextDisplayTile(tile);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event,x,y,pointer,toActor);
                    if(clicked) return;
                    handlers.hud().clearContextDisplay();
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
                        handlers.interactions().parseInteractable(tile.getAllInteractions().get(0));
                    } else {
                        int uniqueEntities = 0;
                        WyrInteraction choice = null;
                        for(WyrInteraction interaction : tile.getAllInteractions()) {
                            if(interaction.getInteractType() == InteractionType.MOVE_WAIT) {
                                uniqueEntities++;
                                choice = interaction;
                            }
                        }
                        if(uniqueEntities == 1) {
                            handlers.interactions().parseInteractable(choice);
                            return;
                        }
                        handlers.hud().displayActionMenuForTile(tile);
                        tile.highlight();
                    }
                }
            };
        }

        public static ClickListener HUD_actionMenuLabel(WyrInteraction interaction) {
            return new ClickListener() {
                boolean dragged = false;
                boolean clicked = false;
                boolean spotLightingPath = false;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event,x,y,pointer,fromActor);

                    // glow label & highlight associated actors

                     if(interaction.getPath() != null) {
                         spotLightingPath = true;
                         handlers.map().spotlightPath(interaction.getPath());
                         // I'm not sure how you'd ever get to this point as anything
                         // other than the Player team, so shading seems unnecessary.
                         interaction.getSubject().applyShader(ShaderState.HIGHLIGHT);
                     }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event,x,y,pointer,toActor);
                    if(!spotLightingPath) return;
                    spotLightingPath = false;
                    handlers.map().restoreAllHighlights();
//                    for(GridTile t : interaction.getPath().getTiles()) {
//                        t.shadeHighlight(ShaderState.STANDARD, TeamAlignment.PLAYER);
//                    }

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
                    handlers.hud().standardize();
                    handlers.interactions().parseInteractable(interaction);

                }
            };
        }

        public static DragListener MAP_drag(WyrScreen RPGridScreen) {
            return new DragListener() {
                final Vector3 tp = new Vector3();
                boolean dragged = false;
                boolean clicked = false;

                @Override
                public boolean mouseMoved (InputEvent event, float x, float y) {
                    try {
//                        if(OLDInputMode == OLD_GridScreen.OLD_InputMode.STANDARD ||
//                            OLDInputMode == OLD_GridScreen.OLD_InputMode.UNIT_SELECTED ||
//                            OLDInputMode == OLD_GridScreen.OLD_InputMode.MENU_FOCUSED) {

                            handlers.camera().actual().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                            handlers.hud().setTileContext(handlers.map().tileAt((int) tp.x, (int) tp.y));

                    } catch (Exception ignored) {}
                    return false;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dragged = false;

                    switch(handlers.input().getInputMode()) {
                        case STANDARD:
                        case ACTOR_FOCUSED:
                        case MENU_FOCUSED:
                            clicked = true;
                            return true;
                        case CUTSCENE:
                        case LOCKED:
                        default:
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

                    switch(handlers.input().getInputMode()) {
                        case ACTOR_FOCUSED:
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
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    if(handlers.input().getInputMode() == STANDARD ||
                        handlers.input().getInputMode() == ACTOR_FOCUSED ||
                        handlers.input().getInputMode() == MENU_FOCUSED) {

                        dragged = true;

                        final float x = input.getDeltaX() * .05f; // TODO: variable drag speed setting can be injected here
                        final float y = input.getDeltaY() * .05f; // TODO: when you zoom in, make the drag slower


                        handlers.camera().translate(-x, y);
                    }
                }
            };
        }

        public static InputAdapter MAP_scroll() {
            return new InputAdapter() {
                @Override
                public boolean scrolled(float amountX, float amountY) {
                    switch(handlers.input().getInputMode()) {
                        case STANDARD:
                        case ACTOR_FOCUSED:
                        case MENU_FOCUSED:
                            float zoomChange = 0.05f * amountY; // Adjust zoom
                            handlers.camera().actual().zoom = Math.max(0.2f, Math.min(handlers.camera().actual().zoom + zoomChange, 1.05f));
                            handlers.camera().actual().update();
                            return true;
                        case CUTSCENE:
                        case LOCKED:
                        default:
                            return false;
                    }
                }
            };
        }

        public static ClickListener UNIT_enemyLeftClick(WyrActor.Unit enemyUnit) {
            return new ClickListener() {
                boolean dragged = false;
                private boolean clicked = false;
                private boolean spotlighting = false;
                private GridPathfinder.Things checkedThings;
                private int turnChecked = -1;
                private int tickChecked = -1;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    switch(handlers.input().moveControlMode) {
                        case TURN_BASED:
                        case FREE_MOVE:
                            switch(handlers.input().getInputMode()) {
                                case STANDARD:
                                    if(!enemyUnit.canMoveOrAct()) return;
                                    spotlighting = true;
                                    // TODO:
                                    //  - implement commented out optimization correctly
                                    //  - update checkedThings after any unit moves
//                                    if(handlers.register().turnCount() != turnChecked && handlers.register().tickCount() != tickChecked) {
                                        checkedThings = GridPathfinder.currentlyAccessibleTo(enemyUnit);
//                                        turnChecked = handlers.register().turnCount();
//                                        tickChecked = handlers.register().tickCount();
//                                    }
                                    handlers.clearEphemeral();
                                    for(RPGridTile t : checkedThings.tiles().keySet()) {
                                        t.highlight();
                                        t.shadeHighlight(ShaderState.STANDARD,TeamAlignment.ENEMY);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(!spotlighting) return;
                    handlers.priority().parsePriority();
                }

                @Override
                public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                    dragged = true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dragged = false;
                    switch(handlers.input().getInputMode()) {
                        case STANDARD:
                        case ACTOR_FOCUSED:
                        case MENU_FOCUSED:
                            return true;
                        case CUTSCENE:
                        case LOCKED:
                        default:
                            return false;
                    }
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int point, int button)  {
                    if(dragged || !enemyUnit.canMoveOrAct()) {
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

        public static ClickListener UNIT_playerLeftClick(WyrActor.Unit playerUnit) {
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
                    switch(handlers.input().getInputMode()) {
                        case STANDARD:
                        case ACTOR_FOCUSED:
                        case MENU_FOCUSED:
                            return true;
                        case CUTSCENE:
                        case LOCKED:
                        default:
                            return false;
                    }

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



                    switch(handlers.input().getInputMode()) {

                        case STANDARD:
                            switch(handlers.input().getMovementControlMode()) {
                                case TURN_BASED:
                                    if(handlers.priority().unitsHoldingPriority().contains(playerUnit, true)) {
                                        if(handlers.priority().getFocusedActor() == playerUnit) {
                                            // Already focused, clicked because player wants to stay on same tile.
                                            handlers.hud().setActionMenuContext(playerUnit.getOccupiedTile(), playerUnit);
                                            handlers.hud().displayModalActionMenu();
                                            handlers.map().hideAllHighlights();
                                        } else {
                                            // Focus on me.
                                            handlers.priority().parsePriority(playerUnit);
                                        }
                                    } else {
                                        // Not my turn.
                                        return;
                                    }

                                case FREE_MOVE:
                                    // todo: wasd exploration
                                    break;
                            }

                        case LOCKED:
                        default:
                            break;
                    }

                }
            };
        }

        public static ClickListener PROP_leftClick(WyrActor prop) {
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
