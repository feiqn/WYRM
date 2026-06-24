package com.feiqn.wyrm.wyrefactor.assemblies.math.damage;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType;

import java.util.Random;

public final class DamageCalculator {

    private static final Random rng = new Random();

    private DamageCalculator() {}

    public static DamageRoll physicalAttackDamage(WyrActor attacker, WyrActor defender) {
        int attackerDamage = Math.max(attacker.stats().getModifiedStatValue(StatType.STRENGTH) - defender.stats().getModifiedStatValue(StatType.DEFENSE), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect(); TODO: effects here, check attacker's weapon, def armor, etc.

        return roll;
    }

    public static DamageRoll magicAttackDamage(WyrActor attacker, WyrActor defender) {
        int attackerDamage = Math.max(attacker.stats().getModifiedStatValue(StatType.MAGIC) - defender.stats().getModifiedStatValue(StatType.RESISTANCE), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll ballistaAttackRoll(WyrActor defender) {
        int damage = Math.max(20 - defender.stats().getModifiedStatValue(StatType.DEFENSE), 0);

        final DamageRoll roll = rollCritOrMiss(damage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll flamerAttackDamage(WyrActor defender) {
        int damage = Math.max(20 - defender.stats().getModifiedStatValue(StatType.RESISTANCE), 0);

        final DamageRoll roll = rollCritOrMiss(damage);

//        roll.applyEffect();

        return roll;
    }

    private static DamageRoll rollCritOrMiss(int rawDamage) {
        final DamageRoll roll = new DamageRoll();

        final int criticalRoll = rng.nextInt(21);
        switch(criticalRoll) {
            case 1:
                roll.setNearMiss();
                rawDamage -= (int) Math.max(1, rawDamage * .3f);
                break;
            case 20:
                roll.setCriticalHit();
                rawDamage += (int) Math.max(1, rawDamage * .3f);
                break;
        }

        if(rawDamage < 0) rawDamage = 0;

        roll.setRawDamage(rawDamage);

        return roll;
    }

}
