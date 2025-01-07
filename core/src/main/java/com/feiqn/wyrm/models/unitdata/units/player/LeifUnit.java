package com.feiqn.wyrm.models.unitdata.units.player;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponType;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.classdata.PrefabClasses.PlaneswalkerClass;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

import java.util.HashMap;

public class LeifUnit extends Unit {

    public LeifUnit(WYRMGame game) {
        super(game, game.assetHandler.pegKnightTexture);

        name = "Leif";
        unitClass = new PlaneswalkerClass(game);
        teamAlignment = TeamAlignment.PLAYER;
        rosterID = UnitRoster.LEIF;

        iron_mobility = 10;
        iron_baseStrength = 2;
        iron_baseDefense = 3;
        iron_baseMaxHP = 20;
        iron_baseDexterity = 5;
        iron_baseSpeed = 5;
        iron_constitution = 6;

        growthRates = new HashMap<>();
        growthRates.put(StatTypes.SPEED, 0.75f);
        growthRates.put(StatTypes.STRENGTH, 0.3f);
        growthRates.put(StatTypes.DEFENSE, 0.45f);
        growthRates.put(StatTypes.DEXTERITY, 0.6f);
        growthRates.put(StatTypes.HEALTH, 0.65f);

        weaponProficiencyLevels.put(WeaponType.BOW, WeaponRank.C);
    }

}
