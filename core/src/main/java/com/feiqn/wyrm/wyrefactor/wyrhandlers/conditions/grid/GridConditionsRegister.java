package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.gameplay.combat.OLD_CombatHandler;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpgrid.RPGridStats.RPGStatType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsRegister;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import java.util.Comparator;

public final class GridConditionsRegister extends WyrConditionsRegister {

    private boolean fogOfWar     = false;
    private boolean ironModeBTW  = false;

    // TODO: two following variables converted to
    //  on-the-fly method calls.
//    protected boolean terminalVictoryConditionMet = false;
//    protected boolean terminalFailureConditionMet = false;

    private int currentTurnNumber = 0;

    private final Array<RPGridActor> bulletsOnStage   = new Array<>();
    private final Array<RPGridProp>  propsOnStage     = new Array<>();
    private final Array<RPGridUnit>  unifiedTurnOrder = new Array<>();

    private final RPGridMetaHandler h; // It's fun to just type "h".

//    protected Array<WyrVictoryCondition> victoryConditions = new Array<>();
//    public static Array<FailureCondition> failureConditions;

    private static OLD_CombatHandler.IronMode ironMode;

    public GridConditionsRegister(RPGridMetaHandler metaHandler) {
        this.h = metaHandler;
    }

    public void advanceTurn() {
        currentTurnNumber++;
        for(RPGridUnit unit : unifiedTurnOrder) {
            unit.resetForNextTurn();
        }
        // TODO:
        //  - call turn CS triggers
        //  - call hud to update

        Gdx.app.log("register", "turn: " + turnCount());
    }

    public void addFog()   { fogOfWar = true;  }
    public void clearFog() { fogOfWar = false; }

    private void addToTurnOrder(RPGridUnit unit) {
        if(!unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.add(unit);
            sortTurnOrder();
        }
    }
    private void removeFromTurnOrder(RPGridUnit unit) {
        if(unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.removeValue(unit,true);
            sortTurnOrder();
        }
    }

    public void declareUnit(RPGridUnit unit) {
        addToTurnOrder(unit);
        h.hud().updateTurnOrder();
    }
    public void delistUnit(RPGridUnit unit) {

    }

    public void registerProp(RPGridProp prop) {
        if(!this.propsOnStage.contains(prop, true)) propsOnStage.add(prop);
    }
    public void delistProp(RPGridProp prop) {

    }

    private void sortTurnOrder() {
        // I'm not even gonna lie to you.
        // I am using a lame language model for this.

        unifiedTurnOrder.sort(new Comparator<RPGridUnit>() { // new What, now?
            @Override
            public int compare(RPGridUnit a, RPGridUnit b) {
                // 1) Speed, descending
                int speedDiff = b.getModifiedStatValue(RPGStatType.SPEED) - a.getModifiedStatValue(RPGStatType.SPEED);
                if (speedDiff != 0) return speedDiff;

                // 2) Team alignment priority
                return teamPriority(teamPriority(teamPriority(a.getTeamAlignment()) - teamPriority(b.getTeamAlignment())));
            }

            private TeamAlignment teamPriority(int i) { // okay, I guess that part makes sense at least...
                switch(i) {
                    case 0: return TeamAlignment.PLAYER;
                    case 1: return TeamAlignment.ENEMY;
                    case 2: return TeamAlignment.ALLY;
                    default: return TeamAlignment.STRANGER;
                }
            }

            private int teamPriority(TeamAlignment ta) {
                switch (ta) {
                    case PLAYER: return 0;
                    case ENEMY:  return 1;
                    case ALLY:   return 2;
                    case STRANGER:  return 3;
                    default:     return 4;
                }
            }
        });

        // Turn 0 is a setup turn where nothing should happen.
        // Once all setup for the Screen is complete,
        // priority can be manually invalidated by Screen.
        if(currentTurnNumber > 0) h.clearAndInvalidate(); // TODO: if something breaks, comment this out
    }

//    public void addVictoryCondition(WyrVictoryCondition condition) {}
//    public void addFailureCondition(WyrFailureCondition condition) {}

    public void satisfyVictoryCondition() {}
    // TODO: victory and failure conditions will be
    //  combined into one shared type.
    public void satisfyFailureCondition() {}

    public RPGridActor getActorByName(String name) {
//     search all props, units, and bullets for examinable with name
        return null;
    }
    public Array<RPGridUnit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public int turnCount() { return currentTurnNumber; }
//    public int tickCount() { return 0; }
//    public Array<VictoryCondition> victoryConditions() { return victoryConditions; }
//    public boolean terminalFailureConditionMet() { return terminalFailureConditionMet; }
//    public boolean terminalVictoryConditionMet() { return terminalVictoryConditionMet; }
    public int currentTurnNumber() { return currentTurnNumber; }
    public boolean hasFog() { return fogOfWar; }
    public boolean inIronMode() { return ironModeBTW; }
    public boolean inCombat() {
        for(RPGridUnit unit : unifiedTurnOrder) {
            if(unit.getTeamAlignment() == TeamAlignment.ENEMY || unit.getTeamAlignment() == TeamAlignment.STRANGER) {
                return true;
            }
        }
        return false;
    }
    public RPGridUnit avatarUnit() {
        for(RPGridUnit u : unifiedTurnOrder) {
            if(u.getRosterID() == UnitIDRoster.LEIF) return u;
        }
        return unifiedTurnOrder.get(0);
    }
}
