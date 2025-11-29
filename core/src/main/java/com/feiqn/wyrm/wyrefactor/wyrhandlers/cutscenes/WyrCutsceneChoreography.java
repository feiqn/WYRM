package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;

public abstract class WyrCutsceneChoreography {

    public enum Type {
        GRID,
        WORLD,
        NARRATIVE,
    }

    protected WyrActor subject;
    protected WyrActor object;

    protected CampaignFlags associatedCampaignFlag;

    private final Type type;

    protected WyrCutsceneChoreography(Type type) {
        this.type = type;
    }

    public Type getType() { return type; }
    public WyrActor getSubject() { return subject; }
    public WyrActor getObject() { return object; }
}
