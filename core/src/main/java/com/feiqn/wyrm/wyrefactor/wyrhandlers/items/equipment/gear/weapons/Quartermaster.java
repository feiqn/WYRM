package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment.gear.weapons;

public final class Quartermaster {

    // SimpleWeapons...

    private Quartermaster() {}

    public final static class Physical {

        public final static class Swords {

            public static WyrWeapon BronzeSword() {
                return new WyrWeapon(WeaponCategory.PHYS_SWORD_SLASH, WeaponRank.E) {

                };
            }

            public static WyrWeapon IronSword() {
                return new WyrWeapon(WeaponCategory.PHYS_SWORD_SLASH, WeaponRank.D) {

                };
            }

            public static WyrWeapon SteelSword() {
                return new WyrWeapon(WeaponCategory.PHYS_SWORD_SLASH, WeaponRank.C) {

                };
            }


            public static WyrWeapon MithrilSword() {
                return new WyrWeapon(WeaponCategory.PHYS_SWORD_SLASH, WeaponRank.B) {

                };
            }

            public static WyrWeapon AdamantiteSword() {
                return new WyrWeapon(WeaponCategory.PHYS_SWORD_SLASH, WeaponRank.A) {

                };
            }

            public static WyrWeapon RunicSword() {
                return new WyrWeapon(WeaponCategory.PHYS_SWORD_SLASH, WeaponRank.A) {

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
