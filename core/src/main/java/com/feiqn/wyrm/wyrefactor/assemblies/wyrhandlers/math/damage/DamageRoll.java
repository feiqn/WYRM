package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.damage;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.StatusEffect;

public class DamageRoll {

    private boolean nearMiss;
    private boolean criticalHit;

    private int rawDamage;

    private final Array<StatusEffect> statusEffects = new Array<>();


    public DamageRoll() {
        nearMiss = false;
        criticalHit = false;
        rawDamage = 0;
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
