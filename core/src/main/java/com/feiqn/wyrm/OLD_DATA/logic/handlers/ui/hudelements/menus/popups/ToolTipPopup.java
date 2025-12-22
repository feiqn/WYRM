package com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.menus.popups;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.ui.hudelements.menus.PopupMenu;

public class ToolTipPopup extends PopupMenu {

    protected CharSequence text;

    public ToolTipPopup(CharSequence text) {
        super(WYRMGame.root());
        this.text = text;


        final Label toolTipLabel = new Label("" + text, WYRMGame.assets().menuLabelStyle);

        layout.add(toolTipLabel).fill();
    }
}
