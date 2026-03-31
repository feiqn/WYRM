package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.feiqn.wyrm.wyrefactor.helpers.Wyr;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.script.WyrCutsceneScript;

public class WyrCutscenePlayer<Actor extends WyrActor, Script extends WyrCutsceneScript<Actor>> implements Wyr {

    protected final Stage gameStage;

    public WyrCutscenePlayer(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public void playCutscene(Script script) {
        // parse script and act out upon gameStage
    }

}
