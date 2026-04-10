package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteraction;

public abstract class WyrActorHandler<
        Actor extends WyrActor<?,?>,
        Interaction extends WyrInteraction<?,?>
            > extends WyrHandler {



    protected WyrActorHandler() {}

    public abstract Array<Interaction> getActorInteractions();

}
