package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.wyrm.CampaignFlags;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.TeamAlignment;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneID;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.Position;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.WyrCutsceneSlide;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.triggers.WyrCutsceneTrigger;

public abstract class WyrCutscene<
        Actor extends WyrActor<?,?,?,?>
            > implements Wyr {

    // refactor of CutsceneScript (barely)

    public enum LoopCondition {
        MULTIPLICATIVE_THRESHOLD,
        BROKEN_THRESHOLD
    }

    protected final Array<WyrCutsceneSlide<?>>   script         = new Array<>();
    protected final Array<WyrCutsceneTrigger<?>> triggers       = new Array<>();
    protected final Array<WyrCutsceneTrigger<?>> defuseTriggers = new Array<>();

    protected boolean hasPlayed   = false;
    protected boolean readyToPlay = false;
    protected boolean defused     = false;

    protected int triggerThreshold = 1;
    protected int defuseThreshold  = 1;
    protected int triggerCount     = 0;
    protected int defuseCount      = 0;
    protected int slideIndex       = 0;

    protected LoopCondition loopCondition = null;

    private final CutsceneID cutsceneID;

    protected WyrCutscene(CutsceneID id) {
        this.cutsceneID = id;
        buildScript();
        declareTriggers();
    }

    protected abstract void buildScript();

    protected abstract void declareTriggers();

    protected void addTrigger(WyrCutsceneTrigger<Actor> trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDefuseTrigger(WyrCutsceneTrigger<Actor> trigger) {
        if(!defuseTriggers.contains(trigger, true)) defuseTriggers.add(trigger);
    }

    private void resetLoop() {
        if(!shouldLoop()) return;

        switch(loopCondition) {
            case MULTIPLICATIVE_THRESHOLD:
                triggerCount = 0;
            case BROKEN_THRESHOLD:
                readyToPlay = false;
            default:
                slideIndex = 0;
                triggers.clear();
                declareTriggers();
                hasPlayed = false;
                break;
        }
    }

    public WyrCutsceneSlide<?> nextSlide() {
        if(defused) return null;
        if(script.size == 0) buildScript();
        if(readyToPlay) {
            readyToPlay = false;
            if(!shouldLoop()) {
                hasPlayed = true;
            }
        }

        slideIndex++;
        return script.get(slideIndex - 1);
    }

    public WyrCutsceneSlide<?> previewNextSlide() {
        try {
            return script.get(slideIndex);
        } catch (Exception ignored) {
            Gdx.app.log("cutScript", "bad preview");
            return null;
        }
    }


    /**
     * Setters and incrementers
     */
    protected void setLoopCondition(LoopCondition condition) { this.loopCondition = condition;}
    protected void incrementTriggerCount() {
        if(readyToPlay) return;
        triggerCount++;
        if(triggerCount >= triggerThreshold) readyToPlay = true;
    }
    protected void incrementDefuseCount() {
        if(defused) return;
        defuseCount++;
        if(defuseCount >= defuseThreshold) defused = true;
    }


    /**
     * Getters
     */
    public boolean isReadyToPlay() {
        if(defused || hasPlayed) return false;
        return readyToPlay;
    }
    public boolean continues() {
        if(defused) return false;
        boolean continues = false;
        try {
            if(slideIndex == 0) {
                if(script.size == 0) buildScript();
                return true;
            }
            int upperMax = script.size;
            if(slideIndex < upperMax) {
                continues = true;
            }
        } catch(Exception ignored) {}

        if(!continues && shouldLoop()) {
            resetLoop();
        }
        return continues;
    }
    public CutsceneID getCutsceneID() { return cutsceneID; }
    protected LoopCondition getLoopCondition() { return loopCondition; }
    protected boolean shouldLoop() { return loopCondition != null; }
    protected WyrCutsceneSlide<?> lastSlide() { return script.get(script.size-1); }


    /**
     * Cheeky dev stuff
     */
    public void DEVELOPER_skipToEnd() {
        this.slideIndex = script.size - 2;
    }


    /**
     * Trigger constructors. AKA, Arming functions.
     */
    protected void armCampaignFlagCutsceneTrigger(CampaignFlags flags, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<>(flags);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTurnCutsceneTrigger(Integer turnToTrigger, boolean exactTurn, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<>(turnToTrigger, exactTurn);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSingleUnitCombatCutsceneTrigger(UnitIDRoster rosterID, boolean beforeCombat, boolean requiresAggressor, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(rosterID, beforeCombat, requiresAggressor);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTwoUnitCombatCutsceneTrigger(UnitIDRoster unit1, UnitIDRoster unit2, boolean beforeCombat, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(unit1, unit2, beforeCombat);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armOtherIDCutsceneTrigger(CutsceneID id, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(id);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSpecificUnitAreaCutsceneTrigger(UnitIDRoster rosterID, Array<Vector2> areas, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(rosterID, areas);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSpecificUnitAreaCutsceneTrigger(UnitIDRoster rosterID, Vector2 area, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(rosterID, area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armAnyUnitAreaCutsceneTrigger(Vector2 area, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armDeathCutsceneTrigger(UnitIDRoster deathOf, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(deathOf);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armDeathCutsceneTrigger(TeamAlignment alignment, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(alignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTeamAlignmentAreaTrigger(Vector2 area, TeamAlignment requiredAlignment, boolean defuser) {
        final WyrCutsceneTrigger<Actor> t = new WyrCutsceneTrigger<Actor>(area, requiredAlignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    /**
     * Trigger checks
     */
    //Mr. Morton is the subject of the sentence, but what the Predicate does in Java, I do not understand.
    public void checkDeathTriggers(UnitIDRoster roster) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkDeathTrigger(roster)) {
                def.fire();
                incrementDefuseCount();
                // Avoid calling break; here,
                // in order to allow multiple triggers
                // to have overlapping conditions,
                // and the potential for complex
                // and contextual trigger handling.
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(roster)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkDeathTriggers(TeamAlignment alignment) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkDeathTrigger(alignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(alignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkAreaTriggers(UnitIDRoster rosterID, Vector2 tileCoordinate) {

        // Checks if specific unit stepped in specific area.

        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(rosterID, tileCoordinate)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkAreaTriggers(Vector2 tileCoordinate, TeamAlignment unitsTeamAlignment) {

        // Checks if anyone, or just if player's own units,
        // stepped in the specific area.

        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCampaignFlagTriggers(CampaignFlags flags) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCampaignFlagTrigger(flags)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCampaignFlagTrigger(flags)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkTurnTriggers(int turn) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkTurnTrigger(turn)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkTurnTrigger(turn)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkOtherCutsceneTrigger(otherID)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkOtherCutsceneTrigger(otherID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatStartTriggers(UnitIDRoster rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatStartTriggers(UnitIDRoster attacker, UnitIDRoster defender) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatEndTriggers(UnitIDRoster rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatEndTriggers(UnitIDRoster attacker, UnitIDRoster defender) {
        if(defused) return;

        for(WyrCutsceneTrigger<?> def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger<?> trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    /**
     * Standard Dialog,
     * Convenience methods for quickly building out scripts.
     */
    protected void script(UnitIDRoster name, Position position, String text) {

    }
    protected void script(UnitIDRoster name, String text) {
        // either default location or infer from previous slides
    }
    protected void script(String text) {
        // Infer based on previous slide
    }

    /**
     * Dialog Choreography:
     * These are things that happen while the conversation window is visible,
     * typically manipulating character portraits.
     */
    protected void choreographAddPortrait() {

    }
    protected void choreographRemovePortrait() {

    }
    protected void choreographPause() {

    }
    protected void choreographLinger() {

    }
    protected void choreographFocusLocation() {

    }
    protected void choreographRevealCondition() {

    }
    protected void choreographEndScene() {

    }


    /**
     * World Choreography:
     * These happen after removing the conversation window,
     * typically manipulating units, props, and world states.
     */
    protected void choreographScreenTransition() {

    }
    protected void choreographAbility() {

    }
    protected void choreographUseProp() {

    }
    protected void choreographSpawn() {

    }
    protected void choreographDespawn() {

    }
    protected void choreographDeath() {

    }
    protected void choreographMoveBy() {

    }
    protected void choreographFollowPath() {

    }
    protected void choreographFocusActor() {

    }
}
