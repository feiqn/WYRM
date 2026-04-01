package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.interactions.prefabinteractions.GridWaitInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.gridworldmap.logicalgrid.pathing.pathfinder.GridPathfinder;

public final class GridComputerPlayer extends WyrComputerPlayer<GridUnit, GridInteraction, GridMetaHandler> {

    public GridComputerPlayer(GridMetaHandler metaHandler) {
        h = metaHandler;
    }

    @Override
    public GridInteraction preferredAction(GridUnit actor) {
        // "deliberateBestOption" in old data
        switch(actor.personality().personalityType()) {
            case AGGRESSIVE:
                return buildAggressiveAction(actor);
            case ESCAPE: // TODO: etc...
            case RECKLESS:
            case FLANKING:
            case DEFENSIVE:
            case PATROLLING:
            case PROTECTIVE:
            case LOS_AGGRO:
            case LOS_FLEE:
            case TARGET_UNIT:
            case TARGET_OBJECT:
            case TARGET_LOCATION:

            case STILL:
                // attack in reach only
            case PLAYER:
            default:
                return new GridWaitInteraction(actor);
        }
    }

    public GridInteraction buildAggressiveAction(GridUnit unit) {

        // First decide if the unit needs to move
        // then build an appropriate action

        final GridPathfinder.Things accessibleThings = GridPathfinder.currentlyAccessibleTo(h.map(), unit);



        return new GridWaitInteraction(unit);
    }

    // TODO:
    //  - best or worst combat action


}
