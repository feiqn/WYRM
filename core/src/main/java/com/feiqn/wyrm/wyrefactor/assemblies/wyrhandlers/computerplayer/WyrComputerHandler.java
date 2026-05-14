package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrComputerHandler extends WyrHandler {

    // TODO: I think this is a case where <T extends> is appropriate

    // Does the "thinking" for non-player entities.
    //
    // Deliberates on units' preferred actions based on
    // there Personality, then constructs an interaction
    // package to then send off to InteractionHandler.
    //
    // Responsible for parsing handling of multiple units
    // sharing priority, and deliberating the best action order
    // to take for strategic play.

    public WyrComputerHandler() {}

    public WyrComputerHandler(MetaHandler metaHandler) {
        super(metaHandler);
    }

//    public void run(Array<WyrActor> actor) {}

//    protected WyrInteraction preferredAction(WyrActor actor) {}

}
