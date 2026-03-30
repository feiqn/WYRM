package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.campaign.DeveloperLand;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.generic.GU_Soldier;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.unique.GU_Leif;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public final class GS_DEBUG extends GridScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void setup() {
        instantiateUnit(new GU_Leif(h), 29, 22,false);
        instantiateUnit(new GU_Soldier(h), 22, 21, true);
    }

    @Override
    public void win() {

    }

}
