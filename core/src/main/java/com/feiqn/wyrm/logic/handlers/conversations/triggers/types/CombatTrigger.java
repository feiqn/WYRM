package com.feiqn.wyrm.logic.handlers.conversations.triggers.types;

import com.feiqn.wyrm.logic.handlers.conversations.dialog.DialogScript;
import com.feiqn.wyrm.logic.handlers.conversations.triggers.ConversationTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.EnumSet;

public class CombatTrigger extends ConversationTrigger {

    public enum When {
        BEFORE,
        DURING,
        AFTER
    }

    private When when;

    public CombatTrigger(EnumSet<UnitRoster> triggerUnits, DialogScript script, When when) {
        super(triggerUnits, script);
        this.when = when;
    }

    public boolean checkTrigger(UnitRoster unit, When now) {
        if(now == when && super.checkTrigger(unit)) {
            triggered = true;
            return true;
        }
        return false;
    }
}
