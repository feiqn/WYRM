package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid;

import com.badlogic.gdx.ScreenAdapter;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.perGame.WYRM;

public abstract class GridCutscene extends WyrCutscene {

    public GridCutscene(WYRM.CutsceneID id) {
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
    protected void choreographAbility() {

    }
    protected void choreographUseProp() {

    }
    protected void choreographSpawn() {

    }
    protected void choreographDespawn() {

    }
    protected void choreographDeath() {

    }
    protected void choreographMoveBy() {

    }
    protected void choreographFollowPath() {

    }
    protected void choreographFocusActor() {

    }
    protected void choreographFocusLocation() {

    }

}
