package com.feiqn.wyrm.logic.handlers.conversation.dialog;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import static com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression.LEIF_SMILING;

public class ChoreographedDialogScript extends DialogScript {

    // includes relevant map and unit data for puppeting in runnable actions

    private final GridScreen ags;

    public ChoreographedDialogScript(GridScreen gridScreen) {
        framesToDisplay = new Array<>();
        frameIndex = 0;
        ags = gridScreen;
//        setSeries();
    }

    @Override
    protected void setSeries() {
        if(ags != null) {
            SimpleUnit leif = ags.conditions().teams().getPlayerTeam().get(0);

            set(LEIF_SMILING, "Oh boy!");

            set(LEIF_SMILING, "This sure is a debug conversation!");

            set(LEIF_SMILING, "I'm gonna move one space to the left!");

            choreographMoveTo(leif, ags.getLogicalMap().nextTileLeftFrom(leif.occupyingTile));

            set(LEIF_SMILING, "Oooooo he's trying!");
        }
    }

    @Override
    public DialogFrame nextFrame() {
        if(frameIndex == 0) {
            setSeries();
        }
        return super.nextFrame();
    }
}
