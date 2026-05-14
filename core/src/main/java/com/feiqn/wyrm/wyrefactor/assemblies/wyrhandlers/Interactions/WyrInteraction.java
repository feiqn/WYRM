package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;

/** Allows defining common tasks in the game world.
 * I.E., "Move there and attack that guy." or,
 * "swap that gem to the right."
 */
public class WyrInteraction extends Subjectivity<WyrActor> implements Wyr {

    protected Enum<?> interactID;
    private boolean hidden = false;

    // Distance is an optional value when implementing Interactions.
    // Examples of use include:
    // (for an rpgrid) 0 = requires standing on top of parent, same tile.
    // (in a gem game) represent how far a gem can be swapped from.
    // etc., etc.
    protected int interactableDistance;

    protected WyrInteraction(WyrActor parent) { this.setSubject(parent); }

    protected WyrInteraction(WyrActor parent, WyrActor object, Enum<?> interactType) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.setObject(object);
    }

    protected WyrInteraction(WyrActor parent, WyrActor object, Enum<?> interactType, int interactableDistance) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.setObject(object);
        this.interactableDistance = interactableDistance;
    }

    public void hide()   { hidden = true; }
    public void unhide() { hidden = false; }

    public Enum<?> getInteractType()   { return interactID; }
    public boolean isHidden()          { return hidden; }
    public boolean hasObject()         { return object != null; }
    public int     interactableRange() { return interactableDistance; }
}
