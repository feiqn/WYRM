package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.feiqn.wyrm.WYRMGame;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.metahandler.MetaHandler;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;

public abstract class WyrActor extends Image {

    // Things shared between different types of WyrScreens,
    // Grid combat, in menus, on world map, etc.,
    // also a generic higher-level class for bullets and effects, etc

    public enum AnimationState {
        WALKING_NORTH,
        WALKING_SOUTH,
        WALKING_EAST,
        WALKING_WEST,
        IDLE,
        FLOURISH,
    }
    protected AnimationState animationState;
    private float previousAnimationChangeClockTime = 0;
    private float timeInCurrentAnimationState = 0;
    // TODO: abstract to ActorAnimationHandler() ?
    protected Animation<TextureRegionDrawable> idleAnimation;
    protected Animation<TextureRegionDrawable> flourishAnimation;
    protected Animation<TextureRegionDrawable> walkingWestAnimation;
    protected Animation<TextureRegionDrawable> walkingEastAnimation;
    protected Animation<TextureRegionDrawable> walkingSouthAnimation;
    protected Animation<TextureRegionDrawable> walkingNorthAnimation;
    // TODO:
    //  protected WyrAnimator animator = new WyrAnimator;

    protected final WYRMGame root;

    protected static MetaHandler h;

    protected String actorName = "";
    protected String actorDescription = "";

    protected boolean hoveredOver = false;
    protected boolean hoverActivated = false;

    protected float hoverTime = 0;

    protected final WyrType wyrType;

    protected final Array<WyrInteraction> interactables = new Array<>();

    public WyrActor(WYRMGame root, WyrType type) {
        this(root, type, (Drawable)null);
    }
    public WyrActor (WYRMGame root,WyrType type, @Null NinePatch patch) {
        this(root, type, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public WyrActor (WYRMGame root, WyrType type,@Null TextureRegion region) {
        this(root, type, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public WyrActor (WYRMGame root, WyrType type,Texture texture) {
        this(root, type, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public WyrActor (WYRMGame root, WyrType type,Skin skin, String drawableName) {
        this(root, type, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public WyrActor (WYRMGame root, WyrType type,@Null Drawable drawable) {
        this(root, type, drawable, Scaling.stretch, Align.center);
    }
    public WyrActor (WYRMGame root, WyrType type,@Null Drawable drawable, Scaling scaling) {
        this(root, type, drawable, scaling, Align.center);
    }
    public WyrActor (WYRMGame root, WyrType type, @Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        this.root = root;
        this.wyrType = type;
        h = root.handlers();
        this.setSize(1, 1); // just a little square
        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hoveredOver = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hoveredOver = false;
            }
        });
    }

    @Override
    public void act(float delta) {
        if(!hoveredOver && hoverTime > 0) { // tick down
            hoverTime -= delta;
            if(hoverTime <= 0) {
                hoverTime = 0;
                unHover();
            }
        } else if(!hoverActivated && hoveredOver && hoverTime < .1f) { // tick up
            hoverTime += delta;
            if(hoverTime >= .1f) {
                hoverOver();
            }
        }

        super.act(delta);
    }

    protected abstract void hoverOver();
//    {
//        hoverActivated = true;
//
//        if(self.animationState == SimpleUnit.AnimationState.IDLE) {
//            flourish();
//        }
//
//        game.activeGridScreen.hud().updateHoveredUnitInfoPanel(self);
//        game.activeGridScreen.hoveredUnit = self;
//
//        if(game.activeGridScreen.activeUnit != null) return;
//        if(game.activeGridScreen.getInputMode() != GridScreen.InputMode.STANDARD) return;
//
//        game.activeGridScreen.getRecursionHandler().recursivelySelectReachableTiles(self);
//
//        for(LogicalTile tile : game.activeGridScreen.reachableTiles) {
//            tile.highlight();
//            highlighted.add(tile);
//        }
//    }

    protected abstract  void unHover();
//    {
//        hoverActivated = false;
//
//        if(self.animationState == SimpleUnit.AnimationState.FLOURISH && game.activeGridScreen.activeUnit != this) {
//            idle();
//        }
//
//        game.activeGridScreen.hoveredUnit = null;
//        for(LogicalTile tile : highlighted) {
//            tile.clearHighlight();
//        }
//        highlighted.clear();
//
////        if(clicked && teamAlignment != TeamAlignment.PLAYER) {
//        // TODO: add unit's reachable tiles to danger heatmap display
////            clicked = false;
////            unHover();
////        }
//    }

//    public void idle() {
//        if(this.animationState == SimpleUnit.AnimationState.IDLE) return;
//        try {
//            this.setDrawable(idleAnimation.getKeyFrame(0));
//            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
//        } catch (Exception ignored) {}
//        timeInCurrentAnimationState = 0;
//        this.animationState = SimpleUnit.AnimationState.IDLE;
//    }
//    public void flourish() {
//        if(this.animationState == SimpleUnit.AnimationState.FLOURISH) return;
//        try {
//            this.setDrawable(flourishAnimation.getKeyFrame(0));
//            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
//        } catch (Exception ignored) {}
//        timeInCurrentAnimationState = 0;
//        this.animationState = SimpleUnit.AnimationState.FLOURISH;
//    }
//    public void faceWest() {
//        if(this.animationState == SimpleUnit.AnimationState.WALKING_WEST) return;
//        try {
//            this.setDrawable(walkingWestAnimation.getKeyFrame(0));
//            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
//        } catch (Exception ignored) {}
//        timeInCurrentAnimationState = 0;
//        this.animationState = SimpleUnit.AnimationState.WALKING_WEST;
//    }
//    public void faceEast() {
//        if(this.animationState == SimpleUnit.AnimationState.WALKING_EAST) return;
//
//        try {
//            this.setDrawable(walkingEastAnimation.getKeyFrame(0));
//            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
//        } catch (Exception ignored) {}
//        timeInCurrentAnimationState = 0;
//        this.animationState = SimpleUnit.AnimationState.WALKING_EAST;
//    }
//    public void faceNorth() {
//        if(this.animationState == SimpleUnit.AnimationState.WALKING_NORTH) return;
//
//        try {
//            this.setDrawable(walkingNorthAnimation.getKeyFrame(0));
//            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
//        } catch (Exception ignored) {}
//        timeInCurrentAnimationState = 0;
//        this.animationState = SimpleUnit.AnimationState.WALKING_NORTH;
//    }
//    public void faceSouth() {
//        if(this.animationState == SimpleUnit.AnimationState.WALKING_SOUTH) return;
//        try {
//            this .setDrawable(walkingSouthAnimation.getKeyFrame(0));
//            previousAnimationChangeClockTime = game.activeGridScreen.getClock();
//        } catch (Exception ignored) {}
//        timeInCurrentAnimationState = 0;
//        this.animationState = SimpleUnit.AnimationState.WALKING_SOUTH;
//    }

    public void setName(String name) { this.actorName = name;}
    public void addInteractable(WyrInteraction interaction) { interactables.add(interaction); }
    public Array<WyrInteraction> getInteractables() { return interactables; }
    @Override
    public String getName() { return actorName; }
    public String getDescription() { return actorDescription; }
    public AnimationState getAnimationState() { return animationState; }
    public WyrType getWyrType() { return wyrType; }

}
