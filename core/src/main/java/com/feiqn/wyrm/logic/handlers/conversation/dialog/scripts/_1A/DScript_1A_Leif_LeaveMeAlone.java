package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A;

import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;

public class DScript_1A_Leif_LeaveMeAlone extends DialogScript {

    private final String bfn;

    public DScript_1A_Leif_LeaveMeAlone(String bestFriend) {
        super();
        this.bfn = bestFriend;
    }

    @Override
    protected void setSeries() {
        set(CharacterExpression.LEIF_PANICKED, "No no no no no no no no");
        set(CharacterExpression.LEIF_PANICKED, "Get off of me!");
        // pegasus uses dive bomb here, stunning enemy
        set(CharacterExpression.LEIF_HOPEFUL, bfn + "!");
        set(CharacterExpression.LEIF_WORRIED, "Ooooohhhhh thank you thank you thank you thank you thank you!");
        set(CharacterExpression.LEIF_DETERMINED, "Let's get out of here!");
    }

}
