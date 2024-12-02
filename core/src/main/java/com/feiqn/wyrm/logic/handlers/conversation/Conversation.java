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

    public enum SpeakerPosition {
        FAR_LEFT,
        LEFT,
        CENTER_LEFT,
        CENTER,
        CENTER_RIGHT,
        RIGHT,
        FAR_RIGHT
    }

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

        speakers = new Array<>();
        mapPositionsToScreen();

        dialogBox = new Image(game.assetHandler.solidBlueTexture);
        nameBox = new Image(game.assetHandler.blueButtonTexture);

        // TODO: move following code to addBoundingBoxes()
        dialogBox.setSize(Gdx.graphics.getWidth() * .9f, Gdx.graphics.getHeight() * .4f);
        dialogBox.setPosition(Gdx.graphics.getWidth() * .5f - dialogBox.getWidth() * .5f, Gdx.graphics.getHeight() * .25f - dialogBox.getHeight() * .5f);
        addActor(dialogBox);

        // TODO: fade in children

        dialogLabel = new Label("", game.assetHandler.menuLabelStyle);

//        nameLabel = new Label("Literally Who?", game.assetHandler.menuLabelStyle);
//        nameLabel.setWidth(Gdx.graphics.getWidth() * .8f);
//        nameLabel.setPosition(0, Gdx.graphics.getHeight() * .5f);
//        addActor(nameLabel);

    }

    private void addBoundingBoxes() {
        // add dialog and name box
        addActor(dialogBox);
        addActor(nameBox);

        dialogBox.setSize(Gdx.graphics.getWidth() * .85f, Gdx.graphics.getHeight() * .4f);
        // set position: center align and off bottom border

//        nameBox.setSize(label width, label height);
    }

    protected void setSpeaker(UnitRoster speaker) {

    }

    protected void mapPositionsToScreen() {

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
