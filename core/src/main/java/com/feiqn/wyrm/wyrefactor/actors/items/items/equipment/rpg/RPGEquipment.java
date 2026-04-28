package com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg;

import com.feiqn.wyrm.wyrefactor.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;

public abstract class RPGEquipment<
        Animation     extends WyrAnimator<?,?,?>,
        Interaction   extends WyrInteraction<?,?>,
        MetaHandle    extends MetaHandler<?,?,?,?,?,?,?,?,?,?>,
        Stats         extends WyrStats<?,?,?,?,?,?>
            > extends WyrEquipment<Animation, RPGEquipmentType, Interaction, MetaHandle, Stats> {


    public RPGEquipment(RPGEquipmentType type) {
        super(type);
    }

}
