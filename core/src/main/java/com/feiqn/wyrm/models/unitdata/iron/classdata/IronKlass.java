package com.feiqn.wyrm.models.unitdata.iron.classdata;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponRank;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.WeaponCategory;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.MovementType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math.stats.StatType;

import java.util.HashMap;

public class IronKlass {

    private WYRMGame game;

    protected UnitClassList classType;

    protected MovementType movementType;

    protected HashMap<WeaponCategory, WeaponRank> weaponTypeProficiencyBonuses;
    protected HashMap<StatType, Float> growthRateBonuses;

    protected int bonus_Strength;
    protected int bonus_Defense;
    protected int bonus_Speed;
    protected int bonus_Health;
    protected int bonus_Resistance;
    protected int bonus_Magic;

    protected String name;

    public IronKlass(WYRMGame game) {
        this.game = game;

        classType = UnitClassList.DRAFTEE;

        movementType = MovementType.INFANTRY;

        name = "Draftee";

        growthRateBonuses = new HashMap<>();
        growthRateBonuses.put(StatType.SPEED,     0f);
        growthRateBonuses.put(StatType.STRENGTH,  0f);
        growthRateBonuses.put(StatType.DEFENSE,   0f);
        growthRateBonuses.put(StatType.DEXTERITY, 0f);
        growthRateBonuses.put(StatType.HEALTH,    0f);

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
    public HashMap<StatType, Float> growthRateBonuses() {
        return growthRateBonuses;
    }
    public HashMap<WeaponCategory, WeaponRank> weaponTypeProficiencyBonuses() {
        return weaponTypeProficiencyBonuses;
    }
}
