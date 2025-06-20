package com.feiqn.wyrm.logic.screens.cutscenes.stage1;

import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.post.DScript_1A_POST_Leif_ShouldFindAntal;
import com.feiqn.wyrm.logic.screens.GridScreen;

public class GridScreen_CUTSCENE_Leif_ShouldFindAntal extends GridScreen {

    public GridScreen_CUTSCENE_Leif_ShouldFindAntal(WYRMGame game) {
        super(game);
    }

    // TODO: loadMap() after making one

    @Override
    protected void initializeVariables() {
        super.initializeVariables();
        setInputMode(InputMode.CUTSCENE);
    }

    @Override
    protected boolean shouldRunAI() {
        return false;
    }

    @Override
    public void show() {
        super.show();
        inputMode = InputMode.CUTSCENE;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                conditions().conversations().startCutscene(new DScript_1A_POST_Leif_ShouldFindAntal(game));
            }
        }, 3);
    }

}
