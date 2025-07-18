package com.feiqn.wyrm.logic.handlers.campaign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.screens.StageList;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class CampaignHandler {

    /**
     * This is ultimately responsible for tracking all save data for a given save file.
     */

    private final WYRMGame game;

    private final Preferences save;

    private final ArmyHandler army;


    public CampaignHandler(WYRMGame game) {
        this.game = game;
        save = Gdx.app.getPreferences("internalState");
        army = new ArmyHandler(game);
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

    public void setFlag(CampaignFlags flag) {
        save.putBoolean("" + flag, true);
        save.flush();
    }

    /**
     * GETTERS
     */

    public boolean getFlag(CampaignFlags flag) {
        if(save.contains("" + flag)) {
            return save.getBoolean("" + flag);
        }
        return false;
    }

//    public boolean unitIsAlive(UnitRoster id) {
//        if(save.contains("UNIT_HAS_DIED:" + id)) {
//            return !save.getBoolean("UNIT_HAS_DIED:" + id);
//        } else {
//            return true;
//        }
//    }

    public Array<StageList> unlockedStages() {
        final Array<StageList> unlockedStages = new Array<>();

        for(StageList stage : StageList.values()) {
            if(save.contains(stage + "_UNLOCKED")) {
                unlockedStages.add(stage);
            }
        }

        return unlockedStages;
    }

}
