package com.feiqn.wyrm.logic.handlers.conversation.triggers.types;

import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;

public class TurnTrigger extends ConversationTrigger {

    private final int turn;

    public TurnTrigger(EnumSet<UnitRoster> triggerUnits, Conversation conversation, int turn) {
        super(triggerUnits, conversation);
        this.turn = turn;
    }

    public boolean checkTrigger(UnitRoster unit, int turn) {
        return this.turn == turn && super.checkTrigger(unit);
    }

}
