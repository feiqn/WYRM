package com.feiqn.wyrm.models.unitdata.classdata;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponType;
import com.feiqn.wyrm.models.unitdata.MovementType;
import com.feiqn.wyrm.models.unitdata.units.StatTypes;

import java.util.HashMap;

public class UnitClass {

    private WYRMGame game;

    protected UnitClassList classType;

    protected MovementType movementType;

    protected HashMap<WeaponType, WeaponRank> weaponTypeProficiencyBonuses;
    protected HashMap<StatTypes, Float> growthRateBonuses;

    protected int bonus_Strength;
    protected int bonus_Defense;
    protected int bonus_Speed;
    protected int bonus_Health;
    protected int bonus_Resistance;
    protected int bonus_Magic;

    protected String name;

    public UnitClass(WYRMGame game) {
        this.game = game;

        classType = UnitClassList.DRAFTEE;

        movementType = MovementType.INFANTRY;

        name = "Draftee";

        growthRateBonuses = new HashMap<>();
        growthRateBonuses.put(StatTypes.SPEED,     0f);
        growthRateBonuses.put(StatTypes.STRENGTH,  0f);
        growthRateBonuses.put(StatTypes.DEFENSE,   0f);
        growthRateBonuses.put(StatTypes.DEXTERITY, 0f);
        growthRateBonuses.put(StatTypes.HEALTH,    0f);

        bonus_Strength   = 0;
        bonus_Defense    = 0;
        bonus_Speed      = 0;
        bonus_Health     = 0;
        bonus_Resistance = 0;
        bonus_Magic      = 0;

        // todo: construct weaponTypeProficiencyBonuses and implement

    }

    public String getName() {
        return name;
    }

    public UnitClassList classType() {
        return classType;
    }
    public int bonusDefense() {
        return bonus_Defense;
    }
    public int bonusHealth() {
        return bonus_Health;
    }
    public int bonusMagic() {
        return bonus_Magic;
    }
    public int bonusResistance() {
        return bonus_Resistance;
    }
    public int bonusSpeed() {
        return bonus_Speed;
    }
    public int bonusStrength() {
        return bonus_Strength;
    }
    public MovementType movementType() {
        return movementType;
    }
    public HashMap<StatTypes, Float> growthRateBonuses() {
        return growthRateBonuses;
    }
    public HashMap<WeaponType, WeaponRank> weaponTypeProficiencyBonuses() {
        return weaponTypeProficiencyBonuses;
    }
}
