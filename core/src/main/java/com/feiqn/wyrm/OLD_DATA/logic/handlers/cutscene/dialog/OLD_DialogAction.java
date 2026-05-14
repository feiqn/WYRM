package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog;

import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

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
    private Wyr.HorizontalPosition subject;
    private Wyr.HorizontalPosition object;
    private Type verb;
    private boolean playParallel;
    private boolean loops;
    private Wyr.Speed speed;
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
        this(Wyr.HorizontalPosition.LEFT, Wyr.HorizontalPosition.RIGHT, Type.SLIDE_TO);
    }

    public OLD_DialogAction(Wyr.HorizontalPosition subject, Type verb) {
        this(subject, verb, null, Wyr.Speed.NORMAL);
    }

    public OLD_DialogAction(Wyr.HorizontalPosition subject, Wyr.HorizontalPosition object, Type verb) {
        this(subject, verb, object, Wyr.Speed.NORMAL);
    }

    public OLD_DialogAction(Wyr.HorizontalPosition subject, Type verb, Wyr.HorizontalPosition object) {
        this(subject, verb, object, Wyr.Speed.NORMAL);
    }

    public OLD_DialogAction(Wyr.HorizontalPosition subject, Type verb, Wyr.HorizontalPosition object, Wyr.Speed speed) {
        this.subject = subject;
        this.object = object;
        this.speed = speed;
        this.verb = verb;
    }

    public Wyr.HorizontalPosition getObject() {
        return object;
    }

    public Wyr.HorizontalPosition getSubject() {
        return subject;
    }

    public Type getVerb() {
        return verb;
    }

    public void setSubject(Wyr.HorizontalPosition subject) {
        this.subject = subject;
    }

    public void setObject(Wyr.HorizontalPosition object) {
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
