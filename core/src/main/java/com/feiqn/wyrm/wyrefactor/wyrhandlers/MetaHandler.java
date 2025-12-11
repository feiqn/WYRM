package com.feiqn.wyrm.wyrefactor.wyrhandlers;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.WyrConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.combat.WyrCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrMap;
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

    protected WyrType build = WyrType.AGNOSTIC;

    public MetaHandler(WYRMGame root) {
        super(root, WyrType.AGNOSTIC);

    }

    public void buildForScreen(WyrScreen screen) {
        this.build = screen.getType();

    }

    private void clearAll() {

    }


    public WYRMAssetHandler assets() { return root().assets(); }
    public WyrScreen screen() { return root().getActiveScreen(); }
    public CameraMan camera() { return null; }
    public WyrMap map() { return null; }
    public WyrCombatHandler combat() { return  null; }
    public WyrInputHandler inputs() { return null; }
    public WyrActorHandler actors() { return null; }
    public WyrConditionsHandler conditions() { return null; }

}
