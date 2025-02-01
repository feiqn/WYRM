package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

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
                game.activeGridScreen.conditionsHandler.combat().physicalAttack(attacker, defender);
                self.remove();
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
                game.activeGridScreen.hudStage.addActor(new FieldActionsPopup(game, attacker, originRow, originColumn));
                self.remove();
            }
        });

        final Label hpLabel = new Label("HP", game.assetHandler.menuLabelStyle);
        final Label attackerHPLabel = new Label("" + attacker.getRollingHP(), game.assetHandler.menuLabelStyle);
        attackerHPLabel.setColor(Color.BLUE);
        final Label defenderHPLabel = new Label("" + defender.getRollingHP(), game.assetHandler.menuLabelStyle);
        defenderHPLabel.setColor(Color.RED);
        final Label damageLabel = new Label("DMG", game.assetHandler.menuLabelStyle);
        int attackerDamage = attacker.modifiedSimpleStrength() - defender.modifiedSimpleDefense();
        if(attackerDamage < 0) {attackerDamage = 0;}
        final Label atkDmgLabel = new Label("" + attackerDamage, game.assetHandler.menuLabelStyle);
        if(attacker.modifiedSimpleSpeed() >= defender.modifiedSimpleSpeed() * 2) {
            final Label doubleAttackLabel = new Label("x2", game.assetHandler.menuLabelStyle);
            doubleAttackLabel.setColor(Color.YELLOW);
        }

        // TODO: layout

    }
    // --HELPER CLASSES--
    public static class IronMode {

    }
}
