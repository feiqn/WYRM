package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.campaign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.FlagID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.StageID;

public class WyrCampaignHandler extends WyrHandler {

    // CampaignHandler appears to be fairly
    // agnostic to Wyr vs OLD_ formatting.
    // Having it extend WyrHandler and leaving
    // it in with the old CameraMan as one of
    // the few legacy systems to get a lifeboat.

    // Will come back later and fill out the systems better.
    // It mostly seems agnostic because there's hardly
    // anything here.

    /**
     * Responsible for tracking save data for a given playthrough.
     */

    private final Preferences save;

    public WyrCampaignHandler() { save = Gdx.app.getPreferences("internalState"); }

    public void hitFlag(FlagID flag) {
        save.putBoolean(flag.toString(), true);
        save.flush();
    }
    public boolean checkFlag(FlagID flag) {
        if(save.contains(flag.toString())) {
            return save.getBoolean(flag.toString());
        }
        return false;
    }

    public void recruitCharacter(Character.Name charID) {
        save.putBoolean(charID + "_RECRUITED", true);
        save.flush();
    }
    public void killCharacter(Character.Name charID) {
        save.putBoolean(charID + "_DIED", true);
        save.flush();
    }
    public boolean characterIsAvailable(Character.Name charID) {
        return characterWasRecruited(charID) && characterIsAlive(charID);
    }
    public boolean characterWasRecruited(Character.Name charID) {
        return save.contains(charID + "_RECRUITED");
    }
    public boolean characterIsAlive(Character.Name charID) {
        return !save.contains(charID + "_DIED");
    }

    public void unlockStage(StageID stageID) {
        save.putBoolean(stageID + "_UNLOCKED", true);
        save.flush();
    }
    public Array<StageID> unlockedStages() {
        final Array<StageID> unlockedStages = new Array<>();
        for(StageID stage : StageID.values()) {
            if(save.contains(stage + "_UNLOCKED")) {
                unlockedStages.add(stage);
            }
        }
        return unlockedStages;
    }
    public void winStage(StageID stageID) {
        save.putBoolean(stageID + "_CLEARED", true);
        save.flush();
    }
    public Array<StageID> wonStages() {
        final Array<StageID> wonStages = new Array<>();
        for(StageID stage : StageID.values()) {
            if(save.contains(stage + "_CLEARED")) {
                wonStages.add(stage);
            }
        }
        return wonStages;
    }
    public void failStage(StageID stageID) {
        save.putBoolean(stageID + "_CLEARED", false);
        save.putBoolean(stageID + "_FAILED", true);
        save.flush();
    }
    public boolean stageWon(StageID stageID) {
        return save.contains(stageID + "_CLEARED");
    }
    public boolean stageFailed(StageID stageID) {
        return save.contains(stageID + "_FAILED");
    }
    public Array<StageID> failedStages() {
        final Array<StageID> failedStages = new Array<>();
        for(StageID stage : StageID.values()) {
            if(save.contains(stage + "_FAILED")) {
                failedStages.add(stage);
            }
        }
        return failedStages;
    }

}
