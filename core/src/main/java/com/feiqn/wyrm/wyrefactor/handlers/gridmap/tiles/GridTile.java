package com.feiqn.wyrm.wyrefactor.handlers.gridmap.tiles;

import com.feiqn.wyrm.WYRMGame;

public class GridTile {

    // refactor of LogicalTile

    protected final WYRMGame game;

    public GridTile(WYRMGame game) {
        this.game = game;
    }
}
