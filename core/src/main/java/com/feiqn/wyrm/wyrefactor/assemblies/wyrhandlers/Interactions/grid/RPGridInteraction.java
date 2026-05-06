package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

public final class RPGridInteraction extends WyrInteraction<RPGridActor, RPGridInteraction.GridInteractID> {

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

    private GridPath path = null;
    private GridCutscene cutscene = null;


    public RPGridInteraction(RPGridActor parent) {
        super(parent);
    }
    public RPGridInteraction(RPGridActor parent, RPGridActor object, GridInteractID actType, int interactableRange) {
        super(parent, object, actType, interactableRange);
    }

    public RPGridInteraction examine(RPGridActor object) {
        this.interactID = GridInteractID.EXAMINE;
        this.setObject(object);
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction moveTo(GridPath path) {
        this.interactID = GridInteractID.MOVE;
        this.path = path;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction attack(RPGridUnit enemy, int range) {
        this.interactID = GridInteractID.ATTACK;
        this.setObject(enemy);
        this.interactableDistance = range; // range is attacker's reach
        return this;
    }
    public RPGridInteraction talkTo(RPGridActor object, GridCutscene scriptToTrigger) {
        this.setObject(object);
        this.interactID = GridInteractID.TALK;
        this.interactableDistance = 1;
        this.cutscene = scriptToTrigger;
        return this;
    }
    public RPGridInteraction moveThenAttack(RPGridUnit enemy, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(enemy);
        this.interactableDistance = 0;
        this.interactID = GridInteractID.MOVE_ATTACK;
        return this;
    }
    public RPGridInteraction moveThenWait(GridPath path) {
        this.path = path;
        this.interactID = GridInteractID.MOVE_WAIT;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction moveThenTalk(RPGridActor talkTo, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(talkTo);
        this.interactID = GridInteractID.MOVE_TALK;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction passPriority() {
        this.interactID = GridInteractID.WAIT;
        this.interactableDistance = 0;
        return this;
    }

    public GridPath getPath() { return path;}
    public GridCutscene getCutscene() { return cutscene; }

}
