package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.math.damage.DamageCalculator;
import com.feiqn.wyrm.wyrefactor.assemblies.math.damage.DamageRoll;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.DamageType;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Utilities.CompassDirection;

import static com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WYRMActors.WyrEmblem.Bullets.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.handlers;

public final class GridCombatSequences {

    private GridCombatSequences() {}

    public static SequenceAction closeCombat(WyrActor attacker, WyrActor defender) {

        final DamageRoll dmg;
        final DamageType dmgT;

        dmgT = attacker.getInventory().getEquippedWeapon().getDamageType();

        switch(dmgT) {

            case HERBAL_TOXIC:
            case HERBAL_SENSORY:
            case HERBAL_CORROSIVE:

            case EXPLOSIVE_BLINDING:
            case EXPLOSIVE_COMBUSTIVE:
            case EXPLOSIVE_PROPULSIVE:

            case PHYS_CUT:
            case PHYS_STAB:
            case PHYS_BLUNT:

            default:
                dmg = DamageCalculator.physicalAttackRoll(attacker, defender);
                break;
        }


        // TODO: HUD method to neatly display damage text
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
        final MoveByAction anim1;
        final CompassDirection direction = handlers.map().directionFromTileToTile(attacker.getOccupiedTile(), defender.getOccupiedTile());

        switch (direction) {
            case N:
                attacker.faceNorth();
                anim1 = Actions.moveBy(0, .5f, .3f);
                break;
            case S:
                attacker.faceSouth();
                anim1 = Actions.moveBy(0, -.5f, .3f);
                break;
            case E:
                attacker.faceEast();
                anim1 = Actions.moveBy(.5f, 0, .3f);
                break;
            case W:
                attacker.faceWest();
                anim1 = Actions.moveBy(-.5f, 0, .3f);
                break;
            default:
                anim1 = Actions.moveBy(0, 0, .3f);
                break;
        }


        return Actions.sequence(
            anim1,
            Actions.run(new Runnable() {// TODO: hud method to simplify and streamline
                @Override
                public void run() {
                    defender.stats().applyDamage(dmg.getRawDamage());

                    handlers.hud().addActor(damageLabel);
                    damageLabel.setPosition(Gdx.graphics.getWidth() * .45f, Gdx.graphics.getHeight() * .55f);

                    // apply affects here from damage roll

                    damageLabel.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.moveTo(damageLabel.getX(), Gdx.graphics.getHeight() * .8f, 3.5f),
                            Actions.fadeOut(4)
                        ),
                        Actions.removeActor()
                    ));
                }
            }),
            Actions.moveBy(-anim1.getAmountX(), -anim1.getAmountY(), .4f)
        );
    }

    public static SequenceAction distantCombat(WyrActor attacker, WyrActor defender) {


        return Actions.sequence(

        );
    }

    public static SequenceAction propArmamentFire(WyrActor.Unit unitFiring, WyrActor.Prop beingFired, WyrActor firedAt) {

        final DamageRoll dmg = DamageCalculator.armamentAttack(beingFired.getInventory().getEquippedWeapon(), firedAt);
        final Actor bullet = debugBullet();

        final CompassDirection directionFromActorToProp = handlers.map().directionFromTileToTile(unitFiring.getGridPosition(), beingFired.getGridPosition());
        final CompassDirection directionFromPropToTarget = handlers.map().directionFromTileToTile(beingFired.getGridPosition(), firedAt.getGridPosition());
        final CompassDirection directionFromTargetToProp = handlers.map().directionFromTileToTile(firedAt.getGridPosition(), beingFired.getGridPosition());

        final float xDifUnitToProp = unitFiring.gridX() - beingFired.gridX();
        final float yDifUnitToProp = unitFiring.gridY() - beingFired.gridY();

        final float xDifPropToTarget = beingFired.gridX() - firedAt.gridX();
        final float yDifPropToTarget = beingFired.gridY() - firedAt.gridY();

        final float xRecoil = (xDifPropToTarget == 0 ? 0 : (xDifPropToTarget > 0 ? .5f : -.5f));
        final float yRecoil = (yDifPropToTarget == 0 ? 0 : (yDifPropToTarget > 0 ? .5f : -.5f));

        // Full sequence as follows is returned onto unitFiring actor
        // and played in sequence with runnable to standardizeParse.
        return Actions.sequence(
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    unitFiring.face(directionFromActorToProp);
//                    firedAt.face(directionFromTargetToProp);
//                    beingFired.face(directionFromPropToTarget);

                    beingFired.addAction(Actions.sequence(
                        Actions.moveBy(xRecoil, yRecoil, 1f),
                        Actions.moveBy(-xRecoil, -yRecoil, .25f)
                    ));
                }
            }),
            Actions.moveBy(xDifUnitToProp * .5f, yDifUnitToProp * .5f, 1),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    handlers.screen().getGameStage().addActor(bullet);
                    bullet.setPosition(beingFired.gridX(), beingFired.gridY());

                    handlers.camera().follow(bullet);

                    bullet.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.moveTo(firedAt.gridX(), firedAt.gridY(), 1),
                            Actions.sequence(
                                Actions.moveBy(0, 1, .5f),
                                Actions.moveBy(0, -1, .5f)
                            )
                        ),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                handlers.camera().standardize();
                                firedAt.parseDamageRoll(dmg);
                                bullet.clearActions();
                                bullet.addAction(Actions.sequence(
                                        Actions.fadeOut(.5f),
                                        Actions.removeActor()
                                ));
                            }
                        })
                    ));
                }
            }),
            Actions.moveBy(-xDifUnitToProp * .5f, -yDifUnitToProp * .5f, .25f)
        );
    }

}
