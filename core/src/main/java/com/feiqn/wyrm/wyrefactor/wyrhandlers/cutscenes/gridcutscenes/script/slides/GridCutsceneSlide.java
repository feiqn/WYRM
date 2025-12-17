package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides;

import com.feiqn.wyrm.logic.handlers.cutscene.dialog.CutsceneFrame;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneSlide;

public abstract class GridCutsceneSlide extends WyrCutsceneSlide {

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

    private CutsceneFrame.Background_ID backgroundID = CutsceneFrame.Background_ID.NONE;
    private CutsceneFrame.Foreground_ID foregroundID = CutsceneFrame.Foreground_ID.NONE;

    private String text = "";
    private String focusedName = "";
    private String doubleSpeakName = "";
    private String doubleSpeakText = "";

    private boolean fullscreen         = false;
    private boolean omitFromLog        = false;
    private boolean autoProgressToNext = false;
    private boolean worldChoreographed = false;
    private boolean stageChoreographed = false;

    private int snapToIndex = 0;

    private float labelDisplaySpeed;

    // TODO: refactor SpeakerPosition Dialog Stage thing

    protected GridCutsceneSlide() {
        super(WyrType.GRIDWORLD);
    }



    /**
     * Getters and setters.
     */
    public CutsceneFrame.Background_ID getBackgroundID() { return backgroundID; }
    public boolean isAutoProgressToNext() { return autoProgressToNext; }
    public boolean isFullscreen() { return fullscreen; }
    public boolean omittedFromLog() { return omitFromLog; }
    public boolean stageChoreographed() { return stageChoreographed; }
    public boolean worldChoreographed() { return worldChoreographed; }
    public float getLabelDisplaySpeed() { return labelDisplaySpeed; }
    public CutsceneFrame.Foreground_ID getForegroundID() { return foregroundID; }
    public String getDoubleSpeakName() { return doubleSpeakName; }
    public String getDoubleSpeakText() { return doubleSpeakText; }
    public String getFocusedName() { return focusedName; }
    public String getText() { return text; }
    public void setAutoProgressToNext(boolean autoProgressToNext) { this.autoProgressToNext = autoProgressToNext; }
    public void setBackgroundID(CutsceneFrame.Background_ID backgroundID) { this.backgroundID = backgroundID; }
    public void setDoubleSpeakName(String doubleSpeakName) { this.doubleSpeakName = doubleSpeakName; }
    public void setDoubleSpeakText(String doubleSpeakText) { this.doubleSpeakText = doubleSpeakText; }
    public void setFocusedName(String focusedName) { this.focusedName = focusedName; }
    public void setForegroundID(CutsceneFrame.Foreground_ID foregroundID) { this.foregroundID = foregroundID; }
    public void setFullscreen() { this.fullscreen = true; }
    public void setLabelDisplaySpeed(float labelDisplaySpeed) { this.labelDisplaySpeed = labelDisplaySpeed; }
    public void omitFromLog() { this.omitFromLog = true; }
    public void setStageChoreographed() { this.stageChoreographed = true; }
    public void setText(String text) { this.text = text; }
    public void setWorldChoreographed() { this.worldChoreographed = true; }
    public boolean usesDoubleSpeak() { return !doubleSpeakText.isEmpty();}
    public void setSnapToIndex(int snapToIndex) { this.snapToIndex = snapToIndex; }
    public int getSnapToIndex() { return snapToIndex; }
}
