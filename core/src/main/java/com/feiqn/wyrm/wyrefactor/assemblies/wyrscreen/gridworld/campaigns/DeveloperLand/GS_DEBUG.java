package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.campaigns.DeveloperLand;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.prefab.generic.GU_Soldier;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.prefab.named.GU_Leif;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.RPGridScreen;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.RPGridPersonalityType.AGGRESSIVE;

public final class GS_DEBUG extends RPGridScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void setup() {
        instantiateUnit(new GU_Leif(h), 29, 22);
        instantiateUnit(new GU_Soldier(h), 22, 21);
        instantiateUnit(new GU_Soldier(h), 26, 20);
        instantiateUnit(new GU_Soldier(h).setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 18, 23);
        instantiateUnit(new GU_Soldier(h).setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 17, 21);
        instantiateUnit(new GU_Leif(h).setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 15, 23);
        h.clearAndInvalidate();
    }

    @Override
    public void win() {

    }

}
