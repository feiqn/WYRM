package com.feiqn.wyrm.logic.handlers.cutscene.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneTrigger;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.TeamAlignment;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import static com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition.*;

public abstract class CutsceneScript {

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

    protected final Array<CutsceneFrame> slideshow; // Add frames programmatically in order, start from index 0
    protected final Array<CutsceneTrigger> triggers;
    protected final Array<CutsceneTrigger> defuseTriggers;

    protected int triggerThreshold;
    protected int triggerCount;
    protected int defuseThreshold;
    protected int defuseCount;

    protected final CutsceneID cutsceneID;

    protected LoopCondition loopCondition;



    protected CutsceneScript(CutsceneID id) {
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

        this.cutsceneID = id;

        setSeries(); // TODO: fill from JSON
        declareTriggers();
    }

    protected void addTrigger(CutsceneTrigger trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDefuseTrigger(CutsceneTrigger trigger) {
        if(!defuseTriggers.contains(trigger, true)) defuseTriggers.add(trigger);
    }

    public void loop(LoopCondition loopCondition) {
        looping = true;
        this.loopCondition = loopCondition;
    }


    /**
     * Trigger checks
     */
    public void checkDeathTriggers(UnitRoster roster) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
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

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(roster)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkDeathTriggers(TeamAlignment alignment) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkDeathTrigger(alignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(alignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkAreaTriggers(UnitRoster rosterID, Vector2 tileCoordinate) {

        // Checks if specific unit stepped in specific area.

        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(rosterID, tileCoordinate)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
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

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(tileCoordinate, unitsTeamAlignment)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkTurnTriggers(int turn) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkTurnTrigger(turn)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkTurnTrigger(turn)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkOtherCutsceneTrigger(otherID)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkOtherCutsceneTrigger(otherID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatStartTriggers(UnitRoster rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatStartTriggers(UnitRoster attacker, UnitRoster defender) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatStartTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatEndTriggers(UnitRoster rosterID, boolean unitIsAggressor) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(rosterID, unitIsAggressor)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatEndTriggers(UnitRoster attacker, UnitRoster defender) {
        if(defused) return;

        for(CutsceneTrigger def : defuseTriggers) {
            if(def.hasFired()) continue;
            if(def.checkCombatEndTrigger(attacker, defender)) {
                def.fire();
                incrementDefuseCount();
            }
        }

        if(defused) return;

        for(CutsceneTrigger trigger : triggers) {
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
    public CutsceneFrame nextFrame() {
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

    public CutsceneFrame previewNextFrame() {
        try {
            return slideshow.get(frameIndex);
        } catch (Exception ignored) {
            Gdx.app.log("cutScript", "bad preview");
            return new CutsceneFrame();
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
    public CutsceneID getCutsceneID() {
        return cutsceneID;
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
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.FADE_OUT_TO_BLACK);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographTransitionScreen(ScreenAdapter screen) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.SCREEN_TRANSITION);

        choreography.setScreenForTransition(screen);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographShortPause() {
        final CutsceneFrame frame = new CutsceneFrame();
        frame.choreograph(new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.SHORT_PAUSE));
        slideshow.add(frame);
    }
    protected void choreographLinger() {
        final CutsceneFrame frame = new CutsceneFrame();
        frame.choreograph(new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.LINGER));
        slideshow.add(frame);
    }
    protected void choreographUseAbility(SimpleUnit subject, Abilities ability, SimpleUnit target) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.ABILITY);

        choreography.setSubject(subject);
        choreography.setObject(target);
        choreography.setAbility(ability);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographBallistaAttack(SimpleUnit subject, SimpleUnit target) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.BALLISTA_ATTACK);

        choreography.setSubject(subject);
        choreography.setObject(target);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographDespawn(SimpleUnit subject) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.DESPAWN);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographSpawn(SimpleUnit subject, int column, int row) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.SPAWN);

        choreography.setSubject(subject);
        choreography.setLocation(column,row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographDeath(SimpleUnit subject) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.UNIT_DEATH);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographMoveTo(SimpleUnit subject, int column, int row) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.MOVE);

        choreography.setSubject(subject);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnLocation(int column, int row) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.FOCUS_TILE);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnUnit(SimpleUnit focusCamera) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.FOCUS_UNIT);
        choreography.setSubject(focusCamera);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographRevealVictCon(CampaignFlags flagID) {
        final CutsceneFrame frame = new CutsceneFrame();

        final CutsceneFrameChoreography choreography = new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.REVEAL_VICTCON);
        choreography.setVictConFlagID(flagID);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographEndCutscene() {
        final CutsceneFrame frame = new CutsceneFrame();
        frame.choreograph(new CutsceneFrameChoreography(CutsceneFrameChoreography.Type.END_OF_CUTSCENE));
        slideshow.add(frame);
    }



    protected void set(CharacterExpression expression, String txt) {
        set(expression, txt, LEFT);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position) {
        set(expression, txt, "", position, false, false);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft) {
        set(expression, txt, "", position, facingLeft, false);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft, boolean autoNext) {
        set(expression, txt, "", position, facingLeft, autoNext);
    }
    protected void set(CharacterExpression expression, String txt, String name, SpeakerPosition pos, boolean facingLeft, boolean autoAutoPlay) {
        final CutsceneFrame frame = new CutsceneFrame();

        frame.setText(txt);
        frame.setFocusedPosition(pos);
        frame.setFocusedExpression(expression);
        frame.setFacingLeft(facingLeft);
        frame.setAutoplayNext(autoAutoPlay);

        slideshow.add(frame);
    }

    protected void setMultiple(SpeakerPosition focusedPosition, SpeakerPosition... positions) {
        // TODO: this ^ won't work. hashmap?
    }

    protected void setAll(SpeakerPosition focusedPosition, String txt, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        setAll(focusedPosition, txt, "", farLeft, left, leftOfCenter, center, rightOfCenter, right, farRight);
    }

    protected void setAll(SpeakerPosition focusedPosition, String txt, String name, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        final CutsceneFrame frame = new CutsceneFrame();

        frame.setComplex(true);
        frame.setFocusedPosition(focusedPosition);
        frame.setText(txt);
        frame.setFocusedName(name);

        switch(focusedPosition) {
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

    protected void setParallelActions(DialogAction... actions) {
//        ParallelAction pAct = new ParallelAction();
        for(DialogAction action : actions) {

        }
    }

    protected void setComplexAction(Array<Action> actions) {

    }

    protected void defineAndSetNextAction(Action action) {

    }




    protected CutsceneTrigger lastTrigger() { return triggers.get(triggers.size-1); }

    protected CutsceneTrigger lastDefuseTrigger() { return defuseTriggers.get(defuseTriggers.size-1); }

    protected CutsceneFrame lastFrame() { return slideshow.get(slideshow.size-1); }

    protected void armTurnCutsceneTrigger(Integer turnToTrigger, boolean exactTurn, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(turnToTrigger, exactTurn);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armSingleUnitCombatCutsceneTrigger(UnitRoster rosterID, boolean beforeCombat, boolean requiresAggressor, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(rosterID, beforeCombat, requiresAggressor);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armTwoUnitCombatCutsceneTrigger(UnitRoster unit1, UnitRoster unit2, boolean beforeCombat, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(unit1, unit2, beforeCombat);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armOtherIDCutsceneTrigger(CutsceneID id, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(id);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armSpecificUnitAreaCutsceneTrigger(UnitRoster rosterID, Array<Vector2> areas, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(rosterID, areas);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armSpecificUnitAreaCutsceneTrigger(UnitRoster rosterID, Vector2 area, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(rosterID, area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armAnyUnitAreaCutsceneTrigger(Vector2 area, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(area);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armDeathCutsceneTrigger(UnitRoster deathOf, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(deathOf);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armDeathCutsceneTrigger(TeamAlignment alignment, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(alignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

    protected void armTeamAlignmentAreaTrigger(Vector2 area, TeamAlignment requiredAlignment, boolean defuser) {
        final CutsceneTrigger t = new CutsceneTrigger(area, requiredAlignment);
        if(defuser) {
            addDefuseTrigger(t);
        } else {
            addTrigger(t);
        }
    }

}
