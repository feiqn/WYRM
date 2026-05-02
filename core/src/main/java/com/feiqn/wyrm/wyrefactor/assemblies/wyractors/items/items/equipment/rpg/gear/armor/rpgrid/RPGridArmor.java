package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.armor.rpgrid;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.armor.RPGArmor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public class RPGridArmor extends RPGArmor<
        RPGridAnimator,
        RPGridInteraction,
        RPGridMetaHandler,
        RPGridStats
            > {

    // category, weight, etc

    public RPGridArmor() {
        super();

    }
}
