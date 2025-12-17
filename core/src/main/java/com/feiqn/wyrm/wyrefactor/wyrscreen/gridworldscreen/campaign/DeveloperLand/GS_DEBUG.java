package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.campaign.DeveloperLand;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.scripts.Scripts;
import com.feiqn.wyrm.models.unitdata.units.player.LeifUnitOLD;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public final class GS_DEBUG extends GridScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void setup() {
//        final LeifUnitOLD testUnit= new LeifUnitOLD(WYRMGame.root());
//        h.actors().placeActor(testUnit, 29, 22);
    }

    @Override
    public void win() {

    }

}
