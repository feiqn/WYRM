package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.prefabs;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.actors.prefab.WYRMActors.WyrEmblem.Units;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Campaign.FlagID.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.Name.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Character.PersonalityType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.Cutscene.ID.*;

public final class WYRMCutscenes {

    private WYRMCutscenes() {}

    public static Array<WyrCutscene> forStage(Campaign.StageID stageID) {
        final Array<WyrCutscene> rV = new Array<>();

        if(!Campaign.checkFlag(UNDO_CUTSCENE_PLAYED)) rV.add(undoCutscene());

        switch(stageID) {
            case STAGE_1A:
                 rV.addAll(Chapter_1.story_A());
                 break;
            case STAGE_2A:
            case STAGE_3A:

            case STAGE_2B:
            default:
                break;
        }

        return rV;
    }

    private static WyrCutscene undoCutscene() {
        return new WyrCutscene(CSID_0_UNDO) {
            @Override
            protected void buildScript() {
                choreographShortPause();
                choreographRevealCondition(UNDO_CUTSCENE_PLAYED);
            }

            @Override
            protected void declareTriggers() {

            }
        };
    }

    private static class Chapter_1 {
        public static Array<WyrCutscene> story_A() {
            final Array<WyrCutscene> rV = new Array<>();

            final WyrCutscene needToEscape = new WyrCutscene(CSID_1A_LEIF_NEED_TO_ESCAPE) {
                @Override
                protected void buildScript() {
                    script(Leif, "I've got to get out of here...");
                    choreographRevealCondition(STAGE_1A_LEIF_ESCAPED_EAST);
                }

                @Override
                protected void declareTriggers() {
                    addTrigger(new Trigger(1, true));
                }
            };
            rV.add(needToEscape);

            final WyrCutscene ballista_1 = new WyrCutscene(CSID_1A_BALLISTA_1) {
                @Override
                protected void buildScript() {

                    choreographFocusUnit(Danial);

                    script(Danial, "Cowardly northerners!, I'll defend my home to the death!");
                    script(Danial, "Firing artillery!");

                    choreographFireArmament(Danial, "cutscene ballista", Liam.toString());

                    script(Leif, "Holy shit!");
                    script(Leif, "That guy just got obliterated!");
                    script(Leif, "I've got to get out of here!");

//                    choreographPassPriority(Danial);
                }

                @Override
                protected void declareTriggers() {
                    addTrigger(new Trigger(2, true));
                    addDefuseTrigger(new Trigger(Danial));
                }
            };
            rV.add(ballista_1);

            final WyrCutscene ballista_2 = new WyrCutscene(CSID_1A_BALLISTA_2) {
                @Override
                protected void buildScript() {

                    choreographFocusUnit(Danial);

                    script(Danial, "For God and Queen, I shall defend this city!");
                    script(Danial, "Fire again!");

                    choreographFireArmament(Danial, "cutscene ballista", Gordon.toString());

                    choreographShortPause();

                    choreographSpawn(Units.fran().ai(AGGRESSIVE), 16, 21);

                    script(Danial, "Damn it! They just keep coming!");
                }

                @Override
                protected void declareTriggers() {
                    addTrigger(new Trigger(3, true));
                    addTrigger(new Trigger(CSID_1A_BALLISTA_1));
                    incrementTriggerThreshold();

                    addDefuseTrigger(new Trigger(Danial));
                }
            };
            rV.add(ballista_2);

            final WyrCutscene ballista_3 = new WyrCutscene(CSID_1A_BALLISTA_3) {
                @Override
                protected void buildScript() {

                    choreographFocusUnit(Danial);

                    script(Danial, "Again!");

                    choreographFireArmament(Danial, "cutscene ballista", Fran.toString());

                    choreographShortPause();

                    choreographSpawn(Units.kaylie().ai(AGGRESSIVE), 17, 22);
                }

                @Override
                protected void declareTriggers() {
                    addTrigger(new Trigger(4, true));
                    addTrigger(new Trigger(CSID_1A_BALLISTA_2));
                    incrementTriggerThreshold();

                    addDefuseTrigger(new Trigger(Danial));
                }
            };
            rV.add(ballista_3);

            final WyrCutscene ballista_4_Death = new WyrCutscene(CSID_1A_BALLISTA_4_DEATH_OF_DANIAL) {
                @Override
                protected void buildScript() {

                    script(Danial, "No, not yet, I can still..."); // todo: face portrait left

                    choreographDeath(Danial);

                    choreographShortPause();

                    script(Leif, "Aw hell, him too?"); // todo: face left, stage right
                    script(Leif, "I really am alone out here...");
                }

                @Override
                protected void declareTriggers() {
                    addTrigger(new Trigger(5, true));
                    addTrigger(new Trigger(Danial));
                }
            };
            rV.add(ballista_4_Death);

            final WyrCutscene antal_Escaping_Alive = new WyrCutscene(CSID_1A_ANTAL_ESCAPING_ALIVE) {
                @Override
                protected void buildScript() {
                    script(Antal, "I made it!");
                    script(Antal, "Th-thank you, kind stranger!");

                    choreographDespawn(Antal);
                }

                @Override
                protected void declareTriggers() {
                    addTrigger(new Trigger(STAGE_1A_ANTAL_ESCAPED));
                }
            };
            rV.add(antal_Escaping_Alive);

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
//            rV.add(antal_Help_Me);

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
//            rV.add(leif_FiredBallista);

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
//            rV.add(leif_Using_Ballista);

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
//            rV.add(leif_Ineffective_Attack);

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
//            rV.add(leif_Getting_Attacked);

            return rV;
        }

    }


    public static Array<WyrCutscene> unsorted() {

        final WyrCutscene TGW_HowOldThisLand = new WyrCutscene(null) {
            @Override
            protected void buildScript() {
                script(The_Great_Wyrm, "How old is this land?");
                script(The_Great_Wyrm, "So, so old... as old as... as anything I can remember.");
            }

            @Override
            protected void declareTriggers() {}

        };

        final WyrCutscene TGW_Rakel_MyOwnName = new WyrCutscene(null) {
            @Override
            protected void buildScript() {
                script(The_Great_Wyrm, "As the void crept in around us, soon there remained only myself and one other.");
                script(The_Great_Wyrm, "She called herself Rakel, for there were none left to know her name.");
                script(The_Great_Wyrm, "There was perhaps nothing special about the affection we showed each other -- we were, together, all that remained.");
                script(The_Great_Wyrm, "Then soon, the void took her, too, in all but name.");
                script(The_Great_Wyrm, "Her name, which I have clung to even at the expense of my own.");
                script(The_Great_Wyrm, "I have lived, only that her name be not forgotten.");
            }

            @Override
            protected void declareTriggers() {}

        };

        final WyrCutscene TGW_AThousandDreams = new WyrCutscene(null) {
            @Override
            protected void buildScript() {
                script(The_Great_Wyrm, "I have dreamt of these last moments a thousand ways.");
                script(The_Great_Wyrm, "Each time, inventing new ways which this might not be the end.");
                script(The_Great_Wyrm, "An obstacle to hinder you, a once-boon now unreachable -- anything that might stay you from this inevitable, ruinous path.");
                script(The_Great_Wyrm, "Your will unshakable, I offered you knowledge of the truth of this land -- just enough that you may be contented.");
                script(The_Great_Wyrm, "Damnable, curious conscience. Must you accept no substitute for truth? ");
            }

            @Override
            protected void declareTriggers() {}

        };


        return null;
    }

}
