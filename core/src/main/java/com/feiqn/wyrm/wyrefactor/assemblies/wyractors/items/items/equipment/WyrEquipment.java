package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.WyrItem;

public abstract class WyrEquipment extends WyrItem {

    // refactor of SimpleEquipment
    // things you can wear

    /** Weapons and armor may have bonus 0..10, and an optional effect. <br>
     *  Accessories may only have effects, not bonuses.
     */
    protected final Array<EquipmentEffect> effects = new Array<>();
    protected final Enum<?> equipmentType;

    public WyrEquipment(Enum<?> equipmentType) {
        super();
        this.equipmentType = equipmentType;
    }

    public Array<EquipmentEffect> getEffects() { return effects;}
    public Enum<?> getEquipmentType() { return equipmentType; }
}
