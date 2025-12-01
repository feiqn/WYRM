package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;

public class GridInteraction extends WyrInteraction {

    public GridInteraction(WYRMGame root, InteractionType actType, Label label, Runnable runnable) {
        super(root, WyrType.GRIDWORLD, actType);
        this.clickableLabel = label;
        this.runnableInteraction.setRunnable(runnable);
    }

    protected GridInteraction(WYRMGame root, InteractionType type) {
        super(root, WyrType.GRIDWORLD, type);
    }

}
