package com.feiqn.wyrm.logic.handlers.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.MapScreen;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class BattleConditionsHandler {
    // Handled by BattleScreen
    // Tracks environmental and logistical parameters for the active instance of a battle screen

    private final WYRMGame game;

    private boolean fogOfWar,
                    terminalVictConMet;

    private int currentTurn;

    private Phase currentPhase;

    private HashMap<Integer, Array<Unit>> turnOrderPriority;

    private final Array<VictoryCondition> victoryConditions;
//    private Array<FailureCondition> failureConditions;

    private Array<Unit> battleRoster;

    public BattleConditionsHandler(WYRMGame game) {
        this.game = game;
        fogOfWar = false;
        terminalVictConMet = false;
        currentTurn = 0;
        currentPhase = Phase.PLAYER_PHASE;
        turnOrderPriority = new HashMap<Integer, Array<Unit>>();
        battleRoster = new Array<>();
        for(int i = 1; i <= 30; i++) {
            turnOrderPriority.put(i, new Array<>());
        }

        victoryConditions = new Array<>();
//        failureConditions = new Array<>();

//        victoryConditions.add(new RoutVictCon(game));
//        failureConditions.add(FailureConditionType.ROUTED);

    }

    public void addVictoryCondition(VictoryCondition victCon) {
        victoryConditions.add(victCon);
    }

//    public void addFailureCondition(FailureCondition failCon) {}

    public void clearTurnOrder() {
        battleRoster = new Array<>();
        calculateTurnOrder();
    }

    public void addToTurnOrder(Unit unit) {
        if(!battleRoster.contains(unit, true)) {
            battleRoster.add(unit);
            calculateTurnOrder();
        }
    }

    private void calculateTurnOrder() {
        for(Array<Unit> a : turnOrderPriority.values()) {
            a = new Array<>();
        }
        for(Unit u : battleRoster) {
            turnOrderPriority.get(u.baseSimpleSpeed()).add(u);
        }

        /* ROTATION LOGIC:
         * ---------------
         * speed values will be: base(0..10) + class(0..10) + equipment(0..10)
         * each turn consists of 30 ticks
         * a unit's combined speed value equals the turn tick they can act on
         * units with same speed may move within battle tick in whatever order they want,
         * with default priority to player -> ally -> enemy -> other
         * a speed of zero will have their turn skipped
         */

    }

    public void passPhase() {
        // By default, turn order is as follows:

        // PLAYER -> ENEMY -> ALLY -> OTHER -> PLAYER

        // Calling this function will pass the turn as normal,
        // or you can manually assign turns (I.E., to give the
        // enemy more chances to move on a certain mission, etc.)
        // via passPhaseToTeam(), which this function simply wraps
        // for convenience.
        game.activeGridScreen.activeUnit = null;

        switch (game.activeGridScreen.conditionsHandler.currentPhase()) {
            case PLAYER_PHASE:
                passPhaseToTeam(TeamAlignment.ENEMY);
                break;
            case ENEMY_PHASE:
                if(game.activeGridScreen.teamHandler.allyTeamUsed) {
                    passPhaseToTeam(TeamAlignment.ALLY);
                } else if(game.activeGridScreen.teamHandler.otherTeamUsed) {
                    passPhaseToTeam(TeamAlignment.OTHER);
                } else {
                    passPhaseToTeam(TeamAlignment.PLAYER);
                }
                break;
            case ALLY_PHASE:
                if(game.activeGridScreen.teamHandler.otherTeamUsed) {
                    passPhaseToTeam(TeamAlignment.OTHER);
                } else {
                    passPhaseToTeam(TeamAlignment.PLAYER);
                }
                break;
            case OTHER_PHASE:
                passPhaseToTeam(TeamAlignment.PLAYER);
                break;
        }
    }

    private void passPhaseToTeam(@NotNull TeamAlignment team) {
        game.activeGridScreen.teamHandler.resetTeams();
        switch (team) {
            case PLAYER:
                if(game.activeGridScreen.conditionsHandler.victoryConditionsAreSatisfied() && game.activeGridScreen.conditionsHandler.turnCount() != 0) {
                    Gdx.app.log("conditions", "You win!");
                    game.activeGridScreen.stageClear();

                    // TODO: do i need to unload this old screen somehow?

                    // The following is debug code that will only run if
                    // child classes are not implemented properly.
                    MapScreen screen = new MapScreen(game);
                    game.activeScreen = screen;
                    game.activeGridScreen = null;
                    game.setScreen(screen);
                    // --END--
                } else {
                    Gdx.app.log("phase: ", "Player Phase");
                    nextTurn();
                    currentPhase = Phase.PLAYER_PHASE;
                }
                break;
            case ALLY:
                Gdx.app.log("phase: ", "Ally Phase");
                currentPhase = Phase.ALLY_PHASE;
                break;
            case ENEMY:
                Gdx.app.log("phase: ", "Enemy Phase");
                currentPhase = Phase.ENEMY_PHASE;
                break;
            case OTHER:
                Gdx.app.log("phase: ", "Other Phase");
                currentPhase = Phase.OTHER_PHASE;
                break;
        }
    }

    public void nextTurn() {
        // Turn count goes up on each Player Phase rotation.
        currentTurn++;
        Gdx.app.log("conditions", "Turn advanced to: " + currentTurn);
    }

    public void satisfyVictCon(int index) {
        victoryConditions.get(index).satisfy();
        game.activeGridScreen.hud().victConUI.get(index).clear();
        Gdx.app.log("conditions", "cleared");
    }

    // --GETTERS--
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
    public Phase currentPhase() { return currentPhase; }
    public HashMap<Integer, Array<Unit>> getTurnOrder() {
        return turnOrderPriority;
    }
}
