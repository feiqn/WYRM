package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.triggers.WyrCutsceneTrigger;

public class GridCutsceneTrigger extends WyrCutsceneTrigger<GridUnit> {

    public GridCutsceneTrigger(CampaignFlags triggerFlag) {
        super(triggerFlag);
    }

    public GridCutsceneTrigger(Integer turnToTrigger, boolean exactTurn) {
        super(turnToTrigger, exactTurn);
    }

    public GridCutsceneTrigger(UnitIDRoster rosterID, boolean beforeCombat, boolean requiresAggressor) {
        super(rosterID, beforeCombat, requiresAggressor);
    }

    public GridCutsceneTrigger(UnitIDRoster attacker, UnitIDRoster defender, boolean beforeCombat) {
        super(attacker, defender, beforeCombat);
    }

    public GridCutsceneTrigger(UnitIDRoster deathOf) {
        super(deathOf);
    }

    public GridCutsceneTrigger(TeamAlignment deathOf) {
        super(deathOf);
    }

    public GridCutsceneTrigger(CutsceneID otherID) {
        super(otherID);
    }

    public GridCutsceneTrigger(UnitIDRoster rosterID, Array<Vector2> areas) {
        super(rosterID, areas);
    }

    public GridCutsceneTrigger(UnitIDRoster rosterID, Vector2 area) {
        super(rosterID, area);
    }

    public GridCutsceneTrigger(Vector2 area) {
        super(area);
    }

    public GridCutsceneTrigger(Vector2 area, TeamAlignment requiredTeamAlignment) {
        super(area, requiredTeamAlignment);
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }
}
