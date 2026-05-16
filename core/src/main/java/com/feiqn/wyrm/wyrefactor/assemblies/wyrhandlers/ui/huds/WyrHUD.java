package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.components.script.WyrCutscene;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr;

public class WyrHUD extends Table implements Wyr {

    protected boolean isBusy = false;
    protected boolean uiHidden = true;
    protected Image   curtain;

    public WyrHUD() {
        this.setFillParent(true);
        this.top();
        curtain = new Image(WYRMGame.assets().solidBlueTexture);
        curtain.setColor(Color.BLACK);
        this.add(curtain).fill();
//        fadeCurtainOut();
    }

    public void standardize() {}

    protected void buildStandard() {}

    public void buildForCutscene(Table playerTable) {}

    public void buildForFullscreenCutscene(Image background, Table playerTable) {
        this.clearChildren();
        final Stack fullscreenStack = new Stack(background);
        fullscreenStack.setFillParent(true);
        fullscreenStack.add(playerTable);
        this.add(fullscreenStack);
    }

    public void fadeCurtainOut() {
        isBusy = true;
        curtain.addAction(Actions.sequence(
            Actions.fadeOut(.5f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    uiHidden = false;
                    buildStandard();
                }
            })
        ));
    }

    public void fadeCurtainIn() {
        isBusy = true;

        this.clearChildren();
        curtain.setColor(0,0,0, 0);
        curtain.addAction(Actions.sequence(
            Actions.fadeIn(.5f),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    isBusy = false;
                    uiHidden = true;
                    // TODO: progress somehow
                }
            })
        ));

    }

    public boolean isBusy() { return isBusy; }
}
