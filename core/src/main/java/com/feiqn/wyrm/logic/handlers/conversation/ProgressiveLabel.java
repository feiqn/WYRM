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
    private int parsingDepth;
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
//        this.game = game;
        parsingDepth = 0;
        activelySpeaking = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(activelySpeaking) {
            update();
        }
        clock += Gdx.graphics.getDeltaTime();
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
//            game.activeBattleScreen.startedTalking(); // TODO: refactor to not rely on abs

            lastClockTime = clock;
        }
//        Gdx.app.log("clock started at", "" + lastClockTime);
    }

    private void update() { // TODO: refactor and comment. This is the ugliest function I have ever seen in my entire life.
        float difference = Math.abs(clock - lastClockTime);
        lastClockTime = clock;

        // TODO: check for markup tags and line breaks, etc
        if(waitLonger == 0) { // not paused
            if(difference >= displaySpeed) { // long enough has passed to add a new char
                CharSequence subSequence = getText();
                if(parsingDepth == 0) { // not parsing markup, behave normally
                    subSequence = target.subSequence(0, getText().length + 1); // add the next char // TODO: might hang on 0 as is?
                } else { // accounting for markup tags
                    for(int d = parsingDepth; d > 0; d--) {
                        subSequence = removeClosingTag(getText()); // remove temporary closing tags in order to scan for actual tags
                        // be mindful that there are now open markup tags not accounted for in the char sequence.
                    }
                    subSequence = target.subSequence(0, subSequence.length() + 1); // now that temporary tags are cleared, add the next char // TODO: array bounds who?
                }

                if(subSequence.length() > 0) {
                    final char lastChar = subSequence.charAt(subSequence.length() - 1); // check the char we just appended to text, but has not been displayed on screen yet
                    if (isPunctuation(lastChar) && parsingDepth == 0) { // take a breath after certain punctuation, to emulate normal speaking rhythm
                        waitLonger = 36; // MAGIC NUMBERS BABY AWW YEAH TIED DIRECTLY TO THE FRAME RATE JUST LIKE WE LIKE IT
                    }
                }
                // TODO: make this actually work!
                // before we display the character we just added, we should check if any markup work should be done to it or preceding chars
                if(target.length() != subSequence.length()) { // there is still more text to be added, update will be called again
                    final char nextChar = target.charAt(subSequence.length()); // look ahead to next char to be added on upcoming loop
                    if (nextChar == '[' && parsingDepth >= 0) { // begin parsing markup, or go one layer deeper., i.e., in a multicolor [GOLD]w[RED]o[BLUE]r[GREEN]d[][][][]
                        parsingDepth++;
                        final int lengthToSkip = scanForMarkupLength(target, subSequence.length()); // find length of markup tag, from '[' to next ']'
                        if(lengthToSkip == 2) { // if the length is 2, the tag can only be '[]' closing brackets. They will be instantly appended, thus parsing depth can be reduced, and temporary tags not added.
                            parsingDepth--;
                        }
                        subSequence = "" + subSequence + target.subSequence(subSequence.length(), subSequence.length() + lengthToSkip); // append the entire markup tag to the subSequence in one go. Our text now either looks like [MARKUP]this[] or like [MARKUP]this[MARKUP]

                        for(int d = parsingDepth; d > 0; d--) {
                            subSequence = appendClosingTag(subSequence); // add temporary closing tags, so markup characters display correctly as they are added
                        }
                    } else if (nextChar != '[' && parsingDepth > 0) { // we are [MARKUP]between[] the opening and closing markup tags, dealing with the text that should actually be marked up each update
                        if (target.charAt(subSequence.length() + 1) == '[') { // found more markup
                            if(target.charAt(subSequence.length() + 2) == ']') { // found closing brackets
                                subSequence = target.subSequence(0, subSequence.length() + 2);
                            } else { // it's a new opening tag, go ahead and add it all in one go? TODO: or wait till next loop? idk rn figure it out later
                                final int lengthToSkip = scanForMarkupLength(target, subSequence.length() + 1);

                                subSequence = "" + subSequence + target.subSequence(subSequence.length(), subSequence.length() + lengthToSkip);
                                for(int d = parsingDepth; d > 0; d--) {
                                    subSequence = appendClosingTag(subSequence);
                                }
                            }
                        } else { // markup continues, add temporary tags
                            for(int d = parsingDepth; d > 0; d--) {
                                subSequence = appendClosingTag(subSequence);
                            }
                        }
                    }
                }

            setText(subSequence); // go ahead and display the most recent character we added

                // TODO: I have officially confused myself. Need to come back to this after filling out helper methods, and possibly using breakpoints

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

    private CharSequence appendClosingTag(CharSequence sequence) {
        return sequence + "[]";
    }

    private CharSequence removeClosingTag(CharSequence sequence) {
        return sequence.subSequence(0, sequence.length()-2);
    }

    private int scanForMarkupLength(CharSequence sequence, int startingIndex) {
        // startingIndex == '['
        int markupLength = 0;
        char nextChar;

       do{
            markupLength++;
            nextChar = sequence.charAt(startingIndex + markupLength);
        } while(nextChar != ']');

        return markupLength;
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

//    public boolean isActivelySpeaking() {
//        return activelySpeaking;
//    }
}
