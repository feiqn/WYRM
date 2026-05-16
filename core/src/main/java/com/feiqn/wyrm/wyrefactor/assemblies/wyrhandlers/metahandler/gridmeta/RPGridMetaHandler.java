package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.gridmeta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.helpers.CameraMan;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.grid.RPGridInteractionHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.computerplayer.grid.RPGridComputerHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.combat.gridcombat.RPGridCombatHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.grid.RPGridConditionsRegister;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.grid.RPGridPriorityHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.handler.RPGridCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.gridinput.RPGridInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.huds.gridworld.RPGridHUD;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.worlds.grid.logicalgrid.RPGridMap;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.gridworld.RPGridScreen;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.wyr.Wyr.InputMode.STANDARD;

public final class RPGridMetaHandler extends MetaHandler {

    // The cameraman seems fairly agnostic to
    // old vs wyr format. Watching him closely, though.

    public RPGridMetaHandler(TiledMap tiledMap) {
        // TODO: testing,
        //  code should eventually be moved to asset manager and called
        //  from handlers rather than passing in
        Skin skin = new Skin(Gdx.files.internal("ui/test/flat-skin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/test/flat-skin.atlas"));

        skin.addRegions(atlas);
        //
        cutsceneHandler       = new RPGridCutsceneHandler(this, skin);
        map                   = new RPGridMap(this, tiledMap);
        cameraMan             = new CameraMan();
        interactionHandler    = new RPGridInteractionHandler(this);
        inputHandler          = new RPGridInputHandler(this);
        combatHandler         = new RPGridCombatHandler(this);
        computerHandler       = new RPGridComputerHandler(this);
        hud                   = new RPGridHUD(this);
        priorityHandler       = new RPGridPriorityHandler(this);
        conditionsRegister    = new RPGridConditionsRegister(this);
    }

    public void standardizeParse() {
        hud().standardize();
        input().standardize();
        camera().standardize();
        for(RPGridActor a : register().unifiedTurnOrder()) {
            a.standardize();
        }
        map().standardize();
        priority().parsePriority();
    }
    public void clearEphemeral() {
        map().standardize();
        for(RPGridActor a : register().unifiedTurnOrder()) {
            a.clearEphemeralInteractions();
        }
    }
    public void clearAndInvalidate() {
        clearEphemeral();
        priority().parsePriority();
    }


    @Override
    public RPGridScreen screen() {
        if(WYRMGame.activeScreen().getWyrType() == WyrType.RPGRID) {
            return (RPGridScreen) WYRMGame.activeScreen();
        } else {
            Gdx.app.log("GridMetaHandler", "Root active screen is not Grid.");
            return null;
        }
    }
    @Override
    public RPGridHUD hud() {
        return (RPGridHUD) hud;
    }
    @Override
    public RPGridMap map() {
        return (RPGridMap) map;
    }
    @Override
    public RPGridInputHandler input() {
        return (RPGridInputHandler) inputHandler;
    }
    @Override
    public RPGridInteractionHandler interactions() {
        return (RPGridInteractionHandler) interactionHandler;
    }
    @Override
    public RPGridCutsceneHandler cutscenes() {
        return (RPGridCutsceneHandler) cutsceneHandler;
    }
    @Override
    public RPGridPriorityHandler priority() {
        return (RPGridPriorityHandler) priorityHandler;
    }
    @Override
    public RPGridCombatHandler combat() {
        return (RPGridCombatHandler) combatHandler;
    }
    @Override
    public RPGridComputerHandler ai() {
        return (RPGridComputerHandler) computerHandler;
    }
    @Override
    public RPGridConditionsRegister register() {
        return (RPGridConditionsRegister) conditionsRegister;
    }
    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRID;
    }
}
