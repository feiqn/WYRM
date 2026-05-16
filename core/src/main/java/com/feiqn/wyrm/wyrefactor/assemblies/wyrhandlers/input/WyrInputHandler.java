package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.InputMode.*;

public class WyrInputHandler extends WyrHandler {

    // Might be superfluous, leaving in as space for
    // higher level things such as keyboard / controller / touch
    // handling, etc.

    private InputMode inputMode    = STANDARD;
    private WyrActor  focusedActor = null;
    private Actor     focusedMenu  = null;

//    protected Array<Input.Keys> pressedKeys = new Array<>();

    public WyrInputHandler() {}

    public WyrInputHandler(MetaHandler metaHandler) {
        super(metaHandler);
    }

//    public void keyPressed(Input.Keys keyCode) {
//        if(!pressedKeys.contains(keyCode, true)) pressedKeys.add(keyCode);
//    }
//
//    public void keyReleased(Input.Keys keyCode) {
//        if(pressedKeys.contains(keyCode, true)) pressedKeys.removeValue(keyCode, true);
//    }
//
//    public boolean keyIsPressed(Input.Keys keyCode) {
//        return pressedKeys.contains(keyCode, true);
//    }

    public void setInputMode(InputMode mode) {
        inputMode = mode;
        if(mode != MENU_FOCUSED)  focusedMenu = null;
        if(mode != ACTOR_FOCUSED) focusedActor = null;
    }
    public void focusMenu(Actor focusedMenu) {
        this.focusedMenu = focusedMenu;
        setInputMode(MENU_FOCUSED);
    }
    public void focusActor(RPGridUnit unit) {
        this.focusedActor = unit;
        inputMode = ACTOR_FOCUSED;
    }
    public void standardize() {
        clearFocus(true);
    }
    public void clearFocus(boolean standardizeInput) {
        focusedMenu = null;
        focusedActor = null;
        if(standardizeInput) inputMode = STANDARD;
    }
    public void lock() {
        inputMode = LOCKED;
    }

    public InputMode getInputMode() { return inputMode; }


}
