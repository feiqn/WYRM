package com.feiqn.wyrm.wyrefactor.helpers;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class Subjectivity<T extends WyrActor> {

    protected T subject;
    protected T object;

    protected void setSubject(T actor) { this.subject = actor; }
    protected void setObject(T actor) { this.object = actor; }

    public T getSubject() { return subject; }
    public T getObject() { return object; }

}
