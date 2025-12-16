package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.grid;

import com.feiqn.wyrm.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.WyrCPAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public class GridCPAction extends WyrCPAction {

    protected GridTile targetTile;
    protected GridUnit subjectUnit; // Like in grammar.
    protected GridUnit objectUnit;  // You know.
    protected GridProp associatedPath; // Possibly vestigial.



    public GridCPAction(WyrType type, ActionType actionType) {
        super(type, actionType);
    }



}
