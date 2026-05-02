package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrPriorityHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import java.util.Objects;

import static com.feiqn.wyrm.wyrefactor.helpers.ShaderState.HIGHLIGHT;
import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.rpg.RPGStatType.*;

public final class GridPriorityHandler extends WyrPriorityHandler {

    private final RPGridMetaHandler h; // It's fun to just type "h".

//    private boolean priorityValidated = false;

    public GridPriorityHandler(RPGridMetaHandler metaHandler) {
        this.h = metaHandler;
    }

    public void parsePriority() { parsePriority(null); }
    public void parsePriority(@Null RPGridUnit forUnit) {
        // Don't run unnecessarily,
        // or while other handlers are busy.
//        if(priorityValidated) {
//            Gdx.app.log("parsePriority", "already validated");
//            return;
//        }
        if(h.isBusy()) {
            Gdx.app.log("parsePriority", "handlers are busy");
            // Personal Responsibility dictates that each handler will
            // attempt to call this method again once it is no longer busy,
            // so in theory no sanity checks should be needed here.
            return;
        }
//        priorityValidated = true;
        h.input().lock();
        h.clearEphemeral();

        // Check if we are (still) in combat.
        if(!h.register().inCombat()) {
            // Combat is over, switch to FreeMove mode.
            h.input().setFreeMove();
            h.camera().follow(h.register().avatarUnit());

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

        final Array<RPGridUnit> holdingPriority = new Array<>();
        if(forUnit == null) {
            holdingPriority.addAll(unitsHoldingPriority());
        } else {
            holdingPriority.add(forUnit);
        }

        final Array<GridPathfinder.Things> thingsPerUnit = new Array<>();
        for(RPGridUnit unit : holdingPriority) {
            thingsPerUnit.add(GridPathfinder.currentlyAccessibleTo(h.map(), unit));
//            unit.getOccupiedTile().addEphemeralInteractable();
        }

        for(int i = 0; i < holdingPriority.size; i++) {
            // Decide if player is in control or if
            // ComputerPlayer should be invoked.
            // If all is as intended then all units in Priority
            // should be on the same team.
            if (Objects.requireNonNull(holdingPriority.get(i).getTeamAlignment()) == TeamAlignment.PLAYER) {
                // Set up for and await human input.

                // Here, tiles().keySet() returns an Array of all the tiles which
                // GridPathFinder has designated as within movement cost for
                // the unit at "holdingPriority.get(i)", excluding any tiles that
                // are blocked off by enemy units, or Actors that have turned Solid.
                for (GridTile tile : thingsPerUnit.get(i).tiles().keySet()) {

                    // Each enemy needs to be checked from each tile to
                    // insure complete population of possibilities.
                    for(RPGridUnit enemy : thingsPerUnit.get(i).enemies().keySet()) {
                        if(h.map().distanceBetweenTiles(tile, enemy.getOccupiedTile()) <= holdingPriority.get(i).getReach()) {
                            // This takes the tile we are currently examining, and compares
                            // the tile's distance to any enemies designated by PathFinder.
                            // If an enemy is within unit(i)'s reach from this tile, an
                            // interaction is generated to first move to this tile, then
                            // immediately attack said enemy.
                            tile.addEphemeralInteractable(new RPGridInteraction(holdingPriority.get(i)).moveThenAttack(enemy, thingsPerUnit.get(i).tiles().get(tile)));
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
                    tile.addEphemeralInteractable(new RPGridInteraction(holdingPriority.get(i)).moveThenWait(thingsPerUnit.get(i).tiles().get(tile)));
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
                holdingPriority.get(i).applyShader(HIGHLIGHT);
                // TODO: add interactions from tilesInReach(i.reach) to i

                for(RPGridUnit enemy : thingsPerUnit.get(i).enemies().keySet()) {
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

                h.input().setInputMode(RPGridInputHandler.InputMode.STANDARD);
            } else {
                // call for AI action
                h.input().setInputMode(RPGridInputHandler.InputMode.LOCKED);
                h.ai().run(holdingPriority);
                // schedule sanity check for hanging
//                Timer.schedule(new Timer.Task() {
//                    @Override
//                    public void run() {
//                        if(!isBusy()) parsePriority();
//                    }
//                }, 60);
                return;
            }
        }

    }

    public void prioritizeUnit(RPGridUnit unit) {
        h.input().focusUnit(unit);
        h.clearEphemeral();
        final GridPathfinder.Things things = GridPathfinder.currentlyAccessibleTo(h.map(), unit);
//        for(GridTile tile : things.tiles().keySet()) {
//            tile.addEphemeralInteractable(new RPGridInteraction(unit).moveThenWait(things.tiles().get(tile)));
//            tile.highlight();
//        }
        for(GridTile tile : things.tiles().keySet()) {
            for(RPGridUnit enemy : things.enemies().keySet()) {
                if(h.map().distanceBetweenTiles(tile, enemy.getOccupiedTile()) <= unit.getReach()) {
                    tile.addEphemeralInteractable(new RPGridInteraction(unit).moveThenAttack(enemy, things.tiles().get(tile)));
                    tile.highlight();
                }
            }
            if(tile.isOccupied() || tile.isSolid()) continue;

            // TODO:
            //  discrete behavior for encountering allies or props
            //  at this stage.
            tile.addEphemeralInteractable(new RPGridInteraction(unit).moveThenWait(things.tiles().get(tile)));
            tile.highlight();
        }
        unit.getOccupiedTile().unhighlight();
        unit.applyShader(HIGHLIGHT);
    }

    public Array<RPGridUnit> unitsHoldingPriority() { return unitsHoldingPriority(false); }
    private Array<RPGridUnit> unitsHoldingPriority(boolean recursed) {
        // Register should already have sorted the UnifiedTurnOrder
        // such that units of the same speed are arranged in order
        // of PLAYER -> ENEMY -> ALLY -> STRANGER.
        final Array<RPGridUnit> returnValue = new Array<>();
        int tick = -1;
        TeamAlignment teamPriority = null;
        for(RPGridUnit unit : h.register().unifiedTurnOrder()) {
            if(tick == -1 && unit.canMove()) {
                returnValue.add(unit);
                tick = unit.getModifiedStatValue(SPEED);
                teamPriority = unit.getTeamAlignment();
            } else {
                if(unit.canMove() && unit.getTeamAlignment() == teamPriority) {
                    if(unit.getModifiedStatValue(SPEED) == tick) {
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
            h.register().advanceTurn();
            return unitsHoldingPriority(true);
        }
        Gdx.app.log("unitsHoldingPriority", "error");
        return new Array<>();
    }

}
