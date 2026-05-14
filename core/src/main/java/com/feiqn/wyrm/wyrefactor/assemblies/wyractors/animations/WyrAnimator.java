package com.feiqn.wyrm.wyrefactor.assemblies.wyractors.animations;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrAnimator implements Wyr {

    protected final WyrActor parent;
    protected Enum<?> state;

    protected MetaHandler h;

    public WyrAnimator(WyrActor parent) {
        this.parent = parent;
    }

    public void update() {}

    public void setState(Enum<?> state) {
        if(this.state == state) return;
        h.time().record(parent); // Time of last frame change.
        h.time().recordStateTime(parent); // Time of state change.
        this.state = state;
        // Child: call super then add specific functionality.
    }

    protected void generateAnimations() {
        Gdx.app.log("TODO", "sorry");
    }
    public Enum<?> getState() { return state; }
    protected WyrActor getParent() { return parent; }

}
