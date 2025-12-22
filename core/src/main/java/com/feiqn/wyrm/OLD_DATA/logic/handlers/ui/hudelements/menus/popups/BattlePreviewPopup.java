package com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.OLD_DATA.logic.screens.OLD_GridScreen;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;

public class BattlePreviewPopup extends PopupMenu {

    final OLD_SimpleUnit attacker,
                     defender;

    public int originRow,
               originColumn;

    final BattlePreviewPopup self = this;

    public BattlePreviewPopup(WYRMGame game, OLD_SimpleUnit attacker, OLD_SimpleUnit defender, int originRow, int originColumn) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;
        this.originRow = originRow;
        this.originColumn = originColumn;

        final Label attackLabel = new Label("ATTACK", WYRMGame.assets().menuLabelStyle);
        attackLabel.setFontScale(2);
        attackLabel.setColor(Color.FIREBRICK);
        attackLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeOLDGridScreen.conditions().combat().visualizeCombat(attacker, defender);
                game.activeOLDGridScreen.hud().removePopup();

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        ags.setInputMode(OLD_GridScreen.OLD_InputMode.STANDARD);
                        ags.checkLineOrder();
                    }
                }, 1);

            }
        });

        final Label backLabel = new Label("CANCEL", WYRMGame.assets().menuLabelStyle);
        backLabel.setFontScale(2);
        backLabel.setColor(Color.CYAN);
        backLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeOLDGridScreen.hud().addPopup(new FieldActionsPopup(game, attacker, originRow, originColumn));
//                self.remove();
            }
        });

//        final Label hpLabel = new Label(" HP ", game.assetHandler.menuLabelStyle);
//        hpLabel.setFontScale(1.75f);
//        hpLabel.setColor(Color.GOLDENROD);

//        final Label attackerHPLabel = new Label("" + attacker.getRollingHP(), game.assetHandler.menuLabelStyle);
//        attackerHPLabel.setFontScale(1.5f);
//        attackerHPLabel.setColor(Color.BLUE);

//        final Label defenderHPLabel = new Label("" + defender.getRollingHP(), game.assetHandler.menuLabelStyle);
//        defenderHPLabel.setFontScale(1.5f);
//        defenderHPLabel.setColor(Color.RED);

        int attackerDamage = attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense();
        if(attackerDamage < 0) {attackerDamage = 0;}

        final boolean twice = attacker.modifiedSimpleSpeed() >= defender.modifiedSimpleSpeed() * 2;

        final Label damageLabel = new Label("[CYAN]" + attacker.getName() + " []will do [SCARLET]" + attackerDamage + " []to [RED]" + defender.getName() + "[]" + (twice ? "[GOLDENROD] twice[]." : "."), WYRMGame.assets().menuLabelStyle);
        damageLabel.setFontScale(1.8f);
//        damageLabel.setColor(Color.GOLDENROD);

//        int attackerDamage = attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense();
//        if(attackerDamage < 0) {attackerDamage = 0;}
//        final Label atkDmgLabel = new Label("" + attackerDamage , game.assetHandler.menuLabelStyle);
//        atkDmgLabel.setFontScale(1.5f);

        layout.padLeft(Gdx.graphics.getWidth() * 0.01f);
        layout.padRight((Gdx.graphics.getWidth() * 0.01f));
        layout.padTop(Gdx.graphics.getHeight() * 0.025f);
        layout.padBottom((Gdx.graphics.getHeight() * 0.025f));

        layout.add(backLabel).colspan(3).padBottom(Gdx.graphics.getHeight() * 0.01f);
        layout.row();
        layout.add(damageLabel).left(); // layout.add(hpLabel); layout.add(defenderHPLabel).right();
        layout.row();
//        layout.add(damageLabel).colspan(2).left(); layout.add(atkDmgLabel).right();
//        if(attacker.modifiedSimpleSpeed() >= defender.modifiedSimpleSpeed() * 2) {
//            final Label doubleAttackLabel = new Label("x2", game.assetHandler.menuLabelStyle);
//            doubleAttackLabel.setColor(Color.YELLOW);
//            doubleAttackLabel.setFontScale(.75f);
//            layout.add(doubleAttackLabel).left();
//        }
//        layout.row();
        layout.add(attackLabel).padTop(Gdx.graphics.getHeight() * 0.01f);

    }
    // --HELPER CLASSES--
    public static class IronMode {

    }
}
