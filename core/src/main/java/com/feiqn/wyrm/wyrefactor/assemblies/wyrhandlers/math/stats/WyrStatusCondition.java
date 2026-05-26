package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.*;

public class WyrStatusCondition implements Wyr {

    // One Condition object per effect, holding
    // duration, potency, targets, and other
    // relevant info

    private final StatType effectType;
    private final WyrActor affectedActor; // a? e?
    private WyrActor targetedActor; // i.e., who the affectActor is soul-branded to, etc.

    private int effectCounter = 0;

    public WyrStatusCondition(StatType effectType, WyrActor affectedActor) {
        this.effectType = effectType;
        this.affectedActor = affectedActor;
    }

    public void tickUpEffect()   { effectCounter++; }
    public void tickDownEffect() { effectCounter--; }

    public void setTarget(WyrActor target) { targetedActor = target; }
    public StatType getEffectType()        { return effectType; }
    public WyrActor getAffectedActor()     { return affectedActor; }
    public WyrActor getTargetedActor()     { return targetedActor; }

    public int effectCounter() { return effectCounter; }
}
