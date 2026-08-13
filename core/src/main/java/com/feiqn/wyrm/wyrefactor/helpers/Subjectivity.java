package com.feiqn.wyrm.wyrefactor.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

public abstract class Subjectivity implements WyrFrame {

    protected WyrActor subject = null;
    protected WyrActor object = null; // direct object
    protected WyrActor prepositional = null; // object of the preposition

    protected String subjectUID = null;
    protected String objectUID = null;
    protected String prepositionalUID = null;

    public void setSubject(WyrActor actor) {
        this.subject = actor;
        this.subjectUID = actor.getName();
    }
    public void setObject (WyrActor actor) {
        this.object  = actor;
        this.objectUID = actor.getName();
    }
    public void setPrepositional(WyrActor actor) {
        this.prepositional = actor;
        this.prepositionalUID = actor.getName();
    }

    public void setSubject(String subjectUID) { this.subjectUID = subjectUID; }
    public void setObject(String objectUID) { this.objectUID = objectUID; }
    public void setPrepositional(String prepositionalUID) { this.prepositionalUID = prepositionalUID; }

    public String getSubjectUID() { return subjectUID; }
    public String getObjectUID() { return objectUID; }
    public String getPrepositionalUID() { return prepositionalUID; }

    public @Null WyrActor getSubject() {
//        if(!hasSubject()) Gdx.app.log("subjectivity", "subject requested but no subject defined");
        return subject != null ? subject :
            handlers.register().getWyrActorFromMap(subjectUID);
    }
    public @Null WyrActor getObject()  {
//        if(!hasObject()) Gdx.app.log("subjectivity", "object requested but no object defined");
        return object != null ? object :
            handlers.register().getWyrActorFromMap(objectUID);
    }
    public @Null WyrActor getPrepositional() {
//        if(!hasPrepositional()) Gdx.app.log("subjectivity", "prepositional requested but no prepositional defined");
        return prepositional != null? prepositional :
            handlers.register().getWyrActorFromMap(prepositionalUID);
    }

    public boolean hasSubject() { return subject!= null || subjectUID != null; }
    public boolean hasObject() { return object != null || objectUID != null; }
    public boolean hasPrepositional() { return prepositional != null || prepositionalUID != null; }
}
