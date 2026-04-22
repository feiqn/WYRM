package com.feiqn.wyrm.wyrefactor.wyrscreen.gridworld.campaigns.DeveloperLand;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.generic.GU_Soldier;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.named.GU_Leif;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworld.RPGridScreen;

public final class GS_DEBUG extends RPGridScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void setup() {
        instantiateUnit(new GU_Leif(h), 29, 22);
        instantiateUnit(new GU_Soldier(h), 22, 21);
        instantiateUnit(new GU_Soldier(h).setTeamAlignment(TeamAlignment.ENEMY), 18, 23);
        h.clearAndInvalidate();
    }

    @Override
    public void win() {

    }

}
