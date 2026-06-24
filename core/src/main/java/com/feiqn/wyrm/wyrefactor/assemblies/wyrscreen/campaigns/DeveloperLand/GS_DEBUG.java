package com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.campaigns.DeveloperLand;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.prefab.units.prefab.generic.templates.GU_Soldier;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.prefab.units.prefab.named.GU_Leif;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.PersonalityType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Cutscene.ID.*;

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
        instantiateUnit(new GU_Soldier(), 30, 22);
//        instantiateUnit( new GU_Soldier(h()), 29, 23);
//        instantiateUnit(new GU_Soldier(h()), 29, 24);
//        instantiateUnit(new GU_Soldier(h()), 29, 25);
//        instantiateUnit(new GU_Soldier(h()), 30, 23);
//        instantiateUnit(new GU_Soldier(h()), 27, 21);
//        instantiateUnit(new GU_Soldier(h()), 27, 23);
//        instantiateUnit(new GU_Soldier(h()), 27, 22);
//        instantiateUnit(new GU_Soldier(h()), 31, 22);
//        instantiateUnit(new GU_Soldier(h()), 29, 21);
//        instantiateUnit(new GU_Soldier(h()), 28, 22);
//        instantiateUnit(new GU_Soldier().setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 18, 23);
        instantiateUnit(new GU_Soldier().setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 17, 21);
        instantiateUnit(new GU_Leif().setTeamAlignment(TeamAlignment.ENEMY).setPersonalityType(AGGRESSIVE), 15, 23);
    }

    @Override
    protected void buildCutscenes() {
        Gdx.app.log("screen", "build cs");
        handlers.cutscenes().addCutscene(new WyrCutscene(CSID_0_DEBUG) {
            @Override
            protected void buildScript() {
                script(Leif, "Hello.").preferredName("Robin Fire Emblem");
                script(Leif, "If you can read this,");
                script(Leif, "Everything turned out better than expected.");
            }

            @Override
            protected void declareTriggers() {
                addTrigger(new Trigger(1, false));
            }
        });


        final WyrCutscene needToEscape = new WyrCutscene(CSID_1A_LEIF_NEED_TO_ESCAPE) {
            @Override
            protected void buildScript() {
                script(Leif, "I've got to get out of here...");
            }

            @Override
            protected void declareTriggers() {
                addTrigger(new Trigger(1, true));
            }
        };

        final WyrCutscene ballista_1 = new WyrCutscene(CSID_1A_BALLISTA_1) {
            @Override
            protected void buildScript() {
                script(Danial, "Cowardly Northerners, attacking unprovoked!");
                script(Danial, "I'll defend our home to the death!");
                script(Danial, "Firing artillery!");

                // ballista attacks generic enemy

                script(Leif, "Holy shit!");
                script(Leif, "That guy just got obliterated!");
                script(Leif, "I've got to get out of here!");
            }

            @Override
            protected void declareTriggers() {

            }
        };

        final WyrCutscene ballista_2 = new WyrCutscene(CSID_1A_BALLISTA_2) {
            @Override
            protected void buildScript() {
                // focus on Danial

                script(Danial, "In the name of the Queen, I shall defend this great nation!");
                script(Danial, "Fire artillery!");

                // fire at generic enemy

                // new enemy spawns in

                script(Danial, "Damn it! They just keep coming!");

            }

            @Override
            protected void declareTriggers() {

            }
        };

        final WyrCutscene ballista_3_Loop = new WyrCutscene(CSID_1A_BALLISTA_LOOP) {
            @Override
            protected void buildScript() {
                // danial fires on generic enemy,

                // new enemy spawns
            }

            @Override
            protected void declareTriggers() {

            }
        };

        final WyrCutscene ballista_4_Death = new WyrCutscene(CSID_1A_BALLISTA_DEATH) {
            @Override
            protected void buildScript() {
                script(Danial, "No, not yet, I can still..."); // todo: face left
//                choreographDeath(Danial);
                script(Leif, "Aw hell, him too?"); // face left, stage right
                script(Leif, "I really am alone out here...");
            }

            @Override
            protected void declareTriggers() {

            }
        };

        final WyrCutscene antal_Escaping_Alive = new WyrCutscene(CSID_1A_ANTAL_ESCAPING_ALIVE) {
            @Override
            protected void buildScript() {
                script(Antal, "I made it!");
                script(Antal, "Th-thank you, kind stranger!");
            }

            @Override
            protected void declareTriggers() {
                // antal escaped alive to the west tile
            }
        };

        final WyrCutscene antal_Help_Me = new WyrCutscene(CSID_1A_ANTAL_HELP_ME) {
            @Override
            protected void buildScript() {
//                set(OLD_CharacterExpression.LEIF_WORRIED, "I think we got away...");
//
//                choreographSpawn(antal, 29, 29);
//
//                choreographFocusOnUnit(antal);
//
//                set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "Please...");
//                set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "...help me.");
//                set(OLD_CharacterExpression.LEIF_PANICKED, "Help you?! Aren't you supposed to be protecting the city?!", HorizontalPosition.RIGHT, true);
//                set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "Please, this armor, it's so heavy...");
//                set(OLD_CharacterExpression.ANTAL_EXHAUSTED, "I'll die if I don't get out of here!");
//
//                choreographShortPause();
//
//                choreographFocusOnUnit(ags.conditions().teams().getPlayerTeam().get(0));
//
//                set(OLD_CharacterExpression.LEIF_WORRIED, "I could flee and save myself, but that knight...");
//                set(OLD_CharacterExpression.LEIF_WORRIED, "What do I do..?");
//
//                choreographRevealVictCon(FlagID.STAGE_1A_ANTAL_ESCAPED);
            }

            @Override
            protected void declareTriggers() {
                // leif crosses the eastern flame wall
            }
        };

        final WyrCutscene leif_FiredBallista = new WyrCutscene(CSID_1A_LEIF_FIRED_BALLISTA) {
            @Override
            protected void buildScript() {
//                set(OLD_CharacterExpression.LEIF_PANICKED, "Holy shit!", HorizontalPosition.RIGHT, true);
//                set(OLD_CharacterExpression.LEIF_PANICKED, "That guy exploded!", HorizontalPosition.RIGHT, true);
//                set(OLD_CharacterExpression.LEIF_PANICKED, "I... I killed that guy.", HorizontalPosition.RIGHT, true);
//
//                // TODO: this may be the place to have generic enemies display names after this cs.
            }

            @Override
            protected void declareTriggers() {
//                armDeathCutsceneTrigger(Wyr.TeamAlignment.ENEMY, false);
//                armOtherIDCutsceneTrigger(thisCutsceneID.CSID_1A_BALLISTA_DEATH, false);
//                triggerThreshold++;
            }
        };

        final WyrCutscene leif_Using_Ballista = new WyrCutscene(CSID_1A_LEIF_GETTING_IN_THE_BALLISTA) {
            @Override
            protected void buildScript() {
//                set(OLD_CharacterExpression.LEIF_WORRIED, "Okay, I can do this, just aim and shoot, same as any old longbow...", HorizontalPosition.RIGHT, true);
//
//                set(OLD_CharacterExpression.LEIF_PANICKED, "...oh, god, this is nothing like a a longbow.", HorizontalPosition.RIGHT, true);
//
//                set(OLD_CharacterExpression.LEIF_PANICKED, "How do I aim this thing?!", HorizontalPosition.RIGHT, true);
            }

            @Override
            protected void declareTriggers() {
//                armSpecificUnitAreaCutsceneTrigger(CharacterID.Leif, new Vector2(35,27), false);
            }
        };

        final WyrCutscene leif_Ineffective_Attack = new WyrCutscene(CSID_1A_LEIF_INEFFECTIVE_ATTACK) {
            @Override
            protected void buildScript() {

//                set(OLD_CharacterExpression.LEIF_WINCING, "Ow ow ow!");
//
//                set(OLD_CharacterExpression.LEIF_WINCING, "I think I hurt my fist more than I hurt him.");
//
//                set(OLD_CharacterExpression.LEIF_WINCING, "I've got to get out of here before these guys kill me!");

            }

            @Override
            protected void declareTriggers() {
//                armSingleUnitCombatCutsceneTrigger(CharacterID.Leif, false, true, false);

//        armOtherIDCutsceneTrigger(CutsceneID.CSID_1A_LEIF_LEAVEMEALONE, true);
            }
        };

        final WyrCutscene leif_Getting_Attacked = new WyrCutscene(CSID_1A_LEIF_LEAVE_ME_ALONE) {
            @Override
            protected void buildScript() {

//                set(OLD_CharacterExpression.LEIF_PANICKED, "No no no no no no no no");
//                set(OLD_CharacterExpression.LEIF_PANICKED, "Get off of me!");
//
//                choreographUseAbility(ags.conditions().teams().getPlayerTeam().get(0), WyRPG.AbilityID.DIVE_BOMB, ags.conditions().teams().getEnemyTeam().get(0));
//
//                set(OLD_CharacterExpression.LEIF_HOPEFUL, bfn + "!");
//                set(OLD_CharacterExpression.LEIF_WORRIED, "Ooooohhhhh thank you thank you thank you thank you thank you!"); // mounted char portrait
//
//                choreographFocusOnLocation(45, 20);
//
//                set(OLD_CharacterExpression.LEIF_HOPEFUL, "To the east! We can fly right over those flames, and the soldiers wont be able to chase us!");
//                set(OLD_CharacterExpression.LEIF_DETERMINED, "Let's get out of here!");
//
//                lastFrame().addDialogAction(new OLD_DialogAction(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(ags.conditions().teams().getPlayerTeam().get(0) instanceof LeifUnitOLD) {
//                            ((LeifUnitOLD) ags.conditions().teams().getPlayerTeam().get(0)).mount();
//                        }
//                    }
//                }));
            }

            @Override
            protected void declareTriggers() {
//                armSingleUnitCombatCutsceneTrigger(CharacterID.Liam, true, true, false);
            }
        };

    }

    @Override
    public void win() {

    }

}
