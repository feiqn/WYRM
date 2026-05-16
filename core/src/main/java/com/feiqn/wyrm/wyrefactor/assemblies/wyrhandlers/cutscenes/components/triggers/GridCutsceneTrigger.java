package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.triggers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public final class GridCutsceneTrigger extends WyrCutsceneTrigger {

    public GridCutsceneTrigger(FlagID triggerFlag) {
        super(triggerFlag);
    }

    public GridCutsceneTrigger(Integer turnToTrigger) {
        super(turnToTrigger, false);
    }


    public GridCutsceneTrigger(Integer turnToTrigger, boolean exactTurn) {
        super(turnToTrigger, exactTurn);
    }

    public GridCutsceneTrigger(CharacterID rosterID, boolean beforeCombat, boolean requiresAggressor) {
        super(rosterID, beforeCombat, requiresAggressor);
    }

    public GridCutsceneTrigger(CharacterID attacker, CharacterID defender, boolean beforeCombat) {
        super(attacker, defender, beforeCombat);
    }

    public GridCutsceneTrigger(CharacterID deathOf) {
        super(deathOf);
    }

    public GridCutsceneTrigger(TeamAlignment deathOf) {
        super(deathOf);
    }

    public GridCutsceneTrigger(CutsceneID otherID) {
        super(otherID);
    }

    public GridCutsceneTrigger(CharacterID rosterID, Array<Vector2> areas) {
        super(rosterID, areas);
    }

    public GridCutsceneTrigger(CharacterID rosterID, Vector2 area) {
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
