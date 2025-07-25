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
import com.feiqn.wyrm.logic.handlers.conversation.dialog.*;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.scripts._1A.post.DScript_1A_POST_Leif_Antal_Campfire;
import com.feiqn.wyrm.logic.handlers.ui.HUDElement;
import com.feiqn.wyrm.logic.screens.GridScreen;
import com.feiqn.wyrm.models.unitdata.UnitRoster;
import com.feiqn.wyrm.logic.handlers.conversation.dialog.DialogFrame.Background_ID;

import java.util.HashMap;



public class Conversation extends HUDElement {

    private final Conversation self = this;

    private final DialogScript dialogScript;

    private final Array<SpeakerSlot> slots;

    private Image rearCurtain,
                  frontCurtain,
                  fullScreenImage;

    private final Stack dialogStack;
//    private final Stack nameStack;

    private final Table characterTable;
    private Table nameTable;

    // This could be changed to allow for variable numbers of speakers. But why tho.
    // Also maybe it's a metaphor for politcs or something stupid like that.
    private final SpeakerSlot slot_FAR_LEFT;
    private final SpeakerSlot slot_LEFT;
    private final SpeakerSlot slot_LEFT_OF_CENTER;
    private final SpeakerSlot slot_CENTER;
    private final SpeakerSlot slot_RIGHT_OF_CENTER;
    private final SpeakerSlot slot_RIGHT;
    private final SpeakerSlot slot_FAR_RIGHT;

    private final Label nameLabel;
    private final Label doubleSpeakNameLabel;
    private final ProgressiveLabel dialogLabel;
    private ProgressiveLabel fullScreenLabel;

    private boolean inFullscreen;
    private boolean firstFrame;
    private boolean choreographing;

    private Background_ID backgroundID;

    public Conversation(WYRMGame game) {
        this(game, new DialogScript());
    }

    public Conversation(WYRMGame game, DialogScript script) {
        super(game);
        clearChildren();

        // TODO: dynamic draw order priority?

        dialogStack    = new Stack();
        slots          = new Array<>();
        characterTable = new Table();
        nameTable      = new Table();

        choreographing = false;

        nameLabel = new Label("Who?", game.assetHandler.nameLabelStyle);
        nameLabel.getStyle().font.getData().markupEnabled = true;
        nameLabel.setAlignment(1);

        doubleSpeakNameLabel = new Label("", game.assetHandler.nameLabelStyle);
        doubleSpeakNameLabel.getStyle().font.getData().markupEnabled = true;
        doubleSpeakNameLabel.setAlignment(1);

        dialogLabel = new ProgressiveLabel("Sample Text", game.assetHandler.menuLabelStyle);
        dialogLabel.getStyle().font.getData().markupEnabled = true;
        dialogLabel.setWrap(true);

        slot_FAR_LEFT        = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.FAR_LEFT);
        slot_LEFT            = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.LEFT);
        slot_LEFT_OF_CENTER  = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.LEFT_OF_CENTER);
        slot_CENTER          = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.CENTER);
        slot_RIGHT_OF_CENTER = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.RIGHT_OF_CENTER);
        slot_RIGHT           = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.RIGHT);
        slot_FAR_RIGHT       = new SpeakerSlot(game.assetHandler.solidBlueTexture, SpeakerPosition.FAR_RIGHT);

        slots.add(slot_FAR_LEFT);
        slots.add(slot_LEFT);
        slots.add(slot_LEFT_OF_CENTER);
        slots.add(slot_CENTER);
        slots.add(slot_RIGHT_OF_CENTER);
        slots.add(slot_RIGHT);
        slots.add(slot_FAR_RIGHT);

        initialBuild();

        dialogScript = script;

        moveNameLabel(SpeakerPosition.FAR_LEFT);

        playNext();

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return game.activeGridScreen.getInputMode() != GridScreen.InputMode.LOCKED;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int point, int button) {
                if(game.activeGridScreen.getInputMode() == GridScreen.InputMode.LOCKED) return;
                if(choreographing) return;

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
        backgroundImage.setColor(1,1,1,0);
        addActor(backgroundImage);
        backgroundID = Background_ID.NONE;

        rearCurtain = new Image(game.assetHandler.solidBlueTexture);
        rearCurtain.setColor(0,0,0,0);
        addActor(rearCurtain);

        Container<ProgressiveLabel> dialogLabelContainer = new Container<>(dialogLabel).padLeft(Gdx.graphics.getWidth() * .025f).width(Gdx.graphics.getWidth() * .9f).padTop(Gdx.graphics.getHeight() * .03f).padRight(Gdx.graphics.getWidth() * .025f);
        dialogLabelContainer.setFillParent(true);
        dialogLabelContainer.top().left();

        dialogStack.add(new Image(game.assetHandler.solidBlueTexture));
        dialogStack.add(dialogLabelContainer);

        buildCharTable();

        layout.pad(Gdx.graphics.getHeight() * .025f);
        layout.center();

        buildLayoutNormal();

        final Container<Table> layoutContainer = new Container<>(layout);
        addActor(layoutContainer);

        addActor(nameTable);

        inFullscreen = false;
        fullScreenImage = new Image(game.assetHandler.solidBlueTexture);
        fullScreenImage.setColor(1,1,1,0);
        addActor(fullScreenImage);

        fullScreenLabel = new ProgressiveLabel("", game.assetHandler.menuLabelStyle);
        addActor(fullScreenLabel);

        frontCurtain = new Image(game.assetHandler.solidBlueTexture);
        frontCurtain.setColor(0,0,0,0);

        addActor(frontCurtain);

        layout.setColor(1,1,1,0);
        nameTable.setColor(1,1,1,0);
        firstFrame = true;
    }

    private void buildLayoutNormal() {
        layout.clearChildren();
        layout.add(characterTable).fill().uniform();
        layout.row();
        layout.add(dialogStack).fill().uniform();

    }

    private void buildCharTable() {
        characterTable.clearChildren();
        characterTable.add(slot_FAR_LEFT).bottom().fill().uniform();
        characterTable.add(slot_LEFT).bottom().fill().uniform();
        characterTable.add(slot_LEFT_OF_CENTER).fill().bottom().uniform();
        characterTable.add(slot_CENTER).bottom().fill().uniform();
        characterTable.add(slot_RIGHT_OF_CENTER).fill().bottom().uniform();
        characterTable.add(slot_RIGHT).fill().bottom().uniform();
        characterTable.add(slot_FAR_RIGHT).bottom().fill().uniform();
    }

    private void buildLayoutDouble() {
        layout.clearChildren();

    }

    private void fadeOut() {
        Gdx.app.log("conversation", "fadeOut");
        self.addAction(Actions.sequence(
            Actions.fadeOut(1),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.activeGridScreen.endConversation();
                }
        })));

    }

    private void setDoubleSpeakNames(SpeakerPosition pos1, SpeakerPosition pos2) {
        nameTable.clearChildren();
        for(SpeakerPosition pos : SpeakerPosition.values()) {
            if(pos1 == pos) {
                nameTable.add(nameLabel).fill().width((float) Gdx.graphics.getWidth() / 8).uniform();
            } else if(pos2 == pos) {
                nameTable.add(doubleSpeakNameLabel).fill().uniform();
            } else {
                nameTable.add().uniform();
            }
        }

    }

    protected void moveNameLabel(SpeakerPosition position) {
        nameTable.clearChildren();
        nameTable.center();

        for(SpeakerPosition pos : SpeakerPosition.values()) {
            if(position == pos) {
                nameTable.add(nameLabel).width(layout.getWidth() / 8).uniform();
            } else {
                nameTable.add().uniform();
            }
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
     * Steps through dialog frames. <br>
     * The main function you should call to handle everything else.
     */
    public void playNext() {
        /* continue on to the next frame.
         * some contextual behavior will be desirable here, for
         * fading character portraits in or out, moving between
         * screen positions, or ending the conversation.
         */

        try {
            final DialogFrame nextFrame = dialogScript.nextFrame();

            if(!nextFrame.isFullscreen()) {
                if(inFullscreen) {
                    fullScreenImage.addAction(Actions.fadeOut(1));
                    fullScreenLabel.addAction(Actions.fadeOut(1));
                }

                boolean choreographed = false;
                if(nextFrame.usesDialogActions()) {
                    for(DialogAction action : nextFrame.getActions()) {
                        if(action.getVerb() == DialogAction.Type.CHOREOGRAPHY) {
                            choreographed = true;
                        }
                    }
                    parseActions(nextFrame.getActions());
                }

                if(!choreographed) {
                    if(firstFrame) {
                        firstFrame = false;
                        nameTable.addAction(Actions.fadeIn(.5f));
                        layout.addAction(Actions.fadeIn(.5f));
                    }

                    checkIfSpeakerAlreadyExistsInOtherSlot(nextFrame.getSpeaker(), nextFrame.getFocusedPosition());

                    if(nextFrame.isComplex()) {
                        layoutComplexFrame(nextFrame);
                    } else {
                        slot(nextFrame.getFocusedPosition()).update(nextFrame.getFocusedExpression(), nextFrame.isFacingLeft());
                    }

                    displayBackground(nextFrame.getBackground());

                    nameLabel.setText(nextFrame.getFocusedName());
                    moveNameLabel(nextFrame.getFocusedPosition());

                    displayDialog(nextFrame.getText(), nextFrame.getProgressiveDisplaySpeed(), nextFrame.getSnapToIndex());

                    dimPortraitsExcept(nextFrame.getFocusedPosition());
                }

            } else {
                displayFullscreen(nextFrame);
            }

            if(nextFrame.autoAutoPlay()) {
                game.activeGridScreen.setInputMode(GridScreen.InputMode.LOCKED);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.activeGridScreen.setInputMode(GridScreen.InputMode.CUTSCENE);
                        playNext();
                    }
                }, 1f); // TODO: dynamic wait time
            }
        } catch (Exception e) {
            Gdx.app.log("playNext", "CRASH HANDLED");
//            fadeOut();
        }
    }

    protected void parseActions(Array<DialogAction> actions) {
        HashMap<SpeakerPosition, SequenceAction> actionMap = new HashMap<>();

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
                    case FLIP:
                        break;
                    case CHOREOGRAPHY:
                        Gdx.app.log("parseActions", "choreographing");
                        beginChoreography(action.getChoreography());
                        break;
                    case ARBITRARY_CODE:
                        action.getCode().run();
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

    private void beginChoreography(DialogChoreography choreography) {
        this.addAction(Actions.fadeOut(0.5f));
//        ags.setInputMode(GridScreen.InputMode.LOCKED);
        choreographing = true;

        // do choreography
        switch (choreography.getType()) {

            case MOVE:
                // TODO: interpolate for move speed, move along path

//                Path path = ags.getRecursionHandler().shortestPath(choreography.getSubject(), ags.getLogicalMap().getTileAtPositionCOLUMNROW((int) choreography.getLocation().x, (int) choreography.getLocation().y), true);

                choreography.getSubject().addAction(Actions.sequence(
                        Actions.moveTo(choreography.getLocation().x, choreography.getLocation().y, 1),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                endChoreography();
                            }
                        })));
                break;

            case ATTACK:
                break;

            case ABILITY:

                // TODO: switch based on ability

                ags.conditions().combat().abilities().DiveBomb(game, choreography.getObject());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        endChoreography();
                    }
                }, 1.f);

                break;

            case FOCUS_UNIT:
                ags.centerCameraOnLocation(choreography.getSubject().getColumnX(),choreography.getSubject().getRowY());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        endChoreography();
                    }
                }, 1);
                break;

            case FOCUS_TILE:

                break;

            case SHORT_PAUSE:
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        endChoreography();
                    }
                }, 1);
                break;

            case LINGER:
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        endChoreography();
                    }
                }, 3f);
                break;

            case SPAWN:
                    ags.rootGroup.addActor(choreography.getSubject());
                    ags.getLogicalMap().placeUnitAtPositionXY(choreography.getSubject(), (int)choreography.getLocation().x, (int)choreography.getLocation().y);
                    ags.conditions().teams().addUnitToTeam(choreography.getSubject());
                    ags.conditions().addToTurnOrder(choreography.getSubject());
                    choreography.getSubject().setCannotMove();
                    choreography.getSubject().setColor(1,1,1,0);
                    choreography.getSubject().addAction(Actions.sequence(
                        Actions.fadeIn(1),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                endChoreography();
                            }
                        })
                    ));
                break;

            case DESPAWN:
                break;

            case REVEAL_VICTCON:
                ags.conditions().revealVictCon(choreography.getVictConFlagID());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        endChoreography();
                    }
                }, .5f);
                break;

            case BALLISTA_ATTACK:
                ags.conditions().combat().simpleVisualCombat(choreography.getSubject(), choreography.getObject());
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        endChoreography();
                    }
                }, 1);
                break;

            case FADE_OUT_TO_BLACK:
                ags.setInputMode(GridScreen.InputMode.LOCKED);

                Image fadeOutImage = new Image(game.assetHandler.solidBlueTexture);
                fadeOutImage.setColor(0,0,0,1);

                Container<Image> fadeOutContainer = new Container<>(fadeOutImage).fill();
                fadeOutContainer.setColor(0,0,0,0);
                fadeOutContainer.setFillParent(true);
                ags.gameStage.addActor(fadeOutContainer);
                fadeOutContainer.addAction(Actions.sequence(
                    Actions.fadeIn(3),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            playNext();
                        }
                    })
                ));
                break;

            case SCREEN_TRANSITION:
                ags.setInputMode(GridScreen.InputMode.LOCKED);
//                Gdx.app.log("conversation", "Waiting to transition");
//                Timer.schedule(new Timer.Task() {
//                    @Override
//                    public void run() {
//                        Gdx.app.log("conversation", "transitioning");
                        game.activeScreen = choreography.getScreenForTransition();
                        if(choreography.getScreenForTransition() instanceof GridScreen) game.activeGridScreen = (GridScreen) choreography.getScreenForTransition();
                        game.transitionScreen(choreography.getScreenForTransition());
//                    }
//                }, 5);
                break;

            default:
                break;

        }
    }

    private void endChoreography() {
        choreographing = false;
        if(firstFrame) {
            if(!dialogScript.previewNextFrame().isChoreographed()) {
                layout.addAction(Actions.fadeIn(.5f));
                nameTable.addAction(Actions.fadeIn(.5f));
                firstFrame = false;
            }
        }
        if(dialogScript.continues()) {
//            if(!dialogScript.previewNextFrame().isChoreographed())
            this.addAction(Actions.fadeIn(0.5f));
            ags.setInputMode(GridScreen.InputMode.CUTSCENE);
            Gdx.app.log("conversation", "attempting to end choreography");
            playNext();
        } else {
            ags.setInputMode(GridScreen.InputMode.STANDARD);
            fadeOut();
        }

    }

    /**
     * sets text for dialog label.
     */
    protected void displayDialog(CharSequence sequence, float progressiveDisplaySpeed, int snapToIndex) {
        // set the dialogLabel text to sequence and display via the chosen method.
        dialogLabel.setText(sequence);
        dialogLabel.progressiveDisplay(sequence, progressiveDisplaySpeed, snapToIndex);
    }

    protected void displayDialog(CharSequence sequence, float speed) {
        dialogLabel.progressiveDisplay(sequence, speed);
    }

    protected void clearDialogBox() {
        // erase text on screen. Scroll away or fade out, something visually pleasant.
    }

    protected void dimPortraitsExcept(SpeakerPosition focusedPosition) {
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

    /**
     * internal helper class
     */
    private static class SpeakerSlot extends Image {
        private Vector2 prefCoordinates; // might be able to use table cell call instead
        private final SpeakerPosition speakerPosition;
        private UnitRoster speakerRoster;
        private boolean fadedOut;
        private boolean used;

        public SpeakerSlot() {
            // only called on error
            speakerPosition = null;
        }

        public SpeakerSlot(TextureRegion region, SpeakerPosition position) {
            super(region);
            this.prefCoordinates = new Vector2(this.getX(), this.getY());
            this.speakerPosition = position;
            this.setScaling(Scaling.fillY);
            setColor(1,1,1,0);
            speakerRoster = UnitRoster.MR_TIMN;
            fadedOut = true;
            used = false;
        }

        public void clearSlot() {
            if(used) {
//                Gdx.app.log("clearing slot:", "" + speakerPosition);
                speakerRoster = UnitRoster.MR_TIMN;
                setPosition(prefCoordinates.x, prefCoordinates.y);
            }
            if(!fadedOut) {
                fadedOut = true;
//                addAction(Actions.fadeOut(0.15f));
                setColor(1,1,1,0);
            }
        }

        /**
         * simultaneously updates internal expression, portrait, name, and roster.
         */
        public void update(CharacterExpression expression) {
            this.update(expression, false);
        }

        public void update(CharacterExpression expression, boolean flip) {
            boolean portraitSet = false;

            Texture texture = new Texture(Gdx.files.internal("test/robin.png"));

            // TODO: this is all placeholder until proper images gathered and implemented into asset handler

            switch(expression) { // TODO: fold in
                case NONE:

                    break;
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
                case LEIF_DETERMINED:
                case LEIF_ANNOYED:
                case LEIF_EXHAUSTED:
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
                case LEIF_SURPRISED:
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
                    if(speakerRoster != UnitRoster.LEIF)  speakerRoster = UnitRoster.LEIF;

//                    name = "Leif"; // TODO: make sure this is overwritten if desired, for example to display ??? or alt name
                    break;

                case GENERIC_SOLDIER:
//                    break;

            // THE BAND:
                case TEMP_KAI:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/whichbythewaymeansloveinchinese/t_kai1.png"));
                        portraitSet = true;
                    }
                    if(speakerRoster != UnitRoster.KAI) speakerRoster = UnitRoster.KAI;
                    break;

                case TEMP_JAY:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/whichbythewaymeansloveinchinese/t_jay1.png"));
                        portraitSet = true;
                    }
                    if(speakerRoster != UnitRoster.JAY) speakerRoster = UnitRoster.JAY;
                    break;

                case TEMP_MOE:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/whichbythewaymeansloveinchinese/t_moe1.png"));
                        portraitSet = true;
                    }
                    if(speakerRoster != UnitRoster.MOE) speakerRoster = UnitRoster.MOE;
                    break;

                case TEMP_ALEX:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/whichbythewaymeansloveinchinese/t_alex1.png"));
                        portraitSet = true;
                    }
                    if(speakerRoster != UnitRoster.ALEX) speakerRoster = UnitRoster.ALEX;
                    break;

                case TEMP_RILEY:
                    if(!portraitSet) {
                        texture = new Texture(Gdx.files.internal("test/whichbythewaymeansloveinchinese/t_riley1.png"));
                        portraitSet = true;
                    }
                    if(speakerRoster != UnitRoster.RILEY) speakerRoster = UnitRoster.RILEY;
                    break;

                case ANTAL_EXHAUSTED:
                case ANTAL_WORK_FACE:
                case ANTAL_DEVASTATED:
                case ANTAL_EMBARRASSED:
                case ANTAL_ENTHUSIASTIC:
                case ANTAL_SAD:
                case ANTAL_CURIOUS:
                case ANTAL_WORRIED:
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

                case THE_GREAT_WYRM:
                default:
                    texture = new Texture(Gdx.files.internal("test/robin.png"));
                    break;
            }

            if(expression != CharacterExpression.NONE) {
                TextureRegion region = new TextureRegion(texture);
                this.setDrawable(new TextureRegionDrawable(region));

                if(flip) region.flip(true,false);
                if(fadedOut) {
//                addAction(Actions.fadeIn(2f));
                    setColor(1,1,1,1);
                    fadedOut = false;
                }

                if(!used) used = true;
            }

        }

        public void reset() {
//            portrait.setPosition(prefCoordinates.x, prefCoordinates.y);
//            shouldReset = false;
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
