package com.feiqn.wyrm.wyrefactor.handlers;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.logic.handlers.ai.AIHandler;
import com.feiqn.wyrm.logic.handlers.campaign.CampaignHandler;
import com.feiqn.wyrm.logic.handlers.cutscene.CutsceneHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.ConditionsHandler;
import com.feiqn.wyrm.logic.handlers.gameplay.combat.CombatHandler;
import com.feiqn.wyrm.logic.handlers.ui.WyrHUD;
import com.feiqn.wyrm.models.mapdata.WyrMap;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public class MetaHandler {

    // MetaHandler is persistent in root Game.

    private static CutsceneHandler cutsceneHandler;
    private static CampaignHandler campaignHandler;
    private static CombatHandler combatHandler;
    private static WYRMAssetHandler assetHandler;
    private static ConditionsHandler conditionsHandler;
    private static AIHandler aiHandler;
    private static WyrHUD uiHandler;
    private static WyrMap logicalMapHandler;

    private final WYRMGame root;



    // combat
    // map
    // -actors
    // -- units
    // -- mapObjects
    // ui
    // - menus
    // ai
    // cutscene
    // battle conditions
    //
    // ====
    // Campaign flags
    // Assets

    public MetaHandler(WYRMGame root) {
        this.root = root;

    }

    public void buildForScreen(WyrScreen screen) {

    }

}
