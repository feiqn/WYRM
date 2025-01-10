package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.logic.handlers.conversation.DialogFrame.Background;

import java.util.HashMap;



public class Conversation extends HUDElement {

    private final Conversation self = this;

    private final DialogScript dialogScript;

    private Array<SpeakerSlot> slots;

    private Image dialogBox,
                  nameBox,
                  blackDrop,
                  curtain,
                  fullScreenImage;

    private Label nameLabel;
    private ProgressiveLabel dialogLabel,
                             fullScreenLabel;

    private HashMap<SpeakerPosition, SequenceAction> actionMap;

    private boolean inFullscreen;

    private Background backdrop;

    public Conversation(WYRMGame game) {
        this(game, DialogScript.FrameSeries.DEBUG);
    }

    public Conversation(WYRMGame game, DialogScript.FrameSeries conversation) {
        super(game);
        self.setFillParent(true);
        clearChildren();

        this.setDebug(true);
        // TODO: dynamic draw order priority

        background.setColor(1,1,1,0);
        addActor(background);
        backdrop = Background.NONE;

        blackDrop = new Image(game.assetHandler.solidBlueTexture);
        blackDrop.setColor(0,0,0,0);
        addActor(blackDrop);

        final Stack db = new Stack();

        final Image tmp = new Image(game.assetHandler.solidBlueTexture);
        final SpeakerSlot tmp1 = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.FAR_LEFT, this);
        final SpeakerSlot tmp2 = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.LEFT, this);
        final SpeakerSlot tmp3 = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.LEFT_OF_CENTER, this);
        final SpeakerSlot tmp4 = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.CENTER, this);
        final SpeakerSlot tmp5 = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.RIGHT_OF_CENTER, this);
        final SpeakerSlot tmp6 = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.RIGHT, this);
        final SpeakerSlot tmp7 = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.FAR_RIGHT, this);
        tmp.setColor(Color.GOLD);

        slots = new Array<>();
        slots.add(tmp1);
        slots.add(tmp2);
        slots.add(tmp3);
        slots.add(tmp4);
        slots.add(tmp5);
        slots.add(tmp6);
        slots.add(tmp7);

        dialogLabel = new ProgressiveLabel("sdfgda", game.assetHandler.menuLabelStyle);

        db.add(tmp);

        final Table dt = new Table();
        dt.setFillParent(true);
        dt.top().left();
        dt.add(dialogLabel).fill().left().top();

        dialogLabel.setText("lajslkjdagjaldg");

        db.add(dt);


        layout.setFillParent(true);
        layout.center();
        layout.add(tmp1); // 1
        layout.add(tmp2); // 2
        layout.add(tmp3); // 3
        layout.add(tmp4); // 4
        layout.add(tmp5); // 5
        layout.add(tmp6); // 6
        layout.add(tmp7); // 7
        layout.row();
        layout.add(db).colspan(7).fill().size(Gdx.graphics.getWidth() * .9f, Gdx.graphics.getHeight() * .4f);

        addActor(layout); // build conversation here

//        nameLabel = new Label("EHCKSDEE", game.assetHandler.menuLabelStyle);
//
//        final Table nameTable = new Table();
//        nameTable.add(nameLabel);
//        nameTable.setDebug(true);
//        addActor(nameTable);

        inFullscreen = false;

        fullScreenImage = new Image(game.assetHandler.solidBlueTexture);

        fullScreenImage.setColor(1,1,1,0);
        addActor(fullScreenImage);

        curtain = new Image(game.assetHandler.solidBlueTexture);
        curtain.setColor(0,0,0,0);

        addActor(curtain);

        dialogScript = new DialogScript(game);
        dialogScript.setFrameSeries();

//        slots = new Array<>();

//        dialogBox = new Image(game.assetHandler.solidBlueTexture);
//        nameBox = new Image(game.assetHandler.blueButtonTexture);



//        addBoundingBoxes();
//        mapPositionsToScreen();
//
//        moveNameBoxAndLabel(SpeakerPosition.FAR_LEFT);
//
        playNext();

//        addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int point, int button) {
//                if(dialogLabel.isActivelySpeaking()) {
//                    dialogLabel.snapToEnd();
//                } else {
//                    if(dialogScript.continues()) {
//                        playNext();
//                    } else {
//                        fadeOut();
//                    }
//                }
//            }
//        });
    }

    private void buildTable() {
        // while the main stack will resize with screen, and thus the layout table, cell properties will need to be reinitialized after resizing
    }

    private void fadeOut() {
        final Action fadeout = Actions.fadeOut(.5f);
        final Action remove = Actions.removeActor(self);

        self.addAction(new SequenceAction(fadeout, remove));

        game.activeGridScreen.hud().addAction(Actions.fadeIn(1)); // TODO: move this to toggle function in abs for setting focus to map / ui / cutscene
    }

    private void addBoundingBoxes() {
        // TODO: fade in children, fade out HUD by function call from ConvoHandler to ABS

        dialogBox.setSize(Gdx.graphics.getWidth() * .9f, Gdx.graphics.getHeight() * .4f);
        dialogBox.setPosition(Gdx.graphics.getWidth() * .5f - dialogBox.getWidth() * .5f, Gdx.graphics.getHeight() * .25f - dialogBox.getHeight() * .5f);
        addActor(dialogBox);

        dialogLabel = new ProgressiveLabel("Sample Text", game.assetHandler.menuLabelStyle);
        dialogLabel.getStyle().font.getData().markupEnabled = true;
        dialogLabel.setWrap(true);
        final float yPadding = dialogBox.getHeight() - (dialogLabel.getHeight()*2.75f);
        final float xPadding = dialogBox.getWidth() * .15f;

        dialogLabel.setBounds(dialogBox.getX() + (xPadding /2.5f) , dialogBox.getY() + yPadding, dialogBox.getWidth() - (xPadding /1.25f ), dialogLabel.getHeight()); //TODO: proper text bounding
        dialogLabel.setAlignment(Align.topLeft);

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
//        final float percentileSpacing = dialogBox.getWidth() / 8;
//        final float yPadding = dialogBox.getHeight();
//
//        final Vector2 coordinate_FAR_LEFT = new Vector2(dialogBox.getX(), dialogBox.getY() + yPadding); // TODO: DEBUG vectors. Adjust
//        final SpeakerSlot slot_FAR_LEFT = new SpeakerSlot(game.assetHandler.solidBlueTexture, coordinate_FAR_LEFT, SpeakerPosition.FAR_LEFT, self);
//        slots.add(slot_FAR_LEFT); // index 0
//
//        final Vector2 coordinate_LEFT = new Vector2(coordinate_FAR_LEFT.x + percentileSpacing, coordinate_FAR_LEFT.y);
//        final SpeakerSlot slot_LEFT = new SpeakerSlot(game.assetHandler.solidBlueTexture, coordinate_LEFT, SpeakerPosition.LEFT, self);
//        slots.add(slot_LEFT); // index 1
//
//        final Vector2 coordinate_CENTER_LEFT = new Vector2(coordinate_LEFT.x + percentileSpacing, coordinate_LEFT.y);
//        final SpeakerSlot slot_CENTER_LEFT = new SpeakerSlot(game.assetHandler.solidBlueTexture, coordinate_CENTER_LEFT, SpeakerPosition.LEFT_OF_CENTER, self);
//        slots.add(slot_CENTER_LEFT); // index 2
//
//        final Vector2 coordinate_CENTER = new Vector2(coordinate_CENTER_LEFT.x + percentileSpacing, coordinate_CENTER_LEFT.y);
//        final SpeakerSlot slot_CENTER = new SpeakerSlot(game.assetHandler.solidBlueTexture, coordinate_CENTER, SpeakerPosition.CENTER, self);
//        slots.add(slot_CENTER); // index 3
//
//        final Vector2 coordinate_CENTER_RIGHT = new Vector2(coordinate_CENTER.x + percentileSpacing, coordinate_CENTER.y);
//        final SpeakerSlot slot_CENTER_RIGHT = new SpeakerSlot(game.assetHandler.solidBlueTexture, coordinate_CENTER_RIGHT, SpeakerPosition.RIGHT_OF_CENTER, self);
//        slots.add(slot_CENTER_RIGHT); // index 4
//
//        final Vector2 coordinate_RIGHT = new Vector2(coordinate_CENTER_RIGHT.x + percentileSpacing, coordinate_CENTER_RIGHT.y);
//        final SpeakerSlot slot_RIGHT = new SpeakerSlot(game.assetHandler.solidBlueTexture, coordinate_RIGHT, SpeakerPosition.RIGHT, self);
//        slots.add(slot_RIGHT); // index 5
//
//        final Vector2 coordinate_FAR_RIGHT = new Vector2(coordinate_RIGHT.x + percentileSpacing, coordinate_CENTER_RIGHT.y);
//        final SpeakerSlot slot_FAR_RIGHT = new SpeakerSlot(game.assetHandler.solidBlueTexture, coordinate_FAR_RIGHT, SpeakerPosition.FAR_RIGHT, self);
//        slots.add(slot_FAR_RIGHT); // index 6
    }

    /**
     * moves the name box and label together between prefab screen positions
     */
    protected void moveNameBoxAndLabel(SpeakerPosition position) {
        Vector2 destination = new Vector2();
        final float yPadding = -nameBox.getHeight() * .5f;

        switch(position) {
            case FAR_LEFT:
                destination = new Vector2(slot(SpeakerPosition.FAR_LEFT).prefCoordinates.x, slot(SpeakerPosition.FAR_LEFT).prefCoordinates.y + yPadding);
                break;
            case LEFT:
                destination = new Vector2(slot(SpeakerPosition.LEFT).prefCoordinates.x, slot(SpeakerPosition.LEFT).prefCoordinates.y + yPadding);
                break;
            case LEFT_OF_CENTER:
                destination = new Vector2(slot(SpeakerPosition.LEFT_OF_CENTER).prefCoordinates.x, slot(SpeakerPosition.LEFT_OF_CENTER).prefCoordinates.y + yPadding);
                break;
            case CENTER:
                destination = new Vector2(slot(SpeakerPosition.CENTER).prefCoordinates.x, slot(SpeakerPosition.CENTER).prefCoordinates.y + yPadding);
                break;
            case RIGHT_OF_CENTER:
                destination = new Vector2(slot(SpeakerPosition.RIGHT_OF_CENTER).prefCoordinates.x, slot(SpeakerPosition.RIGHT_OF_CENTER).prefCoordinates.y + yPadding);
                break;
            case RIGHT:
                destination = new Vector2(slot(SpeakerPosition.RIGHT).prefCoordinates.x, slot(SpeakerPosition.RIGHT).prefCoordinates.y + yPadding);
                break;
            case FAR_RIGHT:
                destination = new Vector2(slot(SpeakerPosition.FAR_RIGHT).prefCoordinates.x, slot(SpeakerPosition.FAR_RIGHT).prefCoordinates.y + yPadding);
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
                return new SpeakerSlot();
        }
    }

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

        final DialogFrame nextFrame = dialogScript.nextFrame();

        if(!nextFrame.isFullscreen()) {
            if(inFullscreen) {
                fullScreenImage.addAction(Actions.fadeOut(1));
                fullScreenLabel.addAction(Actions.fadeOut(1));
            }
            checkIfSpeakerAlreadyExistsInOtherSlot(nextFrame.getSpeaker());

            if(nextFrame.isComplex()) {
                layoutComplexFrame(nextFrame);
            }

            displayBackground(nextFrame.getBackground());

            setNameLabelAndResizeBox(nextFrame.getFocusedName());
            moveNameBoxAndLabel(nextFrame.getFocusedPosition());

            displayDialog(nextFrame.getText(), nextFrame.getProgressiveDisplaySpeed(), nextFrame.getSnapToIndex());

            slot(nextFrame.getFocusedPosition()).update(nextFrame.getFocusedExpression(), nextFrame.isFacingLeft());

            if(nextFrame.usesDialogActions()) {
                parseActions(nextFrame.getActions());
            }

            dimPortraitsExceptFocused(nextFrame.getFocusedPosition());
        } else {
            displayFullscreen(nextFrame);
        }

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

    protected void parseActions(Array<DialogAction> actions) {
        actionMap = new HashMap<>();

//        ParallelAction parAct = new ParallelAction();

        // TODO: looping behavior

        for(DialogAction action : actions) {

                switch(action.getVerb()) {
                    case HOP:
                        if(actionMap.containsKey(action.getSubject())) {
                            actionMap.get(action.getSubject()).addAction(hop(action.getSubject()));
                        } else {
                            actionMap.put(action.getSubject(), hop(action.getSubject()));
                        }
                        break;
                    case SHAKE:
                        if(actionMap.containsKey(action.getSubject())) {
                            actionMap.get(action.getSubject()).addAction(shake(action.getSubject()));
                        } else {
                            actionMap.put(action.getSubject(), shake(action.getSubject()));
                        }
                        break;
                    case RUMBLE:
                        this.addAction(rumble());
                        break;

//                        if(actionMap.containsKey(action.getSubject())) {
//                            actionMap.get(action.getSubject()).addAction(rumble());
//                        } else {
//
//                        }
//                        break;
                    case SLIDE_TO:
                        if(actionMap.containsKey(action.getSubject())) {
                            actionMap.get(action.getSubject()).addAction(slideTo(action.getObject()));
                        } else {
                            actionMap.put(action.getSubject(), Actions.sequence(slideTo(action.getObject())));
                        }
                        break;
                    case BUMP_INTO:
                        if(actionMap.containsKey(action.getSubject())) {
                            actionMap.get(action.getSubject()).addAction(bumpInto(action.getObject()));
                        } else {
                            actionMap.put(action.getSubject(), bumpInto(action.getObject()));
                        }
                        break;
                    case RESET:
                        if(actionMap.containsKey(action.getSubject())) {

                        } else {

                        }
                        break;
                    default:
                        break;
                }
//
//            } else {
//                actionMap.put(action.getSubject(), new SequenceAction());
//            }

//            if(action.parallel()) parAct.addAction

        }

        for(SpeakerPosition position : SpeakerPosition.values()) {
            if(actionMap.containsKey(position)) {
                slot(position).addAction(actionMap.get(position));
            }
        }

    }

    /**
     * pre-scripted action behavior
     */
    // TODO: looping behavior
    private Action slideTo(SpeakerPosition destination) {
        // TODO: variable speed

        return Actions.moveTo(slot(destination).prefCoordinates.x, slot(destination).prefCoordinates.y,3);
    }

    private SequenceAction bumpInto(SpeakerPosition object) {

        final Vector2 v = slot(object).prefCoordinates;
        final float w = Gdx.graphics.getWidth() * .1f ;

        return Actions.sequence(
          Actions.moveTo(v.x, v.y, 3),
          Actions.moveTo(v.x - w, v.y, .5f),
          Actions.moveTo(v.x - w*.5f, v.y, .25f),
          Actions.moveTo(v.x - w, v.y, .15f)
        );
    }

    private SequenceAction hop(SpeakerPosition subject) {
        return Actions.sequence(
          Actions.moveTo(
              slot(subject).getX(),
              slot(subject).getY() + slot(subject).getHeight() * .5f, .2f),
          Actions.moveTo(
              slot(subject).getX(),
              slot(subject).getY(), .1f)
        );
    }

    private SequenceAction shake(SpeakerPosition subject) {

//        final Actor a = slot(subject).portrait;
        final float w = Gdx.graphics.getWidth() * .05f;

        return Actions.sequence(
//          Actions.moveTo(a.getX() - w, a.getY(), 0.25f),
//          Actions.moveTo(a.getX() + w, a.getY(), 0.25f),
//          Actions.moveTo(a.getX() + w, a.getY(), 0.25f),
//          Actions.moveTo(a.getX() - w, a.getY(), 0.25f)
        );
    }

    private SequenceAction rumble() {

        final float w = (Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        return Actions.sequence(
          Actions.moveTo(this.getX() - w, this.getY() - w, .025f),
          Actions.moveTo(this.getX() + w, this.getY() - w, .025f),
          Actions.moveTo(this.getX() + w, this.getY() + w, .025f),
          Actions.moveTo(this.getX() + w, this.getY() - w, .025f),
          Actions.moveTo(this.getX() - w, this.getY() - w, .025f)
        );
    }

    /**
     * sets text for dialog label.
     */
    protected void displayDialog(CharSequence sequence, float progressiveDisplaySpeed, int snapToIndex) {
        // set the dialogLabel text to sequence and display via the chosen method.
        dialogLabel.progressiveDisplay(sequence, progressiveDisplaySpeed, snapToIndex);
    }

    protected void displayDialog(CharSequence sequence, float speed) {
        dialogLabel.progressiveDisplay(sequence, speed);
    }

    protected void clearDialogBox() {
        // erase text on screen. Scroll away or fade out, something visually pleasant.
    }

    protected void dimPortraitsExceptFocused(SpeakerPosition focusedPosition) {
        // set all character portraits to dim,
        // then brighten up the focus again
        for(SpeakerSlot slot : slots) {
            if(slot.init) {
                if(slot.speakerPosition == focusedPosition) {
                    slot.brighten();
                } else {
                    slot.dim();
                }
            }
        }
    }

    protected void layoutComplexFrame(DialogFrame frame) {
        for(SpeakerSlot slot : slots) {
            slot.clearSlot();
            slot.update(frame.getExpressionAtPosition(slot.speakerPosition), frame.isFacingLeft());
        }
    }

    private void fadeBackgroundToBlack() {
        blackDrop.addAction(Actions.fadeIn(1));
    }

    private void fadeBackgroundInFromBlack() {
        blackDrop.addAction(Actions.fadeOut(1));
    }

    private void hideBackground() {
//        backgroundImage.addAction(Actions.fadeOut(1));
    }

    private void displayBackground(Background background) {
        if(this.backdrop != background && background != Background.NONE) {
            boolean fadeIn = false;
            boolean fadeFromBlack = false;

            if(this.backdrop == Background.NONE) {
                fadeIn = true;
            } else if(this.backdrop == Background.BLACK) {
                fadeFromBlack = true;
            }

            if(background == Background.REMOVE) {
                this.backdrop = Background.NONE;
            } else {
                this.backdrop = background;
            }


            switch(background) {
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
                    final Texture t = new Texture(Gdx.files.internal("test/stage.jpg"));
                    final TextureRegion r = new TextureRegion(t, 625, 450, 550,500);
//                    backgroundImage.setDrawable(new TextureRegionDrawable(r));
                    break;

                case BLACK:
                    fadeBackgroundToBlack();
                    break;

                case REMOVE:
                    hideBackground();
                    fadeBackgroundInFromBlack();
                    break;

                default:
                    break;
            }

//            if(fadeIn) backgroundImage.addAction(Actions.fadeIn(1));
            if(fadeFromBlack) fadeBackgroundInFromBlack();
        }
    }

    public void displayFullscreen(DialogFrame frame) {
        if(!inFullscreen) {
//            inFullscreen = true;
//            this.fullScreenImage = frame.getForegroundImage(); // TODO: might need copy constructor instead
//            fullScreenImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//            addActor(fullScreenImage);
//            fullScreenLabel = new ProgressiveLabel(frame.getText(), game.assetHandler.menuLabelStyle);
//            addActor(fullScreenLabel);
//            fullScreenLabel.setPosition(Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .4f); // TODO: dynamic centering
//            fullScreenLabel.progressiveDisplay(frame.getText());
        } else {
            // TODO: fade transition
//            this.fullScreenImage = frame.getForegroundImage();
//            fullScreenLabel.progressiveDisplay(frame.getText());
        }

    }

//    public Group getPortraitGroup() {
//        return portraitGroup;
//    }

    public ProgressiveLabel dialog() {
        return dialogLabel;
    }

    public void resize() {

    }

    /**
     * internal helper class
     */
    private static class SpeakerSlot extends Image {
        private Vector2 prefCoordinates; // might be able to use table cell call instead
        private final SpeakerPosition speakerPosition;
//        private Image portrait;
        private String name; // unneeded?
//        private CharacterExpression characterExpression; // Possibly unneeded
        private UnitRoster speakerRoster;
        private Conversation parent;
        private boolean shouldReset;
        private boolean init;

        public SpeakerSlot() {
            // only called on error
            speakerPosition = null;
        }
//
//        public SpeakerSlot(TextureRegion region) {
//            super(region);
//
//        }

        public SpeakerSlot(TextureRegion region, SpeakerPosition position, Conversation parent) {
            super(region);
            setColor(1,1,1,.5f);
            this.prefCoordinates = new Vector2(this.getX(), this.getY());
            this.speakerPosition = position;
            this.parent = parent;
            this.setScaling(Scaling.fillX);

//            portrait = new Image();
//            portrait.setScaling(Scaling.stretch);
//            portrait.setAlign(Align.center);
//            portrait.setColor(1,1,1,0);
//            parent.getPortraitGroup().addActor(portrait);

            speakerRoster = UnitRoster.MR_TIMN;
            init = false;
        }

        public void clearSlot() {
            if(init) {
//                Gdx.app.log("clearing slot:", "" + speakerPosition);
                speakerRoster = UnitRoster.MR_TIMN;
//                portrait.remove();
                init = false;
            }

//            portrait = new Image();
//            portrait.setPosition(screenCoordinates.x, screenCoordinates.y);
        }

        /**
         * simultaneously updates internal expression, portrait, name, and roster.
         */
        public void update(CharacterExpression expression) {
            this.update(expression, false);
        }

        public void update(CharacterExpression expression, boolean flip) {
//            this.characterExpression = expression;

            boolean portraitSet = false;

            Texture texture = new Texture(Gdx.files.internal("test/robin.png"));

            // TODO: this is all placeholder until proper images gathered and implemented into asset handler

            switch(expression) { // TODO: fold in
                case NONE:

                case LEIF_EXCITED:
                case LEIF_WINCING:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin_hurt.PNG"));
                        portraitSet = true;
                    }
                case LEIF_MANIACAL:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin_evil.jpg"));
                        portraitSet = true;
                    }
                case LEIF_THINKING:
                case LEIF_SLY:
                case LEIF_CURIOUS:
                case LEIF_DESPAIRING:
                case LEIF_BADLY_WOUNDED:
                case LEIF_ANNOYED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin_annoyed.png"));
                        portraitSet = true;
                    }
                case LEIF_EMBARRASSED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin_annoyed.png"));
                        portraitSet = true;
                    }
                case LEIF_PANICKED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin.png"));
                        portraitSet = true;
                    }
                case LEIF_WOUNDED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin_hurt.PNG"));
                        portraitSet = true;
                    }
                case LEIF_WORRIED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/robin_hurt.PNG"));
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
                    /* if(speakerRoster != UnitRoster.LEIF) */ speakerRoster = UnitRoster.LEIF;
//                    if(newSpeaker(speakerRoster)) {
//
//                    }
                    name = "Leif"; // TODO: make sure this is overwritten if desired, for example to display ??? or alt name
                    break;

                case TEMP_BAND_GIRL:

                case ANTAL_EXHAUSTED:
                case ANTAL_WORK_FACE:
                case ANTAL_DEVASTATED:
                case ANTAL_EMBARRASSED:
                case ANTAL_ENTHUSIASTIC:
                case ANTAL_BADLY_WOUNDED:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/corrin_smiling.PNG"));
                        portraitSet = true;
                    }
                    speakerRoster = UnitRoster.ANTAL;
                    break;
                case D:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/drummer.png"));
                        portraitSet = true;
                    }
                    speakerRoster = UnitRoster.D;
                    break;
                case ANVIL:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/guitarist.png"));
                        portraitSet = true;
                    }
                    speakerRoster = UnitRoster.ANVIL;
                    break;
                case TOHNI:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/singer.png"));
                        portraitSet = true;
                    }
                    speakerRoster = UnitRoster.TOHNI;
                    break;
                default:
                    texture = new Texture(Gdx.files.internal("test/robin.png"));
                    break;
            }

            TextureRegion region = new TextureRegion(texture);
            if(flip) region.flip(true,false);

            this.setDrawable(new TextureRegionDrawable(region));

        }

        public void reset() {
//            portrait.setPosition(prefCoordinates.x, prefCoordinates.y);
            shouldReset = false;
        }

//        public void needsReset() {
//            shouldReset = true;
//        }

        public void snapshotPrefScreenCoordinate() {
            prefCoordinates = new Vector2(this.getX(), this.getY());
        }

        public void dim() {
            this.setColor(.5f, .5f, .5f, 1);
        }

        public void brighten() {
            this.setColor(1,1,1,1);
        }

        public boolean newSpeaker(UnitRoster speaker) {
            return speaker != speakerRoster;
        }
    }
}
