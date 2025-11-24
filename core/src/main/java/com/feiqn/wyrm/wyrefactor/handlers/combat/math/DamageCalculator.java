package com.feiqn.wyrm.wyrefactor.handlers.combat.math;

import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import java.util.Random;

public final class DamageCalculator {

    private static final Random rng = new Random();

    private DamageCalculator() {}

    public static DamageRoll physicalAttackDamage(SimpleUnit attacker, SimpleUnit defender) {
        int attackerDamage = attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense();

        // TODO: effects here, check attacker's weapon, def armor, etc.

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect();

        return roll;
    }

    public DamageRoll magicAttackDamage(SimpleUnit attacker, SimpleUnit defender) {
        int attackerDamage = attacker.modifiedSimpleMagic() - defender.modifiedSimpleResistance();

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect();

        return roll;
    }

    public DamageRoll ballistaAttackDamage(SimpleUnit defender) {
        int damage = 20 - defender.modifiedSimpleDefense();

        final DamageRoll roll = rollCritOrMiss(damage);

//        roll.applyEffect();

        return roll;
    }

    public DamageRoll flamerAttackDamage(SimpleUnit defender) {

        int damage = 20 - defender.modifiedSimpleResistance();

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

        roll.setRawDamage(rawDamage);

        return roll;
    }

}
