package com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.StageList;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.army.ArmyHandler;

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

    private final ArmyHandler army;


    public WyrCampaignHandler(WYRMGame root) {
        super(root, WyrType.AGNOSTIC);
        save = Gdx.app.getPreferences("internalState");
        army = new ArmyHandler(root);
    }

    /**
     * AUTOMATED PROCESSES:
     * STATS, INVENTORIES, ETC.
     */
    private void saveArmyStats() {

    }

    private void saveArmyGear() {

    }

    /**
     * INTERFACE
     */
    public void saveGame() {
        saveArmyGear();
        saveArmyStats();
        save.flush();
    }

    public void setCampaignFlag(CampaignFlags flag) {
        save.putBoolean("" + flag, true);
        save.flush();
    }

    /**
     * GETTERS
     */
    public boolean checkCampaignFlag(CampaignFlags flag) {
        if(save.contains("" + flag)) {
            return save.getBoolean("" + flag);
        }
        return false;
    }

    public Array<StageList> unlockedStages() {
        final Array<StageList> unlockedStages = new Array<>();

        for(StageList stage : StageList.values()) {
            if(save.contains(stage + "_UNLOCKED")) {
                unlockedStages.add(stage);
            }
        }

        return unlockedStages;
    }

    public ArmyHandler getArmy() { return army; }
}
