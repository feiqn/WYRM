package com.feiqn.wyrm.wyrefactor.wyrhandlers.items.equipment;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.items.items.WyrItem;

public abstract class WyrEquipment extends WyrItem {

    // refactor of SimpleEquipment
    // things you can wear

    public enum EquipmentType {
        WEAPON,
        ARMOR,
        RING,
        BRACELET,
        AMULET,
    }

    /** Weapons and armor may have bonus 0..10, and an optional effect. <br>
     *  Accessories may only have effects, not bonuses.
     */
    protected int bonus_Strength   = 0;
    protected int bonus_Defense    = 0;
    protected int bonus_Speed      = 0;
    protected int bonus_Health     = 0;
    protected int bonus_Magic      = 0;
    protected int bonus_Resistance = 0;

    protected final Array<EquipmentEffect> effects = new Array<>();
    private final EquipmentType type;

    protected WyrEquipment(EquipmentType type) {
        this.type = type;
    }

    public int getBonus_Strength() { return bonus_Strength; }
    public int getBonus_Speed() { return bonus_Speed; }
    public int getBonus_Resistance() { return bonus_Resistance; }
    public int getBonus_Magic() { return bonus_Magic; }
    public int getBonus_Health() { return bonus_Health; }
    public int getBonus_Defense() { return bonus_Defense; }

    public Array<EquipmentEffect> getEffects() { return effects;}
    public EquipmentType getEquipmentType() { return type; }
}
