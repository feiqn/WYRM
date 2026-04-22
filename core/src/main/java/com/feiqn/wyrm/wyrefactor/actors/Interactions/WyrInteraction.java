package com.feiqn.wyrm.wyrefactor.actors.Interactions;

import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;

public abstract class WyrInteraction<
        Actor extends WyrActor<?,?>,
        E extends Enum<?>
            > extends Subjectivity<Actor> implements Wyr {

    protected E interactID;
    protected int interactableRange; // 0 = requires standing on top of parent, same tile.
    private boolean hidden = false;

    protected WyrInteraction(Actor parent) { this.setSubject(parent); }

    protected WyrInteraction(Actor parent, Actor object, E interactType, int interactableRange) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.setObject(object);
        this.interactableRange = interactableRange;
    }

    public void hide() { hidden = true; }
    public void unhide() { hidden = false; }

    public E getInteractType() { return interactID; }
    public boolean isHidden() { return hidden; }
    public boolean hasObject() { return object != null; }
    public int interactableRange() { return interactableRange; }
}
