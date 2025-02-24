package com.feiqn.wyrm.logic.handlers.conversation.triggers;

import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;

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

    public DialogScript getScript() {
        return dialogScript;
    }

    public boolean isTriggered() {
        return triggered;
    }

}
