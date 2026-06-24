package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType.*;

public class WyrInteraction extends Subjectivity {

    /** Interactions used by gameplay as well as scripted cutscenes.
     */

    protected InteractionType interactID;
    protected Vector2 associatedCoordinate = null;
    private boolean hidden = false;

    protected int interactableDistance = -1; // zero means standing on same tile, negative means from anywhere.

    public WyrInteraction(WyrActor parent) {
        this.setSubject(parent);
    }

    public WyrInteraction(WyrActor parent, InteractionType interactType, int interactableDistance) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.interactableDistance = interactableDistance;
    }

    public WyrInteraction(WyrActor parent, WyrActor object, InteractionType interactType) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.setObject(object);
    }

    public WyrInteraction(WyrActor parent, WyrActor object, InteractionType interactType, int interactableDistance) {
        this.interactID = interactType;
        this.setSubject(parent);
        this.setObject(object);
        this.interactableDistance = interactableDistance;
    }

    private GridPath path = null;
    private WyrCutscene cutscene = null;
    private WyrFrame.GameKit.RPG.AbilityID associatedAbility = null;

    public void hide()   { hidden = true; }
    public void unhide() { hidden = false; }

    public InteractionType getInteractType() { return interactID; }
    public boolean isHidden()          { return hidden; }
    public boolean hasObject()         { return object != null; }
    public int     interactableRange() { return interactableDistance; }

    public WyrInteraction aim(WyrActor.Prop prop) {
        this.interactID = PROP_AIM;
        this.interactableDistance = 1;
        this.setObject(prop);
        return this;
    }
    public WyrInteraction fireArmament(WyrActor.Prop propWithArmament, WyrActor targetOfFire) {
        this.interactID = PROP_FIRE;
//        this.interactableDistance = 1;
        this.setObject(propWithArmament);
        this.setPrepositional(targetOfFire);
        return this;
    }
    public WyrInteraction examine() {
        this.interactID = InteractionType.EXAMINE;
        this.interactableDistance = 0;
        return this;
    }
    public WyrInteraction moveTo(RPGridTile tile) {
        this.interactID = MOVE_BY;
        this.associatedCoordinate = tile.getCoordinates();
        this.interactableDistance = 0;
        return this;
    }
    public WyrInteraction moveBy(float x, float y) {
        this.interactID = MOVE_BY;
        this.associatedCoordinate = new Vector2(x,y);
        return this;
    }
    public WyrInteraction attack(WyrActor enemy, int range) {
        this.interactID = ATTACK;
        this.setObject(enemy);
        this.interactableDistance = range; // range is attacker's reach
        return this;
    }
    public WyrInteraction talkTo(WyrActor object, WyrCutscene scriptToTrigger) {
        this.setObject(object);
        this.interactID = TALK;
        this.interactableDistance = 1;
        this.cutscene = scriptToTrigger;
        return this;
    }
    public WyrInteraction moveThenAttack(WyrActor enemy, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(enemy);
        this.interactableDistance = 0;
        this.interactID = MOVE_ATTACK;
        return this;
    }
    public WyrInteraction moveThenWait(GridPath path) {
        this.path = path;
        this.interactID = MOVE_WAIT;
        this.interactableDistance = 0;
        return this;
    }
    public WyrInteraction moveThenTalk(WyrActor talkTo, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(talkTo);
        this.interactID = MOVE_TALK;
        this.interactableDistance = 0;
        return this;
    }
    public WyrInteraction followPath(GridPath path) {
        this.interactID = MOVE_ALONG_PATH;
        this.path = path;
        return this;
    }
    public WyrInteraction passPriority() {
        this.interactID = WAIT;
        this.interactableDistance = 0;
        return this;
    }
    public WyrInteraction useProp(WyrActor prop) {
        this.interactID = PROP_USE;
        this.interactableDistance = 1;
        return this;
    }
    public WyrInteraction useAbility(WyrFrame.GameKit.RPG.AbilityID abilityID) {
        this.interactID = ABILITY_USE;
        this.associatedAbility = abilityID;
        this.interactableDistance = 1; // TODO: ability reach
        return this;
    }
    public WyrInteraction spawn() {
        switch(getSubject().getActorType()) {
            case ENTITY:
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
    public WyrInteraction despawn() {
        switch(getSubject().getActorType()) {
            case ENTITY:
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
    public WyrInteraction kill() {
        this.interactID = UNIT_DEATH;
        return this;
    }
    public WyrInteraction destroy() {
        this.interactID = PROP_DESTROY;
        return this;
    }
    public WyrInteraction focus() {
        this.interactID = CAMERA_TO_ACTOR;
        return this;
    }
    public WyrInteraction focus(Vector2 location) {
        this.interactID = CAMERA_TO_TILE;
        this.associatedCoordinate = location;
        return this;
    }

    public GridPath getPath() { return path;}
    public WyrCutscene getCutscene() { return cutscene; }
    public WyrFrame.GameKit.RPG.AbilityID getAbility() { return associatedAbility; }


}
