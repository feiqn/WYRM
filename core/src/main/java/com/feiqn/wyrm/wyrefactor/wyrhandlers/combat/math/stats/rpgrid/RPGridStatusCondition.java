package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpgrid;

import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStatusCondition;

public final class RPGridStatusCondition extends WyrStatusCondition<RPGridStatusCondition.EffectType, RPGridActor> {

    public enum EffectType {
        BURN,
        POISON,
        STUN,
        CHILL,
        SOUL_BRAND,
        PETRIFY,
    }


    public RPGridStatusCondition(EffectType effectType, RPGridActor effectedActor) {
        super(effectType, effectedActor);
    }

}
