package com.feiqn.wyrm.logic.handlers.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.actions.AIAction;
import com.feiqn.wyrm.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.mapdata.Path;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTileType;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import org.jetbrains.annotations.NotNull;

public class AIHandler {

    protected Boolean thinking, // HOLD for ME  // TODO: do either of these do anything? it seems
                      waiting;  // HOLD for YOU //     like everything is handled by abs.isBusy()

    protected GridScreen abs;

    protected final WYRMGame game;

    public AIHandler(WYRMGame game) {
        this.game = game;
        abs = game.activeGridScreen;
        startWaiting();
        stopThinking();
    }

    public void run() {
        if(!abs.isBusy()) {
            thinking = true; // While thinking == true, run() will not be called again by ABS
            waiting = false; // waiting should == true while run() should not be called again, but AIHandler still has commands to send to ABS

            AIAction action = new AIAction(deliberateBestOption(game.activeGridScreen.whoseNext()));
            sendAction(action);

            stopThinking();
        } else {
            thinking = false;
            waiting = true;
        }
    }

    private AIAction deliberateBestOption(SimpleUnit unit) {
        // Evaluates and constructs best action to take for a given unit

        final Array<AIAction> options = new Array<>();

        final AIAction waitAction = new AIAction(game, ActionType.WAIT_ACTION);
        waitAction.setSubjectUnit(unit);
        options.add(waitAction); // If you choose not to decide, you still have made a choice.

        abs.getRecursionHandler().recursivelySelectReachableTiles(unit); // Tells us where we can go and what we can do.

        switch(unit.getAiType()) {
            case AGGRESSIVE: // Look for good fights, and advance the enemy.
                // decide who you want to fight
                final AIAction bestCombatAction = new AIAction(evaluateBestOrWorstCombatAction(unit, true));
                Path shortestPath;

                if(abs.attackableUnits.size > 0) { // There are enemies I can reach this turn.
                    if(abs.getLogicalMap().distanceBetweenTiles(unit.getOccupyingTile(), bestCombatAction.getObjectUnit().getOccupyingTile()) > unit.getSimpleReach()) { // Drive me closer, I want to hit them with my sword.
                        shortestPath = new Path(deliberateAggressivePath(unit));
                        bestCombatAction.setPath(shortestPath);
                    }
                    options.add(bestCombatAction);
                } else { // They are too far away... for now.
                    AIAction charge = new AIAction(game, ActionType.MOVE_ACTION);
                    charge.setSubjectUnit(unit);
                    charge.incrementWeight();
                    charge.incrementWeight();
                    charge.incrementWeight();
                    charge.incrementWeight();

                    shortestPath = new Path(deliberateAggressivePath(unit));
                    charge.setPath(shortestPath);

                    options.add(charge);
                }
                break;

            case RECKLESS: // Run towards the nearest enemy and attack anything in sight. Fodder.

                // TODO
                options.get(0).decrementWeight();
                break;

            case STILL: // Stand still and attack anything in reach.
            case LOS_AGGRO: // Stand still but chase anything in LOS.
            case LOS_FLEE: // Stand still but run away from anything in LOS.
            case DEFENSIVE: // Huddle together with other units, ideally around choke points.
            case FLANKING: // Surround the enemy.
            case TARGET_TILE: // Move towards a specific tile.
            case TARGET_UNIT: // Follow a specific unit.
            case TARGET_OBJECT: // Focus on acquiring a chest, getting in a ballista, opening a door, etc.
                break;

            case ESCAPE: // Run towards escape tile
                abs.getRecursionHandler().recursivelySelectReachableTiles(unit.getColumnX(), unit.getRowY(), 100, unit.getMovementType());

                boolean foundAssociatedVictCon = false;
                LogicalTile targetTile = null;
                VictoryCondition associatedVictCon;

                // First, check if this unit wants to run to a specific escape tile, or just escape in general.
                for(VictoryCondition victcon : abs.conditions().getVictoryConditions()) {
                    if(victcon.victConType == VictoryConditionType.ESCAPE_ONE ||
                       victcon.victConType == VictoryConditionType.ESCAPE_MULTIPLE) {
                        if(victcon.getAssociatedUnit() == unit.rosterID) {
                            associatedVictCon = victcon;
                            targetTile = abs.getLogicalMap().getTileAtPositionXY( (int)associatedVictCon.getAssociatedCoordinateXY().x, (int) associatedVictCon.getAssociatedCoordinateXY().y);
                            foundAssociatedVictCon = true;
                            break;
                        }
                    }
                }

                // Here, there is no specific associated escape tile, and so the unit will select the first escape tile it sees.
                if(!foundAssociatedVictCon) {
                    for(LogicalTile tile : abs.reachableTiles) {
                        if(tile.tileType == LogicalTileType.OBJECTIVE_ESCAPE) {
                            targetTile = tile;
                            break;
                        }
                    }
                }

                if(targetTile != null) {
                    final Path shortPath;
                    if(abs.reachableTiles.contains(targetTile, true)) {
                        shortPath = new Path(trimPath(abs.getRecursionHandler().shortestPath(unit, targetTile, true), unit));
                    } else {
                        // look for ideal path and try to move closer
                        shortPath = abs.getRecursionHandler().xRayPath(unit, targetTile);
                    }

                    // navigate along path as far as possible
                    AIAction escapeAction = new AIAction(game, ActionType.ESCAPE_ACTION);
                    escapeAction.setSubjectUnit(unit);
                    escapeAction.setCoordinate(targetTile.getCoordinatesXY());
                    escapeAction.setPath(shortPath);

                    escapeAction.incrementWeight();
                    escapeAction.incrementWeight();
                    escapeAction.incrementWeight();
                    escapeAction.incrementWeight();
                    escapeAction.incrementWeight();

                    options.add(escapeAction);
                }
                break;

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
        Gdx.app.log("AIHandler: ", "sending action of type: " + action.getActionType() + " on " + action.getSubjectUnit().name);
        startWaiting();
        abs.executeAction(action);
    }

    private void endTurn() {
        stopThinking();
        startWaiting();
        abs.executeAction(new AIAction(game, ActionType.PASS_ACTION));
    }

    private Path deliberateAggressivePath(SimpleUnit unit) {
        // If I could go anywhere on the map, where would I want to be?
        // fill attackableEnemies list with all enemies accessible on the map, while also filling
        // reachableTiles with all accessible tiles, with movement cost considered.
        abs.getRecursionHandler().recursivelySelectReachableTiles(unit.getColumnX(), unit.getRowY(), 100, unit.getMovementType());

        // decide who you want to fight.
        final AIAction bestFight = new AIAction(evaluateBestOrWorstCombatAction(unit, true));

        Path shortestPath;

        if(bestFight.getActionType() == ActionType.ATTACK_ACTION) {

            final SimpleUnit bestMatchUp = bestFight.getObjectUnit();

            /* Since we are exclusively dealing in the context of an aggressive unit looking for a fight,
             * the unit's attack range will tell us if we need to search for a continuous path to the destination,
             * or just one within attack range, thus allowing firing ranged attacks over barriers.
             */
            boolean continuous = unit.getSimpleReach() < 2;

            /* find the shortest path to bestMatchUp, then find the furthest tile
             *  along shortestPath unit can reach this turn with its speed and move type
             */
            shortestPath = new Path(abs.getRecursionHandler().shortestPath(unit, bestMatchUp.getOccupyingTile(), continuous));

            shortestPath = new Path(trimPath(shortestPath, unit));

            // Continuous paths contain the destination tile, which in this case is occupied by our target, so we trim.
            // ^is this correct? Bloom() says path will never contain destination
            // ^Yes, this is correct -- good question though! The last tile is added by Bloom()'s helper method at the very end.
            if(shortestPath.lastTile().isOccupied()) shortestPath.shortenPathBy(1);

        } else {
            Gdx.app.log("delib path: ", "bad action type");
            Gdx.app.log("BAD ACTION OF TYPE: ", "" + bestFight.getActionType());
            shortestPath = new Path(game, unit.getOccupyingTile());
        }

        return shortestPath;

    }

    @NotNull
    private AIAction evaluateBestOrWorstCombatAction(SimpleUnit unit, boolean best) {
//        Gdx.app.log("eval", "evaluating match-ups");
//        abs.getRecursionHandler().recursivelySelectReachableTiles(unit);

        if(abs.attackableUnits.size > 0) {
            final Array<AIAction> options = new Array<>();

            for(SimpleUnit enemy : abs.attackableUnits) {
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
    private AIAction weighBestOrWorstOption(boolean best, @NotNull Array<AIAction> options) {
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

    public Path trimPath(Path path, @NotNull SimpleUnit unit) { // TODO: maybe move this to Path class file?
        final Path returnPath = new Path(path);
        float speed = unit.modifiedSimpleSpeed();
        int trim = 0;
        for(LogicalTile tile : returnPath.retrievePath()) {
            if(speed >= tile.getMovementCostForMovementType(unit.getMovementType())) {
                speed -= tile.getMovementCostForMovementType(unit.getMovementType());
            } else {
                trim++;
            }
        }
        if(trim > 0) returnPath.shortenPathBy(trim);

        return returnPath;
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
