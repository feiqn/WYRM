package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.triggers.GridCutsceneTrigger;
import com.feiqn.wyrm.OLD_DATA.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.WyRPG;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.*;
import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.HorizontalPosition.*;

public abstract class OLD_CutsceneScript {

    // Who the hell is Jason? I don't know any Jason!
    // This is OBJECT ORIENTED PROGRAMMING.

    // Creates, holds, and distributes an ordered list of script frames.

    public enum LoopCondition {
        MULTIPLICATIVE_THRESHOLD,
        BROKEN_THRESHOLD
    }

    protected boolean hasPlayed;
    protected boolean readyToPlay;
    protected boolean looping;
    protected boolean defused;

    protected int frameIndex;

    protected final Array<OLD_CutsceneFrame> slideshow; // Add frames programmatically in order, start from index 0
    protected final Array<GridCutsceneTrigger> triggers;
    protected final Array<GridCutsceneTrigger> defuseTriggers;

    protected int triggerThreshold;
    protected int triggerCount;
    protected int defuseThreshold;
    protected int defuseCount;

    protected final CutsceneID thisCutsceneID;

    protected LoopCondition loopCondition;

    protected OLD_CutsceneScript(CutsceneID id) {
        slideshow       = new Array<>();
        triggers        = new Array<>();
        defuseTriggers  = new Array<>();

        hasPlayed = false;
        defused   = false;
        looping   = false;

        frameIndex       = 0;
        triggerCount     = 0;
        triggerThreshold = 1;
        defuseCount      = 0;
        defuseThreshold  = 1;

        this.thisCutsceneID = id;

        setSeries();
        declareTriggers();
    }

    protected void addTrigger(GridCutsceneTrigger trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDefuseTrigger(GridCutsceneTrigger trigger) {
        if(!defuseTriggers.contains(trigger, true)) defuseTriggers.add(trigger);
    }

    public void loop(LoopCondition loopCondition) {
        looping = true;
        this.loopCondition = loopCondition;
    }


    /**
     * Trigger checks
     */
    public void checkDeathTriggers(CharacterID roster) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
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

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(roster)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkDeathTriggers(TeamAlignment alignment) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkDeathTrigger(alignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
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

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
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

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCampaignFlagTriggers(FlagID flags) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCampaignFlagTrigger(flags)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCampaignFlagTrigger(flags)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkTurnTriggers(int turn) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkTurnTrigger(turn)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkTurnTrigger(turn)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkOtherCutsceneTrigger(otherID)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkOtherCutsceneTrigger(otherID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatStartTriggers(CharacterID rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatStartTriggers(CharacterID attacker, CharacterID defender) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatEndTriggers(CharacterID rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatEndTriggers(CharacterID attacker, CharacterID defender) {
        if(defused) return;

        for(GridCutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(GridCutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }



    /**
     * @return a copy of the next set dialog frame, while advancing the index counter for next call.
     */
    public OLD_CutsceneFrame nextFrame() {
        /* We want to return the frame index we are currently on. For example, on
         * first call, the frameIndex is 0, and we want to display the first frame, index 0.
         * On index 0, this will return frame 0 and bump the index up to 1, and so on.
         */
        if(defused) return null;
        if(slideshow.size == 0) setSeries();
        if(readyToPlay) {
            readyToPlay = false;
            if(!looping) {
                hasPlayed = true;
            }
        }

        frameIndex++;
        return slideshow.get(frameIndex - 1);
    }

    public OLD_CutsceneFrame previewNextFrame() {
        try {
            return slideshow.get(frameIndex);
        } catch (Exception ignored) {
            Gdx.app.log("cutScript", "bad preview");
            return new OLD_CutsceneFrame();
        }
    }

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

    protected abstract void declareTriggers();

    protected abstract void setSeries();

    public boolean continues() {
        if(defused) return false;
        boolean continues = false;
        try {
//            Gdx.app.log("continues", "frameIndex: " + frameIndex + " , slideshow size: " + slideshow.size);
            if(frameIndex == 0) {
                if(slideshow.size == 0) setSeries();
//                Gdx.app.log("cutsceneScript", "continues true");
                return true;
            }
            int upperMax = slideshow.size;
            if(frameIndex < upperMax) {
//                Gdx.app.log("continues", "read size as > index");
                continues = true;
            }
        } catch(Exception e) {
            Gdx.app.log("continues", "script failed try/catch");
        }

        if(!continues && looping) {
            resetLoop();
        }
//        Gdx.app.log("cutsceneScript", "continues: " + continues);
        return continues;
    }

    private void resetLoop() {
        if(!looping) return;
        Gdx.app.log("cutsceneScript", "resetting to loop again");

        switch(loopCondition) {
            case MULTIPLICATIVE_THRESHOLD:
                triggerCount = 0;
            case BROKEN_THRESHOLD:
                readyToPlay = false;
            default:
                frameIndex = 0;
                triggers.clear();
                declareTriggers();
                hasPlayed = false;
                break;
        }
    }



    /**
     * GETTERS
     */
    public CutsceneID getThisCutsceneID() {
        return thisCutsceneID;
    }

//    public boolean hasPlayed() {
//        return hasPlayed;
//    }
//
//    public boolean hasDefused() {
//        return defused;
//    }

    public boolean isReadyToPlay() {
        if(defused || hasPlayed) return false;
        return readyToPlay;
    }

    public void DEVELOPER_skipToEnd() {
        this.frameIndex = slideshow.size - 2;
    }



    /**
     * convenience methods for creating new dialog frames
     */
    protected void choreographFadeOut() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.FADE_OUT_TO_BLACK);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographTransitionScreen(ScreenAdapter screen) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.SCREEN_TRANSITION);

        choreography.setScreenForTransition(screen);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographShortPause() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.SHORT_PAUSE));
        slideshow.add(frame);
    }
    protected void choreographLinger() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.LINGER));
        slideshow.add(frame);
    }
    protected void choreographUseAbility(OLD_SimpleUnit subject, WyRPG.AbilityID ability, OLD_SimpleUnit target) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.ABILITY);

        choreography.setSubject(subject);
        choreography.setObject(target);
        choreography.setAbility(ability);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographBallistaAttack(OLD_SimpleUnit subject, OLD_SimpleUnit target) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.BALLISTA_ATTACK);

        choreography.setSubject(subject);
        choreography.setObject(target);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographDespawn(OLD_SimpleUnit subject) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.DESPAWN);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographSpawn(OLD_SimpleUnit subject, int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.SPAWN);

        choreography.setSubject(subject);
        choreography.setLocation(column,row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographDeath(OLD_SimpleUnit subject) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.UNIT_DEATH);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographMoveTo(OLD_SimpleUnit subject, int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.MOVE);

        choreography.setSubject(subject);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnLocation(int column, int row) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.FOCUS_TILE);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnUnit(OLD_SimpleUnit focusCamera) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.FOCUS_UNIT);
        choreography.setSubject(focusCamera);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographRevealVictCon(FlagID flagID) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        final OLD_CutsceneFrameChoreography choreography = new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.REVEAL_VICTCON);
        choreography.setVictConFlagID(flagID);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographEndCutscene() {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();
        frame.choreograph(new OLD_CutsceneFrameChoreography(OLD_CutsceneFrameChoreography.OLD_ChoreoType.END_OF_CUTSCENE));
        slideshow.add(frame);
    }



    protected void set(OLD_CharacterExpression expression, String txt) {
        set(expression, txt, LEFT);
    }
    protected void set(OLD_CharacterExpression expression, String txt, HorizontalPosition horizontalPosition) {
        set(expression, txt, "", horizontalPosition, false, false);
    }
    protected void set(OLD_CharacterExpression expression, String txt, HorizontalPosition horizontalPosition, boolean facingLeft) {
        set(expression, txt, "", horizontalPosition, facingLeft, false);
    }
    protected void set(OLD_CharacterExpression expression, String txt, HorizontalPosition horizontalPosition, boolean facingLeft, boolean autoNext) {
        set(expression, txt, "", horizontalPosition, facingLeft, autoNext);
    }
    protected void set(OLD_CharacterExpression expression, String txt, String name, HorizontalPosition pos, boolean facingLeft, boolean autoAutoPlay) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        frame.setText(txt);
        frame.setFocusedPosition(pos);
        frame.setFocusedExpression(expression);
        frame.setFacingLeft(facingLeft);
        frame.setAutoplayNext(autoAutoPlay);

        slideshow.add(frame);
    }
    protected void setMultiple(HorizontalPosition focusedHorizontalPosition, HorizontalPosition... horizontalPositions) {
        // TODO: this ^ won't work. hashmap?
    }
    protected void setAll(HorizontalPosition focusedHorizontalPosition, String txt, OLD_CharacterExpression farLeft, OLD_CharacterExpression left, OLD_CharacterExpression leftOfCenter, OLD_CharacterExpression center, OLD_CharacterExpression rightOfCenter, OLD_CharacterExpression right, OLD_CharacterExpression farRight) {
        setAll(focusedHorizontalPosition, txt, "", farLeft, left, leftOfCenter, center, rightOfCenter, right, farRight);
    }
    protected void setAll(HorizontalPosition focusedHorizontalPosition, String txt, String name, OLD_CharacterExpression farLeft, OLD_CharacterExpression left, OLD_CharacterExpression leftOfCenter, OLD_CharacterExpression center, OLD_CharacterExpression rightOfCenter, OLD_CharacterExpression right, OLD_CharacterExpression farRight) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        frame.setComplex(true);
        frame.setFocusedPosition(focusedHorizontalPosition);
        frame.setText(txt);
        frame.setFocusedName(name);

        switch(focusedHorizontalPosition) {
            case FAR_LEFT:
                frame.setFocusedExpression(farLeft);
                break;
            case LEFT:
                frame.setFocusedExpression(left);
                break;
            case LEFT_OF_CENTER:
                frame.setFocusedExpression(leftOfCenter);
                break;
            case CENTER:
                frame.setFocusedExpression(center);
                break;
            case RIGHT_OF_CENTER:
                frame.setFocusedExpression(rightOfCenter);
                break;
            case RIGHT:
                frame.setFocusedExpression(right);
                break;
            case FAR_RIGHT:
                frame.setFocusedExpression(farRight);
                break;
        }

        frame.setExpressionAtPosition(farLeft,       FAR_LEFT);
        frame.setExpressionAtPosition(left,          LEFT);
        frame.setExpressionAtPosition(leftOfCenter,  LEFT_OF_CENTER);
        frame.setExpressionAtPosition(center,        CENTER);
        frame.setExpressionAtPosition(rightOfCenter, RIGHT_OF_CENTER);
        frame.setExpressionAtPosition(right,         RIGHT);
        frame.setExpressionAtPosition(rightOfCenter, RIGHT_OF_CENTER);

        slideshow.add(frame);
    }

    protected void setParallelActions(OLD_DialogAction... actions) {
//        ParallelAction pAct = new ParallelAction();
        for(OLD_DialogAction action : actions) {

        }
    }

    protected void setComplexAction(Array<Action> actions) {

    }

    protected void defineAndSetNextAction(Action action) {

    }




    protected GridCutsceneTrigger lastTrigger() { return triggers.get(triggers.size-1); }

    protected GridCutsceneTrigger lastDefuseTrigger() { return defuseTriggers.get(defuseTriggers.size-1); }

    protected OLD_CutsceneFrame lastFrame() { return slideshow.get(slideshow.size-1); }

    protected void armCampaignFlagCutsceneTrigger(FlagID flags, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(flags);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armTurnCutsceneTrigger(Integer turnToTrigger, boolean exactTurn, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(turnToTrigger, exactTurn);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armSingleUnitCombatCutsceneTrigger(CharacterID rosterID, boolean beforeCombat, boolean requiresAggressor, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(rosterID, beforeCombat, requiresAggressor);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armTwoUnitCombatCutsceneTrigger(CharacterID unit1, CharacterID unit2, boolean beforeCombat, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(unit1, unit2, beforeCombat);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armOtherIDCutsceneTrigger(CutsceneID id, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(id);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armSpecificUnitAreaCutsceneTrigger(CharacterID rosterID, Array<Vector2> areas, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(rosterID, areas);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armSpecificUnitAreaCutsceneTrigger(CharacterID rosterID, Vector2 area, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(rosterID, area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armAnyUnitAreaCutsceneTrigger(Vector2 area, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armDeathCutsceneTrigger(CharacterID deathOf, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(deathOf);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armDeathCutsceneTrigger(TeamAlignment alignment, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(alignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armTeamAlignmentAreaTrigger(Vector2 area, TeamAlignment requiredAlignment, boolean defuser) {
        final GridCutsceneTrigger t = new GridCutsceneTrigger(area, requiredAlignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

}
