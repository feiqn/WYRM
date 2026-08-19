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
        // Don't run while other handlers are busy.
        if(handlers.isBusy()) {
            // Personal Responsibility dictates that each handler will
            // attempt to call this method again once it is no longer busy,
            // so in theory no sanity checks should be needed here.
            return false;
        }
//        isBusy = true;
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
            for(WyrActor unit : statePriority) {
                if(unit.canMoveOrAct()) {
                    populateInteractions(unit);
                }
            }
        } else {
//            for(WyrActor actor : statePriority) {
//                stateThings.put(actor, GridPathfinder.currentlyAccessibleTo(actor));
//                actor.setSpotlighting(true);
//            }
            isBusy = false;
            handlers.ai().run(statePriority);
            return true;
        }
        handlers.input().setInputMode(InputMode.STANDARD);
        isBusy = false;
        return true;
    }

    private void populateInteractions(WyrActor forUnit) {
        final GridPathfinder.Things accessible = stateThings(forUnit);

//        final Array<WyrTile> tilesInScope = new Array<>();

//        forUnit.getOccupiedTile().deriveInteractions(forUnit);

        for(WyrTile tile : accessible.walkableTiles().keySet()) {

            tile.highlight().setZ(forUnit.getZIndex()-1);

            tile.deriveInteractions(forUnit, tile, accessible.walkableTiles().get(tile), accessible.getTilesTouchableFromTile(tile));

//            accessible.touchableTiles().remove(tile);

//            if(!tilesInScope.contains(tile, true)) tilesInScope.add(tile);
        }

        forUnit.applyShader(HIGHLIGHT);

//        for(WyrActor actor : accessible.actors()) {
//            if(!tilesInScope.contains(actor.getOccupiedTile(), true)) {
//                tilesInScope.add(actor.getOccupiedTile());
//                actor.getOccupiedTile().highlight().red().setZ(actor.getZIndex()-1);
//            }
//        }

//        for(WyrTile tile : tilesInScope) {
//            tile.deriveInteractions(forUnit);
//        }

        for(WyrTile tile : accessible.exclusivelyTouchableTiles()) {
//            if(!accessible.walkableTiles().containsKey(tile))
                tile.highlight().red().setZ(forUnit.getZIndex()-1);
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

    // todo: TeamPriority


    public GridPathfinder.Things stateThings(WyrActor forActor) {
        if(stateThings.containsKey(forActor)) {
            return stateThings.get(forActor);
        }
        stateThings.put(forActor, GridPathfinder. currentlyAccessibleTo(forActor));
        return stateThings.get(forActor);
    }

    public HashMap<WyrActor, GridPathfinder.Things> stateThings() { return stateThings; }

    public void clearState() {
        statePriority.clear();
        stateThings.clear();
    }

    public @Null WyrActor getFocusedActor() {
        if(unitsHoldingPriority().size == 1) return unitsHoldingPriority().get(0);
        return handlers.register().getSelectedActor();
    }
}
