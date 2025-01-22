package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import javax.print.attribute.EnumSyntax;
import java.awt.*;
import java.util.EnumSet;
import java.util.Set;

public class ConversationTrigger {

    private boolean triggered;
    private final EnumSet<UnitRoster> triggerUnits;
    private final Set<Vector2> triggerTiles;
    private final Conversation conversation;

    public ConversationTrigger(EnumSet<UnitRoster> triggerUnits, Set<Vector2> triggerTiles, Conversation conversation) {
        this.triggerUnits = triggerUnits;
        this.triggerTiles = triggerTiles;
        this.conversation = conversation;
        triggered = false;
    }

    public boolean checkTrigger(UnitRoster unit, Vector2 tilePosition) {
        if(triggered) return false;
        if(triggerUnits.contains(unit) && triggerTiles.contains(tilePosition)) {
            triggered = true;
            return true;
        }
        return false;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public boolean isTriggered() {
        return triggered;
    }

}
