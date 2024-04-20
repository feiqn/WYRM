package com.feiqn.wyrm.logic.handlers.ai.actions;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;

public class AIAction {

    private final WYRMGame game;

    protected ActionType actionType;

    protected Vector2 coordinate;

    protected int decisionWeight;

    protected Unit subjectUnit, // Subject is the actor of the action
                   objectUnit;  // Object is the one being acted upon

    private boolean coordinateInitialized,
                    subjectInitialized,
                    objectInitialized;


    public AIAction(WYRMGame game, ActionType type) {
        this.game = game;
        this.actionType = type;

        decisionWeight = 50;

        coordinateInitialized = false;
        subjectInitialized    = false;
        objectInitialized     = false;

    }

    public void weigh() {
        decisionWeight = 50;
        switch(actionType) {
            case ATTACK_ACTION:
                if(subjectInitialized && objectInitialized) {
                    if(subjectUnit.getAttackSpeed() > objectUnit.getAttackSpeed()) {
                        incrementWeight();
                    } else {
                        decrementWeight();
                    }
                    if(subjectUnit.getAttackPower() > objectUnit.getAttackPower()) {
                        incrementWeight();
                    } else {
                        decrementWeight();
                    }
                    if(subjectUnit.getDefensePower() > objectUnit.getDefensePower()) {
                        incrementWeight();
                    } else {
                        decrementWeight();
                    }
                    if(subjectUnit.getBaseMaxHP() < objectUnit.getBaseMaxHP() * 1.5f) {
                        decrementWeight();
                    }
                } else {
                    decisionWeight = 0;
                    break;
                }
            case MOVE_ACTION:
            case PASS_ACTION:
                decisionWeight = 100;
                for(Unit unit : game.activeBattleScreen.currentTeam()) {
                    if(unit.canMove()) {
                        decisionWeight = 0;
                    }
                }
            default:
                break;
        }
    }

    protected void incrementWeight() {
        decisionWeight += 5;
    }

    protected void decrementWeight() {
        decisionWeight -= 5;
    }

    public void setSubjectUnit(Unit unit) {
        subjectUnit = unit;
        subjectInitialized = true;
    }

    public void setObjectUnit(Unit unit) {
        objectUnit = unit;
        objectInitialized = true;
    }

    public void setCoordinate(Vector2 xy) {
        coordinate = xy;
        coordinateInitialized = true;
    }

    public void setCoordinate(int x, int y) {
        coordinate.x = x;
        coordinate.y = y;
        coordinateInitialized = true;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getDecisionWeight() {
        return decisionWeight;
    }

    public Vector2 getCoordinate() {
        if(coordinateInitialized) {
            return coordinate;
        } else {
            return new Vector2();
        }
    }

    public Unit getObjectUnit() {
        if(objectInitialized) {
            return objectUnit;
        } else {
            return new Unit(game);
        }
    }

    public Unit getSubjectUnit() {
        if(subjectInitialized) {
            return subjectUnit;
        } else {
            return new Unit(game);
        }
    }
}
