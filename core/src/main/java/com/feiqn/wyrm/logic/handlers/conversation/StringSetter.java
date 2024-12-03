package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;

import java.util.HashMap;

public class StringSetter {

    /**
     *  I have no idea what I am doing.
     */

    private final WYRMGame game;

    public enum StringSet {
        DEBUG,
        STAGE_1A_DIALOG_1_LEIF_FLEEING_ALONE,
        STAGE_1A_DIALOG_2_ANTAL_PLEADING_FOR_HELP,
    }

    private final Array<Dialog> dialogToDisplay; // Add frames programmatically in order, start from index 0, remove as you go

    public StringSetter(WYRMGame game) {
        this.game = game;

        dialogToDisplay = new Array<>();
    }

    private void clearString() {

    }

    public void setString(StringSet set) {
        switch(set) {
            case DEBUG:
                setStringSet_DEBUG();
                break;
            case STAGE_1A_DIALOG_1_LEIF_FLEEING_ALONE:
                setStringSet_STAGE_1A_DIALOG_1();
                break;
        }
    }


    private void setStringSet_DEBUG() {
        set(CharacterExpression.LEIF_SMILING, "Hello!");

        set(CharacterExpression.LEIF_TALKING, "Thank you for taking a look at my game!");

    }

    private void setStringSet_STAGE_1A_DIALOG_1() {

    }

    private void set(CharacterExpression expression, String txt) {
        dialogToDisplay.add(new Dialog(expression, txt));
    }

    private static class Dialog {
        public CharacterExpression characterExpression;
        public String text;

        public Dialog(CharacterExpression characterAndExpression, String text) {
            this.characterExpression = characterAndExpression;
            this.text = text;
        }
    }

}
