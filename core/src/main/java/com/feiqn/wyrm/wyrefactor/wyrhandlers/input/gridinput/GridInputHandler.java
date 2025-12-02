package com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;

public class GridInputHandler extends WyrInputHandler {

    public enum InputMode {
        STANDARD,
        UNIT_SELECTED,
        MENU_FOCUSED,
        LOCKED,
        CUTSCENE,
    }

    public enum MovementControl {
        FREE_MOVE,
        COMBAT
    }

    protected InputMode inputMode;

    protected MovementControl movementControl;

    public GridInputHandler(WYRMGame root) {
        super(root);
        inputMode = InputMode.STANDARD;
        movementControl = MovementControl.COMBAT;
    }

    public void setInputMode(InputMode mode) { inputMode = mode; }
    public void setMovementControl(MovementControl movementControl) { this.movementControl = movementControl; }

    public InputMode getInputMode() { return inputMode; }


    public static class GridListeners {

        public

    }
}
