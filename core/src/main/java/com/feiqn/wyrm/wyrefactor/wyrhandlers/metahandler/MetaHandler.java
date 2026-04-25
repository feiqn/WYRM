package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.actors.Interactions.WyrInteractionHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsRegister;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrPriorityHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.handler.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.timekeeper.WyrTimeKeeper;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrMapHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

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
 * @param <InteractionHandler>
 * @param <InputHandler>
 * @param <HUD>
 * @param <Map>
 * @param <CombatHandler>
 * @param <ComputerHandler>
 * @param <CutsceneHandler>
 * @param <PriorityHandler>
 * @param <ConditionsRegister>
 * @param <Screen>
 */
public abstract class MetaHandler<
         InteractionHandler extends WyrInteractionHandler<?>,
         InputHandler       extends WyrInputHandler,
         HUD                extends WyrHUD,
         Map                extends WyrMapHandler<?>,
         CombatHandler      extends WyrCombatHandler<?>,
         ComputerHandler    extends WyrComputerHandler<?,?,?>,
         CutsceneHandler    extends WyrCutsceneHandler<?,?,?>,
         PriorityHandler    extends WyrPriorityHandler,
         ConditionsRegister extends WyrConditionsRegister,
         Screen             extends WyrScreen<?>
            > extends WyrHandler<MetaHandler<?,?,?,?,?,?,?,?,?,?>> {

    protected WyrTimeKeeper      timeKeeper = new WyrTimeKeeper();
    protected CameraMan          cameraMan;
    protected InteractionHandler interactionHandler;
    protected InputHandler       inputHandler;
    protected CombatHandler      combatHandler;
    protected ComputerHandler    computerHandler;
    protected CutsceneHandler    cutsceneHandler;
    protected PriorityHandler    priorityHandler;
    protected ConditionsRegister conditionsRegister;
    protected HUD                hud;
    protected Map                map;

    protected MetaHandler() {}

    public WYRMAssetHandler   assets()   { return WYRMGame.assets(); }
    public WyrCampaignHandler campaign() { return WYRMGame.campaign(); }
    public WyrTimeKeeper      time()     { return timeKeeper; }

    public abstract boolean isBusy();
    public abstract Screen  screen();

    public CameraMan          camera()       { return cameraMan; }
    public HUD                hud()          { return hud; }
    public Map                map()          { return map; }
    public InputHandler       input()        { return inputHandler; }
    public InteractionHandler interactions() { return interactionHandler; }
    public CutsceneHandler    cutscenes()    { return cutsceneHandler; }
    public PriorityHandler    priority()     { return priorityHandler; }
    public CombatHandler      combat()       { return combatHandler; }
    public ComputerHandler    ai()           { return computerHandler; } // Not that kind of AI.
    public ConditionsRegister register()     { return conditionsRegister; }

}
