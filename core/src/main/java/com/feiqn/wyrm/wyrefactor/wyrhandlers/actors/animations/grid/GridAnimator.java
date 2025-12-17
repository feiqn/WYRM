package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.grid;

import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.models.unitdata.units.OLD_SimpleUnit;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.GridActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridprops.GridProp;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.GridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.gridactors.gridunits.prefab.UnitIDRoster;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

import java.util.Objects;

public final class GridAnimator extends WyrAnimator {

    private final GridActor.ActorType actorType;

    public GridAnimator(GridMetaHandler metaHandler, GridActor parent) {
        super(WyrType.GRIDWORLD, metaHandler, parent);
        this.actorType = parent.getActorType();
    }

    @Override
    protected WyrActor getParent() {
        switch(actorType) {
            case UNIT:
                return unitParent();
            case PROP:
                return propParent();
        }
        return super.getParent();
    }
    private GridUnit unitParent() {
        if(!(super.getParent() instanceof GridUnit)) return null;
        return (GridUnit)super.getParent();
    }
    private GridProp propParent() {
        if(!(super.getParent() instanceof GridProp)) return null;
        return (GridProp)super.getParent();
    }

    @Override
    protected void generateAnimations() {
        switch(actorType) {
            case UNIT:
                generateUnitAnimations(Objects.requireNonNull(unitParent()).getRosterID());
                break;
            case PROP:
                generatePropAnimations(Objects.requireNonNull(propParent()).getPropType());
                break;
        }
    }

    private void generatePropAnimations(GridProp.PropType propID) {
        // TODO:
        //  Most props will only ever need to idle.
        //  Simple things like a chest opening can be
        //  done using flourish state.
        //  More complicated prop behaviors may require
        //  a small-scale refactor of AnimationState.
    }
    private void generateUnitAnimations(UnitIDRoster rosterID) {
        idleAnimation = WYRMGame.assets().getAnimation(rosterID, OLD_SimpleUnit.AnimationState.IDLE);
        flourishAnimation = WYRMGame.assets().getAnimation(rosterID, OLD_SimpleUnit.AnimationState.FLOURISH);
        walkingEastAnimation = WYRMGame.assets().getAnimation(rosterID, OLD_SimpleUnit.AnimationState.WALKING_EAST);
        walkingNorthAnimation = WYRMGame.assets().getAnimation(rosterID, OLD_SimpleUnit.AnimationState.WALKING_NORTH);
        walkingSouthAnimation = WYRMGame.assets().getAnimation(rosterID, OLD_SimpleUnit.AnimationState.WALKING_SOUTH);
        walkingWestAnimation = WYRMGame.assets().getAnimation(rosterID, OLD_SimpleUnit.AnimationState.WALKING_WEST);
    }


}
