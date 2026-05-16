package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid;

import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography.GridCutsceneChoreography;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.RPGridCutsceneShot;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

public abstract class RPGridCutscene extends WyrCutscene {

    public RPGridCutscene(CutsceneID id) {
        super(id);
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }

    /**
     * World Choreography:
     * These happen after removing the conversation window,
     * typically manipulating units, props, and world states.
     */
    protected RPGridCutsceneShot choreographAbility(RPGridActor actor, WyRPG.AbilityID abilityID) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(actor).useAbility(abilityID))));
    }
    protected RPGridCutsceneShot choreographUseProp(RPGridActor actor, RPGridProp prop) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(actor).useProp(prop))));
    }
    protected RPGridCutsceneShot choreographSpawn(RPGridActor actor) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(actor).spawn())));
    }
    protected RPGridCutsceneShot choreographDespawn(RPGridActor actor) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(actor).despawn())));
    }
    protected RPGridCutsceneShot choreographDeath(RPGridUnit unit) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(unit).kill())));
    }
    protected RPGridCutsceneShot choreographDestroy(RPGridProp prop) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(prop).destroy())));
    }
    protected RPGridCutsceneShot choreographMoveBy(RPGridActor actor, float x, float y) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(actor).moveBy(x,y))));
    }
    protected RPGridCutsceneShot choreographFollowPath(RPGridActor actor, GridPath path) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(actor).followPath(path))));
    }
    protected RPGridCutsceneShot choreographFocusActor(RPGridActor actor) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(actor).focus())));
    }
    protected RPGridCutsceneShot choreographFocusLocation(Vector2 location) {
        return (RPGridCutsceneShot) script(new RPGridCutsceneShot(new GridCutsceneChoreography(new RPGridInteraction(null).focus(location))));
    }

}
