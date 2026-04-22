package com.feiqn.wyrm.wyrefactor.actors.Interactions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;

public abstract class WyrInteractionHandler<
        Interaction extends WyrInteraction<?,?>
            > extends WyrHandler {

    protected WyrInteractionHandler() {}

    public abstract Array<Interaction> getActorInteractions();

    public abstract void parseInteractable(Interaction interaction);
}
