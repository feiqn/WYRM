package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp.GridComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.gridcombat.GridCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions.GridConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.GridCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.WyrGridScreen;

public class GridMetaHandler extends MetaHandler {

    private final GridInputHandler inputHandler;
    private final GridCombatHandler combatHandler;
    private final GridComputerPlayerHandler computerPlayerHandler;
    private final GridActorHandler actorHandler;
    private final GridCutsceneHandler cutsceneHandler;
    private final GridConditionsHandler conditionsHandler;
    private final WyrGridScreen activeScreen;

    public GridMetaHandler(WYRMGame root, WyrGridScreen gridScreen) {
        super(root, WyrType.GRIDWORLD);
        inputHandler = new GridInputHandler(root);
        combatHandler = new GridCombatHandler(root);
        computerPlayerHandler = new GridComputerPlayerHandler(root);
        actorHandler = new GridActorHandler(root);
        cutsceneHandler = new GridCutsceneHandler(root);
        conditionsHandler = new GridConditionsHandler(root);
        activeScreen = gridScreen;
    }

    @Override
    public WyrGridScreen screen() {
        return activeScreen;
    }

    @Override
    public CameraMan camera() {
        return activeScreen.getCameraMan();
    }

    @Override
    public GridInputHandler inputs() {
        return inputHandler;
    }

    @Override
    public GridActorHandler actors() {
        return actorHandler;
    }
}
