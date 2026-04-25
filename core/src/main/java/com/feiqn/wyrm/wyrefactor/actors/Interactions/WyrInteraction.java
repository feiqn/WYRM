package com.feiqn.wyrm.wyrefactor.actors.Interactions;

import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;

/**@Non-compulsory for implementation.
 * Allows defining common tasks in the game world.
 * I.E., "Move there and attack that guy." or,
 * "swap that gem to the right."
 * @param <Actor>
 * @param <ID>
 */
public abstract class WyrInteraction<
        Actor extends WyrActor<?,?,?,?>,
        ID    extends Enum<?>
            > extends Subjectivity<Actor> implements Wyr {

    protected ID interactID;
    private boolean hidden = false;

    // Distance is an optional value when implementing Interactions.
    // Examples of use include:
    // (for an rpgrid) 0 = requires standing on top of parent, same tile.
    // (in a gem game) represent how far a gem can be swapped from.
    // etc., etc.
    protected int interactableDistance;

    protected WyrInteraction(Actor parent) { this.setSubject(parent); }

    protected WyrInteraction(Actor parent, Actor object, ID interactType) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.setObject(object);
    }

    protected WyrInteraction(Actor parent, Actor object, ID interactType, int interactableDistance) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.setObject(object);
        this.interactableDistance = interactableDistance;
    }

    public void hide() { hidden = true; }
    public void unhide() { hidden = false; }

    public ID getInteractType() { return interactID; }
    public boolean isHidden() { return hidden; }
    public boolean hasObject() { return object != null; }
    public int interactableRange() { return interactableDistance; }
}
