package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.FailureConditionType;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.RoutVictCon;

public class BattleConditionsHandler {

    private final WYRMGame game;

    private boolean fogOfWar,
                    terminalVictConMet;

    private int currentTurn;

    private Array<VictoryCondition> victoryConditions;
//    private Array<FailureCondition> failureConditions;

    public BattleConditionsHandler(WYRMGame game) {
        this.game = game;
        fogOfWar = false;
        terminalVictConMet = false;
        currentTurn = 0;

        victoryConditions = new Array<>();
//        failureConditions = new Array<>();

        victoryConditions.add(new RoutVictCon(game));
//        failureConditions.add(FailureConditionType.ROUTED);


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

//        if(allConsSatisfied) {
//            Gdx.app.log("conshand", "returning: All cons met! True!");
//            return true;
//        } else if(terminalVictConMet) {
//            Gdx.app.log("conshand", "returning: Terminal con met! True!");
//            return true;
//        } else {
//            Gdx.app.log("conshand", "returning: false");
//            return false;
//        }
    }

    public boolean failureConditionsAreSatisfied() {
        return false;
    }

    public void addVictoryCondition(VictoryCondition victCon) {
        victoryConditions.add(victCon);
    }

//    public void addFailureCondition(FailureCondition failCon) {}

    public int turnCount() { return currentTurn; }

    public void nextTurn() {
        // Turn count goes up on each Player Phase rotation.
        currentTurn++;
        Gdx.app.log("conditions", "Turn advanced to: " + currentTurn);
    }

}
