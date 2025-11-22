package com.feiqn.wyrm.logic.handlers.cutscene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneScript;
import com.feiqn.wyrm.logic.handlers.cutscene.triggers.CutsceneTrigger;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

public class CutsceneHandler {

    private final WYRMGame game;

    private final Array<CutsceneScript> cutscenes;



    public CutsceneHandler(WYRMGame game) {
        this.game = game;
        this.cutscenes = new Array<>();
    }

    public void addCutscene(CutsceneScript cutscene) {
        if(!cutscenes.contains(cutscene, true)) cutscenes.add(cutscene);
    }

    public void startCutscene(CutsceneScript DScript) {
        // TODO: persistent CutscenePlayer Actor
        game.activeGridScreen.startCutscene(new CutscenePlayer(game, DScript));
    }


    /**
     * Trigger checks
     */
    public void checkDeathTriggers(UnitRoster roster) {
        for(CutsceneScript cutscene : cutscenes) {
            for(CutsceneTrigger trigger : cutscene.getTriggers()) {
                if(trigger.hasTriggered()) continue;
                if(trigger.checkDeathTrigger(roster)) {
                    trigger.getScript().incrementTriggerCount();
                    if(trigger.getScript().thresholdMet()) {
                        startCutscene(trigger.getScript());
                        break;
                    }
                }
            }
        }
    }

    public void checkAreaTriggers(Vector2 tileCoordinate) {
        for(CutsceneScript cutscene : cutscenes) {
            for(CutsceneTrigger trigger : cutscene.getTriggers()) {
                if(trigger.hasTriggered()) continue;
                if(trigger.checkAreaTrigger(tileCoordinate)) {
                    trigger.getScript().incrementTriggerCount();
                    if(trigger.getScript().thresholdMet()) {
                        startCutscene(trigger.getScript());
                        break;
                    }
                }
            }
        }
    }

    public void checkTurnTriggers(int turn) {
        for(CutsceneScript cutscene : cutscenes) {
            for(CutsceneTrigger trigger : cutscene.getTriggers()) {
                if(trigger.hasTriggered()) continue;
                if(trigger.checkTurnTrigger(turn)) {
                    trigger.getScript().incrementTriggerCount();
                    if(trigger.getScript().thresholdMet()) {
                        startCutscene(trigger.getScript());
                        break;
                    }
                }
            }
        }
    }

    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        for(CutsceneScript cutscene : cutscenes) {
            for(CutsceneTrigger trigger : cutscene.getTriggers()) {
                if(trigger.hasTriggered()) continue;
                if(trigger.checkOtherCutsceneTrigger(otherID)) {
                    trigger.getScript().incrementTriggerCount();
                    if(trigger.getScript().thresholdMet()) {
                        startCutscene(trigger.getScript());
                        break;
                    }
                }
            }
        }

    }

    public void checkCombatStartTriggers(UnitRoster attacker, UnitRoster defender) {
        for(CutsceneScript cutscene : cutscenes) {
            for(CutsceneTrigger trigger : cutscene.getTriggers()) {
                if(trigger.hasTriggered()) continue;
                if(trigger.checkCombatStartTrigger(attacker, defender)) {
                    trigger.getScript().incrementTriggerCount();
                    if(trigger.getScript().thresholdMet()) {
                        startCutscene(trigger.getScript());
                        break;
                    }
                }
            }
        }
    }

    public void checkCombatEndTriggers(UnitRoster attacker, UnitRoster defender) {
        for(CutsceneScript cutscene : cutscenes) {
            for(CutsceneTrigger trigger : cutscene.getTriggers()) {
                if(trigger.hasTriggered()) continue;
                if(trigger.checkCombatEndTrigger(attacker, defender)) {
                    trigger.getScript().incrementTriggerCount();
                    if(trigger.getScript().thresholdMet()) {
                        startCutscene(trigger.getScript());
                        break;
                    }
                }
            }
        }
    }
}
