package com.feiqn.wyrm.logic.handlers.conversation.triggers;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;
import java.util.Set;

public class ConversationTrigger {

    protected boolean triggered;
    protected final EnumSet<UnitRoster> triggerUnits;
    protected final DialogScript dialogScript;

    public ConversationTrigger(EnumSet<UnitRoster> triggerUnits, DialogScript dialogScript) {
        this.triggerUnits = triggerUnits;
        this.dialogScript = dialogScript;
        triggered = false;
    }

    protected boolean checkTrigger(UnitRoster unit) {
        // child class should set triggered = true;
        if(triggered) return false;
        return triggerUnits.contains(unit);
    }

    public DialogScript getConversation() {
        return dialogScript;
    }

    public boolean isTriggered() {
        return triggered;
    }

}
