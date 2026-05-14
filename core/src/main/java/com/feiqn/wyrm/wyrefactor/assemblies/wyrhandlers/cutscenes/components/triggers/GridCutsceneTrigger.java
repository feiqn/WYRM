package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.triggers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;

public final class GridCutsceneTrigger extends WyrCutsceneTrigger {

    public GridCutsceneTrigger(WYRM.CampaignFlag triggerFlag) {
        super(triggerFlag);
    }

    public GridCutsceneTrigger(Integer turnToTrigger, boolean exactTurn) {
        super(turnToTrigger, exactTurn);
    }

    public GridCutsceneTrigger(WYRM.Character rosterID, boolean beforeCombat, boolean requiresAggressor) {
        super(rosterID, beforeCombat, requiresAggressor);
    }

    public GridCutsceneTrigger(WYRM.Character attacker, WYRM.Character defender, boolean beforeCombat) {
        super(attacker, defender, beforeCombat);
    }

    public GridCutsceneTrigger(WYRM.Character deathOf) {
        super(deathOf);
    }

    public GridCutsceneTrigger(TeamAlignment deathOf) {
        super(deathOf);
    }

    public GridCutsceneTrigger(WYRM.CutsceneID otherID) {
        super(otherID);
    }

    public GridCutsceneTrigger(WYRM.Character rosterID, Array<Vector2> areas) {
        super(rosterID, areas);
    }

    public GridCutsceneTrigger(WYRM.Character rosterID, Vector2 area) {
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
        return WyrType.RPGRID;
    }
}
