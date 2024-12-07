package com.feiqn.wyrm.logic.handlers.conversation;

public class DialogFrame {

    /**
     *  DialogFrames are lines in the script. <br>
     *  DialogFrameHandler is the script. <br>
     *  Conversation is the choreography. <br>
     *  ConversationHandler is the director.
     */

    private final CharacterExpression characterExpression;
    private final String text;
    private final SpeakerPosition position;
    private final Boolean facingLeft;
    private final Boolean autoplayNext;
    private final float progressiveDisplaySpeed;

    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition position) {
        this(characterAndExpression, text, position, false, .1f, false);
    }

    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition position, Boolean facingLeft) {
        this(characterAndExpression,text, position, facingLeft, .1f, false);
    }

    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition pos, Boolean facingLeft, float progressiveDisplaySpeed, Boolean autoNext) {
        this.characterExpression = characterAndExpression;
        this.text = text;
        this.position = pos;
        this.facingLeft = facingLeft;
        this.autoplayNext = autoNext;
        this.progressiveDisplaySpeed = progressiveDisplaySpeed;
    }

    public CharacterExpression getCharacterExpression() {
        return characterExpression;
    }
    public String getText() {
        return text;
    }
    public Boolean FacingLeft() {
        return facingLeft;
    }
    public Boolean autoplayNext() {
        return autoplayNext;
    }
    public SpeakerPosition getPosition() {
        return position;
    }
    public float getProgressiveDisplaySpeed() {
        return progressiveDisplaySpeed;
    }
}
