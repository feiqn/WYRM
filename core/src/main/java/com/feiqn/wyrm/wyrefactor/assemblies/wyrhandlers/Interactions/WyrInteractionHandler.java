package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor.Prop;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor.Unit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs.GridAbilitySequences;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs.GridCombatSequences;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AbilityID;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.AnimationState.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.TeamAlignment.*;

public final class WyrInteractionHandler extends WyrHandler {

    private final Array<WyrInteraction> queuedInteractions = new Array<>();

    private boolean parsingChoreo = false;

    public WyrInteractionHandler() {}

    private void followPath(WyrActor actor, GridPath path) {
        final SequenceAction movementSequence = animatedPathingSequence(actor, path);

        RunnableAction finishMoving = new RunnableAction();
        finishMoving.setRunnable(new Runnable() {
            @Override
            public void run() {
//                actor.stats().spendSteps(path.costFor(actor));
//                handlers.map().placeActor(actor, path.lastTile().getXColumn(), path.lastTile().getYRow());
//                actor.clearState();

                if(actor.getTeamAlignment() == PLAYER) {
                    if((actor).stats().canStep()) {
                        finishInteracting();
                        return;
                    } else {
//                        handlers.hud().setTileContext(path.lastTile());
                        handlers.hud().anchorActionsMenu();
//                        handlers.hud().setActionMenuContext(path.lastTile(), actor);
//                        handlers.hud().displayModalActionMenu();
                    }
                } else {
                    actor.setAnimationState(IDLE);
                    actor.stats().spendAP();
                    actor.stats().depleteSteps();
//                    handlers.standardizeParse();
                }

                finishInteracting();
            }
        });

        handlers.camera().follow(actor);
        actor.addAction(Actions.sequence(
            movementSequence,
            finishMoving)
        );
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

        handlers.camera().addAction(Actions.sequence(
            Actions.moveTo(beingFired.gridX(), beingFired.gridY(), .3f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    unitFiring.addAction(Actions.sequence(
                        GridCombatSequences.propArmamentFire(unitFiring, beingFired, firedAt),
                        finishAction
                    ));
                }
            }))
        );
    }

    private void useAbility(AbilityID abilityID, WyrActor subject, WyrActor object, WyrActor prepositional) {
        final SequenceAction abilitySequence = GridAbilitySequences.fromID(abilityID, subject, object, prepositional);

        RunnableAction finishAction = new RunnableAction();
        finishAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                subject.setAnimationState(IDLE);
                subject.stats().spendAP();
                finishInteracting();
            }
        });

        handlers.camera().addAction(Actions.sequence(
            Actions.moveTo(subject.gridX(), subject.gridY(), .3f),
            abilitySequence,
            finishAction
            )
        );
    }

    private void mount(WyrActor unit) {
        if(!unit.stats().mountAvailable()) return;

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
        unit.clearState();
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
        }
        movementSequence.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                actor.stats().spendSteps(path.costFor(actor));
                handlers.map().placeActor(actor, path.lastTile().getXColumn(), path.lastTile().getYRow());
            }
        }));
        return movementSequence;
    }

    public void parseChoreo(WyrInteraction interaction) {
        parsingChoreo = true;
        parseInteraction(interaction);
    }

    public void moveThenParseChoreo(GridPath path, WyrInteraction interaction) {
        parsingChoreo = true;
        moveThenParse(path, interaction);
    }

    private void moveThenParse(GridPath path, WyrInteraction interaction) {
        if(isBusy || (handlers.cutscenes().cutsceneIsPlaying() && !handlers.cutscenes().isChoreographing())) {
            queuedInteractions.add(interaction);
            return;
        }
        isBusy = true;

        final WyrActor subject = interaction.getSubject();

        final SequenceAction movementSequence = animatedPathingSequence(subject, path);

        handlers.camera().follow(subject);

        subject.addAction(Actions.sequence(
            movementSequence,
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    parse(interaction);
                }
            })
        ));
    }

    public void parseInteraction(WyrInteraction interaction) {

        isBusy = true;
        handlers.time().incrementStateClock();
        handlers.hud().hideActionsMenu();
        handlers.clearMapState();
        handlers.input().setInputMode(InputMode.LOCKED);

        final WyrActor subject = interaction.getSubject();

        // Interactions aren't obligated to pre-calculate their own path.

        if(interaction.hasPath()) {
            isBusy = false;
            moveThenParse(interaction.getPath(), interaction);
        } else {
            // Check if the interaction needs a path.

            @Null WyrTile destinationTile = interaction.hasObject() ? interaction.getObject().getOccupiedTile() :
                interaction.getCoordinate() != null ? handlers.map().tileAt((int) interaction.getCoordinate().x, (int) interaction.getCoordinate().y)
                    : null;

            if(destinationTile != null) {
                // Got somewhere to be.
                final int d = handlers.map().distanceBetweenTiles(subject.getOccupiedTile(), destinationTile);

                if(d > subject.getReach()) {
                    // Need to move.
                    if(destinationTile.groundIsOccupied()) {
                        destinationTile = handlers.map().nearestAccessibleNeighbor(destinationTile.getXColumn(), destinationTile.getYRow(), subject);
                    }
                    final GridPath path = handlers.priority().stateThings(subject).walkableTiles().getOrDefault(destinationTile, new GridPath(subject.getOccupiedTile()));
                    interaction.setPath(path);
                    isBusy = false;
                    moveThenParse(path, interaction);
                }
            } else {
                isBusy = false;
                parse(interaction);
            }
        }
    }

    private void parse(WyrInteraction interaction) {
        if(isBusy || (handlers.cutscenes().cutsceneIsPlaying() && !handlers.cutscenes().isChoreographing())) {
            queuedInteractions.add(interaction);
            return;
        }
//        isBusy = true;
//        handlers.time().incrementStateClock();
//        handlers.hud().hideActionsMenu();
//        handlers.clearMapState();
//        handlers.input().setInputMode(InputMode.LOCKED);

        final WyrActor subject = (
                interaction.getSubject() != null ? interaction.getSubject() :
                    handlers.register().getWyrActorFromMap(interaction.getSubjectUID())
            );

        final @Null WyrActor object = (
                interaction.getObject() != null ? interaction.getObject() :
                    (interaction.getObjectUID() != null ? handlers.register().getWyrActorFromMap(interaction.getObjectUID()) : null)
            );

        final @Null WyrActor prepositional = (
                interaction.getPrepositional() != null ? interaction.getPrepositional() :
                    (interaction.getPrepositionalUID() != null ? handlers.register().getWyrActorFromMap(interaction.getPrepositionalUID()) : null)
            );

//        if(interaction.hasObject()) {
//            if(handlers.map().distanceBetweenTiles(subject.getOccupiedTile(), object.getOccupiedTile()) > interaction.interactableRange()) {
//                Gdx.app.log("parse", "I can't reach that.");
//                finishInteracting();
//                return;
//            }
//        }

        switch(interaction.getInteractType()) {

            case MOVE_ATTACK:
            case ATTACK:
                if(subject instanceof Unit && object instanceof Unit) {
                    final Unit sUnit = (Unit) subject;
                    final Unit oUnit = (Unit) object;
                    if(handlers.cutscenes().checkCombatStartTriggers(sUnit.getCharacterID(), sUnit.getTeamAlignment(), oUnit.getCharacterID(), oUnit.getTeamAlignment())) {
                        queueInteraction(interaction);
                        isBusy = false;
                        return;
                    }
                }
                attack(subject, object);
                break;

//            case MOVE_ATTACK:
//                moveThenAttack(subject, interaction.getPath(), object);
//                break;

            case MOVE_WAIT:
                // Under new pipeline, paths that come attached to interactions
                // are fired automatically be default before the interaction
                // is ever passed in for parsing.
                // Therefore, at this point, the movement has already happened.
                //
                // Interactions that are passed through with no pre-computed path
                // first have their interactability distance checked against the
                // interaction's subject's reach. If reach is exceeded, a path is
                // generated then to the nearest accessible tile within a range
                // defined by GridPathFinder
            case WAIT:
                passPriority(subject);
                break;

            case FOLLOW_PATH:
                followPath(subject, interaction.getPath());
                break;

            case CAMERA_TO_ACTOR:
                cameraTo(subject.gridX(), subject.gridY());
                break;

            case MOUNT:
                subject.stats().mount();
                finishInteracting();
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

            case ABILITY_USE:
                useAbility(interaction.getAbility(), subject, object, prepositional);
                break;

            case EXAMINE:
                // open examine menu in hud
                break;

            case TALK:
                // start cutscene

            case SPAWN:
            case DESPAWN:
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
        isBusy = false;
        handlers.time().incrementStateClock();
        if(parsingChoreo) {
            parsingChoreo = false;
            handlers.cutscenes().continueScene();
        } else {
            handlers.standardizeParse();
        }
    }

    public void queueInteraction(WyrInteraction interaction) {
        if(queuedInteractions.contains(interaction, true)) return;
        queuedInteractions.add(interaction);
    }

    public boolean interactionQueued() { return !queuedInteractions.isEmpty(); }

//    public Array<WyrInteraction> getActorGridInteractions() {
//        // This felt more at-home here when this was ActorHandler,
//        // funnily now changing the scope has made this seem both
//        // appropriately placed here, and also a bit awkward.
//        // I'll leave it for now.
//        final Array<WyrInteraction> returnValue = new Array<>();
//        for(WyrActor actor : handlers.register().unifiedTurnOrder()) {
//            returnValue.addAll(actor.getInteractions());
//        }
//        return returnValue;
//    }

}
