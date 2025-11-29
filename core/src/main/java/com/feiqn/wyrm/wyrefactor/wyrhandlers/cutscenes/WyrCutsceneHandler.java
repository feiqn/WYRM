package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.WYRMGame;

public abstract class WyrCutsceneHandler {

    public enum Type {
        GRID,
        WORLD,
        NARRATIVE
    }

    private final Type type;

    protected final WYRMGame root;

    protected final Array<WyrCutsceneScript> cutscenes;



    public WyrCutsceneHandler(WYRMGame root, Type type) {
        this.type = type;
        this.root = root;
        this.cutscenes = new Array<>();
    }

    public void addCutscene(WyrCutsceneScript cutscene) {
        if(!cutscenes.contains(cutscene, true)) cutscenes.add(cutscene);
    }

    public abstract void startCutscene(WyrCutsceneScript WyrCSScript);



    public Type getType() {
        return type;
    }
}
