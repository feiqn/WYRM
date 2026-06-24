package com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.prefab;


import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.WyrEquipment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.items.WyrEquipment.WyrWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.Equipment.WeaponRank;

public final class Quartermaster {

    // SimpleWeapons...

    // TODO:
    //  eventually we can abstract equipment more, probably

    private Quartermaster() {}

    public static final class PhysicalWeapons {

        public static final class Swords {

//            public static RPGEquipment.RPGWeapon BronzeSword() {
//                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.E) {
//                    @Override
//                    protected void setup() {
//                        stats.setStatValue(STRENGTH, 1);
//                    }
//
//                    @Override
//                    public String getName() {
//                        return "Bronze Sword";
//                    }
//
//                    @Override
//                    public String getExamine() {
//                        return "Cheap, flimsy metal; yet still sharp enough to puncture vital organs.";
//                    }
//                };
//            }
//
//            public RPGEquipment.RPGWeapon IronSword() {
//                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.D) {
//
//                };
//            }
//
//            public RPGEquipment.RPGWeapon SteelSword() {
//                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.C) {
//
//                };
//            }
//
//
//            public RPGEquipment.RPGWeapon MithrilSword() {
//                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.B) {
//
//                };
//            }
//
//            public RPGEquipment.RPGWeapon AdamantiteSword() {
//                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.A) {
//
//                };
//            }
//
//            public RPGEquipment.RPGWeapon RunicSword() {
//                return new RPGEquipment.RPGWeapon(PHYS_SWORD_SLASH, WyRPG.WeaponRank.A) {
//
//                };
//            }

        }

    }

    public final static class MagicalWeapons {

    }

    public final static class HerbalWeapons {

    }

    public final static class ExplosiveWeapons {

    }

    public final static class PropWeapons {

        public static WyrWeapon HeavyBallista() {
            return new WyrWeapon() {

                @Override
                protected void setup() {
                    weaponCategory = WeaponCategory.PHYS_BOW_STAB;
                    weaponRank = WeaponRank.A;
                    setName("Heavy Ballista");
                }

                @Override
                public String getExamine() {
                    return "A stationary siege weapon. \n Firing one of these can't be much different from hunting with a longbow... right?";
                }

            };
        }

    }

    // TODO: etc...

}
