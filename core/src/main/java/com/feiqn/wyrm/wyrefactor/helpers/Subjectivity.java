package com.feiqn.wyrm.wyrefactor.helpers;

import com.badlogic.gdx.scenes.scene2d.Actor;
public abstract class Subjectivity<T extends Actor> {

    protected T subject;
    protected T object;

    public void setSubject(T actor) { this.subject = actor; }
    public void setObject (T actor) { this.object  = actor; }

    public T getSubject() { return subject; }
    public T getObject()  { return object;  }

    public String getSubjectName() { return subject.getName(); }
    public String getObjectName()  { return object.getName(); }

}
