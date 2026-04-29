package com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.accessories.rpgrid;

import com.feiqn.wyrm.wyrefactor.actors.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.RPGEquipment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.RPGEquipmentType.RING;

public class RPGridRing extends RPGEquipment<
        RPGridAnimator,
        RPGridInteraction,
        RPGridMetaHandler,
        RPGridStats
            > {

    public RPGridRing() {
        super(RING);
    }

    @Override
    protected void setup() {

    }

    @Override
    public String getName() {
        return "Dull ring";
    }

    @Override
    public String getExamine() {
        return "A plain piece of common metal, skillfully banded into a smooth and comfortable fit... for some great-ancestor's hand, no doubt.";
    }

}
