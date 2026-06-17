package com.feiqn.wyrm.wyrefactor.helpers;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

public abstract class Subjectivity {

    protected WyrActor subject;
    protected WyrActor object;

    public void setSubject(WyrActor actor) { this.subject = actor; }
    public void setObject (WyrActor actor) { this.object  = actor; }

    public WyrActor getSubject() { return subject; }
    public WyrActor getObject()  { return object;  }

    public String getSubjectName() { return subject.getName(); }
    public String getObjectName()  { return object.getName(); }

}
