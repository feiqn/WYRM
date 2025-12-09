package com.feiqn.wyrm.models.unitdata.units.ally.recruitable;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.klass.prefabklasses.unique.ShieldKnightKlass;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.UnitRoster;

public class AntalUnitOLD extends OLD_SimpleUnit {

    public AntalUnitOLD(WYRMGame game) {
        super(game, game.assetHandler.armorKnightTexture);

        characterName = "Antal";
        teamAlignment = TeamAlignment.PLAYER;
        rosterID = UnitRoster.ANTAL;

        simpleKlass = new ShieldKnightKlass();

        simple_Speed = 4;
        simple_Defense = 2;
        simple_Health = 4;
        rollingHP = modifiedSimpleHealth();

        ability = Abilities.SHOVE;

//        ironKlass = new ShieldKnightClass(game);

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
