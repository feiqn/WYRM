package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.campaigns.DeveloperLand;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.prefab.units.prefab.generic.GU_Soldier;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.prefab.units.prefab.named.GU_Leif;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Character.Name.Leif;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Character.PersonalityType.AGGRESSIVE;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Cutscene.ID.CSID_0_DEBUG;

public final class GS_DEBUG extends WyrScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void setup() {
        Gdx.app.log("screen", "setup");
        instantiateUnit(new GU_Leif(),29, 22);
//        instantiateUnit(new GU_Soldier(),28, 23);
//        instantiateUnit(new GU_Soldier(),28, 21);
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
        instantiateUnit(new GU_Soldier().setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 18, 23);
//        instantiateUnit(new GU_Soldier().setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 17, 21);
//        instantiateUnit(new GU_Leif().setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 15, 23);
    }

    @Override
    protected void buildCutscenes() {
        Gdx.app.log("screen", "build cs");
//        handlers.cutscenes().addCutscene(new WyrCutscene(CSID_0_DEBUG) {
//            @Override
//            protected void buildScript() {
//                script(Leif, "Hello.");
//                script(Leif, "If you can read this,");
//                script(Leif, "Everything turned out better than expected.");
//            }
//
//            @Override
//            protected void declareTriggers() {
//                addTrigger(new Trigger(1, false));
//            }
//        });
    }

    @Override
    public void win() {

    }

}
