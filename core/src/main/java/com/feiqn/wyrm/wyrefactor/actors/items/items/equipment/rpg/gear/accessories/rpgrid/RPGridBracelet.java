package com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.accessories.rpgrid;

import com.feiqn.wyrm.wyrefactor.actors.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.RPGEquipment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.rpgrid.RPGridStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import static com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.RPGEquipmentType.BRACELET;

public  class RPGridBracelet extends RPGEquipment<
        RPGridAnimator,
        RPGridInteraction,
        RPGridMetaHandler,
        RPGridStats
            > {

    public RPGridBracelet() {
        super(BRACELET);
    }

    @Override
    protected void setup() {

    }

    @Override
    public String getName() {
        return "Braided Grass";
    }

    @Override
    public String getExamine() {
        return "Stories and legends as old as the sky teach and reinforce the importance of always keeping a piece of the Earth close to one's self at all times. A thin layer of resin watermarks this age-old style of preserving sweetgrass in a rugged but comfortable braid.";
    }

}
