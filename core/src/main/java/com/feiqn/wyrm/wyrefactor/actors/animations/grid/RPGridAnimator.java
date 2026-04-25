package com.feiqn.wyrm.wyrefactor.actors.animations.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.helpers.WyrType;
import com.feiqn.wyrm.wyrefactor.actors.animations.WyrAnimator;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.RPGridActor;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.props.RPGridProp;
import com.feiqn.wyrm.wyrefactor.actors.actors.rpgrid.prefab.units.RPGridUnit;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.RPGridMetaHandler;

import java.util.Objects;

public final class RPGridAnimator extends WyrAnimator<
        RPGridActor,
        RPGridMetaHandler,
        RPGridAnimator.RPGridAnimState> {

    public enum RPGridAnimState {
        FACING_NORTH,
        FACING_SOUTH,
        FACING_EAST,
        FACING_WEST,
        IDLE,
        FLOURISH,
    }

    private Animation<TextureRegionDrawable> idleAnimation;
    private Animation<TextureRegionDrawable> flourishAnimation;
    private Animation<TextureRegionDrawable> walkingWestAnimation;
    private Animation<TextureRegionDrawable> walkingEastAnimation;
    private Animation<TextureRegionDrawable> walkingSouthAnimation;
    private Animation<TextureRegionDrawable> walkingNorthAnimation;


    private final RPGridActor.ActorType actorType;

    public RPGridAnimator(RPGridMetaHandler metaHandler, RPGridActor parent) {
        super(parent);
        this.h = metaHandler;
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
        if(Objects.requireNonNull(unitParent()).getRosterID() == null) {
            Gdx.app.log("GridAnimator", "null ID");
            return;
        }
        idleAnimation         = WYRMGame.assets().getRPGridAnimation(Objects.requireNonNull(unitParent()), RPGridAnimState.IDLE);
        flourishAnimation     = WYRMGame.assets().getRPGridAnimation(Objects.requireNonNull(unitParent()), RPGridAnimState.FLOURISH);
        walkingEastAnimation  = WYRMGame.assets().getRPGridAnimation(Objects.requireNonNull(unitParent()), RPGridAnimState.FACING_EAST);
        walkingNorthAnimation = WYRMGame.assets().getRPGridAnimation(Objects.requireNonNull(unitParent()), RPGridAnimState.FACING_NORTH);
        walkingSouthAnimation = WYRMGame.assets().getRPGridAnimation(Objects.requireNonNull(unitParent()), RPGridAnimState.FACING_SOUTH);
        walkingWestAnimation  = WYRMGame.assets().getRPGridAnimation(Objects.requireNonNull(unitParent()), RPGridAnimState.FACING_WEST);
    }

    @Override
    public void update() {
        try {
            switch(state) {
                case IDLE:
                    if(h.time().diff(parent) >= idleAnimation.getFrameDuration()) {
                        parent.setDrawable(idleAnimation.getKeyFrame(h.time().stateTime(parent), true));
                        h.time().record(parent);
                    }
                    break;
                case FLOURISH:
                    if(h.time().diff(parent) >= flourishAnimation.getFrameDuration()) {
                        parent.setDrawable(flourishAnimation.getKeyFrame(h.time().stateTime(parent), true));
                        h.time().record(parent);
                    }
                    break;
                case FACING_SOUTH:
                    if(h.time().diff(parent) >= walkingSouthAnimation.getFrameDuration()) {
                        parent.setDrawable(walkingSouthAnimation.getKeyFrame(h.time().stateTime(parent), true));
                        h.time().record(parent);
                    }
                    break;
                case FACING_NORTH:
                    if(h.time().diff(parent) >= walkingNorthAnimation.getFrameDuration()) {
                        parent.setDrawable(walkingNorthAnimation.getKeyFrame(h.time().stateTime(parent), true));
                        h.time().record(parent);
                    }
                    break;
                case FACING_WEST:
                    if(h.time().diff(parent) >= walkingWestAnimation.getFrameDuration()) {
                        parent.setDrawable(walkingWestAnimation.getKeyFrame(h.time().stateTime(parent), true));
                        h.time().record(parent);
                    }
                    break;
                case FACING_EAST:
                    if(h.time().diff(parent) >= walkingEastAnimation.getFrameDuration()) {
                        parent.setDrawable(walkingEastAnimation.getKeyFrame(h.time().stateTime(parent), true));
                        h.time().record(parent);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Gdx.app.log("WyrAnimator", "Failed to update");
        }
    }

    @Override
    public void setState(RPGridAnimState state) {
        super.setState(state);
        try {
            final float relativeWidth;
            final float relativeHeight;
            float oldWidth = parent.getWidth();
            float oldX = parent.getX();
            parent.setVisible(false);
            switch(state) {
                case IDLE:
                    parent.setDrawable(idleAnimation.getKeyFrame(0));
                    break;
                case FLOURISH:
                    parent.setDrawable(flourishAnimation.getKeyFrame(0));
                    break;
                case FACING_EAST:
                    parent.setDrawable(walkingEastAnimation.getKeyFrame(0));
                    break;
                case FACING_WEST:
                    parent.setDrawable(walkingWestAnimation.getKeyFrame(0));
                    break;
                case FACING_NORTH:
                    parent.setDrawable(walkingNorthAnimation.getKeyFrame(0));
                    break;
                case FACING_SOUTH:
                default:
                    parent.setDrawable(walkingSouthAnimation.getKeyFrame(0));
                    break;
            }

            relativeWidth = parent.getDrawable().getMinWidth() /  16;
            relativeHeight = parent.getDrawable().getMinHeight() / 16;

            parent.setSize(relativeWidth, relativeHeight);
            parent.setX(oldX + (oldWidth - parent.getWidth()) / 2);
            parent.setVisible(true);
        } catch (Exception e) {
            Gdx.app.log("Animator", "setState [error]");
        }
    }

    @Override
    public WyrType getWyrType() {
        return WyrType.RPGRIDWORLD;
    }

}
