package com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.metahandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.OLD_DATA.logic.handlers.WYRMAssetHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.Interactions.WyrInteractionHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ai.WyrAIHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyRegister;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.conditions.WyrPriorityHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.input.WyrInputHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.ui.hud.WyrHUD;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.map.WyrMap;
import com.feiqn.wyrm.wyrefactor.helpers.CameraMan;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.cutscenes.WyrCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrhandlers.timekeeper.WyrTimeKeeper;
import com.feiqn.wyrm.wyrefactor.assemblies.wyrscreen.WyrScreen;

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
 */
public class MetaHandler {

    protected WyrTimeKeeper         timeKeeper = new WyrTimeKeeper();
    protected CameraMan             cameraMan = new CameraMan();
    protected WyrInteractionHandler interactionHandler;
    protected WyrInputHandler       inputHandler;
    protected WyrAIHandler computerHandler;
    protected WyrCutsceneHandler    cutsceneHandler;
    protected WyrPriorityHandler    priorityHandler;
    protected WyRegister conditionsRegister;
    protected WyrHUD                hud;
    protected WyrMap                map;

    public MetaHandler(TiledMap tiledMap) {
        // TODO: testing,
        //  code should eventually be moved to asset manager and called
        //  from handlers rather than passing in
        Skin skin = new Skin(Gdx.files.internal("ui/test/flat-skin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/test/flat-skin.atlas"));

        skin.addRegions(atlas);
        //
        inputHandler          = new WyrInputHandler();
        map                   = new WyrMap(tiledMap);
        cutsceneHandler       = new WyrCutsceneHandler(skin);
        interactionHandler    = new WyrInteractionHandler();
        computerHandler       = new WyrAIHandler();
        hud                   = new WyrHUD();
        priorityHandler       = new WyrPriorityHandler();
        conditionsRegister    = new WyRegister();

    }

    public WYRMAssetHandler   assets()   { return WYRMGame.assets(); }
    public WyrTimeKeeper      time()     { return timeKeeper; }

    public boolean isBusy() {
        return(input().isBusy()
            || interactions().isBusy()
            || ai().isBusy()
            || cutscenes().isBusy()
            || priority().isBusy()
            || map().isBusy()
            || hud().isBusy()
        );
    }

    public void standardizeParse() {
        time().incrementStateClock();
        if(cutscenes().cutsceneIsPlaying()) return;
        if(cutscenes().cutsceneInQueue()) {
            cutscenes().playFromQueue();
            return;
        }
        if(interactions().interactionQueued()) {
            interactions().parseFromQueue();
            return;
        }
        standardize();
        priority().parsePriority();
    }

    public void clearMapState() { map().standardize(); }

    public WyrScreen screen() { return (WyrScreen) WYRMGame.root().getScreen(); }

    public void standardize() {
        input().standardize();
        hud().standardize();
        map().standardize();
        camera().standardize();
    }

    public CameraMan             camera()       { return cameraMan; }
    public WyrHUD                hud()          { return hud; }
    public WyrMap                map()          { return map; }
    public WyrInputHandler       input()        { return inputHandler; }
    public WyrInteractionHandler interactions() { return interactionHandler; }
    public WyrCutsceneHandler    cutscenes()    { return cutsceneHandler; }
    public WyrPriorityHandler    priority()     { return priorityHandler; }
    public WyrAIHandler ai()           { return computerHandler; } // Not that kind of AI.
    public WyRegister register()     { return conditionsRegister; }

}
