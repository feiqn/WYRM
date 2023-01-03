package com.feiqn.wyrm.logic.ui.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

        redTall.setColor(1,0,0,1);
        redTall.setWidth(background.getWidth() / 2);
        redTall.setHeight(background.getHeight() * .8f);
        redTall.setPosition(background.getX(), background.getY());

        addActor(redTall);

    }
}
