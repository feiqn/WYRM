package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.post;

import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;

public class DScript_1A_POST_Leif_ShouldFindAntal extends DialogScript {

    public DScript_1A_POST_Leif_ShouldFindAntal() { super(); }

    @Override
    protected void setSeries() {
        set(CharacterExpression.LEIF_EXHAUSTED, "We managed to escape...");
        set(CharacterExpression.LEIF_WORRIED, "And that knight, traveling alone on the road...");
        set(CharacterExpression.LEIF_THINKING, "We should find him.");
    }

}
