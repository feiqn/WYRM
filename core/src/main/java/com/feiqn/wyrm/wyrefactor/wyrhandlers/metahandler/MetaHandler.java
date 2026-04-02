package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.handler.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.timekeeper.WyrTimeKeeper;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrMap;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public abstract class MetaHandler<
         ActorHandler      extends WyrActorHandler,
         InputHandler      extends WyrInputHandler,
         HUD               extends WyrHUD,
         Map               extends WyrMap,
         CombatHandler     extends WyrCombatHandler<?>,
         ComputerHandler   extends WyrComputerPlayerHandler<?, ?>,
         CutsceneHandler   extends WyrCutsceneHandler<?,?,?>,
         ConditionsHandler extends WyrConditionsHandler,
         Screen            extends WyrScreen<?>
            > extends WyrHandler {

    //  ^^^
    // I just learned how to do this.
    // It probably does not represent best practices.
    // I'm mad with power and LLMs are dumb.
    // Mar '26

    protected WyrTimeKeeper     timeKeeper = new WyrTimeKeeper();
    protected CameraMan         cameraMan;
    protected ActorHandler      actorHandler;
    protected InputHandler      inputHandler;
    protected CombatHandler     combatHandler;
    protected ComputerHandler   comHandler;
    protected CutsceneHandler   cutsceneHandler;
    protected ConditionsHandler conditionsHandler;
    protected HUD               hud;
    protected Map               map;

    protected MetaHandler() {}

    public WYRMAssetHandler   assets()   { return WYRMGame.assets(); }
    public WyrCampaignHandler campaign() { return WYRMGame.campaign(); }
    public WyrTimeKeeper      time()     { return timeKeeper; }

    public abstract boolean isBusy();
    public abstract Screen  screen();

    public CameraMan         camera()     { return cameraMan; }
    public HUD               hud()        { return hud; }
    public Map               map()        { return map; }
    public InputHandler      input()      { return inputHandler; }
    public ActorHandler      actors()     { return actorHandler; }
    public CutsceneHandler   cutscenes()  { return cutsceneHandler; }
    public ConditionsHandler conditions() { return conditionsHandler; }
    public CombatHandler     combat()     { return combatHandler; }
    public ComputerHandler   ai()         { return comHandler; } // Not that kind of AI.

}
