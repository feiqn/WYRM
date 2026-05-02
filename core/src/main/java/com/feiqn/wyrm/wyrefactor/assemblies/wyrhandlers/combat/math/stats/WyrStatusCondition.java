package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrStatusCondition<
        EffectType extends Enum<?>,
        Actor      extends WyrActor<?,?,?,?>
            > implements Wyr {

    // One Condition object per effect, holding
    // duration, potency, targets, and other
    // relevant info

    private final EffectType effectType;
    private final Actor effectedActor; // maybe one day there will be other effects in overworld or some other battle structure?
    private Actor targetedActor; // i.e., who the effectActor is soul-branded to, etc.

    private int effectCounter = 0;


    public WyrStatusCondition(EffectType effectType, Actor effectedActor) {
        this.effectType = effectType;
        this.effectedActor = effectedActor;
    }

    public void tickUpEffect()   { effectCounter++; }
    public void tickDownEffect() { effectCounter--; }

    public void setTarget(Actor target) { targetedActor = target; }
    public EffectType getEffectType()   { return effectType; }
    public Actor getEffectedActor()     { return effectedActor; }
    public Actor getTargetedActor()     { return targetedActor; }

    public int effectCounter() { return effectCounter; }
}
