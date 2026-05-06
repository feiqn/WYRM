package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.grid;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.campaign.wyrm.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCon;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public final class RPGridWinCon extends WyrWinCon {

    private RPGridActor associatedActor = null;
    private CampaignFlags associatedFlag = null;
    private Vector2 associatedCoordinate = null;
    private int turnGoal = -1;

    private final RPGridMetaHandler h;

    public RPGridWinCon(RPGridMetaHandler metaHandler, String uniqueID) { super(uniqueID); h = metaHandler; }

    public RPGridWinCon setActor(RPGridActor actor) {
        associatedActor = actor;
        imageDrawable.setDrawable(actor.getDrawable());
        return this;
    }
    public RPGridWinCon setFlag(CampaignFlags flag) { associatedFlag = flag; return this; }
    public RPGridWinCon setLocal(Vector2 coordinate) { associatedCoordinate = coordinate; return this;}
    public RPGridWinCon setTurn(int turnGoal) { this.turnGoal = turnGoal; return this; }

    public CampaignFlags getAssociatedFlag() {
        return associatedFlag;
    }

    public int getTurnGoal() {
        return turnGoal;
    }

    public RPGridActor getAssociatedActor() {
        return associatedActor;
    }

    public Vector2 getAssociatedCoordinate() {
        return associatedCoordinate;
    }

    @Override
    public void satisfy() {
        super.satisfy();
        // trigger stuff
    }

    @Override
    public void reveal() {
        super.reveal();
//        h.hud().
    }
}
