package com.feiqn.wyrm.logic.handlers.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.HashMap;

public class BattleConditionsHandler {
    // Handled by BattleScreen
    // Tracks environmental and logistical parameters for the active instance of a grid screen

    private final WYRMGame game;

    private boolean fogOfWar,
                    terminalVictConMet;

    private int currentTurn;
    private int currentTick;

    private final HashMap<Integer, Array<Unit>> turnOrderPriority;

    private final Array<VictoryCondition> victoryConditions;
//    private Array<FailureCondition> failureConditions;

    private final Array<Unit> battleRoster;

    private Phase currentPhase;

    public BattleConditionsHandler(WYRMGame game) {
        this.game = game;
        fogOfWar = false;
        terminalVictConMet = false;
        currentTurn = 1;
        currentTick = 1;
        turnOrderPriority = new HashMap<Integer, Array<Unit>>();
        battleRoster = new Array<>();
        for(int i = 1; i <= 40; i++) {
            turnOrderPriority.put(i, new Array<>());
        }

        currentPhase = Phase.PLAYER_PHASE;

        victoryConditions = new Array<>();
//        failureConditions = new Array<>();

//        victoryConditions.add(new RoutVictCon(game));
//        failureConditions.add(FailureConditionType.ROUTED);

    }

    public void addVictoryCondition(VictoryCondition victCon) {
        victoryConditions.add(victCon);
        game.activeGridScreen.hud().reset();
    }

//    public void addFailureCondition(FailureCondition failCon) {}

    public void clearTurnOrder() {
        battleRoster.clear();
        calculateTurnOrder();
    }

    public void addToTurnOrder(Unit unit) {
        if(!battleRoster.contains(unit, true)) {
            Gdx.app.log("added to turn order:", unit.name);
            battleRoster.add(unit);
            calculateTurnOrder();
        }
    }

    public Array<Unit> unitsThisTick() {
        for(Unit unit : turnOrderPriority.get(currentTick)) {
            if(unit.canMove()) {
                return turnOrderPriority.get(currentTick);
            }
        }
        if(currentTick < 40) {
            currentTick++;
        } else {
            advanceTurn();
        }
        return unitsThisTick();
    }

    public Array<Unit> unitsThisPhaseThisTick() {
        updatePhase();
        final Array<Unit> a = new Array<>();
        for(Unit unit : unitsThisTick()) {
            if(phaseFromAlignment(unit.getTeamAlignment()) == currentPhase) {
                a.add(unit);
            }
        }
        for(int i = 0; i < a.size; i++) {
            Gdx.app.log("UTPTT", a.get(i).name);
        }
        return a;
    }

    private void advanceTurn() {
        currentTurn++;
        currentTick = 1;

        for(Array<Unit> a : turnOrderPriority.values()) {
            for(Unit u : a) {
                u.setCanMove();
            }
        }
        game.activeGridScreen.hud().updateTurnOrderPanel(); // maybe unneeded, didn't test
    }

    private void calculateTurnOrder() {
//        turnOrderPriority = new HashMap<>();
//        for(int i = 1; i <= 40; i++) {
//            turnOrderPriority.put(i, new Array<>());
//        }
        for(Array<Unit> a : turnOrderPriority.values()) {
            a.clear();
        }
        for(Unit u : battleRoster) {
            turnOrderPriority.get(u.modifiedSimpleSpeed()).add(u);
            Gdx.app.log("calculate", "added: " + u.name + " to tick: " + u.modifiedSimpleSpeed());
        }

        /* ROTATION LOGIC:
         * ---------------
         * speed values will be: base(0..10) + class(0..10) + weapon(0..10) + armor(0..10)
         * each turn consists of 40 ticks
         * a unit's combined speed value equals the turn tick they can act on
         * units with same speed may move within battle tick in whatever order they want,
         * with default priority to player -> ally -> enemy -> other
         * a speed of zero will have their turn skipped
         */

    }

//    public void passPhase() {
//        // By default, turn order is as follows:
//
//        // PLAYER -> ENEMY -> ALLY -> OTHER -> PLAYER
//
//        // Calling this function will pass the turn as normal,
//        // or you can manually assign turns (I.E., to give the
//        // enemy more chances to move on a certain mission, etc.)
//        // via passPhaseToTeam(), which this function simply wraps
//        // for convenience.
//        game.activeGridScreen.activeUnit = null;
//
//        switch (game.activeGridScreen.conditionsHandler.currentPhase()) {
//            case PLAYER_PHASE:
//                passPhaseToTeam(TeamAlignment.ENEMY);
//                break;
//            case ENEMY_PHASE:
//                if(game.activeGridScreen.teamHandler.allyTeamUsed) {
//                    passPhaseToTeam(TeamAlignment.ALLY);
//                } else if(game.activeGridScreen.teamHandler.otherTeamUsed) {
//                    passPhaseToTeam(TeamAlignment.OTHER);
//                } else {
//                    passPhaseToTeam(TeamAlignment.PLAYER);
//                }
//                break;
//            case ALLY_PHASE:
//                if(game.activeGridScreen.teamHandler.otherTeamUsed) {
//                    passPhaseToTeam(TeamAlignment.OTHER);
//                } else {
//                    passPhaseToTeam(TeamAlignment.PLAYER);
//                }
//                break;
//            case OTHER_PHASE:
//                passPhaseToTeam(TeamAlignment.PLAYER);
//                break;
//        }
//    }

//    private void passPhaseToTeam(@NotNull TeamAlignment team) {
//        game.activeGridScreen.teamHandler.resetTeams();
//        switch (team) {
//            case PLAYER:
//                if(game.activeGridScreen.conditionsHandler.victoryConditionsAreSatisfied() && game.activeGridScreen.conditionsHandler.turnCount() != 0) {
//                    Gdx.app.log("conditions", "You win!");
//                    game.activeGridScreen.stageClear();
//
//                    // TODO: do i need to unload this old screen somehow?
//
//                    // The following is debug code that will only run if
//                    // child classes are not implemented properly.
//                    MapScreen screen = new MapScreen(game);
//                    game.activeScreen = screen;
//                    game.activeGridScreen = null;
//                    game.setScreen(screen);
//                    // --END--
//                } else {
//                    Gdx.app.log("phase: ", "Player Phase");
//                    nextTurn();
//                    currentPhase = Phase.PLAYER_PHASE;
//                }
//                break;
//            case ALLY:
//                Gdx.app.log("phase: ", "Ally Phase");
//                currentPhase = Phase.ALLY_PHASE;
//                break;
//            case ENEMY:
//                Gdx.app.log("phase: ", "Enemy Phase");
//                currentPhase = Phase.ENEMY_PHASE;
//                break;
//            case OTHER:
//                Gdx.app.log("phase: ", "Other Phase");
//                currentPhase = Phase.OTHER_PHASE;
//                break;
//        }
//    }

    public void satisfyVictCon(int index) {
        victoryConditions.get(index).satisfy();
//        game.activeGridScreen.hud().victConUI.get(index).clear();
        Gdx.app.log("conditions", "cleared");
    }

    private Phase phaseFromAlignment(TeamAlignment team) {
        switch(team) {
            case PLAYER:
                return Phase.PLAYER_PHASE;
            case ENEMY:
                return Phase.ENEMY_PHASE;
            case ALLY:
                return Phase.ALLY_PHASE;
            case OTHER:
                return Phase.OTHER_PHASE;
        }
        return Phase.PLAYER_PHASE;
    }

    public void updatePhase() {
        currentPhase = getUpdatedPhase();
        Gdx.app.log("updatePhase", "to " + currentPhase);
        if(game.activeGridScreen.hud() != null)
            game.activeGridScreen.hud().updateTurnOrderPanel();
    }

    // --GETTERS--
    public HashMap<TeamAlignment, Array<Unit>> segregatedTickOrder() {
        return segregatedTickOrder(currentTick);
    }
    public HashMap<TeamAlignment, Array<Unit>> segregatedTickOrder(int tick) {
        final HashMap<TeamAlignment, Array<Unit>> h = new HashMap<>();
        h.put(TeamAlignment.PLAYER, new Array<>());
        h.put(TeamAlignment.ENEMY, new Array<>());
        h.put(TeamAlignment.ALLY, new Array<>());
        h.put(TeamAlignment.OTHER, new Array<>());

        for(Unit u : turnOrderPriority.get(tick)) {
            switch(u.getTeamAlignment()) {
                case PLAYER:
                    h.get(TeamAlignment.PLAYER).add(u);
                    break;
                case ENEMY:
                    h.get(TeamAlignment.ENEMY).add(u);
                    break;
                case ALLY:
                    h.get(TeamAlignment.ALLY).add(u);
                    break;
                case OTHER:
                    h.get(TeamAlignment.OTHER).add(u);
                    break;
            }
        }
        return h;
    }
    public int victConIndexOf(VictoryCondition victCon) {
        if(victoryConditions.contains(victCon, true)) {
            return victoryConditions.indexOf(victCon, true);
        }
        else return 42069;
    }
    public int turnCount() { return currentTurn; }
    public Array<VictoryCondition> getVictoryConditions() {
        return victoryConditions;
    }
    public boolean victoryConditionIsSatisfied(int index) {
        return victoryConditions.get(index).conditionIsSatisfied();
    }
    public boolean victoryConditionsAreSatisfied() {
        boolean allConsSatisfied = true;
        terminalVictConMet = false;

        for(VictoryCondition victcon : victoryConditions) {
            if(!victcon.conditionIsSatisfied()) {
                allConsSatisfied = false;
            } else if(victcon.conditionIsSatisfied() && victcon.isTerminal()) {
                terminalVictConMet = true;
            }
        }

        return allConsSatisfied || terminalVictConMet;
    }
    public boolean failureConditionsAreSatisfied() {
        return false;
    }
    public HashMap<Integer, Array<Unit>> getTurnOrder() {
        return turnOrderPriority;
    }
    public Phase getCurrentPhase() {
        return currentPhase;
    }

    private Phase getUpdatedPhase() {
        if(turnOrderPriority.get(currentTick) != null) {
            for(Unit unit : turnOrderPriority.get(currentTick)) {
                if(unit.canMove()) {
                    switch(unit.getTeamAlignment()) {
                        case PLAYER:
                            return Phase.PLAYER_PHASE;
                        case ENEMY:
                            return Phase.ENEMY_PHASE;
                        case ALLY:
                            return Phase.ALLY_PHASE;
                        case OTHER:
                            return Phase.OTHER_PHASE;
                    }
                }
            }
            if(currentTick < 40) {
                currentTick++;
            } else {
                advanceTurn();
            }
            return getUpdatedPhase();
        }
        Gdx.app.log("FAILSAFE", "Oh no");
        return Phase.PLAYER_PHASE;
    }
}
