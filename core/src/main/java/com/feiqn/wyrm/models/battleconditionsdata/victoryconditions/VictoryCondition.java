package com.feiqn.wyrm.models.battleconditionsdata.victoryconditions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.models.battleconditionsdata.VictoryConditionType;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class VictoryCondition {

    protected WYRMGame game;

    public VictoryConditionType victConType;

    protected boolean terminal,
                      satisfied,
                      hidden;

    protected UnitRoster associatedUnit;
    protected CampaignFlags associatedFlag;
    protected Vector2 associatedCoordinateXY;
    protected int turnGoal;

    protected String objectiveText;
    protected String moreInfo;

    protected Drawable drawable;

    public VictoryCondition(WYRMGame game, VictoryConditionType type, boolean terminal) {
        this.game = game;
        this.associatedUnit = UnitRoster.MR_TIMN;
        victConType = type;
        this.terminal = terminal;
        turnGoal = 0;
        satisfied = false;
        hidden = true;
        associatedCoordinateXY = new Vector2(-1, -1);
    }

    public boolean isTerminal() { return  terminal; }

    public void setAssociatedCoordinateXY(int right, int up) {
        associatedCoordinateXY = new Vector2(right, up);
    }

    public void setObjectiveText(String sequence) {
        objectiveText = sequence;
    }

    public void setMoreInfo(String string) {
        moreInfo = string;
    }

    public void setAssociatedFlag(CampaignFlags flag) {
        associatedFlag = flag;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void reveal() {
        if(hidden) hidden = false;
    }

    public void satisfy() {
        if (!satisfied) satisfied = true;
        if(associatedFlag != null) {
            game.campaignHandler.setFlag(associatedFlag);
        }
    }

    public boolean conditionIsSatisfied(){ return satisfied; }

    public void updateInternalValues() {
        // used by child classes to set flags for internal game state
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public UnitRoster getAssociatedUnit() { return associatedUnit; }

    public CampaignFlags getAssociatedFlag() { return associatedFlag; }

    public Vector2 getAssociatedCoordinateXY() { return associatedCoordinateXY; }

    public String getObjectiveText() {
        return objectiveText;
    }

    public int getTurnGoal() {
        return turnGoal;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public VictoryConditionType getVictConType() {
        return victConType;
    }

    public boolean isHidden() {
        return hidden;
    }
}
