package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.WYRMGame;

public class ProgressiveLabel extends Label {

//    private WYRMGame game;

    private CharSequence target;

    private float lastClockTime;
    private float displaySpeed;
    private float ySpacing;
    private float dynamicPreferredHeight;
    private float clock;

    private int snapToIndex;
    private int waitLonger;

    private boolean activelySpeaking;

    public ProgressiveLabel(CharSequence text, Skin skin) {
        super(text, skin);
        sharedInit();
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String styleName) {
        super(text, skin, styleName);
        sharedInit();
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, Color color) {
        super(text, skin, fontName, color);
        sharedInit();
    }

    public ProgressiveLabel(CharSequence text, Skin skin, String fontName, String colorName) {
        super(text, skin, fontName, colorName);
        sharedInit();
    }

    public ProgressiveLabel(CharSequence text, LabelStyle style) {
        super(text, style);
        sharedInit();
    }

    private void sharedInit() {
        snapToIndex = 0;
        activelySpeaking = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(activelySpeaking) {
            update();
        }
//        clock += Gdx.graphics.getDeltaTime();
        clock += delta;
        if(clock >= 1){
            clock -= 1;;
        }
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

            lastClockTime = clock;
        }
    }

    private void update() {
        float difference = Math.abs(clock - lastClockTime);
        lastClockTime = clock;

        // TODO: check for line breaks, etc
        if(waitLonger == 0) { // not paused
            if(difference >= displaySpeed) { // long enough has passed to add a new char
                StringBuilder subSequence = new StringBuilder(getText());

                if(subSequence.length() == 0) {
                    if(target.charAt(subSequence.length()) != '[') {
                        subSequence = new StringBuilder(target.subSequence(0,subSequence.length()));
                    } else {

                        while(target.charAt(subSequence.length()) ==  '[') {
                            final int markupLength = scanForMarkupLength(target, subSequence.length());
                            subSequence = new StringBuilder(target.subSequence(0, subSequence.length() + markupLength));
                        }

//                        int length = scanForMarkupLength(target, 0);
                        subSequence = new StringBuilder("" + target.subSequence(0, subSequence.length() + 1));
                    }
                } else {
                    subSequence = new StringBuilder(target.subSequence(0, getText().length + 1)); // add the next char
                }

                if(subSequence.length() > 0) {
                    final char lastChar = subSequence.charAt(subSequence.length() - 1); // check the char we just appended to text, but has not been displayed on screen yet
                    if (isPunctuation(lastChar)) { // take a breath after certain punctuation, to emulate normal speaking rhythm
                        waitLonger = 36; // MAGIC NUMBERS BABY AWW YEAH TIED DIRECTLY TO THE FRAME RATE JUST LIKE WE LIKE IT
                    }
                }

                if(target.length() != subSequence.length()) {
                    while(target.charAt(subSequence.length()) ==  '[') {
                        final int length = scanForMarkupLength(target, subSequence.length());
                        subSequence = new StringBuilder("" + subSequence + target.subSequence(subSequence.length(), subSequence.length() + length));
                    }
                }

            setText(subSequence.toString());
                final float spacing = (ySpacing - getPrefHeight() * .5f);
                this.setPosition(getX(), spacing);
            } // TODO: fix pixel jitter on new line

            if(getText().length == target.length()) {
                waitLonger = 0;
                activelySpeaking = false;
            }
        } else {
            waitLonger--;
        }
    }

    private int scanForMarkupLength(CharSequence sequence, int startingIndex) {
        // startingIndex == '['
        int markupLength = 0;
        char nextChar;

       do {
            markupLength++;
            nextChar = sequence.charAt(startingIndex + markupLength);
        } while(nextChar != ']');


        return markupLength + 1;
    }

    // TODO: catch escaped bracket [[

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
