package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

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
