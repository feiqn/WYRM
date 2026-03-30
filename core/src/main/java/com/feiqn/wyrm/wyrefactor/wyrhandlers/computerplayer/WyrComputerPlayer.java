package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;

public abstract class WyrComputerPlayer<Actor extends WyrActor, Action extends WyrInteraction, Handler extends MetaHandler<?,?,?,?,?,?,?,?,?>> implements Wyr {

    // Deliberates on the preferred action for a
    // given unit's personality type,
    // constructs an Interactable package representing
    // said action,
    // and returns that package to parent handler for
    // consideration and execution.

    protected Handler handlers;

    protected WyrComputerPlayer() {}

    public abstract Action preferredAction(Actor actor);



}
