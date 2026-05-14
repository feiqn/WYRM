package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.gear;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment.rpg.RPGEquipment;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.StatType.STRENGTH;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG.WeaponCategory.*;

public final class Quartermaster {

    // SimpleWeapons...

    // TODO:
    //  eventually we can abstract equipment more, probably

    private Quartermaster() {}

    public static final class Physical {

        public static final class Swords {

            public static RPGEquipment.RPGWeapon BronzeSword() {
                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.E) {
                    @Override
                    protected void setup() {
                        stats.setStatValue(STRENGTH, 1);
                    }

                    @Override
                    public String getName() {
                        return "Bronze Sword";
                    }

                    @Override
                    public String getExamine() {
                        return "Cheap, flimsy metal; yet still sharp enough to puncture vital organs.";
                    }
                };
            }

            public RPGEquipment.RPGWeapon IronSword() {
                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.D) {

                };
            }

            public RPGEquipment.RPGWeapon SteelSword() {
                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.C) {

                };
            }


            public RPGEquipment.RPGWeapon MithrilSword() {
                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.B) {

                };
            }

            public RPGEquipment.RPGWeapon AdamantiteSword() {
                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.A) {

                };
            }

            public RPGEquipment.RPGWeapon RunicSword() {
                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.A) {

                };
            }

        }

    }

    public final static class Magical {

    }

    public final static class Herbal {

    }

    public final static class Explosive {

    }

    // TODO: etc...

}
