package com.feiqn.wyrm.logic.handlers.conversation;

public class SpecialDialogAction {

    public enum Type {
        SLIDE,
        BUMP_INTO,
    }

    private SpeakerPosition subject;
    private SpeakerPosition object;
    private Type verb;

    public SpecialDialogAction() {

    }

    public SpeakerPosition getObject() {
        return object;
    }

    public SpeakerPosition getSubject() {
        return subject;
    }

    public Type getVerb() {
        return verb;
    }

    public void setSubject(SpeakerPosition subject) {
        this.subject = subject;
    }

    public void setObject(SpeakerPosition object) {
        this.object = object;
    }

    public void setVery(Type verb) {
        this.verb = verb;
    }

}
