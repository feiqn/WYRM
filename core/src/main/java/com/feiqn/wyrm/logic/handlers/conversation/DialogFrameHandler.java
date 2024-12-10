package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;

public class DialogFrameHandler {

    /**
     *  DialogFrames are lines in the script. <br>
     *  DialogFrameHandler is the script. <br>
     *  Conversation is the choreography. <br>
     *  ConversationHandler is the director.
     */

    private final WYRMGame game;

    private int frameIndex;

    public enum FrameSeries {
        DEBUG,

        STAGE_1A_DIALOG_1_LEIF_FLEEING_ALONE,
        STAGE_1A_DIALOG_2_ANTAL_PLEADING_FOR_HELP,
    }

    private final Array<DialogFrame> framesToDisplay; // Add frames programmatically in order, start from index 0, remove as you go

    public DialogFrameHandler(WYRMGame game) {
        this.game = game;

        framesToDisplay = new Array<>();
        frameIndex = 0;
    }

    /**
     * clear out all frames from dialogToDisplay array
     */
    private void clearArray() {
        for(DialogFrame frame : framesToDisplay) {
            framesToDisplay.removeValue(frame, true);
        }
        Gdx.app.log("cleared array", "array size: " + framesToDisplay.size); // DEBUG
    }

    /**
     * @return a static copy of the next set dialog frame, while advancing the index counter for next call.
     */
    public DialogFrame nextFrame() {
        /* We want to return the frame index we are currently on. For example, on
         * first call, the frameIndex is 0, and we want to display the first frame, index 0.
         * On index 0, this will return frame 0 and bump the index up to 1, and so on.
         */
        frameIndex++;
        return framesToDisplay.get(frameIndex - 1);
    }

    /**
     * deprecated feature, do not use. <br> <br>
     * new implementation returns only text from previous frames to build a log.
     * this was done due to lack of preservation of visual screen state when
     * flipping arbitrarily between frames. <br>
     * can be re-implemented later if desired. don't see much point right now.
     */
    public DialogFrame previousFrame() {
//        frameIndex--;
        return framesToDisplay.get(frameIndex);
    }

    /**
     * wrapper method for internal private function calls,
     * all of which programmatically create a scripted
     * list of dialog frames, and add frames in order to
     * display array.
     */
    public void setFrameSeries(FrameSeries set) {
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
        set(CharacterExpression.LEIF_SMILING, "Hello!", SpeakerPosition.LEFT, false);

        set(CharacterExpression.LEIF_TALKING, "Thank you so much for taking a look at my game!", SpeakerPosition.LEFT, false);

        set(CharacterExpression.LEIF_EMBARRASSED, "There's not really a whole lot to look at right now...", SpeakerPosition.LEFT, false);

        set(CharacterExpression.LEIF_HOPEFUL, "But despite humble appearances, this actually represents a huge [GOLD]milestone[] in progress!", SpeakerPosition.LEFT, false);



    }

    private void setFrameSeries_STAGE_1A_DIALOG_1() {
        // Leif Fleeing Alone

    }

    /**
     * simple convenience method for creating new dialog frames
     */
    private void set(CharacterExpression expression, String txt, SpeakerPosition pos, Boolean facingLeft) {
        framesToDisplay.add(new DialogFrame(expression, txt, pos, facingLeft));
    }

    public boolean continues() {
        return framesToDisplay.size > frameIndex;
    }

}
