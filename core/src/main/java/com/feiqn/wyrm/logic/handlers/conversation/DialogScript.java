package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;

public class DialogScript {

    private final WYRMGame game;

    private int frameIndex;

    public enum FrameSeries {
        DEBUG,

        STAGE_1A_DIALOG_1_LEIF_FLEEING_ALONE,
        STAGE_1A_DIALOG_2_ANTAL_PLEADING_FOR_HELP,
    }

    private final Array<DialogFrame> framesToDisplay; // Add frames programmatically in order, start from index 0, remove as you go

    public DialogScript(WYRMGame game) {
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
//        Gdx.app.log("cleared array", "array size: " + framesToDisplay.size); // DEBUG
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
        // TODO: S U B C L A S S E S
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
        set(CharacterExpression.LEIF_SMILING, "[[[GOLD][[H,[][][],[RED],e[]l,[GREEN]l,[BLUE]o,,,[][]![[[", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_TALKING, "Thank you so much for taking a look at my game!", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_EMBARRASSED, "There's not really a whole lot to look at right now...", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_HOPEFUL, "But despite humble appearances, this actually represents a huge [GOLD]milestone[] in progress!", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_EXCITED, "And don't get hung up on all the stole-", SpeakerPosition.LEFT, true, true);

        set(CharacterExpression.LEIF_WINCING, "And don't get hung up on all the, er", SpeakerPosition.LEFT, true, true);
        lastSetFrame().setProgressiveDisplaySpeed(0); // causes text to display instantly rather than one character at a time

        set(CharacterExpression.LEIF_TALKING, "And don't get hung up on all the borrowed artwork!", SpeakerPosition.LEFT, true);
        lastSetFrame().snapToIndex(31);

        set(CharacterExpression.LEIF_SLY, "That's what we in the business call \"[GOLD]Placeholder Art[]\".", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_SMILING, "Besides, before starting on this project, I knew [RED]hardly anything[] about programming; so having come this far is a really big deal to me!", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_WORRIED, "Who cares if a more experienced programmer could have thrown together what I have here in a long weekend... (or if an AI could have done it in a few seconds)", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_EXCITED, "What's important is all the cool shit I can do now!", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_EXCITED, "Like, how it sort of pauses, when there's like, a comma or something?", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_MANIACAL, "Or how I gaslit you a few frames ago? They don't sell that shit in a box, baby!", SpeakerPosition.LEFT, true);

        set(CharacterExpression.LEIF_EXCITED, "That shit is fucking [GREEN]cash[GOLD]!", SpeakerPosition.LEFT, true);

    }

    private void setFrameSeries_STAGE_1A_DIALOG_1() {
        // Leif Fleeing Alone

    }

    public boolean continues() {
        return framesToDisplay.size > frameIndex;
    }

    /**
     * convenience methods for creating new dialog frames
     */
    private void set(CharacterExpression expression, String txt, SpeakerPosition position) {
        set(expression, txt, "", position, false, false);
    }
    private void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft) {
        set(expression, txt, "", position, facingLeft, false);
    }
    private void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft, boolean autoNext) {
        set(expression, txt, "", position, facingLeft, autoNext);
    }

    private void set(CharacterExpression expression, String txt, String name, SpeakerPosition pos, boolean facingLeft, boolean autoAutoPlay) {
        final DialogFrame frame = new DialogFrame();

        frame.setText(txt);
        frame.setFocusedPosition(pos);
        frame.setFocusedExpression(expression);
        frame.setFacingLeft(facingLeft);
        frame.setAutoplayNext(autoAutoPlay);

        framesToDisplay.add(frame);
    }

    private void setMultiple(/*SpeakerPosition pos1, pos2... */) {

    }

    private void setAll(SpeakerPosition focusedPosition, String txt, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        setAll(focusedPosition, txt, "", farLeft, left, leftOfCenter, center, rightOfCenter, right, farRight);
    }

    private void setAll(SpeakerPosition focusedPosition, String txt, String name, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        final DialogFrame frame = new DialogFrame();

        frame.setComplex(true);
        frame.setFocusedPosition(focusedPosition);
        frame.setText(txt);
        frame.setFocusedName(name);

        switch(focusedPosition) {
            case FAR_LEFT:
                frame.setFocusedExpression(farLeft);
                break;
            case LEFT:
                frame.setFocusedExpression(left);
                break;
            case LEFT_OF_CENTER:
                frame.setFocusedExpression(leftOfCenter);
                break;
            case CENTER:
                frame.setFocusedExpression(center);
                break;
            case RIGHT_OF_CENTER:
                frame.setFocusedExpression(rightOfCenter);
                break;
            case RIGHT:
                frame.setFocusedExpression(right);
                break;
            case FAR_RIGHT:
                frame.setFocusedExpression(farRight);
                break;
        }

        frame.setExpressionAtPosition(farLeft,       SpeakerPosition.FAR_LEFT);
        frame.setExpressionAtPosition(left,          SpeakerPosition.LEFT);
        frame.setExpressionAtPosition(leftOfCenter,  SpeakerPosition.LEFT_OF_CENTER);
        frame.setExpressionAtPosition(center,        SpeakerPosition.CENTER);
        frame.setExpressionAtPosition(rightOfCenter, SpeakerPosition.RIGHT_OF_CENTER);
        frame.setExpressionAtPosition(right,         SpeakerPosition.RIGHT);
        frame.setExpressionAtPosition(rightOfCenter, SpeakerPosition.RIGHT_OF_CENTER);

        framesToDisplay.add(frame);
    }

    private DialogFrame lastSetFrame() {
        return framesToDisplay.get(framesToDisplay.size-1);
    }
}
