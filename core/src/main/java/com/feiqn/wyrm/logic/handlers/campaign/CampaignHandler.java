package com.feiqn.wyrm.logic.handlers.campaign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.StageList;
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

    public void setStageAsUnlocked(StageList id) {
        save.putBoolean("STAGE_WAS_UNLOCKED:" + id, true);
    }

    public void setStageAsCompleted(StageList id) {
        save.putBoolean("STAGE_WAS_COMPLETED:" + id, true);
    }

    public boolean stageWasUnlocked(StageList id) {
        if(save.contains("STAGE_WAS_UNLOCKED:" + id)) {
            return save.getBoolean("STAGE_WAS_UNLOCKED:" + id);
        } else {
            return false;
        }
    }

    public boolean stageWasCompleted(StageList id) {
        if(save.contains("STAGE_WAS_COMPLETED:" + id)) {
            return save.getBoolean("STAGE_WAS_COMPLETED:" + id);
        } else {
            return false;
        }
    }

    public void setUnitAsRecruited(UnitRoster id) {
        save.putBoolean("UNIT_WAS_RECRUITED:" + id, true);
    }

    public void setUnitAsDead(UnitRoster id) {
        save.putBoolean("UNIT_HAS_DIED:" + id, true);
    }

    public boolean unitWasRecruited(UnitRoster id) {
        if(save.contains("UNIT_WAS_RECRUITED:" + id)) {
            return save.getBoolean("UNIT_WAS_RECRUITED:" + id);
        } else {
            return false;
        }
    }

    public boolean unitIsAlive(UnitRoster id) {
        if(save.contains("UNIT_HAS_DIED:" + id)) {
            return !save.getBoolean("UNIT_HAS_DIED:" + id);
        } else {
            return true;
        }
    }

    /**
     * GETTERS
     */

    public Array<StageList> unlockedStages() {
        final Array<StageList> unlockedStages = new Array<>();

        for(StageList stage : StageList.values()) {
            if(save.contains("STAGE_UNLOCKED:" + stage)) {
                unlockedStages.add(stage);
            }
        }

        return unlockedStages;
    }

}
