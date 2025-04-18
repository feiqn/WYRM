package com.feiqn.wyrm.logic.handlers.conversation.dialog;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.models.mapdata.tiledata.LogicalTile;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import static com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogAction.Type.*;
import static com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogFrame.Background_ID.*;
import static com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition.*;

public class DialogScript {

    protected int frameIndex;

    protected Array<DialogFrame> framesToDisplay; // Add frames programmatically in order, start from index 0, remove as you go

    public DialogScript() {
        framesToDisplay = new Array<>();
        frameIndex = 0;
        setSeries();
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
        return framesToDisplay.get(frameIndex);
    }

    /**
     * debug conversation
     */
    protected void setSeries() {

//        set(TEMP_KAI, "Hello!", FAR_LEFT);
//        set(TEMP_KAI, "Hello!", LEFT);
//        set(TEMP_KAI, "Hello!", LEFT_OF_CENTER);
//        set(TEMP_KAI, "Hello!", CENTER);
//        set(TEMP_KAI, "Hello!", RIGHT_OF_CENTER);
//        set(TEMP_KAI, "Hello!", RIGHT);
//        set(TEMP_KAI, "Hello!", FAR_RIGHT);

        set(LEIF_SMILING, "Hello!", LEFT);
        lastFrame().setFocusedName("Robin Fire Emblem");

        set(LEIF_TALKING, "Thank you so much for taking a look at my game!", LEFT);
        lastFrame().setFocusedName("Robin Fire Emblem");

        set(LEIF_EMBARRASSED, "There's not really a whole lot to look at right now...", LEFT);
        lastFrame().setFocusedName("Robin Fire Emblem");
        lastFrame().setBackground(BLACK);

        set(LEIF_HOPEFUL, "But despite humble appearances, this actually represents a huge [GOLD]milestone[] in progress!", LEFT);
        lastFrame().setFocusedName("Robin Fire Emblem");
        lastFrame().setBackground(REMOVE);

        set(LEIF_EXCITED, "And don't get hung up on all the stole-", LEFT);
        lastFrame().setAutoplayNext(true);
        lastFrame().setFocusedName("Robin Fire Emblem");

        set(LEIF_WINCING, "And don't get hung up on all the, er", LEFT);
        lastFrame().setProgressiveDisplaySpeed(0); // causes text to display instantly rather than one character at a time
        lastFrame().setAutoplayNext(true);
        lastFrame().setFocusedName("Robin Fire Emblem");

        set(LEIF_TALKING, "And don't get hung up on all the borrowed [GOLD]assets[]!", LEFT);
        lastFrame().snapToIndex(31);
        lastFrame().setFocusedName("Robin Fire Emblem");

        set(LEIF_SLY, "That's what we in the business call, \"[GOLD]Placeholder Art[].\"", LEFT);
        lastFrame().setFocusedName("Robin Fire Emblem");
//
//        set(LEIF_SMILING, "Besides, before starting on this project, I knew [RED]hardly anything[] about programming; so having come this far is a really big deal to me!", LEFT);
//        lastFrame().setFocusedName("Robin Fire Emblem");
//
//        set(LEIF_WORRIED, "Who cares if a more experienced programmer could have thrown together what I have here in a long weekend... (or if an AI could have done it in a few seconds)", LEFT);
//        lastFrame().setFocusedName("Robin Fire Emblem");
//
//        set(LEIF_EXCITED, "What's important is all the [RED]c[ORANGE]o[YELLOW]o[GREEN]l [BLUE]s[PURPLE]h[]i[]t[][][][][][][][][][] I can do now!", LEFT);
//        lastFrame().setFocusedName("Robin Fire Emblem");
//
//        set(LEIF_EXCITED, "Like, how it sort of pauses, when there's like, a comma or something?", LEFT);
//        lastFrame().setFocusedName("Robin Fire Emblem");
//
//        set(LEIF_MANIACAL, "Or how I gaslit you a few frames ago? Or if the next word is justtoolongtofitinthebox, it'll automatically start typing on the next line instead?", LEFT);
//        lastFrame().setFocusedName("Robin Fire Emblem");
//
//        set(LEIF_EXCITED, "I can slide around and hop and spin! ...okay, sort of... but you get the idea.", LEFT);
//        lastFrame().addDialogAction(new DialogAction(LEFT, SLIDE_TO, RIGHT));
//        lastFrame().addDialogAction(new DialogAction(LEFT, HOP));
//        lastFrame().addDialogAction(new DialogAction(LEFT, FLIP));
//        lastFrame().addDialogAction(new DialogAction(LEFT, SLIDE_TO, LEFT));
//        lastFrame().addDialogAction(new DialogAction(LEFT, FLIP));
//
//        set(LEIF_HOPEFUL, "I hope you can appreciate the passion I have been weaving into this project. There's a long way to go, but each small step brings us closer to completion.");
    }

    public boolean continues() {
        return framesToDisplay.size > frameIndex;
    }

    /**
     * convenience methods for creating new dialog frames
     */
    private void setComplexAction(Array<Action> actions) {

    }
    private void defineNextAction(Action action) {

    }
    protected void choreographLinger() {
        final DialogFrame frame = new DialogFrame();
        frame.choreograph(new DialogChoreography(DialogChoreography.Type.LINGER));
        framesToDisplay.add(frame);

    }
    protected void choreographUseAbility(SimpleUnit subject, CombatHandler.Abilities ability, SimpleUnit target) {
        // TODO
    }
    protected void choreographSpawn(SimpleUnit subject, LogicalTile spawnPoint) {

    }
    protected void choreographMoveTo(SimpleUnit subject, LogicalTile destination) {
        final DialogFrame frame = new DialogFrame();

        DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.MOVE);

        choreography.setSubject(subject);
        choreography.setLocation(destination);

        frame.choreograph(choreography);

        framesToDisplay.add(frame);
    }
    protected void choreographFocusCamera(SimpleUnit focusCamera) {
        final DialogFrame frame = new DialogFrame();

        DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.CENTER_CAMERA);
        choreography.setSubject(focusCamera);

        frame.choreograph(choreography);

        framesToDisplay.add(frame);
    }
    protected void set(CharacterExpression expression, String txt) {
        set(expression, txt, LEFT);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position) {
        set(expression, txt, "", position, false, false);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft) {
        set(expression, txt, "", position, facingLeft, false);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft, boolean autoNext) {
        set(expression, txt, "", position, facingLeft, autoNext);
    }

    protected void set(CharacterExpression expression, String txt, String name, SpeakerPosition pos, boolean facingLeft, boolean autoAutoPlay) {
        final DialogFrame frame = new DialogFrame();

        frame.setText(txt);
        frame.setFocusedPosition(pos);
        frame.setFocusedExpression(expression);
        frame.setFacingLeft(facingLeft);
        frame.setAutoplayNext(autoAutoPlay);

        framesToDisplay.add(frame);
    }



    protected void setMultiple(SpeakerPosition focusedPosition, SpeakerPosition... positions) {
        // TODO: this ^ won't work. hashmap?
    }

    protected void setAll(SpeakerPosition focusedPosition, String txt, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        setAll(focusedPosition, txt, "", farLeft, left, leftOfCenter, center, rightOfCenter, right, farRight);
    }

    protected void setAll(SpeakerPosition focusedPosition, String txt, String name, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
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

        frame.setExpressionAtPosition(farLeft,       FAR_LEFT);
        frame.setExpressionAtPosition(left,          LEFT);
        frame.setExpressionAtPosition(leftOfCenter,  LEFT_OF_CENTER);
        frame.setExpressionAtPosition(center,        CENTER);
        frame.setExpressionAtPosition(rightOfCenter, RIGHT_OF_CENTER);
        frame.setExpressionAtPosition(right,         RIGHT);
        frame.setExpressionAtPosition(rightOfCenter, RIGHT_OF_CENTER);

        framesToDisplay.add(frame);
    }

    protected DialogFrame lastFrame() {
        return framesToDisplay.get(framesToDisplay.size-1);
    }

    private void setParallelActions(DialogAction... actions) {
//        ParallelAction pAct = new ParallelAction();
        for(DialogAction action : actions) {

        }
    }
}
