package com.feiqn.wyrm.models.unitdata.units.player;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponLevel;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;
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

        mobility = 10;
        baseStrength = 2;
        baseDefense = 3;
        baseMaxHP = 20;
        baseDexterity = 5;
        baseSpeed = 5;
        constitution = 6;

        growthRates = new HashMap<>();
        growthRates.put(StatTypes.SPEED, 0.75f);
        growthRates.put(StatTypes.STRENGTH, 0.3f);
        growthRates.put(StatTypes.DEFENSE, 0.45f);
        growthRates.put(StatTypes.DEXTERITY, 0.6f);
        growthRates.put(StatTypes.HEALTH, 0.65f);

        weaponProficiencyLevels.put(WeaponType.BOW, WeaponLevel.C);
    }

}
