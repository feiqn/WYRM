package com.feiqn.wyrm.logic.handlers.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.actions.AIAction;
import com.feiqn.wyrm.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.models.mapdata.Path;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.Unit;
import org.jetbrains.annotations.NotNull;

public class AIHandler {

    protected Boolean thinking,
                      waiting;

    protected BattleScreen abs;

    protected final WYRMGame game;

    public AIHandler(WYRMGame game) {
        this.game = game;
        abs = game.activeBattleScreen;
        startWaiting();
        stopThinking();
    }

    public void run() {
        if(!abs.isBusy()) {
            thinking = true;
            waiting = false;

            for(int u = 0; u < abs.currentTeam().size; u++) {
                if(abs.currentTeam().get(u).canMove()) {
                    sendAction(evaluateBestOption(abs.currentTeam().get(u)));
                }
            }

            boolean done = true;

            for(int u = 0; u < abs.currentTeam().size; u++) {
                if(abs.currentTeam().get(u).canMove()) {
                    done = false;
                }
            }

            if(done) endTurn();

        } else {
            thinking = false;
            waiting = true;
        }
    }

    private AIAction evaluateBestOption(Unit unit) {
        final Array<AIAction> options = new Array<>();

        options.add(new AIAction(game, ActionType.WAIT_ACTION)); // If you choose not to decide, you still have made a choice.

        abs.recursionHandler.recursivelySelectReachableTiles(unit); // Tells us where we can go and what we can do.

        switch(unit.getAiType()) {
            case AGGRESSIVE:
                // Look for good fights, and advance the enemy.
                if(abs.attackableUnits.size > 0) {
                    options.add(evaluateBestOrWorstCombatAction(unit, true));
                } else {
                    // Move forward
                    AIAction charge = new AIAction(game, ActionType.MOVE_ACTION);
                    charge.setSubjectUnit(unit);

                    final Path path = deliberateMovementPath(unit);
                    final LogicalTile destination = path.lastTile();

                    charge.setCoordinate(destination.getRow(), destination.getColumn());
                    options.add(charge);
                }
                break;
            case RECKLESS:
                // Run towards the enemy and attack anything in sight. Fodder.
                options.get(0).decrementWeight();
            case STILL:
                // Stand still and attack anything in range.
            case LOS_AGGRO:
                // Stand still but chase anything in range.
            case LOS_FLEE:
                // Stand still but run away from anything in range.
            case DEFENSIVE:
                // Huddle together with other units, ideally around choke points.
            case FLANKING:
                // Surround the enemy.
            case PLAYER:
                // Make mistakes.
            default:
                break;
        }

        int highestWeight = -1;
        AIAction bestOption = options.get(0);

        for(AIAction option : options) {
            if(option.getDecisionWeight() > highestWeight) {
                highestWeight = option.getDecisionWeight();
                bestOption = option;
            }
        }

//        Gdx.app.log("AIHandler: ","evaluate done, sending best option");
        return bestOption;
    }

    protected void sendAction(AIAction action) {
//        Gdx.app.log("AIHandler: ", "sending action");
        startWaiting();
        abs.executeAction(action);
        stopWaiting();
    }

    private void endTurn() {
        stopThinking();
        startWaiting();
        abs.executeAction(new AIAction(game, ActionType.PASS_ACTION));
    }

    private Path deliberateMovementPath(Unit unit) {
        // todo: switch based on ai type

//        Gdx.app.log("delib move path: ", "start");

        // If I could go anywhere on the map, where would I want to be?

        // fill attackableEnemies list with all enemies accessible on map, while also filling
        // reachableTiles with all accessible tiles, with movement cost considered.
        abs.recursionHandler.recursivelySelectReachableTiles(unit.getRow(), unit.getColumn(), 100, unit.getMovementType());

        // decide who you want to fight or avoid
        AIAction bestFight = evaluateBestOrWorstCombatAction(unit, true);
//        AIAction worstFight = evaluateBestOrWorstCombatAction(unit, false);

        Path shortestPath;

        if(bestFight.getActionType() == ActionType.ATTACK_ACTION) {

            Unit bestMatchUp = bestFight.getObjectUnit();
//            Unit worstMatchUp = worstFight.getObjectUnit();

            // --case aggressive:

            // find the shortest path to bestMatchUp
            shortestPath = abs.recursionHandler.shortestPath(unit, bestMatchUp.occupyingTile);

            // find the furthest tile along shortestPath unit can reach this turn with its speed and move type
            float speed = unit.getModifiedMobility();
            int trim = 0;
            for(LogicalTile tile : shortestPath.retrievePath()) {
                if(speed >= tile.getMovementCostForMovementType(unit.getMovementType())) {
                    speed -= tile.getMovementCostForMovementType(unit.getMovementType());
                } else {
                    trim++;
                }
            }
//            Gdx.app.log("trim:", "" + trim + " out of a total length: " + shortestPath.size());
            shortestPath.shortenPathBy(trim);

        } else {
            Gdx.app.log("delib path: ", "bad action type");
            Gdx.app.log("BAD ACTION OF TYPE: ", "" + bestFight.getActionType());
            shortestPath = new Path(game, unit.occupyingTile);
        }

        for(LogicalTile tile : shortestPath.retrievePath()) {
            tile.highlightCanSupport();
        }

        return shortestPath;

    }

    @NotNull
    private AIAction evaluateBestOrWorstCombatAction(Unit unit, boolean best) {
        if(abs.attackableUnits.size > 0) {
            final Array<AIAction> options = new Array<>();

            for(Unit enemy : abs.attackableUnits) {
//                Gdx.app.log("combat eval: ", "adding option");
                final AIAction option = new AIAction(game, ActionType.ATTACK_ACTION);
                option.setSubjectUnit(unit);
                option.setObjectUnit(enemy);
                options.add(option);
            }
//            Gdx.app.log("combat eval: ", "");
            if(best) {
                return weighBestOrWorstOption(true, options);
            } else {
                return weighBestOrWorstOption(false, options);
            }

        } else {
            Gdx.app.log("combat eval: ", "none reachable");
            return new AIAction(game, ActionType.WAIT_ACTION);
        }
    }

    @NotNull
    private AIAction weighBestOrWorstOption(boolean best, Array<AIAction> options) {
        int weight = 0;
        AIAction winningOption = new AIAction(game, ActionType.WAIT_ACTION);

        for(AIAction option : options) {
            if(best) {
                if(option.getDecisionWeight() > weight) {
                    weight = option.getDecisionWeight();
                    winningOption = option;
                }
            } else {
                // instead, return the worst match up.
                if(option.getDecisionWeight() < weight) {
                    weight = option.getDecisionWeight();
                    winningOption = option;
                }
            }
        }
        return winningOption;
    }

    // --SETTERS--

    private void startThinking() { thinking = true;}
    public void stopThinking() { thinking = false; }
    public void stopWaiting() { waiting = false; }
    public void startWaiting() { waiting = true; }

    // --GETTERS--

    public boolean isThinking() {return thinking;}
    public boolean isWaiting() {return waiting;}
}
