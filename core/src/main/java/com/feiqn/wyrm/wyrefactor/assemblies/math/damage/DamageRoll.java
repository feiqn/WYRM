package com.feiqn.wyrm.wyrefactor.assemblies.math.damage;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStatusCondition;

public class DamageRoll {

    private boolean nearMiss;
    private boolean criticalHit;

    private int rawDamage;

    private final Array<WyrStatusCondition> conditionsAppliedOnHit = new Array<>();

    public DamageRoll() {
        nearMiss = false;
        criticalHit = false;
        rawDamage = 0;
    }


    public void setRawDamage(int i) { rawDamage = i; }
    public void setNearMiss() { nearMiss = true; }
    public void setCriticalHit() { criticalHit = true; }
    public void addStatusCondition(WyrStatusCondition effect) { conditionsAppliedOnHit.add(effect); }
    public void addStatusConditions(Array<WyrStatusCondition> effects) { conditionsAppliedOnHit.addAll(effects); }

    public boolean isNearMiss() { return nearMiss; }
    public boolean isCrit() { return criticalHit; }
    public Array<WyrStatusCondition> getStatusConditions() { return conditionsAppliedOnHit; }
    public int getRawDamage() { return rawDamage;}
}

