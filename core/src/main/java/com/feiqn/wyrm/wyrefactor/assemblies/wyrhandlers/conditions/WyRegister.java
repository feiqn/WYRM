package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.TeamAlignment;

import java.util.Comparator;
import java.util.Objects;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPathfinder.teamsAreAllied;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MoveControlMode.FREE_MOVE;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.MoveControlMode.TURN_BASED;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.handlers;

public class WyRegister {

    private boolean fogOfWar = false;
    private boolean ironModeBTW = false;

    private int currentTurnNumber = 0;

    private WyrTile hoveredTile  = null;
    private WyrActor hoveredActor = null;
    private WyrActor selectedActor = null;

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
        handlers.invalidateAll();
        handlers.priority().parsePriority();
    }

    public void addFog()   { fogOfWar = true;  }
    public void clearFog() { fogOfWar = false; }

    private void addToTurnOrder(WyrActor.Unit unit) {
        if(!unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.add(unit);
            sortTurnOrder();
            if(handlers.input().getMovementControlMode() == FREE_MOVE && !teamsAreAllied(TeamAlignment.PLAYER, unit.getTeamAlignment())) handlers.input().setMoveControl(TURN_BASED);
        }
        handlers.invalidateAll();
    }
    public void removeFromTurnOrder(WyrActor.Unit unit) {
        if(unifiedTurnOrder.contains(unit, true)) {
            unifiedTurnOrder.removeValue(unit,true);
            if(handlers.input().getMovementControlMode() == TURN_BASED && !inCombat()) handlers.input().setFreeMove();
            sortTurnOrder();
        }
        handlers.invalidateAll();
        handlers.hud().updateTurnOrder();
    }

    public void declareUnit(WyrActor.Unit unit) {
        addToTurnOrder(unit);
        handlers.hud().updateTurnOrder();
    }

    private void sortTurnOrder() {
        // I'm not even gonna lie to you.
        // I used a lame language model for this part.

        unifiedTurnOrder.sort(new Comparator<WyrActor.Unit>() { // new What, now?
            @Override
            public int compare(WyrActor.Unit a, WyrActor.Unit b) {
                // 1) Speed, descending
                int speedDiff = b.stats().getNetValue(SPEED) - a.stats().getNetValue(SPEED);
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
    }

//    public void clearActiveUnit() { activeUnit = null; }
//    public void setActiveUnit(WyrActor.Unit unit) { activeUnit = unit; }

    public void setHoveredTile(@Null WyrTile tile) {
        if(tile == null) return;
        if(tile == hoveredTile) return;
        hoveredTile = tile;
        handlers.hud().setTileContext(tile);
    }

    public void addWinCon(WyrWinCondition condition) { winCons.add(condition); }
    public void revealWinCon(WyrFrame.Campaign.FlagID flagID) {
        for(WyrWinCondition w : winCons) {
            if(Objects.equals(w.getAssociatedFlag(), flagID.toString())) {
                w.reveal();
                handlers.hud().updateWinCon();
                return;
            }
        }
    }
    public @Null Actor getActorByName(String name) {
        for(Actor actor : handlers.screen().getGameStage().getActors()) {
            if(actor.getName().equalsIgnoreCase(name)) return actor;
        }
        return null;
    }
    public @Null WyrActor getWyrActorFromMap(String name) {

        for(WyrTile tile : handlers.map().getAllTiles()) {
            for(WyrActor actor : tile.getActorsOnGround()) {
                if(actor.getName().equalsIgnoreCase(name)) return actor;
            }
        }

//        for(WyrActor.Unit unit : unifiedTurnOrder) {
//            if(Objects.equals(unit.getCharacterID().toString().toLowerCase(), name.toLowerCase())) return unit;
//            if(unit.getName().equalsIgnoreCase(name)) return unit;
//        }
//
//        for(WyrActor.Prop prop : propsOnStage) {
//            if(Objects.equals(prop.getPropType().toString().toLowerCase(), name.toLowerCase())) return prop;
//            if(prop.getName().equalsIgnoreCase(name)) return prop;
//        }

        return null;
    }
    public Array<WyrActor.Unit> unifiedTurnOrder() { return unifiedTurnOrder; }
    public int turnCount() { return currentTurnNumber; }
    public int tickCount() { return handlers.priority().unitsHoldingPriority().get(0).stats().getNetValue(SPEED); }
    public Array<WyrWinCondition> revealedVictoryConditions() {
        final Array<WyrWinCondition> rV = new Array<>();
        for(WyrWinCondition c : winCons) {
            if(c.isRevealed()) {
                rV.add(c);
            }
        }
        return rV;
    }
    public int currentTurnNumber() { return currentTurnNumber; }
    public boolean terminalFailureConditionMet() {
        return false;
    }
    public boolean terminalVictoryConditionMet() {
        return false;
    }
    public boolean characterIsInPlay(Name charID) {
        return getWyrActorFromMap(charID.toString()) != null;
    }
    public boolean hasFog() { return fogOfWar; }
    public boolean inIronMode() { return ironModeBTW; }
    public boolean inCombat() {
        for(WyrActor.Unit unit : unifiedTurnOrder) {
            if(unit.getTeamAlignment() == TeamAlignment.ENEMY || unit.getTeamAlignment() == TeamAlignment.STRANGER) {
                handlers.input().setMoveControl(TURN_BASED);
                return true;
            }
        }
        handlers.input().setMoveControl(FREE_MOVE);
        return false;
    }
    public WyrActor.Unit avatarUnit() {
        for(WyrActor.Unit u : unifiedTurnOrder) {
            if(u.getCharacterID() == Name.Leif) return u;
        }
        return unifiedTurnOrder.get(0);
    }
    public WyrTile getHoveredTile() { return hoveredTile; }
}
