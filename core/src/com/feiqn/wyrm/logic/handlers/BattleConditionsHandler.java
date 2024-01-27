package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.FailureCondition;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryCondition;

public class BattleConditionsHandler {

    private final WYRMGame game;

    private boolean fogOfWar;

    private int victConsForWin;

    private int totalVictoryConditions,
                totalFailureConditions,
                turnGoal,
                currentTurn;

    private Array<VictoryCondition> victoryConditions;
    private Array<FailureCondition> failureConditions;

    public BattleConditionsHandler(WYRMGame game) {
        this.game = game;
        fogOfWar = false;
        turnGoal = 10;
        currentTurn = 1;

        victoryConditions = new Array<>();
        failureConditions = new Array<>();

        victoryConditions.add(VictoryCondition.ROUT);
        failureConditions.add(FailureCondition.ROUTED);

        totalVictoryConditions = victoryConditions.size;
        totalFailureConditions = failureConditions.size;

        victConsForWin = totalVictoryConditions;

    }

    public boolean victoryConditionsSatisfied() {

        int consMet = 0;

        for(VictoryCondition victCon : victoryConditions) {
            switch(victCon) {
                case ROUT:
                    if(game.activeBattleScreen.enemyTeam.size == 0) {
                        consMet++;
                    }
                    break;
                case SURVIVE:
                    if(currentTurn >= turnGoal) {
                        consMet++;
                    }
                    break;
                case ESCAPE_ALL:
                case ESCAPE_ONE:
                case DEFEND_TILE:
                case DEFEND_UNIT:
                case ESCAPE_MULTIPLE:
                default:
                    break;
            }
        }

        return consMet >= victConsForWin;
    }

    public boolean failureConditionsSatisfied() {
        return false;
    }

    public void addVictoryCondition(VictoryCondition victCon, int turns) {
        addVictoryCondition(victCon);
        setTurnGoal(turns);
    }

    public void addVictoryCondition(VictoryCondition victCon) {
        victoryConditions.add(victCon);
        totalVictoryConditions = victoryConditions.size;

//        switch(victCon) {
//            case ROUT:
//            case SURVIVE:
//            case ESCAPE_ALL:
//            case ESCAPE_ONE:
//            case DEFEND_TILE:
//            case DEFEND_UNIT:
//            case ESCAPE_MULTIPLE:
//                break;
//        }
    }

    public void addFailureCondition(FailureCondition failCon) {
        // TODO: same as victCon

        failureConditions.add(failCon);
        totalFailureConditions = failureConditions.size;
    }

    public void setTurnGoal(int goal) {
        this.turnGoal = goal;
    }

    public void nextTurn() {
        currentTurn++;
    }

}
