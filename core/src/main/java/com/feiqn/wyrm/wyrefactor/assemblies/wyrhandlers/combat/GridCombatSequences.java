package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.math.damage.DamageCalculator;
import com.feiqn.wyrm.wyrefactor.assemblies.math.damage.DamageRoll;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.GameKit.RPG.DamageType;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.handlers;

public final class GridCombatSequences {

    private GridCombatSequences() {}

    public static SequenceAction closeCombat(WyrActor attacker, WyrActor defender) {

        final DamageRoll dmg;
        final DamageType dmgT;

        switch(attacker.getActorType()) {
            case ENTITY:
                dmgT = ((WyrActor.Unit)attacker).getInventory().equipment().getEquippedWeapon().getDamageType(false);
                break;
            case PROP:
                dmgT = ((WyrActor.Prop)attacker).getInventory().getArmament().getDamageType(false);
                break;
            default:
                dmgT = DamageType.PHYSICAL;
                break;
        }

        switch(dmgT) {

            case MAGIC:

            case HERBAL:

            case EXPLOSIVE:

            case PHYSICAL:
            default:
                dmg = DamageCalculator.physicalAttackDamage(attacker, defender);
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
        final Wyr.Utilities.Compass direction = handlers.map().directionFromTileToTile(attacker.getOccupiedTile(), defender.getOccupiedTile());

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
//            Actions.moveTo(attacker.gridX(), attacker.gridY(), .2f),
            Actions.moveBy(-anim1.getAmountX(), -anim1.getAmountY(), .4f)
        );
    }

    public static SequenceAction distantCombat(WyrActor attacker, WyrActor defender) {


        return Actions.sequence(

        );
    }

}
