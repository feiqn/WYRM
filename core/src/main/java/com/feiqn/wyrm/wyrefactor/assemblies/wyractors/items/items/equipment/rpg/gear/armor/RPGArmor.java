package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.armor;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.RPGEquipment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.RPGEquipmentType.ARMOR;


public abstract class RPGArmor<
        Animation     extends WyrAnimator<?,?,?>,
        Interaction   extends WyrInteraction<?,?>,
        MetaHandle    extends MetaHandler<?,?,?,?,?,?,?,?,?,?>,
        Stats         extends WyrStats<?,?,?,?,?,?>
            > extends RPGEquipment<Animation, Interaction, MetaHandle, Stats> {


    public RPGArmor() {
        super(ARMOR);
    }

    @Override
    protected void setup() {

    }

    @Override
    public String getName() {
        return "Cloth Shirt";
    }

    @Override
    public String getExamine() {
        return "Worn, faded, darned, and patched. Well-loved, without any doubt.";
    }

}
