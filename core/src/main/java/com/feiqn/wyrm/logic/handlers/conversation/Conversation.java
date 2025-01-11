package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.logic.handlers.conversation.DialogFrame.Background_ID;

import java.util.HashMap;



public class Conversation extends HUDElement {

    private final Conversation self = this;

    private final DialogScript dialogScript;

    private Array<SpeakerSlot> slots;

    private Image rearCurtain,
                  frontCurtain,
                  fullScreenImage;

    private final Stack dialogStack;

    private Container<Stack> nameContainer;
    private Container<ProgressiveLabel> dialogContainer;

    // This could be changed to allow for variable numbers of speakers. But why tho.
    // Also maybe it's a metaphore for politcs or something stupid like that.
    private final SpeakerSlot slot_FAR_LEFT;
    private final SpeakerSlot slot_LEFT;
    private final SpeakerSlot slot_LEFT_OF_CENTER;
    private final SpeakerSlot slot_CENTER;
    private final SpeakerSlot slot_RIGHT_OF_CENTER;
    private final SpeakerSlot slot_RIGHT;
    private final SpeakerSlot slot_FAR_RIGHT;

    private Label nameLabel;
    private ProgressiveLabel dialogLabel,
                             fullScreenLabel;

    private HashMap<SpeakerPosition, SequenceAction> actionMap;

    private boolean inFullscreen;

    private Background_ID backgroundID;

    public Conversation(WYRMGame game) {
        this(game, DialogScript.FrameSeries.DEBUG);
    }

    public Conversation(WYRMGame game, DialogScript.FrameSeries conversation) {
        super(game);
        self.setFillParent(true);
        clearChildren();

//        layout.setDebug(true);

        // TODO: dynamic draw order priority

        dialogStack = new Stack();
        slots       = new Array<>();

        nameLabel = new Label("Who?", game.assetHandler.nameLabelStyle);
        nameLabel.getStyle().font.getData().markupEnabled = true;

        dialogLabel = new ProgressiveLabel("Sample Text", game.assetHandler.menuLabelStyle);
        dialogLabel.getStyle().font.getData().markupEnabled = true;
        dialogLabel.setWrap(true);

        slot_FAR_LEFT        = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.FAR_LEFT, this);
        slot_LEFT            = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.LEFT, this);
        slot_LEFT_OF_CENTER  = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.LEFT_OF_CENTER, this);
        slot_CENTER          = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.CENTER, this);
        slot_RIGHT_OF_CENTER = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.RIGHT_OF_CENTER, this);
        slot_RIGHT           = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.RIGHT, this);
        slot_FAR_RIGHT       = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.FAR_RIGHT, this);

        initialBuild();

        dialogScript = new DialogScript(game);
        dialogScript.setFrameSeries();

        moveNameContainer(SpeakerPosition.FAR_LEFT);

        playNext();

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                if(dialogLabel.isActivelySpeaking()) {
                    dialogLabel.snapToEnd();
                } else {
                    if(dialogScript.continues()) {
                        playNext();
                    } else {
                        fadeOut();
                    }
                }
            }
        });
    }

    private void initialBuild() {
        slots.add(slot_FAR_LEFT);
        slots.add(slot_LEFT);
        slots.add(slot_LEFT_OF_CENTER);
        slots.add(slot_CENTER);
        slots.add(slot_RIGHT_OF_CENTER);
        slots.add(slot_RIGHT);
        slots.add(slot_FAR_RIGHT);

        backgroundImage.setColor(1,1,1,0);
        addActor(backgroundImage);
        backgroundID = Background_ID.NONE;

        rearCurtain = new Image(game.assetHandler.solidBlueTexture);
        rearCurtain.setColor(0,0,0,0);
        addActor(rearCurtain);

        final float w = Gdx.graphics.getWidth() * .95f;

        dialogContainer = new Container<>(dialogLabel).padLeft(Gdx.graphics.getWidth() * .025f).width(Gdx.graphics.getWidth() * .9f).padTop(Gdx.graphics.getHeight() * .03f); // TODO: this needs to be rebuilt after resizing
        dialogContainer.setFillParent(true);
        dialogContainer.top().left();

        dialogStack.add(new Image(game.assetHandler.solidBlueTexture));
        dialogStack.add(dialogContainer);

//        layout.setDebug(true);
        layout.padTop(Gdx.graphics.getHeight() * .025f);
        layout.padBottom(Gdx.graphics.getHeight() * .025f);
        layout.add(slot_FAR_LEFT).bottom().fill().height(Gdx.graphics.getHeight() * .40f).width(w/7).uniform();
        layout.add(slot_LEFT).bottom().fill().uniform();
        layout.add(slot_LEFT_OF_CENTER).fill().bottom().uniform();
        layout.add(slot_CENTER).bottom().fill().uniform();
        layout.add(slot_RIGHT_OF_CENTER).fill().bottom().uniform();
        layout.add(slot_RIGHT).fill().bottom().uniform();
        layout.add(slot_FAR_RIGHT).bottom().fill().uniform();
        layout.row();
        layout.add(dialogStack).colspan(7).size(Gdx.graphics.getWidth() * .95f, Gdx.graphics.getHeight() * .40f); // TODO: this needs to be rebuilt after resizing

        addActor(layout);

//        buildNameTableForPosition(SpeakerPosition.LEFT);

        final Stack nameStack = new Stack();
        nameStack.add(new Image(game.assetHandler.solidBlueTexture));
        final Container<Label> c = new Container<>(nameLabel);
        nameStack.add(c);

        nameContainer = new Container<>(nameStack);
        nameContainer.padTop(nameLabel.getHeight() * 3.5f);

        addActor(nameContainer);

        inFullscreen = false;
        fullScreenImage = new Image(game.assetHandler.solidBlueTexture);
        fullScreenImage.setColor(1,1,1,0);
        addActor(fullScreenImage);

        fullScreenLabel = new ProgressiveLabel("", game.assetHandler.menuLabelStyle);
        addActor(fullScreenLabel);

        frontCurtain = new Image(game.assetHandler.solidBlueTexture);
        frontCurtain.setColor(0,0,0,0);

        addActor(frontCurtain);
    }

    private void rebuildDialogTable() {
//        if(dialogStack.getChild(1) instanceof Table) {
//            ((Table) dialogStack.getChild(1)).clearChildren(true);
//            ((Table) dialogStack.getChild(1)).add(dialogLabel);
//        }
    }

    private void fadeOut() {
        self.addAction(Actions.sequence(Actions.fadeOut(.5f), Actions.removeActor(self)));

        game.activeGridScreen.hud().addAction(Actions.fadeIn(1)); // TODO: move this to toggle function in abs for setting focus to map / ui / cutscene
    }

    protected void moveNameContainer(SpeakerPosition position) {
        switch(position) {
            case FAR_LEFT:
//                nameTable.center();
                nameContainer.padRight(Gdx.graphics.getWidth() * .5f);
                break;
            case LEFT:
            case LEFT_OF_CENTER:
            case RIGHT_OF_CENTER:
            case CENTER:
            case RIGHT:
            case FAR_RIGHT:
                break;
        }
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
    protected void checkIfSpeakerAlreadyExistsInOtherSlot(UnitRoster speaker, SpeakerPosition skippedPosition) {
        for(SpeakerSlot slot : slots) {
            if(slot.speakerRoster == speaker && slot.speakerPosition != skippedPosition) {
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
//        nameLabel.setSize(nameLabel.getPrefWidth(), nameLabel.getPrefHeight());
//        nameBox.setSize(nameLabel.getPrefWidth() * 1.5f, nameLabel.getPrefHeight() * 1.5f);
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
//            buildLayout();
            if(inFullscreen) {
                fullScreenImage.addAction(Actions.fadeOut(1));
                fullScreenLabel.addAction(Actions.fadeOut(1));
            }
            checkIfSpeakerAlreadyExistsInOtherSlot(nextFrame.getSpeaker(), nextFrame.getFocusedPosition());

            if(nextFrame.isComplex()) {
                layoutComplexFrame(nextFrame);
            }

            displayBackground(nextFrame.getBackground());

            setNameLabelAndResizeBox(nextFrame.getFocusedName());
            moveNameContainer(nextFrame.getFocusedPosition());

            displayDialog(nextFrame.getText(), nextFrame.getProgressiveDisplaySpeed(), nextFrame.getSnapToIndex());

            slot(nextFrame.getFocusedPosition()).update(nextFrame.getFocusedExpression(), nextFrame.isFacingLeft());

            if(nextFrame.usesDialogActions()) {
                parseActions(nextFrame.getActions());
            }

            dimPortraitsExceptFocused(nextFrame.getFocusedPosition());
//            buildLayout();
        } else {
            displayFullscreen(nextFrame);
        }

        if(nextFrame.autoAutoPlay()) {
            // TODO: allow input no
            game.activeGridScreen.setInputMode(GridScreen.InputMode.LOCKED);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    game.activeGridScreen.setInputMode(GridScreen.InputMode.CUTSCENE);
                    playNext();
                    // TODO: allow input yes
                }
            }, 1f); // TODO: dynamic wait time
        }
    }

    protected void parseActions(Array<DialogAction> actions) {
        actionMap = new HashMap<>();

        ParallelAction parAct = new ParallelAction();

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

        final Actor a = slot(subject);
        final float w = Gdx.graphics.getWidth() * .05f;

        return Actions.sequence(
          Actions.moveTo(a.getX() - w, a.getY(), 0.25f),
          Actions.moveTo(a.getX() + w, a.getY(), 0.25f),
          Actions.moveTo(a.getX() + w, a.getY(), 0.25f),
          Actions.moveTo(a.getX() - w, a.getY(), 0.25f)
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
        dialogLabel.setText(sequence);
        rebuildDialogTable();
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
            if(slot.used) {
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
        rearCurtain.addAction(Actions.fadeIn(1));
    }

    private void fadeBackgroundInFromBlack() {
        rearCurtain.addAction(Actions.fadeOut(1));
    }

    private void hideBackground() {
        backgroundImage.addAction(Actions.fadeOut(1));
    }

    private void displayBackground(Background_ID backgroundID) {
        if(this.backgroundID != backgroundID && backgroundID != Background_ID.NONE) {
            boolean fadeIn = false;
            boolean fadeFromBlack = false;

            if(this.backgroundID == Background_ID.NONE) {
                fadeIn = true;
            } else if(this.backgroundID == Background_ID.BLACK) {
                fadeFromBlack = true;
            }

            if(backgroundID == Background_ID.REMOVE) {
                this.backgroundID = Background_ID.NONE;
            } else {
                this.backgroundID = backgroundID;
            }


            switch(backgroundID) {
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
                    backgroundImage.setDrawable(new TextureRegionDrawable(r));
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

            if(fadeIn) this.backgroundImage.addAction(Actions.fadeIn(1));
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
        private String name; // unneeded?
//        private CharacterExpression characterExpression; // Possibly unneeded
        private UnitRoster speakerRoster;
        private Conversation parent;
        private boolean shouldReset;
        private boolean fadedOut;
        private boolean used;
        private boolean doubleSpeak;

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
//            setColor(1,1,1,.5f);
            this.prefCoordinates = new Vector2(this.getX(), this.getY());
            this.speakerPosition = position;
            this.parent = parent;
            this.setScaling(Scaling.fillY);
            setColor(1,1,1,0);
            speakerRoster = UnitRoster.MR_TIMN;
            fadedOut = true;
            used = false;
            doubleSpeak = false;
        }

        public void clearSlot() {
            if(used) {
//                Gdx.app.log("clearing slot:", "" + speakerPosition);
                speakerRoster = UnitRoster.MR_TIMN;
                setPosition(prefCoordinates.x, prefCoordinates.y);
            }
            if(!fadedOut) {
                fadedOut = true;
                addAction(Actions.fadeOut(0.15f));
            }
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
            if(fadedOut) {
                Gdx.app.log("init", "" + speakerPosition);
                addAction(Actions.fadeIn(.25f));
                fadedOut = false;
            }

//            final Vector2 current = new Vector2(this.getX(), this.getY());
//            final float currentW = this.getWidth();
//            final float currentH = this.getHeight();
            this.setDrawable(new TextureRegionDrawable(region));
//            this.setWidth(currentW);
//            this.setHeight(currentH);
//            this.setPosition(current.x, current.y);

            if(!used) used = true;

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
            if(!fadedOut) this.setColor(.5f, .5f, .5f, 1);
        }

        public void brighten() {
            if(!fadedOut) this.setColor(1,1,1,1);
        }

        public boolean newSpeaker(UnitRoster speaker) {
            return speaker != speakerRoster;
        }
    }
}
