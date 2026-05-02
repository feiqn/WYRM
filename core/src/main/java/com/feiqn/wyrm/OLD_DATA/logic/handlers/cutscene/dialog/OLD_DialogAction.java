package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog;

import com.feiqn.wyrm.wyrefactor.helpers.Speed;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.Position;

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
    private Position subject;
    private Position object;
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
        this(Position.LEFT, Position.RIGHT, Type.SLIDE_TO);
    }

    public OLD_DialogAction(Position subject, Type verb) {
        this(subject, verb, null, Speed.NORMAL);
    }

    public OLD_DialogAction(Position subject, Position object, Type verb) {
        this(subject, verb, object, Speed.NORMAL);
    }

    public OLD_DialogAction(Position subject, Type verb, Position object) {
        this(subject, verb, object, Speed.NORMAL);
    }

    public OLD_DialogAction(Position subject, Type verb, Position object, Speed speed) {
        this.subject = subject;
        this.object = object;
        this.speed = speed;
        this.verb = verb;
    }

    public Position getObject() {
        return object;
    }

    public Position getSubject() {
        return subject;
    }

    public Type getVerb() {
        return verb;
    }

    public void setSubject(Position subject) {
        this.subject = subject;
    }

    public void setObject(Position object) {
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
