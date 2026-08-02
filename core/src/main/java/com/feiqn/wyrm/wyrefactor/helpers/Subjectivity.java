package com.feiqn.wyrm.wyrefactor.helpers;

import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;

public abstract class Subjectivity {

    protected WyrActor subject = null;
    protected WyrActor object = null; // direct object
    protected WyrActor prepositional = null; // object of the preposition

    public void setSubject(WyrActor actor) { this.subject = actor; }
    public void setObject (WyrActor actor) { this.object  = actor; }
    public void setPrepositional(WyrActor actor) {this.prepositional = actor; }

    public @Null WyrActor getPrepositional() { return prepositional; }
    public @Null WyrActor getSubject() { return subject; }
    public @Null WyrActor getObject()  { return object;  }

    public @Null String getSubjectName() { return subject.getName(); }
    public @Null String getObjectName()  { return object.getName(); }

}
