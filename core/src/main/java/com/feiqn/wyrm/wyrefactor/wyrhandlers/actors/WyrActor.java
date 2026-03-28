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
import com.feiqn.wyrm.wyrefactor.Examinable;
import com.feiqn.wyrm.wyrefactor.Wyr;
import com.feiqn.wyrm.wyrefactor.Wyr_DEPRECATED;
import com.feiqn.wyrm.wyrefactor.WyrType;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.combat.math.stats.WyrStats;
import com.feiqn.wyrm.wyrefactor.wyrhandlers.worlds.WyrInteraction;

public abstract class WyrActor extends Image implements Wyr, Examinable {

    public enum ActorType {
        UNIT,
        PROP,
        ITEM,
        BULLET,
    }

    protected final WyrStats stats;

    protected final ActorType actorType;

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

    protected final Array<ShaderState> shaderStates = new Array<>();

    private boolean hoveredOver = false;
    private boolean hoverActivated = false;

    private float hoverTime = 0;

    protected final Array<WyrInteraction> interactables = new Array<>();

    final WyrType wyrType;

    // TODO:
    //  Need to come back and clean all this up again
    //  to make consistent with new singleton implementation
    //  as of 12/13/25 (I learned to do it.)

    public WyrActor(WyrType type, ActorType actorType) {
        this(type, actorType, (Drawable)null);
    }
    public WyrActor (WyrType type, ActorType actorType,  @Null NinePatch patch) {
        this(type, actorType, new NinePatchDrawable(patch), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType,  @Null TextureRegion region) {
        this(type, actorType, new TextureRegionDrawable(region), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType, Texture texture) {
        this(type, actorType, new TextureRegionDrawable(new TextureRegion(texture)));
    }
    public WyrActor(WyrType type, ActorType actorType, Skin skin, String drawableName) {
        this(type, actorType, skin.getDrawable(drawableName), Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType, @Null Drawable drawable) {
        this(type, actorType, drawable, Scaling.stretch, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType,  @Null Drawable drawable, Scaling scaling) {
        this(type, actorType, drawable, scaling, Align.center);
    }
    public WyrActor(WyrType type, ActorType actorType, @Null Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
        this.wyrType = type;
        this.actorType = actorType;
        this.stats = new WyrStats(this, actorType);
        this.setAlign(Align.center);
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

    public void applyShader(ShaderState shaderState) {
        if(!shaderStates.contains(shaderState, true)) shaderStates.add(shaderState);
    }
    public void removeShader(ShaderState shaderState) {
        if(shaderStates.contains(shaderState, true)) shaderStates.removeValue(shaderState, true);
    }

    public void addInteractable(WyrInteraction interaction) { interactables.add(interaction); }

    public Array<WyrInteraction> getInteractables() { return interactables; }
    public WyrType getWyrType() { return wyrType; }

}
