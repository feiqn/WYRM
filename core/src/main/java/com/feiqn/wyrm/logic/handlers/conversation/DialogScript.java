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
        set(CharacterExpression.LEIF_SMILING, "Hello!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_TALKING, "Thank you so much for taking a look at my game!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EMBARRASSED, "There's not really a whole lot to look at right now...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_HOPEFUL, "But despite humble appearances, this actually represents a huge [GOLD]milestone[] in progress!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EXCITED, "And don't get hung up on all the stole-", SpeakerPosition.LEFT, true, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_WINCING, "And don't get hung up on all the, er", SpeakerPosition.LEFT, true, true);
        lastSetFrame().setProgressiveDisplaySpeed(0); // causes text to display instantly rather than one character at a time
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_TALKING, "And don't get hung up on all the borrowed [GOLD]assets[]!", SpeakerPosition.LEFT, true);
        lastSetFrame().snapToIndex(31);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_SLY, "That's what we in the business call, \"[GOLD]Placeholder Art[].\"", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_SMILING, "Besides, before starting on this project, I knew [RED]hardly anything[] about programming; so having come this far is a really big deal to me!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_WORRIED, "Who cares if a more experienced programmer could have thrown together what I have here in a long weekend... (or if an AI could have done it in a few seconds)", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EXCITED, "What's important is all the [RED]c[ORANGE]o[YELLOW]o[GREEN]l [BLUE]s[PURPLE]h[]i[]t[][][][][][][][][][] I can do now!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EXCITED, "Like, how it sort of pauses, when there's like, a comma or something?", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_MANIACAL, "Or how I gaslit you a few frames ago? Or if the next word is reallyhonestlyjusttoolongtofitinthebox, it'll automatically start typing on the next line instead?", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EXCITED, "I put a lot of work into coding the logic for this progressive-display text module, and have been feeling very proud of my success!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_SMILING, "Truth be told, before creating this dialog module myself, I had assumed this is such a common game feature that someone would have already made a version of it publicly available.", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_THINKING, "I searched around on several occasions, and found similar implementations in other frameworks, but none for LibGDX which project this game is based on.", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EXCITED, "So, I set out to create it myself; and ended up having a lot of fun doing so!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_SMILING, "Feeling quite self-satisfied, I headed over to the LibGDX Discord server, to see if there would be an appropriate #channel to post my module for others to use as well!", SpeakerPosition.LEFT, true);
        // image panel right: libgdx discord
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EXCITED, "Software communism!", SpeakerPosition.LEFT, true);
        // single excited hop
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_WINCING, "I actually joined this Discord server a few years back, but like most popular servers it has an overwhelming amount of channels and topics, which all just looks like chaos to me!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_SMILING, "But I think we can hold our breath long enough to go in and make a post in an appropriate space!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_CURIOUS, "Now, let's see here...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // gif of scrolling down channel list

        set(CharacterExpression.LEIF_THINKING, "...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // focus on #libraries

        set(CharacterExpression.LEIF_WORRIED, "...!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // full image of libraries channel

        set(CharacterExpression.LEIF_WORRIED, "...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // focus on textra

        set(CharacterExpression.LEIF_DESPAIRING, "...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // textra display sample gif

        set(CharacterExpression.LEIF_DESPAIRING, " ", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // fade character away

        set(CharacterExpression.NONE, "...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("");
        // fade background to black

        set(CharacterExpression.NONE, "...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("");
        // fade image panel right away

        set(CharacterExpression.NONE, "...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("");

        //switch here to using textra instead of progressive
        set(CharacterExpression.LEIF_SMILING, "Okay, look, to be fair, I was an early adopter of Discord, like, [RED]10[] years ago.", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_EMBARRASSED, "I have been conditioned to expect every Discord server that I join to be an absolute chaotic mess of un-navigable, [GOLD]unhelpful[] nonsense.", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_ANNOYED, "I don't know when this became the default place to house and distribute [GOLD]important information[].", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_TALKING, "But, all in all, this is a good problem to have.", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.ANTAL_ENTHUSIASTIC, "It means now we can focus on this really cool [GOLD]cutscene engine[] instead!", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.ANTAL_ENTHUSIASTIC, "I bet no one has made a free module for that yet!", SpeakerPosition.RIGHT, true);

        set(CharacterExpression.LEIF_EXCITED, "That's right, [GOLD]Antal[]! What a lovely thing to point out!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_TALKING, "Before going off in the weeds with that redundant typing label, we've been excited to show off the first iteration of [GOLD]WYRFrame - Conversations[], a simple tool for cutscenes that is feature-robust, and easy to script!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_TALKING, "And since it's all in-house, any addition desired features can be implemented, to create exactly the scene in your mind!", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        set(CharacterExpression.LEIF_TALKING, "Features like, for example...", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // fade transition background to concert stage, fade in crowd cheering noises

        set(CharacterExpression.TEMP_BAND_GIRL, " ", SpeakerPosition.RIGHT_OF_CENTER, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");
        // fade add character image

        set(CharacterExpression.LEIF_EXCITED, "So, what do you say, babe?", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

        // FULLSCREEN anime beach proposal background
        set(CharacterExpression.LEIF_TALKING, "Will you make a video game with me?", SpeakerPosition.LEFT, true);
        lastSetFrame().setFocusedName("Robin Fire Emblem");

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

    private void setMultiple(SpeakerPosition focusedPosition, SpeakerPosition... positions) {
        // TODO: this ^ won't work. hashmap?
    }

    private void setAll(SpeakerPosition focusedPosition, String txt, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        setAll(focusedPosition, txt, "", farLeft, left, leftOfCenter, center, rightOfCenter, right, farRight);
    }

    private void setParallelActions(DialogAction... actions) {
        for(DialogAction action : actions) {

        }
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
