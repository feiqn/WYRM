package com.feiqn.wyrm.logic.handlers.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ai.actions.AIAction;
import com.feiqn.wyrm.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.logic.screens.BattleScreen;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.HashMap;

public class AIHandler {

    protected Boolean thinking,
                    waiting;

    protected BattleScreen abs;

    protected final WYRMGame game;

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
                    evaluateOptions(unit);
                }
            }
        } else {
            thinking = false;
            waiting = true;
        }
    }

//    private void evaluateUnit(Unit unit) {
//        Array<Unit> enemiesInRange = new Array<>();
//        Array<Unit> alliesInRange = new Array<>();
//
//        switch(unit.getAiType()) {
//            case AGGRESSIVE:
//            case STILL:
//            case RECKLESS:
//            default:
//                break;
//        }
//
//    }

    private Array<AIAction> evaluateOptions(Unit unit) {
        final Array<AIAction> options = new Array<>();

        abs.reachableTiles = new Array<>();
        abs.attackableUnits = new Array<>();
        
        abs.tileCheckedAtSpeed = new HashMap<>();
        abs.recursivelySelectReachableTiles(unit);

        switch(unit.getAiType()) {
            case AGGRESSIVE:
                // Look for good fights, and advance the enemy.
            case RECKLESS:
                // Run towards the enemy and attack anything in sight. Fodder.
            case STILL:
                // Stand still and attack anything in range.
            case LOS_AGGRO:
                // Stand still but chase anything in range.
            case LOS_FLEE:
                // Stand still but run away from anything in range.
            case DEFENSIVE:
                // Huddle together with other units, ideally around choke points.
            case FLANKING:
                // Surround the enemy.
            case PLAYER:
                // Make mistakes.
            default:
                break;
        }

        return options;
    }

    private void endTurn() {
        stopThinking();
        startWaiting();
        sendPassAction();
    }

    private void sendMoveAction(Unit subject, Vector2 destination) {

    }

    private void sendAttackAction() {}

    private void sendPassAction() {
        abs.executeAction(new AIAction(game, ActionType.PASS_ACTION));
    }

    // --SETTERS--

    private void startThinking() { thinking = true;}
    public void stopThinking() { thinking = false; }
    public void stopWaiting() { waiting = false; }
    public void startWaiting() { waiting = true; }
}
