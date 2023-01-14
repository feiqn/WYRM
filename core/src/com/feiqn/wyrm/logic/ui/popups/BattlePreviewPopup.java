package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.ui.PopupMenu;
import com.feiqn.wyrm.models.unitdata.Unit;

public class BattlePreviewPopup extends PopupMenu {

    final Unit attacker,
               defender;
    public BattlePreviewPopup(WYRMGame game, Unit attacker, Unit defender) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;

        AddLargeRight();
    }

    @Override
    protected void AddLargeRight() {
        super.AddLargeRight();

        background.setWidth(background.getWidth() / 2);

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

        // TODO: bunch of math and then preview battle results

        // HP
        final Label HPLabel = new Label("HP", game.activeBattleScreen.menuLabelStyle);
        HPLabel.setPosition(background.getX() + background.getWidth() * .35f, background.getHeight() * .8f);
        addActor(HPLabel);

        final Label attackerHPLabel = new Label("" + attacker.getCurrentHP(), game.activeBattleScreen.menuLabelStyle);
        attackerHPLabel.setPosition(background.getX() + background.getWidth() * .65f, background.getHeight() * .8f);
        attackerHPLabel.setFontScale(1.25f);
        addActor(attackerHPLabel);

        final Label defenderHPLabel = new Label("" + defender.getCurrentHP(), game.activeBattleScreen.menuLabelStyle);
        defenderHPLabel.setPosition(background.getX() + background.getWidth() * .05f, attackerHPLabel.getY());
        defenderHPLabel.setFontScale(1.25f);
        addActor(defenderHPLabel);

        // ACCURACY
        final Label HitLabel = new Label("HIT", game.activeBattleScreen.menuLabelStyle);
        HitLabel.setPosition(HPLabel.getX() - HitLabel.getWidth() * .15f, HPLabel.getY() - HitLabel.getHeight() * 2);
        addActor(HitLabel);

        // TODO: calculate accuracy

        /*
        show current health
        show damage each unit will receive, and how many times it will hit
        show chance to hit / miss
        show chance to crit / dodge / parry
         */
    }
}
