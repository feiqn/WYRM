package com.feiqn.wyrm.models.unitdata.units.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponLevel;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;
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

        movementSpeed = 3;
        baseStrength = 3;
        baseDefense = 10;
        baseMaxHP = 35;
        baseDexterity = 3;
        baseSpeed = 2;
        constitution = 8;

        growthRates = new HashMap<>();
        growthRates.put(StatTypes.SPEED, 0.35f);
        growthRates.put(StatTypes.STRENGTH, 0.5f);
        growthRates.put(StatTypes.DEFENSE, 0.75f);
        growthRates.put(StatTypes.DEXTERITY, 0.4f);
        growthRates.put(StatTypes.HEALTH, 0.65f);

        weaponProficiencyLevels.put(WeaponType.SHIELD, WeaponLevel.C);
    }

}
