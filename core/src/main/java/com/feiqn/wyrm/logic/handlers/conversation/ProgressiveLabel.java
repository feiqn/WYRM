package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.WYRMGame;

public class ProgressiveLabel extends Label {

    private final WYRMGame game;

    private float lastClockTime;
    private float displaySpeed;

    private CharSequence target;

    private float ySpacing;

    private float dynamicPreferredHeight;

    public ProgressiveLabel(CharSequence text, Skin skin, WYRMGame game) {
        super(text, skin);
        this.game = game;
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String styleName, WYRMGame game) {
        super(text, skin, styleName);
        this.game = game;
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, Color color, WYRMGame game) {
        super(text, skin, fontName, color);
        this.game = game;
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, String colorName, WYRMGame game) {
        super(text, skin, fontName, colorName);
        this.game = game;
    }

    public ProgressiveLabel(CharSequence text, LabelStyle style, WYRMGame game) {
        super(text, style);
        this.game = game;
    }

    public void setYSpacing(float spacing) {
        this.ySpacing = spacing;
    }

    public void progressivelyDisplayText(CharSequence sequence, float displaySpeed) {

        setText(sequence);
        dynamicPreferredHeight = getPrefHeight();
        setText("");
        target = sequence;

        this.displaySpeed = displaySpeed;
        game.activeBattleScreen.startedTalking();

        lastClockTime = game.activeBattleScreen.clockTime();

//        Gdx.app.log("clock started at", "" + lastClockTime);
    }

    public void update() {
        float difference = Math.abs(game.activeBattleScreen.clockTime() - lastClockTime);
        lastClockTime = game.activeBattleScreen.clockTime();

        // TODO: check for markup tags and line breaks, etc

        if(difference >= displaySpeed) {
            setText(target.subSequence(0, getText().length + 1));

            final float spacing = (ySpacing - getPrefHeight() * .5f);
            this.setPosition(getX(), spacing);
        } // TODO: fix pixel jitter on new line

        if(getText().length == target.length()) {
            game.activeBattleScreen.stoppedTalking();
        }
    }

    // TODO: this one is gonna be a doozey. I really didn't wanna have to do this, but I know it's the right thing to do.

//    private float absoluteDifference(float x, float y) {
//
//    }
//
//    private int charLengthToBound() {
//
//    }
//
//    private int scanAheadForWordLength(CharSequence sequence) {
//
//    }
}
