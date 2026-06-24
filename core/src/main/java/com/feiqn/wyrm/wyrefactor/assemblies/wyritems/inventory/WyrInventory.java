package com.feiqn.wyrm.wyrefactor.assemblies.wyritems.inventory;


import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.prefab.ItemBank;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType;

public abstract class WyrInventory {

    protected WyrItem hammerSpace = null;

    public WyrItem items() {
        return hammerSpace;
    }

    public WyrInventory() {}

    protected void setup() {}

    public static class UnitInventory extends WyrInventory {

        private final WornGear wornGear = new WornGear();

        public UnitInventory() {
            hammerSpace = ItemBank.Containers.Pocket();
            setup();
        }

        public WornGear equipment() { return wornGear; }

        public static class WornGear {
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

            public WornGear() {}

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

    public static class PropInventory extends WyrInventory{

        protected WyrEquipment.WyrWeapon armament = null; // I.E., a turret, ballista, or a shotgun rigged up to a door.
        protected WyrEquipment.WyrArmor reinforcement = null; // I.E., metal plating to strengthen a door or window.

        public PropInventory() {
            setup();
        }

        public void setArmament(WyrEquipment.WyrWeapon armament) {
            this.armament = armament;
        }

        public void setReinforcement(WyrEquipment.WyrArmor reinforcement) {
            this.reinforcement = reinforcement;
        }

        public WyrEquipment.WyrArmor getReinforcement() {
            return reinforcement;
        }

        public WyrEquipment.WyrWeapon getArmament() {
            return armament;
        }
    }
}
