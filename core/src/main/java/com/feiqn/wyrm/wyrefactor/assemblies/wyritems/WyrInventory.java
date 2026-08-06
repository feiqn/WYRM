package com.feiqn.wyrm.wyrefactor.assemblies.wyritems;


import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.prefabs.ItemBank;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType;

public class WyrInventory {

    private static WyrEquipment.WyrAmulet amuletSlot = new WyrEquipment.WyrAmulet();
    private static WyrEquipment.WyrArmor armorSlot = new WyrEquipment.WyrArmor();
    private static WyrEquipment.WyrRing ringSlot = new WyrEquipment.WyrRing();
    private static WyrEquipment.WyrWeapon  weaponSlot = new WyrEquipment.WyrWeapon();
    private static WyrEquipment.WyrBracelet braceletSlot = new WyrEquipment.WyrBracelet();

    protected WyrItem container = null;

    public WyrInventory() {
//        container = ItemBank.Containers.Pocket();
    }

    public void setContainer(WyrItem containerItem) { this.container = containerItem; }

    public void equipBracelet(WyrEquipment.WyrBracelet bracelet) {
        braceletSlot = bracelet;
    }
    public void equipWeapon(WyrEquipment.WyrWeapon weapon) {
        weaponSlot = weapon;
    }
    public void equipAmulet(WyrEquipment.WyrAmulet amulet) {
        amuletSlot = amulet;
    }
    public void equipArmor(WyrEquipment.WyrArmor armor) {
        armorSlot = armor;
    }
    public void equipRing(WyrEquipment.WyrRing ring) {
        ringSlot = ring;
    }

    public WyrItem getContainer() { return container; }

    public WyrEquipment.WyrBracelet getEquippedBracelet() { return braceletSlot; }
    public WyrEquipment.WyrWeapon getEquippedWeapon()   { return weaponSlot;   }
    public WyrEquipment.WyrAmulet getEquippedAmulet()   { return amuletSlot;   }
    public WyrEquipment.WyrArmor getEquippedArmor()    { return armorSlot;    }
    public WyrEquipment.WyrRing getEquippedRing()     { return ringSlot;     }
    public Array<WyrEquipment> getEquippedGear() {
        final Array<WyrEquipment> returnValue = new Array<>();
        returnValue.addAll(amuletSlot, armorSlot, braceletSlot, weaponSlot, ringSlot);
        return returnValue;
    }
    public Array<WyrStatusCondition> getAllGearEffects() {
        final Array<WyrStatusCondition> rV = new Array<>();

        for(WyrEquipment e : getEquippedGear()) {
            rV.addAll(e.getEffects());
        }

        return rV;
    }

    public int combinedGearModifiersValue(StatType stat) {
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
