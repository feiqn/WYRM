package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Objects;

public class DialogFrame {

    public enum SpecialEffect {
        SHAKE_PORTRAIT,
        SHAKE_SCREEN,
        FADE_IN,
        FADE_OUT,
        ARBITRARY_CODE,
    }

    public enum Background {
        NONE,

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

    private Background background;

    private final HashMap<SpeakerPosition, CharacterExpression> positionsMap = new HashMap<>();

    private String text;
    private String focusedName;

    private SpeakerPosition focusedPosition;

    private boolean facingLeft;
    private boolean autoplayNext;
    private boolean complex;
    private boolean usesSpecialDialogActions;
    private boolean omitFromLog;
    private boolean fullscreen;

    private int snapToIndex;

    private float progressiveDisplaySpeed;

    private final Array<SpecialDialogAction> actions = new Array<>();

    public DialogFrame() {
        initPositionMap();
        text = "";
        focusedName = "";
        snapToIndex = 0;
        focusedPosition = SpeakerPosition.CENTER;
        facingLeft = false;
        autoplayNext = false;
        complex = false;
        progressiveDisplaySpeed = .01f;
        usesSpecialDialogActions = false;
        fullscreen = false;
        background = Background.NONE;
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

    public void addSpecialAction(SpecialDialogAction action) {
        usesSpecialDialogActions = true;
        actions.add(action);
    }
    public void omitFromLog() {
        omitFromLog = true;
    }

    //    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition position) {
//        this(characterAndExpression, text, position, false, .1f, false);
//    }
//
//    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition position, Boolean facingLeft) {
//        this(characterAndExpression,text, position, facingLeft, .1f, false);
//    }
//
//    public DialogFrame(CharacterExpression characterAndExpression, String text, SpeakerPosition pos, Boolean facingLeft, float progressiveDisplaySpeed, Boolean autoNext) {
//        this.characterExpression = characterAndExpression;
//        this.text = text;
//        this.focusedPosition = pos;
//        this.facingLeft = facingLeft;
//        this.autoplayNext = autoNext;
//        this.progressiveDisplaySpeed = progressiveDisplaySpeed;
//    }

    public CharacterExpression getFocusedExpression() {
        return positionsMap.get(focusedPosition);
    }
    public String getText() {
        return text;
    }
    public Boolean FacingLeft() {
        return facingLeft;
    }
    public Boolean autoAutoPlay() { // quickly skip the frame without waiting for player input
        return autoplayNext;
    }
    public SpeakerPosition getFocusedPosition() {
        return focusedPosition;
    }
    public float getProgressiveDisplaySpeed() {
        return progressiveDisplaySpeed;
    }
    public Boolean isComplex() {
        return complex;
    }
    public Boolean isFacingLeft() {
        return facingLeft;
    }
    public String getFocusedName() {
        if(!Objects.equals(focusedName, "")) {
            return focusedName;
        } else {
            return deriveName(getFocusedExpression());
        }
    }
    public Array<SpecialDialogAction> getActions() {
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

    private String deriveName(CharacterExpression expression) { // TODO: refactor / fold into other uses of expression for fewer reduant calls when updating expression enum list. yes you know what i mean.
        switch(expression) {
            case LEIF_HOPEFUL:
            case LEIF_SMILING:
            case LEIF_TALKING:
            case LEIF_WORRIED:
            case LEIF_WOUNDED:
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
            case LEIF_ANNOYED:
                return "Leif";

            case ANTAL_EXHAUSTED:
            case ANTAL_WORK_FACE:
            case ANTAL_DEVASTATED:
            case ANTAL_EMBARRASSED:
            case ANTAL_ENTHUSIASTIC:
            case ANTAL_BADLY_WOUNDED:
                return "Antal";

            case TEMP_BAND_GIRL:

            case NONE:
            default:
                return "";
        }
    }
}
