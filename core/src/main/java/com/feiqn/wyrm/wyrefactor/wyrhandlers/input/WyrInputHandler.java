package com.feiqn.wyrm.wyrefactor.wyrhandlers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrInputHandler extends WyrHandler {

    // Might be superfluous, leaving in as space for
    // higher level things such as keyboard / controller / touch
    // handling, etc.

    protected Array<Input.Keys> pressedKeys = new Array<>();

    protected WyrInputHandler(WyrType type) {
        super(type);
    }

    public void keyPressed(Input.Keys keyCode) {
        if(!pressedKeys.contains(keyCode, true)) pressedKeys.add(keyCode);
    }

    public void keyReleased(Input.Keys keyCode) {
        if(pressedKeys.contains(keyCode, true)) pressedKeys.removeValue(keyCode, true);
    }

    public boolean keyIsPressed(Input.Keys keyCode) {
        return pressedKeys.contains(keyCode, true);
    }


}
