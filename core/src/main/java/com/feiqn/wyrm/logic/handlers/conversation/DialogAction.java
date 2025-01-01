package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.utils.Array;

public class DialogAction {

    public enum Type {
        SLIDE_TO,
        BUMP_INTO,
        HOP,
        SHAKE,
        RUMBLE,
        RESET,
        FLIP,
    }

    public enum Speed {
        SUPER_FAST,
        FAST,
        NORMAL,
        SLOW,
        SUPER_SLOW,
    }

    private SpeakerPosition subject;
    private SpeakerPosition object;
    private Type verb;
    private boolean playParallel;
    private boolean loops;
    private Speed speed;

    public DialogAction() {
        this(SpeakerPosition.LEFT, SpeakerPosition.RIGHT, Type.SLIDE_TO);
    }

    public DialogAction(SpeakerPosition subject, Type verb) {
        this(subject, verb, null, Speed.NORMAL);
    }

    public DialogAction(SpeakerPosition subject, SpeakerPosition object, Type verb) {
        this(subject, verb, object, Speed.NORMAL);
    }

    public DialogAction(SpeakerPosition subject, Type verb, SpeakerPosition object, Speed speed) {
        this.subject = subject;
        this.object = object;
        this.speed = speed;
        this.verb = verb;
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

    public void setVerb(Type verb) {
        this.verb = verb;
    }

    public void playParallel() {
        playParallel = true;
    }

    public boolean parallel() {
        return playParallel;
    }

}
