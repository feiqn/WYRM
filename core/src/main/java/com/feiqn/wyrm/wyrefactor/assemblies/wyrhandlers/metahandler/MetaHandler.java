package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.wyrefactor.helpers.CameraMan;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteractionHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.WyrComputerHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrConditionsRegister;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrPriorityHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.timekeeper.WyrTimeKeeper;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.WyrMap;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

/**
 * This intends to compartmentalize the basic functions of any given game
 * into a set of assemblies which can be built out at scale and iterated upon,
 * while maintaining a clear chain of communication and authority.
 * <br><br>
 * All WyrModules are built on a system of implicit personal responsibility.
 * This takes a wider philosophical meaning throughout the project. Here, it
 * means that each handler will individually manage both flagging other systems
 * not to run while it is busy(), and signaling a standard universal check once
 * it is no longer holding priority.
 * <br>
 * Similarly, it dictates that each handlers' exposed methods must complete all
 * necessary aspects of their job, including setup and cleanup tasks that should
 * trigger before or after a task.
 */
public class MetaHandler extends WyrHandler {

    protected WyrTimeKeeper         timeKeeper = new WyrTimeKeeper();
    protected CameraMan             cameraMan = new CameraMan();
    protected WyrInteractionHandler interactionHandler = new WyrInteractionHandler();
    protected WyrInputHandler       inputHandler = new WyrInputHandler();
    protected WyrCombatHandler      combatHandler = new WyrCombatHandler();
    protected WyrComputerHandler    computerHandler = new WyrComputerHandler();
    protected WyrCutsceneHandler    cutsceneHandler = new WyrCutsceneHandler();
    protected WyrPriorityHandler    priorityHandler = new WyrPriorityHandler();
    protected WyrConditionsRegister conditionsRegister = new WyrConditionsRegister();
    protected WyrHUD                hud = new WyrHUD();
    protected WyrMap                map = new WyrMap();

    public MetaHandler() {}

    public WYRMAssetHandler   assets()   { return WYRMGame.assets(); }
    public WyrCampaignHandler campaign() { return WYRMGame.campaign(); }
    public WyrTimeKeeper      time()     { return timeKeeper; }

    @Override
    public boolean isBusy() {
        return(input().isBusy()
            || interactions().isBusy()
            || combat().isBusy()
            || ai().isBusy()
            || cutscenes().isBusy()
            || priority().isBusy()
            || map().isBusy()
            || hud().isBusy()
        );
    }

    public WyrScreen  screen() {
        return WYRMGame.activeScreen();
    }

    public void standardize() {
        hud().standardize();
        input().standardize();
        map().standardize();
        camera().standardize();
    }

    public CameraMan             camera()       { return cameraMan; }
    public WyrHUD                hud()          { return hud; }
    public WyrMap                map()          { return map; }
    public WyrInputHandler       input()        { return inputHandler; }
    public WyrInteractionHandler interactions() { return interactionHandler; }
    public WyrCutsceneHandler    cutscenes()    { return cutsceneHandler; }
    public WyrPriorityHandler    priority()     { return priorityHandler; }
    public WyrCombatHandler      combat()       { return combatHandler; }
    public WyrComputerHandler    ai()           { return computerHandler; } // Not that kind of AI.
    public WyrConditionsRegister register()     { return conditionsRegister; }

}
