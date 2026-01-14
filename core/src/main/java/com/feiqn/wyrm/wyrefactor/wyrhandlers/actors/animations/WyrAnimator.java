package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.gridmeta.GridMetaHandler;

public abstract class WyrAnimator extends Wyr {

    public enum AnimationState {
        FACING_NORTH,
        FACING_SOUTH,
        FACING_EAST,
        FACING_WEST,
        IDLE,
        FLOURISH,
    }

    protected Animation<TextureRegionDrawable> idleAnimation;
    protected Animation<TextureRegionDrawable> flourishAnimation;
    protected Animation<TextureRegionDrawable> walkingWestAnimation;
    protected Animation<TextureRegionDrawable> walkingEastAnimation;
    protected Animation<TextureRegionDrawable> walkingSouthAnimation;
    protected Animation<TextureRegionDrawable> walkingNorthAnimation;

    protected AnimationState state = AnimationState.IDLE;
    private final WyrActor parent;
    protected final GridMetaHandler h;

    public WyrAnimator(WyrType wyrType, GridMetaHandler metaHandler, WyrActor parent) {
        super(wyrType);
        this.parent = parent;
        this.h = metaHandler;
    }

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
//                    Gdx.app.log("WyrAnimator", "Blank State");
                    break;
            }
        } catch (Exception e) {
//            Gdx.app.log("WyrAnimator", "Failed to update");
        }
    }

    public void setState(AnimationState state) {
        if(this.state == state) return;
        h.time().record(parent); // Time of last frame change.
        h.time().recordStateTime(parent); // Time of state change.
        this.state = state;
        try {
            final Drawable newDrawable;
            switch(state) {
                case IDLE:
                    newDrawable = idleAnimation.getKeyFrame(0);
                    if(newDrawable.getMinWidth() > 16) {
                        final float relativeWidth = newDrawable.getMinWidth() /  16;
                        parent.setSize(relativeWidth, parent.getHeight());
                    }
                    if(newDrawable.getMinHeight() > 16) {
                        final float relativeHeight = newDrawable.getMinHeight() / 16;
                        parent.setSize(parent.getWidth(), relativeHeight);
                    }
                    parent.setDrawable(newDrawable);
                    break;

                case FLOURISH:
                    newDrawable = flourishAnimation.getKeyFrame(0);
                    if(newDrawable.getMinWidth() > 16) {
                        final float relativeWidth = newDrawable.getMinWidth() /  16;
                        parent.setSize(relativeWidth, parent.getHeight());
                    }
                    if(newDrawable.getMinHeight() > 16) {
                        final float relativeHeight = newDrawable.getMinHeight() / 16;
                        parent.setSize(parent.getWidth(), relativeHeight);
                    }
                    parent.setDrawable(newDrawable);
                    break;

                case FACING_EAST:
                    newDrawable = walkingEastAnimation.getKeyFrame(0);
                    if(newDrawable.getMinWidth() > 16) {
                        final float relativeWidth = newDrawable.getMinWidth() /  16;
                        parent.setSize(relativeWidth, parent.getHeight());
                    }
                    if(newDrawable.getMinHeight() > 16) {
                        final float relativeHeight = newDrawable.getMinHeight() / 16;
                        parent.setSize(parent.getWidth(), relativeHeight);
                    }
                    parent.setDrawable(newDrawable);
                    break;
                case FACING_WEST:
                    newDrawable = walkingWestAnimation.getKeyFrame(0);
                    if(newDrawable.getMinWidth() > 16) {
                        final float relativeWidth = newDrawable.getMinWidth() /  16;
                        parent.setSize(relativeWidth, parent.getHeight());
                    }
                    if(newDrawable.getMinHeight() > 16) {
                        final float relativeHeight = newDrawable.getMinHeight() / 16;
                        parent.setSize(parent.getWidth(), relativeHeight);
                    }
                    parent.setDrawable(newDrawable);
                    break;
                case FACING_NORTH:
                    newDrawable = walkingNorthAnimation.getKeyFrame(0);
                    if(newDrawable.getMinWidth() > 16) {
                        final float relativeWidth = newDrawable.getMinWidth() /  16;
                        parent.setSize(relativeWidth, parent.getHeight());
                    }
                    if(newDrawable.getMinHeight() > 16) {
                        final float relativeHeight = newDrawable.getMinHeight() / 16;
                        parent.setSize(parent.getWidth(), relativeHeight);
                    }
                    parent.setDrawable(newDrawable);
                    break;
                case FACING_SOUTH:
                    newDrawable = walkingSouthAnimation.getKeyFrame(0);
                    if(newDrawable.getMinWidth() > 16) {
                        final float relativeWidth = newDrawable.getMinWidth() /  16;
                        parent.setSize(relativeWidth, parent.getHeight());
                    }
                    if(newDrawable.getMinHeight() > 16) {
                        final float relativeHeight = newDrawable.getMinHeight() / 16;
                        parent.setSize(parent.getWidth(), relativeHeight);
                    }
                    parent.setDrawable(newDrawable);
                    break;
            }
        } catch (Exception e) {
            Gdx.app.log("WyrAnimator", "setState [error]");
        }

    }

    protected void generateAnimations() {}
    public AnimationState getState() { return state; }
    protected WyrActor getParent() { return parent; }



}
