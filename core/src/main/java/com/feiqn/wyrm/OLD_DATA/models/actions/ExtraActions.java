package com.feiqn.wyrm.OLD_DATA.models.actions;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ExtraActions extends Actions {

    static public PauseAction pauseAction() {
        PauseAction action = action(PauseAction.class);
//        action.setDuration(duration);
        return action;
    }

}
