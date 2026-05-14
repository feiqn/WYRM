package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrConditionsRegister extends WyrHandler {

    // TODO:
    //  rebrand as just WyrRegister,
    //  track actors, etc. that are used,
    //  expose simple read/write methods
    //  without centralizing any logic
    //  here that can be abstracted to a Handler.

    public WyrConditionsRegister() {}

    public WyrConditionsRegister(MetaHandler metaHandler) {
        super(metaHandler);
    }
}
