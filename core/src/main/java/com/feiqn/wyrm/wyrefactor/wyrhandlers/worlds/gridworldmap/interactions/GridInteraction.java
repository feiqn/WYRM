package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;

public abstract class GridInteraction extends WyrInteraction {

    protected GridInteraction(GridActor parent, InteractionType actType, int interactableRange, CharSequence label, CharSequence toolTipText) {
        super(WyrType.GRIDWORLD, parent, actType, interactableRange, label, toolTipText);
    }

}
