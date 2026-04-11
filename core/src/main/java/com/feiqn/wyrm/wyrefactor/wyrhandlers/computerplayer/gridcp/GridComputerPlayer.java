package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.actors.grid.units.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.Interactions.grid.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;

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
                return new GridInteraction(actor).passPriority();
        }
    }

    public GridInteraction buildAggressiveAction(GridUnit unit) {

        // First decide if the unit needs to move
        // then build an appropriate action

        final GridPathfinder.Things accessibleThings = GridPathfinder.currentlyAccessibleTo(h.map(), unit);

        switch(accessibleThings.enemies().size()) {
            case 0:
                // No enemies in range, pick target and move closer.
                break;

            case 1:
                // Only one enemy, decide if it's close enough or need to move first.
                break;

            default:
                // Decide which enemy to aggress.
                break;
        }


        return new GridInteraction(unit);
    }

    private GridActor preferredTarget(GridUnit forAggressor) {
        // Cycle through all units and props to see what we want to hit most.
        return null;
    }

    private GridActor preferredTarget(Array<GridActor> fromList) {

        return null;
    }
    // TODO:
    //  - best or worst combat action


}
