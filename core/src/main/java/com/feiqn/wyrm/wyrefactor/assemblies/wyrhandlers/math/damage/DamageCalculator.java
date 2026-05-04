package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.damage;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.math.stats.rpg.RPGStatType;

import java.util.Random;

public final class DamageCalculator {

    private static final Random rng = new Random();

    private DamageCalculator() {}

    public static DamageRoll physicalAttackDamage(RPGridActor attacker, RPGridActor defender) {
        int attackerDamage = Math.max(attacker.stats().getModifiedStatValue(RPGStatType.STRENGTH) - defender.stats().getModifiedStatValue(RPGStatType.DEFENSE), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect(); TODO: effects here, check attacker's weapon, def armor, etc.

        return roll;
    }

    public static DamageRoll magicAttackDamage(RPGridActor attacker, RPGridActor defender) {
        int attackerDamage = Math.max(attacker.stats().getModifiedStatValue(RPGStatType.MAGIC) - defender.stats().getModifiedStatValue(RPGStatType.RESISTANCE), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll ballistaAttackRoll(RPGridActor defender) {
        int damage = Math.max(20 - defender.stats().getModifiedStatValue(RPGStatType.DEFENSE), 0);

        final DamageRoll roll = rollCritOrMiss(damage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll flamerAttackDamage(RPGridActor defender) {
        int damage = Math.max(20 - defender.stats().getModifiedStatValue(RPGStatType.RESISTANCE), 0);

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
