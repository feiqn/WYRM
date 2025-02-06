package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class BattlePreviewPopup extends PopupMenu {

    final SimpleUnit attacker,
                     defender;

    public int originRow,
               originColumn;

    final BattlePreviewPopup self = this;

    public BattlePreviewPopup(WYRMGame game, SimpleUnit attacker, SimpleUnit defender, int originRow, int originColumn) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;
        this.originRow = originRow;
        this.originColumn = originColumn;

        final Label attackLabel = new Label("ATTACK", game.assetHandler.menuLabelStyle);
        attackLabel.setFontScale(1.5f);
        attackLabel.setColor(Color.RED);
        attackLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeGridScreen.conditionsHandler.combat().simpleVisualCombat(attacker, defender);
//                self.remove();
                game.activeGridScreen.checkLineOrder();

            }
        });

        final Label backLabel = new Label("CANCEL", game.assetHandler.menuLabelStyle);
        backLabel.setFontScale(1.5f);
        backLabel.setColor(Color.ROYAL);
        backLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeGridScreen.hud().addPopup(new FieldActionsPopup(game, attacker, originRow, originColumn));
//                self.remove();
            }
        });

        final Label hpLabel = new Label(" HP ", game.assetHandler.menuLabelStyle);
        hpLabel.setColor(Color.GOLDENROD);
        final Label attackerHPLabel = new Label("" + attacker.getRollingHP(), game.assetHandler.menuLabelStyle);
        attackerHPLabel.setColor(Color.BLUE);
        final Label defenderHPLabel = new Label("" + defender.getRollingHP(), game.assetHandler.menuLabelStyle);
        defenderHPLabel.setColor(Color.RED);
        final Label damageLabel = new Label("DMG", game.assetHandler.menuLabelStyle);
        damageLabel.setColor(Color.GOLDENROD);
        int attackerDamage = attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense();
        if(attackerDamage < 0) {attackerDamage = 0;}
        final Label atkDmgLabel = new Label("" + attackerDamage, game.assetHandler.menuLabelStyle);


        layout.pad(Gdx.graphics.getHeight() * 0.01f);

        layout.add(backLabel).colspan(3).padBottom(Gdx.graphics.getHeight() * 0.01f);
        layout.row();
        layout.add(attackerHPLabel).left(); layout.add(hpLabel); layout.add(defenderHPLabel).right();
        layout.row();
        layout.add(damageLabel).colspan(2).left(); layout.add(atkDmgLabel).right();
        layout.row();
        layout.add(attackLabel).colspan(3).padTop(Gdx.graphics.getHeight() * 0.01f);

        if(attacker.modifiedSimpleSpeed() >= defender.modifiedSimpleSpeed() * 2) {
            final Label doubleAttackLabel = new Label("x2", game.assetHandler.menuLabelStyle);
            doubleAttackLabel.setColor(Color.YELLOW);
            doubleAttackLabel.setFontScale(.5f);
            layout.add(doubleAttackLabel);
        }
    }
    // --HELPER CLASSES--
    public static class IronMode {

    }
}
