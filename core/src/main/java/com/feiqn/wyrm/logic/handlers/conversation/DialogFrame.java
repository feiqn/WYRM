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

    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition pos, Boolean facingLeft) {
        this.characterExpression = characterAndExpression;
        this.text = text;
        this.position = pos;
        this.facingLeft = facingLeft;
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
    public SpeakerPosition getPosition() {
        return position;
    }
}
