package com.feiqn.wyrm.wyrefactor.wyrscreen.gridmapscreen;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public class WyrGridScreen extends WyrScreen {

    // full refactor of GridScreen.
    // first need to refact WyrMap

    public WyrGridScreen(WYRMGame game) {
        super(game, Type.GRID);
    }
}
