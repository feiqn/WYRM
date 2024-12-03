package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;

public class DialogFrameHandler {

    /**
     *  I have no idea what I am doing.
     */

    private final WYRMGame game;

    public enum FrameSet {
        DEBUG,
        STAGE_1A_DIALOG_1_LEIF_FLEEING_ALONE,
        STAGE_1A_DIALOG_2_ANTAL_PLEADING_FOR_HELP,
    }

    private final Array<DialogFrame> dialogToDisplay; // Add frames programmatically in order, start from index 0, remove as you go

    public DialogFrameHandler(WYRMGame game) {
        this.game = game;

        dialogToDisplay = new Array<>();
    }

    /**
     * clear out all frames from dialogToDisplay array
     */
    private void clearArray() {

    }

    /**
     * @return a static copy of the next set dialog frame, then removes frame from set list.
     */
    public DialogFrame nextFrame() {
        final DialogFrame returnFrame = new DialogFrame(dialogToDisplay.get(0).getCharacterExpression(), dialogToDisplay.get(0).getText(), dialogToDisplay.get(0).getPosition(), dialogToDisplay.get(0).FacingLeft());
        dialogToDisplay.removeIndex(0);
        return returnFrame;
    }

    /**
     * wrapper method for internal private function calls,
     * all of which programmatically create a scripted
     * list of dialog frames, and add frames in order to
     * display array.
     */
    public void setFrameSeries(FrameSet set) {
        clearArray();
        switch(set) {
            case DEBUG:
                setFrameSeries_DEBUG();
                break;
            case STAGE_1A_DIALOG_1_LEIF_FLEEING_ALONE:
                setFrameSeries_STAGE_1A_DIALOG_1();
                break;
        }
    }

    /**
     * debug conversation
     */
    private void setFrameSeries_DEBUG() {
        set(CharacterExpression.LEIF_SMILING, "Hello!", SpeakerPosition.CENTER_LEFT, false);

        set(CharacterExpression.LEIF_TALKING, "Thank you so much for taking a look at my game!", SpeakerPosition.CENTER_LEFT, false);

        set(CharacterExpression.LEIF_EMBARRASSED, "There's not really a whole lot to look at right now...", SpeakerPosition.CENTER_LEFT, false);

        set(CharacterExpression.LEIF_HOPEFUL, "But despite humble appearances, this actually represents a huge milestone in progress!", SpeakerPosition.CENTER_LEFT, false);



    }

    private void setFrameSeries_STAGE_1A_DIALOG_1() {
        // Leif Fleeing Alone

    }

    /**
     * simple convenience method for creating new dialog frames
     */
    private void set(CharacterExpression expression, String txt, SpeakerPosition pos, Boolean facingLeft) {
        dialogToDisplay.add(new DialogFrame(expression, txt, pos, facingLeft));
    }

}
