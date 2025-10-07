package com.feiqn.wyrm.logic.handlers.conversation.triggers.types;

import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;

public class DeathTrigger extends ConversationTrigger {

    public DeathTrigger(EnumSet<UnitRoster> triggerUnits, DialogScript script) {
        super(triggerUnits, script);
    }

    public boolean checkTrigger(UnitRoster unit) {
        return super.checkTrigger(unit);
    }

}
