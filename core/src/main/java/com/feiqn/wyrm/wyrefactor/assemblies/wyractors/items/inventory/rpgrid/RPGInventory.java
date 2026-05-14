package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory.rpgrid;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.inventory.WyrInventory;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.RPGEquipment;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public final class RPGInventory extends WyrInventory {

    private final Gear gear = new Gear();

    public RPGInventory() {}

    public Gear gear() { return gear; }

    public static class Gear {
        private static RPGEquipment.RPGAmulet   amuletSlot   = new RPGEquipment.RPGAmulet();
        private static RPGEquipment.RPGArmor    armorSlot    = new RPGEquipment.RPGArmor();
        private static RPGEquipment.RPGRing     ringSlot     = new RPGEquipment.RPGRing();
        private static RPGEquipment.RPGWeapon   weaponSlot   = new RPGEquipment.RPGWeapon();
        private static RPGEquipment.RPGBracelet braceletSlot = new RPGEquipment.RPGBracelet();

//    public Array<RPGEquipment<RPGridAnimator, RPGridInteraction, RPGridMetaHandler, RPGridStats
//        >> getEquippedGear() {
//        final Array<WyrEquipment> returnValue = new Array<>();
//        returnValue.addAll(amuletSlot, armorSlot, braceletSlot, weaponSlot, ringSlot);
//        return returnValue;
//    }

        public Gear() {}

        public void equipBracelet(RPGEquipment.RPGBracelet bracelet) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipWeapon(RPGEquipment.RPGWeapon weapon) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipAmulet(RPGEquipment.RPGAmulet amulet) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipArmor(RPGEquipment.RPGArmor armor) {
            Gdx.app.log("TODO", "XD");
        }
        public void equipRing(RPGEquipment.RPGRing ring) {
            Gdx.app.log("TODO", "XD");
        }

        public RPGEquipment.RPGBracelet getEquippedBracelet() { return braceletSlot; }
        public RPGEquipment.RPGWeapon getEquippedWeapon()   { return weaponSlot;   }
        public RPGEquipment.RPGAmulet getEquippedAmulet()   { return amuletSlot;   }
        public RPGEquipment.RPGArmor getEquippedArmor()    { return armorSlot;    }
        public RPGEquipment.RPGRing getEquippedRing()     { return ringSlot;     }


        public int combinedGearModifiersValue(WyRPG.StatType stat) {
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
