package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrame;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.OLD_CharacterExpression;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.slides.WyrCutsceneShot;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.HorizontalPosition.*;

public class WyrCutsceneScriptWriter implements Wyr {


    protected void set(OLD_CharacterExpression expression, String txt) {
        set(expression, txt, LEFT);
    }
    protected void set(OLD_CharacterExpression expression, String txt, HorizontalPosition horizontalPosition) {
        set(expression, txt, "", horizontalPosition, false, false);
    }
    protected void set(OLD_CharacterExpression expression, String txt, HorizontalPosition horizontalPosition, boolean facingLeft) {
        set(expression, txt, "", horizontalPosition, facingLeft, false);
    }
    protected void set(OLD_CharacterExpression expression, String txt, HorizontalPosition horizontalPosition, boolean facingLeft, boolean autoNext) {
        set(expression, txt, "", horizontalPosition, facingLeft, autoNext);
    }
    protected void set(OLD_CharacterExpression expression, String txt, String name, HorizontalPosition pos, boolean facingLeft, boolean autoAutoPlay) {
        final WyrCutsceneShot frame = new WyrCutsceneShot() {
        };

//        frame.setText(txt);
//        frame.setFocusedPosition(pos);
//        frame.setFocusedExpression(expression);
//        frame.setFacingLeft(facingLeft);
//        frame.setAutoplayNext(autoAutoPlay);
//
//        slideshow.add(frame);
    }
    protected void setMultiple(HorizontalPosition focusedHorizontalPosition, HorizontalPosition... horizontalPositions) {
        // TODO: this ^ won't work. hashmap?
    }
    protected void setAll(HorizontalPosition focusedHorizontalPosition, String txt, OLD_CharacterExpression farLeft, OLD_CharacterExpression left, OLD_CharacterExpression leftOfCenter, OLD_CharacterExpression center, OLD_CharacterExpression rightOfCenter, OLD_CharacterExpression right, OLD_CharacterExpression farRight) {
        setAll(focusedHorizontalPosition, txt, "", farLeft, left, leftOfCenter, center, rightOfCenter, right, farRight);
    }
    protected void setAll(HorizontalPosition focusedHorizontalPosition, String txt, String name, OLD_CharacterExpression farLeft, OLD_CharacterExpression left, OLD_CharacterExpression leftOfCenter, OLD_CharacterExpression center, OLD_CharacterExpression rightOfCenter, OLD_CharacterExpression right, OLD_CharacterExpression farRight) {
        final OLD_CutsceneFrame frame = new OLD_CutsceneFrame();

        frame.setComplex(true);
        frame.setFocusedPosition(focusedHorizontalPosition);
        frame.setText(txt);
        frame.setFocusedName(name);

        switch(focusedHorizontalPosition) {
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

//        slideshow.add(frame);
    }
}
