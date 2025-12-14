package com.feiqn.wyrm.logic.handlers.gameplay.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.Direction;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math.DamageCalculator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.math.DamageRoll;

public class CombatSequences {

    private final WYRMGame game;

    public CombatSequences(WYRMGame game) {
        this.game = game;
    }

    // Sort of temporary fixes all throughout here. Want to switch to
    // using CombatOverlay for communicating most information, while
    // retaining this functionality for visualization.

    public SequenceAction closeCombatSequence(OLD_SimpleUnit attacker, OLD_SimpleUnit defender) {
        // build a sequence action for the attacking unit
        // animate move towards enemy ->
        // add on screen damage indicator with timer action to remove self ->
        // apply damage to defender ->
        // animate move back into position

        // DAMAGE
        final DamageRoll dmg;

        switch (attacker.simpleWeapon().getDamageType()) {
            case MAGIC:
                dmg = DamageCalculator.magicAttackDamage(attacker, defender);
                break;

            case HERBAL:

            case PHYSICAL:
            default:
                dmg = DamageCalculator.physicalAttackDamage(attacker, defender);
                break;
        }



        // LABEL
        final Label damageLabel = new Label("" + dmg.getRawDamage(), WYRMGame.assets().menuLabelStyle);
        damageLabel.setFontScale(4);
        if (dmg.isNearMiss()) {
            damageLabel.setColor(Color.PURPLE);
            damageLabel.setText("Near Miss! " + dmg.getRawDamage());
        } else if (dmg.isCrit()) {
            damageLabel.setColor(Color.GOLD);
            damageLabel.setText("Critical Hit! " + dmg.getRawDamage());
        }


        // ANIMATION
        final MoveByAction firstMove;
        final Direction direction = game.activeOLDGridScreen.getLogicalMap().directionFromTileToTile(attacker.getOccupyingTile(), defender.getOccupyingTile());

        switch (direction) {
            case NORTH:
                attacker.faceNorth();
                firstMove = Actions.moveBy(0, .5f, .3f);
                break;
            case SOUTH:
                attacker.faceSouth();
                firstMove = Actions.moveBy(0, -.5f, .3f);
                break;
            case EAST:
                attacker.faceEast();
                firstMove = Actions.moveBy(.5f, 0, .3f);
                break;
            case WEST:
                attacker.faceWest();
                firstMove = Actions.moveBy(-.5f, 0, .3f);
                break;
            default:
                firstMove = Actions.moveBy(0, 0, .3f);
                break;
        }

        return Actions.sequence(
            firstMove,
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    defender.applyDamage(dmg.getRawDamage());

                    game.activeOLDGridScreen.hudStage.addActor(damageLabel);
                    damageLabel.setPosition(Gdx.graphics.getWidth() * .45f, Gdx.graphics.getHeight() * .65f);

                    // apply affects here from damage roll

                    damageLabel.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 2),
                            Actions.fadeOut(4)
                        ),
                        Actions.removeActor()
                    ));
                }
            }),
            Actions.moveTo(attacker.getX(), attacker.getY(), .2f)
        );
    }

    public SequenceAction ballistaCombatSequence(Vector2 ballistaCoordinate, OLD_SimpleUnit defender) {
        final DamageRoll dmg = DamageCalculator.ballistaAttackRoll(defender);

        // LABEL
        final Label damageLabel = new Label("" + dmg.getRawDamage(), WYRMGame.assets().menuLabelStyle);
        damageLabel.setFontScale(4);
        if (dmg.isNearMiss()) {
            damageLabel.setColor(Color.PURPLE);
            damageLabel.setText("Near Miss! " + dmg.getRawDamage());
        } else if (dmg.isCrit()) {
            damageLabel.setColor(Color.GOLD);
            damageLabel.setText("Critical Hit! " + dmg.getRawDamage());
        }

        return Actions.sequence(
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    defender.applyDamage(dmg.getRawDamage());

                    game.activeOLDGridScreen.hudStage.addActor(damageLabel);
                    damageLabel.setPosition(Gdx.graphics.getWidth() * .45f, Gdx.graphics.getHeight() * .65f);

                    // apply affects here from damage roll

                    damageLabel.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 2),
                            Actions.fadeOut(4)
                        ),
                        Actions.removeActor()
                    ));
                }
            })
            );
    }
}


    // I'm imagining later we can have different sequences with more flair and animation.
    // I think this is also where we would add in close-up larger animated sequences (BattleWindow)

