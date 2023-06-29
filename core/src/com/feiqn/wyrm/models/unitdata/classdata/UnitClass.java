package com.feiqn.wyrm.models.unitdata.classdata;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponLevel;
import com.feiqn.wyrm.models.itemdata.weapondata.WeaponType;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

import java.util.HashMap;

public class UnitClass {

    private WYRMGame game;

    public UnitClassList classType;

    public MovementType movementType;

    public HashMap<WeaponType, WeaponLevel> weaponTypeProficiencyBonuses;
    public HashMap<StatTypes, Float> growthRateBonuses;

    public String name;

    public UnitClass(WYRMGame game) {
        this.game = game;

        classType = UnitClassList.DRAFTEE;

        movementType = MovementType.INFANTRY;

        name = "Draftee";

        growthRateBonuses = new HashMap<>();
        growthRateBonuses.put(StatTypes.SPEED, 0f);
        growthRateBonuses.put(StatTypes.STRENGTH, 0f);
        growthRateBonuses.put(StatTypes.DEFENSE, 0f);
        growthRateBonuses.put(StatTypes.DEXTERITY, 0f);
        growthRateBonuses.put(StatTypes.HEALTH, 0f);

        // todo: construct weaponTypeProficiencyBonuses and implement

    }
}
