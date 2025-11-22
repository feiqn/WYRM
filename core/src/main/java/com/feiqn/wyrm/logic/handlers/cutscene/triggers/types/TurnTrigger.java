package com.feiqn.wyrm.logic.handlers.cutscene.triggers.types;

import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.logic.handlers.cutscene.triggers.CutsceneTrigger;

public class TurnTrigger extends CutsceneTrigger {

    private final int turn;

    public TurnTrigger(CutsceneScript script, int turn) {
        super(script);
        this.turn = turn;
    }

    public boolean checkTrigger(int turn) {
        return this.turn == turn && !hasTriggered;
    }

}
