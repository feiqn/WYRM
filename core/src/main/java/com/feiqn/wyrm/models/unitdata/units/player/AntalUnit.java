package com.feiqn.wyrm.models.unitdata.units.player;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.SimpleUnit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class AntalUnit extends SimpleUnit {

    public AntalUnit(WYRMGame game) {
        super(game, game.assetHandler.armorKnightTexture);

        name = "Antal";
//        ironKlass = new ShieldKnightClass(game);
        teamAlignment = TeamAlignment.PLAYER;
        rosterID = UnitRoster.ANTAL;

//        iron_mobility = 3;
//        iron_baseStrength = 3;
//        iron_baseDefense = 10;
//        iron_baseMaxHP = 35;
//        iron_baseDexterity = 3;
//        iron_baseSpeed = 2;
//        iron_constitution = 8;
//
//        ironGrowthRates = new HashMap<>();
//        ironGrowthRates.put(StatTypes.SPEED, 0.35f);
//        ironGrowthRates.put(StatTypes.STRENGTH, 0.5f);
//        ironGrowthRates.put(StatTypes.DEFENSE, 0.75f);
//        ironGrowthRates.put(StatTypes.DEXTERITY, 0.4f);
//        ironGrowthRates.put(StatTypes.HEALTH, 0.65f);
//
//        ironWeaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.C);
    }

}
