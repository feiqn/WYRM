package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions.GridMoveInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

import java.util.Objects;

public class GridConditionsHandler extends WyrConditionsHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridConditionsHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD, new GridConditionRegister());
        this.h = metaHandler;
    }

//    public void declareVictoryAndFailureConditions() {}

    public void parsePriority() {
        // Decide if player is in control or if
        // computerPlayer should be invoked.
        final Array<GridUnit> priority = unitsHoldingPriority();
        final Array<GridPathfinder.Things> things = new Array<>();
        for(GridUnit unit : priority) {
            things.add(GridPathfinder.currentlyAccessibleTo(unit));
        }
        for(int i = 0; i < priority.size; i++) {
            // If all is as intended then all units in priority
            // should be on the same team.
            if (Objects.requireNonNull(priority.get(i).teamAlignment()) == TeamAlignment.PLAYER) {
                // highlight reachable things
                for (GridTile tile : things.get(i).tiles().keySet()) {
                    tile.addInteractable(new GridMoveInteraction(h, priority.get(i), things.get(i).tiles().get(tile)));
                    tile.highlight(true);
                }
                // TODO
                //  - attackables, etc
            } else {// call for AI action
                // TODO:
                //  Collect all actions for multiple
                //  enemies moving on the same tick,
                //  parse priority of which should
                //  move first for optimal strategy.
                h.ai().run(priority.get(i));
                return;
            }
        }
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
        return null;
    }

    public Array<GridUnit> unifiedTurnOrder() {
        return register().unifiedTurnOrder();
    }

    private void handleTurnAdvance() {
        register().advanceTurn(); // prompts units to update
        // TODO:
        //  - call turn CS triggers
        //  - call hud to update
        //  - call map to show options for turn
        //  - check if call to ai is needed
    }

    @Override
    protected GridConditionRegister register() {
        return (GridConditionRegister) register;
    }

}
