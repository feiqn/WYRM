package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides;

import com.feiqn.wyrm.wyrefactor.helpers.Speed;
import com.feiqn.wyrm.wyrefactor.helpers.Subjectivity;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneChoreography;

public abstract class WyrCutsceneSlide<
        Choreo extends WyrCutsceneChoreography<?>
            > implements Wyr {

    // refactor of CutsceneFrame

    public enum Background_ID {
        NONE,
        REMOVE,

        BLACK,

        EXTERIOR_FOREST_DAY,
        EXTERIOR_FOREST_NIGHT,

        EXTERIOR_BEACH_DAY,
        EXTERIOR_BEACH_NIGHT,

        EXTERIOR_STREETS_DIRT_DAY,
        EXTERIOR_STREETS_DIRT_NIGHT,

        EXTERIOR_STREETS_STONE_DAY,
        EXTERIOR_STREETS_STONE_NIGHT,

        EXTERIOR_CAMP_WOODS_DAY,
        EXTERIOR_CAMP_WOODS_NIGHT,

        INTERIOR_STONE_TORCHLIGHT,
        INTERIOR_STONE_DAY,
        INTERIOR_STONE_NIGHT,

        INTERIOR_WOOD_FIRELIGHT,
        INTERIOR_WOOD_DAY,
        INTERIOR_WOOD_NIGHT,
    }

    public enum Foreground_ID {
        NONE,
        BLACK,
    }

    protected Speed speed;

    protected Background_ID backgroundID = Background_ID.NONE;
    protected Foreground_ID foregroundID = Foreground_ID.NONE;

    protected String dialog = "";
    protected String focusedName = "";
    protected String doubleSpeakName = "";
    protected String doubleSpeakText = "";

    protected boolean fullscreen         = false;
    protected boolean omitFromLog        = false;
    protected boolean autoProgressToNext = false;
    protected boolean worldChoreographed = false;
    protected boolean stageChoreographed = false;

    protected int snapToIndex = 0;

    protected Position focusedPosition;

    protected Choreo choreography;

    protected WyrCutsceneSlide() {}


    /**
     * Getters and setters.
     */
    public void choreograph(Choreo choreography) { this.choreography = choreography; }
    public void setFocusedPosition(Position position) { this.focusedPosition = position; }
    public void setAutoProgressToNext(boolean autoProgressToNext) { this.autoProgressToNext = autoProgressToNext; }
    public void setBackgroundID(Background_ID backgroundID) { this.backgroundID = backgroundID; }
    public void setDoubleSpeakName(String doubleSpeakName) { this.doubleSpeakName = doubleSpeakName; }
    public void setDoubleSpeakText(String doubleSpeakText) { this.doubleSpeakText = doubleSpeakText; }
    public void setFocusedName(String focusedName) { this.focusedName = focusedName; }
    public void setForegroundID(Foreground_ID foregroundID) { this.foregroundID = foregroundID; }
    public void setFullscreen() { this.fullscreen = true; }
    public void setSpeed(Speed displaySpeed) { this.speed = displaySpeed; }
    public void setStageChoreographed() { this.stageChoreographed = true; }
    public void setDialog(String dialog) { this.dialog = dialog; }
    public void setWorldChoreographed() { this.worldChoreographed = true; }
    public void setSnapToIndex(int snapToIndex) { this.snapToIndex = snapToIndex; }
    public void omitFromLog() { this.omitFromLog = true; }

    public int getSnapToIndex() { return snapToIndex; }
    public Background_ID getBackgroundID() { return backgroundID; }
    public boolean usesDoubleSpeak() { return !doubleSpeakText.isEmpty();}
    public boolean isChoreographed() { return choreography != null; }
    public boolean isAutoProgressToNext() { return autoProgressToNext; }
    public boolean isFullscreen() { return fullscreen; }
    public boolean omittedFromLog() { return omitFromLog; }
    public boolean stageChoreographed() { return stageChoreographed; }
    public boolean worldChoreographed() { return worldChoreographed; }
    public Speed getDisplaySpeed() { return speed; }
    public Foreground_ID getForegroundID() { return foregroundID; }
    public String getDoubleSpeakName() { return doubleSpeakName; }
    public String getDoubleSpeakText() { return doubleSpeakText; }
    public String getFocusedName() { return focusedName; }
    public String getDialog() { return dialog; }
    public Position getFocusedPosition() { return focusedPosition; }

}
