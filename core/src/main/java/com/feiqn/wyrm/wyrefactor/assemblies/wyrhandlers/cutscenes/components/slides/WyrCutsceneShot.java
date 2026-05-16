package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.choreography.WyrCutsceneChoreography;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class WyrCutsceneShot implements Wyr {

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

    protected Speed textSpeed;

    protected Background_ID backgroundID = Background_ID.NONE;
    protected Foreground_ID foregroundID = Foreground_ID.NONE;

    protected DialogDirection focusedDirection = null; // always has lines
    protected DialogDirection doubleSpeakDirection = null; // only has lines when used
    protected Array<DialogDirection> supportingCharacters = new Array<>(); // silent

    protected boolean fullscreen         = false;
    protected boolean omitFromLog        = false;
    protected boolean autoProgressToNext = false;
    protected boolean worldChoreographed = false;
    protected boolean stageChoreographed = false;

    protected int snapToIndex = 0;

    protected WyrCutsceneChoreography choreography;

    public WyrCutsceneShot() {}

    public WyrCutsceneShot(WyrCutsceneChoreography choreography) {
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

    public WyrCutsceneShot(DialogDirection direction) {
        focusedDirection = direction;
    }

    public WyrCutsceneShot(CharacterID focusedCharacterID, Expression expression, String dialog) {
        focusedDirection = new DialogDirection(focusedCharacterID).expression(expression).line(dialog);
    }

    public WyrCutsceneShot focus(DialogDirection character)         { this.focusedDirection = character;        return this; }
    public WyrCutsceneShot doubleSpeak(DialogDirection character)   { this.doubleSpeakDirection = character;    return this; }
    public WyrCutsceneShot autoplay()                               { autoProgressToNext = true;                return this; }
    public WyrCutsceneShot fullscreen()                             { fullscreen = true;                        return this; }
    public WyrCutsceneShot omit()                                   { omitFromLog = true;                       return this; }
    public WyrCutsceneShot addSupporting(DialogDirection character) { this.supportingCharacters.add(character); return this; }
    public WyrCutsceneShot background(Background_ID backgroundID)   { this.backgroundID = backgroundID;         return this; }
    public WyrCutsceneShot foreground(Foreground_ID foregroundID)   { this.foregroundID = foregroundID;         return this; }
    public WyrCutsceneShot textSpeed(Speed textSpeed)               { this.textSpeed = textSpeed;               return this; }
    public WyrCutsceneShot snapToIndex(int index)                   { this.snapToIndex = index;                 return this; }

    public DialogDirection getFocusedDirection()            { return focusedDirection; }
    public DialogDirection getDoubleSpeakDirection()        { return doubleSpeakDirection; }
    public Array<DialogDirection> getSupportingCharacters() { return supportingCharacters; }

    public void choreograph(WyrCutsceneChoreography choreography) { this.choreography = choreography; stageChoreographed = true; }

    public WyrCutsceneChoreography getChoreo() { return choreography; }

    public Background_ID getBackgroundID()    { return backgroundID; }
    public Foreground_ID getForegroundID()    { return foregroundID; }
    public boolean       usesDoubleSpeak()    { return doubleSpeakDirection != null;}
    public boolean       autoProgresses()     { return autoProgressToNext; }
    public boolean       isFullscreen()       { return fullscreen; }
    public boolean       omittedFromLog()     { return omitFromLog; }
    public boolean       isChoreographed()    { return stageChoreographed || worldChoreographed; }
    public boolean       stageChoreographed() { return stageChoreographed; }
    public boolean       worldChoreographed() { return worldChoreographed; }
    public Speed         getDisplaySpeed()    { return textSpeed; }
    public int           getSnapToIndex()     { return snapToIndex; }

    public static class DialogDirection {
        private CharacterID        characterID   = null;
        private Wyr.Expression     expression    = Expression.DETERMINED;
        private HorizontalPosition position      = HorizontalPosition.LEFT;
        private boolean            facingLeft    = false;
        private String             preferredName;
        private String             line;
        private TextureRegionDrawable drawable = null;

        public DialogDirection(String line) {
            // when character is null, player assigns line
            // to the last referenced character in script
            this.line = line;
        }
        public DialogDirection(CharacterID characterID) { this.characterID = characterID; }

        public DialogDirection drawable(TextureRegionDrawable drawable) {
            this.drawable = drawable;
            return this;
        }
        public DialogDirection expression(Expression expression) {
            this.expression = expression;
            return this;
        }
        public DialogDirection position(HorizontalPosition position) {
            this.position = position;
            return this;
        }
        public DialogDirection faceLeft(boolean faceLeft) {
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

        public TextureRegionDrawable getDrawable() { return drawable; }
        public CharacterID getCharacter() {
            return characterID;
        }
        public boolean isFacingLeft() {
            return facingLeft;
        }
        public Expression getExpression() {
            return expression;
        }
        public HorizontalPosition getPosition() {
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
