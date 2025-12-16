package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayer;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.grid.GridCPAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridComputerPlayer extends WyrComputerPlayer {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridComputerPlayer(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
    }

    public GridCPAction bestAction(GridUnit actor) {
        switch(actor.personality().personalityType()) {
            case AGGRESSIVE:
                return aggressiveAction(actor);
            case ESCAPE:
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
                // return pass action;
                break;
        }
        return null;
    }

    private GridCPAction aggressiveAction(GridUnit unit) {
        return null;
    }

    // TODO:
    //  - best or worst combat action


}
