package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.gridcp;

import com.feiqn.wyrm.OLD_DATA.logic.handlers.ai.actions.ActionType;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerPlayerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.cpaction.grid.GridCPAction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public final class GridComputerPlayerHandler extends WyrComputerPlayerHandler {

    private final GridMetaHandler h; // It's fun to just type "h".

    public GridComputerPlayerHandler(GridMetaHandler metaHandler) {
        super(WyrType.GRIDWORLD);
        this.h = metaHandler;
        computerPlayer = new GridComputerPlayer(h);
    }

    public void run(GridUnit unit) {

        final GridCPAction action = ((GridComputerPlayer)computerPlayer).bestAction(unit);

        switch(action.actionType()) {
            case MOVE_ACTION:
                move(action);
                break;
            case ATTACK_ACTION:
                attack(action);
                break;
            case USE_ITEM_ACTION:
                useItem(action);
                break;
            case ESCAPE_ACTION:
                escape(action);
                break;
            case WAIT_ACTION:
                wait(action);
                break;
            case PASS_ACTION:
                pass(action);
                break;
            case USE_ABILITY_ACTION:
                useAbility(action);
                break;
            case WORLD_INTERACT_ACTION:
                worldInteraction(action);
                break;
            default:
                break;
        }
        // TODO:
        //  - parse action into actor handler
    }

    public GridCPAction bestAction(GridUnit actor) {
        switch(actor.personality().personalityType()) {
            case AGGRESSIVE:
                return aggressiveAction(actor);
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
                return new GridCPAction(ActionType.PASS_ACTION);
        }
//        return null;
    }

    /**
     * These functions build a set of GridInteractions which are
     * then passed together as a series to be carried out sequentially.
     * I.E., Unit move to XY, then Attack Enemy
     * @param action
     */

    private void move(GridCPAction action) {

    }

    private void attack(GridCPAction action) {

    }

    private void useItem(GridCPAction action) {

    }

    private void escape(GridCPAction action) {

    }

    private void wait(GridCPAction action) {

    }

    private void pass(GridCPAction action) {

    }

    private void useAbility(GridCPAction action) {

    }

    private void worldInteraction(GridCPAction action) {

    }

}
