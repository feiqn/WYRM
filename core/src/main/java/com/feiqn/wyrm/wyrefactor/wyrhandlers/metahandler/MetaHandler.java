package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.WyrCampaignHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.WyrHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrMap;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public abstract class MetaHandler extends WyrHandler {

    protected MetaHandler(WyrType type) {
        super(type);
    }
    protected MetaHandler(WYRMGame root, WyrType wyrType) {
        super(root, wyrType);

    }

    public WYRMAssetHandler assets() { return WYRMGame.assets(); }
    public WyrCampaignHandler campaign() { return WYRMGame.campaign(); }
    public abstract WyrScreen screen();
    public abstract CameraMan camera();
    public abstract WyrHUD hud();
    public abstract WyrMap map();
    public abstract WyrInputHandler inputs();
    public abstract WyrActorHandler actors();

//    public WyrMap map() { return null; }
//    public WyrCombatHandler combat() { return  null; }
//    public WyrActorHandler actors() { return null; }
//    public WyrConditionsHandler conditions() { return null; }

}
