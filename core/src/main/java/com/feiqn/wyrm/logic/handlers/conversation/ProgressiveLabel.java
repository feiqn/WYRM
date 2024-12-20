package com.feiqn.wyrm.logic.handlers.conversation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

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

//    private enum DisplaySpeed {
//        SUPER_SLOW,
//        SLOW,
//        STANDARD,
//        FAST,
//        SUPER_FAST
//    }

    private CharSequence target;
    private float lastClockTime;
    private float displaySpeed;
    private float clock;
    private int snapToIndex;
    private int waitLonger;
    private int punctuationPause;
    private boolean activelySpeaking;
    private boolean shouldBlink;
//    private boolean blinking;
//    private Label dummyLabel;
    private StringBuilder stringBuilder;
//    private DisplaySpeed displaySpeed;

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
        displaySpeed = 0.001f;
        waitLonger = 0;
        shouldBlink = false;
//        blinking = false;
//        dummyLabel = new Label("", getStyle());
//        dummyLabel.setFontScale(getFontScaleX(), getFontScaleY());
        stringBuilder = new StringBuilder();
    }

    @Override
    public void setFontScale(float x, float y) {
        super.setFontScale(x, y);
//        dummyLabel.setFontScale(x, y);
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

    public void progressiveDisplay(CharSequence sequence) {
        progressiveDisplay(sequence, displaySpeed, snapToIndex);
    }

    public void progressiveDisplay(CharSequence sequence, float displaySpeed, int snapToIndex) {
        this.snapToIndex = snapToIndex;
        progressiveDisplay(sequence, displaySpeed);
    }

    public void progressiveDisplay(CharSequence sequence, float displaySpeed) {

        setText(sequence);

        if(displaySpeed > 0) { // A Display speed of 0 will display the entire text in one frame, rather than progressively. This can be used for dynamic affect when scripting conversations.
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
        final float difference = Math.abs(clock - lastClockTime);
        lastClockTime = clock;

        if(difference >= displaySpeed) { // long enough has passed to add a new char
            if(waitLonger <= 0) { // not paused for punctuation, etc.
//                stringBuilder = new StringBuilder(getText());
                stringBuilder.clear();
                stringBuilder.append(getText());
//                if(blinking && !stringBuilder.isEmpty()) {
//                    if(stringBuilder.charAt(stringBuilder.length-1) == '|'){
//                        stringBuilder.deleteCharAt(stringBuilder.length()-1);
//                    }
//                    blinking = false;
//                }

                boolean endOfSequence = target.length() == stringBuilder.length();

                while(!endOfSequence && target.charAt(stringBuilder.length()) == '[') { // check for additional [MARKUP][TAGS][JUST]TO[TRIP[][][][][][][][][][][][][][][]YOU[][][[UP[
                    final int markupLength = scanMarkupLength(target, stringBuilder.length()); // get the length of the markup tag

//                    stringBuilder = new StringBuilder(target.subSequence(0, stringBuilder.length() + markupLength)); // then append the entire markup tag in one go, so it is never partially displayed incorrectly as plaintext
//                    stringBuilder = builders.obtain().append(target.subSequence(0, stringBuilder.length() + markupLength));

                    stringBuilder.append(target.subSequence(stringBuilder.length, stringBuilder.length + markupLength));

                    endOfSequence = target.length() == stringBuilder.length();
                }
                if(!endOfSequence && target.charAt(stringBuilder.length()) == '\\') {
//                    stringBuilder = new StringBuilder(target.subSequence(0, stringBuilder.length() + 2)); // automatically grab escaped characters, i.e., \n
                    stringBuilder.append(target.subSequence(stringBuilder.length, stringBuilder.length() + 2));
                }
                if(!endOfSequence) {
                    if(target.charAt(stringBuilder.length()) == ' ') {
                        final int wordLength = scanWordLength(target, stringBuilder.length()+1);
//                        dummyLabel.setText(new StringBuilder(target.subSequence(0, stringBuilder.length() + wordLength)));
                        final Label dummyLabel = new Label(target.subSequence(0, stringBuilder.length() + wordLength), getStyle());
                        final float dummyWidth = dummyLabel.getPrefWidth();

                        if(dummyWidth > getWidth()) {
                            // TODO: StringBuilder has an insert method that is probably better than doing this
                            target = new StringBuilder("" + target.subSequence(0, stringBuilder.length()) + '\n' + target.subSequence(stringBuilder.length()+1, target.length()));
//                            target = new StringBuilder(target).insert(stringBuilder.length + 1, '\n');
//                            Gdx.app.log("update", "" + target);
//                            target = new StringBuilder("" + target.subSequence(0, stringBuilder.length()) + ('\n') + target.subSequence(stringBuilder.length() + 1, target.length()));
                        }
//                        dummyLabel.setText("");
                    }

//                    stringBuilder = new StringBuilder(target.subSequence(0, stringBuilder.length() + 1)); // TODO: Not sure about appropriate use of repeatedly calling constructor again rather than update methods
                    stringBuilder.append(target.charAt(stringBuilder.length()));
                }

                final char lastChar = stringBuilder.charAt(stringBuilder.length() - 1); // Check the char we just appended to text, but has not been displayed on screen yet.
                if(isPunctuation(lastChar)) { // Take a breath after certain punctuation, to emulate normal speaking rhythm.
                    waitLonger = punctuationPause; // This represents the amount of calls to update() which will be internally ignored, after accounting for deltaTime. Thus, the time it will wait = (waitLonger * displaySpeed)
                }

//                if(shouldBlink) {
//                        stringBuilder.append('|');
//                        blinking = true;
//                }

                setText(stringBuilder.toString());

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
            nextChar = sequence.charAt(startingIndex + wordLength - 1);
        } while(nextChar != ' ' && sequence.length() > startingIndex + wordLength);

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

    // TODO: animal crossing style infinite scrolling. maxLineCount, indexOfLineBreak

    // TODO: wrap-on-dashes, (if=' '||'-')

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
        return activelySpeaking || shouldBlink;
    }

    public void setPunctuationPause(int pauseLength) {
        punctuationPause = pauseLength;
    }

    public boolean isActivelySpeaking() {
        return activelySpeaking;
    }
}
