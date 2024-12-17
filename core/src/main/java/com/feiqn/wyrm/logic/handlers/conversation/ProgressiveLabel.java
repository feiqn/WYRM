package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * An extension of Nathan Sweet's Label widget.
 * <p>
 * This provides the popular game feature of displaying text progressively,
 * or one character at a time, while intelligently parsing LibGDX Color Markup Language
 * on the fly. To do so, call progressivelyDisplayText(CharSequence sequence)
 * </p>
 * <p>
 * By default, the Label will automatically take short pauses after appropriate punctuation,
 * such as commas and periods, to emulate a normal speaking rhythm. The length of these "breaths"
 * can be manually set or disabled via setPunctuationPause(int pauseLength). Setting to 0 will disable pausing.
 * </p>
 * <p>
 * Several methods are provided for adding flare and flavor to your dialogue. Examples of how these can be used
 * can be found on GitHub. </br>
 * While the Label will handle itself once added to the scene, other objects can query its status with isActivelySpeaking();
 * </p>
 * @author Feiqn */

public class ProgressiveLabel extends Label {

    private CharSequence target;
    private float lastClockTime;
    private float displaySpeed;
    private float ySpacing;
    private float dynamicPreferredHeight; // TODO: something about this or bounds for line jitter
    private float clock;
    private int snapToIndex;
    private int waitLonger;
    private int punctuationPause;
    private boolean activelySpeaking;

    // TODO: voice beeps and bops!

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
        punctuationPause = 15;
        displaySpeed = 0.01f;
        ySpacing = 0;
        waitLonger = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(shouldUpdate()) {
            update();
        }
        clock += delta;
        if(clock >= 1){
            clock -= 1;;
        }
    }

    public void setYSpacing(float spacing) {
        this.ySpacing = spacing;
    }

    public void progressiveDisplay(CharSequence sequence) {
        progressiveDisplay(sequence, displaySpeed, snapToIndex);
    }

    public void progressiveDisplay(CharSequence sequence, float displaySpeed, int snapToIndex) {
        this.snapToIndex = snapToIndex;
        progressiveDisplay(sequence, displaySpeed);
    }

    public void progressiveDisplay(CharSequence sequence, float displaySpeed) {

        setText(sequence);
        dynamicPreferredHeight = getPrefHeight();

        if(displaySpeed > 0) { // A Display speed of 0 will display the entire text in one frame, rather than progressively. This can be used for dynamic affect when scripting conversations.
            activelySpeaking = true;
            setText("");
            Gdx.app.log("Target:", "" +sequence);
            target = sequence;

            if(snapToIndex > 0) {
                setText(target.subSequence(0, snapToIndex));
            }

            this.displaySpeed = displaySpeed;

            lastClockTime = clock;
        }
    }

    private void update() {
        final float difference = Math.abs(clock - lastClockTime);
        lastClockTime = clock;

        // TODO: grab next word length and calculate if new line insert is needed before printing

        if(difference >= displaySpeed) { // long enough has passed to add a new char
            if(waitLonger <= 0) { // not paused for punctuation, etc.
                StringBuilder subSequence = new StringBuilder(getText());

                boolean endOfSequence = target.length() == subSequence.length();

                while(!endOfSequence && target.charAt(subSequence.length()) == '[') { // check for additional [MARKUP][TAGS][JUST]TO[TRIP[][][][][][][][][][][][][][][]YOU[][][[UP[
                    final int markupLength = scanMarkupLength(target, subSequence.length()); // get the length of the markup tag
                    subSequence = new StringBuilder(target.subSequence(0, subSequence.length() + markupLength)); // then append the entire markup tag in one go, so it is never partially displayed incorrectly as plaintext

                    endOfSequence = target.length() == subSequence.length();
                }
                if(!endOfSequence && target.charAt(subSequence.length()) == '\\') {
                    subSequence = new StringBuilder(target.subSequence(0, subSequence.length() + 2)); // automatically grab escaped characters, i.e., \n
                }
                if(!endOfSequence) {
                    subSequence = new StringBuilder(target.subSequence(0, subSequence.length() + 1)); // Not sure about appropriate use of calling constructor again rather than update method
                }

                final char lastChar = subSequence.charAt(subSequence.length() - 1); // Check the char we just appended to text, but has not been displayed on screen yet.
                if(isPunctuation(lastChar)) { // Take a breath after certain punctuation, to emulate normal speaking rhythm.
                    waitLonger = punctuationPause; // This represents the amount of calls to update() which will be internally ignored, after accounting for deltaTime. Thus, the time it will wait = (waitLonger * displaySpeed)
                } else {
                    int wordLength = scanWordLength(target, subSequence.length()-1);
                    // if wordLength would cause string to overflow:
//                        subSequence = new StringBuilder(target.subSequence(0, subSequence.length() + wordLength));
                }

                setText(subSequence.toString());

                final float spacing = (ySpacing - getPrefHeight() * .5f); // TODO: fix pixel jitter on new line
                this.setPosition(getX(), spacing);

                if(getText().length == target.length()) {
                    waitLonger = 0;
                    activelySpeaking = false;
                }
            } else {
                waitLonger--;
            }
        }
    }

    private int scanWordLength(CharSequence sequence, int startingIndex) {
        // startingIndex == first letter of word. I.E., WORD
        //                                              ^starting index
        int wordLength = 0;

        if(startingIndex+1 > sequence.length()-1) { // out of bounds
            return 1;
        }
        char nextChar;

        do {
            wordLength++;
            nextChar = sequence.charAt(startingIndex + wordLength);
        } while(nextChar != ' ' && sequence.length() >= startingIndex + wordLength);

        return wordLength;
    }

    private int scanMarkupLength(CharSequence sequence, int startingIndex) {
        // startingIndex == '['
        int markupLength = 0;

        if(startingIndex+1 > sequence.length()-1) { // out of bounds
            return 1;
        }
        char nextChar = sequence.charAt(startingIndex + 1);

        if(nextChar == '[') {
            return 2; // escaped bracket., I.E., [[
        }

        do {
            markupLength++;
            nextChar = sequence.charAt(startingIndex + markupLength);
        } while(nextChar != ']');

        return markupLength + 1;
    }

    // TODO: if word will overflow to new line, insert line break preemptively

    // TODO: blink() | on last char, blinkSpeed, etc.

    // TODO: right-to-left display

    // TODO: top-down display (JP, etc.)

    private boolean isPunctuation(char c) {
        switch(c) {
            case ',':
            case '.':
            case '!':
            case '?':
            case ';':
            case ':':
                return true;
            default:
                return false;
        }
    }

    private boolean shouldUpdate() {
        // Made this now for future proofing, as I feel further refactoring may be optimal.
        return activelySpeaking;
    }

    public void setPunctuationPause(int pauseLength) {
        punctuationPause = pauseLength;
    }

    public boolean isActivelySpeaking() {
        return activelySpeaking;
    }
}
