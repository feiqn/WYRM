package com.feiqn.wyrm.logic.handlers.cutscene.dialog;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignFlags;
import com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneID;
import com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition;
import com.feiqn.wyrm.logic.handlers.cutscene.triggers.CutsceneTrigger;
import com.feiqn.wyrm.models.unitdata.Abilities;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.models.unitdata.units.SimpleUnit;

import static com.feiqn.wyrm.logic.handlers.cutscene.CharacterExpression.*;
import static com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneFrame.Background_ID.*;
import static com.feiqn.wyrm.logic.handlers.cutscene.SpeakerPosition.*;

public abstract class CutsceneScript {

    // Who the hell is Jason? I don't know any Jason!
    // This is OBJECT ORIENTED PROGRAMMING.

    // Creates, holds, and distributes an ordered list of script frames.

    protected boolean hasPlayed;
    protected boolean readyToPlay;
    protected boolean looping;
    protected boolean diffused;

    protected int frameIndex;

    protected final Array<CutsceneFrame> slideshow; // Add frames programmatically in order, start from index 0, remove as you go
    protected final Array<CutsceneTrigger> triggers;
    protected final Array<CutsceneTrigger> diffuseTriggers;

    protected int triggerThreshold;
    protected int triggerCount;
    protected int diffuseThreshold;
    protected int diffuseCount;

    protected final CutsceneID cutsceneID;



    protected CutsceneScript(CutsceneID id) {
        slideshow       = new Array<>();
        triggers        = new Array<>();
        diffuseTriggers = new Array<>();

        hasPlayed = false;
        diffused  = false;

        frameIndex       = 0;
        triggerCount     = 0;
        triggerThreshold = 1;
        diffuseCount     = 0;
        diffuseThreshold = 1;

        this.cutsceneID = id;


        setSeries(); // TODO: fill from JSON
    }

    protected void addTrigger(CutsceneTrigger trigger) {
        if(!triggers.contains(trigger, true)) triggers.add(trigger);
    }

    protected void addDiffuseTrigger(CutsceneTrigger trigger) {
        if(!diffuseTriggers.contains(trigger, true)) diffuseTriggers.add(trigger);
    }


    /**
     * Trigger checks
     */
    public void checkDeathTriggers(UnitRoster roster) {
        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkDeathTrigger(roster)) {
                diff.fire();
                incrementDiffuseCount();
                // Avoid calling break; here,
                // in order to allow multiple triggers
                // to have overlapping conditions,
                // and the potential for complex
                // and contextual trigger handling.
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkDeathTrigger(roster)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkAreaTriggers(UnitRoster rosterID, Vector2 tileCoordinate) {

        // Checks if specific unit stepped in specific area.

        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkAreaTrigger(rosterID, tileCoordinate)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(rosterID, tileCoordinate)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkAreaTriggers(Vector2 tileCoordinate, boolean isPlayerUnit) {

        // Checks if anyone, or just if player's own units,
        // stepped in the specific area.

        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkAreaTrigger(tileCoordinate, isPlayerUnit)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkAreaTrigger(tileCoordinate, isPlayerUnit)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkTurnTriggers(int turn) {
        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkTurnTrigger(turn)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkTurnTrigger(turn)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkOtherCutsceneTriggers(CutsceneID otherID) {
        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkOtherCutsceneTrigger(otherID)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkOtherCutsceneTrigger(otherID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatStartTriggers(UnitRoster rosterID) {
        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkCombatStartTrigger(rosterID)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(rosterID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatStartTriggers(UnitRoster attacker, UnitRoster defender) {
        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkCombatStartTrigger(attacker, defender)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatStartTrigger(attacker, defender)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatEndTriggers(UnitRoster rosterID) {
        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkCombatEndTrigger(rosterID)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

        for(CutsceneTrigger trigger : triggers) {
            if(trigger.hasFired()) continue;
            if(trigger.checkCombatEndTrigger(rosterID)) {
                trigger.fire();
                incrementTriggerCount();
            }
        }
    }

    public void checkCombatEndTriggers(UnitRoster attacker, UnitRoster defender) {
        if(diffused) return;

        for(CutsceneTrigger diff : diffuseTriggers) {
            if(diff.hasFired()) continue;
            if(diff.checkCombatEndTrigger(attacker, defender)) {
                diff.fire();
                incrementDiffuseCount();
            }
        }

        if(diffused) return;

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
        if(diffused) return null;
        if(slideshow.get(frameIndex) == null) setSeries();
        if(!hasPlayed) {
            hasPlayed = true;
            readyToPlay = false;
        }

        frameIndex++;
        return slideshow.get(frameIndex - 1);
    }

    public CutsceneFrame previewNextFrame() {
        try {
            return slideshow.get(frameIndex);
        } catch (Exception ignored) {
            return new CutsceneFrame();
        }
    }

    public void incrementTriggerCount() {
        if(readyToPlay) return;
        triggerCount++;
        if(triggerCount >= triggerThreshold) readyToPlay = true;
    }

    public void incrementDiffuseCount() {
        if(diffused) return;
        diffuseCount++;
        if(diffuseCount >= diffuseThreshold) diffused = true;
    }

    protected abstract void declareTriggers();

    protected abstract void setSeries();

    public boolean continues() {
        if(diffused) return false;
        return slideshow.size > frameIndex;
    }


    /**
     * GETTERS
     */
    public CutsceneID getCutsceneID() {
        return cutsceneID;
    }

    public Array<CutsceneTrigger> getTriggers() {
        return triggers;
        // prepare this for removal most likely
    }

//    public boolean hasPlayed() {
//        return hasPlayed;
//    }
//
//    public boolean hasDiffused() {
//        return diffused;
//    }

    public boolean isReadyToPlay() {
        if(diffused || hasPlayed) return false;
        return readyToPlay;
    }


    /**
     * convenience methods for creating new dialog frames
     */
    private void setComplexAction(Array<Action> actions) {

    }
    private void defineNextAction(Action action) {

    }
    /**
     * All the choreograph function calls should probably have been one
     * giant switch statement or something; but here we are and there's
     * no turning back.
     */
    protected void choreographFadeOut() {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.FADE_OUT_TO_BLACK);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographTransitionScreen(ScreenAdapter screen) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.SCREEN_TRANSITION);

        choreography.setScreenForTransition(screen);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographShortPause() {
        final CutsceneFrame frame = new CutsceneFrame();
        frame.choreograph(new DialogChoreography(DialogChoreography.Type.SHORT_PAUSE));
        slideshow.add(frame);
    }
    protected void choreographLinger() {
        final CutsceneFrame frame = new CutsceneFrame();
        frame.choreograph(new DialogChoreography(DialogChoreography.Type.LINGER));
        slideshow.add(frame);
    }
    protected void choreographUseAbility(SimpleUnit subject, Abilities ability, SimpleUnit target) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.ABILITY);

        choreography.setSubject(subject);
        choreography.setObject(target);
        choreography.setAbility(ability);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographBallistaAttack(SimpleUnit subject, SimpleUnit target) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.BALLISTA_ATTACK);

        choreography.setSubject(subject);
        choreography.setObject(target);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographDespawn(SimpleUnit subject) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.DESPAWN);

        choreography.setSubject(subject);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographSpawn(SimpleUnit subject, int column, int row) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.SPAWN);

        choreography.setSubject(subject);
        choreography.setLocation(column,row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographMoveTo(SimpleUnit subject, int column, int row) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.MOVE);

        choreography.setSubject(subject);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnLocation(int column, int row) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.FOCUS_TILE);
        choreography.setLocation(column, row);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographFocusOnUnit(SimpleUnit focusCamera) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.FOCUS_UNIT);
        choreography.setSubject(focusCamera);

        frame.choreograph(choreography);

        slideshow.add(frame);
    }
    protected void choreographRevealVictCon(CampaignFlags flagID) {
        final CutsceneFrame frame = new CutsceneFrame();

        final DialogChoreography choreography = new DialogChoreography(DialogChoreography.Type.REVEAL_VICTCON);
        choreography.setVictConFlagID(flagID);

        frame.choreograph(choreography);

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

    protected CutsceneFrame lastFrame() {
        return slideshow.get(slideshow.size-1);
    }

    private void setParallelActions(DialogAction... actions) {
//        ParallelAction pAct = new ParallelAction();
        for(DialogAction action : actions) {

        }
    }
}
