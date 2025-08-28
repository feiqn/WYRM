package com.feiqn.wyrm.logic.handlers.conversations.triggers.types;

import com.feiqn.wyrm.logic.handlers.conversations.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversations.triggers.ConversationTrigger;
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
