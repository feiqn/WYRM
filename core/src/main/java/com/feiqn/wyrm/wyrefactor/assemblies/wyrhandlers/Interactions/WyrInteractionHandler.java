package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.GridCombatSequences;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.AnimationState.*;

public final class WyrInteractionHandler extends WyrHandler {

    public WyrInteractionHandler() {}

    private void moveThenParse(WyrActor actor, GridPath path) {
        final SequenceAction movementSequence = animatedPathingSequence(actor, path);

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                actor.stats().spendSteps(path.costFor(actor));
                handlers.map().placeActor(actor, path.lastTile().getXColumn(), path.lastTile().getYRow());
                actor.clearEphemeralInteractions();

                if(actor.getActorType() == WyrFrame.ActorType.ENTITY) {
                    if(((WyrActor.Unit)actor).getTeamAlignment() == WyrFrame.TeamAlignment.PLAYER) {
                        if((actor).stats().canStep()) {
                            isBusy = false;
                            handlers.standardizeParse();
                            return;
                        } else {
                            handlers.hud().setActionMenuContext(path.lastTile(), actor);
                            handlers.hud().displayModalActionMenu();
                        }
                    } else {
                        actor.setAnimationState(IDLE);
                        actor.stats().spendAP();
                        actor.stats().depleteSteps();
                        handlers.standardizeParse();
                    }
                } // TODO: props

                handlers.camera().standardize();
                isBusy = false;
            }
        });

        handlers.camera().follow(actor);
        actor.addAction(Actions.sequence(movementSequence, finishMoving));
    }

    private void moveThenAttack(WyrActor attacker, GridPath path, WyrActor target) {
        final SequenceAction movementSequence = animatedPathingSequence(attacker, path);

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
                attacker.stats().spendSteps(path.costFor(attacker));
                handlers.map().placeActor(attacker, path.lastTile().getXColumn(), path.lastTile().getYRow());
                attack(attacker, target);
            }
        });

        handlers.camera().follow(attacker);

        attacker.addAction(Actions.sequence(
            movementSequence,
            finishMoving)
        );
    }

    private void moveThenInteract(WyrActor actor, GridPath path) {} // props

    public void aim(WyrActor unitAiming, GameKit.RPG.AbilityID spellBeingAimed) {

    }

    private void aim(WyrActor.Unit unitAiming, WyrActor.Prop propBeingAimed) {

    }

    private void mount(WyrActor.Unit unit) {
        if(!unit.stats().getRPGClass().mountAvailable()) return;
        switch(unit.stats().getRPGClass().getMountType()) {
            case PEGASUS:


            default:
                break;
        }
    }

    private void dismount(WyrActor.Unit unit) {

    }

    private void attack(WyrActor attacker, WyrActor defender) {
        final int distance = handlers.map().distanceBetweenTiles(attacker.getOccupiedTile(), defender.getOccupiedTile());

        final SequenceAction attackSequence;

        if(distance == 1) {
            attackSequence = GridCombatSequences.closeCombat(attacker, defender);
        } else {
            attackSequence = GridCombatSequences.distantCombat(attacker, defender);
        }

        RunnableAction finishAction = new RunnableAction();
        finishAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                attacker.setAnimationState(IDLE);
                attacker.stats().spendAP();
//                attacker.standardize();
//                handlers.camera().standardize();
                isBusy = false;
                handlers.standardizeParse();
            }
        });

        handlers.camera().follow(attacker);

        attacker.addAction(Actions.sequence(
            attackSequence,
            finishAction
        ));
    }

    private void passPriority(WyrActor unit) {
        unit.clearEphemeralInteractions();
        unit.stats().depleteAP();
        unit.stats().depleteSteps();
        unit.setAnimationState(IDLE);
        handlers.map().placeActor(unit, unit.getOccupiedTile());

        isBusy = false;
        handlers.standardizeParse();
    }

    private SequenceAction animatedPathingSequence(WyrActor actor, GridPath path) {
        final SequenceAction movementSequence = new SequenceAction();

        for(int i = 0; i < path.length(); i++) {

            final Utilities.Compass nextDirection;

            if(i == 0) {
                nextDirection = handlers.map().directionFromTileToTile(actor.getOccupiedTile(), path.getTiles().get(0));
            } else if(i != path.length() - 1) {
                nextDirection = handlers.map().directionFromTileToTile(path.getTiles().get(i-1), path.getTiles().get(i));
            } else if(i == path.length() - 1) {
                nextDirection = handlers.map().directionFromTileToTile(path.getTiles().get(i-1), path.lastTile());
            } else {
                nextDirection = Utilities.Compass.S;
            }

            final RunnableAction changeDirection = new RunnableAction();
            Utilities.Compass finalNextDirection = nextDirection;

            changeDirection.setRunnable(new Runnable() {
                @Override
                public void run() {
                    isBusy = true;
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
            moveBy.setDuration(WyrFrame.MOVE_SPEED);

            final ParallelAction animation = new ParallelAction();
            animation.addAction(changeDirection);
            animation.addAction(moveBy);
            movementSequence.addAction(animation);
            movementSequence.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                }
            }));
        }
        return movementSequence;
    }

    public void parseInteractable(WyrInteraction interactable) {

        handlers.hud().clearContextDisplay();
        handlers.map().standardize();
        handlers.input().setInputMode(InputMode.LOCKED);
        isBusy = true;

        switch(interactable.getInteractType()) {

            case MOVE_WAIT:
                moveThenParse(interactable.getSubject(), interactable.getPath());
                break;

            case MOVE_ATTACK:
                moveThenAttack(interactable.getSubject(), interactable.getPath(), interactable.getObject());
                break;

            case ATTACK:
                attack(interactable.getSubject(), interactable.getObject());
                break;

            case WAIT:
                passPriority(interactable.getSubject());
                break;

            case MOUNT:
                assert interactable.getSubject() instanceof WyrActor.Unit;
                mount((WyrActor.Unit) interactable.getSubject());
                break;

            case DISMOUNT:
                assert interactable.getSubject() instanceof WyrActor.Unit;
                dismount((WyrActor.Unit)interactable.getSubject());
                break;

            case PROP_AIM:

            default:
                break;
        }
    }

    public Array<WyrInteraction> getActorGridInteractions() {
        // This felt more at-home here when this was ActorHandler,
        // funnily now changing the scope has made this seem both
        // appropriately placed here, and also a bit awkward.
        // I'll leave it for now.
        final Array<WyrInteraction> returnValue = new Array<>();
        for(WyrActor actor : handlers.register().unifiedTurnOrder()) {
            returnValue.addAll(actor.getInteractions());
        }
        return returnValue;
    }

}
