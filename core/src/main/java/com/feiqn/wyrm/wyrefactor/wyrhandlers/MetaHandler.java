package com.feiqn.wyrm.wyrefactor.wyrhandlers;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.wyrefactor.wyrscreen.WyrScreen;

public class MetaHandler extends WyrHandler {

    // MetaHandler is persistent in root Game.

    // grid map
    // - combat
    // - battle conditions
    // - computer player
    // - actors
    // -- mapObjects
    // -- units
    // --- teams
    // overworld map
    // ui
    // - menus
    // cutscenes
    //
    // ====
    // Campaign flags
    // - Army
    // Assets

    public MetaHandler(WYRMGame root) {
        super(root, WyrType.AGNOSTIC);


    }

    public void buildForScreen(WyrScreen screen) {

    }

    private void clearAll() {

    }


    // TODO: add null state checks?
    public WYRMAssetHandler assets() { return root().assets(); }
//    public CutsceneHandler cutscenes() { return cutsceneHandler; }
//    public CampaignHandler campaign() { return campaignHandler; }
//    public ArmyHandler army() { return campaignHandler.getArmy(); }


}
