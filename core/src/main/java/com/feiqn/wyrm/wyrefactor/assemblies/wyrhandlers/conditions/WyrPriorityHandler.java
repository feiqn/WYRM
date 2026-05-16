package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions;

import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;

public class WyrPriorityHandler extends WyrHandler {

    public WyrPriorityHandler() {}

    public WyrPriorityHandler(MetaHandler metaHandler) {
        super(metaHandler);
    }

    public boolean parsePriority() {
        // return false if priority validation was aborted for any reason.
        if(h().cutscenes().cutsceneIsPlaying()) {
            h().cutscenes().playNext();
            return false;
        }
        return true;
    }

}
