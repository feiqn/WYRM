package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.menus.popups.FieldActionsPopup;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.Direction;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions.GridMoveInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public class GridActorHandler extends WyrActorHandler {

    // props
    // units

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridActorHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
    }

    public void startCombat(GridUnit attacker, GridUnit defender) {
        // TODO:
        //  Talk to CombatHandler to initiate combat
    }

    public void placeActor(GridActor actor, GridTile tile) {
        this.placeActor(actor, tile.getCoordinates());
    }
    public void placeActor(GridActor actor, Vector2 coordinates) {
        this.placeActor(actor, (int)coordinates.x, (int)coordinates.y);
    }
    public void placeActor(GridActor actor, int x, int y) {
        switch(actor.getActorType()) {
            case UNIT:
                h.map().tileAt(x, y).occupy((GridUnit) actor);
                // TODO: check area cutscene trigger
                break;

            case PROP:
                h.map().tileAt(x,y).setProp((GridProp) actor);
                break;
        }
        actor.setPosByGrid(x, y);
//        actor.setPosition((x + .5f) - (actor.getWidth() * .5f), y);

    }

    public void moveThenWait(GridActor actor, GridPath path) {
        // moveAlongPath

        final SequenceAction movementSequence = new SequenceAction();
        Direction nextDirection = null;

        for(int i = 0; i < path.length(); i++) {

            switch(h.map().directionFromTileToTile(path.getPath().get(i), path.getPath().get(i+1))) {
                case NORTH:
                    nextDirection = Direction.NORTH;
                    break;
                case SOUTH:
                    nextDirection = Direction.SOUTH;
                    break;
                case EAST:
                    nextDirection = Direction.EAST;
                    break;
                case WEST:
                    nextDirection = Direction.WEST;
                    break;
            }

            final RunnableAction changeDirection = new RunnableAction();
            final Direction decidedNextDirection = nextDirection;

            changeDirection.setRunnable(new Runnable() {
                @Override
                public void run() {
                    switch(decidedNextDirection) {
                        case NORTH:
                            actor.setAnimationState(WyrAnimator.AnimationState.FACING_NORTH);
                            break;
                        case SOUTH:
                            actor.setAnimationState(WyrAnimator.AnimationState.FACING_SOUTH);
                            break;
                        case WEST:
                            actor.setAnimationState(WyrAnimator.AnimationState.FACING_WEST);
                            break;
                        case EAST:
                            actor.setAnimationState(WyrAnimator.AnimationState.FACING_EAST);
                            break;
                    }
                }
            });

            movementSequence.addAction(changeDirection);

            final MoveToAction move = new MoveToAction();
            move.setPosition((path.getPath().get(i).getXColumn() + .5f) - (actor.getWidth() * .5f), path.getPath().get(i).getYRow());
            move.setDuration(.15f);

            movementSequence.addAction(move);
        }

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                placeActor(actor, path.lastTile().getYRow(), path.lastTile().getXColumn());

                if(actor.actorType == GridActor.ActorType.UNIT) {
                    assert actor instanceof GridUnit;
                    final GridUnit unit = (GridUnit) actor;
                    if(unit.teamAlignment() == TeamAlignment.PLAYER) {

                        // TODO: generate and open FAP via HUD

//                        final FieldActionsPopup fap = new FieldActionsPopup(game, unit, originRow, originColumn);
//                        game.activeOLDGridScreen.setInputMode(OLD_GridScreen.OLD_InputMode.MENU_FOCUSED);
//                        game.activeOLDGridScreen.hud().addPopup(fap);

                    } else {
                        unit.setAnimationState(WyrAnimator.AnimationState.IDLE);
                        unit.stats().consumeAP();
//                        game.activeOLDGridScreen.finishExecutingAction();
                        h.conditions().parsePriority();
                    }
                }

            }
        });

        actor.addAction(Actions.sequence(movementSequence, finishMoving));

    }

    public void moveThenAttack(GridActor actor, GridPath path, GridUnit target) {}

    public void moveThenInteract(GridActor actor, GridPath path) {} // props

    private void followPath(GridActor actor, GridPath path) {
        Gdx.app.log("Actors()", "You'd like me to move, yes.");
        // TODO
        //  - move
        //  - parse player / cp
        //  - fap / cp action
    }

    public void parseInteractable(GridInteraction interactable) {
        switch(interactable.getInteractType()) {
            case MOVE:
                moveThenWait(interactable.getParent(), ((GridMoveInteraction)interactable).getPath());
                break;
            case ATTACK:

            default:
                break;
        }
        for(GridTile tile : h.map().getAllTiles()) {
            tile.clearInteractables();
        }
    }

}
