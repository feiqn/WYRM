package com.feiqn.wyrm.wyrefactor.wyrhandlers;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.handlers.ai.AIHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.CampaignHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.campaign.army.ArmyHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.CutsceneHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.ConditionsHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.logic.handlers.ui.WyrHUD;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public class MetaHandler extends WyrHandler {

    // MetaHandler is persistent in root Game.

    private static CutsceneHandler cutsceneHandler;
    private static CampaignHandler campaignHandler;
    private static WYRMAssetHandler assetHandler;
    private static ConditionsHandler conditionsHandler;
    private static AIHandler aiHandler;
    private static WyrHUD uiHandler;
    private static WyrMap gridMapHandler;
    private static CombatHandler gridCombatHandler;

    // grid map
    // - combat
    // - battle conditions
    // - computer player
    // - actors
    // -- mapObjects
    // -- units
    // --- teams
    // world map
    // ui
    // - menus
    // cutscenes
    //
    // ====
    // Campaign flags
    // - Army
    // Assets

    public MetaHandler(WYRMGame root) {
        super(root);

        cutsceneHandler = new CutsceneHandler(root);
//        campaignHandler = new CampaignHandler(root);

    }

    public void buildForScreen(WyrScreen screen) {

    }

    private void clearAll() {

    }


    // TODO: add null state checks?
    public WYRMAssetHandler assets() { return root.assets(); }
    public CutsceneHandler cutscenes() { return cutsceneHandler; }
    public CampaignHandler campaign() { return campaignHandler; }
    public ArmyHandler army() { return campaignHandler.getArmy(); }


}
