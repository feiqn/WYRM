package com.feiqn.wyrm.wyrefactor.actors.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.wyrefactor.actors.animations.grid.RPGridAnimator;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

public abstract class WyrAnimator<
        Actor      extends WyrActor<?,?,?,?>,
        MetaHandle extends MetaHandler<?,?,?,?,?,?,?,?,?,?>,
        State      extends Enum<?>
            > implements Wyr {

    protected final Actor parent;
    protected State state;

    protected MetaHandle h;

    public WyrAnimator(Actor parent) {
        this.parent = parent;
    }

    abstract public void update();


    public void setState(State state) {
        if(this.state == state) return;
        h.time().record(parent); // Time of last frame change.
        h.time().recordStateTime(parent); // Time of state change.
        this.state = state;
        // Child: call super then add specific functionality.
    }

    protected void generateAnimations() {
        Gdx.app.log("TODO", "sorry");
    }
    public State getState() { return state; }
    protected Actor getParent() { return parent; }



}
