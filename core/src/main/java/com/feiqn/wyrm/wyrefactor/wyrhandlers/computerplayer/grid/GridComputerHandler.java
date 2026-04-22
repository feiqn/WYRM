package com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.grid;

import com.badlogic.gdx.utils.Array;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.computerplayer.WyrComputerHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;
import com.feiqn.wyrm.wyrefactor.actors.Interactions.grid.GridInteraction;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.grid.logicalgrid.pathing.pathfinder.GridPathfinder;

public final class GridComputerHandler extends WyrComputerHandler<RPGridUnit, GridInteraction, RPGridMetaHandler> {



    public GridComputerHandler(RPGridMetaHandler metaHandler) {
        this.h = metaHandler;
    }

    public void run(Array<RPGridUnit> units) {
        final Array<GridInteraction> options = new Array<>();

        for(RPGridUnit unit : units) {
            final GridInteraction action = preferredAction(unit);
            options.add(action);
        }

        h.interactions().parseInteractable(preferredActionFromList(options));
    }

    @Override
    protected GridInteraction preferredAction(RPGridUnit actor) {
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
            case TARGET_PROP:
            case TARGET_LOCATION:

            case STILL:
                // attack in reach only
            case PLAYER:
            default:
                return new GridInteraction(actor).passPriority();
        }
    }

    public GridInteraction buildAggressiveAction(RPGridUnit unit) {

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

    private RPGridActor preferredTarget(RPGridUnit forAggressor) {
        // Cycle through all units and props to see what unit wants to hit most.
        return null;
    }

    private RPGridActor preferredTargetFromList(Array<RPGridActor> fromList) {
        return null;
    }

    // TODO:
    //  - best or worst combat action

    private GridInteraction preferredActionFromList(Array<GridInteraction> options) {
        GridInteraction bestChoice = options.first();

        for(GridInteraction option : options) {
            if(weightForChoice(option) > weightForChoice(bestChoice)) {
                bestChoice = option;
            }
        }

        return bestChoice;
    }

    private int weightForChoice(GridInteraction interaction) {

        switch(interaction.getSubject().stats().getPersonality().personalityType()) {

            case PLAYER:
            case STILL:
                switch(interaction.getInteractType()) {
                    case WAIT:
                        return 10;

                    case MOVE:
                    case ATTACK:
                    case TALK:

                    case MOVE_TALK:
                    case MOVE_WAIT:
                    case MOVE_ATTACK:

                    case PROP_LOOT:
                    case PROP_OPEN:
                    case PROP_PILOT:
                    case PROP_SEIZE:
                    case PROP_ESCAPE:
                    case PROP_DESTROY:

                    case EXAMINE:
                    default:
                        return -10;
                }

            case ESCAPE:
            case FLANKING:
            case LOS_FLEE:
            case RECKLESS:

            case PATROLLING:
            case LOS_AGGRO:
            case AGGRESSIVE:
                switch(interaction.getInteractType()) {
                    case WAIT:
                        return -2;

                    case MOVE:
                    case TALK:

                    case MOVE_TALK:

                    case MOVE_WAIT:

                    case ATTACK:
                    case MOVE_ATTACK:
                        // TODO:
                        //  Weigh outcome of combat vs interaction.object,
                        //  decide if the fight is favorable.
                        return 2;

                    case PROP_LOOT:
                    case PROP_OPEN:
                    case PROP_PILOT:
                    case PROP_SEIZE:
                    case PROP_ESCAPE:
                    case PROP_DESTROY:

                    case EXAMINE:
                    default:
                        return -10;
                }


            case DEFENSIVE:
            case PROTECTIVE:


            case TARGET_UNIT:
            case TARGET_PROP:
            case TARGET_LOCATION:

            default:
                return -10;
        }

    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRIDWORLD;
    }
}
