package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.damage;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.math.stats.rpg.RPGStatusEffect;

public class DamageRoll {

    private boolean nearMiss;
    private boolean criticalHit;

    private int rawDamage;

    private final Array<RPGStatusEffect> statusEffects = new Array<>();


    public DamageRoll() {
        nearMiss = false;
        criticalHit = false;
        rawDamage = 0;
    }

    public void setRawDamage(int i) { rawDamage = i; }
    public void setNearMiss() { nearMiss = true; }
    public void setCriticalHit() { criticalHit = true; }
    public void applyEffect(RPGStatusEffect effect) { statusEffects.add(effect); }

    public boolean isNearMiss() { return nearMiss; }
    public boolean isCrit() { return criticalHit; }
    public Array<RPGStatusEffect> getStatusEffects() { return statusEffects; }
    public int getRawDamage() { return rawDamage;}
}
