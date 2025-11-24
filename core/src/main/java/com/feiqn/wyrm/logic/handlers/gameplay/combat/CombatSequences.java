package com.feiqn.wyrm.logic.handlers.gameplay.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.mapdata.Direction;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;
import com.feiqn.wyrm.wyrefactor.handlers.combat.math.DamageCalculator;
import com.feiqn.wyrm.wyrefactor.handlers.combat.math.DamageRoll;

public class CombatSequences {

    private final WYRMGame game;

    public CombatSequences(WYRMGame game) {
        this.game = game;
    }

    public SequenceAction closeCombatSequence(SimpleUnit attacker, SimpleUnit defender) {
        // build a sequence action for the attacking unit
        // animate move towards enemy ->
        // add on screen damage indicator with timer action to remove self ->
        // apply damage to defender ->
        // animate move back into position

        // DAMAGE
        final DamageRoll dmg;

        if (attacker.isOccupyingMapObject) {
            switch (attacker.getOccupyingMapObject().objectType) {
                case BALLISTA:
                    dmg = DamageCalculator.ballistaAttackDamage(defender);
                    break;
                case FLAMETHROWER:
                    dmg = DamageCalculator.flamerAttackDamage(defender);
                    break;
                default:
                    dmg = new DamageRoll();
            }
        } else {
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
        }


        // LABEL
        final Label damageLabel = new Label("" + dmg.getRawDamage(), game.assetHandler.menuLabelStyle);
        damageLabel.setFontScale(4);
        if (dmg.isNearMiss()) {
            damageLabel.setColor(Color.PURPLE);
            damageLabel.setText("Near Miss! " + dmg);
        } else if (dmg.isCrit()) {
            damageLabel.setColor(Color.GOLD);
            damageLabel.setText("Critical Hit! " + dmg);
        }


        // ANIMATION
        final MoveByAction firstMove;
        final Direction direction = game.activeGridScreen.getLogicalMap().directionFromTileToTile(attacker.getOccupyingTile(), defender.getOccupyingTile());

        switch (direction) {
            case NORTH:
                attacker.faceNorth();
                firstMove = Actions.moveBy(0, .5f, .2f);
                break;
            case SOUTH:
                attacker.faceSouth();
                firstMove = Actions.moveBy(0, -.5f, .2f);
                break;
            case EAST:
                attacker.faceEast();
                firstMove = Actions.moveBy(.5f, 0, .2f);
                break;
            case WEST:
                attacker.faceWest();
                firstMove = Actions.moveBy(-.5f, 0, .2f);
                break;
            default:
                firstMove = Actions.moveBy(0, 0, .2f);
                break;
        }

        final SequenceAction returnValue = Actions.sequence(
            firstMove,
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    defender.applyDamage(dmg.getRawDamage());

                    game.activeGridScreen.hudStage.addActor(damageLabel);
                    damageLabel.setPosition(Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .45f);

                    // apply affects here from damage roll

                    damageLabel.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .85f, 5),
                            Actions.fadeOut(6)
                        ),
                        Actions.removeActor()
                    ));
                }
            }),
            Actions.moveTo(attacker.getX(), attacker.getY(), .2f)
        );

        return returnValue;

    }
}


    // I'm imagining later we can have different sequences with more flair and animation.
    // I think this is also where we would add in close-up larger animated sequences (BattleWindow)

