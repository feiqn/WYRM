package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pool;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.WyrTile;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AbilityID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType.*;

public class WyrInteraction extends Subjectivity implements Pool.Poolable{

    /** Interactions used by gameplay as well as scripted cutscenes.
     */

    protected InteractionType interactID = null;
    protected Vector2 associatedCoordinate = null;
    private boolean hidden = false;

    // TODO: WyrItem interactions

    protected int interactableDistance = -1; // zero means standing on same tile, negative means from anywhere.

    public WyrInteraction(WyrActor parent) {
        this.setSubject(parent);
    }

    public WyrInteraction(String subjectName) {
        this.subjectUID = subjectName;
    }

    public WyrInteraction(Name subjectName) {
        this.subjectUID = subjectName.toString();
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
    private AbilityID associatedAbility = null;

    public void hide()   { hidden = true; }
    public void unhide() { hidden = false; }

    public InteractionType getInteractType() { return interactID; }

    public boolean isHidden() { return hidden; }

    public int interactableRange() { return interactableDistance; }

    public WyrInteraction aim(String propUID) {
        this.interactID = PROP_AIM;
        this.interactableDistance = 1;
        this.objectUID = propUID;
        return this;
    }

    public WyrInteraction aim(WyrActor prop) {
        this.interactID = PROP_AIM;
        this.interactableDistance = 1;
        this.setObject(prop);
        return this;
    }

    public WyrInteraction fireArmament(WyrActor propWithArmament, WyrActor targetOfFire) {
        this.interactID = PROP_FIRE;
//        this.interactableDistance = 1;
        this.setObject(propWithArmament);
        this.setPrepositional(targetOfFire);
        return this;
    }
    public WyrInteraction fireArmament(String propWithArmamentUID, String targetOfFireUID) {
        this.interactID = PROP_FIRE;
//        this.interactableDistance = 1;
        this.objectUID = propWithArmamentUID;
        this.prepositionalUID = targetOfFireUID;
        return this;
    }

    public WyrInteraction examine() {
        this.interactID = InteractionType.EXAMINE;
        this.interactableDistance = -1;
        return this;
    }

    public WyrInteraction moveTo(WyrTile tile) {
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
        this.interactableDistance = enemy.getReach(); // range is attacker's reach
        return this;
    }
    public WyrInteraction moveThenAttack(WyrActor enemy, GridPath pathTo) {
        this.path = pathTo;
        this.setObject(enemy);
        this.interactableDistance = 0;
        this.interactID = MOVE_ATTACK;
        return this;
    }

    public WyrInteraction talkTo(WyrActor object, WyrCutscene scriptToTrigger) {
        this.setObject(object);
        this.interactID = TALK;
        this.interactableDistance = 1;
        this.cutscene = scriptToTrigger;
        return this;
    }
    public WyrInteraction followPath(GridPath path) {
        this.interactID = FOLLOW_PATH;
        this.path = path;
        return this;
    }
    public WyrInteraction passPriority() {
        this.interactID = WAIT;
        this.interactableDistance = 0;
        return this;
    }
    public WyrInteraction moveThenWait(GridPath path) {
        this.path = path;
        this.interactID = MOVE_TO;
        this.interactableDistance = 0;
        return this;
    }
    public WyrInteraction useProp(WyrActor prop) {
        this.interactID = PROP_USE;
        this.interactableDistance = 1;
        return this;
    }
    public WyrInteraction useAbility(AbilityID abilityID) {
        return useAbility(abilityID, "");
    }
    public WyrInteraction useAbility(AbilityID abilityID, @Null Name objectName) {
        return useAbility(abilityID, objectName.toString());
    }
    public WyrInteraction useAbility(AbilityID abilityID, @Null String objectUID) {
        this.interactID = ABILITY_USE;
        this.objectUID = objectUID;
        this.associatedAbility = abilityID;
        this.interactableDistance = 1; // TODO: ability reach
        return this;
    }
    public WyrInteraction spawn(Vector2 atCoordinate) {
        this.interactID = SPAWN;
        this.associatedCoordinate = atCoordinate;
        return this;
    }
    public WyrInteraction despawn() {
        this.interactID = DESPAWN;
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
    public WyrInteraction mount() {
        this.interactID = MOUNT;
        return this;
    }

    public WyrInteraction setPath(GridPath path) {
        this.path = path;
        return this;
    }

    public WyrInteraction setCoordinate(Vector2 coordinate) {
        this.associatedCoordinate = coordinate;
        return this;
    }

    public WyrInteraction setInteractableDistance(int distance) {
        this.interactableDistance = distance;
        return this;
    }

    public boolean hasPath() { return getPath() != null; }
    public @Null GridPath getPath() { return path; }
    public @Null Vector2 getCoordinate() { return associatedCoordinate; }
    public @Null WyrCutscene getCutscene() { return cutscene; }
    public @Null AbilityID getAbility() { return associatedAbility; }

    @Override
    public void reset() {

    }
}
