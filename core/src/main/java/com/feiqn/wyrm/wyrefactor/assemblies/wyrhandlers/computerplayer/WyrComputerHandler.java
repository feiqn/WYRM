package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.damage.DamageCalculator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.WyrMap;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.ActorType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.StatType.*;


public final class WyrComputerHandler extends WyrHandler {

    public WyrComputerHandler() {

    }

    public void run(Array<WyrActor.Unit> units) {
        if(isBusy) return;
        isBusy = true;
        final Array<WyrInteraction> options = new Array<>();

        for(WyrActor.Unit unit : units) {
            final WyrInteraction action = preferredAction(unit);
            options.add(action);
        }

        final WyrInteraction finalAnswer = preferredActionFromList(options);

        queueInteraction(finalAnswer);
    }

    private void queueInteraction(@NotNull final WyrInteraction i) {
        handlers.camera().addAction(Actions.sequence(
            Actions.moveTo(i.getSubject().gridX(), i.getSubject().gridY(), TARGET_SPEED),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    handlers.camera().follow(i.getSubject());
                    isBusy = false;
                    handlers.interactions().parseInteractable(i);
                }
            })
        ));
    }

    private WyrInteraction preferredAction(WyrActor.Unit actor) {
        // "deliberateBestOption" in old data
        switch(actor.getPersonality().getPersonalityType()) {
            case PLAYER:
                Gdx.app.log("AI_preferredAction", "ai called for player unit :(");
                break;
            case AGGRESSIVE:
                return buildAggressiveAction(actor);
            case ESCAPE: // TODO: etc...
            case RECKLESS:
            case FLANKING:
            case DEFENSIVE:
            case PATROLLING:
            case PROTECTIVE:
            case LOS_AGGRO:
            case LOS_FLEE:
            case TARGET_UNIT:
            case TARGET_PROP:
            case TARGET_LOCATION:

            case STILL:
                // attack in reach only
            default:
                return new WyrInteraction(actor).passPriority();
        }
        Gdx.app.log("AI_preferredAction", "null personality? :(");
        return new WyrInteraction(actor).passPriority();
    }

    public WyrInteraction buildAggressiveAction(WyrActor.Unit unit) {
        final GridPathfinder.Things currentlyAccessible = GridPathfinder.currentlyAccessibleTo((WyrMap) handlers.map(), unit);
        final HashMap<WyrActor, GridPath> opposition = new HashMap<>(currentlyAccessible.opposition(unit.getTeamAlignment()));

        switch(opposition.size()) {
            case 0:
                // No enemies in range, scout distant target and move closer.
                final GridPathfinder.Things potentiallyAccessible = GridPathfinder.potentiallyAccessibleTo((WyrMap) handlers.map(), unit);
                final HashMap<WyrActor, GridPath> futureOpposition = new HashMap<>(potentiallyAccessible.opposition(unit.getTeamAlignment()));
                    switch(futureOpposition.size()) {
                        case 0:
                            // Can't find see a path to any enemy.
                            return new WyrInteraction(unit).passPriority();
                        case 1:
                            // Only one target, hunt it down.
//                            final GridPath path = futureOpposition.values().iterator().next();
                            // trim and truncate the theoretical path to what
                            // the unit can actually do this turn.
//                            path.realize(unit);
//                            return new RPGridInteraction(unit).moveThenWait(path);
                        default:
                            // Decide from many targets.
                            final Array<WyrActor> targetList = new Array<>();
                            targetList.addAll(futureOpposition.keySet().toArray(new WyrActor[0]));
                            final WyrActor targetFromList = preferredTargetFromList(unit, targetList);
                            final GridPath realizedPath = futureOpposition.get(targetFromList).realize(unit);
                            if(realizedPath.lastTile() == unit.getOccupiedTile()) return new WyrInteraction(unit).passPriority();
                            return new WyrInteraction(unit).moveThenWait(realizedPath);
                    }
//            case 1:
//                // Only one enemy, decide if it's close enough from current tile or need to move first.
//                final GridPath   path   = currentlyAccessible.opposition(unit.getTeamAlignment()).values().iterator().next();
//                final RPGridUnit target = currentlyAccessible.opposition(unit.getTeamAlignment()).keySet().iterator().next();
//                path.realize(unit);
//                return new RPGridInteraction(unit).moveThenAttack(target,path);

            default:
                // Decide which enemy to aggress.
                final Array<WyrActor> a = new Array<>();
                a.addAll(opposition.keySet().toArray(new WyrActor[0]));
                final WyrActor t = preferredTargetFromList(unit, a);
                if(t.getActorType() == ENTITY) {
                    if(handlers.map().distanceBetweenTiles(unit.getOccupiedTile(), t.getOccupiedTile()) == 1) {
                        return new WyrInteraction(unit).attack((WyrActor) t, 1);
                    }
                    return new WyrInteraction(unit).moveThenAttack((WyrActor)t, opposition.get(t));
                } else if (t.getActorType() == PROP) {
                    // TODO: handle prop attack
                }
                return new WyrInteraction(unit).passPriority();
        }
    }

    // TODO: handle guys who wanna chase props

    private WyrActor preferredTarget(WyrActor forAggressor) {
        // Cycle through all units and props to see what unit wants to hit most.
        return null;
    }

    private WyrActor preferredTargetFromList(WyrActor forAggressor, Array<WyrActor> fromList) {
        // TODO: logic:
        //  if the path to the preferred target is obstructed, check if the obstruction should be targeted

        WyrActor choice = null;
        int choiceWeight = -11; // 1 less than the lower bound

        for(WyrActor u : fromList) {
            final int w = weightForCombat(forAggressor, u);
            if(w > choiceWeight) {
                choiceWeight = w;
                choice = u;
            }
        }

        return choice;
    }

    // TODO:
    //  - best or worst combat action

    private WyrInteraction preferredActionFromList(Array<WyrInteraction> options) {
        WyrInteraction bestChoice = options.first();

        for(WyrInteraction option : options) {
            if(weightForChoice(option) > weightForChoice(bestChoice)) {
                bestChoice = option;
            }
        }

//        Gdx.app.log("ai_preferredAction", "action type: " + bestChoice.getInteractType());
        return bestChoice;
    }

    /** Weight values between -10~10, starting at 0 and adjusting. Higher is better.
     * @param attacker
     * @param defender
     * @return weight
     */
    private int weightForCombat(WyrActor attacker, WyrActor defender) {
        if(attacker == null || defender == null) return -10;
        // TODO:
        //  switch based on attacker's weapon damage type (phys / mag, etc)
        if(DamageCalculator.physicalAttackDamage(attacker, defender).getRawDamage() >= defender.getRollingHP()) return 10;

        int weight = 0;
        int reciprocalDamage = DamageCalculator.physicalAttackDamage(defender,attacker).getRawDamage();

        weight = (reciprocalDamage                         >=  attacker.getRollingHP()                      ? weight-3 : weight);
        weight = (attacker.getMaxHP()                      >   defender.getMaxHP()                          ? weight+1 : weight-1);
        weight = (attacker.getMaxHP()                      >= (defender.getMaxHP()*2)                       ? weight+1 : weight  );
        weight = (attacker.getModifiedStatValue(STRENGTH)  >   defender.getModifiedStatValue(DEFENSE)       ? weight+1 : weight-1);
        weight = (attacker.getModifiedStatValue(STRENGTH)  >= (defender.getModifiedStatValue(DEFENSE)*2)    ? weight+1 : weight  );
        weight = (attacker.getModifiedStatValue(SPEED)     >=  defender.getModifiedStatValue(SPEED)         ? weight+1 : weight-1);
        weight = (attacker.getModifiedStatValue(SPEED)     >= (defender.getModifiedStatValue(SPEED)*2)      ? weight+1 : weight  );
        weight = (attacker.getModifiedStatValue(MAGIC)     >   defender.getModifiedStatValue(RESISTANCE)    ? weight+1 : weight-1);
        weight = (attacker.getModifiedStatValue(MAGIC)     >= (defender.getModifiedStatValue(RESISTANCE)*2) ? weight+1 : weight  );
        weight = (attacker.getModifiedStatValue(DEXTERITY) >   defender.getModifiedStatValue(DEXTERITY)     ? weight+2 : weight);
        weight = (attacker.getModifiedStatValue(DEXTERITY) >= (defender.getModifiedStatValue(DEXTERITY)*2)  ? weight+2 : weight);
        weight = (attacker.getModifiedStatValue(DEXTERITY) <   defender.getModifiedStatValue(DEXTERITY)     ? weight-2 : weight);
        weight = (attacker.getModifiedStatValue(DEXTERITY) <= (defender.getModifiedStatValue(DEXTERITY)*2)  ? weight-2 : weight);
        weight = (attacker.getReach()                      > defender.getReach()                            ? weight+1 : weight);
        weight = (defender.getReach()                      > attacker.getReach()                            ? weight-1 : weight);

        if(weight > 10) weight = 10;
        if(weight < -10) weight = -10;
        Gdx.app.log("combat weight", attacker.getName() + " -> " + defender.getName() + ": " + weight);
        return weight;
    }
    private int weightForChoice(WyrInteraction interaction) {
        if(interaction.getSubject() == null) return -10;
        if(interaction.getSubject().getActorType() != ENTITY) return -10; // TODO: maybe props will use interactions later? idk rn
        switch(interaction.getSubject().getPersonality().getPersonalityType()) {

            case PLAYER:
            case STILL:
                switch(interaction.getInteractType()) {
                    case WAIT:
                        return 10;

                    case MOVE_BY:
                    case ATTACK:
                    case TALK:

                    case MOVE_TALK:
                    case MOVE_WAIT:
                    case MOVE_ATTACK:

                    case PROP_LOOT:
                    case PROP_OPEN:
                    case PROP_PILOT:
                    case PROP_SEIZE:
                    case PROP_ESCAPE:
                    case PROP_DESTROY:

                    case EXAMINE:
                    default:
                        return -10;
                }

            case ESCAPE:
            case FLANKING:
            case LOS_FLEE:
            case RECKLESS:

            case PATROLLING:
            case LOS_AGGRO:
            case AGGRESSIVE:
                switch(interaction.getInteractType()) {
                    case WAIT:
                        return -2;

                    case MOVE_BY:
                    case TALK:

                    case MOVE_TALK:

                    case MOVE_WAIT:

                    case ATTACK:
                    case MOVE_ATTACK:
                        // TODO:
                        //  Weigh outcome of combat vs interaction.object,
                        //  decide if the fight is favorable.
                        return 2;

                    case PROP_LOOT:
                    case PROP_OPEN:
                    case PROP_PILOT:
                    case PROP_SEIZE:
                    case PROP_ESCAPE:
                    case PROP_DESTROY:

                    case EXAMINE:
                    default:
                        return -10;
                }


            case DEFENSIVE:
            case PROTECTIVE:


            case TARGET_UNIT:
            case TARGET_PROP:
            case TARGET_LOCATION:

            default:
                return -10;
        }

    }

}
