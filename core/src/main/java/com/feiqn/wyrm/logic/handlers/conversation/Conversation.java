package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

public class Conversation extends Group {

    /**
     *  DialogFrames are lines in the script. <br>
     *  DialogFrameHandler is the script. <br>
     *  Conversation is the choreography. <br>
     *  ConversationHandler is the director.
     */

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

    private final DialogFrameHandler dialogFrameHandler;

    private final Conversation self = this;

    private Background background;

    private final WYRMGame game;

//    private Array<UnitRoster> speakers;

    private Array<SpeakerSlot> slots;

    private Image dialogBox,
                  nameBox;

    private Label nameLabel;
    private ProgressiveLabel dialogLabel;

    private final Group portraitGroup;

    public Conversation(WYRMGame game) {
        this(game, DialogFrameHandler.FrameSeries.DEBUG);
    }

    public Conversation(WYRMGame game, DialogFrameHandler.FrameSeries conversation) {
        // TODO: dynamic draw order priority

        this.game = game;

        portraitGroup = new Group();
        addActor(portraitGroup);

        dialogFrameHandler = new DialogFrameHandler(game);
        dialogFrameHandler.setFrameSeries(conversation);

        slots = new Array<>();
//        speakers = new Array<>();

        dialogBox = new Image(game.assetHandler.solidBlueTexture);
        nameBox = new Image(game.assetHandler.blueButtonTexture);

        addBoundingBoxes();
        mapPositionsToScreen();

        moveNameBoxAndLabel(SpeakerPosition.FAR_LEFT);

        playNext();

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                if(dialogFrameHandler.continues()) {
                    playNext();
                } else {
                    fadeOut();
                }

            }
        });
    }

    private void fadeOut() {
        final Action fadeout = Actions.fadeOut(.5f);
        final Action remove = Actions.removeActor(self);

        self.addAction(new SequenceAction(fadeout, remove));

        game.activeBattleScreen.uiGroup.addAction(Actions.fadeIn(1)); // TODO: move this to toggle function in abs for setting focus to map / ui / cutscene
    }

    private void addBoundingBoxes() {
        // TODO: fade in children, fade out HUD by function call from ConvoHandler to ABS

        dialogBox.setSize(Gdx.graphics.getWidth() * .9f, Gdx.graphics.getHeight() * .4f);
        dialogBox.setPosition(Gdx.graphics.getWidth() * .5f - dialogBox.getWidth() * .5f, Gdx.graphics.getHeight() * .25f - dialogBox.getHeight() * .5f);
        addActor(dialogBox);

        dialogLabel = new ProgressiveLabel("Sample Text", game.assetHandler.menuLabelStyle, game);

        dialogLabel.getStyle().font.getData().markupEnabled = true;
        dialogLabel.setWrap(true);

        dialogLabel.setWidth(dialogBox.getWidth() * .9f);
//        dialogLabel.setHeight(dialogBox.getHeight());
        dialogLabel.setAlignment(Align.top);
        dialogLabel.setAlignment(Align.left);

        dialogLabel.setPosition((dialogBox.getX() + (dialogBox.getWidth() * .05f))  , dialogBox.getY() + dialogBox.getHeight() - (dialogBox.getHeight() * .25f));

        dialogLabel.setYSpacing(dialogBox.getY() + dialogBox.getHeight() - (dialogBox.getHeight() * .25f));

//        dialogLabel.setBounds(); TODO: I think this is the call I need to make for proper text bounding?

//        dialogLabel.setAlignment(Align.top);
//        dialogLabel.setAlignment(Align.left);

        addActor(dialogLabel);

        nameLabel = new Label("Literally Who?", game.assetHandler.menuLabelStyle);

        nameBox.setSize(nameLabel.getWidth() * 1.2f, nameLabel.getHeight() * 1.2f);

        addActor(nameBox);
        addActor(nameLabel);
    }

    /**
     * build slots array with prefab screen positions
     */
    protected void mapPositionsToScreen() {
        final float percentileSpacing = dialogBox.getWidth() / 7;
        final float yPadding = dialogBox.getHeight();

        final Vector2 coordinate_FAR_LEFT = new Vector2(dialogBox.getX(), dialogBox.getY() + yPadding); // TODO: DEBUG vectors. Adjust
        final SpeakerSlot slot_FAR_LEFT = new SpeakerSlot(game, coordinate_FAR_LEFT, SpeakerPosition.FAR_LEFT, self);
        slots.add(slot_FAR_LEFT); // index 0

        final Vector2 coordinate_LEFT = new Vector2(coordinate_FAR_LEFT.x + percentileSpacing, coordinate_FAR_LEFT.y);
        final SpeakerSlot slot_LEFT = new SpeakerSlot(game, coordinate_LEFT, SpeakerPosition.LEFT, self);
        slots.add(slot_LEFT); // index 1

        final Vector2 coordinate_CENTER_LEFT = new Vector2(coordinate_LEFT.x + percentileSpacing, coordinate_LEFT.y);
        final SpeakerSlot slot_CENTER_LEFT = new SpeakerSlot(game, coordinate_CENTER_LEFT, SpeakerPosition.LEFT_OF_CENTER, self);
        slots.add(slot_CENTER_LEFT); // index 2

        final Vector2 coordinate_CENTER = new Vector2(coordinate_CENTER_LEFT.x + percentileSpacing, coordinate_CENTER_LEFT.y);
        final SpeakerSlot slot_CENTER = new SpeakerSlot(game, coordinate_CENTER, SpeakerPosition.CENTER, self);
        slots.add(slot_CENTER); // index 3

        final Vector2 coordinate_CENTER_RIGHT = new Vector2(coordinate_CENTER.x + percentileSpacing, coordinate_CENTER.y);
        final SpeakerSlot slot_CENTER_RIGHT = new SpeakerSlot(game, coordinate_CENTER_RIGHT, SpeakerPosition.RIGHT_OF_CENTER, self);
        slots.add(slot_CENTER_RIGHT); // index 4

        final Vector2 coordinate_RIGHT = new Vector2(coordinate_CENTER_RIGHT.x + percentileSpacing, coordinate_CENTER_RIGHT.y);
        final SpeakerSlot slot_RIGHT = new SpeakerSlot(game, coordinate_RIGHT, SpeakerPosition.RIGHT, self);
        slots.add(slot_RIGHT); // index 5

        final Vector2 coordinate_FAR_RIGHT = new Vector2(coordinate_RIGHT.x + percentileSpacing, coordinate_CENTER_RIGHT.y);
        final SpeakerSlot slot_FAR_RIGHT = new SpeakerSlot(game, coordinate_FAR_RIGHT, SpeakerPosition.FAR_RIGHT, self);
        slots.add(slot_FAR_RIGHT); // index 6
    }

    /**
     * moves the name box and label together between prefab screen positions
     */
    protected void moveNameBoxAndLabel(SpeakerPosition position) {
        Vector2 destination = new Vector2();
        final float yPadding = -nameBox.getHeight() * .5f;

        switch(position) {
            case FAR_LEFT:
                destination = new Vector2(slot(SpeakerPosition.FAR_LEFT).screenCoordinates.x, slot(SpeakerPosition.FAR_LEFT).screenCoordinates.y + yPadding);
                break;
            case LEFT:
                destination = new Vector2(slot(SpeakerPosition.LEFT).screenCoordinates.x, slot(SpeakerPosition.LEFT).screenCoordinates.y + yPadding);
                break;
            case LEFT_OF_CENTER:
                destination = new Vector2(slot(SpeakerPosition.LEFT_OF_CENTER).screenCoordinates.x, slot(SpeakerPosition.LEFT_OF_CENTER).screenCoordinates.y + yPadding);
                break;
            case CENTER:
                destination = new Vector2(slot(SpeakerPosition.CENTER).screenCoordinates.x, slot(SpeakerPosition.CENTER).screenCoordinates.y + yPadding);
                break;
            case RIGHT_OF_CENTER:
                destination = new Vector2(slot(SpeakerPosition.RIGHT_OF_CENTER).screenCoordinates.x, slot(SpeakerPosition.RIGHT_OF_CENTER).screenCoordinates.y + yPadding);
                break;
            case RIGHT:
                destination = new Vector2(slot(SpeakerPosition.RIGHT).screenCoordinates.x, slot(SpeakerPosition.RIGHT).screenCoordinates.y + yPadding);
                break;
            case FAR_RIGHT:
                destination = new Vector2(slot(SpeakerPosition.FAR_RIGHT).screenCoordinates.x, slot(SpeakerPosition.FAR_RIGHT).screenCoordinates.y + yPadding);
                break;
        }

        nameBox.setPosition(destination.x, destination.y);

        final float offsetX = destination.x + ((nameBox.getWidth() * .5f) - (nameLabel.getPrefWidth() * .66f));
        final float offsetY = destination.y + ((nameBox.getHeight() * .5f) - (nameLabel.getPrefHeight() * .5f));
        final Vector2 nameLabelOffsetPosition = new Vector2(offsetX, offsetY);

        nameLabel.setPosition(nameLabelOffsetPosition.x, nameLabelOffsetPosition.y);
    }

    /**
     * convenience method for accessing slots array by screen position label rather than index
     */
    private SpeakerSlot slot(SpeakerPosition position) {
        switch(position) {
            case FAR_LEFT:
                return slots.get(0);
            case LEFT:
                return slots.get(1);
            case LEFT_OF_CENTER:
                return slots.get(2);
            case CENTER:
                return slots.get(3);
            case RIGHT_OF_CENTER:
                return slots.get(4);
            case RIGHT:
                return slots.get(5);
            case FAR_RIGHT:
                return slots.get(6);
            default:
                Gdx.app.log("slot", "ERROR");
                return new SpeakerSlot(game);
        }
    }

    /**
     * derive relevant character name and set portrait at appropriate slot
     */
//    protected void deriveSpeaker(DialogFrame frame) {
//
//        final UnitRoster speaker = speakerFromExpression()
//
//        checkIfSpeakerAlreadyExistsInOtherSlot(speakerFromExpression(frame.getFocusedExpression()));
//
//        switch(frame.getFocusedExpression()) {
//            case LEIF_HOPEFUL:
//            case LEIF_SMILING:
//            case LEIF_TALKING:
//            case LEIF_WORRIED:
//            case LEIF_WOUNDED:
//            case LEIF_PANICKED:
//            case LEIF_EMBARRASSED:
//            case LEIF_BADLY_WOUNDED:
//            //etc...
//                checkIfSpeakerAlreadyExistsInOtherSlot(UnitRoster.LEIF);
//                slot(frame.getFocusedPosition()).speaker = UnitRoster.LEIF;
//                break;
//
//            default:
//                break;
//
//            // TODO: continue to fill in over time
//        }
//
//    }

    /**
     * Called to make sure the same character is never displayed twice in the same dialog frame.
     * @param speaker
     */
    protected void checkIfSpeakerAlreadyExistsInOtherSlot(UnitRoster speaker) {
        for(SpeakerSlot slot : slots) {
            if(slot.speakerRoster == speaker) {
                slot.clearSlot();
            }
        }
    }

    /**
     * updates label text and resizes name box
     * @param name
     */
    protected void setNameLabelAndResizeBox(CharSequence name) {
        nameLabel.setText(name);
        nameLabel.setSize(nameLabel.getPrefWidth(), nameLabel.getPrefHeight());
        nameBox.setSize(nameLabel.getPrefWidth() * 1.5f, nameLabel.getPrefHeight() * 1.5f);
    }

//    protected void setActiveSpeaker(UnitRoster speaker) {
//        // sets speaker as the character in focus for dimming other
//        // portraits, name box position and label, etc.
//    }

    /**
     * Steps through dialog frames. <br>
     * The main function you should call to handle everything else.
     */
    public void playNext() {
        /* continue on to the next frame.
         * some contextual behavior will be desirable here, for
         * fading character portraits in or out, moving between
         * screen positions, or ending the conversation.
         */
        final DialogFrame nextFrame = dialogFrameHandler.nextFrame();

        if(nextFrame.isComplex()) {
            layoutComplexFrame(nextFrame);
        }

        setNameLabelAndResizeBox(nextFrame.getFocusedName());
        moveNameBoxAndLabel(nextFrame.getFocusedPosition());

        displayDialog(nextFrame.getText(), nextFrame.getProgressiveDisplaySpeed(), nextFrame.getSnapToIndex());

//        deriveSpeaker(nextFrame);
        checkIfSpeakerAlreadyExistsInOtherSlot(speakerRosterFromExpression(nextFrame.getFocusedExpression()));

//        slot(nextFrame.getFocusedPosition()).speakerRoster = speakerRosterFromExpression(nextFrame.getFocusedExpression());


        slot(nextFrame.getFocusedPosition()).update(nextFrame.getFocusedExpression(), nextFrame.isFacingLeft());

        dimPortraitsExceptFocused(nextFrame.getFocusedPosition());

        if(nextFrame.autoAutoPlay()) {
            // TODO: allow input no
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    playNext();
                    // TODO: allow input yes
                }
            }, 1f); // TODO: dynamic wait time
        }
    }

    /**
     * sets text for dialog label.
     */
    protected void displayDialog(CharSequence sequence, float progressiveDisplaySpeed, int snapToIndex) {
        // set the dialogLabel text to sequence and display via the chosen method.
        dialogLabel.progressivelyDisplayText(sequence, progressiveDisplaySpeed, snapToIndex);
    }

    protected void displayDialog(CharSequence sequence, float speed) {
        dialogLabel.progressivelyDisplayText(sequence, speed);
    }

    protected void clearDialogBox() {
        // erase text on screen. Scroll away or fade out, something visually pleasant.
    }

    protected void dimPortraitsExceptFocused(SpeakerPosition focusedPosition) {
        // set all character portraits to dim,
        // then brighten up the focus again
        for(SpeakerSlot slot : slots) {
            if(slot.speakerPosition == focusedPosition) {
                slot.brighten();
            } else {
                slot.dim();
            }
        }
    }

    protected void layoutComplexFrame(DialogFrame frame) {
        for(SpeakerSlot slot : slots) {
            slot.clearSlot();
            slot.update(frame.getExpressionAtPosition(slot.speakerPosition), frame.isFacingLeft());
        }
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

    public UnitRoster speakerRosterFromExpression(CharacterExpression expression) {
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
                return UnitRoster.LEIF;

            case ANTAL_EXHAUSTED:
            case ANTAL_WORK_FACE:
            case ANTAL_DEVASTATED:
            case ANTAL_EMBARRASSED:
            case ANTAL_ENTHUSIASTIC:
            case ANTAL_BADLY_WOUNDED:
                return UnitRoster.ANTAL;

            case NONE:
            default:
                return UnitRoster.MR_TIMN;
        }
    }

    public String nameFromSpeakerRoster(UnitRoster speaker) {
        String name = speaker.toString().toLowerCase();

        return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    public Group getPortraitGroup() {
        return portraitGroup;
    }

    public ProgressiveLabel dialog() {
        return dialogLabel;
    }

    private static class SpeakerSlot {
        private final Vector2 screenCoordinates;
        private final SpeakerPosition speakerPosition;
        private Image characterPortrait;
        private CharacterExpression characterExpression; // Possibly unneeded
        private UnitRoster speakerRoster;
        private final WYRMGame game;
        private final Conversation parent;
//        private boolean focused;

        public SpeakerSlot(WYRMGame game) {
            // only called on error
            screenCoordinates = new Vector2();
            speakerPosition = null;
            characterPortrait = new Image();
            speakerRoster = null;
            this.game = game;
            parent = new Conversation(game);
//            focused = false;
        }

        public SpeakerSlot(WYRMGame game, Vector2 coordinates, SpeakerPosition position, Conversation parent) {
            this.game = game;
            this.screenCoordinates = coordinates;
            this.speakerPosition = position;
            this.parent = parent;

//            focused = false;
            characterPortrait = new Image();
            speakerRoster = UnitRoster.MR_TIMN;
//            parent.addActor(characterPortrait);
        }

        public void clearSlot() {
//            focused = false;
            speakerRoster = UnitRoster.MR_TIMN;
            characterPortrait = new Image();
            characterPortrait.setPosition(screenCoordinates.x, screenCoordinates.y);
        }

        /**
         * simultaneously updates internal expression, portrait, and roster. </br>
         *
         * does not update name.
         * @param expression
         */
        public void update(CharacterExpression expression) {
            this.update(expression, false);
        }

        public void update(CharacterExpression expression, boolean flip) {
            this.characterExpression = expression;

            boolean portraitSet = false;

            Texture texture = new Texture(Gdx.files.internal("test/robin.png"));

            // TODO: this is all placeholder until proper images gathered and implemented into asset handler

            switch(expression) {
                case NONE:

                case LEIF_EXCITED:
                case LEIF_WINCING:
                case LEIF_MANIACAL:
                case LEIF_BADLY_WOUNDED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_EMBARRASSED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_PANICKED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_WOUNDED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_WORRIED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_TALKING:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_SMILING:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_HOPEFUL:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                    speakerRoster = UnitRoster.LEIF;
                    break;

                case ANTAL_EXHAUSTED:
                case ANTAL_WORK_FACE:
                case ANTAL_DEVASTATED:
                case ANTAL_EMBARRASSED:
                case ANTAL_ENTHUSIASTIC:
                case ANTAL_BADLY_WOUNDED:

                default:
                    texture = new Texture(Gdx.files.internal("test/robin.png"));
                    break;
            }

            TextureRegion region = new TextureRegion(texture);
            if(flip) {
                region.flip(true,false);
            }

            characterPortrait = new Image(region);
            characterPortrait.setPosition(screenCoordinates.x, screenCoordinates.y);
            parent.getPortraitGroup().addActor(characterPortrait);

        }

//        public boolean isFocused() {
//            return focused;
//        }
//        public SpeakerPosition getSpeakerPosition() {
//            return speakerPosition;
//        }
//        public Image getCharacterPortrait() {
//            return characterPortrait;
//        }
//        public UnitRoster getSpeaker() {
//            return speaker;
//        }
//        public Vector2 getScreenCoordinates() {
//            return screenCoordinates;
//        }
//        public CharacterExpression getExpression() {
//            return expression;
//        }
//        public void setExpression(CharacterExpression expression) {
//            this.expression = expression;
//        }
//        public void setCharacterPortrait(Image characterPortrait) {
//            this.characterPortrait = characterPortrait;
//        }
//        public void setSpeaker(UnitRoster speaker) {
//            this.speaker = speaker;
//        }
//        public void setFocused(boolean focused) {
//            this.focused = focused;
//        }

        public void dim() {
            characterPortrait.setColor(.5f, .5f, .5f, 1);
        }

        public void brighten() {
            characterPortrait.setColor(1,1,1,1);
        }

    }
}
