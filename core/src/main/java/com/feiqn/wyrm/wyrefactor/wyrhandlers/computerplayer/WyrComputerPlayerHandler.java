package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrComputerPlayerHandler<
        Actor    extends WyrActor,
        Computer extends WyrComputerPlayer<?,?,?>
            > extends WyrHandler {

    // Does the "thinking" for non-player entities,
    // particularly handling CP Actions (instruction packages)
    // which are built and passed in by WyrComputerPlayer and are
    // then passed to WyrActorHandler.
    // Also responsible for parsing handling of multiple units
    // sharing priority, and deliberating the best action order
    // to take for strategic play.

    protected Computer computerPlayer;

    protected WyrComputerPlayerHandler() {}

    public abstract void run(Actor actor);



}
