package com.feiqn.wyrm.wyrefactor.handlers.gridmap;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.handlers.gridmap.actors.GridActorHandler;

public class WyrGrid {

    // refactor of WyrMap

    private final WYRMGame root;

    private final GridActorHandler actorHandler;

    /*
    - grid combat handler
    - computer player handler
     */

    public WyrGrid(WYRMGame game) {
        this.root = game;
        this.actorHandler = new GridActorHandler(root);
    }

    public GridActorHandler getActorHandler() { return actorHandler; }
}
