package com.feiqn.wyrm.wyrefactor.actors.items.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.accessories.rpgrid.RPGridAmulet;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.accessories.rpgrid.RPGridBracelet;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.accessories.rpgrid.RPGridRing;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.armor.RPGArmor;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.rpgrid.RPGridWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrInventory implements Wyr {

    // defines and holds info for an actor's equipment
    // slots and loadout. Gear, inventory, etc.

    // probably replaces SimpleInventory

    private static final Array<WyrItem> containers = new Array<>();

//    private static RPGridAmulet amuletSlot   = new RPGridAmulet();
//    private static RPGArmor armorSlot    = new RPGArmor();
//    private static RPGridRing ringSlot     = new RPGridRing();
//    private static RPGridWeapon weaponSlot   = new RPGridWeapon();
//    private static RPGridBracelet braceletSlot = new RPGridBracelet();

    public WyrInventory() {}

//    public int combinedGearModifiersValue(RPGStatType stat) {
//        // Add values from all relevant gear then return total.
//        switch(stat) {
//            case STRENGTH:
//            case DEXTERITY:
//            case DEFENSE:
//
//            case MAGIC:
//            case RESISTANCE:
//
//            case SPEED:
//            case HEALTH:
//            default:
//                break;
//        }
//        return 0;
//    }

    public Array<WyrEquipment> getEquippedGear() {
        final Array<WyrEquipment> returnValue = new Array<>();
//        returnValue.addAll(amuletSlot, armorSlot, braceletSlot, weaponSlot, ringSlot);
        return returnValue;
    }

    public void equipBracelet(RPGridBracelet bracelet) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipWeapon(RPGridWeapon weapon) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipAmulet(RPGridAmulet amulet) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipArmor(RPGArmor armor) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipRing(RPGridRing ring) {
        Gdx.app.log("TODO", "XD");
    }

//    public RPGridBracelet getEquippedBracelet() { return braceletSlot; }
//    public RPGridWeapon getEquippedWeapon()   { return weaponSlot; }
//    public RPGridAmulet getEquippedAmulet()   { return amuletSlot; }
//    public RPGArmor getEquippedArmor()    { return armorSlot; }
//    public RPGridRing getEquippedRing()     { return ringSlot; }

    public Array<WyrItem> getContainers() { return containers; }


}
