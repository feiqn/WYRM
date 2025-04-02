package com.feiqn.wyrm.logic.handlers.conversation.dialog;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class ChoreographedDialogScript extends DialogScript {
    // includes relevant map and unit data for puppeting in runnable actions

    private final GridScreen ags;

    public ChoreographedDialogScript(GridScreen gridScreen) {
        super();
        ags = gridScreen;
    }

    @Override
    protected void setSeries() {

    }
}
