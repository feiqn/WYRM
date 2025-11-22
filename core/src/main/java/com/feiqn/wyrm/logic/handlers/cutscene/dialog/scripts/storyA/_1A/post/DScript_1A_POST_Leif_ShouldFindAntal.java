package com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.storyA._1A.post;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.ChoreographedCutsceneScript;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.DialogAction;
import com.feiqn.wyrm.logic.screens.storyA.stage1.GridScreen_CUTSCENE_Leif_FoundAntal;

public class DScript_1A_POST_Leif_ShouldFindAntal extends ChoreographedCutsceneScript {

    public DScript_1A_POST_Leif_ShouldFindAntal(WYRMGame game) {
        super(game, CutsceneID.CSID_1A_POST_LEIF_SHOULDFINDANTAL);
    }

    @Override
    protected void declareTriggers() {

    }

    @Override
    protected void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(CharacterExpression.LEIF_EXHAUSTED, "We managed to escape...");
        set(CharacterExpression.LEIF_WORRIED, "And that knight, traveling alone on the road...");
        set(CharacterExpression.LEIF_THINKING, "...We should find him.");

        lastFrame().addDialogAction(new DialogAction(new Runnable() {
            @Override
            public void run() {
                ags.gameStage.addAction(Actions.sequence(
                        Actions.fadeOut(3),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.transitionToScreen(new GridScreen_CUTSCENE_Leif_FoundAntal(game));
                            }
                        })
                    )
                );
            }
        }));
    }

}
