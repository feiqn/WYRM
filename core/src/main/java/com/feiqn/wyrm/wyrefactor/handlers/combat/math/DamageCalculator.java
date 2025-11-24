package com.feiqn.wyrm.wyrefactor.handlers.combat.math;

import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import java.util.Random;

public final class DamageCalculator {

    private static final Random rng = new Random();

    private DamageCalculator() {}

    public static DamageRoll physicalAttackDamage(SimpleUnit attacker, SimpleUnit defender) {
        int attackerDamage = Math.max(attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense(), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect(); TODO: effects here, check attacker's weapon, def armor, etc.

        return roll;
    }

    public static DamageRoll magicAttackDamage(SimpleUnit attacker, SimpleUnit defender) {
        int attackerDamage = Math.max(attacker.modifiedSimpleMagic() - defender.modifiedSimpleResistance(), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll ballistaAttackDamage(SimpleUnit defender) {
        int damage = Math.max(20 - defender.modifiedSimpleDefense(), 0);

        final DamageRoll roll = rollCritOrMiss(damage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll flamerAttackDamage(SimpleUnit defender) {
        int damage = Math.max(20 - defender.modifiedSimpleResistance(), 0);

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
