package com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.mapdata.CameraMan;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp.GridComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.gridcombat.GridCombatHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.conditions.gridconditions.GridConditionsHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.cutscenes.gridcutscenes.GridCutsceneHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.input.gridinput.GridInputHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.ui.huds.grid.GridHUD;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.GridMap;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder.GridPathfinder;
import com.feiqn.wyrm.wyrefactor.wyrscreen.gridworldscreen.GridScreen;

public final class GridMetaHandler extends MetaHandler {

    private final GridInputHandler          inputHandler;
    private final GridCombatHandler         combatHandler;
    private final GridComputerPlayerHandler computerPlayerHandler;
    private final GridActorHandler          actorHandler;
    private final GridCutsceneHandler       cutsceneHandler;
    private final GridConditionsHandler     conditionsHandler;
    private final GridHUD                   hud;
    private final GridMap map;
//    private final GridScreen ags; // "Active GridScreen", or "Armadyl God Sword"
    private final GridPathfinder            pathfinder;
    // The cameraman seems fairly agnostic to
    // old vs wyr format. Watching him closely, though.
    private final CameraMan                 cameraMan;


    public GridMetaHandler(TiledMap tiledMap) {
        super(WyrType.GRIDWORLD);
        map                   = new GridMap(this, tiledMap);
        cameraMan = new CameraMan();
        actorHandler          = new GridActorHandler(this);
        inputHandler          = new GridInputHandler(this);
        combatHandler         = new GridCombatHandler(this);
        computerPlayerHandler = new GridComputerPlayerHandler(this);
        cutsceneHandler       = new GridCutsceneHandler(this);
        hud                   = new GridHUD(this);
        pathfinder            = new GridPathfinder(map);
        conditionsHandler     = new GridConditionsHandler(this);
    }

    @Override
    public GridScreen screen() {
        if(WYRMGame.activeScreen() instanceof GridScreen) {
            return (GridScreen) WYRMGame.activeScreen();
        } else {
            Gdx.app.log("GridMetaHandler", "Root active screen is not Grid.");
            throw new IllegalStateException("root screen not parseable.");
        }
    }
    @Override
    public CameraMan camera() { return cameraMan; }
    @Override
    public GridHUD hud() { return hud; }
    @Override
    public GridMap map() { return map; }
    @Override
    public GridInputHandler inputs() { return inputHandler; }
    @Override
    public GridActorHandler actors() { return actorHandler; }
    public GridPathfinder pathfinder() { return pathfinder; }
    public GridCutsceneHandler cutscenes() { return cutsceneHandler; }
    public GridConditionsHandler conditions() { return conditionsHandler; }
    public GridCombatHandler combat() { return combatHandler; }
    public GridComputerPlayerHandler ai() { return computerPlayerHandler; } // Not that kind of AI.
}
