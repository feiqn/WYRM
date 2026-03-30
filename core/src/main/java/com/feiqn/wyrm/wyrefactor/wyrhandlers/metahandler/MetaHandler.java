package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.OLD_DATA.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.timekeeper.WyrTimeKeeper;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrMap;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public abstract class MetaHandler
        <Actor      extends WyrActorHandler,
         Input      extends WyrInputHandler,
         HUD        extends WyrHUD,
         Map        extends WyrMap,
         Combat     extends WyrCombatHandler<?>,
         Computer   extends WyrComputerPlayerHandler<?, ?>,
         Cutscenes  extends WyrCutsceneHandler<?,?,?>,
         Conditions extends WyrConditionsHandler,
         Screen   extends WyrScreen<?>
            > extends WyrHandler {

    //  ^^^
    // I just learned how to do this.
    // It probably does not represent best practices.
    // I'm mad with power and LLMs are dumb.
    // Mar '26

    protected WyrTimeKeeper timeKeeper = new WyrTimeKeeper();
    protected Actor         actorHandler;
    protected Input         inputHandler;
    protected Combat        combatHandler;
    protected HUD           hud;
    protected Map           map;
    protected Computer      comPlayer;
    protected Cutscenes     cutscenes;
    protected Conditions    conditions;

    protected MetaHandler() {}

    public WYRMAssetHandler assets() { return WYRMGame.assets(); }
    public WyrCampaignHandler campaign() { return WYRMGame.campaign(); }
    public WyrTimeKeeper time() { return timeKeeper; }

    public abstract CameraMan camera();
    public abstract Screen screen();
    public abstract HUD hud();
    public abstract Map map();
    public abstract Input input();
    public abstract Actor actors();



}
