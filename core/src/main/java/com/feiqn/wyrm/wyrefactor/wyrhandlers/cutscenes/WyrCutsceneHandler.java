package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;

public abstract class WyrCutsceneHandler {

    private final WyrType type;

    protected final WYRMGame root;

    protected final Array<WyrCutsceneScript> cutscenes;



    public WyrCutsceneHandler(WYRMGame root, WyrType type) {
        this.type = type;
        this.root = root;
        this.cutscenes = new Array<>();
    }

    public void addCutscene(WyrCutsceneScript cutscene) {
        if(!cutscenes.contains(cutscene, true)) cutscenes.add(cutscene);
    }

    public abstract void startCutscene(WyrCutsceneScript WyrCSScript);



    public WyrType getType() {
        return type;
    }
}
