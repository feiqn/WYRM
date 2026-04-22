package com.feiqn.wyrm.wyrefactor.actors.Interactions.grid;

import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

public final class GridInteraction extends WyrInteraction<RPGridActor, GridInteraction.GridInteractID> {

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

    public GridInteraction(RPGridActor parent) {
        super(parent);
    }
    public GridInteraction(RPGridActor parent, RPGridActor object, GridInteractID actType, int interactableRange) {
        super(parent, object, actType, interactableRange);
    }

    public GridInteraction examine(RPGridActor object) {
        this.interactID = GridInteractID.EXAMINE;
        this.setObject(object);
        this.interactableRange = 0;
        return this;
    }
    public GridInteraction moveTo(GridPath path) {
        this.interactID = GridInteractID.MOVE;
        this.path = path;
        this.interactableRange = 0;
        return this;
    }
    public GridInteraction attack(RPGridUnit enemy, int range) {
        this.interactID = GridInteractID.ATTACK;
        this.setObject(enemy);
        this.interactableRange = range; // range is attacker's reach
        return this;
    }
    public GridInteraction talkTo(RPGridActor object, GridCutscene scriptToTrigger) {
        this.setObject(object);
        this.interactID = GridInteractID.TALK;
        this.interactableRange = 1;
        this.cutscene = scriptToTrigger;
        return this;
    }
    public GridInteraction moveThenAttack(RPGridUnit enemy, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(enemy);
        this.interactableRange = 0;
        this.interactID = GridInteractID.MOVE_ATTACK;
        return this;
    }
    public GridInteraction moveThenWait(GridPath path) {
        this.path = path;
        this.interactID = GridInteractID.MOVE_WAIT;
        this.interactableRange = 0;
        return this;
    }
    public GridInteraction moveThenTalk(RPGridActor talkTo, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(talkTo);
        this.interactID = GridInteractID.MOVE_TALK;
        this.interactableRange = 0;
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
