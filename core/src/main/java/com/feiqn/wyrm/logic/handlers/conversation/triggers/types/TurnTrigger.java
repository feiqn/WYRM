package com.feiqn.wyrm.logic.handlers.conversation.triggers.types;

import com.feiqn.wyrm.logic.handlers.conversation.Conversation;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.ConversationTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;

public class TurnTrigger extends ConversationTrigger {

    private final int turn;

    public TurnTrigger(DialogScript script, int turn) {
        super(script);
        this.turn = turn;
    }

    public boolean checkTrigger(int turn) {
        return this.turn == turn && !triggered;
    }

}
