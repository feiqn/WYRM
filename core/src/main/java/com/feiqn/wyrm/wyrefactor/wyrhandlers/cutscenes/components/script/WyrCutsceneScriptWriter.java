package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrame;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.CharacterExpression;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.SpeakerPosition;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.WyrCutsceneSlide;

import static com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.SpeakerPosition.*;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.SpeakerPosition.CENTER;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.SpeakerPosition.RIGHT;
import static com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.SpeakerPosition.RIGHT_OF_CENTER;

public class WyrCutsceneScriptWriter implements Wyr {


    protected void set(CharacterExpression expression, String txt) {
        set(expression, txt, LEFT);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position) {
        set(expression, txt, "", position, false, false);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft) {
        set(expression, txt, "", position, facingLeft, false);
    }
    protected void set(CharacterExpression expression, String txt, SpeakerPosition position, boolean facingLeft, boolean autoNext) {
        set(expression, txt, "", position, facingLeft, autoNext);
    }
    protected void set(CharacterExpression expression, String txt, String name, SpeakerPosition pos, boolean facingLeft, boolean autoAutoPlay) {
        final WyrCutsceneSlide frame = new WyrCutsceneSlide() {
        };

        frame.setText(txt);
        frame.setFocusedPosition(pos);
        frame.setFocusedExpression(expression);
        frame.setFacingLeft(facingLeft);
        frame.setAutoplayNext(autoAutoPlay);

        slideshow.add(frame);
    }
    protected void setMultiple(SpeakerPosition focusedPosition, SpeakerPosition... positions) {
        // TODO: this ^ won't work. hashmap?
    }
    protected void setAll(SpeakerPosition focusedPosition, String txt, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        setAll(focusedPosition, txt, "", farLeft, left, leftOfCenter, center, rightOfCenter, right, farRight);
    }
    protected void setAll(SpeakerPosition focusedPosition, String txt, String name, CharacterExpression farLeft, CharacterExpression left, CharacterExpression leftOfCenter, CharacterExpression center, CharacterExpression rightOfCenter, CharacterExpression right, CharacterExpression farRight) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        frame.setComplex(true);
        frame.setFocusedPosition(focusedPosition);
        frame.setText(txt);
        frame.setFocusedName(name);

        switch(focusedPosition) {
            case FAR_LEFT:
                frame.setFocusedExpression(farLeft);
                break;
            case LEFT:
                frame.setFocusedExpression(left);
                break;
            case LEFT_OF_CENTER:
                frame.setFocusedExpression(leftOfCenter);
                break;
            case CENTER:
                frame.setFocusedExpression(center);
                break;
            case RIGHT_OF_CENTER:
                frame.setFocusedExpression(rightOfCenter);
                break;
            case RIGHT:
                frame.setFocusedExpression(right);
                break;
            case FAR_RIGHT:
                frame.setFocusedExpression(farRight);
                break;
        }

        frame.setExpressionAtPosition(farLeft,       FAR_LEFT);
        frame.setExpressionAtPosition(left,          LEFT);
        frame.setExpressionAtPosition(leftOfCenter,  LEFT_OF_CENTER);
        frame.setExpressionAtPosition(center,        CENTER);
        frame.setExpressionAtPosition(rightOfCenter, RIGHT_OF_CENTER);
        frame.setExpressionAtPosition(right,         RIGHT);
        frame.setExpressionAtPosition(rightOfCenter, RIGHT_OF_CENTER);

        slideshow.add(frame);
    }
}
