package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public class WyrStatusCondition {

    // One Condition object per effect, holding
    // duration, potency, targets, and other
    // relevant info

    public enum EffectType {
        BURN,
        POISON,
        STUN,
        CHILL,
        SOUL_BRAND,
        PETRIFY,
    }

    private final EffectType effectType;
    private final WyrActor effectedActor; // maybe one day there will be other effects in overworld or some other battle structure?
    private WyrActor targetedActor; // i.e., who the effectActor is soul-branded to, etc.

    private int effectCounter = 0;


    public WyrStatusCondition(EffectType effectType, WyrActor effectedActor) {
        this.effectType = effectType;
        this.effectedActor = effectedActor;
    }

    public void tickUpEffect() { effectCounter++; }
    public void tickDownEffect() { effectCounter--; }

    public void setTarget(WyrActor target) { targetedActor = target; }
    public EffectType getEffectType() { return effectType; }
    public WyrActor getEffectedActor() { return effectedActor; }
    public WyrActor getTargetedActor() { return targetedActor; }

    public int getEffectCounter() { return effectCounter; }
}
