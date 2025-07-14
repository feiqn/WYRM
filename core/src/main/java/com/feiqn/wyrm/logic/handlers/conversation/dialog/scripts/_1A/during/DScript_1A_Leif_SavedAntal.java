package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.during;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.ChoreographedDialogScript;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogAction;
import com.feiqn.wyrm.logic.screens.cutscenes.stage1.GridScreen_CUTSCENE_Leif_ShouldFindAntal;

public class DScript_1A_Leif_SavedAntal extends ChoreographedDialogScript {

    public DScript_1A_Leif_SavedAntal(WYRMGame game) {
        super(game);
    }

    @Override
    public void setSeries() {
        if(ags == null) return;

        set(CharacterExpression.LEIF_WORRIED, "We made it!");

        choreographFadeOut();

        choreographTransitionScreen(new GridScreen_CUTSCENE_Leif_ShouldFindAntal(game));
    }

}
