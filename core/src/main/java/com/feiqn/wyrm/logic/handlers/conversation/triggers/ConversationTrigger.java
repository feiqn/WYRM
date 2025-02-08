package com.feiqn.wyrm.logic.handlers.conversation.triggers;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;
import java.util.Set;

public class ConversationTrigger {

    protected boolean triggered;
    protected final EnumSet<UnitRoster> triggerUnits;
    protected final Conversation conversation;

    public ConversationTrigger(EnumSet<UnitRoster> triggerUnits, Conversation conversation) {
        this.triggerUnits = triggerUnits;
        this.conversation = conversation;
        triggered = false;
    }

    protected boolean checkTrigger(UnitRoster unit) {
        // child class should set triggered = true;
        if(triggered) return false;
        return triggerUnits.contains(unit);
    }

    public Conversation getConversation() {
        return conversation;
    }

    public boolean isTriggered() {
        return triggered;
    }

}
