package com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A;

import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogScript;

public class DScript_1A_Antal_HelpMe extends DialogScript {

    public DScript_1A_Antal_HelpMe() {
        super();
    }

    @Override
    protected void setSeries() {
        set(CharacterExpression.ANTAL_EXHAUSTED, "Please...");
        set(CharacterExpression.ANTAL_EXHAUSTED, "...help me.");
        set(CharacterExpression.LEIF_PANICKED, "Help you?! Aren't you supposed to be protecting the city?!", SpeakerPosition.RIGHT);
        set(CharacterExpression.ANTAL_EXHAUSTED, "Please, this armor, it's so heavy...");
        set(CharacterExpression.ANTAL_EXHAUSTED, "I'll die if I don't get out of here!");
    }

}
