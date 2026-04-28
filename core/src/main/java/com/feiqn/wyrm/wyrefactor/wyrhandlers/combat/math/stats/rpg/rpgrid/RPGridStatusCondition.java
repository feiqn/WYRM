package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid;

import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.RPGStatusEffect;

public final class RPGridStatusCondition extends WyrStatusCondition<RPGStatusEffect, RPGridActor> {

    public RPGridStatusCondition(RPGStatusEffect effectType, RPGridActor effectedActor) {
        super(effectType, effectedActor);
    }

}
