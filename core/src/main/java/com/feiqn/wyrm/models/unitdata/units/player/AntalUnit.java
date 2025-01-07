package com.feiqn.wyrm.models.unitdata.units.player;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.classdata.PrefabClasses.ShieldKnightClass;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

import java.util.HashMap;

public class AntalUnit extends Unit {

    public AntalUnit(WYRMGame game) {
        super(game, game.assetHandler.armorKnightTexture);

        name = "Antal";
        unitClass = new ShieldKnightClass(game);
        teamAlignment = TeamAlignment.PLAYER;
        rosterID = UnitRoster.ANTAL;

        iron_mobility = 3;
        iron_baseStrength = 3;
        iron_baseDefense = 10;
        iron_baseMaxHP = 35;
        iron_baseDexterity = 3;
        iron_baseSpeed = 2;
        iron_constitution = 8;

        growthRates = new HashMap<>();
        growthRates.put(StatTypes.SPEED, 0.35f);
        growthRates.put(StatTypes.STRENGTH, 0.5f);
        growthRates.put(StatTypes.DEFENSE, 0.75f);
        growthRates.put(StatTypes.DEXTERITY, 0.4f);
        growthRates.put(StatTypes.HEALTH, 0.65f);

        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponRank.C);
    }

}
