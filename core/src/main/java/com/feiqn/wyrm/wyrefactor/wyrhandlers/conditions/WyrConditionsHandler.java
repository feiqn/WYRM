package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;

public abstract class WyrConditionsHandler<Register extends WyrConditionRegister> extends WyrHandler {

    protected final Register register;


    protected WyrConditionsHandler(Register register) {
        this.register = register;
    }


}
