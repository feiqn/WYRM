package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script;

import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;

public abstract class WyrCutsceneChoreography<T extends WyrActor> extends Subjectivity<T> implements Wyr {

    public enum Set {
        WORLD,
        DIALOG,
    }

    private final Set set;

    protected CampaignFlags associatedCampaignFlag;

    protected WyrCutsceneChoreography(Set set) { this.set = set; }

    public Set getSet() { return set; }

}
