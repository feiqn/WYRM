package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.TeamAlignment;

import java.util.Comparator;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.handlers;

public class WyRegister {

    private boolean fogOfWar     = false;
    private boolean ironModeBTW  = false;

    private int currentTurnNumber = 0;

    private RPGridTile hoveredTile  = null;
    private WyrActor hoveredActor = null;
    private WyrActor.Unit activeUnit = null;

    private final Array<WyrActor.Bullet> bulletsOnStage = new Array<>();
    private final Array<WyrActor.Prop> propsOnStage     = new Array<>();
    private final Array<WyrActor.Unit> unifiedTurnOrder = new Array<>();

    private final Array<WyrWinCondition> winCons = new Array<>();

    public WyRegister() {}

    public void advanceTurn() {
        currentTurnNumber++;
        for(WyrActor.Unit unit : unifiedTurnOrder) {
            unit.resetForNextTurn();
        }

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
    public void registerProp(WyrActor.Prop prop) {
//        if(!this.propsOnStage.contains(prop, true)) propsOnStage.add(prop);
    }
    public void delistProp(WyrActor.Prop prop) {

    }

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

//    public void clearActiveUnit() { activeUnit = null; }
//    public void setActiveUnit(WyrActor.Unit unit) { activeUnit = unit; }
    public void addWinCon(WyrWinCondition condition) { winCons.add(condition); }
    public void revealWinCon(WyrFrame.Campaign.FlagID flagID) {
        for(WyrWinCondition w : winCons) {
            if(w.getAssociatedFlag() == flagID) {
                w.reveal();
                handlers.hud().updateWinCon();
                return;
            }
        }
    }
    public WyrActor getActorByName(String name) {
        return null;
    }
    public Array<WyrActor.Unit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public int turnCount() { return currentTurnNumber; }
    public int tickCount() { return handlers.priority().unitsHoldingPriority().get(0).getModifiedStatValue(SPEED); }
    public Array<WyrWinCondition> revealedVictoryConditions() {
        final Array<WyrWinCondition> rV = new Array<>();
        for(WyrWinCondition c : winCons) {
            if(c.isRevealed()) {
                rV.add(c);
            }
        }
        return rV;
    }
    public boolean terminalFailureConditionMet() {
        return false;
    }
    public boolean terminalVictoryConditionMet() {
        return false;
    }
    public int currentTurnNumber() { return currentTurnNumber; }
    public boolean hasFog() { return fogOfWar; }
    public boolean inIronMode() { return ironModeBTW; }
    public boolean inCombat() {
//        Gdx.app.log("register", "uto size: " + unifiedTurnOrder.size);
        for(WyrActor.Unit unit : unifiedTurnOrder) {
            if(unit.getTeamAlignment() == TeamAlignment.ENEMY || unit.getTeamAlignment() == TeamAlignment.STRANGER) {
                return true;
            }
        }
        return false;
    }
    public WyrActor.Unit avatarUnit() {
        for(WyrActor.Unit u : unifiedTurnOrder) {
            if(u.getCharacterID() == WyrFrame.Character.Name.Leif) return u;
        }
        return unifiedTurnOrder.get(0);
    }
}
