package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;

import java.util.Objects;

public class GridConditionsHandler extends WyrConditionsHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    private boolean priorityValidated = false;

    public GridConditionsHandler(GridMetaHandler metaHandler) {
        super(new GridConditionRegister(metaHandler));
        this.h = metaHandler;
    }

//    public void declareVictoryAndFailureConditions() {}

    public void parsePriority() {
        // Don't run parsePriorty() while it's already resolving,
        // or while handlers are busy.
        if(priorityValidated) {
            Gdx.app.log("parsePriority", "already validated");
            return;
        }
        if(h.isBusy()) {
            Gdx.app.log("parsePriority", "handlers are busy");
            return;
        }
        priorityValidated = true;
        isBusy = true;

        // Decide if player is in control or if
        // computerPlayer should be invoked.

        final Array<GridUnit> holdingPriority = unitsHoldingPriority();

        final Array<GridPathfinder.Things> thingsPerPriority = new Array<>();
        for(GridUnit unit : holdingPriority) {
            thingsPerPriority.add(GridPathfinder.currentlyAccessibleTo(h.map(), unit));
        }

        for(int i = 0; i < holdingPriority.size; i++) {
            // If all is as intended then all units in priority
            // should be on the same team.
            if (Objects.requireNonNull(holdingPriority.get(i).getTeamAlignment()) == TeamAlignment.PLAYER) {

                // highlight reachable things with clickable tile
                for (GridTile tile : thingsPerPriority.get(i).tiles().keySet()) {
                    // Tiles can be "reachable" for interaction but not
                    // appropriate for moving to, for various raisins.
                    if(tile.isOccupied() || tile.isSolid()) continue;
                    tile.addEphemeralInteractable(new GridMoveInteraction(holdingPriority.get(i), thingsPerPriority.get(i).tiles().get(tile)));
                    tile.highlight();
                }

                holdingPriority.get(i).getOccupiedTile().unhighlight();

                // TODO: listener on priotiy.get(i) to stay on the same tile



                // TODO
                //  - attackables, etc

                h.input().setInputMode(GridInputHandler.InputMode.STANDARD);


            } else { // call for AI action
                Gdx.app.log("Conditions", "expected AI to run");

                h.input().setInputMode(GridInputHandler.InputMode.LOCKED);

                // TODO:
                //  Collect all actions for multiple
                //  enemies moving on the same tick,
                //  parse priority of which should
                //  move first for optimal strategy.
                h.ai().run(holdingPriority.get(i));
                isBusy = false;
                return;
            }
        }
        isBusy = false;
        // TODO: sanity checks to prevent hanging
    }

    public void prioritizeUnit(GridUnit unit) {
        h.map().clearAllHighlights();
        final GridPathfinder.Things things = GridPathfinder.currentlyAccessibleTo(h.map(), unit);
        for (GridTile tile : things.tiles().keySet()) {
            tile.addEphemeralInteractable(new GridMoveInteraction(unit, things.tiles().get(tile)));
            tile.highlight();
        }
        unit.getOccupiedTile().unhighlight();
    }

    public void invalidatePriority() {
//        Gdx.app.log("conditions", "priority invalidated");
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

    public Array<GridUnit> unifiedTurnOrder() {
        return register().unifiedTurnOrder();
    }

    public GridActor getActorByName(String name) {
        // search all props, units, and bullets for examinable with name
    }

    private void handleTurnAdvance() {

        register().advanceTurn(); // prompts units to update
        // TODO:
        //  - call turn CS triggers
        //  - call hud to update
        //  - call map to show options for turn
        //  - check if call to ai is needed

        Gdx.app.log("conditions", "turn: " + register().turnCount());
    }

    public void declareUnit(GridUnit unit, boolean readyToParse) {
        register().addToTurnOrder(unit);
        h.hud().updateTurnOrder();
        if(!readyToParse) return;
        h.map().clearAllHighlights();
        invalidatePriority();
    }

    @Override
    protected GridConditionRegister register() {
        return (GridConditionRegister) register;
    }

}
