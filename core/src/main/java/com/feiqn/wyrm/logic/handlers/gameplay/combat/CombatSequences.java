package com.feiqn.wyrm.logic.handlers.gameplay.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.itemdata.simple.equipment.weapons.SimpleWeapon;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class CombatSequences {

    private final WYRMGame game;
    private final CombatHandler damageCalc;

    public CombatSequences(WYRMGame game, CombatHandler combatHandler) {
        this.game = game;
        this.damageCalc = combatHandler;
    }

    public SequenceAction visualCombatSequence(SimpleUnit attacker, SimpleUnit defender) {
        // build a sequence action for the attacking unit
        // animate move towards enemy ->
        // add on screen damage indicator with timer action to remove self ->
        // apply damage to defender ->
        // animate move back into position

        // calculate damage
        // TODO: account for herbal, vehicle, etc damage types

        final int dmg;

        if(attacker.getOccupyingMapObject() != null) {
            switch(attacker.getOccupyingMapObject().objectType) {
                case BALLISTA:
                    dmg = damageCalc.ballistaAttackDamage(defender);
                    break;
                case FLAMETHROWER:
                    dmg = damageCalc.flamerAttackDamage(defender);
                    break;
                default:
                    dmg = Math.max(attacker.simpleWeapon().getDamageType() == SimpleWeapon.DamageType.PHYSICAL ? damageCalc.physicalAttackDamage(attacker, defender) : damageCalc.magicAttackDamage(attacker, defender), 0);;
                    break;
            }
        } else {
            dmg = Math.max(attacker.simpleWeapon().getDamageType() == SimpleWeapon.DamageType.PHYSICAL ? damageCalc.physicalAttackDamage(attacker, defender) : damageCalc.magicAttackDamage(attacker, defender), 0);;
        }

        final Label damageLabel = new Label("" + dmg, game.assetHandler.menuLabelStyle);
        damageLabel.setFontScale(3);
        if (damageCalc.nearlyMissed()) {
            damageLabel.setColor(Color.PURPLE);
            damageLabel.setText("Near Miss! " + dmg);
        } else if (damageCalc.justCrit()) {
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
                    switch(game.activeGridScreen.getLogicalMap().directionFromTileToTile(attacker.getOccupyingTile(), defender.getOccupyingTile())) {
                        case NORTH:
                            attacker.faceNorth();
                            break;
                        case SOUTH:
                            attacker.faceSouth();
                            break;
                        case EAST:
                            attacker.faceEast();
                            break;
                        case WEST:
                            attacker.faceWest();
                            break;
                    }
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

    // I'm imagining later we can have different sequences with more flair and animation.
    // I think this is also where we would add in close-up larger animated sequences (BattleWindow)
}
