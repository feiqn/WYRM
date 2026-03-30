package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat.GridCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp.GridComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions.GridConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.GridCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.timekeeper.WyrTimeKeeper;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.gridworld.GridHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrMap;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.GridMap;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public abstract class MetaHandler<
         Actor      extends WyrActorHandler,
         Input      extends WyrInputHandler,
         HUD        extends WyrHUD,
         Map        extends WyrMap,
         Combat     extends WyrCombatHandler<?>,
         Computer   extends WyrComputerPlayerHandler<?, ?>,
         Cutscenes  extends WyrCutsceneHandler<?,?,?>,
         Conditions extends WyrConditionsHandler,
         Screen     extends WyrScreen<?>
            > extends WyrHandler {

    //  ^^^
    // I just learned how to do this.
    // It probably does not represent best practices.
    // I'm mad with power and LLMs are dumb.
    // Mar '26

    protected WyrTimeKeeper timeKeeper = new WyrTimeKeeper();
    protected CameraMan     cameraMan;
    protected Actor         actorHandler;
    protected Input         inputHandler;
    protected Combat        combatHandler;
    protected Computer      comHandler;
    protected Cutscenes     cutsceneHandler;
    protected Conditions    conditionsHandler;
    protected HUD           hud;
    protected Map           map;

    protected MetaHandler() {}

    public WYRMAssetHandler   assets()   { return WYRMGame.assets(); }
    public WyrCampaignHandler campaign() { return WYRMGame.campaign(); }
    public WyrTimeKeeper      time()     { return timeKeeper; }

    public abstract boolean isBusy();
    public abstract Screen  screen();

    public CameraMan  camera()     { return cameraMan; }
    public HUD        hud()        { return hud; }
    public Map        map()        { return map; }
    public Input      input()      { return inputHandler; }
    public Actor      actors()     { return actorHandler; }
    public Cutscenes  cutscenes()  { return cutsceneHandler; }
    public Conditions conditions() { return conditionsHandler; }
    public Combat     combat()     { return combatHandler; }
    public Computer   ai()         { return comHandler; } // Not that kind of AI.


}
