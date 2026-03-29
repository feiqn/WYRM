package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog;

import com.feiqn.wyrm.wyrefactor.helpers.Speed;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.SpeakerPosition;

public class OLD_DialogAction {

    public enum Type {
        SLIDE_TO,
        BUMP_INTO,
        HOP,
        SHAKE,
        RUMBLE,
        RESET,
        FLIP,
        CHOREOGRAPHY,
        ARBITRARY_CODE,
    }
    private SpeakerPosition subject;
    private SpeakerPosition object;
    private Type verb;
    private boolean playParallel;
    private boolean loops;
    private Speed speed;
    private Runnable code;
    private OLD_CutsceneFrameChoreography choreography;

    public OLD_DialogAction(OLD_CutsceneFrameChoreography choreography) {
        this.verb = Type.CHOREOGRAPHY;
        this.choreography = choreography;
    }

    public OLD_DialogAction(Runnable arbitraryCode) {
        this.verb = Type.ARBITRARY_CODE;
        this.code = arbitraryCode;
    }

    public OLD_DialogAction() {
        this(SpeakerPosition.LEFT, SpeakerPosition.RIGHT, Type.SLIDE_TO);
    }

    public OLD_DialogAction(SpeakerPosition subject, Type verb) {
        this(subject, verb, null, Speed.NORMAL);
    }

    public OLD_DialogAction(SpeakerPosition subject, SpeakerPosition object, Type verb) {
        this(subject, verb, object, Speed.NORMAL);
    }

    public OLD_DialogAction(SpeakerPosition subject, Type verb, SpeakerPosition object) {
        this(subject, verb, object, Speed.NORMAL);
    }

    public OLD_DialogAction(SpeakerPosition subject, Type verb, SpeakerPosition object, Speed speed) {
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

    public OLD_CutsceneFrameChoreography getChoreography() {
        return choreography;
    }

    public Runnable getCode() {
        if(code != null) return  code;
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }

}
