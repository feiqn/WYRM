package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.equipment;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.models.itemdata.simple.equipment.EquipmentEffect;

public abstract class WyrEquipment {

    // refactor of SimpleEquipment

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

    protected Drawable thumbnail;
    protected String name = "";
    protected Array<EquipmentEffect> effects = new Array<>();
    protected EquipmentType type;

    public int getBonus_Strength() { return bonus_Strength; }
    public int getBonus_Speed() { return bonus_Speed; }
    public int getBonus_Resistance() { return bonus_Resistance; }
    public int getBonus_Magic() { return bonus_Magic; }
    public int getBonus_Health() { return bonus_Health; }
    public int getBonus_Defense() { return bonus_Defense; }
    public String getName() { return name; }
    public Array<EquipmentEffect> getEffects() { return effects;}
    public Drawable getThumbnail() { return thumbnail; }
    public EquipmentType getType() { return type; }
}
