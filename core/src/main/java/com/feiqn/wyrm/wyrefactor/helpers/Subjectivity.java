package com.feiqn.wyrm.wyrefactor.helpers;

import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;

public abstract class Subjectivity {

    protected WyrActor subject = null;
    protected WyrActor object = null; // direct object
    protected WyrActor prepositional = null; // object of the preposition

    protected String subjectUID = null;
    protected String objectUID = null;
    protected String prepositionalUID = null;

    public void setSubject(WyrActor actor) { this.subject = actor; }
    public void setObject (WyrActor actor) { this.object  = actor; }
    public void setPrepositional(WyrActor actor) {this.prepositional = actor; }

    public void setSubjectUID(String subjectUID) { this.subjectUID = subjectUID; }
    public void setObjectUID(String objectUID) { this.objectUID = objectUID; }
    public void setPrepositionalUID(String prepositionalUID) { this.prepositionalUID = prepositionalUID; }

    public String getSubjectUID() { return subjectUID; }
    public String getObjectUID() { return objectUID; }
    public String getPrepositionalUID() { return prepositionalUID; }

    public @Null WyrActor getPrepositional() { return prepositional; }
    public @Null WyrActor getSubject() { return subject; }
    public @Null WyrActor getObject()  { return object;  }

}
