package com.feiqn.wyrm.logic.handlers.ui.hudelements.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.PopupMenu;
import com.feiqn.wyrm.models.unitdata.Unit;

public class BattlePreviewPopup extends PopupMenu {

    final Unit attacker,
               defender;

    public int originRow,
               originColumn;

    final BattlePreviewPopup self = this;
    public BattlePreviewPopup(WYRMGame game, Unit attacker, Unit defender, int originRow, int originColumn) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;

        this.originRow = originRow;
        this.originColumn = originColumn;

        addLargeRight();
    }

    protected void addLargeRight() {
        addActor(background);

        background.setWidth(background.getWidth() / 1.5f);

        final Image redTall;
        final Texture blueSquareTexture = new Texture(Gdx.files.internal("ui/menu.png"));
        final TextureRegion blueSquareRegion = new TextureRegion(blueSquareTexture,0,0,96,96);
        redTall = new Image(blueSquareRegion);

        redTall.setColor(1,0,0,0.8f);
        redTall.setWidth(background.getWidth() / 2);
        redTall.setHeight(background.getHeight() * .8f);
        redTall.setPosition(background.getX(), background.getY());

        // todo: make an actual sprite for this menu bg

        final Image redCorner;
        redCorner = new Image(blueSquareRegion);
        redCorner.setColor(1,0,0,0.8f);
        redCorner.setWidth(redTall.getWidth());
        redCorner.setHeight(background.getHeight() * .2f);
        redCorner.setPosition(redTall.getX() + redTall.getWidth(), redTall.getY());

//        addActor(redCorner);
        addActor(redTall);

        // HP
        final Label hpLabel = new Label("HP", game.assetHandler.menuLabelStyle);
        hpLabel.setPosition(background.getX() + background.getWidth() * .35f, background.getHeight() * .8f);
        addActor(hpLabel);

        final Label attackerHPLabel = new Label("" + attacker.getCurrentHP(), game.assetHandler.menuLabelStyle);
        attackerHPLabel.setPosition(background.getX() + background.getWidth() * .65f, background.getHeight() * .8f);
        attackerHPLabel.setFontScale(1.25f);
        addActor(attackerHPLabel);

        final Label defenderHPLabel = new Label("" + defender.getCurrentHP(), game.assetHandler.menuLabelStyle);
        defenderHPLabel.setPosition(background.getX() + background.getWidth() * .05f, attackerHPLabel.getY());
        defenderHPLabel.setFontScale(1.25f);
        addActor(defenderHPLabel);

        // ACCURACY
        final Label hitLabel = new Label("HIT", game.assetHandler.menuLabelStyle);
        hitLabel.setPosition(hpLabel.getX() - hitLabel.getWidth() * .15f, hpLabel.getY() - hitLabel.getHeight() * 2);
        addActor(hitLabel);

        int attackerAccuracy = attacker.getHitRate() - defender.getEvade();
        if(attackerAccuracy > 100) {attackerAccuracy = 100;} else if(attackerAccuracy < 0) {attackerAccuracy = 0;}
        Gdx.app.log("accuracy: ", "" + attackerAccuracy);

        int defenderAccuracy = defender.getHitRate() - attacker.getEvade();
        if(defenderAccuracy > 100) {defenderAccuracy = 100;} else if(defenderAccuracy < 0) {defenderAccuracy = 0;}
        Gdx.app.log("accuracy: ", "" + defenderAccuracy);

        final Label atkAccLabel = new Label("" + attackerAccuracy, game.assetHandler.menuLabelStyle);
        atkAccLabel.setPosition(background.getX() + background.getWidth() * .65f, hitLabel.getY());
        addActor(atkAccLabel);

        final Label defAccLabel = new Label("" + defenderAccuracy, game.assetHandler.menuLabelStyle);
        defAccLabel.setPosition(background.getX() + background.getWidth() * .05f, atkAccLabel.getY());
        addActor(defAccLabel);

        // DAMAGE

        final Label damageLabel = new Label("DMG", game.assetHandler.menuLabelStyle);
        damageLabel.setPosition(hitLabel.getX(), hitLabel.getY() - damageLabel.getHeight() * 2);
        addActor(damageLabel);

        int attackerDamage = attacker.getAttackPower() - defender.getDefensePower();
        int defenderDamage = defender.getAttackPower() - attacker.getDefensePower();
        if(attackerDamage < 0) {attackerDamage = 0;}
        if(defenderDamage < 0) {defenderDamage = 0;}

        Gdx.app.log("damage: ", "" + attackerDamage);
        Gdx.app.log("damage: ", "" + defenderDamage);

        final Label atkDmgLabel = new Label("" + attackerDamage, game.assetHandler.menuLabelStyle);
        atkDmgLabel.setPosition(background.getX() + background.getWidth() * .65f, damageLabel.getY());
        addActor(atkDmgLabel);

        final Label defDmgLabel = new Label("" + defenderDamage, game.assetHandler.menuLabelStyle);
        defDmgLabel.setPosition(background.getX() + background.getWidth() * .05f, atkDmgLabel.getY());
        addActor(defDmgLabel);

        if(attacker.getAttackSpeed() >= defender.getAttackSpeed() + 4) {
            Gdx.app.log("double", "double");
            final Label doubleAttackLabel = new Label("x2", game.assetHandler.menuLabelStyle);
            doubleAttackLabel.setPosition(atkDmgLabel.getX() + atkDmgLabel.getWidth(), atkDmgLabel.getY() - atkAccLabel.getHeight() / 3);
            doubleAttackLabel.setColor(Color.YELLOW);
            addActor(doubleAttackLabel);
        } else if(defender.getAttackSpeed() >= attacker.getAttackSpeed() + 4) {
            final Label doubleAttackLabel = new Label("x2", game.assetHandler.menuLabelStyle);
            doubleAttackLabel.setPosition(defDmgLabel.getX() + defDmgLabel.getWidth(), defDmgLabel.getY() - defAccLabel.getHeight() / 3);
            doubleAttackLabel.setColor(Color.YELLOW);
            addActor(doubleAttackLabel);
        }

        // CRIT / DODGE / PARRY?

        // ATTACK BUTTON
        final Label attackLabel = new Label("ATTACK", game.assetHandler.menuLabelStyle);
        attackLabel.setPosition(background.getX(), background.getY() + background.getHeight() * .1f);
        attackLabel.setFontScale(1.5f);
        attackLabel.setColor(Color.RED);
        attackLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeGridScreen.combatHandler.goToCombat(attacker, defender);
                self.remove();
                game.activeGridScreen.checkIfAllUnitsHaveMovedAndPhaseShouldChange();

            }
        });

        addActor(attackLabel);

        // BACK BUTTON
        final Label backLabel = new Label("CANCEL", game.assetHandler.menuLabelStyle);
        backLabel.setPosition(background.getX() - backLabel.getWidth() * .2f, background.getY() + background.getHeight() * .9f);
        backLabel.setColor(Color.ROYAL);
        backLabel.setFontScale(1.5f);

        backLabel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                game.activeGridScreen.uiGroup.addActor(new FieldActionsPopup(game, attacker, x , y, originRow, originColumn));
                self.remove();
            }
        });

        addActor(backLabel);
    }
}
