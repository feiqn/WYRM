package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.grid;

import com.feiqn.wyrm.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.WyrCPAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public class GridCPAction extends WyrCPAction {

    protected GridTile targetTile;
    protected GridUnit subjectUnit; // Like in grammar.
    protected GridUnit objectUnit;  // You know.
    protected GridPath associatedPath; // Possibly vestigial.

    // TODO: campaign flags ?

    public GridCPAction(ActionType actionType) {
        super(WyrType.GRIDWORLD, actionType);
    }

    public void setTargetTile(GridTile targetTile) { this.targetTile = targetTile; }
    public void setSubjectUnit(GridUnit subjectUnit) { this.subjectUnit = subjectUnit; }
    public void setObjectUnit(GridUnit unit) { objectUnit = unit; }
    public void setAssociatedPath(GridPath associatedPath) { this.associatedPath = associatedPath; }
    // TODO: weigh() ?

    public GridPath associatedPath() { return associatedPath; }
    public GridTile targetTile() { return targetTile; }
    public GridUnit subjectUnit() { return subjectUnit; }
    public GridUnit objectUnit() { return objectUnit; }
}
