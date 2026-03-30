package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.ai.actions.AI_ActionType;
import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract  class WyrCPAction extends Wyr_DEPRECATED {

    private final AI_ActionType actionType; // aka 'verb'

    public WyrCPAction(WyrType type, AI_ActionType actionType) {
        super(type);
        this.actionType = actionType;
    }

    public AI_ActionType actionType() { return actionType; }

}
