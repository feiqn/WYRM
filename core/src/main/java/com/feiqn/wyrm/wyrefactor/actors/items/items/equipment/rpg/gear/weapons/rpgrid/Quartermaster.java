package com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.rpgrid;

import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponRank;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.RPGStatType;

import static com.feiqn.wyrm.wyrefactor.actors.items.items.equipment.rpg.gear.weapons.WeaponCategory.*;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.rpg.RPGStatType.*;

public final class Quartermaster {

    // SimpleWeapons...

    // TODO:
    //  eventually we can abstract equipment more, probably

    private Quartermaster() {}

    public static final class Physical {

        public static final class Swords {

            public static RPGridWeapon BronzeSword() {
                return new RPGridWeapon(PHYS_SWORD_SLASH, WeaponRank.E) {
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

            public RPGridWeapon IronSword() {
                return new RPGridWeapon(PHYS_SWORD_SLASH, WeaponRank.D) {

                };
            }

            public RPGridWeapon SteelSword() {
                return new RPGridWeapon(PHYS_SWORD_SLASH, WeaponRank.C) {

                };
            }


            public RPGridWeapon MithrilSword() {
                return new RPGridWeapon(PHYS_SWORD_SLASH, WeaponRank.B) {

                };
            }

            public RPGridWeapon AdamantiteSword() {
                return new RPGridWeapon(PHYS_SWORD_SLASH, WeaponRank.A) {

                };
            }

            public RPGridWeapon RunicSword() {
                return new RPGridWeapon(PHYS_SWORD_SLASH, WeaponRank.A) {

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
