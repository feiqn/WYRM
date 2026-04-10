package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.gameplay.combat.OLD_CombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.StatType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionRegister;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

import java.util.Comparator;

public final class GridConditionRegister extends WyrConditionRegister {

    // TODO:
    //  consider if this should remain nested within ConditionsHandler
    //  or become it's own MetaHandle call as just GridRegister (.register()),
    //  containing read data for the active screen / game world.
    //  This would likely be in tandem with rebranding ConditionsHandler as
    //  something like PriorityHandler, TurnHandler, ActivityHandler, or simply
    //  StateHandler.

    private boolean fogOfWar     = false;
    private boolean ironModeBTW  = false;

    // TODO: two following variables converted to
    //  on-the-fly method calls.
//    protected boolean terminalVictoryConditionMet = false;
//    protected boolean terminalFailureConditionMet = false;

    private int currentTurnNumber = 0;

    private final Array<GridActor> bulletsOnStage   = new Array<>();
    private final Array<GridProp>  propsOnStage     = new Array<>();
    private final Array<GridUnit>  unifiedTurnOrder = new Array<>();

    private final GridMetaHandler h; // It's fun to just type "h".

//    protected Array<WyrVictoryCondition> victoryConditions = new Array<>();
//    public static Array<FailureCondition> failureConditions;

    private static OLD_CombatHandler.IronMode ironMode;

    public GridConditionRegister(GridMetaHandler handler) {
        this.h = handler;
    }

    public void advanceTurn() {
        currentTurnNumber++;
        for(GridUnit unit : unifiedTurnOrder) {
            unit.resetForNextTurn();
        }
    }
    public void addFog() { fogOfWar = true; }
    public void addToTurnOrder(GridUnit unit) {
        if(!unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.add(unit);
            calculateTurnOrder();
        }
    }
    public void removeFromTurnOrder(GridUnit unit) {
        if(unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.removeValue(unit,true);
            calculateTurnOrder();
        }
    }
    public void addProp(GridProp prop) {
        if(!this.propsOnStage.contains(prop, true)) propsOnStage.add(prop);
    }
    public void removeProp(GridProp prop) {

    }
    private void calculateTurnOrder() {
        // I'm not even gonna lie to you.
        // I am using a lame language model for this.

        unifiedTurnOrder.sort(new Comparator<GridUnit>() { // new What, now?
            @Override
            public int compare(GridUnit a, GridUnit b) {
                // 1) Speed, descending
                int speedDiff = b.modifiedStatValue(StatType.SPEED) - a.modifiedStatValue(StatType.SPEED);
                if (speedDiff != 0) return speedDiff;

                // 2) Team alignment priority
                return teamPriority(teamPriority(teamPriority(a.getTeamAlignment()) - teamPriority(b.getTeamAlignment())));
            }

            private TeamAlignment teamPriority(int i) { // okay, I guess that part makes sense at least...
                switch(i) {
                    case 0: return TeamAlignment.PLAYER;
                    case 1: return TeamAlignment.ENEMY;
                    case 2: return TeamAlignment.ALLY;
                    default: return TeamAlignment.OTHER;
                }
            }

            private int teamPriority(TeamAlignment ta) {
                switch (ta) {
                    case PLAYER: return 0;
                    case ENEMY:  return 1;
                    case ALLY:   return 2;
                    case OTHER:  return 3;
                    default:     return 4;
                }
            }
        });

//        Gdx.app.log("con reg", "uto size: " + unifiedTurnOrder.size);

        h.map().clearAllHighlights(); // TODO: if something breaks, comment these out
        h.conditions().invalidatePriority();
    }

//    public void addVictoryCondition(WyrVictoryCondition condition) {}
//    public void addFailureCondition(WyrFailureCondition condition) {}

    public void satisfyVictoryCondition() {}
    // TODO: victory and failure conditions will be
    //  combined into one shared type.
    public void satisfyFailureCondition() {}

    public Array<GridUnit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public int turnCount() { return currentTurnNumber; }
//    public int tickCount() { return 0; }
//    public Array<VictoryCondition> victoryConditions() { return victoryConditions; }
//    public boolean terminalFailureConditionMet() { return terminalFailureConditionMet; }
//    public boolean terminalVictoryConditionMet() { return terminalVictoryConditionMet; }
    public int currentTurnNumber() { return currentTurnNumber; }
    public boolean hasFog() { return fogOfWar; }
    public boolean inIronMode() { return ironModeBTW; }
    public boolean inCombat() {
        for(GridUnit unit : unifiedTurnOrder) {
            if(unit.getTeamAlignment() == TeamAlignment.ENEMY || unit.getTeamAlignment() == TeamAlignment.OTHER) {
                return true;
            }
        }
        return false;
    }
    public GridUnit avatarUnit() {
        for(GridUnit u : unifiedTurnOrder) {
            if(u.getRosterID() == UnitIDRoster.LEIF) return u;
        }
        return unifiedTurnOrder.get(0);
    }
}
