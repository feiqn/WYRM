package com.feiqn.wyrm.wyrefactor.wyrhandlers.Interactions.grid;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.ActorType;
import com.feiqn.wyrm.wyrefactor.helpers.Compass;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.Interactions.WyrInteractionHandler;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat.GridCombatSequences;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator.RPGridAnimState.*;

public final class GridInteractionHandler extends WyrInteractionHandler<RPGridInteraction> {

    private final RPGridMetaHandler h; // It's fun to just type "h".

    public GridInteractionHandler(RPGridMetaHandler metaHandler) {
        this.h = metaHandler;
    }

    private void moveThenWait(RPGridActor actor, GridPath path) {
        final SequenceAction movementSequence = animatedPathingSequence(actor, path);

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                h.map().placeActor(actor, path.lastTile().getXColumn(), path.lastTile().getYRow());
                actor.clearEphemeralInteractions();

                if(actor.getActorType() == ActorType.UNIT) {
                    assert actor instanceof RPGridUnit;
                    final RPGridUnit unit = (RPGridUnit) actor;
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

    private void moveThenAttack(RPGridActor attacker, GridPath path, RPGridActor target) {
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

    private void moveThenInteract(RPGridActor actor, GridPath path) {} // props

    private void attack(RPGridActor attacker, RPGridActor defender) {
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
                attacker.clearEphemeralInteractions();
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

    private void passPriority(RPGridUnit unit) {
        unit.clearEphemeralInteractions();
        unit.stats().spendAP();
        unit.setAnimationState(IDLE);
        h.map().placeActor(unit, unit.getOccupiedTile());

        h.standardize();
        h.priority().invalidatePriority();
    }

    private SequenceAction animatedPathingSequence(RPGridActor actor, GridPath path) {
        final SequenceAction movementSequence = new SequenceAction();

        for(int i = 0; i < path.length(); i++) {

            final Compass nextDirection;

            if(i == 0) {
                nextDirection = h.map().directionFromTileToTile(actor.getOccupiedTile(), path.getPath().get(0));
            } else if(i != path.length() - 1) {
                nextDirection = h.map().directionFromTileToTile(path.getPath().get(i-1), path.getPath().get(i));
            } else if(i == path.length() - 1) {
                nextDirection = h.map().directionFromTileToTile(path.getPath().get(i-1), path.lastTile());
            } else {
                nextDirection = Compass.S;
            }

            final RunnableAction changeDirection = new RunnableAction();
            Compass finalNextDirection = nextDirection;

            changeDirection.setRunnable(new Runnable() {
                @Override
                public void run() {
                    switch(finalNextDirection) {
                        case N:
                            actor.setAnimationState(FACING_NORTH);
                            break;
                        case S:
                            actor.setAnimationState(FACING_SOUTH);
                            break;
                        case W:
                            actor.setAnimationState(FACING_WEST);
                            break;
                        case E:
                            actor.setAnimationState(FACING_EAST);
                            break;
                    }
                }
            });

            final MoveByAction moveBy = new MoveByAction();
            switch(nextDirection) {
                case N:
                    moveBy.setAmount(0, 1);
                    break;
                case S:
                    moveBy.setAmount(0, -1);
                    break;
                case E:
                    moveBy.setAmount(1, 0);
                    break;
                case W:
                    moveBy.setAmount(-1, 0);
                    break;
                default:
                    break;
            }
            moveBy.setDuration(Wyr.MOVE_SPEED);

            final ParallelAction animation = new ParallelAction();
            animation.addAction(changeDirection);
            animation.addAction(moveBy);
            movementSequence.addAction(animation);
        }
        return movementSequence;
    }

    public void parseInteractable(RPGridInteraction interactable) {

        h.hud().clearContextDisplay();
        h.map().clearAllHighlights();
        h.input().setInputMode(RPGridInputHandler.InputMode.LOCKED);

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
                if(interactable.getSubject() instanceof RPGridUnit) passPriority((RPGridUnit)interactable.getSubject());
                break;

            default:
                break;
        }
    }

    @Override
    public Array<RPGridInteraction> getActorInteractions() {
        // This felt more at-home here when this was ActorHandler,
        // funnily now changing the scope has made this seem both
        // appropriately placed here, and also a bit awkward.
        // I'll leave it for now.
        final Array<RPGridInteraction> returnValue = new Array<>();
        for(RPGridActor actor : h.register().unifiedTurnOrder()) {
            returnValue.addAll(actor.getInteractions());
        }
        return returnValue;
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }
}
