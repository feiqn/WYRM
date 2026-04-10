package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.grid.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import java.util.Objects;

public final class GridConditionsHandler extends WyrConditionsHandler<GridConditionRegister> {

    // TODO: rebrand as StateHandler?

    private final GridMetaHandler h; // It's fun to just type "h".

    private boolean priorityValidated = false;

    public GridConditionsHandler(GridMetaHandler metaHandler) {
        super(new GridConditionRegister(metaHandler));
        this.h = metaHandler;
    }

//    public void declareVictoryAndFailureConditions() {}

    public void parsePriority() {
        // Don't run while it's already resolving,
        // or while other handlers are busy.
        if(priorityValidated) {
            Gdx.app.log("parsePriority", "already validated");
            return;
        }
        if(h.isBusy()) {
            Gdx.app.log("parsePriority", "handlers are busy");
            // Personal Responsibility dictates that each handler will
            // attempt to call this method again once it is no longer busy,
            // so in theory no sanity checks should be needed here.
            return;
        }
        priorityValidated = true;

        // Check if we are (still) in combat.
        if(!register.inCombat()) {
            // Combat is over, switch to FreeMove mode.
            h.input().setFreeMove();
            h.camera().follow(register.avatarUnit());

            // TODO:
            //  - prompt HUD to adjust for free move (turn order, etc)
            //  - prompt actors() to enter a passive state where they can be interacted with freely (talk, trade, etc.)
            //  ELSEWHERE:
            //  - Set up listeners for FreeMove state, including mapping cursor position to world objects (see OLD_DATA)

            return;

        } else {
            // returns immediately if already set to combat.
            h.input().setToCombat();
        }

        // Decide if player is in control or if
        // ComputerPlayer should be invoked.
        final Array<GridUnit> holdingPriority = unitsHoldingPriority();

        final Array<GridPathfinder.Things> thingsPerPriority = new Array<>();
        for(GridUnit unit : holdingPriority) {
            thingsPerPriority.add(GridPathfinder.currentlyAccessibleTo(h.map(), unit));
        }

        for(int i = 0; i < holdingPriority.size; i++) {
            // If all is as intended then all units in priority
            // should be on the same team.
            if (Objects.requireNonNull(holdingPriority.get(i).getTeamAlignment()) == TeamAlignment.PLAYER) {
                // Set up for and await human input.

                // Here, tiles().keySet() returns an Array of all the tiles which
                // GridPathFinder has designated as within movement cost for
                // the unit at "holdingPriority.get(i)", excluding any tiles that
                // are blocked off by enemy units, or Actors that have turned Solid;
                // but including tiles which are occupied by allies, or other Actors
                // that can be traversed through, such as an open Door.
                for (GridTile tile : thingsPerPriority.get(i).tiles().keySet()) {

                    // Each enemy needs to be checked from each tile to
                    // insure complete population of possibilities.
                    for(GridUnit enemy : thingsPerPriority.get(i).enemies().keySet()) {
                        if(h.map().distanceBetweenTiles(tile, enemy.getOccupiedTile()) <= holdingPriority.get(i).getReach()) {
                            // This takes the tile we are currently examining, and compares
                            // the tile's distance to any enemies designated by PathFinder.
                            // If an enemy is within unit(i)'s reach from this tile, an
                            // interaction is generated to first move to this tile, then
                            // immediately attack said enemy.
                            tile.addEphemeralInteractable(new GridInteraction(holdingPriority.get(i)).moveThenAttack(enemy, thingsPerPriority.get(i).tiles().get(tile)));
                            tile.highlight();
                        }
                    }

                    // Tiles that are occupied by allies or certain objects
                    // can still be passed through, but not stopped on.
                    // Therefore, they are included in tiles().keySet(),
                    // but should not be populated with a Move interaction.
                    if(tile.isOccupied() || tile.isSolid()) continue;
                    // TODO:
                    //  discrete behavior for encountering allies or props
                    //  at this stage.
                    tile.addEphemeralInteractable(new GridInteraction(holdingPriority.get(i)).moveThenWait(thingsPerPriority.get(i).tiles().get(tile)));
                    tile.highlight();

                }

                // Current design theory thinks it's weird and confusing to
                // highlight the tile that unit(i) is on; and so instead,
                // the highlighter on the tile is removed, while the Actor is
                // itself populated with the appropriate Ephemeral Interactions
                // for the tile (like any other tile in this set), and then
                // inputs().Listeners.playerUnitClickListener (left / right
                // respectively) is pre-built with contextual behavior to react
                // when Combat is active && unit(i) is holding priority &&
                // input mode is STANDARD.
                holdingPriority.get(i).getOccupiedTile().unhighlight();
                // TODO: listener on (i) to open context action or just wait

                for(GridUnit enemy : thingsPerPriority.get(i).enemies().keySet()) {
                    // TODO:
                    //  Each enemy within the keySet is tied to a GridPath value
                    //  which represents the shortest path to the first tile from
                    //  which PathFinder saw said enemy and marked it as reachable.
                    //  Similar to the above listener for player units,
                    //  enemyUnitClickListener(s) should be set up to contextually
                    //  display and trigger the Actor's interactions, after they are
                    //  programmatically added here.
                    //  I.E.,
                    //  Here, we would add MOVE_ATTACK interactions to each enemy,
                    //  with the associated path being the corresponding value in
                    //  enemies() for enemy. Then, clicking the enemy would fire
                    //  the Actor's interactions based on how many options were present.
                    //  All Actors should always have an Examine interaction, so if there
                    //  is only 1 interaction, it would auto-fire as a Static Examine.
                    //  If there are exactly 2 interactions, a standard click would auto-fire
                    //  the NON-Examine interaction.
                    //  If 3 or more, left clicking either opens the context menu same as right
                    //  clicking normally, or perhaps later we will add a quick-select option
                    //  using the scroll-wheel.
                }




                // TODO
                //  - attackables, etc

                h.input().setInputMode(GridInputHandler.InputMode.STANDARD);
            } else {
                // call for AI action
                Gdx.app.log("Conditions", "expected AI to run");

                h.input().setInputMode(GridInputHandler.InputMode.LOCKED);

                // TODO:
                //  Collect all actions for multiple
                //  enemies moving on the same tick,
                //  parse priority of which should
                //  move first for optimal strategy.
                h.ai().run(holdingPriority.get(i));
                return;
            }
        }
        // TODO: sanity checks to prevent hanging
    }

    public void prioritizeUnit(GridUnit unit) {
        h.map().clearAllHighlights();
        h.input().focusUnit(unit);
        final GridPathfinder.Things things = GridPathfinder.currentlyAccessibleTo(h.map(), unit);
        for (GridTile tile : things.tiles().keySet()) {
            tile.addEphemeralInteractable(new GridInteraction(unit).moveThenWait(things.tiles().get(tile)));
            tile.highlight();
        }
        unit.getOccupiedTile().unhighlight();
    }

    public void invalidatePriority() {
        priorityValidated = false;
        parsePriority();
    }

    public Array<GridUnit> unitsHoldingPriority() { return unitsHoldingPriority(false); }
    private Array<GridUnit> unitsHoldingPriority(boolean recursed) {
        final Array<GridUnit> returnValue = new Array<>();
        int tick = -1;
        for(GridUnit unit : unifiedTurnOrder()) {
            if(tick == -1) {
                if(unit.canMove()) {
                    returnValue.add(unit);
                    tick = unit.modifiedStatValue(StatType.SPEED);
                }
            } else {
                if(unit.canMove()) {
                    if(unit.modifiedStatValue(StatType.SPEED) == tick) {
                        returnValue.add(unit);
                    } else {
                        break;
                    }
                }
            }
        }
        h.hud().updateTurnOrder();
        if(returnValue.size > 0) return returnValue;
        if(!recursed) {
            // I can imagine a situation in which
            // all units in battle have negative AP
            // and require several turns to restore
            // back, but the game nonetheless has not
            // hung.
            // I'll leave this for now as some training
            // wheels, then take it out later.
            handleTurnAdvance();
            return unitsHoldingPriority(true);
        }
        Gdx.app.log("unitsHoldingPriority", "error");
        return new Array<>();
    }

    public Array<GridUnit> unifiedTurnOrder() { return register.unifiedTurnOrder(); }

//    public GridActor getActorByName(String name) {
        // search all props, units, and bullets for examinable with name
//    }

    private void handleTurnAdvance() {

        register.advanceTurn(); // prompts units to update
        // TODO:
        //  - call turn CS triggers
        //  - call hud to update
        //  - call map to show options for turn
        //  - check if call to ai is needed

        Gdx.app.log("conditions", "turn: " + register.turnCount());
    }

    public void declareUnit(GridUnit unit, boolean readyToParse) {
        // TODO: move to register if / when exposed as standalone
        register.addToTurnOrder(unit);
        h.hud().updateTurnOrder();
        if(!readyToParse) return;
        h.map().clearAllHighlights();
        invalidatePriority();
    }


}
