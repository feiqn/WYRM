package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.prefabs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.AbilityID;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.GameKit.RPG.InteractionType.MOVE_TO;

public final class Interactions {

    private Interactions() {}

    // TODO: pooling like Triggers

    public static WyrInteraction Interaction(Array<InteractionType> types, WyrActor actingActor, @Null WyrActor actedOnActor, @Null WyrActor prepositionalActor) {
        return null;
    }

    public static WyrInteraction PathToTile(WyrActor unitMoving, Vector2 destinationCoordinate) {
        return new WyrInteraction(unitMoving, MOVE_TO, 0).setCoordinate(destinationCoordinate);
    }

    public static WyrInteraction Attack(WyrActor unitAttacking, WyrActor beingAttacked) {
        return new WyrInteraction(unitAttacking).attack(beingAttacked, unitAttacking.getReach());
    }

    public static WyrInteraction FireArmament(WyrActor unitFiring, WyrActor propWithArmament, WyrActor targetOfFire) {
        return new WyrInteraction(unitFiring).fireArmament(propWithArmament, targetOfFire);
    }

    public static WyrInteraction Aim(WyrActor unitAiming, WyrActor.Prop propBeingAimed) {
        return new WyrInteraction(unitAiming).aim(propBeingAimed);
    }

    public static WyrInteraction Examine(WyrActor toExamine) {
        return new WyrInteraction(toExamine).examine();
    }

    public static WyrInteraction Mount(String subjectName) {
        return new WyrInteraction(subjectName).mount();
    }

    public static WyrInteraction Kill(String actorName) {
        return new WyrInteraction(actorName).kill();
    }

    public static WyrInteraction Spawn(WyrActor actor, Vector2 atCoordinate) {
        return new WyrInteraction(actor).spawn(atCoordinate);
    }

    public static WyrInteraction Despawn(String actorName) {
        return new WyrInteraction(actorName).despawn();
    }

    public static WyrInteraction Ability(String actorUsingAbility, AbilityID abilityID) {
        return new WyrInteraction(actorUsingAbility).useAbility(abilityID);
    }

    public static WyrInteraction Ability(String actorUsingAbility, AbilityID abilityID, String targetedActor) {
        return new WyrInteraction(actorUsingAbility).useAbility(abilityID, targetedActor);
    }

    public static WyrInteraction Focus(String actorToFocusOn) {
        return new WyrInteraction(actorToFocusOn).focus();
    }

    public static WyrInteraction Focus(Vector2 coordinate) {
        return new WyrInteraction("Leif").focus(coordinate);
    }

    public static WyrInteraction FollowPath(String actor, GridPath path) {
        return new WyrInteraction(actor).moveThenWait(path);
    }



}
