package com.feiqn.wyrm.logic.handlers.ai.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.Path;
import com.feiqn.wyrm.models.unitdata.Unit;

public class AIAction {

    private final WYRMGame game;

    protected ActionType actionType;

    protected Vector2 coordinate;

    protected int decisionWeight;

    protected Path associatedPath;

    protected Unit subjectUnit, // Subject is the actor of the action
                   objectUnit;  // Object is the one being acted upon

    private boolean coordinateInitialized,
                    subjectInitialized,
                    objectInitialized,
                    pathInitialized;


    public AIAction(WYRMGame game, ActionType type) {
        this.game = game;
        this.actionType = type;

        decisionWeight = 0;
        coordinate = new Vector2();

        coordinateInitialized = false;
        subjectInitialized    = false;
        objectInitialized     = false;
        pathInitialized       = false;

    }

    private void weigh() {
        switch(actionType) {
            case ATTACK_ACTION:
                decisionWeight = 50;
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
                    Gdx.app.log("Weighing: ", "ERROR, Bad Attack Action");
                    decisionWeight = 0;
                }
                break;
            case MOVE_ACTION:
                // TODO: IDK SOMETHING OTHER THAN MAGIC NUMBERS HERE
                decisionWeight = 45;
                break;
            case WAIT_ACTION:
                //
            case USE_ITEM_ACTION:
            case WORLD_INTERACT_ACTION:
                decisionWeight = 0;
                break;
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

    // --SETTERS--

    public void incrementWeight() {
        decisionWeight += 5;
    }

    public void decrementWeight() {
        decisionWeight -= 5;
    }

    public void setPath(Path path) {
        associatedPath = path;
        pathInitialized = true;
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

    // --GETTERS--

    public ActionType getActionType() {
        return actionType;
    }

    public int getDecisionWeight() {
        weigh();
        Gdx.app.log("DECISION WEIGHT:", actionType + " " + decisionWeight);
        return decisionWeight;
    }

    public Path getAssociatedPath() {
        if(pathInitialized) {
            return associatedPath;
        } else {
            Gdx.app.log("ERROR", "path not initialized");
            return new Path(game);
        }
    }

    public Vector2 getCoordinate() {
        if(coordinateInitialized) {
            return coordinate;
        } else {
            Gdx.app.log("ERROR","coordinate not initialized");
            return new Vector2();
        }
    }

    public Unit getObjectUnit() {
        if(objectInitialized) {
            return objectUnit;
        } else {
            Gdx.app.log("ERROR " + actionType,"object unit not initialized");
            return new Unit(game);
        }
    }

    public Unit getSubjectUnit() {
        if(subjectInitialized) {
            return subjectUnit;
        } else {
            Gdx.app.log("ERROR " + actionType,"subject unit not initialized");
            return new Unit(game);
        }
    }
}
