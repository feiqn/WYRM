package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.grid;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

public final class GridInteraction extends WyrInteraction<GridActor, GridInteraction.GridInteractID> {

    private GridPath path;
    private GridCutscene cutscene;

    public enum GridInteractID {
        EXAMINE,

        TALK,
        ATTACK,
        MOVE,
        WAIT,

        MOVE_TALK,
        MOVE_ATTACK,
        MOVE_WAIT,

        PROP_ESCAPE, // objectives as props rather than tile types
        PROP_SEIZE,

        PROP_PILOT, // like a ballista, etc.

        PROP_OPEN, // i.e., a door
        PROP_LOOT, // a chest, a corpse
        PROP_DESTROY,
    }

    public GridInteraction(GridActor parent) {
        super(parent);
    }
    public GridInteraction(GridActor parent, GridActor object, GridInteractID actType, int interactableRange) {
        super(parent, object, actType, interactableRange);
    }

    public GridInteraction moveThenWait(GridPath path) {
        this.path = path;
        this.interactID = GridInteractID.MOVE_WAIT;
        this.interactableRange = 0;
        return this;
    }
    public GridInteraction talkTo(GridActor object, GridCutscene scriptToTrigger) {
        this.setObject(object);
        this.interactID = GridInteractID.TALK;
        this.interactableRange = 1;
        this.cutscene = scriptToTrigger;
        return this;
    }

    public GridInteraction passPriority() {
        this.interactID = GridInteractID.WAIT;
        this.interactableRange = 0;
        return this;
    }

    public GridPath getPath() { return path;}
    public GridCutscene getCutscene() { return cutscene; }

}
