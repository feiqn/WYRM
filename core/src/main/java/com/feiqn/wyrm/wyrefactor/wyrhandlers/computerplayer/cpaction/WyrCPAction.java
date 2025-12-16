package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction;

import com.feiqn.wyrm.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract  class WyrCPAction extends Wyr {

    private final ActionType actionType; // aka 'verb'

    public WyrCPAction(WyrType type, ActionType actionType) {
        super(type);
        this.actionType = actionType;
    }

    public ActionType actionType() { return actionType; }

}
