package com.feiqn.wyrm.logic.handlers.combat;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.Unit;

import java.util.Random;

public class CombatHandler {
    // Handled by BattleScreen
    // Logical backend functions for combat on a battle screen

    private final WYRMGame game;

    public CombatHandler(WYRMGame game) {
        this.game = game;
    }

    public void iron_goToCombat(Unit attacker, Unit defender) {
//
//        boolean continueCombat = true;
//
//        int attackerAccuracy = attacker.iron_getHitRate() - defender.iron_getEvade();
//        int defenderAccuracy = defender.iron_getHitRate() - attacker.iron_getEvade();
//        if(attackerAccuracy > 100) {attackerAccuracy = 100;} else if(attackerAccuracy < 0) {attackerAccuracy = 0;}
//        if(defenderAccuracy > 100) {defenderAccuracy = 100;} else if(defenderAccuracy < 0) {defenderAccuracy = 0;}
//
//
//        int attackerDamage = attacker.getAttackPower() - defender.getDefensePower();
//        int defenderDamage = defender.getAttackPower() - attacker.getDefensePower();
//        if(attackerDamage < 0) {attackerDamage = 0;}
//        if(defenderDamage < 0) {defenderDamage = 0;}
//
//        int playerDamageDealt = 0;
//        boolean enemyWasKilled = false;
//
//        // todo: class relative power 1-10
//
//        // todo: other exps from staffs, non-combat actions such as stealing and breaking terrain, etc
//
////        if(defender.canMove()) { // Reset attacked unit's highlight to what it was before highlighting attackable
////            defender.standardColor();
////        } else {
////            defender.dimColor();
////        }
//
//        int attackerRotations = 1;
//        int defenderRotations = 1;
//        if(attacker.iron_getAttackSpeed() >= defender.iron_getAttackSpeed() + 4) {
//            attackerRotations++;
//        } else if (defender.iron_getAttackSpeed() >= attacker.iron_getAttackSpeed() + 4) {
//            defenderRotations++;
//        }
//
//        int defNewHP1 = defender.getRollingHP() - attackerDamage;
//        int atkNewHP1 = attacker.getRollingHP() - defenderDamage;
//
//        final Random random = new Random();
//        final int atkRoll1 = random.nextInt(100);
//
//        if(atkRoll1 <= attackerAccuracy) {
//            if (defNewHP1 > 0) {
//                Gdx.app.log("combat", "first rotation");
//                defender.setRollingHP(defNewHP1);
//
//                if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
//                    playerDamageDealt += attackerDamage;
//                }
//
//                Gdx.app.log("combat", "" + attacker.name + " deals " + attackerDamage + " to " + defender.name);
//                Gdx.app.log("combat", "" + defender.name + " has " + defNewHP1 + " hp remaining");
//            } else {
//                defender.kill();
//                if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
//                    enemyWasKilled = true;
//                }
//                continueCombat = false;
//            }
//        } else {
//            // miss
//        }
//
//        // -- defender counter attack
//        if(continueCombat) {
//            final int defRoll1 = random.nextInt(100);
//
//            if (defRoll1 <= defenderAccuracy) {
//                if (atkNewHP1 > 0) {
//                    Gdx.app.log("combat", "first rotation counter");
//                    attacker.setRollingHP(atkNewHP1);
//
//                    if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
//                        playerDamageDealt += defenderDamage;
//                    }
//
//                    Gdx.app.log("combat", "" + defender.name + " deals " + defenderDamage + " to " + attacker.name);
//                    Gdx.app.log("combat", "" + attacker.name + " has " + atkNewHP1 + " hp remaining");
//
//                } else {
//                    attacker.kill();
//                    if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
//                        enemyWasKilled = true;
//                    }
//                    continueCombat = false;
//                }
//            } else {
//                // miss
//            }
//        }
//
//        // -- double attack if applicable
//        if(continueCombat) {
//            if (attackerRotations > 1) {
//
//                final int atkRoll2 = random.nextInt(100);
//
//                Gdx.app.log("combat", "second rotation");
//                int defNewHP2 = defender.getRollingHP() - attackerDamage;
//
//                if (atkRoll2 <= attackerAccuracy) {
//                    if (defNewHP2 > 0) {
//                        Gdx.app.log("combat", "" + attacker.name + " deals " + attackerDamage + " to " + defender.name);
//                        Gdx.app.log("combat", "" + defender.name + " has " + defNewHP2 + " hp remaining");
//
//                        defender.setRollingHP(defNewHP2);
//
//                        if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
//                            playerDamageDealt += attackerDamage;
//                        }
//
//                    } else {
//                        defender.kill();
//                        if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
//                            enemyWasKilled = true;
//                        }
//                        continueCombat = false;
//                    }
//                } else {
//                    // miss
//                }
//            }
//        }
//
//        if(continueCombat) {
//            if (defenderRotations > 1) {
//
//                final int defRoll2 = random.nextInt(100);
//
//                Gdx.app.log("combat", "second rotation");
//                int atkNewHP2 = attacker.getRollingHP() - defenderDamage;
//
//                if (defRoll2 <= defenderAccuracy) {
//                    if (atkNewHP2 > 0) {
//                        Gdx.app.log("combat", "" + defender.name + " deals " + defenderDamage + " to " + attacker.name);
//                        Gdx.app.log("combat", "" + attacker.name + " has " + atkNewHP2 + " hp remaining");
//
//                        attacker.setRollingHP(atkNewHP2);
//
//                        if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
//                            playerDamageDealt += defenderDamage;
//                        }
//
//                    } else {
//                        attacker.kill();
//                        if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
//                            enemyWasKilled = true;
//                        }
//                        continueCombat = false;
//                    }
//                } else {
//                    // miss
//                }
//            }
//        }
//
//
//        // -- further attacks from skills or effects i.e., brave weapons
//
//        // -- EXP
//        if(enemyWasKilled){
//            int bossBonus = 0;
//
//            if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
//                if(defender.isABoss()) bossBonus = 20;
//                final int lvDiff = defender.getLevel() - attacker.getLevel();
//
//                if(lvDiff >= 0) {
//                    attacker.iron_addExp(((31 - lvDiff) / 3) + 20 + (lvDiff * 3) + bossBonus);
//                } else if(lvDiff == -1) {
//                    attacker.iron_addExp(10 + 20 + bossBonus);
//                } else {
//                    attacker.iron_addExp(17 + bossBonus);
//                }
//
//
//            } else if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
//                if(attacker.isABoss()) bossBonus = 20;
//                final int lvDiff = attacker.getLevel() - defender.getLevel();
//
//                if(lvDiff >= 0) {
//                    defender.iron_addExp(((31 - lvDiff) / 3) + 20 + (lvDiff * 3) + bossBonus);
//                } else if(lvDiff == -1) {
//                    defender.iron_addExp(10 + 20 + bossBonus);
//                } else {
//                    defender.iron_addExp(17 + bossBonus);
//                }
//
//            }
//
//        } else if(playerDamageDealt > 0) {
//            if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
//                final int lvDiff = defender.getLevel() - attacker.getLevel();
//
//                if(lvDiff >= 0) {
//                    attacker.iron_addExp((31 - lvDiff) / 3);
//                } else if(lvDiff == -1) {
//                    attacker.iron_addExp(10);
//                } else {
//                    attacker.iron_addExp(1);
//                }
//
//            } else if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
//                final int lvDiff = attacker.getLevel() - defender.getLevel();
//
//                if(lvDiff >= 0) {
//                    defender.iron_addExp((31 - lvDiff) / 3);
//                } else if(lvDiff == -1) {
//                    defender.iron_addExp(10);
//                } else {
//                    defender.iron_addExp(1);
//                }
//
//            }
//        } else {
//            if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
//                attacker.iron_addExp(1);
//            } else if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
//                defender.iron_addExp(1);
//            }
//        }
    }
}
