package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.RPGridCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.tiles.GridTile;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction.RPGridInteractType.*;

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

        CAMERA_TO_ACTOR,
        CAMERA_TO_TILE,

        UNIT_DEATH,

        TALK,
        ATTACK,
        MOVE_BY,
        MOVE_TO,
        MOVE_ALONG_PATH,
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

    private GridPath        path              = null;
    private RPGridCutscene cutscene          = null;
    private WyRPG.AbilityID associatedAbility = null;

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
    public RPGridInteraction moveTo(GridTile tile) {
        this.interactID = RPGridInteractType.MOVE_BY;
        this.associatedCoordinate = tile.getCoordinates();
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction moveBy(float x, float y) {
        this.interactID = MOVE_BY;
        this.associatedCoordinate = new Vector2(x,y);
        return this;
    }
    public RPGridInteraction attack(RPGridUnit enemy, int range) {
        this.interactID = ATTACK;
        this.setObject(enemy);
        this.interactableDistance = range; // range is attacker's reach
        return this;
    }
    public RPGridInteraction talkTo(RPGridActor object, RPGridCutscene scriptToTrigger) {
        this.setObject(object);
        this.interactID = TALK;
        this.interactableDistance = 1;
        this.cutscene = scriptToTrigger;
        return this;
    }
    public RPGridInteraction moveThenAttack(RPGridUnit enemy, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(enemy);
        this.interactableDistance = 0;
        this.interactID = MOVE_ATTACK;
        return this;
    }
    public RPGridInteraction moveThenWait(GridPath path) {
        this.path = path;
        this.interactID = MOVE_WAIT;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction moveThenTalk(RPGridActor talkTo, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(talkTo);
        this.interactID = MOVE_TALK;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction followPath(GridPath path) {
        this.interactID = MOVE_ALONG_PATH;
        this.path = path;
        return this;
    }
    public RPGridInteraction passPriority() {
        this.interactID = WAIT;
        this.interactableDistance = 0;
        return this;
    }
    public RPGridInteraction useProp(RPGridProp prop) {
        this.interactID = PROP_USE;
        this.interactableDistance = 1;
        return this;
    }
    public RPGridInteraction useAbility(WyRPG.AbilityID abilityID) {
        this.interactID = ABILITY_USE;
        this.associatedAbility = abilityID;
        this.interactableDistance = 1; // TODO: ability reach
        return this;
    }
    public RPGridInteraction spawn() {
        switch(getSubject().getActorType()) {
            case UNIT:
                this.interactID = SPAWN_UNIT;
                break;
            case PROP:
                this.interactID = SPAWN_PROP;
                break;
            default:
                break;
        }
        return this;
    }
    public RPGridInteraction despawn() {
        switch(getSubject().getActorType()) {
            case UNIT:
                this.interactID = DESPAWN_UNIT;
                break;
            case PROP:
                this.interactID = DESPAWN_PROP;
                break;
            default:
                break;
        }
        return this;
    }
    public RPGridInteraction kill() {
        this.interactID = UNIT_DEATH;
        return this;
    }
    public RPGridInteraction destroy() {
        this.interactID = PROP_DESTROY;
        return this;
    }
    public RPGridInteraction focus() {
        this.interactID = CAMERA_TO_ACTOR;
        return this;
    }
    public RPGridInteraction focus(Vector2 location) {
        this.interactID = CAMERA_TO_TILE;
        this.associatedCoordinate = location;
        return this;
    }

    public GridPath getPath() { return path;}
    public RPGridCutscene getCutscene() { return cutscene; }
    public WyRPG.AbilityID getAbility() { return associatedAbility; }

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
