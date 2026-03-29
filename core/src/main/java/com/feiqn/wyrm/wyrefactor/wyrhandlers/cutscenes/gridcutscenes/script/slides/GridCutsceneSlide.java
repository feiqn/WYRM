package com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.script.slides;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.cutscene.dialog.OLD_CutsceneFrame;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.components.slides.WyrCutsceneSlide;

public final class GridCutsceneSlide extends WyrCutsceneSlide<GridActor> {

    public GridCutsceneSlide() {}

    @Override
    public WyrType getWyrType() {
        return WyrType.GRIDWORLD;
    }

}
