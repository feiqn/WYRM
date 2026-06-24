package com.feiqn.wyrm.wyrefactor.assemblies.math.stats;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.*;

public class WyrStatusCondition extends Subjectivity implements WyrFrame {

    // One Condition object per effect, holding
    // duration, potency, targets, and other
    // relevant info

    private final StatusCondition effectType;

    private Utilities.Superiority superiority = Utilities.Superiority.STANDARD;

    private int effectCounter = 0;

    public WyrStatusCondition(StatusCondition effectType, WyrActor affectedActor) {
        this(effectType, affectedActor, 0);
    }

    public WyrStatusCondition(StatusCondition effectType, WyrActor affectedActor, int counter) {
        this(effectType, affectedActor, counter, null);
    }

    public WyrStatusCondition(StatusCondition effectType, WyrActor affectedActor, int counter, WyrActor inflictingActor) {
        this.effectType = effectType;
        this.effectCounter = counter;
        setSubject(affectedActor);
        setObject(inflictingActor);
    }

    public void tickUpEffect()   { effectCounter++; }
    public void tickDownEffect() { effectCounter--; }

    public StatusCondition getEffectType() { return effectType; }

    public Utilities.Superiority getSuperiority() { return superiority; }

    public void setSuperiority(Utilities.Superiority superiority) { this.superiority = superiority; }

    public int effectCounter() { return effectCounter; }
}
