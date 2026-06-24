package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

public final class Interactions {

    private Interactions() {}

    public static WyrInteraction FireArmament(WyrActor.Unit unitFiring, WyrActor.Prop propWithArmament, WyrActor targetOfFire) {
        return new WyrInteraction(unitFiring).fireArmament(propWithArmament, targetOfFire);
    }


    public static WyrInteraction Aim(WyrActor.Unit unitAiming, WyrActor.Prop propBeingAimed) {
        return new WyrInteraction(unitAiming).aim(propBeingAimed);
    }

    public static WyrInteraction Examine(WyrActor toExamine) {
        return new WyrInteraction(toExamine).examine();
    }

}
