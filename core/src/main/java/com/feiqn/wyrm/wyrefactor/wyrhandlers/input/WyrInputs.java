package com.feiqn.wyrm.wyrefactor.wyrhandlers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public final class WyrInputs /*implements Input*/ {

    private WyrInputs() {}

    private static Array<Input.Keys> pressedKeys;


    public static void keyPressed(Input.Keys key) {
        if(!pressedKeys.contains(key, true)) pressedKeys.add(key);
    }

    public static void keyReleased(Input.Keys key) {
        if(pressedKeys.contains(key, true)) pressedKeys.removeValue(key, true);
    }

    public static boolean KEY_IS_PRESSED(Input.Keys key) {
        return pressedKeys.contains(key, true);
    }

}
