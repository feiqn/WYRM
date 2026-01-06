package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;

public abstract class GridInteraction extends WyrInteraction {

    protected GridPath associatedPath = null;

    protected GridInteraction(GridActor parent, GridActor object, InteractionType actType, int interactableRange) {
        super(WyrType.GRIDWORLD, parent, object, actType, interactableRange);
    }

    @Override
    public GridActor getParent() { return (GridActor)super.getParent(); }
    @Override
    public GridActor getObject() { return (GridActor)super.getObject(); }
}
