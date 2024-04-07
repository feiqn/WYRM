package com.feiqn.wyrm.logic.handlers.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.HashMap;

public class AIHandler {

    private Boolean thinking,
                    waiting;

    private BattleScreen abs;

    private final WYRMGame game;

    public AIHandler(WYRMGame game) {
        this.game = game;
        abs = game.activeBattleScreen;
        startWaiting();
        stopThinking();
    }

    public void run() {
        if(!abs.isBusy()) {
            thinking = true;
            waiting = false;

            for(Unit unit : abs.currentTeam()) {
                if(unit.canMove()) {
                    evaluateUnit(unit);
                }
            }
        }
    }

    private void evaluateUnit(Unit unit) {
        Array<Unit> enemiesInRange = new Array<>();
        Array<Unit> alliesInRange = new Array<>();

        switch(unit.getAiType()) {
            case AGGRESIVE:

            default:
                break;
        }

    }

//    private Array evaluateOptions(Unit unit) {
//        abs.reachableTiles = new Array<>();
//        abs.attackableUnits = new Array<>();
//        abs.tileCheckedAtSpeed = new HashMap<>();
//
//        abs.recursivelySelectReachableTiles(unit);
//
//
//    }

    private void endTurn() {
        stopThinking();
        startWaiting();
        sendPassAction();
    }

    private void sendMoveAction(Unit subject, Vector2 destination) {

    }

    private void sendAttackAction() {}

    private void sendPassAction() {
        abs.executeAction(ActionType.PASS_ACTION, null, null, null);
    }

    // --SETTERS--

    private void startThinking() { thinking = true;}
    public void stopThinking() { thinking = false; }
    public void stopWaiting() { waiting = false; }
    public void startWaiting() { waiting = true; }
}
