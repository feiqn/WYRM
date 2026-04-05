package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutscene;

public class WyrCutscenePlayer<Script extends WyrCutscene<?>> implements Wyr {

    protected Stage gameStage;

    public WyrCutscenePlayer() {}

    public void playCutscene(Script script) {
        // parse script and act out upon gameStage
    }

}
