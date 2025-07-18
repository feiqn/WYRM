package com.feiqn.wyrm.logic.handlers.conversation.dialog;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.logic.handlers.conversation.CharacterExpression;
import com.feiqn.wyrm.logic.handlers.conversation.SpeakerPosition;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.HashMap;
import java.util.Objects;

public class DialogFrame {

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

    private final HashMap<SpeakerPosition, CharacterExpression> positionsMap = new HashMap<>();

    private String text;
    private String doubleSpeakText;
    private String focusedName;
    private String doubleSpeakName;

    private SpeakerPosition focusedPosition;
    private SpeakerPosition doubleSpeakPosition;

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

    private final Array<DialogAction> actions = new Array<>();

    public DialogFrame() {
        initPositionMap();
        text = "";
        focusedName = "";
        doubleSpeakName = "";
        snapToIndex = 0;
        focusedPosition = SpeakerPosition.CENTER;
        doubleSpeakPosition   = null;
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
        positionsMap.put(SpeakerPosition.FAR_LEFT,        CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.LEFT,            CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.LEFT_OF_CENTER,  CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.CENTER,          CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.RIGHT_OF_CENTER, CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.RIGHT,           CharacterExpression.NONE);
        positionsMap.put(SpeakerPosition.FAR_RIGHT,       CharacterExpression.NONE);
    }

    public void addExpressionAtPosition(CharacterExpression expression, SpeakerPosition position) {
        positionsMap.put(position, expression);
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

    public void setFocusedPosition(SpeakerPosition position) {
        this.focusedPosition = position;
    }

    public void setFocusedExpression(CharacterExpression expression) {
        positionsMap.put(focusedPosition, expression);
    }

    public void setExpressionAtPosition(CharacterExpression expression, SpeakerPosition position) {
        positionsMap.put(position, expression);
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

    public void addDialogAction(DialogAction action) {
        usesDialogActions = true;
        actions.add(action);
    }

    public void addParallelActions(DialogAction... actions) {

    }

    public void omitFromLog() {
        omitFromLog = true;
    }

//    public void setDoubleSpeakText

    public void choreograph(DialogChoreography choreography) {
        choreographed = true;
        usesDialogActions = true;
        actions.add(new DialogAction(choreography));
    }

    /**
     * GETTERS
     */
    public boolean isChoreographed() { return choreographed; }
    public CharacterExpression getFocusedExpression() {
        return positionsMap.get(focusedPosition);
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
    public SpeakerPosition getFocusedPosition() {
        return focusedPosition;
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
    public Array<DialogAction> getActions() {
        return actions;
    }
    public CharacterExpression getExpressionAtPosition(SpeakerPosition position) {
        return positionsMap.get(position);
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
    public UnitRoster getSpeaker() { return rosterFromExpression(positionsMap.get(focusedPosition)); }

    private UnitRoster rosterFromExpression(CharacterExpression expression) {
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
                return UnitRoster.LEIF;

            case ANTAL_EXHAUSTED:
            case ANTAL_WORK_FACE:
            case ANTAL_DEVASTATED:
            case ANTAL_EMBARRASSED:
            case ANTAL_ENTHUSIASTIC:
            case ANTAL_SAD:
            case ANTAL_CURIOUS:
            case ANTAL_WORRIED:
            case ANTAL_BADLY_WOUNDED:
                return UnitRoster.ANTAL;

            case TEMP_KAI:
                return UnitRoster.KAI;
            case TEMP_JAY:
                return UnitRoster.JAY;
            case TEMP_MOE:
                return UnitRoster.MOE;
            case TEMP_ALEX:
                return UnitRoster.ALEX;
            case TEMP_RILEY:
                return UnitRoster.RILEY;

            case GENERIC_SOLDIER:
                return UnitRoster.GENERIC_SOLDIER;

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
                return UnitRoster.MR_TIMN;
        }
    }

    private String nameFromSpeakerRoster(UnitRoster speaker) {
        String name = speaker.toString().toLowerCase();

        return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    private String deriveName(CharacterExpression expression) {
        // TODO: don't display Mr. Timn
        return nameFromSpeakerRoster(rosterFromExpression(expression));

    }
}
