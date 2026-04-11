package com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions;

import com.feiqn.wyrm.wyrefactor.helpers.Wyr;

public abstract class WyrConditionsRegister implements Wyr {

    // TODO:
    //  rebrand as just WyrRegister,
    //  track actors, etc. that are used,
    //  expose simple read/write methods
    //  without centralizing any logic
    //  here that can be abstracted to a Handler.

    public WyrConditionsRegister() {}
}
