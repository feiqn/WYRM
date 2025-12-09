package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math;

import com.badlogic.gdx.utils.Array;

public class DamageRoll {

    private boolean nearMiss;
    private boolean criticalHit;

    private int rawDamage;

    private Array<StatusEffect> statusEffects;


    public DamageRoll() {
        nearMiss = false;
        criticalHit = false;
        rawDamage = 0;
        statusEffects = new Array<>();
    }

    public void setRawDamage(int i) { rawDamage = i; }
    public void setNearMiss() { nearMiss = true; }
    public void setCriticalHit() { criticalHit = true; }
    public void applyEffect(StatusEffect effect) { statusEffects.add(effect); }

    public boolean isNearMiss() { return nearMiss; }
    public boolean isCrit() { return criticalHit; }
    public Array<StatusEffect> getStatusEffects() { return statusEffects; }
    public int getRawDamage() { return rawDamage;}
}
