package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor.Prop;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor.Unit;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WYRMActors.WyrEmblem.Props.Mounts;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.GridCombatSequences;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AbilityID;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.AnimationState.*;

public final class WyrInteractionHandler extends WyrHandler {

    private final Array<WyrInteraction> queuedInteractions = new Array<>();

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
                    if(((Unit)actor).getTeamAlignment() == WyrFrame.TeamAlignment.PLAYER) {
                        if((actor).stats().canStep()) {
                            finishInteracting();
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

                finishInteracting();
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

    private void aimSpell(Unit unitAiming, AbilityID spellBeingAimed) {

    }

    private void aimProp(Unit unitAiming, Prop propBeingAimed) {


    }

    private void fireProp(Unit unitFiring, Prop beingFired, WyrActor firedAt) {
        RunnableAction finishAction = new RunnableAction();
        finishAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                unitFiring.setAnimationState(IDLE);
                unitFiring.stats().spendAP();
                finishInteracting();
            }
        });

        handlers.camera().addAction(Actions.moveTo(beingFired.gridX(), beingFired.gridY(), .3f));

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                unitFiring.addAction(Actions.sequence(
                    GridCombatSequences.propArmamentFire(unitFiring, beingFired, firedAt),
                    finishAction
                ));
            }
        }, .35f);
    }

    private void mount(Unit unit) {
        if(!unit.stats().mountAvailable()) return;
        switch(Mounts.fromID(unit.stats().ownedMountID()).getMountType()) {
            case PEGASUS:


            default:
                break;
        }
    }

    private void dismount(Unit unit) {

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
                finishInteracting();
            }
        });

        handlers.camera().follow(attacker);

        attacker.addAction(Actions.sequence(
            attackSequence,
            finishAction
        ));
    }

    private void cameraTo(int x, int y) {
        handlers.camera().addAction(
            Actions.sequence(
                Actions.moveTo(x, y, .5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        finishInteracting();
                    }
                })
            )
        );
    }

    private void passPriority(WyrActor unit) {
        unit.clearEphemeralInteractions();
        unit.stats().depleteAP();
        unit.stats().depleteSteps();
        unit.setAnimationState(IDLE);
        handlers.map().placeActor(unit, unit.getOccupiedTile());
        finishInteracting();
    }

    private SequenceAction animatedPathingSequence(WyrActor actor, GridPath path) {
        final SequenceAction movementSequence = new SequenceAction();

        for(int i = 0; i < path.length(); i++) {

            final Utilities.CompassDirection nextDirection;

            if(i == 0) {
                nextDirection = handlers.map().directionFromTileToTile(actor.getOccupiedTile(), path.getTiles().get(0));
            } else if(i != path.length() - 1) {
                nextDirection = handlers.map().directionFromTileToTile(path.getTiles().get(i-1), path.getTiles().get(i));
            } else if(i == path.length() - 1) {
                nextDirection = handlers.map().directionFromTileToTile(path.getTiles().get(i-1), path.lastTile());
            } else {
                nextDirection = Utilities.CompassDirection.S;
            }

            final RunnableAction changeDirection = new RunnableAction();
            Utilities.CompassDirection finalNextDirection = nextDirection;

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

    public void parseInteraction(WyrInteraction interactable) {
        if(isBusy || (handlers.cutscenes().cutsceneIsPlaying() && !handlers.cutscenes().isChoreographing())) {
            queuedInteractions.add(interactable);
            return;
        }

        handlers.hud().clearContextDisplay();
        handlers.map().standardize();
        handlers.input().setInputMode(InputMode.LOCKED);
        isBusy = true;

        final WyrActor subject = (
                interactable.getSubject() == null ?
                    handlers.register().getActorByName(interactable.getSubjectUID()) : interactable.getSubject()
            );

        final @Null WyrActor object = (
                interactable.getObject() != null ? interactable.getObject() :
                    (interactable.getObjectUID() != null ? handlers.register().getActorByName(interactable.getObjectUID()) : null)
            );

        final @Null WyrActor prepositional = (
                interactable.getPrepositional() != null ? interactable.getPrepositional() :
                    (interactable.getPrepositionalUID() != null ? handlers.register().getActorByName(interactable.getPrepositionalUID()) : null)
            );

        switch(interactable.getInteractType()) {

            case ATTACK:
                attack(subject, object);
                break;

            case MOVE_ATTACK:
                moveThenAttack(subject, interactable.getPath(), object);
                break;

            case WAIT:
                passPriority(subject);
                break;

            case MOVE_WAIT:
                moveThenParse(subject, interactable.getPath());
                break;

            case CAMERA_TO_ACTOR:
                cameraTo(subject.gridX(), subject.gridY());
                break;

            case MOUNT:
                assert subject instanceof Unit;
                mount((Unit) subject);
                break;

            case DISMOUNT:
                assert subject instanceof Unit;
                dismount((Unit)subject);
                break;

            case PROP_AIM:
                break;

            case PROP_FIRE:
                assert subject instanceof Unit;
                assert object instanceof Prop;
                fireProp((Unit) subject, (Prop) object, prepositional);
                break;

            case UNIT_DEATH:
                assert subject instanceof Unit;
                handlers.camera().follow(subject);
                ((Unit) subject).kill();
                finishInteracting();
                break;

            default:
                break;
        }
    }

    public void parseFromQueue() {
        if(queuedInteractions.isEmpty()) finishInteracting();
        final WyrInteraction i = queuedInteractions.get(0);
        queuedInteractions.removeIndex(0);
        parseInteraction(i);
    }

    private void finishInteracting() {
        if(!isBusy) return;
        isBusy = false;
        handlers.standardizeParse();
    }

    public void queueInteraction(WyrInteraction interaction) {
        if(queuedInteractions.contains(interaction, true)) return;
        queuedInteractions.add(interaction);
    }

    public boolean interactionQueued() {
        return !queuedInteractions.isEmpty();
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
