package com.feiqn.wyrm.logic.handlers;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.Random;

public class CombatHandler {

    private final WYRMGame game;

    public CombatHandler(WYRMGame game) {
        this.game = game;
    }

    public void goToCombat(Unit attacker, Unit defender) {

        boolean continueCombat = true;

        int attackerAccuracy = attacker.getHitRate() - defender.getEvade();
        int defenderAccuracy = defender.getHitRate() - attacker.getEvade();
        if(attackerAccuracy > 100) {attackerAccuracy = 100;} else if(attackerAccuracy < 0) {attackerAccuracy = 0;}
        if(defenderAccuracy > 100) {defenderAccuracy = 100;} else if(defenderAccuracy < 0) {defenderAccuracy = 0;}


        int attackerDamage = attacker.getAttackPower() - defender.getDefensePower();
        int defenderDamage = defender.getAttackPower() - attacker.getDefensePower();
        if(attackerDamage < 0) {attackerDamage = 0;}
        if(defenderDamage < 0) {defenderDamage = 0;}


        if(defender.canMove()) { // Reset attacked unit's highlight to what it was before highlighting attackable
            defender.standardColor();
        } else {
            defender.dimColor();
        }

        int attackerRotations = 1;
        int defenderRotations = 1;
        if(attacker.getAttackSpeed() >= defender.getAttackSpeed() + 4) {
            attackerRotations++;
        } else if (defender.getAttackSpeed() >= attacker.getAttackSpeed() + 4) {
            defenderRotations++;
        }

        int defNewHP1 = defender.getCurrentHP() - attackerDamage;
        int atkNewHP1 = attacker.getCurrentHP() - defenderDamage;

        final Random random = new Random();
        final int atkRoll1 = random.nextInt(100);

        if(atkRoll1 <= attackerAccuracy) {
            if (defNewHP1 > 0) {
                Gdx.app.log("combat", "first rotation");
                defender.setCurrentHP(defNewHP1);

                Gdx.app.log("combat", "" + attacker.name + " deals " + attackerDamage + " to " + defender.name);
                Gdx.app.log("combat", "" + defender.name + " has " + defNewHP1 + " hp remaining");
            } else {
                defender.kill();
                continueCombat = false;
            }
        } else {
            // miss
        }

        // -- defender counter attack
        if(continueCombat) {
            final int defRoll1 = random.nextInt(100);

            if (defRoll1 <= defenderAccuracy) {
                if (atkNewHP1 > 0) {
                    Gdx.app.log("combat", "first rotation counter");
                    attacker.setCurrentHP(atkNewHP1);

                    Gdx.app.log("combat", "" + defender.name + " deals " + defenderDamage + " to " + attacker.name);
                    Gdx.app.log("combat", "" + attacker.name + " has " + atkNewHP1 + " hp remaining");

                } else {
                    attacker.kill();
                    continueCombat = false;
                }
            } else {
                // miss
            }
        }

        // -- double attack if applicable
        if(continueCombat) {
            if (attackerRotations > 1) {

                final int atkRoll2 = random.nextInt(100);

                Gdx.app.log("combat", "second rotation");
                int defNewHP2 = defender.getCurrentHP() - attackerDamage;

                if (atkRoll2 <= attackerAccuracy) {
                    if (defNewHP2 > 0) {
                        Gdx.app.log("combat", "" + attacker.name + " deals " + attackerDamage + " to " + defender.name);
                        Gdx.app.log("combat", "" + defender.name + " has " + defNewHP2 + " hp remaining");

                        defender.setCurrentHP(defNewHP2);
                    } else {
                        defender.kill();
                        continueCombat = false;
                    }
                } else {
                    // miss
                }
            }
        }
        // -- further attacks from skills or effects i.e., brave weapons
    }
}
