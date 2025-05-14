package com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.hudelements.menus.PopupMenu;

public class ToolTipPopup extends PopupMenu {

    protected CharSequence text;

    public ToolTipPopup(WYRMGame game, CharSequence text) {
        super(game);
        this.text = text;

        layout.setFillParent(true);
//        layout.pad(Gdx.graphics.getHeight() * .01f);

        final Label toolTipLabel = new Label("" + text, game.assetHandler.menuLabelStyle);
        layout.add(toolTipLabel);
        toolTipLabel.setFontScale(2);
    }
}
