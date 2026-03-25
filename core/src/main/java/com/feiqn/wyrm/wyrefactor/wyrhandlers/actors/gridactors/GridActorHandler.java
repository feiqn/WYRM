package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.Direction;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
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
                actor.occupy(h.map().tileAt(x, y));
                if(h.map().tileAt(x,y).occupier() != actor) {
                    Gdx.app.log("placeActor", "ERROR: invalid occupier at destination tile.");
                }

                // TODO: check area cutscene trigger
                break;

            case PROP:
                h.map().tileAt(x,y).setProp((GridProp) actor);
                break;

            default:
                Gdx.app.log("placeActor", "ERROR: invalid ActorType.");
        }
        actor.setPosByGrid(x, y);

//        Gdx.app.log("placeActor", "Attempted to place " + actor.getActorType() + " at " + x + " " + y);
//        Gdx.app.log("placeActor", "Actual: " + actor.occupiedTile.getXColumn() + " " + actor.occupiedTile.getYRow());

        if(h.map().tileAt(x, y).occupier() != actor) {
            Gdx.app.log("placeActor", "ERROR: wrong actor at tile!.");
        }
        if(actor.occupiedTile != h.map().tileAt(x, y)) {
            Gdx.app.log("placeActor", "ERROR: wrong tile for actor.");
        }

    }

    public void moveThenWait(GridActor actor, GridPath path) {
        // moveAlongPath
        final SequenceAction movementSequence = animatedPathingSequence(actor, path);

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                placeActor(actor, path.lastTile().getXColumn(), path.lastTile().getYRow());

                if(actor.actorType == GridActor.ActorType.UNIT) {
                    assert actor instanceof GridUnit;
                    final GridUnit unit = (GridUnit) actor;
                    if(unit.getTeamAlignment() == TeamAlignment.PLAYER) {

//                        Gdx.app.log("moveThenWait", "unit is player");

                        // generate and open Action Menu via HUD
                        h.hud().setActionMenuContext(path.lastTile(), unit);
                        h.hud().displayModalActionMenu();

                    } else {

//                        Gdx.app.log("moveThenWait", "unit is not player");

                        unit.setAnimationState(WyrAnimator.AnimationState.IDLE);
                        unit.stats().spendAP();
                        h.conditions().invalidatePriority();
                    }
                } // TODO: props, bullets?

                h.camera().stopFollowing();
            }
        });

        h.camera().follow(actor);
        actor.addAction(Actions.sequence(movementSequence, finishMoving));

    }

    public void moveThenAttack(GridActor actor, GridPath path, GridUnit target) {}

    public void moveThenInteract(GridActor actor, GridPath path) {} // props

    private SequenceAction animatedPathingSequence(GridActor actor, GridPath path) {
        final SequenceAction movementSequence = new SequenceAction();

        for(int i = 0; i < path.length(); i++) {

            final Direction nextDirection;

            if(i == 0) {
                nextDirection = h.map().directionFromTileToTile(actor.occupiedTile, path.getPath().get(0));
            } else if(i != path.length() - 1) {
                nextDirection = h.map().directionFromTileToTile(path.getPath().get(i-1), path.getPath().get(i));
            } else if(i == path.length() - 1) {
                nextDirection = h.map().directionFromTileToTile(path.getPath().get(i-1), path.lastTile());
            } else {
                nextDirection = Direction.SOUTH;
            }

            final RunnableAction changeDirection = new RunnableAction();
            Direction finalNextDirection = nextDirection;

            changeDirection.setRunnable(new Runnable() {
                @Override
                public void run() {
                    switch(finalNextDirection) {
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

            final MoveByAction moveBy = new MoveByAction();
            switch(nextDirection) {
                case NORTH:
                    moveBy.setAmount(0, 1);
                    break;
                case SOUTH:
                    moveBy.setAmount(0, -1);
                    break;
                case EAST:
                    moveBy.setAmount(1, 0);
                    break;
                case WEST:
                    moveBy.setAmount(-1, 0);
                    break;
                default:
                    break;
            }
            moveBy.setDuration(.175f);

            final ParallelAction animation = new ParallelAction();
            animation.addAction(changeDirection);
            animation.addAction(moveBy);
            movementSequence.addAction(animation);
        }
        return movementSequence;
    }

    public void parseInteractable(GridInteraction interactable) {

        h.input().setInputMode(GridInputHandler.InputMode.LOCKED);

        switch(interactable.getInteractType()) {

            case MOVE:
                moveThenWait(interactable.getParent(), ((GridMoveInteraction)interactable).getPath());
                break;

            case ATTACK:
                break;

            case WAIT:
                switch(interactable.getParent().getActorType()) {
                    case UNIT:
                        final GridUnit subjectUnit = (GridUnit)interactable.getParent();

                        if(subjectUnit == null) {
                            Gdx.app.log("parseInteractable", "ERROR: no subject for Wait action");
                            return;
                        }

                        subjectUnit.stats().spendAP();
                        subjectUnit.setAnimationState(WyrAnimator.AnimationState.IDLE);

                        placeActor(subjectUnit, subjectUnit.occupiedTile);

                        h.input().setInputMode(GridInputHandler.InputMode.STANDARD);
                        h.map().clearAllHighlights();
                        h.hud().standardize();
                        h.conditions().invalidatePriority();
                        break;

                    case PROP:
                        break;

                    default:
                        Gdx.app.log("parseInteractable", "ERROR: invalid ActorType");
                        break;
                }

            default:
                break;
        }
    }

}
