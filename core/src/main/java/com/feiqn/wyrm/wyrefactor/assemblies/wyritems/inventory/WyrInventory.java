package com.feiqn.wyrm.wyrefactor.assemblies.wyritems.inventory;


import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.ItemBank;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.equipment.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.StatType;

public class WyrInventory {

    protected WyrItem hammerSpace = ItemBank.Containers.Pocket();

    public WyrItem items() {
        return hammerSpace;
    }

    private final Equipment equipment = new Equipment();

    public WyrInventory() {}

    public Equipment equipment() { return equipment; }

    public static class Equipment {
        private static WyrEquipment.WyrAmulet amuletSlot = new WyrEquipment.WyrAmulet();
        private static WyrEquipment.WyrArmor armorSlot = new WyrEquipment.WyrArmor();
        private static WyrEquipment.WyrRing ringSlot = new WyrEquipment.WyrRing();
        private static WyrEquipment.WyrWeapon  weaponSlot = new WyrEquipment.WyrWeapon();
        private static WyrEquipment.WyrBracelet braceletSlot = new WyrEquipment.WyrBracelet();

        public Array<WyrEquipment> getEquippedGear() {
            final Array<WyrEquipment> returnValue = new Array<>();
            returnValue.addAll(amuletSlot, armorSlot, braceletSlot, weaponSlot, ringSlot);
            return returnValue;
        }

        public Equipment() {}

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

        public WyrEquipment.WyrBracelet getEquippedBracelet() { return braceletSlot; }
        public WyrEquipment.WyrWeapon getEquippedWeapon()   { return weaponSlot;   }
        public WyrEquipment.WyrAmulet getEquippedAmulet()   { return amuletSlot;   }
        public WyrEquipment.WyrArmor getEquippedArmor()    { return armorSlot;    }
        public WyrEquipment.WyrRing getEquippedRing()     { return ringSlot;     }

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
}
