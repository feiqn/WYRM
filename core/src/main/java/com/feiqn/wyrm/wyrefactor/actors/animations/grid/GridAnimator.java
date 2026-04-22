package com.feiqn.wyrm.wyrefactor.actors.animations.grid;

import com.badlogic.gdx.Gdx;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import java.util.Objects;

public final class GridAnimator extends WyrAnimator<RPGridActor> {

    private final RPGridActor.ActorType actorType;

    public GridAnimator(RPGridMetaHandler metaHandler, RPGridActor parent) {
        super(metaHandler, parent);
        this.actorType = parent.getActorType();
    }

    @Override
    protected RPGridActor getParent() {
        switch(actorType) {
            case UNIT:
                return unitParent();
            case PROP:
                return propParent();
        }
        return super.getParent();
    }
    private RPGridUnit unitParent() {
        if(!(super.getParent() instanceof RPGridUnit)) return null;
        return (RPGridUnit)super.getParent();
    }
    private RPGridProp propParent() {
        if(!(super.getParent() instanceof RPGridProp)) return null;
        return (RPGridProp)super.getParent();
    }

    @Override
    public void generateAnimations() {
        switch(actorType) {
            case UNIT:
                generateUnitAnimations();
                break;
            case PROP:
                generatePropAnimations(Objects.requireNonNull(propParent()).getPropType());
                break;
        }
    }

    private void generatePropAnimations(RPGridProp.PropType propID) {
        // TODO:
        //  Most props will only ever need to idle.
        //  Simple things like a chest opening can be
        //  done using flourish state.
        //  More complicated prop behaviors may require
        //  a small-scale refactor of AnimationState.
    }

    private void generateUnitAnimations() {
        if(unitParent().getRosterID() == null) {
            Gdx.app.log("GridAnimator", "null ID");
            return;
        }
        idleAnimation         = WYRMGame.assets().getAnimation(Objects.requireNonNull(unitParent()), AnimationState.IDLE);
        flourishAnimation     = WYRMGame.assets().getAnimation(Objects.requireNonNull(unitParent()), AnimationState.FLOURISH);
        walkingEastAnimation  = WYRMGame.assets().getAnimation(Objects.requireNonNull(unitParent()), AnimationState.FACING_EAST);
        walkingNorthAnimation = WYRMGame.assets().getAnimation(Objects.requireNonNull(unitParent()), AnimationState.FACING_NORTH);
        walkingSouthAnimation = WYRMGame.assets().getAnimation(Objects.requireNonNull(unitParent()), AnimationState.FACING_SOUTH);
        walkingWestAnimation  = WYRMGame.assets().getAnimation(Objects.requireNonNull(unitParent()), AnimationState.FACING_WEST);
    }


    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRIDWORLD;
    }

}
