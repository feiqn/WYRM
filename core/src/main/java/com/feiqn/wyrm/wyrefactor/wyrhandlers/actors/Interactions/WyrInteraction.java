package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions;

import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.WyrActor;

public abstract class WyrInteraction<
        Actor extends WyrActor<?>
            > extends Subjectivity<Actor> implements Wyr {

    // Honestly don't know what best practice
    // says about making this one giant enum
    // or a bunch of smaller enums in discrete
    // classes.
    // Most likely the answer is the opposite
    // of whatever I go with.
    public enum InteractionType { // TODO: can probably abstract these to not be "grid_"... etc
        EXAMINE,

        TALK,
        ATTACK,
        MOVE,
        WAIT,

        MOVE_TALK,
        MOVE_WAIT,
        MOVE_ATTACK,

        PROP_ESCAPE, // objectives as props rather than tile types
        PROP_SEIZE,

        PROP_PILOT, // like a ballista, etc.

        PROP_OPEN, // i.e., a door
        PROP_LOOT, // a chest, a corpse
        PROP_DESTROY,
    }

    private final InteractionType interactType;

    private final int interactableRange; // 0 = requires standing on top of parent, same tile.

    private boolean hidden = false;

    protected WyrInteraction(Actor parent, Actor object, InteractionType interactType, int interactableRange) {
        this.interactType = interactType;
        this.setSubject(parent);
        this.setObject(object);
        this.interactableRange = interactableRange;
    }

    public void hide() { hidden = true; }
    public void unhide() { hidden = false; }

    public InteractionType getInteractType() { return interactType; }
    public boolean isHidden() { return hidden; }
    public boolean hasObject() { return object != null; }
    public int interactableRange() { return interactableRange; }
}
