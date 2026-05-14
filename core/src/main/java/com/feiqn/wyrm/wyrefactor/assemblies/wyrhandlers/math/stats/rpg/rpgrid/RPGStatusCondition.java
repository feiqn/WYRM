package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.rpg.rpgrid;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public final class RPGStatusCondition extends WyrStatusCondition {

    public RPGStatusCondition(WyRPG.StatusEffect effectType, WyrActor effectedActor) {
        super(effectType, effectedActor);
    }

    @Override
    public WyRPG.StatusEffect getEffectType() {
        assert super.getEffectType() instanceof WyRPG.StatusEffect;
        return (WyRPG.StatusEffect) super.getEffectType();
    }

}
