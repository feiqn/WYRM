package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public abstract class WyrComputerHandler<
        Actor   extends WyrActor<?,?,?,?>,
        Action  extends WyrInteraction<?,?>,
        Handler extends MetaHandler<?,?,?,?,?,?,?,?,?,?>
            > extends WyrHandler<Handler> {

    // Does the "thinking" for non-player entities.
    //
    // Deliberates on units' preferred actions based on
    // there Personality, then constructs an interaction
    // package to then send off to InteractionHandler.
    //
    // Responsible for parsing handling of multiple units
    // sharing priority, and deliberating the best action order
    // to take for strategic play.

    protected WyrComputerHandler() {}

    public abstract void run(Array<Actor> actor);

    protected abstract Action preferredAction(Actor actor);

}
