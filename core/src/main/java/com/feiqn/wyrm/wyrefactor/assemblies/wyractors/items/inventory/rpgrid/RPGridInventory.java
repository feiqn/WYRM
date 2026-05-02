package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory.rpgrid;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory.WyrInventory;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.accessories.rpgrid.RPGridAmulet;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.accessories.rpgrid.RPGridBracelet;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.accessories.rpgrid.RPGridRing;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.armor.rpgrid.RPGridArmor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear.weapons.rpgrid.RPGridWeapon;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.rpg.RPGStatType;

public final class RPGridInventory extends WyrInventory {

    private static RPGridAmulet   amuletSlot   = new RPGridAmulet();
    private static RPGridArmor    armorSlot    = new RPGridArmor();
    private static RPGridRing     ringSlot     = new RPGridRing();
    private static RPGridWeapon   weaponSlot   = new RPGridWeapon();
    private static RPGridBracelet braceletSlot = new RPGridBracelet();

//    public Array<RPGEquipment<RPGridAnimator, RPGridInteraction, RPGridMetaHandler, RPGridStats
//        >> getEquippedGear() {
//        final Array<WyrEquipment> returnValue = new Array<>();
//        returnValue.addAll(amuletSlot, armorSlot, braceletSlot, weaponSlot, ringSlot);
//        return returnValue;
//    }

    public void equipBracelet(RPGridBracelet bracelet) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipWeapon(RPGridWeapon weapon) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipAmulet(RPGridAmulet amulet) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipArmor(RPGridArmor armor) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipRing(RPGridRing ring) {
        Gdx.app.log("TODO", "XD");
    }

    public RPGridBracelet getEquippedBracelet() { return braceletSlot; }
    public RPGridWeapon   getEquippedWeapon()   { return weaponSlot;   }
    public RPGridAmulet   getEquippedAmulet()   { return amuletSlot;   }
    public RPGridArmor    getEquippedArmor()    { return armorSlot;    }
    public RPGridRing     getEquippedRing()     { return ringSlot;     }


    public int combinedGearModifiersValue(RPGStatType stat) {
        // Add values from all relevant gear then return total.
        switch(stat) {
            case STRENGTH:
            case DEXTERITY:
            case DEFENSE:

            case MAGIC:
            case RESISTANCE:

            case SPEED:
            default:
                break;
        }
        return 0;
    }

}
