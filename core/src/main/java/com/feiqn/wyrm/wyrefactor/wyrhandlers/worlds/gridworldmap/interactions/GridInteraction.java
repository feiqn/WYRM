package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions;

import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;

public abstract class GridInteraction extends WyrInteraction {

    protected GridInteraction(GridActor parent, InteractionType actType, int interactableRange, CharSequence label, CharSequence toolTipText) {
        super(WyrType.GRIDWORLD, parent, actType, interactableRange, label, toolTipText);
    }

}
