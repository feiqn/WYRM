package com.feiqn.wyrm.wyrefactor.wyrhandlers.actors;

import com.badlogic.gdx.graphics.Texture;
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
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;

public abstract class WyrActor extends Image {

    // Things shared between different types of WyrScreens,
    // Grid combat, in menus, on world map, etc.,
    // also a generic higher-level class for bullets and effects, etc

    public enum ShaderState {
        DIM,
        HIGHLIGHT,
        TEAM_ENEMY,
        TEAM_OTHER,
        TEAM_ALLY,
    }

    private final Wyr wyr;

//    protected static MetaHandler h;

//    protected WyrAnimator animator;

    protected final Array<ShaderState> shaderStates = new Array<>();

    private String actorName = "";
    private String actorDescription = "";

    private boolean hoveredOver = false;
    private boolean hoverActivated = false;

    private float hoverTime = 0;

    protected final Array<WyrInteraction> interactables = new Array<>();

    // TODO:
    //  Need to come back and clean all this up again
    //  to make consistent with new singleton implementation
    //  as of 12/13/25 (I learned to do it.)

    public WyrActor(WyrType type) {
        this(type, (Drawable)null);
    }
    public WyrActor (WyrType type, @Null NinePatch patch) {
        this(type, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type,@Null TextureRegion region) {
        this(type, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type,Texture texture) {
        this(type, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public WyrActor(WyrType type,Skin skin, String drawableName) {
        this(type, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type,@Null Drawable drawable) {
        this(type, drawable, Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type,@Null Drawable drawable, Scaling scaling) {
        this(type, drawable, scaling, Align.center);
    }
    public WyrActor(WyrType type, @Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        wyr = new Wyr(type);
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

    protected void hoverOver() {
        hoverActivated = true;
    }

    protected void unHover() {
        hoverActivated = false;
    }
//    {
//
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
    public void setDescription(String description) {this.actorDescription = description;}
    public void addInteractable(WyrInteraction interaction) { interactables.add(interaction); }
    public Array<WyrInteraction> getInteractables() { return interactables; }
    @Override
    public String getName() { return actorName; }
    public String getDescription() { return actorDescription; }
    public WyrType getWyrType() { return wyr.wyrType(); }

}
