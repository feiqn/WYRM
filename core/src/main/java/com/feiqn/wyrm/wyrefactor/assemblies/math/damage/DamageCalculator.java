package com.feiqn.wyrm.wyrefactor.assemblies.math.damage;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.math.stats.WyrStatusCondition;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyritems.WyrEquipment.WyrWeapon;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.StatType;

import java.util.Random;

public final class DamageCalculator {

    private static final Random rng = new Random();

    private DamageCalculator() {}

    public static DamageRoll armamentAttack(WyrWeapon armament, WyrActor defender) {

        DamageRoll roll = null;

        switch(armament.getDamageType()) {
            case PHYS_BLUNT:
            case PHYS_CUT:
            case PHYS_STAB:
            default: // temporary default catch
                roll = rollCritOrMiss(physicalDamage(
                    armament.getStatBonus(StatType.STRENGTH),
                    defender.stats().getNetValue(StatType.DEFENSE),
                    piercingValue(armament.getEffects()))
                );
                break;
        }

        roll.addStatusConditions(armament.getEffects());

        return roll;
    }

    public static DamageRoll unitAttack(WyrActor.Unit attacker, WyrActor defender) {

        DamageRoll roll = null;

        switch (attacker.getInventory().getEquippedWeapon().getDamageType()) {
            case PHYS_BLUNT:
            case PHYS_CUT:
            case PHYS_STAB:
            default: // temp
                roll = rollCritOrMiss(physicalDamage(
                    attacker.stats().getNetValue(StatType.STRENGTH),
                    defender.stats().getNetValue(StatType.DEFENSE),
                    piercingValue(attacker.getInventory().getEquippedWeapon().getEffects())
                ));
        }

//        roll.addStatusConditions(attacker.getInventory().getAllGearEffects().getAllEffects());

        return roll;

    }

    public static DamageRoll physicalAttackRoll(WyrActor attacker, WyrActor defender) {
        int attackerDamage = Math.max(attacker.stats().getNetValue(StatType.STRENGTH) - defender.stats().getNetValue(StatType.DEFENSE), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect(); TODO: effects here, check attacker's weapon, def armor, etc.

        return roll;
    }

    public static DamageRoll magicAttackRoll(WyrActor attacker, WyrActor defender) {
        int attackerDamage = Math.max(attacker.stats().getNetValue(StatType.MAGIC) - defender.stats().getNetValue(StatType.RESISTANCE), 0);

        final DamageRoll roll = rollCritOrMiss(attackerDamage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll ballistaFlatDmg(WyrActor defender) {
        int damage = Math.max(20 - defender.stats().getNetValue(StatType.DEFENSE), 0);

        final DamageRoll roll = rollCritOrMiss(damage);

//        roll.applyEffect();

        return roll;
    }

    public static DamageRoll flamerFlatDmg(WyrActor defender) {
        int damage = Math.max(20 - defender.stats().getNetValue(StatType.RESISTANCE), 0);

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

    /**
     * Pass in 0 for no piercing, 1 for full piercing, etc.
     * @param piercing
     */
    private static int physicalDamage(WyrActor attacker, WyrActor defender, float piercing) {
        return physicalDamage(attacker.stats().getNetValue(StatType.STRENGTH), defender.stats().getNetValue(StatType.DEFENSE), piercing);
    }

    private static int physicalDamage(int strengthValue, int defenseValue, float piercing) {
        final float effectiveDefense = defenseValue * Math.abs(piercing - 1);
        return (int) Math.max(strengthValue - effectiveDefense, 0);
    }

    private static int magicalDamage(WyrActor attacker, WyrActor defender, float piercing) {
        final float effectiveResistance = defender.stats().getNetValue(StatType.RESISTANCE) * Math.abs(piercing - 1);
        return (int) Math.max(attacker.stats().getNetValue(StatType.MAGIC) - effectiveResistance, 0);
    }

    private static float piercingValue(Array<WyrStatusCondition> equipmentEffects) {
        float p = 0;

        for(WyrStatusCondition e : equipmentEffects) {
            switch(e.getEffectType()) {
                case PIERCE_DEFENSE_HALF_ON_HIT:
                    p += .5f;
                    break;
                case PIERCE_DEFENSE_FULL_ON_HIT:
                    p += 1;
                    break;
            }
        }

        return Math.min(p, 1);
    }

}
