package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.campaign.DeveloperLand;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.unique.GU_Leif;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public final class GS_DEBUG extends GridScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void setup() {
        final GU_Leif testUnit = new GU_Leif(h);
        h.actors().placeActor(testUnit, 29, 22);
        h.conditions().declareUnit(testUnit);
        gameStage.addActor(testUnit);
    }

    @Override
    public void win() {

    }

}
