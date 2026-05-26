package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrWinCon;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.tiles.RPGridTile;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr;

import com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Cutscene.LoopCondition;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Cutscene.Choreography.DialogChoreoType.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Cutscene.HorizontalPosition.LEFT;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.Wyr.Cutscene.TriggerType.*;

public abstract class WyrCutscene implements Wyr {

    private   final Array<Shot>    script = new Array<>(); // used to be its own class but felt too bloaty for one assembly
    protected final Array<Trigger> triggers       = new Array<>();
    protected final Array<Trigger> defuseTriggers = new Array<>();

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

    protected WyrCutscene(Cutscene.ID id) {
        this.CutsceneID = id;
        buildScript();
        declareTriggers();
    }

    protected abstract void buildScript();

    protected abstract void declareTriggers();

    protected void addTrigger(Trigger trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDefuseTrigger(Trigger trigger) {
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

    public Shot nextShot() {
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

    public Shot previewNextShot() {
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
    protected Shot lastSlide() { return script.get(script.size-1); }

    /**
     * Cheeky dev stuff
     */
    public void DEVELOPER_skipToEnd() {
        this.scriptIndex = script.size - 2;
    }

    /**
     * Trigger constructors. AKA, Arming functions.
     */
    protected void arm() {}
    protected void armCampaignFlagCutsceneTrigger(Campaign.FlagID flags, boolean defuser) {
        final Trigger t = new Trigger(flags);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTurnCutsceneTrigger(Integer turnToTrigger, boolean exactTurn, boolean defuser) {
        final Trigger t = new Trigger(turnToTrigger, exactTurn);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSingleUnitCombatCutsceneTrigger(Character.Name rosterID, boolean beforeCombat, boolean requiresAggressor, boolean defuser) {
        final Trigger t = new Trigger(rosterID, beforeCombat, requiresAggressor);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTwoUnitCombatCutsceneTrigger(Character.Name unit1, Character.Name unit2, boolean beforeCombat, boolean defuser) {
        final Trigger t = new Trigger(unit1, unit2, beforeCombat);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armOtherIDCutsceneTrigger(Character.Name id, boolean defuser) {
        final Trigger t = new Trigger(id);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSpecificUnitAreaCutsceneTrigger(Character.Name rosterID, Array<Vector2> areas, boolean defuser) {
        final Trigger t = new Trigger(rosterID, areas);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armSpecificUnitAreaCutsceneTrigger(Character.Name rosterID, Vector2 area, boolean defuser) {
        final Trigger t = new Trigger(rosterID, area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armAnyUnitAreaCutsceneTrigger(Vector2 area, boolean defuser) {
        final Trigger t = new Trigger(area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armDeathCutsceneTrigger(Character.Name deathOf, boolean defuser) {
        final Trigger t = new Trigger(deathOf);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armDeathCutsceneTrigger(TeamAlignment alignment, boolean defuser) {
        final Trigger t = new Trigger(alignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }
    protected void armTeamAlignmentAreaTrigger(Vector2 area, TeamAlignment requiredAlignment, boolean defuser) {
        final Trigger t = new Trigger(area, requiredAlignment);
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
    public void check() {}
    public void checkDeathTriggers(Character.Name roster) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
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

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(roster)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkDeathTriggers(TeamAlignment alignment) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkDeathTrigger(alignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(alignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkAreaTriggers(Character.Name rosterID, Vector2 tileCoordinate) {

        // Checks if specific unit stepped in specific area.

        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
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

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCampaignFlagTriggers(Campaign.FlagID flags) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCampaignFlagTrigger(flags)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
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


        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkTurnTrigger(turn)) {
                def.fire();
//                Gdx.app.log("CS", "def trigger");
                incrementDefuseCount();
            }
        }

        if(defused) return;
//        Gdx.app.log("CS", "checking...");

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkTurnTrigger(turn)) {
                trigger.fire();
//                Gdx.app.log("CS", "turn trigger");
                incrementTriggerCount();
            }
        }
    }
    public void checkOtherCutsceneTriggers(Cutscene.ID otherID) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkOtherCutsceneTrigger(otherID)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkOtherCutsceneTrigger(otherID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatStartTriggers(Character.Name rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatStartTriggers(Character.Name attacker, Character.Name defender) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatEndTriggers(Character.Name rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }
    public void checkCombatEndTriggers(Character.Name attacker, Character.Name defender) {
        if(defused) return;

        for(Trigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(Trigger trigger : triggers) {
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
    protected Shot script(Character.Name characterID, Cutscene.HorizontalPosition position, String line) {
        script.add(new Shot(new DialogDirection(characterID).position(position).line(line)));
        return script.get(script.size-1);
    }
    protected Shot script(Character.Name characterID, String line) {
        // either default location or infer from previous slides
        script.add(new Shot(new DialogDirection(characterID).line(line)));
        return script.get(script.size-1);
    }
    protected Shot script(String line) {
        // Infer based on previous slide
        script.add(new Shot(new DialogDirection(line)));
        return script.get(script.size-1);
    }
    protected Shot script(Shot shot) {
        script.add(shot);
        return script.get(script.size-1);
    }

    /**
     * Dialog Choreography:
     * These are things that happen while the conversation window is visible,
     * typically manipulating character portraits, or are generally agnostic
     * to the external world / screen state.
     */
    protected Shot choreographScreenTransition(WyrScreen screen) {
        script.add(new Shot(new Choreography(SCREEN_TRANSITION).setScreenForTransition(screen)));
        return script.get(script.size-1);
    }
    // TODO: create Pool class for (these copy gdx actions)
    protected Shot choreographScreenFadeOut() {
        script.add(new Shot(new Choreography(SCREEN_FADE_OUT)));
        return script.get(script.size-1);
    }
    protected Shot choreographScreenFadeIn() {
        script.add(new Shot(new Choreography(SCREEN_FADE_IN)));
        return script.get(script.size-1);
    }
    protected Shot choreographShortPause() {
        script.add(new Shot(new Choreography(PAUSE_SHORT)));
        return script.get(script.size-1);
    }
    protected Shot choreographLongPause() {
        script.add(new Shot(new Choreography(PAUSE_LONG)));
        return script.get(script.size-1);
    }
    protected Shot choreographRevealCondition(WyrWinCon condition) {
        script.add(new Shot(new Choreography(WINCON_REVEAL).setWinCon(condition)));
        return script.get(script.size-1);
    }
    protected Shot choreographSatisfyCondition(WyrWinCon condition) {
        script.add(new Shot(new Choreography(WINCON_SATISFY).setWinCon(condition)));
        return script.get(script.size-1);
    }
    protected Shot choreographEndScene() {
        script.add(new Shot(new Choreography(CUTSCENE_END)));
        return script.get(script.size-1);
    }
    protected Shot choreographAbility(WyrActor actor, GameKit.RPG.AbilityID abilityID) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).useAbility(abilityID))));
    }
    protected Shot choreographUseProp(WyrActor actor, WyrActor prop) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).useProp(prop))));
    }
    protected Shot choreographSpawn(WyrActor actor) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).spawn())));
    }
    protected Shot choreographDespawn(WyrActor actor) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).despawn())));
    }
    protected Shot choreographDeath(WyrActor unit) {
        return script(new Shot(new Choreography(new WyrInteraction(unit).kill())));
    }
    protected Shot choreographDestroy(WyrActor prop) {
        return script(new Shot(new Choreography(new WyrInteraction(prop).destroy())));
    }
    protected Shot choreographMoveBy(WyrActor actor, float x, float y) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).moveBy(x,y))));
    }
    protected Shot choreographFollowPath(WyrActor actor, GridPath path) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).followPath(path))));
    }
    protected Shot choreographFocusActor(WyrActor actor) {
        return script(new Shot(new Choreography(new WyrInteraction(actor).focus())));
    }
    protected Shot choreographFocusLocation(Vector2 location) {
        return script(new Shot(new Choreography(new WyrInteraction(null).focus(location))));
    }

    public static class Trigger {

        protected boolean hasFired   = false;
        protected boolean isCompound = false; // Requires 2 or more conditions to be met simultaneously.
        protected boolean defused    = false; // Individual triggers for cutscenes can be diffused, rather than the entire cutscene.

        protected int defuseThreshold = 1;
        protected int defuseCount = 0;

        protected Cutscene.TriggerType TriggerType;

        protected Campaign.FlagID triggerFlag;

        protected final Array<Character.Name> triggerUnits    = new Array<>();
        protected final Array<Vector2> triggerAreas         = new Array<>();
        protected final Array<Integer> triggerTurns         = new Array<>();
        protected final Array<Cutscene.ID> triggerCutscenes  = new Array<>();
        protected final Array<Trigger> defuseTriggers = new Array<>();

        protected boolean requiresTeamAlignment = false;
        protected boolean requiresAggressor     = false;
        protected boolean exactTurn             = false; // Should turn trigger fire only on exact turns or any turn greater than?

        protected TeamAlignment requiredTeamAlignment = TeamAlignment.PLAYER;

        public Trigger(Campaign.FlagID triggerFlag) {
            this.TriggerType = CAMPAIGN_FLAG;
            this.triggerFlag = triggerFlag;
            // TODO: can broaden the scope on this to include a flag array later if need arrises
        }
        public Trigger(Integer turnToTrigger, boolean exactTurn) {
            this.TriggerType = TURN;
            this.exactTurn = exactTurn;
            triggerTurns.add(turnToTrigger);
        }
        public Trigger(Character.Name rosterID, boolean beforeCombat, boolean requiresAggressor) {
            if(beforeCombat) {
                this.TriggerType = COMBAT_START;
            } else {
                this.TriggerType = COMBAT_END;
            }
            this.requiresAggressor = requiresAggressor;
            triggerUnits.add(rosterID);
        }
        public Trigger(Character.Name attacker, Character.Name defender, boolean beforeCombat) {
            isCompound = true;
            if(beforeCombat) {
                this.TriggerType = COMBAT_START;
            } else {
                this.TriggerType = COMBAT_END;
            }
            triggerUnits.add(attacker, defender);
        }
        public Trigger(Character.Name deathOf) {
            this.TriggerType = DEATH;
            triggerUnits.add(deathOf);
        }
        public Trigger(TeamAlignment deathOf) {
            this.TriggerType = DEATH;

            requiresTeamAlignment = true;
            requiredTeamAlignment = deathOf;
        }
        public Trigger(Cutscene.ID otherID) {
            this.TriggerType = OTHER_CUTSCENE;
            triggerCutscenes.add(otherID);
        }
        public Trigger(Character.Name rosterID, Array<Vector2> areas) {
            isCompound = true;
            this.TriggerType = AREA;
            triggerUnits.add(rosterID);
            for(Vector2 vector : areas) {
                triggerAreas.add(vector);
            }
        }
        public Trigger(Character.Name rosterID, Vector2 area) {
            isCompound = true;
            this.TriggerType = AREA;
            triggerUnits.add(rosterID);
            triggerAreas.add(area);
        }
        public Trigger(Vector2 area) {
            this.TriggerType = AREA;
            triggerAreas.add(area);
        }
        public Trigger(Vector2 area, TeamAlignment requiredTeamAlignment) {
            this.TriggerType = AREA;
            isCompound = true;
            this.requiredTeamAlignment = requiredTeamAlignment;
            this.requiresTeamAlignment = true;

            triggerAreas.add(area);
        }

        /*
         Other constructor ideas:
         - deathOf TeamAlignment
         - combatBy TeamAlignment
         - combatBy two specific TeamAlignments (i.e., enemy and other)
         */

        protected void incrementDefuseCount() {
            if(defused) return;
            defuseCount++;
            if(defuseCount >= defuseThreshold) defused = true;
        }
        public void setDefuseThreshold(int i) {
            defuseThreshold = i;
        }

        public boolean hasFired() {
            if(defused) return false;
            return hasFired;
        }
        public void fire() {
            if(defused) return;
            hasFired = true;
//        Gdx.app.log("CS trig", "fired");
        }
        public void addDefuseTrigger(Trigger trigger) {
            if(!defuseTriggers.contains(trigger,true)) defuseTriggers.add(trigger);
        }

        /**
         * CHECKERS (gotta eat)
         */
        public boolean checkCampaignFlagTrigger(Campaign.FlagID flag) {
            if(defused) return false;
            if(hasFired) return false;
            if(isCompound) return false;
            if(this.TriggerType != CAMPAIGN_FLAG) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;

                if(def.checkCampaignFlagTrigger(flag)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(this.triggerFlag == flag) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkDeathTrigger(Character.Name roster) {
            if(defused) return false;
            if(hasFired) return false;
            if(isCompound) return false;
            if(this.TriggerType != DEATH) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;

                if(def.checkDeathTrigger(roster)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(this.triggerUnits.contains(roster, true)) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkDeathTrigger(TeamAlignment alignment) {
            if(defused) return false;
            if(hasFired) return false;
            if(isCompound) return false;
            if(!requiresTeamAlignment) return false;
            if(this.TriggerType != DEATH) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;

                if(def.checkDeathTrigger(alignment)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(requiredTeamAlignment == alignment) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkAreaTrigger(Character.Name rosterID, Vector2 tileCoordinate) {
            if(defused) return false;
            if(!isCompound) return false;
            if(hasFired) return false;
            if(this.TriggerType != AREA) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            boolean found = false;

            for(Vector2 vector : triggerAreas) {
                if(vector.x == tileCoordinate.x && vector.y == tileCoordinate.y) {
                    found = true;
                    break;
                }
            }

            if(found && triggerUnits.contains(rosterID, true)) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkAreaTrigger(Vector2 tileCoordinate, TeamAlignment unitsAlignment) {
            if(defused) return false;
            if(hasFired) return false;
            if(requiresTeamAlignment && unitsAlignment != requiredTeamAlignment) return false;
            if(this.TriggerType != AREA) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkAreaTrigger(tileCoordinate, unitsAlignment)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }
            if(defused) return false;

            boolean found = false;

            for(Vector2 vector : triggerAreas) {
                if(vector.x == tileCoordinate.x && vector.y == tileCoordinate.y) {
                    found = true;
                    break;
                }
            }

            if(found && requiredTeamAlignment == unitsAlignment) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkTurnTrigger(int turn) {
            if(defused) return false;
            if(hasFired) return false;
            if(isCompound) return false;
            if(this.TriggerType != TURN) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkTurnTrigger(turn)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(exactTurn) {
                if(triggerTurns.contains(turn, true)) {
                    hasFired = true;
                    return true;
                }
            } else {
                if(turn >= triggerTurns.get(0)) {
                    hasFired = true;
                    return true;
                }
            }

            return false;
        }
        public boolean checkOtherCutsceneTrigger(Cutscene.ID otherID) {
            if(defused) return false;
            if(hasFired) return false;
            if(isCompound) return false;
            if(this.TriggerType != OTHER_CUTSCENE) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkOtherCutsceneTrigger(otherID)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(triggerCutscenes.contains(otherID, true)) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkCombatStartTrigger(Character.Name rosterID, boolean unitIsAggressor) {
            // This will trigger if the unit fights anyone.
            if(defused) return false;
            if(hasFired) return false;
            if(this.isCompound) return false;
            if(this.requiresAggressor && !unitIsAggressor) return false;
            if(this.requiresAggressor && !triggerUnits.contains(rosterID, true)) return false;
            if(this.TriggerType != COMBAT_START) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(triggerUnits.contains(rosterID, true)) {
                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkCombatStartTrigger(Character.Name attacker, Character.Name defender) {
            // This will only trigger if two specific units fight each other. (Regardless of who starts it.)
            if(defused) return false;
            if(hasFired) return false;
            if(this.TriggerType != COMBAT_START) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkCombatStartTrigger(attacker, defender)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(triggerUnits.contains(attacker, true) &&
                triggerUnits.contains(defender, true)) {

                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkCombatEndTrigger(Character.Name rosterID, boolean unitIsAggressor) {
            if(defused) return false;
            if(hasFired) return false;
            if(this.isCompound) return false;
            if(this.requiresAggressor && !unitIsAggressor) return false;
            if(this.requiresAggressor && !triggerUnits.contains(rosterID, true)) return false;
            if(this.TriggerType != COMBAT_END) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(triggerUnits.contains(rosterID, true)) {

                hasFired = true;
                return true;
            }

            return false;
        }
        public boolean checkCombatEndTrigger(Character.Name attacker, Character.Name defender) {
            if(defused) return false;
            if(hasFired) return false;
            if(this.TriggerType != COMBAT_END) return false;

            for(Trigger def : defuseTriggers) {
                if(def.hasFired()) continue;
                if(def.checkCombatEndTrigger(attacker, defender)) {
                    def.fire();
                    incrementDefuseCount();
                }
            }

            if(defused) return false;

            if(triggerUnits.contains(attacker, true) &&
                triggerUnits.contains(defender, true)) {

                hasFired = true;
                return true;
            }

            return false;
        }

    }

    public static class Shot {

        protected Utilities.Speed textSpeed;

        protected Cutscene.Background backgroundID = Cutscene.Background.NONE;
        protected Cutscene.Foreground foregroundID = Cutscene.Foreground.NONE;

        protected DialogDirection focusedDirection = null; // always has lines
        protected DialogDirection doubleSpeakDirection = null; // only has lines when used
        protected Array<DialogDirection> supportingCharacters = new Array<>(); // silent

        protected boolean fullscreen         = false;
        protected boolean omitFromLog        = false;
        protected boolean autoProgressToNext = false;
        protected boolean worldChoreographed = false;
        protected boolean stageChoreographed = false;

        protected int snapToIndex = 0;

        protected Choreography choreography;

        public Shot() {}

        public Shot(Choreography choreography) {
            this.choreography = choreography;
            switch(choreography.getChoreoStage()) {
                case DIALOG:
                    stageChoreographed = true;
                    break;
                case WORLD:
                    worldChoreographed = true;
                    break;
            }
        }

        public Shot(DialogDirection direction) {
            focusedDirection = direction;
        }

        public Shot(Character.Name focusedCharacterID, Character.Expression expression, String dialog) {
            focusedDirection = new DialogDirection(focusedCharacterID).expression(expression).line(dialog);
        }

        public Shot focus(DialogDirection character)         { this.focusedDirection = character;        return this; }
        public Shot doubleSpeak(DialogDirection character)   { this.doubleSpeakDirection = character;    return this; }
        public Shot autoplay()                               { autoProgressToNext = true;                return this; }
        public Shot fullscreen()                             { fullscreen = true;                        return this; }
        public Shot omit()                                   { omitFromLog = true;                       return this; }
        public Shot addSupporting(DialogDirection character) { this.supportingCharacters.add(character); return this; }
        public Shot background(Cutscene.Background backgroundID) { this.backgroundID = backgroundID;     return this; }
        public Shot foreground(Cutscene.Foreground foregroundID) { this.foregroundID = foregroundID;     return this; }
        public Shot textSpeed(Utilities.Speed textSpeed) { this.textSpeed = textSpeed;               return this; }
        public Shot snapToIndex(int index) { this.snapToIndex = index;                 return this; }

        public DialogDirection getFocusedDirection()            { return focusedDirection; }
        public DialogDirection getDoubleSpeakDirection()        { return doubleSpeakDirection; }
        public Array<DialogDirection> getSupportingCharacters() { return supportingCharacters; }

        public void choreograph(Choreography choreography) { this.choreography = choreography; stageChoreographed = true; }

        public Choreography getChoreo() { return choreography; }

        public Cutscene.Background getBackgroundID() { return backgroundID; }
        public Cutscene.Foreground getForegroundID() { return foregroundID; }
        public boolean       usesDoubleSpeak()    { return doubleSpeakDirection != null;}
        public boolean       autoProgresses()     { return autoProgressToNext; }
        public boolean       isFullscreen()       { return fullscreen; }
        public boolean       omittedFromLog()     { return omitFromLog; }
        public boolean       isChoreographed()    { return stageChoreographed || worldChoreographed; }
        public boolean       stageChoreographed() { return stageChoreographed; }
        public boolean       worldChoreographed() { return worldChoreographed; }
        public Utilities.Speed getDisplaySpeed()    { return textSpeed; }
        public int           getSnapToIndex()     { return snapToIndex; }

    }

    public static class Choreography extends Subjectivity {

        // refactored combination of CutsceneFrameChoreography and DialogAction

        // "Choreography is stuff that happens on the map / over-world,
        // as opposed to DialogActions which happen inside the Conversation window."

        private final Cutscene.Choreography.ChoreoStage choreoStage;
        protected Enum<?>          characterID            = null;
        protected WyrInteraction worldInteraction       = null;
        protected Cutscene.Choreography.DialogChoreoType dialogChoreoType       = null;
        protected Enum<?>          associatedCampaignFlag = null;
        protected Vector2          associatedCoordinate   = null;
        protected WyrScreen        screenForTransition    = null;
        protected Runnable         payload                = null;
        protected WyrWinCon        associatedWinCon       = null;
        protected boolean          loops                  = false;
        protected boolean          playParallel           = false;
        protected Utilities.Speed  actSpeed               = Utilities.Speed.NORMAL;
        protected LoopCondition    loopCondition = null;

        public Choreography(WyrInteraction worldInteraction) {
            this.choreoStage = Cutscene.Choreography.ChoreoStage.WORLD;
            this.worldInteraction = worldInteraction;
        }
        public Choreography(Cutscene.Choreography.DialogChoreoType dialogChoreoType) {
            this.choreoStage = Cutscene.Choreography.ChoreoStage.DIALOG;
            this.dialogChoreoType = dialogChoreoType;
        }

        // SETTERS
        public Choreography loop()                                   { this.loops = true; return this;}
        public Choreography setCoordinate(float column, float row)   { this.associatedCoordinate = new Vector2(column, row); return this; }
        public Choreography setCoordinate(Vector2 coordinates)       { this.associatedCoordinate = coordinates; return this; }
        public Choreography setLocation(RPGridTile tile)               { this.associatedCoordinate = new Vector2(tile.getXColumn(), tile.getYRow()); return this; }
        public Choreography setFlag(Enum<?> flagID)                  { this.associatedCampaignFlag = flagID; return this; }
        public Choreography setScreenForTransition(WyrScreen screen) { this.screenForTransition = screen; return this; }
        public Choreography setWinCon(WyrWinCon condition)           { this.associatedWinCon = condition; return this; }

        // GETTERS
        public Cutscene.Choreography.ChoreoStage getChoreoStage()      { return choreoStage; }
        public WyrInteraction   getWorldInteraction() { return worldInteraction; }
        public Cutscene.Choreography.DialogChoreoType getDialogChoreoType() { return dialogChoreoType; }

        public boolean loops() { return loops;}
        public ScreenAdapter getScreenForTransition() { return screenForTransition; }
        public Enum<?> getFlag() { return associatedCampaignFlag; }
        public Vector2 getLocation() { return associatedCoordinate; }
        public Enum<?> getCharacterID() { return characterID; }

        public static class TopDown2D {

        }

        public static class RPG {
            protected GameKit.RPG.AbilityID abilityID;
            public GameKit.RPG.AbilityID getAbility() {
                return abilityID;
            }
        }

    }

    public static class DialogDirection implements Wyr{
        private Character.Name              characterID = null;
        private Character.Expression        expression  = null;
        private Cutscene.HorizontalPosition position    = LEFT;
        private boolean                     facingLeft  = false;
        private String                      preferredName;
        private String                      line;

        public DialogDirection(String line) {
            // when character is null, player assigns line
            // to the last referenced character in script
            this.line = line;
        }
        public DialogDirection(Character.Name characterID) { this.characterID = characterID; }

        public DialogDirection expression(Character.Expression expression) {
            this.expression = expression;
            return this;
        }
        public DialogDirection position(Cutscene.HorizontalPosition position) {
            this.position = position;
            return this;
        }
        public DialogDirection faceLeft() {
            return faceLeft(true);
        }
        public DialogDirection faceRight() {
            return faceLeft(false);
        }
        private DialogDirection faceLeft(boolean faceLeft) {
            if(facingLeft == faceLeft) return this;
            this.facingLeft = faceLeft;
            return this;
        }
        public DialogDirection preferredName(String name) {
            this.preferredName = name;
            return this;
        }
        public DialogDirection line(String line) {
            this.line = line;
            return this;
        }

        public Character.Name getCharacter() {
            return characterID;
        }
        public boolean isFacingLeft() {
            return facingLeft;
        }
        public Character.Expression getExpression() {
            return expression;
        }
        public Cutscene.HorizontalPosition getPosition() {
            return position;
        }
        public String getPreferredName() {
            return preferredName;
        }
        public String getLine() {
            return line;
        }
    }

}
