package com.feiqn.wyrm.wyrefactor.assemblies.math.stats;

import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.Superiority;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.*;

public class WyrStatusCondition {

    // One Condition object per effect, holding
    // duration, potency, targets, and other
    // relevant info

    private final StatusConditionID effectType;
    private Superiority superiority = Superiority.STANDARD;
    private int effectDuration; // pass in zero or a negative value for perpetual effect
    private boolean isPerpetual = false;
    private WyrActor boundActor = null;
    protected int areaOfEffectRange = 0;

    // TODO: "cleansable by x" list of effects or elements that should
    //  remove this effect. Maybe.

    public WyrStatusCondition(StatusConditionID effectType) { this(effectType, 0); }
    public WyrStatusCondition(StatusConditionID effectType, int counter) {
        this.effectType = effectType;
        this.effectDuration = counter;
    }

    public WyrStatusCondition duration(int turns) { effectDuration = turns; return this; }
    public WyrStatusCondition perpetual() { isPerpetual = true; return this; }
    public WyrStatusCondition aoe(int range) { areaOfEffectRange = range; return this; }

    public void tickUpEffect()   { effectDuration++; }
    public void tickDownEffect() { effectDuration--; }

    public void setBoundActor(WyrActor boundActor) { this.boundActor = boundActor; }
    public void setEffectDuration(int effectDuration) { this.effectDuration = effectDuration; }
    public void setAreaOfEffectRange(int areaOfEffectRange) { this.areaOfEffectRange = areaOfEffectRange; }
    public void setSuperiority(Superiority superiority) { this.superiority = superiority; }

    public int getAreaOfEffectRange() { return areaOfEffectRange; }
    public int getEffectDuration() { return effectDuration; }
    public WyrActor getBoundActor() { return boundActor; }
    public StatusConditionID getEffectType() { return effectType; }
    public Superiority getSuperiority() { return superiority; }
    public boolean isPerpetual() { return isPerpetual; }
    public int getDuration() { return effectDuration; }
}
