package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Objects;

public class DialogFrame {

    /**
     *  DialogFrames are lines in the script. <br>
     *  DialogFrameHandler is the script. <br>
     *  Conversation is the choreography. <br>
     *  ConversationHandler is the director.
     */

    public enum SpecialEffect {
        SHAKE_PORTRAIT,
        SHAKE_SCREEN,
        FADE_IN,
        FADE_OUT,
    }

    private final HashMap<SpeakerPosition, CharacterExpression> positionsMap = new HashMap<>();

    private String text;
    private String focusedName;

    private SpeakerPosition focusedPosition;

    private Boolean facingLeft;
    private Boolean autoplayNext;
    private Boolean complex;
    private Boolean usesSpecialDialogActions;

    private float progressiveDisplaySpeed;

    private final Array<SpecialDialogAction> actions = new Array<>();

    public DialogFrame() {
        initPositionMap();
        text = "";
        focusedName = "";
        focusedPosition = SpeakerPosition.CENTER;
        facingLeft = false;
        autoplayNext = false;
        complex = false;
        progressiveDisplaySpeed = .1f;
        usesSpecialDialogActions = false;
    }

    private void initPositionMap() {
        positionsMap.put(SpeakerPosition.FAR_LEFT,        CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.LEFT,            CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.LEFT_OF_CENTER,  CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.CENTER,          CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.RIGHT_OF_CENTER, CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.RIGHT,           CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.FAR_RIGHT,       CharacterExpression.NONE);
    }

    public void addExpressionAtPosition(CharacterExpression expression, SpeakerPosition position) {
        positionsMap.put(position, expression);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFocusedName(String focusedName) {
        this.focusedName = focusedName;
    }

    public void setFocusedPosition(SpeakerPosition position) {
        this.focusedPosition = position;
    }

    public void setFocusedExpression(CharacterExpression expression) {
        positionsMap.put(focusedPosition, expression);
    }

    public void setExpressionAtPosition(CharacterExpression expression, SpeakerPosition position) {
        positionsMap.put(position, expression);
    }

    public void setAutoplayNext(Boolean autoplayNext) {
        this.autoplayNext = autoplayNext;
    }

    public void setComplex(Boolean complex) {
        this.complex = complex;
    }

    public void setFacingLeft(Boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public void setProgressiveDisplaySpeed(float progressiveDisplaySpeed) {
        this.progressiveDisplaySpeed = progressiveDisplaySpeed;
    }

    public void addSpecialAction(SpecialDialogAction action) {
        usesSpecialDialogActions = true;
        actions.add(action);
    }

    //    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition position) {
//        this(characterAndExpression, text, position, false, .1f, false);
//    }
//
//    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition position, Boolean facingLeft) {
//        this(characterAndExpression,text, position, facingLeft, .1f, false);
//    }
//
//    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition pos, Boolean facingLeft, float progressiveDisplaySpeed, Boolean autoNext) {
//        this.characterExpression = characterAndExpression;
//        this.text = text;
//        this.focusedPosition = pos;
//        this.facingLeft = facingLeft;
//        this.autoplayNext = autoNext;
//        this.progressiveDisplaySpeed = progressiveDisplaySpeed;
//    }

    public CharacterExpression getFocusedExpression() {
        return positionsMap.get(focusedPosition);
    }
    public String getText() {
        return text;
    }
    public Boolean FacingLeft() {
        return facingLeft;
    }
    public Boolean autoAutoPlay() { // quickly skip the frame without waiting for player input
        return autoplayNext;
    }
    public SpeakerPosition getFocusedPosition() {
        return focusedPosition;
    }
    public float getProgressiveDisplaySpeed() {
        return progressiveDisplaySpeed;
    }
    public Boolean isComplex() {
        return complex;
    }
    public String getFocusedName() {
        if(!Objects.equals(focusedName, "")) {
            return focusedName;
        } else {
            return deriveName(getFocusedExpression());
        }
    }
    public Array<SpecialDialogAction> getActions() {
        return actions;
    }
    public CharacterExpression getExpressionAtPosition(SpeakerPosition position) {
        return positionsMap.get(position);
    }

    private String deriveName(CharacterExpression expression) {
        switch(expression) {
            case LEIF_HOPEFUL:
            case LEIF_SMILING:
            case LEIF_TALKING:
            case LEIF_WORRIED:
            case LEIF_WOUNDED:
            case LEIF_PANICKED:
            case LEIF_EMBARRASSED:
            case LEIF_BADLY_WOUNDED:
                return "Leif";

            case ANTAL_EXHAUSTED:
            case ANTAL_WORK_FACE:
            case ANTAL_DEVASTATED:
            case ANTAL_EMBARRASSED:
            case ANTAL_ENTHUSIASTIC:
            case ANTAL_BADLY_WOUNDED:
                return "Antal";

            case NONE:
            default:
                return "";
        }
    }
}
