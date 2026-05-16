package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.RPGridScreen;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.MoveControlMode;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.RPGMode;

import static com.badlogic.gdx.Gdx.input;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.MoveControlMode.COMBAT;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.MoveControlMode.FREE_MOVE;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.InputMode.*;

public final class RPGridInputHandler extends WyrInputHandler {

    private MoveControlMode moveControlMode = COMBAT;
    private RPGMode         rpgMode         = RPGMode.DIVINE;

    public RPGridInputHandler(RPGridMetaHandler metaHandler) {
        super(metaHandler);
    }

    public void setMoveControl(MoveControlMode moveControlMode) { this.moveControlMode = moveControlMode; }

    public void setToCombat() { this.setToCombat(true); }
    public void setToCombat(boolean standardize) {
//        if(this.moveControlMode == COMBAT) return;
        this.moveControlMode = COMBAT;
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
    public boolean isBusy() { return isBusy || getInputMode() == MENU_FOCUSED || getInputMode() == ACTOR_FOCUSED; }
    @Override
    public RPGridMetaHandler h() {
        assert super.h() instanceof RPGridMetaHandler;
        return (RPGridMetaHandler) super.h();
    }

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
                    handler.map().standardize();
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
                        int uniqueEntities = 0;
                        RPGridInteraction choice = null;
                        for(RPGridInteraction interaction : tile.getAllInteractions()) {
                            if(interaction.getInteractType() == RPGridInteraction.RPGridInteractType.MOVE_WAIT) {
//                                handler.interactions().parseInteractable(interaction);
//                                return;
                                uniqueEntities++;
                                choice = interaction;
                            }
                        }
                        if(uniqueEntities == 1) {
                            handler.interactions().parseInteractable(choice);
                            return;
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
                boolean spotLightingPath = false;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event,x,y,pointer,fromActor);

                    // glow label & highlight associated actors

                     if(interaction.getPath() != null) {
                         spotLightingPath = true;
                         handler.map().spotlightPath(interaction.getPath());
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
                    handler.map().restoreAllHighlights();
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
                public boolean mouseMoved (InputEvent event, float x, float y) {
                    try {
//                        if(OLDInputMode == OLD_GridScreen.OLD_InputMode.STANDARD ||
//                            OLDInputMode == OLD_GridScreen.OLD_InputMode.UNIT_SELECTED ||
//                            OLDInputMode == OLD_GridScreen.OLD_InputMode.MENU_FOCUSED) {

                            handler.camera().actual().unproject(tp.set((float) (double) input.getX(), (float) (double) input.getY(), 0));

                            handler.hud().setTileContext(handler.map().tileAt((int) tp.x, (int) tp.y));

                    } catch (Exception ignored) {}
                    return false;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    dragged = false;

                    switch(handler.input().getInputMode()) {
                        case STANDARD:
                        case MENU_FOCUSED:
                        case ACTOR_FOCUSED:
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

                    switch(handler.input().getInputMode()) {
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
                    if(handler.input().getInputMode() == STANDARD ||
                        handler.input().getInputMode() == ACTOR_FOCUSED ||
                        handler.input().getInputMode() == MENU_FOCUSED) {

                        dragged = true;

                        final float x = input.getDeltaX() * .05f; // TODO: variable drag speed setting can be injected here
                        final float y = input.getDeltaY() * .05f; // TODO: when you zoom in, make the drag slower


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
                       mode == ACTOR_FOCUSED ||
                       mode == MENU_FOCUSED) {

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
                private boolean clicked = false;
                private boolean spotlighting = false;
                private GridPathfinder.Things checkedThings;
                private int turnChecked = -1;
                private int tickChecked = -1;

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    switch(handler.input().moveControlMode) {
                        case COMBAT:
                        case FREE_MOVE:
                            switch(handler.input().getInputMode()) {
                                case STANDARD:
                                    if(!enemyUnit.canMove()) return;
                                    spotlighting = true;
                                    // TODO: update checkedThings after any unit moves
                                    if(handler.register().turnCount() != turnChecked && handler.register().tickCount() != tickChecked) {
                                        checkedThings = GridPathfinder.currentlyAccessibleTo(handler.map(), enemyUnit);
                                        turnChecked = handler.register().turnCount();
                                        tickChecked = handler.register().tickCount();
                                    }
                                    handler.map().hideAllHighlights();
                                    for(GridTile t : checkedThings.tiles().keySet()) {
                                        t.highlight();
                                        t.unhideHighlight();
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
                    handler.priority().parsePriority();
//                    for(GridTile t : checkedThings.tiles().keySet()) {
//                        t.shadeHighlight(ShaderState.STANDARD, TeamAlignment.PLAYER);
//                        t.
//                    }
//                    handler.map().restoreAllHighlights();
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
                                    if(handler.priority().unitsHoldingPriority().contains(playerUnit, true)) {
                                        if(handler.priority().getFocusedActor() == playerUnit) {
                                            // Already focused, clicked because player wants to stay on same tile.
                                            handler.hud().setActionMenuContext(playerUnit.getOccupiedTile(), playerUnit);
                                            handler.hud().displayModalActionMenu();
                                            handler.map().hideAllHighlights();
                                        } else {
                                            // Focus on me.
                                            handler.priority().parsePriority(playerUnit);
                                        }
                                    } else {
                                        // Not my turn.
                                        return;
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
