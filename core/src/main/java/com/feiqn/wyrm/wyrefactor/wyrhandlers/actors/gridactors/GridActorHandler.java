package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActorHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions.GridMoveInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.GridPath;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.tiles.GridTile;

public class GridActorHandler extends WyrActorHandler {

    // props
    // units

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridActorHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
    }

    public void startCombat(GridUnit attacker, GridUnit defender) {
        // TODO:
        //  Talk to CombatHandler to initiate combat
    }

    public void placeActor(GridActor actor, GridTile tile) {
        this.placeActor(actor, tile.getCoordinates());
    }
    public void placeActor(GridActor actor, Vector2 coordinates) {
        this.placeActor(actor, (int)coordinates.x, (int)coordinates.y);
    }
    public void placeActor(GridActor actor, int x, int y) {
        switch(actor.getActorType()) {
            case UNIT:
                h.map().tileAt(x, y).occupy((GridUnit) actor);
                // TODO: check area cutscene trigger
                break;

            case PROP:
                h.map().tileAt(x,y).setProp((GridProp) actor);
                break;
        }
        actor.setPosByGrid(x, y);
//        actor.setPosition((x + .5f) - (actor.getWidth() * .5f), y);

    }

    public void followPath(GridActor actor, GridPath path) {
        Gdx.app.log("Actors()", "You'd like me to move, yes.");
        // TODO
        //  - move
        //  - parse player / cp
        //  - fap / cp action
    }

    public void parseInteractable(GridInteraction interactable) {
        switch(interactable.getInteractType()) {
            case MOVE:
                followPath(interactable.getParent(), ((GridMoveInteraction)interactable).getPath());
                break;
            case ATTACK:

            default:
                break;
        }
        for(GridTile tile : h.map().getAllTiles()) {
            tile.clearInteractables();
        }
    }

}
