package com.feiqn.wyrm.logic.handlers.gameplay.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.triggers.types.CombatTrigger;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnit;

import java.util.Random;

public class CombatHandler {
    // Handled by ConditionsHandler
    // Logical backend functions for combat on a grid screen

    private final WYRMGame game;

    private final Random rng = new Random();

    private IronMode ironMode;

    private final Abilities abilities = new Abilities();

    private boolean visualizing;
    private boolean criticalHit;
    private boolean nearMiss;

    public CombatHandler(WYRMGame game) {
        this.game = game;
        visualizing = false;
        criticalHit = false;
        nearMiss = false;
    }

    public void simpleVisualCombat(SimpleUnit attacker, SimpleUnit defender) {
        if(!visualizing) {
            visualizing = true;

            final Runnable finish = new Runnable() {
                @Override
                public void run() {
                    attacker.setCannotMove();

                    game.activeGridScreen.conditions().conversations().checkCombatTriggers(attacker.rosterID, CombatTrigger.When.AFTER);
                    game.activeGridScreen.conditions().conversations().checkCombatTriggers(defender.rosterID, CombatTrigger.When.AFTER);

                    game.activeGridScreen.checkLineOrder();
                    visualizing = false;
                }
            };

            final int rotations = (attacker.modifiedSimpleSpeed() >= defender.modifiedSimpleSpeed() * 2 ? 2 : 1);

            switch (rotations) {
                case 2: // TODO: refactor this. better logic = declare sequence action, add common actions, if() to add the extra rotation in the middle
                    attacker.addAction(Actions.sequence(
//                        Actions.run(new Runnable() {
//                            @Override
//                            public void run() {
//                                game.activeGridScreen.conditions().conversations().checkCombatTriggers(attacker.rosterID, CombatTrigger.When.BEFORE);
//                                game.activeGridScreen.conditions().conversations().checkCombatTriggers(defender.rosterID, CombatTrigger.When.BEFORE);
//                            }
//                        }),
                        visualCombatSequence(attacker, defender),
                        visualCombatSequence(attacker, defender),
                        Actions.run(finish)
                    ));
                    break;
                case 1:
                    attacker.addAction(Actions.sequence(
//                        Actions.run(new Runnable() {
//                            @Override
//                            public void run() {
//                                game.activeGridScreen.conditions().conversations().checkCombatTriggers(attacker.rosterID, CombatTrigger.When.BEFORE);
//                                game.activeGridScreen.conditions().conversations().checkCombatTriggers(defender.rosterID, CombatTrigger.When.BEFORE);
//                            }
//                        }),
                        visualCombatSequence(attacker, defender),
                        Actions.run(finish)
                    ));
                    break;
            }

        } else {
            Gdx.app.log("lag?", "called while visualizing");
        }
    }

    private SequenceAction visualCombatSequence(SimpleUnit attacker, SimpleUnit defender) {
        // build a sequence action for the attacking unit
        // animate move towards enemy -> add on screen damage indicator with timer action to remove self
        // -> apply damage to defender -> animate move back into position

        // calculate damage
        // TODO: account for herbal, vehicle, etc damage types

        final int dmg;

        if(attacker.getOccupyingMapObject() != null) {
            switch(attacker.getOccupyingMapObject().objectType) {
                case BALLISTA:
                    dmg = ballistaAttack(defender);
                    break;
                case FLAMETHROWER:
                    dmg = flamerAttack(defender);
                    break;
                default:
                    dmg = Math.max(attacker.simpleWeapon().getDamageType() == SimpleWeapon.DamageType.PHYSICAL ? physicalAttack(attacker, defender) : magicAttack(attacker, defender), 0);;
                    break;
            }
        } else {
            dmg = Math.max(attacker.simpleWeapon().getDamageType() == SimpleWeapon.DamageType.PHYSICAL ? physicalAttack(attacker, defender) : magicAttack(attacker, defender), 0);;
        }

        final Label damageLabel = new Label("" + dmg, game.assetHandler.menuLabelStyle);
        damageLabel.setFontScale(3);
        if (nearMiss) {
            damageLabel.setColor(Color.PURPLE);
            damageLabel.setText("Near Miss! " + dmg);
        } else if (criticalHit) {
            damageLabel.setColor(Color.GOLD);
            damageLabel.setText("Critical Hit! " + dmg);
        }

        // figure out which direction the baddie is in
        float x;
        float y;

        if (attacker.getColumnX() == defender.getColumnX()) {
            x = attacker.getX();
        } else if (attacker.getColumnX() > defender.getColumnX()) {
            //attacker is to the right of defender, swing left
            x = attacker.getX() - .5f;
        } else {
            x = attacker.getX() + .5f;
        }

        if (attacker.getRowY() == defender.getRowY()) {
            y = attacker.getY();
        } else if (attacker.getRowY() > defender.getRowY()) {
            y = attacker.getY() - .5f;
        } else {
            y = attacker.getY() + .5f;
        }

        return (Actions.sequence(
            Actions.moveTo(x, y, .125f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.activeGridScreen.hudStage.addActor(damageLabel);
                    damageLabel.setPosition(Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .6f);
                    defender.applyDamage(dmg);

                    // apply affects here?

                    damageLabel.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 3),
                            Actions.fadeOut(3)
                        ),
                        Actions.removeActor()
                    ));
                }
            }),
            Actions.moveTo(attacker.getX(), attacker.getY(), .125f)
            )
        );
    }

    private int physicalAttack(SimpleUnit attacker, SimpleUnit defender) {
        final int criticalRoll = rng.nextInt(21);
        nearMiss = false;
        criticalHit = false;

        int attackerDamage = attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense();

        switch(criticalRoll) {
            case 1:
                nearMiss = true;
                attackerDamage -= 1;
                break;
            case 20:
                criticalHit = true;
                attackerDamage += 1;
                break;
        }

        return attackerDamage;
    }

    private int magicAttack(SimpleUnit attacker, SimpleUnit defender) {
        final int criticalRoll = rng.nextInt(21);
        nearMiss = false;
        criticalHit = false;

        int attackerDamage = attacker.modifiedSimpleMagic() - defender.modifiedSimpleResistance();

        switch(criticalRoll) {
            case 1:
                nearMiss = true;
                attackerDamage -= 1;
                break;
            case 20:
                criticalHit = true;
                attackerDamage += 1;
                break;
        }

        return attackerDamage;
    }

    private int ballistaAttack(SimpleUnit defender) {
        final int criticalRoll = rng.nextInt(21);
        nearMiss = false;
        criticalHit = false;

        int damage = 20 - defender.modifiedSimpleDefense();

        switch(criticalRoll) {
            case 1:
                nearMiss = true;
                damage -= 5;
                break;
            case 20:
                criticalHit = true;
                damage += 5;
                break;
        }

        return damage;
    }

    private int flamerAttack(SimpleUnit defender) {
        final int criticalRoll = rng.nextInt(21);
        nearMiss = false;
        criticalHit = false;

        int damage = 20 - defender.modifiedSimpleResistance();

        switch(criticalRoll) {
            case 1:
                nearMiss = true;
                damage -= 5;
                break;
            case 20:
                criticalHit = true;
                damage += 5;
                break;
        }

        return damage;
    }

    // ---GETTERS---
    public IronMode iron() {
        if(ironMode == null) {
            ironMode = new IronMode();
        }
        return ironMode;
    }
    public Abilities abilities() { return abilities; }
    public Boolean isVisualizing() {
        return visualizing;
    }

    // --HELPER CLASSES--

    // Abilities
    public static class Abilities {

        public void FireLighter(WYRMGame game, SimpleUnit attacker, SimpleUnit defender) {
            defender.burn();
            defender.burn();
            // deal 1 damage
            // play a blowing-fire effect
        }

        public void Shove(WYRMGame game, SimpleUnit attacker, Array<SimpleUnit> defenders) {
            // for each defender, first determine its cardinal
            // direction from attacker,
            // then determine if the next tile in that direction
            // is occupied and or traversable by that unit's type
            // if the spot is valid for shove, move each defender
            // into the next tile.
            // apply a "bump" / "push-back" stutter in the animation
            // play an impact sound
            // later: apply some dust animation under the defenders


        }

        public void DiveBomb(WYRMGame game, SimpleUnit defender) {
            // new image from attacker's flyer mount drawable
            // fade in new image up and to the left of defender
            // image "swoop" down on defender,
            // visually apply stun to defender
            // image fly off up right
            // image fade out

            game.activeGridScreen.setInputMode(GridScreen.InputMode.CUTSCENE);

            LeifUnit lu = new LeifUnit(game);
            lu.setSize(1, 1.5f);
            lu.setColor(0,0,0,0);
            lu.setPosition(defender.getX() - 1, defender.getY() + 1);

            game.activeGridScreen.gameStage.addActor(lu);

            lu.addAction(Actions.sequence(
                Actions.parallel(
                    Actions.fadeIn(.2f),
                    Actions.moveTo(defender.getX(), defender.getY(), .5f)
                    // todo: animation / drawable changes
                ),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        final Label damageLabel = new Label("Stunned!", game.assetHandler.menuLabelStyle);
                        damageLabel.setFontScale(3);

                        game.activeGridScreen.hudStage.addActor(damageLabel);
                        damageLabel.setPosition(Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .6f);
                        defender.stun();

                        // apply affects here?

                        damageLabel.addAction(Actions.sequence(
                            Actions.parallel(
                                Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 3),
                                Actions.fadeOut(3.5f)
                            ),
                            Actions.removeActor()
                        ));
                    }
                }),
                Actions.parallel(
                    Actions.fadeOut(.2f),
                    Actions.moveTo(defender.getX() + 1, defender.getY() + 1)
                ),
                Actions.removeActor()
            ));
        }

    }

    // Iron mode
    public static class IronMode {
        public IronMode() {
            Gdx.app.log("", "I am IronMan.");
        }

        public void goToCombat(SimpleUnit attacker, SimpleUnit defender) {
            boolean continueCombat = true;

            int attackerAccuracy = attacker.iron().getHitRate() - defender.iron().getEvade();
            int defenderAccuracy = defender.iron().getHitRate() - attacker.iron().getEvade();
            if(attackerAccuracy > 100) {attackerAccuracy = 100;} else if(attackerAccuracy < 0) {attackerAccuracy = 0;}
            if(defenderAccuracy > 100) {defenderAccuracy = 100;} else if(defenderAccuracy < 0) {defenderAccuracy = 0;}


            int attackerDamage = attacker.iron().getAttackPower() - defender.iron().getDefensePower();
            int defenderDamage = defender.iron().getAttackPower() - attacker.iron().getDefensePower();
            if(attackerDamage < 0) {attackerDamage = 0;}
            if(defenderDamage < 0) {defenderDamage = 0;}

            int playerDamageDealt = 0;
            boolean enemyWasKilled = false;

            // todo: class relative power 1-10

            // todo: other exps from staffs, non-combat actions such as stealing and breaking terrain, etc

            if(defender.canMove()) { // Reset attacked unit's highlight to what it was before highlighting attackable
                defender.standardColor();
            } else {
                defender.dimColor();
            }

            int attackerRotations = 1;
            int defenderRotations = 1;
            if(attacker.iron().getAttackSpeed() >= defender.iron().getAttackSpeed() + 4) {
                attackerRotations++;
            } else if (defender.iron().getAttackSpeed() >= attacker.iron().getAttackSpeed() + 4) {
                defenderRotations++;
            }

            int defNewHP1 = defender.getRollingHP() - attackerDamage;
            int atkNewHP1 = attacker.getRollingHP() - defenderDamage;

            final Random random = new Random();
            final int atkRoll1 = random.nextInt(100);

            if(atkRoll1 <= attackerAccuracy) {
                if (defNewHP1 > 0) {
                    Gdx.app.log("combat", "first rotation");
                    defender.setRollingHP(defNewHP1);

                    if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
                        playerDamageDealt += attackerDamage;
                    }

                    Gdx.app.log("combat", "" + attacker.name + " deals " + attackerDamage + " to " + defender.name);
                    Gdx.app.log("combat", "" + defender.name + " has " + defNewHP1 + " hp remaining");
                } else {
                    defender.kill();
                    if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
                        enemyWasKilled = true;
                    }
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
                        attacker.setRollingHP(atkNewHP1);

                        if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
                            playerDamageDealt += defenderDamage;
                        }

                        Gdx.app.log("combat", "" + defender.name + " deals " + defenderDamage + " to " + attacker.name);
                        Gdx.app.log("combat", "" + attacker.name + " has " + atkNewHP1 + " hp remaining");

                    } else {
                        attacker.kill();
                        if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
                            enemyWasKilled = true;
                        }
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
                    int defNewHP2 = defender.getRollingHP() - attackerDamage;

                    if (atkRoll2 <= attackerAccuracy) {
                        if (defNewHP2 > 0) {
                            Gdx.app.log("combat", "" + attacker.name + " deals " + attackerDamage + " to " + defender.name);
                            Gdx.app.log("combat", "" + defender.name + " has " + defNewHP2 + " hp remaining");

                            defender.setRollingHP(defNewHP2);

                            if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
                                playerDamageDealt += attackerDamage;
                            }

                        } else {
                            defender.kill();
                            if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
                                enemyWasKilled = true;
                            }
                            continueCombat = false;
                        }
                    } else {
                        // miss
                    }
                }
            }

            if(continueCombat) {
                if (defenderRotations > 1) {

                    final int defRoll2 = random.nextInt(100);

                    Gdx.app.log("combat", "second rotation");
                    int atkNewHP2 = attacker.getRollingHP() - defenderDamage;

                    if (defRoll2 <= defenderAccuracy) {
                        if (atkNewHP2 > 0) {
                            Gdx.app.log("combat", "" + defender.name + " deals " + defenderDamage + " to " + attacker.name);
                            Gdx.app.log("combat", "" + attacker.name + " has " + atkNewHP2 + " hp remaining");

                            attacker.setRollingHP(atkNewHP2);

                            if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
                                playerDamageDealt += defenderDamage;
                            }

                        } else {
                            attacker.kill();
                            if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
                                enemyWasKilled = true;
                            }
                            continueCombat = false;
                        }
                    } else {
                        // miss
                    }
                }
            }


            // -- further attacks from skills or effects i.e., brave weapons

            // -- EXP
            if(enemyWasKilled){
                int bossBonus = 0;

                if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
                    if(defender.isABoss()) bossBonus = 20;
                    final int lvDiff = defender.iron().getLevel() - attacker.iron().getLevel();

                    if(lvDiff >= 0) {
                        attacker.iron().addExp(((31 - lvDiff) / 3) + 20 + (lvDiff * 3) + bossBonus);
                    } else if(lvDiff == -1) {
                        attacker.iron().addExp(10 + 20 + bossBonus);
                    } else {
                        attacker.iron().addExp(17 + bossBonus);
                    }


                } else if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
                    if(attacker.isABoss()) bossBonus = 20;
                    final int lvDiff = attacker.iron().getLevel() - defender.iron().getLevel();

                    if(lvDiff >= 0) {
                        defender.iron().addExp(((31 - lvDiff) / 3) + 20 + (lvDiff * 3) + bossBonus);
                    } else if(lvDiff == -1) {
                        defender.iron().addExp(10 + 20 + bossBonus);
                    } else {
                        defender.iron().addExp(17 + bossBonus);
                    }

                }

            } else if(playerDamageDealt > 0) {
                if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
                    final int lvDiff = defender.iron().getLevel() - attacker.iron().getLevel();

                    if(lvDiff >= 0) {
                        attacker.iron().addExp((31 - lvDiff) / 3);
                    } else if(lvDiff == -1) {
                        attacker.iron().addExp(10);
                    } else {
                        attacker.iron().addExp(1);
                    }

                } else if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
                    final int lvDiff = attacker.iron().getLevel() - defender.iron().getLevel();

                    if(lvDiff >= 0) {
                        defender.iron().addExp((31 - lvDiff) / 3);
                    } else if(lvDiff == -1) {
                        defender.iron().addExp(10);
                    } else {
                        defender.iron().addExp(1);
                    }

                }
            } else {
                if(attacker.getTeamAlignment() == TeamAlignment.PLAYER) {
                    attacker.iron().addExp(1);
                } else if(defender.getTeamAlignment() == TeamAlignment.PLAYER) {
                    defender.iron().addExp(1);
                }
            }
        }
    }
}
