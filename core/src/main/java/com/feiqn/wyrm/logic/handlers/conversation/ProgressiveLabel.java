package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.WYRMGame;

public class ProgressiveLabel extends Label {

    private WYRMGame game;

    private float lastClockTime;
    private float displaySpeed;

    private CharSequence target;

    private float ySpacing;

    private int waitLonger;

    private float dynamicPreferredHeight;

    private int snapToIndex;

    private boolean activelySpeaking;
    private boolean parsingMarkup;

    public ProgressiveLabel(CharSequence text, Skin skin, WYRMGame game) {
        super(text, skin);
        sharedInit(game);
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String styleName, WYRMGame game) {
        super(text, skin, styleName);
        sharedInit(game);
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, Color color, WYRMGame game) {
        super(text, skin, fontName, color);
        sharedInit(game);
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, String colorName, WYRMGame game) {
        super(text, skin, fontName, colorName);
        sharedInit(game);
    }

    public ProgressiveLabel(CharSequence text, LabelStyle style, WYRMGame game) {
        super(text, style);
        sharedInit(game);
    }

    private void sharedInit(WYRMGame game) {
        snapToIndex = 0;
        this.game = game;
        parsingMarkup = false;
        activelySpeaking = false;
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
                CharSequence subSequence;
                if(!parsingMarkup) {
                    subSequence = target.subSequence(0, getText().length + 1);
                } else {
                    subSequence = removeClosingTag(getText());
                }

                final char lastChar = subSequence.charAt(subSequence.length()-1);
                if(isPunctuation(lastChar) && !parsingMarkup) {
                    waitLonger = 36;
                }

                // TODO: make this actually work!
//                if(target.length() != subSequence.length()) {
//                    final char nextChar = target.charAt(subSequence.length()); // TODO: bad line here?
//                    if (nextChar == '[' && !parsingMarkup) {
//                        parsingMarkup = true;
//                        final int lengthToSkip = scanForMarkupLength(target, subSequence.length());
//
//                        subSequence = "" + subSequence + target.subSequence(subSequence.length(), lengthToSkip);
//                        subSequence = appendClosingTag(subSequence);
//                    } else if (nextChar != '[' && parsingMarkup) {
//                        if (target.charAt(subSequence.length() + 1) == ']') {
//                            subSequence = target.subSequence(0, subSequence.length() + 1);
//                        } else {
//                            final int lengthToSkip = scanForMarkupLength(target, subSequence.length());
//
//                            subSequence = "" + subSequence + target.subSequence(subSequence.length(), lengthToSkip);
//                            subSequence = appendClosingTag(subSequence);
//                        }
//                    }
//                }
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

    private CharSequence appendClosingTag(CharSequence sequence) {
        return sequence + "[]";
    }

    private CharSequence removeClosingTag(CharSequence sequence) {
        return sequence.subSequence(0, sequence.length()-2);
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
    private int scanForMarkupLength(CharSequence sequence, int startingIndex) {
        // TODO: scan ahead for [] tag
        return 0;
    }

//    private boolean isMarkup(char c) {
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
