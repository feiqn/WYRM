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
        attackLabel.setFontScale(2);
        attackLabel.setColor(Color.RED);
        attackLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeGridScreen.conditions().combat().simpleVisualCombat(attacker, defender);
                game.activeGridScreen.hud().removePopup();
//                self.remove();
                game.activeGridScreen.checkLineOrder();

            }
        });

        final Label backLabel = new Label("CANCEL", game.assetHandler.menuLabelStyle);
        backLabel.setFontScale(2);
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
        hpLabel.setFontScale(1.75f);
        hpLabel.setColor(Color.GOLDENROD);

        final Label attackerHPLabel = new Label("" + attacker.getRollingHP(), game.assetHandler.menuLabelStyle);
        attackerHPLabel.setFontScale(1.5f);
        attackerHPLabel.setColor(Color.BLUE);

        final Label defenderHPLabel = new Label("" + defender.getRollingHP(), game.assetHandler.menuLabelStyle);
        defenderHPLabel.setFontScale(1.5f);
        defenderHPLabel.setColor(Color.RED);

        final Label damageLabel = new Label("DMG", game.assetHandler.menuLabelStyle);
        damageLabel.setFontScale(1.75f);
        damageLabel.setColor(Color.GOLDENROD);

        int attackerDamage = attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense();
        if(attackerDamage < 0) {attackerDamage = 0;}
        final Label atkDmgLabel = new Label("" + attackerDamage , game.assetHandler.menuLabelStyle);
        atkDmgLabel.setFontScale(1.5f);

        layout.padLeft(Gdx.graphics.getWidth() * 0.01f);
        layout.padRight((Gdx.graphics.getWidth() * 0.01f));
        layout.padTop(Gdx.graphics.getHeight() * 0.025f);
        layout.padBottom((Gdx.graphics.getHeight() * 0.025f));

        layout.add(backLabel).colspan(3).padBottom(Gdx.graphics.getHeight() * 0.01f);
        layout.row();
        layout.add(attackerHPLabel).left(); layout.add(hpLabel); layout.add(defenderHPLabel).right();
        layout.row();
        layout.add(damageLabel).colspan(2).left(); layout.add(atkDmgLabel).right();
        if(attacker.modifiedSimpleSpeed() >= defender.modifiedSimpleSpeed() * 2) {
            final Label doubleAttackLabel = new Label("x2", game.assetHandler.menuLabelStyle);
            doubleAttackLabel.setColor(Color.YELLOW);
            doubleAttackLabel.setFontScale(.75f);
            layout.add(doubleAttackLabel).left();
        }
        layout.row();
        layout.add(attackLabel).colspan(3).padTop(Gdx.graphics.getHeight() * 0.01f);

    }
    // --HELPER CLASSES--
    public static class IronMode {

    }
}
