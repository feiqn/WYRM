package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCon;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography.WyrCutsceneChoreography;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.WyrCutsceneShot;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.triggers.WyrCutsceneTrigger;

import static com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography.WyrCutsceneChoreography.DialogChoreoType.*;

public abstract class WyrCutscene implements Wyr {

    // refactor of OLD_CutsceneScript

    public enum LoopCondition {
        MULTIPLICATIVE_THRESHOLD,
        BROKEN_THRESHOLD
    }

    private   final Array<WyrCutsceneShot>    script         = new Array<>(); // used to be its own class but felt too bloaty for one assembly
    protected final Array<WyrCutsceneTrigger> triggers       = new Array<>();
    protected final Array<WyrCutsceneTrigger> defuseTriggers = new Array<>();

    protected boolean hasPlayed   = false;
    protected boolean readyToPlay = false;
    protected boolean defused     = false;

    protected int triggerThreshold = 1;
    protected int defuseThreshold  = 1;
    protected int triggerCount     = 0;
    protected int defuseCount      = 0;
    protected int scriptIndex      = 0;

    protected Image backgroundImage = new Image();

    protected LoopCondition loopCondition = null;

    private final Enum<?> CutsceneID;

    protected WyrCutscene(CutsceneID id) {
        this.CutsceneID = id;
        buildScript();
        declareTriggers();
    }

    protected abstract void buildScript();

    protected abstract void declareTriggers();

    protected void addTrigger(WyrCutsceneTrigger trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDefuseTrigger(WyrCutsceneTrigger trigger) {
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
                scriptIndex = 0;
                triggers.clear();
                declareTriggers();
                hasPlayed = false;
                break;
        }
    }

    public WyrCutsceneShot nextShot() {
        if(defused) return null;
        if(script.size == 0) buildScript();
        if(readyToPlay) {
            readyToPlay = false;
            if(!shouldLoop()) {
                hasPlayed = true;
            }
        }

        scriptIndex++;
        return script.get(scriptIndex - 1);
    }

    public WyrCutsceneShot previewNextShot() {
        try {
            return script.get(scriptIndex);
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
    protected void setFullscreenImage(Drawable drawable) { backgroundImage.setDrawable(drawable); }

    /**
     * Getters
     */
    public boolean isReadyToPlay() {
        if(defused || hasPlayed) return false;
        Gdx.app.log("CS", "ready to play: " + readyToPlay);
        return readyToPlay;
    }
    public boolean continues() {
        if(defused) return false;
        boolean continues = false;
        try {
            if(scriptIndex == 0) {
                if(script.size == 0) buildScript();
                return true;
            }
            int upperMax = script.size;
            if(scriptIndex < upperMax) {
                continues = true;
            }
        } catch(Exception ignored) {}

        if(!continues && shouldLoop()) {
            resetLoop();
        }
        return continues;
    }
    public Enum<?> getCutsceneID() { return CutsceneID; }
    public Image getBackgroundImage() { return backgroundImage; }
    protected LoopCondition getLoopCondition() { return loopCondition; }
    protected boolean shouldLoop() { return loopCondition != null; }
    protected WyrCutsceneShot lastSlide() { return script.get(script.size-1); }

    /**
     * Cheeky dev stuff
     */
    public void DEVELOPER_skipToEnd() {
        this.scriptIndex = script.size - 2;
    }


    /**
     * Trigger constructors. AKA, Arming functions.
     */
    protected void armCampaignFlagCutsceneTrigger(FlagID flags, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(flags);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTurnCutsceneTrigger(Integer turnToTrigger, boolean exactTurn, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(turnToTrigger, exactTurn);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSingleUnitCombatCutsceneTrigger(CharacterID rosterID, boolean beforeCombat, boolean requiresAggressor, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(rosterID, beforeCombat, requiresAggressor);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTwoUnitCombatCutsceneTrigger(CharacterID unit1, CharacterID unit2, boolean beforeCombat, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(unit1, unit2, beforeCombat);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armOtherIDCutsceneTrigger(CharacterID id, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(id);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSpecificUnitAreaCutsceneTrigger(CharacterID rosterID, Array<Vector2> areas, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(rosterID, areas);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSpecificUnitAreaCutsceneTrigger(CharacterID rosterID, Vector2 area, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(rosterID, area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armAnyUnitAreaCutsceneTrigger(Vector2 area, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armDeathCutsceneTrigger(CharacterID deathOf, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(deathOf);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armDeathCutsceneTrigger(TeamAlignment alignment, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(alignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTeamAlignmentAreaTrigger(Vector2 area, TeamAlignment requiredAlignment, boolean defuser) {
        final WyrCutsceneTrigger t = new WyrCutsceneTrigger(area, requiredAlignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    /**
     * Trigger checks
     */
    // Mr. Morton is the subject of the sentence, but what the Predicate does in Java, I do not understand.
    public void checkDeathTriggers(CharacterID roster) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
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

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(roster)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkDeathTriggers(TeamAlignment alignment) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkDeathTrigger(alignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(alignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkAreaTriggers(CharacterID rosterID, Vector2 tileCoordinate) {

        // Checks if specific unit stepped in specific area.

        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
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

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCampaignFlagTriggers(FlagID flags) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCampaignFlagTrigger(flags)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCampaignFlagTrigger(flags)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkTurnTriggers(int turn) {
        if(defused) return;
//        Gdx.app.log("CS", "checking...");


        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkTurnTrigger(turn)) {
                def.fire();
//                Gdx.app.log("CS", "def trigger");
                incrementDefuseCount();
            }
        }

        if(defused) return;
//        Gdx.app.log("CS", "checking...");

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkTurnTrigger(turn)) {
                trigger.fire();
//                Gdx.app.log("CS", "turn trigger");
                incrementTriggerCount();
            }
        }
    }
    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkOtherCutsceneTrigger(otherID)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkOtherCutsceneTrigger(otherID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatStartTriggers(CharacterID rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatStartTriggers(CharacterID attacker, CharacterID defender) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatEndTriggers(CharacterID rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatEndTriggers(CharacterID attacker, CharacterID defender) {
        if(defused) return;

        for(WyrCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(WyrCutsceneTrigger trigger : triggers) {
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
    protected WyrCutsceneShot script(CharacterID characterID, HorizontalPosition position, String line) {
        script.add(new WyrCutsceneShot(new WyrCutsceneShot.DialogDirection(characterID).position(position).line(line)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot script(CharacterID characterID, String line) {
        // either default location or infer from previous slides
        script.add(new WyrCutsceneShot(new WyrCutsceneShot.DialogDirection(characterID).line(line)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot script(String line) {
        // Infer based on previous slide
        script.add(new WyrCutsceneShot(new WyrCutsceneShot.DialogDirection(line)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot script(WyrCutsceneShot shot) {
        script.add(shot);
        return script.get(script.size-1);
    }

    /**
     * Dialog Choreography:
     * These are things that happen while the conversation window is visible,
     * typically manipulating character portraits, or are generally agnostic
     * to the external world / screen state.
     */
    protected WyrCutsceneShot choreographScreenTransition(WyrScreen screen) {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(SCREEN_TRANSITION).setScreenForTransition(screen)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot choreographScreenFadeOut() {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(SCREEN_FADE_OUT)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot choreographScreenFadeIn() {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(SCREEN_FADE_IN)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot choreographShortPause() {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(PAUSE_SHORT)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot choreographLongPause() {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(PAUSE_LONG)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot choreographRevealCondition(WyrWinCon condition) {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(WINCON_REVEAL).setWinCon(condition)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot choreographSatisfyCondition(WyrWinCon condition) {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(WINCON_SATISFY).setWinCon(condition)));
        return script.get(script.size-1);
    }
    protected WyrCutsceneShot choreographEndScene() {
        script.add(new WyrCutsceneShot(new WyrCutsceneChoreography(CUTSCENE_END)));
        return script.get(script.size-1);
    }

}
