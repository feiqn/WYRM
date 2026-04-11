package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.grid;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.Direction;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteractionHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat.GridCombatSequences;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator.AnimationState.*;

public final class GridInteractionHandler extends WyrInteractionHandler<GridInteraction> {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridInteractionHandler(GridMetaHandler metaHandler) {
        this.h = metaHandler;
    }

    private void moveThenWait(GridActor actor, GridPath path) {
        final SequenceAction movementSequence = animatedPathingSequence(actor, path);

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                h.map().placeActor(actor, path.lastTile().getXColumn(), path.lastTile().getYRow());

                if(actor.getActorType() == GridActor.ActorType.UNIT) {
                    assert actor instanceof GridUnit;
                    final GridUnit unit = (GridUnit) actor;
                    if(unit.getTeamAlignment() == TeamAlignment.PLAYER) {
                        // generate and open Action Menu via HUD
                        h.hud().setActionMenuContext(path.lastTile(), unit);
                        h.hud().displayModalActionMenu();
                    } else {
                        unit.setAnimationState(IDLE);
                        unit.stats().spendAP();
                        h.priority().invalidatePriority();
                    }
                } // TODO: props

                h.camera().stopFollowing();
            }
        });

        h.camera().follow(actor);
        actor.addAction(Actions.sequence(movementSequence, finishMoving));
    }

    private void moveThenAttack(GridActor attacker, GridPath path, GridActor target) {
        final SequenceAction movementSequence = animatedPathingSequence(attacker, path);

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                h.map().placeActor(attacker, path.lastTile().getXColumn(), path.lastTile().getYRow());
                attack(attacker, target);
            }
        });

        h.camera().follow(attacker);

        attacker.addAction(Actions.sequence(
            movementSequence,
            finishMoving)
        );
    }

    private void moveThenInteract(GridActor actor, GridPath path) {} // props

    private void attack(GridActor attacker, GridActor defender) {
        final int distance = h.map().distanceBetweenTiles(attacker.getOccupiedTile(), defender.getOccupiedTile());

        final SequenceAction attackSequence;

        if(distance == 1) {
            attackSequence = GridCombatSequences.closeCombat(h, attacker, defender);
        } else {
            attackSequence = GridCombatSequences.distantCombat(h, attacker, defender);
        }

        RunnableAction finishAction = new RunnableAction();
        finishAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                attacker.setAnimationState(IDLE);
                attacker.stats().spendAP();
                h.camera().stopFollowing();
                h.priority().invalidatePriority();
            }
        });

        h.camera().follow(attacker);

        attacker.addAction(Actions.sequence(
            attackSequence,
            finishAction
        ));
    }

    private void passPriority(GridUnit unit) {
        unit.stats().spendAP();
        unit.setAnimationState(IDLE);
        h.map().placeActor(unit, unit.getOccupiedTile());

//        h.input().setInputMode(STANDARD);
//        h.map().clearAllHighlights();
//        h.hud().standardize();
        h.standardize();
        h.priority().invalidatePriority();
    }

    private SequenceAction animatedPathingSequence(GridActor actor, GridPath path) {
        final SequenceAction movementSequence = new SequenceAction();

        for(int i = 0; i < path.length(); i++) {

            final Direction nextDirection;

            if(i == 0) {
                nextDirection = h.map().directionFromTileToTile(actor.getOccupiedTile(), path.getPath().get(0));
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

        h.hud().clearContextDisplay();
        h.map().clearAllHighlights();
        h.input().setInputMode(GridInputHandler.InputMode.LOCKED);

        switch(interactable.getInteractType()) {

            case MOVE_WAIT:
                moveThenWait(interactable.getSubject(), interactable.getPath());
                break;

            case MOVE_ATTACK:
                moveThenAttack(interactable.getSubject(), interactable.getPath(), interactable.getObject());
                break;

            case ATTACK:
                attack(interactable.getSubject(), interactable.getObject());
                break;

            case WAIT:
                if(interactable.getSubject() instanceof GridUnit) passPriority((GridUnit)interactable.getSubject());
                break;

            default:
                break;
        }
    }

    @Override
    public Array<GridInteraction> getActorInteractions() {
        // This felt more at-home here when this was ActorHandler,
        // funnily now changing the scope has made this seem both
        // appropriately placed here, and also a bit awkward.
        // I'll leave it for now.
        final Array<GridInteraction> returnValue = new Array<>();
        for(GridActor actor : h.register().unifiedTurnOrder()) {
            returnValue.addAll(actor.getInteractions());
        }
        return returnValue;
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }
}
