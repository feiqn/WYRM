package com.feiqn.wyrm.logic.handlers.conversation.triggers.types;

import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;

public class DeathTrigger extends ConversationTrigger {

    public DeathTrigger(EnumSet<UnitRoster> triggerUnits, Conversation conversation) {
        super(triggerUnits, conversation);
    }

    public boolean checkTrigger(UnitRoster unit) {
        return super.checkTrigger(unit);
    }

}
