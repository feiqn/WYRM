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

    protected Boolean thinking, // HOLD for ME
                      waiting;  // HOLD for YOU

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
            thinking = true; // While thinking == true, run() will not be called again by ABS
            waiting = false; // waiting should == true while run() should not be called again, but AIHandler still has commands to send to ABS

            for(int u = 0; u < abs.currentTeam().size; u++) {
                if(abs.currentTeam().get(u).canMove()) {
                    sendAction(deliberateBestOption(abs.currentTeam().get(u)));
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

    private AIAction deliberateBestOption(Unit unit) {
        // Evaluates and constructs best action to take for a given unit

        final Array<AIAction> options = new Array<>();

        final AIAction waitAction = new AIAction(game, ActionType.WAIT_ACTION);
        waitAction.setSubjectUnit(unit);
        options.add(waitAction); // If you choose not to decide, you still have made a choice.

        abs.recursionHandler.recursivelySelectReachableTiles(unit); // Tells us where we can go and what we can do.

        switch(unit.getAiType()) {
            case AGGRESSIVE: // Look for good fights, and advance the enemy.
//                Gdx.app.log("AI Type:", "Aggressive unit");
                if(abs.attackableUnits.size > 0) {
//                    Gdx.app.log("eval best option", "enemies in range");

                    AIAction action = new AIAction(evaluateBestOrWorstCombatAction(unit, true));

                    if(abs.distanceBetweenTiles(unit.occupyingTile, action.getObjectUnit().occupyingTile) > unit.getReach()) {
                        final Path path = new Path(deliberateMovementPath(unit));
                        action.setPath(path);
                    }
                    options.add(action);
                } else {
                    // Move forward
//                    Gdx.app.log("eval best option", "NONE in range");
                    AIAction charge = new AIAction(game, ActionType.MOVE_ACTION);
                    charge.setSubjectUnit(unit);
                    charge.incrementWeight();

                    final Path path = new Path(deliberateMovementPath(unit));

                    charge.setPath(path);
                    options.add(charge);
                }
                break;
            case RECKLESS: // Run towards nearest enemy and attack anything in sight. Fodder.
                options.get(0).decrementWeight();
                break;
            case STILL: // Stand still and attack anything in reach.

            case LOS_AGGRO: // Stand still but chase anything in LOS.

            case LOS_FLEE: // Stand still but run away from anything in LOS.

            case DEFENSIVE: // Huddle together with other units, ideally around choke points.

            case FLANKING: // Surround the enemy.

            case TARGET_TILE: // Move towards a specific tile.

            case TARGET_UNIT: // Follow a specific unit.

            case TARGET_OBJECT: // Focus on acquiring a chest, manning a ballista, opening a door, etc

            case ESCAPE: // Run towards escape tile
                // TODO: Make Antal run
            case PLAYER: // Make mistakes.
            default:
                break;
        }

        AIAction bestOption = options.get(0);
        int highestWeight = bestOption.getDecisionWeight();

        for(AIAction option : options) {
//            Gdx.app.log("WEIGHING OPTION", option.getActionType() + " " + option.getDecisionWeight() + " against weight: " + highestWeight);
            if(option.getDecisionWeight() > highestWeight) {
                highestWeight = option.getDecisionWeight();
                bestOption = option;
            }
        }

//        Gdx.app.log("AIHandler: ","evaluate done, sending best option of type: " + bestOption.getActionType());
        return bestOption;
    }

    protected void sendAction(AIAction action) {
//        Gdx.app.log("AIHandler: ", "sending action of type: " + action.getActionType());
        startWaiting();
        abs.executeAction(action);
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

//        for(LogicalTile tile : shortestPath.retrievePath()) {
//            tile.highlightCanSupport();
//        }

        return shortestPath;

    }

    @NotNull
    private AIAction evaluateBestOrWorstCombatAction(Unit unit, boolean best) {
//        Gdx.app.log("eval", "evaluating match-ups");

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
//                Gdx.app.log("eval", "BEST");
                return new AIAction(weighBestOrWorstOption(true, options));
            } else {
//                Gdx.app.log("eval", "WORST");
                return new AIAction(weighBestOrWorstOption(false, options));
            }

        } else {
            Gdx.app.log("combat eval: ", "none reachable");
            final AIAction wait = new AIAction(game, ActionType.WAIT_ACTION);
            wait.setSubjectUnit(unit);
            return wait;
        }
    }

    @NotNull
    private AIAction weighBestOrWorstOption(boolean best, Array<AIAction> options) {
        int weight = 0;
        AIAction winningOption = options.get(0);

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

//        Gdx.app.log("WEIGHT RESULTs:", "winning action type: " + winningOption.getActionType());

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
