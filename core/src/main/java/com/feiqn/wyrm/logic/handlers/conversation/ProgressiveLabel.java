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

    private int waitLonger;

    private float dynamicPreferredHeight;

    private int snapToIndex;

    private boolean activelySpeaking;

    public ProgressiveLabel(CharSequence text, Skin skin, WYRMGame game) {
        super(text, skin);
        this.game = game;
        snapToIndex = 0;
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String styleName, WYRMGame game) {
        super(text, skin, styleName);
        this.game = game;
        snapToIndex = 0;
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, Color color, WYRMGame game) {
        super(text, skin, fontName, color);
        this.game = game;
        snapToIndex = 0;
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, String colorName, WYRMGame game) {
        super(text, skin, fontName, colorName);
        this.game = game;
    }

    public ProgressiveLabel(CharSequence text, LabelStyle style, WYRMGame game) {
        super(text, style);
        this.game = game;
        snapToIndex = 0;
    }

    public void setYSpacing(float spacing) {
        this.ySpacing = spacing;
    }

    public void progressivelyDisplayText(CharSequence sequence, float displaySpeed, int snapToIndex) {
        this.snapToIndex = snapToIndex;
        progressivelyDisplayText(sequence, displaySpeed);
    }

    public void progressivelyDisplayText(CharSequence sequence, float displaySpeed) {

        setText(sequence);
        dynamicPreferredHeight = getPrefHeight();

        if(displaySpeed > 0) {
            activelySpeaking = true;
            setText("");
            target = sequence;

            if(snapToIndex > 0) {
                setText(target.subSequence(0, snapToIndex));
            }

            this.displaySpeed = displaySpeed;
            game.activeBattleScreen.startedTalking();

            lastClockTime = game.activeBattleScreen.clockTime();
        }
//        Gdx.app.log("clock started at", "" + lastClockTime);
    }

    public void update() {
        float difference = Math.abs(game.activeBattleScreen.clockTime() - lastClockTime);
        lastClockTime = game.activeBattleScreen.clockTime();

        // TODO: check for markup tags and line breaks, etc
        if(waitLonger == 0) {
            if(difference >= displaySpeed) {
                final CharSequence subSequence = target.subSequence(0, getText().length + 1);
                final char lastChar = subSequence.charAt(subSequence.length()-1);

                if(isPunctuation(lastChar)) {
                    waitLonger = 36;
                }
                setText(subSequence);


                final float spacing = (ySpacing - getPrefHeight() * .5f);
                this.setPosition(getX(), spacing);
            } // TODO: fix pixel jitter on new line

            if(getText().length == target.length()) {
                game.activeBattleScreen.stoppedTalking();
                waitLonger = 0;
                activelySpeaking = false;
            }
        } else {
            waitLonger--;
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

    private boolean isPunctuation(char c) {
        switch(c) {
            case ',':
            case '.':
            case '!':
            case '?':
            case '\"':
            case ';':
            case ':':
                return true;
            default:
                return false;
        }
    }

    public boolean isActivelySpeaking() {
        return activelySpeaking;
    }
}
