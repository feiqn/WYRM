package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.accessories.rpgrid;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.RPGEquipment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.rpg.rpgrid.RPGridStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.RPGEquipmentType.AMULET;

public class RPGridAmulet extends RPGEquipment<
        RPGridAnimator,
        RPGridInteraction,
        RPGridMetaHandler,
        RPGridStats
            > {

    public RPGridAmulet() {
        super(AMULET);
    }

    @Override
    protected void setup() {

    }

    @Override
    public String getName() {
        return "Simple pendant";
    }

    @Override
    public String getExamine() {
        return  "A dull and simple pendant on an old, smooth chain. Perhaps it is worn for sentiment more than function.";
    }
}
