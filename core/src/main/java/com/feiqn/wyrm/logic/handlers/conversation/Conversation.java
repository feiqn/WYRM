package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

import java.util.Objects;

public class Conversation extends Group {

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

    private DialogFrameHandler dialogFrameHandler;

    private Background background;

    private final WYRMGame game;

//    private Array<UnitRoster> speakers;

    private Array<SpeakerSlot> slots;

    private Image dialogBox,
                  nameBox;

    private Label nameLabel,
                  dialogLabel;

    public Conversation(WYRMGame game) {
        this.game = game;

        dialogFrameHandler = new DialogFrameHandler(game);

        slots = new Array<>();
//        speakers = new Array<>();

        dialogBox = new Image(game.assetHandler.solidBlueTexture);
        nameBox = new Image(game.assetHandler.blueButtonTexture);

        addBoundingBoxes();
        mapPositionsToScreen();

    }

    private void addBoundingBoxes() {
        // TODO: fade in children, fade out HUD by function call from ConvoHandler to ABS

        dialogBox.setSize(Gdx.graphics.getWidth() * .9f, Gdx.graphics.getHeight() * .4f);
        dialogBox.setPosition(Gdx.graphics.getWidth() * .5f - dialogBox.getWidth() * .5f, Gdx.graphics.getHeight() * .25f - dialogBox.getHeight() * .5f);
        addActor(dialogBox);

        dialogLabel = new Label("Sample Text", game.assetHandler.menuLabelStyle);

        dialogLabel.setWrap(true);
        dialogLabel.setPosition((dialogBox.getX() + (dialogBox.getWidth() * .05f))  , dialogBox.getY() + dialogBox.getHeight() - (dialogBox.getHeight() * .25f));
        dialogLabel.setWidth(dialogBox.getWidth() * .9f);
//        dialogLabel.setBounds(); TODO: I think this is the call I need to make for proper text bounding
        addActor(dialogLabel);

        nameLabel = new Label("Literally Who?", game.assetHandler.menuLabelStyle);

        nameBox.setSize(nameLabel.getWidth() * 1.2f, nameLabel.getHeight() * 1.2f);

        moveNameBoxAndLabel(SpeakerPosition.LEFT); // TODO: can't do this till mapPositions done

        addActor(nameBox);
        addActor(nameLabel);

    }

    protected void mapPositionsToScreen() {
        // set up 7 SpeakerSlot objects and add to slots<>

        final float percentileSpacing = dialogBox.getWidth() / 7;

        final Vector2 coordinate_FAR_LEFT = new Vector2(dialogBox.getX(), dialogBox.getY()); // TODO: DEBUG vectors. Adjust
        final SpeakerSlot slot_FAR_LEFT = new SpeakerSlot(coordinate_FAR_LEFT, SpeakerPosition.FAR_LEFT);
        slots.add(slot_FAR_LEFT); // index 0

        final Vector2 coordinate_LEFT = new Vector2(coordinate_FAR_LEFT.x + percentileSpacing, coordinate_FAR_LEFT.y);
        // slot LEFT // index 1

        final Vector2 coordinate_CENTER_LEFT = new Vector2(coordinate_LEFT.x + percentileSpacing, coordinate_LEFT.y);
        // slot CENTER_LEFT // index 2

        // vector CENTER
        // slot CENTER // index 3

        // vector CENTER_RIGHT
        // slot CENTER_RIGHT // index 4

        // vector RIGHT
        // slot RIGHT // index 5

        // vector FAR_RIGHT
        // slot FAR_RIGHT // index 6

    }

    protected void moveNameBoxAndLabel(SpeakerPosition position) {
        // TODO: shift name box and label to mapped vector2 position minus half box height, inset into dialog box graphic for slight overlay
        switch(position) {
            case FAR_LEFT:
                nameBox.setPosition(slot(SpeakerPosition.FAR_LEFT).screenCoordinates.x, slot(SpeakerPosition.FAR_LEFT).screenCoordinates.y);
                break;
            case LEFT:
            case CENTER_LEFT:
            case CENTER:
            case CENTER_RIGHT:
            case RIGHT:
            case FAR_RIGHT:
                break;
        }
    }

    private SpeakerSlot slot(SpeakerPosition position) {
        switch(position) {
            case FAR_LEFT:
                return slots.get(0);
            case LEFT:
                return slots.get(1);
            case CENTER_LEFT:
                return slots.get(2);
            case CENTER:
                return slots.get(3);
            case CENTER_RIGHT:
                return slots.get(4);
            case RIGHT:
                return slots.get(5);
            case FAR_RIGHT:
                return slots.get(6);
            default:
                return new SpeakerSlot();
        }
    }

    /**
     * derive relevant character name and set portrait
     */
    protected void deriveSpeaker(DialogFrame frame) {
        switch(frame.getCharacterExpression()) {
            case LEIF_HOPEFUL:
            case LEIF_SMILING:
            case LEIF_TALKING:
            case LEIF_WORRIED:
            case LEIF_WOUNDED:
            case LEIF_PANICKED:
            case LEIF_EMBARRASSED:
            case LEIF_BADLY_WOUNDED:
            //etc...
//                setActiveSpeaker(UnitRoster.LEIF);
                break;

            // TODO: continue to fill in over time
        }
    }

    protected void setNameLabelAndResize(CharSequence name) {
        nameLabel.setText(name);

    }

//    protected void setActiveSpeaker(UnitRoster speaker) {
//        // sets speaker as the character in focus for dimming other
//        // portraits, name box position and label, etc.
//    }

    public void playNext() {
        /* continue on to the next frame.
         * some contextual behavior will be desirable here, for
         * fading character portraits in or out, moving between
         * screen positions, or ending the conversation.
         */
    }

    protected void displayDialog(CharSequence sequence) {
        clearDialogBox();
        // set the dialogLabel text to sequence and display via the chosen method.
    }

    protected void clearDialogBox() {
        // erase text on screen. Scroll away or fade out, something visually pleasant.
    }

    protected void flipPortrait(SpeakerPosition position) {

    }
//    protected void flipSpeaker(int index) {
//        // take the character image at index and flip horizontally (or draw custom left-facing sprites and switch to those.)
//    }

    protected void animateMovePortraitToNewSlot(SpeakerPosition current, SpeakerPosition goal) {
        // animate slide move from one place to another

    }

    protected void setPortraitAtPosition(CharacterExpression portrait, SpeakerPosition position, boolean flipped) {

    }
//    protected void setSpeakerAtPosition(UnitRoster speaker, SpeakerPosition position) {
//        setSpeakerAtPosition(speaker, position, false);
//    }
//    protected void setSpeakerAtPosition(int index, SpeakerPosition position, boolean flipped) {
//        if(speakers.size <= index) {
//            setSpeakerAtPosition(speakers.get(index), position, flipped);
//        }
//    }
//    protected void setSpeakerAtPosition(int index, SpeakerPosition position) {
//        if(speakers.size <= index) {
//            setSpeakerAtPosition(speakers.get(index), position, false);
//        }
//    }
//
//    public void addSpeaker(UnitRoster speaker) {
//        speakers.add(speaker);
//    }

    public void setBackground(Background background) {
        this.background = background;
    }

    private void displayBackground() {
        switch(background) { // TODO: set image, size, and add actor
            case INTERIOR_WOOD_DAY:
            case INTERIOR_WOOD_NIGHT:
            case INTERIOR_WOOD_FIRELIGHT:

            case INTERIOR_STONE_DAY:
            case INTERIOR_STONE_NIGHT:
            case INTERIOR_STONE_TORCHLIGHT:

            case EXTERIOR_BEACH_DAY:
            case EXTERIOR_BEACH_NIGHT:

            case EXTERIOR_FOREST_DAY:
            case EXTERIOR_FOREST_NIGHT:

            case EXTERIOR_CAMP_WOODS_DAY:
            case EXTERIOR_CAMP_WOODS_NIGHT:

            case EXTERIOR_STREETS_DIRT_DAY:
            case EXTERIOR_STREETS_DIRT_NIGHT:

            case EXTERIOR_STREETS_STONE_DAY:
            case EXTERIOR_STREETS_STONE_NIGHT:

            case NONE:
            default:
                break;
        }
    }

    public void displayBackground(Background background) {
        setBackground(background);
        displayBackground();
    }

    private static class SpeakerSlot {
        private final Vector2 screenCoordinates;
        private final SpeakerPosition speakerPosition;
        private Image characterPortrait;
        private UnitRoster speaker;
        private boolean focused;

        public SpeakerSlot() {
            screenCoordinates = new Vector2();
            speakerPosition = null;
            characterPortrait = new Image();
            speaker = null;
            focused = false;
        }

        public SpeakerSlot(Vector2 coordinates, SpeakerPosition position) {
            this.screenCoordinates = coordinates;
            this.speakerPosition = position;

            focused = false;
            characterPortrait = new Image();
            speaker = UnitRoster.MR_TIMN;
        }

        public void clearSlot() {
            focused = false;
            speaker = UnitRoster.MR_TIMN;
            characterPortrait = new Image();
            characterPortrait.setPosition(screenCoordinates.x, screenCoordinates.y);
        }

        public boolean isFocused() {
            return focused;
        }
        public SpeakerPosition getSpeakerPosition() {
            return speakerPosition;
        }
        public Image getCharacterPortrait() {
            return characterPortrait;
        }
        public UnitRoster getSpeaker() {
            return speaker;
        }
        public Vector2 getScreenCoordinates() {
            return screenCoordinates;
        }

        public void setCharacterPortrait(Image characterPortrait) {
            this.characterPortrait = characterPortrait;
        }
        public void setSpeaker(UnitRoster speaker) {
            this.speaker = speaker;
        }
        public void setFocused(boolean focused) {
            this.focused = focused;
        }
    }
}
