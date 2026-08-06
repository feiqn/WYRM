package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.campaigns.DeveloperLand;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WYRMActors.WyrEmblem.*;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCondition;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.prefabs.WYRMCutscenes;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.FlagID.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.StageID.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.WinConPolarity.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.WinConType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.WinConType.ESCAPE;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.PersonalityType.*;

public final class GS_DEBUG extends WyrScreen {

    public GS_DEBUG() {
        super(new TmxMapLoader().load("test/maps/1A_v0.tmx"));
    }

    @Override
    protected void declareActors() {

        instantiateUnit(Units.leif(),29, 26);

        instantiateUnit(Units.danial(), 36,27);

        instantiateUnit(Units.collin(), 11, 23);
        instantiateUnit(Units.gordon(), 8, 24);
        instantiateUnit(Units.liam().ai(AGGRESSIVE), 17, 21);

        instantiateProp(Props.ballista("cutscene ballista"), 35, 27);
    }

    @Override
    protected void declareWinCons() {
        if(!Campaign.checkFlag(UNDO_CUTSCENE_PLAYED)) {
            handlers.register().addWinCon(
                new WyrWinCondition(
                    DEATH,
                    FAILURE,
                    UNDO_CUTSCENE_PLAYED,
                    "[PURPLE]We can never go back."
                )
            );
        }

        handlers.register().addWinCon(
            new WyrWinCondition(
                ESCAPE,
                VICTORY,
                STAGE_1A_LEIF_ESCAPED_EAST,
                "[GREEN]VICTORY:[] Escape!"
            )
                .setCharacter(Leif)
                .setPropUID("escape east")
                .setTerminal()
        );

        handlers.register().addWinCon(
            new WyrWinCondition(
                ESCAPE,
                VICTORY,
                STAGE_1A_LEIF_ESCAPED_WEST
            )
               .setCharacter(Leif)
               .setPropUID("escape west")
               .setTerminal()
        );

        handlers.register().addWinCon(
            new WyrWinCondition(
                ESCAPE,
                OPTIONAL,
                STAGE_1A_ANTAL_ESCAPED,
                "[GOLDENROD]OPTIONAL:[] Antal escapes."
            )
                .setCharacter(Antal)
                .setPropUID("escape west")
        );

        handlers.register().addWinCon(
            new WyrWinCondition(
                DEATH,
                FAILURE,
                Leif + "_DIED",
                "[RED]FAILURE:[] Death."
            )
                .setTerminal()
                .setCharacter(Leif)
        );

    }

    @Override
    protected void declareCutscenes() {

        // TODO: uncomment this to test cutscene queueing later.
//        handlers.cutscenes().addCutscene(new WyrCutscene(CSID_0_DEBUG) {
//            @Override
//            protected void buildScript() {
//                script(Leif, "Hello.").preferredName("Robin Fire Emblem");
//                script(Leif, "If you can [RED]read[] this,");
//                script(Leif, "Everything turned out [GOLD]better than expected[].");
//            }
//
//            @Override
//            protected void declareTriggers() {
//                addTrigger(new Trigger(1, false));
//            }
//        });

//        for(WyrCutscene cs : WYRMCutscenes.forStage(STAGE_1A)) {
//            handlers.cutscenes().addCutscene(cs);
//        }

    }

    @Override
    public void win() {

    }

    @Override
    protected void fail() {

    }

}
