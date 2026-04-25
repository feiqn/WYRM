package com.feiqn.wyrm.wyrefactor.actors.items.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories.WyrAmulet;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories.WyrBracelet;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.accessories.WyrRing;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.armor.WyrArmor;
import com.feiqn.wyrm.wyrefactor.actors.items.equipment.gear.weapons.WyrWeapon;
import com.feiqn.wyrm.wyrefactor.actors.items.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrInventory implements Wyr {

    // defines and holds info for an actor's,
    // usually a GridUnit's, equipment slots and
    // loadout. Gear, inventory, etc.

    // probably replaces SimpleInventory

    private static final Array<WyrItem> containers = new Array<>();

    private static WyrAmulet amuletSlot   = new WyrAmulet();
    private static WyrArmor armorSlot    = new WyrArmor();
    private static WyrRing ringSlot     = new WyrRing();
    private static WyrWeapon weaponSlot   = new WyrWeapon();
    private static WyrBracelet braceletSlot = new WyrBracelet();

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
        returnValue.addAll(amuletSlot, armorSlot, braceletSlot, weaponSlot, ringSlot);
        return returnValue;
    }

    public void equipBracelet(WyrBracelet bracelet) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipWeapon(WyrWeapon weapon) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipAmulet(WyrAmulet amulet) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipArmor(WyrArmor armor) {
        Gdx.app.log("TODO", "XD");
    }
    public void equipRing(WyrRing ring) {
        Gdx.app.log("TODO", "XD");
    }

    public WyrBracelet getEquippedBracelet() { return braceletSlot; }
    public WyrWeapon   getEquippedWeapon()   { return weaponSlot; }
    public WyrAmulet   getEquippedAmulet()   { return amuletSlot; }
    public WyrArmor    getEquippedArmor()    { return armorSlot; }
    public WyrRing     getEquippedRing()     { return ringSlot; }

    public Array<WyrItem> getContainers() { return containers; }


}
