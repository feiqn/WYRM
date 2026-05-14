package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.damage;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public class DamageRoll {

    private boolean nearMiss;
    private boolean criticalHit;

    private int rawDamage;

    private final Array<WyRPG.StatusEffect> statusEffects = new Array<>();


    public DamageRoll() {
        nearMiss = false;
        criticalHit = false;
        rawDamage = 0;
    }

    public void setRawDamage(int i) { rawDamage = i; }
    public void setNearMiss() { nearMiss = true; }
    public void setCriticalHit() { criticalHit = true; }
    public void applyEffect(WyRPG.StatusEffect effect) { statusEffects.add(effect); }

    public boolean isNearMiss() { return nearMiss; }
    public boolean isCrit() { return criticalHit; }
    public Array<WyRPG.StatusEffect> getStatusEffects() { return statusEffects; }
    public int getRawDamage() { return rawDamage;}
}
