package com.feiqn.wyrm.logic.handlers.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.VictoryCondition;
import com.feiqn.wyrm.models.battleconditionsdata.victoryconditions.prefabvictcons.RoutVictCon;

public class  BattleConditionsHandler {

    private final WYRMGame game;

    private boolean fogOfWar,
                    terminalVictConMet;

    private int currentTurn;

    private final Array<VictoryCondition> victoryConditions;
//    private Array<FailureCondition> failureConditions;

    public BattleConditionsHandler(WYRMGame game) {
        this.game = game;
        fogOfWar = false;
        terminalVictConMet = false;
        currentTurn = 0;

        victoryConditions = new Array<>();
//        failureConditions = new Array<>();

//        victoryConditions.add(new RoutVictCon(game));
//        failureConditions.add(FailureConditionType.ROUTED);

    }

    public Array<VictoryCondition> getVictoryConditions() {
        return victoryConditions;
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

    public void satisfyVictCon(int index) {
        victoryConditions.get(index).satisfy();
        game.activeBattleScreen.victConUI.get(index).clear();
        Gdx.app.log("conditions", "cleared");
    }

    public int victConIndexOf(VictoryCondition victCon) {
        if(victoryConditions.contains(victCon, true)) {
            return victoryConditions.indexOf(victCon, true);
        }
        else return 58008;
    }

}
