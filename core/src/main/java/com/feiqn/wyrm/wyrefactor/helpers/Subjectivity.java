package com.feiqn.wyrm.wyrefactor.helpers;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

public abstract class Subjectivity {

    protected WyrActor subject;
    protected WyrActor object; // direct object
    protected WyrActor prepositional; // object of the preposition

    public void setSubject(WyrActor actor) { this.subject = actor; }
    public void setObject (WyrActor actor) { this.object  = actor; }
    public void setPrepositional(WyrActor actor) {this.prepositional = actor; }

    public WyrActor getPrepositional() { return prepositional; }
    public WyrActor getSubject() { return subject; }
    public WyrActor getObject()  { return object;  }

    public String getSubjectName() { return subject.getName(); }
    public String getObjectName()  { return object.getName(); }

}
