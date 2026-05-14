package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class WyrStatusCondition implements Wyr {

    // One Condition object per effect, holding
    // duration, potency, targets, and other
    // relevant info

    private final Enum<?> effectType;
    private final WyrActor effectedActor; // maybe one day there will be other effects in overworld or some other battle structure?
    private WyrActor targetedActor; // i.e., who the effectActor is soul-branded to, etc.

    private int effectCounter = 0;

    public WyrStatusCondition(Enum<?> effectType, WyrActor effectedActor) {
        this.effectType = effectType;
        this.effectedActor = effectedActor;
    }

    public void tickUpEffect()   { effectCounter++; }
    public void tickDownEffect() { effectCounter--; }

    public void setTarget(WyrActor target) { targetedActor = target; }
    public Enum<?>  getEffectType()        { return effectType; }
    public WyrActor getEffectedActor()     { return effectedActor; }
    public WyrActor getTargetedActor()     { return targetedActor; }

    public int effectCounter() { return effectCounter; }
}
