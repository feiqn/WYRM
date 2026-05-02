package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.equipment;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.items.items.WyrItem;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public abstract class WyrEquipment<
        Animation     extends WyrAnimator<?,?,?>,
        EquipmentType extends Enum<?>,
        Interaction   extends WyrInteraction<?,?>,
        MetaHandle    extends MetaHandler<?,?,?,?,?,?,?,?,?,?>,
        Stats         extends WyrStats<?,?,?,?,?,?>
            > extends WyrItem<Animation, Interaction, MetaHandle, Stats> {

    // refactor of SimpleEquipment
    // things you can wear

    /** Weapons and armor may have bonus 0..10, and an optional effect. <br>
     *  Accessories may only have effects, not bonuses.
     */
    protected final Array<EquipmentEffect> effects = new Array<>();
    private final EquipmentType type;

    public WyrEquipment(EquipmentType type) {
        super();
        this.type = type;
    }

    public Array<EquipmentEffect> getEffects() { return effects;}
    public EquipmentType getEquipmentType() { return type; }
}
