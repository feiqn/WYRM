package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.UnitRoster;

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

    private Array<UnitRoster> speakers;

    private Image dialogBox,
                  nameBox;

    private Label nameLabel,
                  dialogLabel;

    private Vector2 farLeftPosition,
                    leftPosition,
                    centerLeftPosition,
                    centerPosition,
                    centerRightPosition,
                    rightPosition,
                    farRightPosition;

    public Conversation(WYRMGame game) {
        this.game = game;

        dialogFrameHandler = new DialogFrameHandler(game);

        speakers = new Array<>();
        mapPositionsToScreen();

        dialogBox = new Image(game.assetHandler.solidBlueTexture);
        nameBox = new Image(game.assetHandler.blueButtonTexture);

        addBoundingBoxes();

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

        moveNameBoxAndLabel(SpeakerPosition.LEFT);

        addActor(nameBox);
        addActor(nameLabel);

    }

    protected void moveNameBoxAndLabel(SpeakerPosition position) {
        // TODO: shift name box and label to mapped vector2 position minus half box height, inset into dialog box graphic for slight overlay
        switch(position) {

        }
    }

    protected void setSpeaker(UnitRoster speaker) {

    }

    protected void mapPositionsToScreen() {
        // declare vector2 positions for character portraits
    }

    public void playNext() {

    }

    protected void displayDialog(CharSequence sequence) {
        clearDialogBox();

    }

    protected void clearDialogBox() {

    }

    protected void flipSpeaker(UnitRoster speaker) {

    }

    protected void flipSpeaker(int index) {

    }

    protected void setSpeakerAtPosition(UnitRoster speaker, SpeakerPosition position, boolean flipped) {

    }

    protected void setSpeakerAtPosition(UnitRoster speaker, SpeakerPosition position) {
        setSpeakerAtPosition(speaker, position, false);
    }
    protected void setSpeakerAtPosition(int index, SpeakerPosition position, boolean flipped) {
        if(speakers.size <= index) {
            setSpeakerAtPosition(speakers.get(index), position, flipped);
        }
    }
    protected void setSpeakerAtPosition(int index, SpeakerPosition position) {
        if(speakers.size <= index) {
            setSpeakerAtPosition(speakers.get(index), position, false);
        }
    }

    public void addSpeaker(UnitRoster speaker) {
        speakers.add(speaker);
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void displayBackground() {
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
}
