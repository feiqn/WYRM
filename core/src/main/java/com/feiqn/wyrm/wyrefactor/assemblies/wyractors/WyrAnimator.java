package com.feiqn.wyrm.wyrefactor.assemblies.wyractors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.assemblies.wyractors.actors.WyrActor;
import com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame;

import static com.feiqn.wyrm.wyrefactor.helpers.interfaces.WyrFrame.AnimationState.*;

public class WyrAnimator implements WyrFrame {

    private Animation<TextureRegionDrawable> idleAnimation;
    private Animation<TextureRegionDrawable> flourishAnimation;
    private Animation<TextureRegionDrawable> walkingWestAnimation;
    private Animation<TextureRegionDrawable> walkingEastAnimation;
    private Animation<TextureRegionDrawable> walkingSouthAnimation;
    private Animation<TextureRegionDrawable> walkingNorthAnimation;

    protected final WyrActor parentActor;
    protected AnimationState state;

    public WyrAnimator(WyrActor parent) {
        parentActor = parent;
    }

    public void update() {
        try {
            switch(getState()) {
                case IDLE:
                    if(handlers.time().diff(parentActor) >= idleAnimation.getFrameDuration()) {
                        parentActor.setDrawable(idleAnimation.getKeyFrame(handlers.time().stateTime(parentActor), true));
                        handlers.time().record(parentActor);
                    }
                    break;
                case FLOURISH:
                    if(handlers.time().diff(parentActor) >= flourishAnimation.getFrameDuration()) {
                        parentActor.setDrawable(flourishAnimation.getKeyFrame(handlers.time().stateTime(parentActor), true));
                        handlers.time().record(parentActor);
                    }
                    break;
                case FACING_SOUTH:
                    if(handlers.time().diff(parentActor) >= walkingSouthAnimation.getFrameDuration()) {
                        parentActor.setDrawable(walkingSouthAnimation.getKeyFrame(handlers.time().stateTime(parentActor), true));
                        handlers.time().record(parentActor);
                    }
                    break;
                case FACING_NORTH:
                    if(handlers.time().diff(parentActor) >= walkingNorthAnimation.getFrameDuration()) {
                        parentActor.setDrawable(walkingNorthAnimation.getKeyFrame(handlers.time().stateTime(parentActor), true));
                        handlers.time().record(parentActor);
                    }
                    break;
                case FACING_WEST:
                    if(handlers.time().diff(parentActor) >= walkingWestAnimation.getFrameDuration()) {
                        parentActor.setDrawable(walkingWestAnimation.getKeyFrame(handlers.time().stateTime(parentActor), true));
                        handlers.time().record(parentActor);
                    }
                    break;
                case FACING_EAST:
                    if(handlers.time().diff(parentActor) >= walkingEastAnimation.getFrameDuration()) {
                        parentActor.setDrawable(walkingEastAnimation.getKeyFrame(handlers.time().stateTime(parentActor), true));
                        handlers.time().record(parentActor);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception ignored) {}
    }
    public void setState(AnimationState state) {
        if(this.state == state) return;
        handlers.time().record(parentActor); // Time of last frame change.
        handlers.time().recordStateTime(parentActor); // Time of state change.
        this.state = state;
        try {
            final float relativeWidth;
            final float relativeHeight;
            float oldWidth = parentActor.getWidth();
            float oldX = parentActor.getX();
            parentActor.setVisible(false);
            switch(state) {
                case IDLE:
                    parentActor.setDrawable(idleAnimation.getKeyFrame(0));
                    break;
                case FLOURISH:
                    parentActor.setDrawable(flourishAnimation.getKeyFrame(0));
                    break;
                case FACING_EAST:
                    parentActor.setDrawable(walkingEastAnimation.getKeyFrame(0));
                    break;
                case FACING_WEST:
                    parentActor.setDrawable(walkingWestAnimation.getKeyFrame(0));
                    break;
                case FACING_NORTH:
                    parentActor.setDrawable(walkingNorthAnimation.getKeyFrame(0));
                    break;
                case FACING_SOUTH:
                default:
                    parentActor.setDrawable(walkingSouthAnimation.getKeyFrame(0));
                    break;
            }

            relativeWidth = parentActor.getDrawable().getMinWidth() /  16;
            relativeHeight = parentActor.getDrawable().getMinHeight() / 16;

            parentActor.setSize(relativeWidth, relativeHeight);
            parentActor.setX(oldX + (oldWidth - parentActor.getWidth()) / 2);
            parentActor.setVisible(true);
        } catch (Exception e) {
//            Gdx.app.log("Animator", "setState [error]");
        }
    }

    public void generateAnimations() {
        idleAnimation         = WYRMGame.assets().getWyrAnimation(parentActor, IDLE);
        flourishAnimation     = WYRMGame.assets().getWyrAnimation(parentActor, FLOURISH);
        walkingEastAnimation  = WYRMGame.assets().getWyrAnimation(parentActor, FACING_EAST);
        walkingNorthAnimation = WYRMGame.assets().getWyrAnimation(parentActor, FACING_NORTH);
        walkingSouthAnimation = WYRMGame.assets().getWyrAnimation(parentActor, FACING_SOUTH);
        walkingWestAnimation  = WYRMGame.assets().getWyrAnimation(parentActor, FACING_WEST);
    }
    public AnimationState getState() { return state; }
    protected WyrActor getParentActor() { return parentActor; }

}
