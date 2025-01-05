package com.feiqn.wyrm.logic.handlers.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.MapScreen;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.phasedata.Phase;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import org.jetbrains.annotations.NotNull;

public class BattleConditionsHandler {
    // Handled by BattleScreen
    // Tracks environmental and logistical parameters for the active instance of a battle screen

    private final WYRMGame game;

    private boolean fogOfWar,
                    terminalVictConMet;

    private int currentTurn;

    private Phase currentPhase;

    private final Array<VictoryCondition> victoryConditions;
//    private Array<FailureCondition> failureConditions;

    public BattleConditionsHandler(WYRMGame game) {
        this.game = game;
        fogOfWar = false;
        terminalVictConMet = false;
        currentTurn = 0;
        currentPhase = Phase.PLAYER_PHASE;

        victoryConditions = new Array<>();
//        failureConditions = new Array<>();

//        victoryConditions.add(new RoutVictCon(game));
//        failureConditions.add(FailureConditionType.ROUTED);

    }

    public void addVictoryCondition(VictoryCondition victCon) {
        victoryConditions.add(victCon);
    }

//    public void addFailureCondition(FailureCondition failCon) {}

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
        game.activeGridScreen.victConUI.get(index).clear();
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
}
