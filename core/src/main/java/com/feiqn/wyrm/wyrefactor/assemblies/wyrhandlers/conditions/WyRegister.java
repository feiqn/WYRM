package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.TeamAlignment;

import java.util.Comparator;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.StatType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.handlers;

public class WyRegister {

    private boolean fogOfWar     = false;
    private boolean ironModeBTW  = false;

    // TODO: two following variables converted to
    //  on-the-fly method calls.
//    protected boolean terminalVictoryConditionMet = false;
//    protected boolean terminalFailureConditionMet = false;

    private int currentTurnNumber = 0;

    private RPGridTile hoveredTile  = null;
    private WyrActor hoveredActor = null;

//    private final Array<WyrActor.Bullet> bulletsOnStage   = new Array<>();
//    private final Array<WyrActor.Prop> propsOnStage     = new Array<>();
    private final Array<WyrActor.Unit> unifiedTurnOrder = new Array<>();

    private final Array<WyrWinCon> winCons = new Array<>();

//    private static OLD_CombatHandler.IronMode ironMode;

    public WyRegister() {

    }

    public void advanceTurn() {
        currentTurnNumber++;
        for(WyrActor.Unit unit : unifiedTurnOrder) {
            unit.resetForNextTurn();
        }
        // TODO:
        //  - call turn CS triggers
        //  - call hud to update

        handlers.cutscenes().checkTurnTriggers(currentTurnNumber);

        Gdx.app.log("register", "turn: " + turnCount());
        handlers.priority().parsePriority();
    }

    public void addFog()   { fogOfWar = true;  }
    public void clearFog() { fogOfWar = false; }

    private void addToTurnOrder(WyrActor.Unit unit) {
        if(!unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.add(unit);
            sortTurnOrder();
        }
    }
    public void removeFromTurnOrder(WyrActor.Unit unit) {
        if(unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.removeValue(unit,true);
            sortTurnOrder();
        }
        handlers.hud().updateTurnOrder();
    }

    public void declareUnit(WyrActor.Unit unit) {
        addToTurnOrder(unit);
        handlers.hud().updateTurnOrder();
    }

//    public void registerProp(WyrActor prop) {
//        if(!this.propsOnStage.contains(prop, true)) propsOnStage.add(prop);
//    }
//    public void delistProp(WyrActor prop) {

//    }

    private void sortTurnOrder() {
        // I'm not even gonna lie to you.
        // I am using a lame language model for this.

        unifiedTurnOrder.sort(new Comparator<WyrActor.Unit>() { // new What, now?
            @Override
            public int compare(WyrActor.Unit a, WyrActor.Unit b) {
                // 1) Speed, descending
                int speedDiff = b.getModifiedStatValue(SPEED) - a.getModifiedStatValue(SPEED);
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
//        if(currentTurnNumber > 0) handlers.clearAndInvalidate(); // TODO: if something breaks, comment this out
    }

    public void registerWinCon(WyrWinCon condition) { winCons.add(condition); }
    public WyrActor getActorByName(String name) {
//     search all props, units, and bullets for examinable with name
        return null;
    }
    public Array<WyrActor.Unit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public int turnCount() { return currentTurnNumber; }
    public int tickCount() { return handlers.priority().unitsHoldingPriority().get(0).getModifiedStatValue(SPEED); }
    public Array<WyrWinCon> revealedVictoryConditions() {
        final Array<WyrWinCon> rV = new Array<>();
        for(WyrWinCon c : winCons) {
            if(c.isRevealed()) {
                rV.add(c);
            }
        }
        return rV;
    }
    public boolean terminalFailureConditionMet() { return false; }
    public boolean terminalVictoryConditionMet() { return false; }
    public int currentTurnNumber() { return currentTurnNumber; }
    public boolean hasFog() { return fogOfWar; }
    public boolean inIronMode() { return ironModeBTW; }
    public boolean inCombat() {
        Gdx.app.log("register", "uto size: " + unifiedTurnOrder.size);
        for(WyrActor.Unit unit : unifiedTurnOrder) {
            if(unit.getTeamAlignment() == TeamAlignment.ENEMY || unit.getTeamAlignment() == TeamAlignment.STRANGER) {
                return true;
            }
        }
        return false;
    }
    public WyrActor.Unit avatarUnit() {
        for(WyrActor.Unit u : unifiedTurnOrder) {
            if(u.getCharacterID() == Wyr.Character.Name.Leif) return u;
        }
        return unifiedTurnOrder.get(0);
    }
}
