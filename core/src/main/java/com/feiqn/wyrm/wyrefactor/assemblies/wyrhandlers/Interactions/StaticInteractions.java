package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;

public final class StaticInteractions {

    private StaticInteractions() {}

    public static WyrInteraction AimInteraction(WyrActor.Prop parent) {
        return new WyrInteraction(parent, Wyr.GameKit.RPG.InteractionType.PROP_AIM, 1);
    }

    public static WyrInteraction ExamineInteraction(WyrActor parent) {
        return new WyrInteraction(parent, Wyr.GameKit.RPG.InteractionType.EXAMINE);
    }

}
