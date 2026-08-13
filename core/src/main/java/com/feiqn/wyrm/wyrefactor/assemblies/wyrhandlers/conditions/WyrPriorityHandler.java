package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;

import java.util.HashMap;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MoveControlMode.TURN_BASED;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.ShaderState.*;

public class WyrPriorityHandler extends WyrHandler {

//    private WyrActor focusedActor = null;

//    private boolean internalStateIsValid = false;

    private final Array<WyrActor> statePriority = new Array<>();

    protected final HashMap<WyrActor, GridPathfinder.Things> stateThings = new HashMap<>();

    public WyrPriorityHandler() {}

    public boolean parsePriority() { return  parsePriority(null); }
    public boolean parsePriority(@Null WyrActor.Unit forUnit) {
//        Gdx.app.log("priority", "start");
        // Don't run while other handlers are busy.
        if(handlers.isBusy()) {
            // Personal Responsibility dictates that each handler will
            // attempt to call this method again once it is no longer busy,
            // so in theory no sanity checks should be needed here.
//            Gdx.app.log("priority", "busy handler");
            return false;
        }
        isBusy = true;
        handlers.input().lock();
        handlers.clearMapState();
        clearState();

        // Check if we are (still) in combat.
        if(!handlers.register().inCombat()) {
            // Combat is over, switch to FreeMove mode.
            Gdx.app.log("priority", "no combat");
            handlers.input().setFreeMove();
            handlers.camera().follow(handlers.register().avatarUnit());

            // TODO:
            //  - prompt HUD to adjust for free move (turn order, etc)
            //  - prompt actors() to enter a passive state where they can be interacted with freely (talk, trade, etc.)
            //  ELSEWHERE:
            //  - Set up listeners for FreeMove state, including mapping cursor position to world objects (see OLD_DATA)

            isBusy = false;
            return true;

        } else {
            handlers.input().setMoveControl(TURN_BASED);
        }

        if(forUnit == null) {
            statePriority.addAll(unitsHoldingPriority());
//            handlers.register().clearSelectedActor();
        } else {
            statePriority.add(forUnit);
//            handlers.register().setSelectedActor(forUnit);
        }

        if(statePriority.isEmpty()) {
            handlers.register().advanceTurn();
            isBusy = false;
            return false;
        }


        // By this point all units holding priority are
        // assured to be on the same team
        if(statePriority.get(0).getTeamAlignment() == TeamAlignment.PLAYER) {
            // Set up for and await human input.
//            Gdx.app.log("priority", "populating");
            for(WyrActor unit : statePriority) {
                populateInteractions(unit);
//                for(WyrTile tile : handlers.map().getAllTiles()) {
//                    tile.deriveInteractions(unit, true);
//                }
            }
        } else {
            for(WyrActor actor : statePriority) {
                stateThings.put(actor, GridPathfinder.currentlyAccessibleTo(actor));
            }
            handlers.ai().run(statePriority);
        }
//        internalStateIsValid = true;
        handlers.input().setInputMode(InputMode.STANDARD);
        isBusy = false;
//        Gdx.app.log("priority", "done");
        return true;
    }

    private void populateInteractions(WyrActor forUnit) {
        final GridPathfinder.Things accessible = GridPathfinder.currentlyAccessibleTo(forUnit);
        stateThings.put(forUnit, accessible);

        final Array<WyrTile> tilesInScope = new Array<>();

//        if(forUnit.stats().canAct()) {
//            for(WyrActor enemy : accessible.enemies().keySet()) {
//                enemy.addEphemeralInteraction(new WyrInteraction(forUnit).moveThenAttack(enemy, accessible.enemies().get(enemy)));
//            }
//        }

        // Here, tiles().keySet() returns an Array of all the tiles which
        // GridPathFinder has designated as within movement cost for
        // the unit at "holdingPriority.get(i)", excluding any tiles that
        // are blocked off by enemy units, or Actors that have turned Solid.
        for(WyrTile tile : accessible.tiles().keySet()) {

//           final Array<WyrInteraction> derivedInteractions = tile.deriveInteractions(focusedActor, true);

            // Each enemy needs to be checked from each tile to
            // insure complete population of possibilities.
//            if(forUnit.stats().canAct()) {
//                for(WyrActor enemy : accessible.enemies().keySet()) {
//                    if(handlers.map().distanceBetweenTiles(tile, enemy.getOccupiedTile()) <= forUnit.getReach()) {
                        // This takes the tile we are currently examining, and compares
                        // the tile's distance to any enemies designated by PathFinder.
                        // If an enemy is within unit(i)'s reach from this tile, an
                        // interaction is generated to first move to this tile, then
                        // immediately attack said enemy.
//                        tile.addEphemeralInteractable(new WyrInteraction(forUnit).moveThenAttack(enemy, accessible.tiles().get(tile)));
//                        tile.highlight();
//                    }
//                }
//            }


            // Tiles that are occupied by allies or certain objects
            // can still be passed through, but not stopped on.
            // Therefore, they are included in tiles().keySet(),
            // but should not be populated with a Move interaction.
//            if(tile.hasUnit() || tile.groundIsObstructed(forUnit)) continue;

//            tile.addEphemeralInteractable(new WyrInteraction(forUnit).moveThenWait(accessible.tiles().get(tile)));
            tile.highlight();
//            tile.deriveInteractions(forUnit, true);

            if(!tilesInScope.contains(tile, true)) tilesInScope.add(tile);
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

//        forUnit.getOccupiedTile().standardize();

//        forUnit.getOccupiedTile().unhideHighlight();

//        forUnit.getOccupiedTile().unHighlight();
        forUnit.applyShader(HIGHLIGHT);

        for(WyrActor actor : accessible.actors()) {
            // If an actor in accessible.actors is also already on a tile
            // also in accessible.tiles, that actor will have already been
            // checked by WyrTile's recursive derivation.
//            if(!accessible.tiles().containsKey(actor.getOccupiedTile())) {
//                actor.deriveInteractions(forUnit);
//            }

            if(!tilesInScope.contains(actor.getOccupiedTile(), true)) tilesInScope.add(actor.getOccupiedTile());

        }

        // TODO: add interactions from tilesInReach(i.reach) to i

        for(WyrActor enemy : accessible.enemies().keySet()) {
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

        for(WyrTile tile : tilesInScope) {
            tile.deriveInteractions(forUnit);
        }

    }

    public Array<WyrActor> unitsHoldingPriority() {
        // Register should already have sorted the UnifiedTurnOrder
        // such that units of the same speed are arranged in order
        // of PLAYER -> ENEMY -> ALLY -> STRANGER.

        final Array<WyrActor> returnValue = new Array<>();
        int tick = -1;
        TeamAlignment teamPriority = null;
        for(WyrActor.Unit unit : handlers.register().unifiedTurnOrder()) {
            if(tick == -1 && unit.canMoveOrAct()) {
                returnValue.add(unit);
                tick = unit.stats().getNetValue(SPEED);
                teamPriority = unit.getTeamAlignment();
            } else {
                if(unit.canMoveOrAct() && unit.getTeamAlignment() == teamPriority) {
                    if(unit.stats().getNetValue(SPEED) == tick) {
                        returnValue.add(unit);
                    } else {
                        break;
                    }
                }
            }
        }
        handlers.hud().updateTurnOrder();
        return returnValue;
    }


    public GridPathfinder.Things stateThings(WyrActor forActor) {
        if(stateThings.containsKey(forActor)) {
            return stateThings.get(forActor);
        }
        stateThings.put(forActor, GridPathfinder.currentlyAccessibleTo(forActor));
        return stateThings.get(forActor);
    }

    public HashMap<WyrActor, GridPathfinder.Things> stateThings() { return stateThings; }

    public void clearState() {
//        internalStateIsValid = false;
        statePriority.clear();
        stateThings.clear();
    }

    public @Null WyrActor getFocusedActor() {
        if(unitsHoldingPriority().size == 1) return unitsHoldingPriority().get(0);
        return handlers.register().getSelectedActor();
    }
}
