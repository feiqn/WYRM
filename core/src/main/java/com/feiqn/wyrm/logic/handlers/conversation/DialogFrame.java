package com.feiqn.wyrm.logic.handlers.conversation;

import java.util.HashMap;

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

    private float progressiveDisplaySpeed;

    public DialogFrame() {
        initPositionMap();
        text = "";
        focusedName = "";
        focusedPosition = SpeakerPosition.CENTER;
        facingLeft = false;
        autoplayNext = false;
        complex = false;
        progressiveDisplaySpeed = .1f;
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
    public Boolean getComplex() {
        return complex;
    }
    public String getFocusedName() {
        return focusedName;
    }
}
