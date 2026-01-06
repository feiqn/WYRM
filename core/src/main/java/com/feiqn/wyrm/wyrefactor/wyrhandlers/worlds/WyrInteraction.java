package com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds;

import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;

public abstract class WyrInteraction extends Wyr {

    // Honestly don't know what best practice
    // says about making this one giant enum
    // or a bunch of smaller enums in discrete
    // classes.
    // Most likely the answer is the opposite
    // of whatever I go with.
    public enum InteractionType { // TODO: can probably abstract these to not be "grid_"... etc
        TALK,
        ATTACK,
        MOVE,

        PROP_ESCAPE, // objectives as props rather than tile types
        PROP_SEIZE,

        PROP_PILOT, // like a ballista, etc.

        PROP_OPEN, // i.e., a door
        PROP_LOOT, // a chest, a corpse
        PROP_DESTROY,
    }

    private final InteractionType interactType;

    private final WyrActor parent;

    private final WyrActor object;

    private final int interactableRange; // 0 = requires standing on top of parent, same tile.

    private boolean hidden = false;

    protected WyrInteraction(WyrType wyrType, WyrActor parent, WyrActor object, InteractionType interactType, int interactableRange) {
        super(wyrType);
        this.interactType = interactType;
        this.parent = parent;
        this.object = object;
        this.interactableRange = interactableRange;
    }

    public void hide() { hidden = true; }
    public void unhide() { hidden = false; }

    public InteractionType getInteractType() { return interactType; }
    public boolean isHidden() { return hidden; }
    public boolean hasObject() { return object != null; }
    protected WyrActor getParent() { return parent; }
    protected WyrActor getObject() { return object; }
    public int interactableRange() { return interactableRange; }
}
