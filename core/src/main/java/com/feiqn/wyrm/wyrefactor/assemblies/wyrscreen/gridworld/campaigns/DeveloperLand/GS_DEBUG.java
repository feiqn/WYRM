package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.campaigns.DeveloperLand;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.prefab.generic.GU_Soldier;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.prefab.named.GU_Leif;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.grid.RPGridCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.triggers.GridCutsceneTrigger;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.RPGridScreen;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.personality.PersonalityType.AGGRESSIVE;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.CharacterID.Leif;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.CutsceneID.CSID_0_DEBUG;

public final class GS_DEBUG extends RPGridScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void setup() {
        instantiateUnit(new GU_Leif(h()), 29, 22);
        instantiateUnit(new GU_Soldier(h()), 28, 23);
        instantiateUnit(new GU_Soldier(h()), 28, 21);
//        instantiateUnit(new GU_Soldier(h()), 30, 22);
//        instantiateUnit(new GU_Soldier(h()), 29, 23);
//        instantiateUnit(new GU_Soldier(h()), 29, 24);
//        instantiateUnit(new GU_Soldier(h()), 29, 25);
//        instantiateUnit(new GU_Soldier(h()), 30, 23);
//        instantiateUnit(new GU_Soldier(h()), 27, 21);
//        instantiateUnit(new GU_Soldier(h()), 27, 23);
//        instantiateUnit(new GU_Soldier(h()), 27, 22);
//        instantiateUnit(new GU_Soldier(h()), 31, 22);
//        instantiateUnit(new GU_Soldier(h()), 29, 21);
//        instantiateUnit(new GU_Soldier(h()), 28, 22);
        instantiateUnit(new GU_Soldier(h()).setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 18, 23);
        instantiateUnit(new GU_Soldier(h()).setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 17, 21);
        instantiateUnit(new GU_Leif(h()).setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 15, 23);
    }

    @Override
    protected void buildCutscenes() {
        h().cutscenes().addCutscene(new RPGridCutscene(CSID_0_DEBUG) {
            @Override
            protected void buildScript() {
                script(Leif, "Hello.");
                script(Leif, "If you can read this,");
                script(Leif, "Everything turned out better than expected.");
            }

            @Override
            protected void declareTriggers() {
                addTrigger(new GridCutsceneTrigger(1));
            }
        });
    }

    @Override
    public void win() {

    }

}
