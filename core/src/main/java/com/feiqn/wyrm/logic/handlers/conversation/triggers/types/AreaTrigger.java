package com.feiqn.wyrm.logic.handlers.conversation.triggers.types;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;
import java.util.Set;

public class AreaTrigger extends ConversationTrigger {

    private final Set<Vector2> triggerTiles;

    public AreaTrigger(EnumSet<UnitRoster> triggerUnits, Set<Vector2> triggerTiles, DialogScript script) {
        super(triggerUnits, script);
        this.triggerTiles = triggerTiles;
    }

    public boolean checkTrigger(UnitRoster unit, Vector2 tilePosition) {
        if(super.checkTrigger(unit) && triggerTiles.contains(tilePosition)) {
            triggered = true;
            return true;
        }
        return false;
    }

}
