package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;

public abstract class WyrCutsceneChoreography {

    protected WyrActor subject;
    protected WyrActor object;

    protected CampaignFlags associatedCampaignFlag;

    private final WyrType type;

    protected WyrCutsceneChoreography(WyrType type) {
        this.type = type;
    }

    protected void setSubject(WyrActor actor) { this.subject = actor; }
    protected void setObject(WyrActor actor) { this.object = actor; }

    public WyrType getType() { return type; }
    public WyrActor getSubject() { return subject; }
    public WyrActor getObject() { return object; }
}
