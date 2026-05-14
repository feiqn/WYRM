package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrInteractionHandler extends WyrHandler {

    public WyrInteractionHandler() {}

    public WyrInteractionHandler(MetaHandler metaHandler) {
        super(metaHandler);
    }

    public Array<WyrInteraction> getActorInteractions() {
        return null;
    }

    public void parseInteractable(WyrInteraction interaction) {}
}
