package com.feiqn.wyrm.logic.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;

public class PopupMenu extends Group {

    public enum MenuType {
        FIELD_MENU,

    }

    private final MenuType menuType;

    final WYRMGame game;

    private Label label1,
                  label2,
                  label3;

    public PopupMenu(WYRMGame game, MenuType type) {
        super();
        this.game = game;
        this.menuType = type;
        game.activeBattleScreen.activePopupMenu = this;

        switch(menuType) {
            case FIELD_MENU:

        }


    }

    private void ConstructFieldMenu() {

    }

}
