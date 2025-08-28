package com.feiqn.wyrm.logic.handlers.conversations.triggers.types;

import com.feiqn.wyrm.logic.handlers.conversations.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversations.triggers.ConversationTrigger;

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
