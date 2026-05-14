package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid;

import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.GridCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;

public final class RPGridInteraction extends WyrInteraction {

    /** Interactions used by gameplay as well as scripted cutscenes.
     */
    public enum RPGridInteractType {
        EXAMINE,

        SPAWN_UNIT,
        SPAWN_PROP,
        SPAWN_BULLET,

        DESPAWN_UNIT,
        DESPAWN_PROP,
        DESPAWN_BULLET,

        CAMERA_TO_UNIT,
        CAMERA_TO_TILE,

        UNIT_DEATH,

        TALK,
        ATTACK,
        MOVE,
        WAIT,

        MOVE_TALK,
        MOVE_ATTACK,
        MOVE_WAIT,

        ABILITY_USE,

        PROP_ESCAPE, // objectives as props rather than tile types
        PROP_SEIZE,

        PROP_USE,
        PROP_PILOT, // like a ballista, etc.

        PROP_OPEN, // i.e., a door
        PROP_LOOT, // a chest, a corpse
        PROP_DESTROY, // break a wall or object

        CUTSCENE_QUEUE,
        CUTSCENE_END,
    }

    private GridPath     path     = null;
    private GridCutscene cutscene = null;

    public RPGridInteraction(RPGridActor parent) {
        super(parent);
    }
    public RPGridInteraction(RPGridActor parent, RPGridActor object, RPGridInteractType actType, int interactableRange) {
        super(parent, object, actType, interactableRange);
    }

    public RPGridInteraction examine(RPGridActor object) {
        this.interactID = RPGridInteractType.EXAMINE;
        this.setObject(object);
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction moveTo(GridPath path) {
        this.interactID = RPGridInteractType.MOVE;
        this.path = path;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction attack(RPGridUnit enemy, int range) {
        this.interactID = RPGridInteractType.ATTACK;
        this.setObject(enemy);
        this.interactableDistance = range; // range is attacker's reach
        return this;
    }
    public RPGridInteraction talkTo(RPGridActor object, GridCutscene scriptToTrigger) {
        this.setObject(object);
        this.interactID = RPGridInteractType.TALK;
        this.interactableDistance = 1;
        this.cutscene = scriptToTrigger;
        return this;
    }
    public RPGridInteraction moveThenAttack(RPGridUnit enemy, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(enemy);
        this.interactableDistance = 0;
        this.interactID = RPGridInteractType.MOVE_ATTACK;
        return this;
    }
    public RPGridInteraction moveThenWait(GridPath path) {
        this.path = path;
        this.interactID = RPGridInteractType.MOVE_WAIT;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction moveThenTalk(RPGridActor talkTo, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(talkTo);
        this.interactID = RPGridInteractType.MOVE_TALK;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction passPriority() {
        this.interactID = RPGridInteractType.WAIT;
        this.interactableDistance = 0;
        return this;
    }

    public GridPath getPath() { return path;}
    public GridCutscene getCutscene() { return cutscene; }

    @Override
    public RPGridActor getSubject() {
        assert super.getSubject() instanceof RPGridActor;
        return (RPGridActor)super.getSubject();
    }
    @Override
    public RPGridActor getObject() {
        assert super.getObject() instanceof RPGridActor;
        return (RPGridActor)super.getObject();
    }
    @Override
    public RPGridInteractType getInteractType() {
        return (RPGridInteractType) interactID;
    }

}
