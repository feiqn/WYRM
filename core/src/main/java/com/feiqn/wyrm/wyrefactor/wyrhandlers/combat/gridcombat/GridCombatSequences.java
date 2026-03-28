package com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.damage.DamageCalculator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.damage.DamageRoll;

public final class GridCombatSequences {

    private GridCombatSequences() {}

    public static SequenceAction closeCombat(GridUnit attacker, GridUnit defender) {

        final DamageRoll dmg;

        switch(attacker.stats().inventory().getEquippedWeapon().getDamageType(false)) {

            case PHYSICAL:
                dmg = DamageCalculator.physicalAttackDamage(attacker, defender);
                break;

            case MAGIC:

            case HERBAL:

            case EXPLOSIVE:
                break;
        }

        // TODO:
        //  - send for damage label to display and animate
        //  - build 3-part animation sequence for attacker ( move, run, move )


        // TODO: HUD method to neatly display damage text
        // LABEL
//        final Label damageLabel = new Label("" + dmg.getRawDamage(), WYRMGame.assets().menuLabelStyle);
//        damageLabel.setFontScale(4);
//        if (dmg.isNearMiss()) {
//            damageLabel.setColor(Color.PURPLE);
//            damageLabel.setText("Near Miss! " + dmg.getRawDamage());
//        } else if (dmg.isCrit()) {
//            damageLabel.setColor(Color.GOLD);
//            damageLabel.setText("Critical Hit! " + dmg.getRawDamage());
//        }

    }

}
