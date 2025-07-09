package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.during;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogAction;
import com.feiqn.wyrm.logic.screens.MainMenuScreen;

public class DScript_1A_Leif_FleeingAlone extends ChoreographedDialogScript {

    public DScript_1A_Leif_FleeingAlone(WYRMGame game) {
        super(game);
    }

    @Override
    public void setSeries() {
        if(ags == null) return;

        choreographShortPause();

        set(CharacterExpression.LEIF_WORRIED, "I'm sorry...");
        set(CharacterExpression.LEIF_WORRIED, "I can't help you.");
        set(CharacterExpression.LEIF_WORRIED, "I've got to get out of here...");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                game.activeGridScreen.gameStage.addAction(
                    new SequenceAction(
                        Actions.fadeOut(3),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.transitionScreen(new MainMenuScreen(game));
                            }
                        })
                    )
                );
            }
        };
        DialogAction action = new DialogAction(runnable);

        lastFrame().addDialogAction(action);
    }

}
