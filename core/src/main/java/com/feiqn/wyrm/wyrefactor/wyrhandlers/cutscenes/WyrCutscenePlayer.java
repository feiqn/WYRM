package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;

public abstract class WyrCutscenePlayer extends Wyr {

    public WyrCutscenePlayer(WyrType wyrType) {
        super(wyrType);
    }

    public abstract void playCutscene();

}
