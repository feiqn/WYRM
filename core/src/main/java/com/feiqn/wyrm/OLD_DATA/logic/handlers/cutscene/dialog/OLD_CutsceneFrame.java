package com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.OLD_DATA.OLD_UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

import java.util.HashMap;
import java.util.Objects;

public class OLD_CutsceneFrame {

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

    private Background_ID backgroundID;
    private Foreground_ID foregroundID;

    private final HashMap<Wyr.HorizontalPosition, OLD_CharacterExpression> positionsMap = new HashMap<>();

    private String text;
    private String doubleSpeakText;
    private String focusedName;
    private String doubleSpeakName;

    private Wyr.HorizontalPosition focusedHorizontalPosition;
    private Wyr.HorizontalPosition doubleSpeakHorizontalPosition;

    private Image foregroundImage;

    private boolean facingLeft;
    private boolean doubleSpeakFacingLeft;
    private boolean autoplayNext;
    private boolean complex;
    private boolean usesDialogActions;
    private boolean omitFromLog;
    private boolean fullscreen;
    private boolean doubleSpeak;
    private boolean choreographed;

    private int snapToIndex;

    private float progressiveDisplaySpeed;

    private OLD_DialogAction action;

    public OLD_CutsceneFrame() {
        initPositionMap();
        text = "";
        focusedName = "";
        doubleSpeakName = "";
        snapToIndex = 0;
        action = new OLD_DialogAction();
        focusedHorizontalPosition = Wyr.HorizontalPosition.CENTER;
        doubleSpeakHorizontalPosition = null;
        facingLeft            = false;
        autoplayNext          = false;
        complex               = false;
        progressiveDisplaySpeed = .01f;
        usesDialogActions     = false;
        fullscreen            = false;
        doubleSpeak           = false;
        doubleSpeakFacingLeft = false;
        choreographed         = false;
        foregroundID = Foreground_ID.NONE;
        foregroundImage = new Image();
        backgroundID = Background_ID.NONE;
    }

    private void initPositionMap() {
        positionsMap.put(Wyr.HorizontalPosition.FAR_LEFT,        OLD_CharacterExpression.NONE);
        positionsMap.put(Wyr.HorizontalPosition.LEFT,            OLD_CharacterExpression.NONE);
        positionsMap.put(Wyr.HorizontalPosition.LEFT_OF_CENTER,  OLD_CharacterExpression.NONE);
        positionsMap.put(Wyr.HorizontalPosition.CENTER,          OLD_CharacterExpression.NONE);
        positionsMap.put(Wyr.HorizontalPosition.RIGHT_OF_CENTER, OLD_CharacterExpression.NONE);
        positionsMap.put(Wyr.HorizontalPosition.RIGHT,           OLD_CharacterExpression.NONE);
        positionsMap.put(Wyr.HorizontalPosition.FAR_RIGHT,       OLD_CharacterExpression.NONE);
    }

    public void addExpressionAtPosition(OLD_CharacterExpression expression, Wyr.HorizontalPosition horizontalPosition) {
        positionsMap.put(horizontalPosition, expression);
    }

    public void setFullscreen(TextureRegion region) {
        foregroundImage = new Image(region);
        fullscreen = true;
    }

    public void snapToIndex(int index) {
        snapToIndex = index;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFocusedName(String focusedName) {
        this.focusedName = focusedName;
    }

    public void setFocusedPosition(Wyr.HorizontalPosition horizontalPosition) {
        this.focusedHorizontalPosition = horizontalPosition;
    }

    public void setFocusedExpression(OLD_CharacterExpression expression) {
        positionsMap.put(focusedHorizontalPosition, expression);
    }

    public void setExpressionAtPosition(OLD_CharacterExpression expression, Wyr.HorizontalPosition horizontalPosition) {
        positionsMap.put(horizontalPosition, expression);
    }

    public void setBackground(Background_ID backgroundID) {
        this.backgroundID = backgroundID;
    }

    public void setAutoplayNext(Boolean autoplayNext) {
        this.autoplayNext = autoplayNext;
    }

    public void setComplex(Boolean complex) {
        this.complex = complex;
    }

    public void setFacingLeft(Boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public void setProgressiveDisplaySpeed(float progressiveDisplaySpeed) {
        this.progressiveDisplaySpeed = progressiveDisplaySpeed;
    }

    public void addDialogAction(OLD_DialogAction action) {
        usesDialogActions = true;
        this.action = action;
    }

    public void addParallelActions(OLD_DialogAction... actions) {

    }

    public void omitFromLog() {
        omitFromLog = true;
    }

//    public void setDoubleSpeakText

    public void choreograph(OLD_CutsceneFrameChoreography choreography) {
        choreographed = true;
        usesDialogActions = true;
        action = new OLD_DialogAction(choreography);
    }

    /**
     * GETTERS
     */
    public boolean isChoreographed() { return choreographed; }
    public OLD_CharacterExpression getFocusedExpression() {
        return positionsMap.get(focusedHorizontalPosition);
    }
    public Background_ID getBackground() {
        return backgroundID;
    }
    public String getText() {
        return text;
    }
    public boolean FacingLeft() {
        return facingLeft;
    }
    public boolean autoAutoPlay() { // quickly skip the frame without waiting for player input
        return autoplayNext;
    }
    public Wyr.HorizontalPosition getFocusedPosition() {
        return focusedHorizontalPosition;
    }
    public float getProgressiveDisplaySpeed() {
        return progressiveDisplaySpeed;
    }
    public boolean isComplex() {
        return complex;
    }
    public boolean isFacingLeft() {
        return facingLeft;
    }
    public boolean usesDialogActions() { return usesDialogActions; }
    public String getFocusedName() {
        if(!Objects.equals(focusedName, "")) {
            return focusedName;
        } else {
            return deriveName(getFocusedExpression());
        }
    }
    public OLD_DialogAction getAction() {
        return action;
    }
    public OLD_CharacterExpression getExpressionAtPosition(Wyr.HorizontalPosition horizontalPosition) {
        return positionsMap.get(horizontalPosition);
    }
    public int getSnapToIndex() {
        return snapToIndex;
    }
    public boolean isOmitted() {
        return omitFromLog;
    }
    public boolean isFullscreen() {
        return fullscreen;
    }
    public Image getForegroundImage() {
        return foregroundImage;
    }
    public OLD_UnitIDRoster getSpeaker() { return rosterFromExpression(positionsMap.get(focusedHorizontalPosition)); }

    private OLD_UnitIDRoster rosterFromExpression(OLD_CharacterExpression expression) {
        switch(expression) {
            case LEIF_HOPEFUL:
            case LEIF_SMILING:
            case LEIF_TALKING:
            case LEIF_DETERMINED:
            case LEIF_WORRIED:
            case LEIF_WOUNDED:
            case LEIF_SURPRISED:
            case LEIF_PANICKED:
            case LEIF_EMBARRASSED:
            case LEIF_BADLY_WOUNDED:
            case LEIF_EXCITED:
            case LEIF_WINCING:
            case LEIF_MANIACAL:
            case LEIF_SLY:
            case LEIF_THINKING:
            case LEIF_CURIOUS:
            case LEIF_DESPAIRING:
            case LEIF_EXHAUSTED:
            case LEIF_ANNOYED:
                return OLD_UnitIDRoster.LEIF;

            case ANTAL_EXHAUSTED:
            case ANTAL_WORK_FACE:
            case ANTAL_DEVASTATED:
            case ANTAL_EMBARRASSED:
            case ANTAL_ENTHUSIASTIC:
            case ANTAL_SAD:
            case ANTAL_CURIOUS:
            case ANTAL_WORRIED:
            case ANTAL_BADLY_WOUNDED:
                return OLD_UnitIDRoster.ANTAL;

            case TEMP_KAI:
                return OLD_UnitIDRoster.KAI;
            case TEMP_JAY:
                return OLD_UnitIDRoster.JAY;
            case TEMP_MOE:
                return OLD_UnitIDRoster.MOE;
            case TEMP_ALEX:
                return OLD_UnitIDRoster.ALEX;
            case TEMP_RILEY:
                return OLD_UnitIDRoster.RILEY;

            case GENERIC_SOLDIER:
                return OLD_UnitIDRoster.GENERIC_SOLDIER;

            case TOHNI:
            case ANVIL:
            case D:

            case LEON_DETERMINED:
            case LEON_DEVASTATED:
            case LEON_DISTANT:
            case LEON_DYING:
            case LEON_PANICKED:

            case BREA_SHOUTING:
            case BREA_STOIC:
            case BREA_ANGRY:

            case THE_GREAT_WYRM:
            case NONE:
            default:
                return OLD_UnitIDRoster.MR_TIMN;
        }
    }

    private String nameFromSpeakerRoster(OLD_UnitIDRoster speaker) {
        String name = speaker.toString().toLowerCase();

        return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    private String deriveName(OLD_CharacterExpression expression) {
        // TODO: don't display Mr. Timn
        return nameFromSpeakerRoster(rosterFromExpression(expression));

    }
}
